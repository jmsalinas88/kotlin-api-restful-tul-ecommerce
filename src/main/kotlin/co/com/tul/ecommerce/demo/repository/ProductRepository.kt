package co.com.tul.ecommerce.demo.repository

import co.com.tul.ecommerce.demo.model.Product
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface ProductRepository : CrudRepository<Product, String> {

    @Query("SELECT * FROM product", nativeQuery = true)
    fun getAllProducts() : List<Product>

    @Query("SELECT p.* " +
            "FROM CART as c " +
            "INNER JOIN CART_ITEMS as cis " +
            "ON c.id = cis.cart_id " +
            "INNER JOIN CART_ITEM ci " +
            "ON ci.id = cis.items_id " +
            "INNER JOIN PRODUCT p " +
            "ON p.id = ci.product_id " +
            "WHERE c.id = :id", nativeQuery = true)
    fun getAllProductsByCartId(id: String) : List<Product>

    @Query("SELECT COUNT(*) " +
                 "FROM CART_ITEM " +
                 "WHERE product_id = :idProduct ", nativeQuery = true)
    fun existsAssociatedCart(@Param("idProduct") idProduct: String) : Long

}