# reporte_completo_corregido.py
import os
import io
import re
import shutil
import base64
from datetime import datetime
from collections import Counter
from typing import List, Union, Optional
from PIL import Image
import matplotlib.pyplot as plt
import html as html_lib

# ---------------- utilidades ----------------
def _ensure_str(s):
    return "" if s is None else str(s)

def _flatten_faltas(f):
    """Normaliza faltas_ortograficas a lista de strings"""
    out = []
    if f is None:
        return out
    if isinstance(f, str):
        parts = [p.strip() for p in f.split(",")] if "," in f else [f.strip()]
        return [p for p in parts if p]
    if isinstance(f, (list, tuple, set)):
        for it in f:
            if it is None:
                continue
            if isinstance(it, (list, tuple, set)):
                out.extend(_flatten_faltas(it))
            else:
                out.append(str(it).strip())
    else:
        out.append(str(f).strip())
    # quitar vacíos y duplicados manteniendo orden
    seen = set()
    res = []
    for x in out:
        if x and x not in seen:
            res.append(x)
            seen.add(x)
    return res

# ---------- CORRECCIÓN: función de resaltado segura ----------
def _highlight_text_with_words(raw_text: str, misspellings: list) -> str:
    """
    Escapa el texto y resalta cada palabra en 'misspellings' envolviéndola en un <span>
    en rojo. Evita lookbehinds de ancho variable para no lanzar excepciones.
    """
    text = html_lib.escape(_ensure_str(raw_text))

    # normalizar tokens: quitar vacíos, ordenar por longitud descendente (evitar reemplazos parciales)
    tokens = [t for t in (x.strip() for x in (misspellings or [])) if t]
    # mantener orden único (preserva primer aparición)
    seen = set()
    uniq_tokens = []
    for t in tokens:
        if t not in seen:
            uniq_tokens.append(t)
            seen.add(t)
    tokens = sorted(uniq_tokens, key=lambda s: -len(s))

    if not tokens:
        return text.replace("\n", "<br/>")

    for tok in tokens:
        p = re.escape(tok)

        # si el token contiene espacios, usamos búsqueda literal (sin límites de palabra)
        if re.search(r'\s', tok):
            try:
                pattern = re.compile(p, flags=re.IGNORECASE)
            except re.error:
                pattern = None
        else:
            # usar límites con lookbehind/lookahead de ancho 1 (permitidos por Python)
            # (?<!\w)(...)(?!\w) asegura coincidencia de palabra sin ancho variable
            try:
                pattern = re.compile(rf'(?<!\w)({p})(?!\w)', flags=re.IGNORECASE)
            except re.error:
                pattern = None

        def repl(m):
            matched = m.group(1) if m.groups() else m.group(0)
            return (
                f'<span style="color:#b91c1c;background:#fff1f1;'
                f'padding:2px 4px;border-radius:4px;font-weight:600">{matched}</span>'
            )

        if pattern is not None:
            try:
                text = pattern.sub(repl, text)
                continue
            except re.error:
                # si falla por cualquier razón en regex, caeremos al fallback
                pass

        # fallback: reemplazo case-insensitive simple (no word boundaries)
        def _ci_replace(s, old):
            pattern_simple = re.compile(re.escape(old), flags=re.IGNORECASE)
            return pattern_simple.sub(lambda m: repl(m), s)

        text = _ci_replace(text, tok)

    return text.replace("\n", "<br/>")

# ---------- funciones de gráficas ----------
def _chart_bar_save(counter: Counter, top_n: int, out_file: Optional[str] = None):
    most = counter.most_common(top_n)
    if not most:
        fig = plt.figure(figsize=(6,2))
        plt.text(0.5, 0.5, "No hay datos", ha='center', va='center')
        plt.axis('off')
    else:
        labels, values = zip(*most)
        fig, ax = plt.subplots(figsize=(8, max(2, 0.33*len(labels)+1)))
        ax.barh(range(len(labels)), values)
        ax.set_yticks(range(len(labels)))
        ax.set_yticklabels(labels)
        ax.invert_yaxis()
        ax.set_xlabel("Frecuencia")
        ax.set_title(f"Top {top_n} - Errores más frecuentes")
        plt.tight_layout()
    if out_file:
        fig.savefig(out_file, bbox_inches='tight')
    buf = io.BytesIO()
    fig.savefig(buf, format="png", bbox_inches='tight')
    plt.close(fig)
    buf.seek(0)
    return base64.b64encode(buf.read()).decode('ascii')

def _chart_pie_save(total_words: int, good_words: int, error_words: int, out_file: Optional[str] = None):
    labels = []
    sizes = []
    if good_words > 0:
        labels.append("Palabras buenas")
        sizes.append(good_words)
    if error_words > 0:
        labels.append("Palabras con errores")
        sizes.append(error_words)
    others = max(0, total_words - (good_words + error_words))
    if others > 0:
        labels.append("Otras")
        sizes.append(others)
    if sum(sizes) == 0:
        fig = plt.figure(figsize=(4,3))
        plt.text(0.5, 0.5, "No hay datos", ha='center', va='center')
        plt.axis('off')
    else:
        fig, ax = plt.subplots(figsize=(5,4))
        ax.pie(sizes, labels=labels, autopct="%1.1f%%", startangle=130)
        ax.set_title("Distribución: palabras buenas vs con errores")
        ax.axis('equal')
        plt.tight_layout()
    if out_file:
        fig.savefig(out_file, bbox_inches='tight')
    buf = io.BytesIO()
    fig.savefig(buf, format="png", bbox_inches='tight')
    plt.close(fig)
    buf.seek(0)
    return base64.b64encode(buf.read()).decode('ascii')

# ---------------- función principal ----------------
def generate_report_from_elements(
    page_name: str,
    elementos_faltas: List[Union[object, dict]],
    paragraphs_count: int,
    words_count: int,
    errors_count: int,
    screenshot_path: Optional[str] = None,
    output_root: str = "reports",
    top_n: int = 15
) -> str:
    """
    Genera carpeta única con reporte HTML y recursos.
    Devuelve la ruta al HTML generado.
    """
    # Normalizar elementos a dicts con las claves esperadas
    normalized = []
    for el in elementos_faltas:
        if el is None:
            continue
        if isinstance(el, dict):
            d = el.copy()
        else:
            d = {
                "etiqueta": getattr(el, "etiqueta", getattr(el, "tag", "")),
                "texto": getattr(el, "texto", getattr(el, "text", "")),
                "id_elemento": getattr(el, "id_elemento", getattr(el, "element_id", "")),
                "clase_elemento": getattr(el, "clase_elemento", getattr(el, "element_class", "")),
                "ubicacion": getattr(el, "ubicacion", getattr(el, "location", "")),
                "esta_activo": getattr(el, "esta_activo", getattr(el, "is_active", "")),
                "faltas_ortograficas": getattr(el, "faltas_ortograficas", getattr(el, "errors", []))
            }
        d["texto"] = _ensure_str(d.get("texto", ""))
        d["id_elemento"] = _ensure_str(d.get("id_elemento", ""))
        d["ubicacion"] = d.get("ubicacion", "")
        d["faltas_ortograficas"] = _flatten_faltas(d.get("faltas_ortograficas", []))
        normalized.append(d)

    # contador global de errores por token (ocurrencias)
    all_faults_flat = []
    for d in normalized:
        all_faults_flat.extend(d["faltas_ortograficas"])
    counter = Counter(all_faults_flat)

    # preparar carpeta timestamp
    ts = datetime.now().strftime("%Y%m%d_%H%M%S")
    folder_name = f"report_{ts}"
    folder_path = os.path.join(output_root, folder_name)
    os.makedirs(folder_path, exist_ok=True)

    # copiar screenshot y crear b64
    screenshot_b64 = ""
    screenshot_mime = "image/png"
    if screenshot_path and os.path.exists(screenshot_path):
        dest = os.path.join(folder_path, "screenshot.png")
        try:
            img = Image.open(screenshot_path)
            img.save(dest, format="PNG")
        except Exception:
            shutil.copy2(screenshot_path, dest)
        with open(dest, "rb") as f:
            screenshot_b64 = base64.b64encode(f.read()).decode('ascii')

    # generar gráficas y guardarlas
    chart_bar_file = os.path.join(folder_path, "chart_bar.png")
    chart_bar_b64 = _chart_bar_save(counter, top_n=top_n, out_file=chart_bar_file)

    good_words = max(0, words_count - errors_count)
    chart_pie_file = os.path.join(folder_path, "chart_pie.png")
    chart_pie_b64 = _chart_pie_save(words_count, good_words, errors_count, out_file=chart_pie_file)

    # preparar filas top para la tabla
    top_rows = counter.most_common(top_n)
    if top_rows:
        top_rows_html = "\n".join("<tr><td>{}</td><td>{}</td></tr>".format(html_lib.escape(k), v) for k,v in top_rows)
    else:
        top_rows_html = "<tr><td colspan='2' class='meta'>No hay datos</td></tr>"

    # preparar detalle por elemento: id, ubicacion, faltas list, texto con resaltado
    elements_html_parts = []
    for d in normalized:
        id_el = html_lib.escape(d.get("id_elemento", "—"))
        ubic = d.get("ubicacion", "")
        if isinstance(ubic, (tuple, list)) and len(ubic) >= 2:
            ubic_s = f"{ubic[0]}, {ubic[1]}"
        elif isinstance(ubic, dict):
            x = ubic.get("x", ubic.get("left", ""))
            y = ubic.get("y", ubic.get("top", ""))
            ubic_s = f"{x}, {y}"
        else:
            ubic_s = html_lib.escape(str(ubic))
        faltas = d.get("faltas_ortograficas", [])
        faltas_disp = ", ".join(faltas) if faltas else "—"
        highlighted = _highlight_text_with_words(d.get("texto", ""), faltas)

        part = f"""
        <div class="elem card" style="margin-bottom:12px">
          <div style="display:flex;justify-content:space-between;gap:8px;align-items:center">
            <div><b>ID:</b> {id_el} <span style="color:#6b7280;margin-left:8px"><b>Ubicación:</b> {html_lib.escape(ubic_s)}</span></div>
            <div style="color:#6b7280"><b>Faltas:</b> {html_lib.escape(faltas_disp)}</div>
          </div>
          <div style="margin-top:8px;line-height:1.45">{highlighted}</div>
        </div>
        """
        elements_html_parts.append(part)

    elements_html = "\n".join(elements_html_parts) if elements_html_parts else "<div class='meta'>— Sin elementos con faltas —</div>"

    # Template HTML (placeholders)
    with open("template.html", "r", encoding="utf-8") as f:
            html_template = f.read()

    html_filled = html_template.replace("__PAGE__", html_lib.escape(page_name))\
                               .replace("__FECHA__", datetime.now().strftime("%Y-%m-%d %H:%M:%S"))\
                               .replace("__PARAGRAPHS__", str(paragraphs_count))\
                               .replace("__WORDS__", str(words_count))\
                               .replace("__ERRORS__", str(errors_count))\
                               .replace("__UNIQUE__", str(len(counter)))\
                               .replace("__CHARTBAR__", chart_bar_b64)\
                               .replace("__CHARTPIE__", chart_pie_b64)\
                               .replace("__TOPN__", str(top_n))\
                               .replace("__TOP_ROWS__", top_rows_html)\
                               .replace("__ELEMENTS_DETAIL__", elements_html)\
                               .replace("__GOODWORDS__", str(good_words))\
                               .replace("__SCREENSHOT_BLOCK__", (f'<div class="screenshot-box"><img src="data:{screenshot_mime};base64,{screenshot_b64}" alt="screenshot"/></div>' if screenshot_b64 else "<div class='meta'>No hay captura proporcionada.</div>"))\
                               .replace("__FOLDER__", folder_path)

    html_filename = f"report_{ts}.html"
    html_path = os.path.join(folder_path, html_filename)
    with open(html_path, "w", encoding="utf-8") as f:
        f.write(html_filled)

    return html_path

# ---------------- ejemplo de uso ----------------
if __name__ == "__main__":
    # definimos una clase de ejemplo como la tuya (opcional)
    class ElementData:
        def __init__(self, etiqueta, texto, id_elemento, clase_elemento, ubicacion, esta_activo, faltas_ortograficas):
            self.etiqueta = etiqueta
            self.texto = texto
            self.id_elemento = id_elemento
            self.clase_elemento = clase_elemento
            self.ubicacion = ubicacion
            self.esta_activo = esta_activo
            self.faltas_ortograficas = faltas_ortograficas

    elementos_faltas = [
        ElementData("p", "Bienvenido a nuestra tienda de laptops y accesorios.", "p-1", "intro", (120,300), True, []),
        ElementData("p", "Ofertas en productos, catalogo y destacad0s en articulos electronicos.", "p-2", "promo", (140,420), True, ["catalogo", "destacad0s", "articulos"]),
        ElementData("p", "Contáctanos para mas informacion y aprovecha estas ofertass.", "p-3", "contact", (180,520), True, ["Contáctanos", "ofertass"]),
    ]

    out = generate_report_from_elements(
        page_name="https://ejemplo.com/tienda",
        elementos_faltas=elementos_faltas,
        paragraphs_count=86,
        words_count=243,
        errors_count=108,
        screenshot_path=None,   # o "captura_larga.png"
        output_root="reports",
        top_n=10
    )
    print("Reporte generado en:", out)
