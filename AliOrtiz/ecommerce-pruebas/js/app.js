const PRODUCTS = [
  {id:1, title:"Celular Samsung", price:500, category:"Electrónica", img:"https://picsum.photos/200?1"},
  {id:2, title:"Notebook Lenovo", price:900, category:"Electrónica", img:"https://picsum.photos/200?2"},
  {id:3, title:"Sofá 3 plazas", price:350, category:"Hogar", img:"https://picsum.photos/200?3"},
  {id:4, title:"Taza personalizada", price:15, category:"Hogar", img:"https://picsum.photos/200?4"},
  {id:5, title:"Camiseta Nike", price:40, category:"Ropa", img:"https://picsum.photos/200?5"},
  {id:6, title:"Zapatillas Adidas", price:70, category:"Ropa", img:"https://picsum.photos/200?6"}
];

let cart = {};

const el = {
  products: document.getElementById("products"),
  electronics: document.getElementById("electronics"),
  home: document.getElementById("home"),
  clothes: document.getElementById("clothes"),
  template: document.getElementById("product-template"),
  cartBtn: document.getElementById("cart-btn"),
  cartModal: document.getElementById("cart-modal"),
  closeCart: document.getElementById("close-cart"),
  cartItems: document.getElementById("cart-items"),
  cartTotal: document.getElementById("cart-total"),
  cartCount: document.getElementById("cart-count"),
  cartItemTpl: document.getElementById("cart-item-template"),
  search: document.getElementById("search"),
  category: document.getElementById("category"),
  price: document.getElementById("price"),
  priceLabel: document.getElementById("price-label"),
  sort: document.getElementById("sort")
};

function init(){
  renderAllSections(PRODUCTS);
  populateCategories();
  bindEvents();
}

function renderAllSections(data){
  renderProducts(el.products, data.slice(0,3)); // ofertas del día
  renderProducts(el.electronics, data.filter(p => p.category==="Electrónica"));
  renderProducts(el.home, data.filter(p => p.category==="Hogar"));
  renderProducts(el.clothes, data.filter(p => p.category==="Ropa"));
}

function renderProducts(container, products){
  container.innerHTML = "";
  products.forEach(p=>{
    const tpl = el.template.content.cloneNode(true);
    tpl.querySelector(".product-img").src = p.img;
    tpl.querySelector(".product-title").textContent = p.title;
    tpl.querySelector(".product-price").textContent = `$${p.price}`;
    tpl.querySelector(".add-to-cart").addEventListener("click", ()=>addToCart(p));
    container.appendChild(tpl);
  });
}

function addToCart(product){
  if(!cart[product.id]) cart[product.id] = {...product, qty:0};
  cart[product.id].qty++;
  renderCart();
}

function renderCart(){
  el.cartItems.innerHTML = "";
  let total=0, count=0;
  Object.values(cart).forEach(item=>{
    const tpl = el.cartItemTpl.content.cloneNode(true);
    tpl.querySelector(".cart-item-title").textContent = item.title;
    tpl.querySelector(".cart-item-price").textContent = `$${item.price*item.qty}`;
    tpl.querySelector(".item-qty").value = item.qty;
    tpl.querySelector(".remove-item").addEventListener("click", ()=>{
      delete cart[item.id];
      renderCart();
    });
    tpl.querySelector(".item-qty").addEventListener("change",(e)=>{
      item.qty = parseInt(e.target.value)||1;
      renderCart();
    });
    el.cartItems.appendChild(tpl);
    total += item.price*item.qty;
    count += item.qty;
  });
  el.cartTotal.textContent = `$${total}`;
  el.cartCount.textContent = count;
}

/* --- Filtros --- */
function populateCategories(){
  const cats = [...new Set(PRODUCTS.map(p=>p.category))];
  cats.forEach(c=>{
    const opt=document.createElement("option");
    opt.value=c;
    opt.textContent=c;
    el.category.appendChild(opt);
  });
}

function applyFilters(){
  let data = PRODUCTS.slice();
  if(el.search.value.trim()){
    data = data.filter(p=>p.title.toLowerCase().includes(el.search.value.toLowerCase()));
  }
  if(el.category.value!=="all"){
    data = data.filter(p=>p.category===el.category.value);
  }
  data = data.filter(p=>p.price<=el.price.value);
  switch(el.sort.value){
    case "price-asc": data.sort((a,b)=>a.price-b.price); break;
    case "price-desc": data.sort((a,b)=>b.price-a.price); break;
    case "name-asc": data.sort((a,b)=>a.title.localeCompare(b.title)); break;
    case "name-desc": data.sort((a,b)=>b.title.localeCompare(a.title)); break;
  }
  renderAllSections(data);
}

function bindEvents(){
  el.cartBtn.addEventListener("click", ()=>el.cartModal.setAttribute("aria-hidden","false"));
  el.closeCart.addEventListener("click", ()=>el.cartModal.setAttribute("aria-hidden","true"));
  el.search.addEventListener("input", applyFilters);
  el.category.addEventListener("change", applyFilters);
  el.price.addEventListener("input", ()=>{
    el.priceLabel.textContent=el.price.value;
    applyFilters();
  });
  el.sort.addEventListener("change", applyFilters);
  document.getElementById("checkout").addEventListener("click",()=>{
    alert("Compra realizada!");
    cart={};
    renderCart();
    el.cartModal.setAttribute("aria-hidden","true");
  });
}

init();
