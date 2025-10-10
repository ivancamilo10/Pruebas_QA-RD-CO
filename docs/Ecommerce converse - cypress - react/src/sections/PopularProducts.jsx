import { useMemo, useState } from "react";
import { products } from "../assets/constants";
import PopularProductCard from "../components/PopularProductCard";
import "animate.css";

const PopularProducts = () => {
  const [query, setQuery] = useState("");

  const filtered = useMemo(() => {
    const q = query.trim().toLowerCase();
    if (!q) return products;
    return products.filter((p) => p.name.toLowerCase().includes(q));
  }, [query]);

  return (
    <section id="products" className="max-container max-sm:mt-12">
      <div className="flex flex-col justify-start gap-5">
        <h2 className="text-4xl font-palanquin font-bold">
          Our <span className="text-purple-900">Popular</span> Products
        </h2>
        <p className="lg:max-w-lg mt-2 font-montserrat text-slate-gray">
          Experience top-notch quality and style with our sought-after
          selections. Discover a world of comfort, design, and value.
        </p>
      </div>

      {/* Search input used by Cypress tests */}
      <div className="mt-6">
        <input
          aria-label="Buscar productos"
          placeholder="Buscar"
          value={query}
          onChange={(e) => setQuery(e.target.value)}
          className="w-full max-w-md rounded-md border px-4 py-2"
        />
      </div>

      <div className="mt-16">
        {filtered.length === 0 ? (
          <div className="no-results">No se encontraron productos</div>
        ) : (
          // .product-list wrapper expected by tests
          <div className={`product-list grid lg:grid-cols-4 md:grid-cols-3 sm:grid-cols-2 grid-cols-1 sm:gap-4 gap-14 animate__animated animate__fadeIn animate__slow`}>
            {filtered.map((product, index) => (
              <PopularProductCard
                key={product.name}
                {...product}
                isLast={index === products.length - 1}
              />
            ))}
          </div>
        )}
      </div>
    </section>
  );
};

export default PopularProducts;
