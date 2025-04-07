import React, { useState, useEffect } from "react";
import { useParams } from "react-router-dom";
import EShopService from "../repository/EShopService";
import ProductCard from "../components/ProductCard/ProductCard";

const CategoryProducts=()=>{
    const{categoryId}=useParams();
    const [products,setProducts]=useState([]);


    useEffect(() => {
        EShopService.fetchProductsByCategory(categoryId)
            .then(response=>setProducts(response.data))
            .catch(error=>console.error("Error fetching category products:",error));

    }, [categoryId]);

    return(
        <div>

            <div className="product-list">
                {products.length>0?(
                    products.map(product=><ProductCard key={product.id} product={product}/> )
                ):(
                    <p>No products found for this category.</p>
                )}

            </div>

        </div>

    );
};
export default CategoryProducts;