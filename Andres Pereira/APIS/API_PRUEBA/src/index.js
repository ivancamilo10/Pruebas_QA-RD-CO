import express from "express";

const app = express();
const port = 4040;

// Middleware para parsear JSON
app.use(express.json());

let productos = [];

// GET todos los productos
app.get("/productos", (req, res) => {
  res.json(productos);
  
});

// GET producto por ID
app.get("/productos/:id", (req, res) => {
  const product = productos.find((i) => i.id === parseInt(req.params.id));

  if (!product) {
    return res.status(404).json({ message: "Persona no encontrada" });
  }

  res.json(product);
});

// POST crear producto
app.post("/productos", (req, res) => {
  try {
    const { nombre, precio, stock } = req.body;
    if (!nombre || !precio || !stock) {
      return res.status(400).json({ message: "Faltan campos obligatorios" });
    }

    const newProduct = {
      id: productos.length > 0 ? productos[productos.length - 1].id + 1 : 1,
      nombre,
      precio,
      stock,
    };

    productos.push(newProduct);

    res.status(201).json(newProduct);
  } catch (e) {
    console.log({ message: "Error del servidor", error: e });
  }
});

// PUT actualizar producto
app.put("/productos/:id", (req, res) => {
  const id = parseInt(req.params.id);
  const index = productos.findIndex((i) => i.id === id);

  if (index === -1) {
    return res.status(404).json({ message: "Producto no encontrado" });
  }

  productos[index] = { ...productos[index], ...req.body };

  res.json(productos[index]);
});

// DELETE eliminar producto
app.delete("/productos/:id", (req, res) => {
  const id = parseInt(req.params.id);
  const product = productos.find((i) => i.id === id);

  if (!product) {
    return res.status(404).json({ message: "Producto no encontrado" });
  }

  productos = productos.filter((a) => a.id !== id);

  res.json({ message: "Producto eliminado", productos });
});

app.listen(port, () => {
  console.log("Servidor corriendo en http://localhost:" + port);
});
