package co.com.tul.ecommerce.demo.repository

import co.com.tul.ecommerce.demo.model.Cart
import co.com.tul.ecommerce.demo.model.Product
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface CartRepository : CrudRepository<Cart, String> {

    @Query("DELETE ci, cis " +
            "FROM CART_ITEM as ci " +
            "INNER JOIN CART_ITEMS as cis ON ci.id = cis.ITEMS_ID " +
            "INNER JOIN PRODUCT as p ON p.id =  ci.product_id " +
            "WHERE p.id = :id", nativeQuery = true)
    fun deleteProduct(id: String)

}