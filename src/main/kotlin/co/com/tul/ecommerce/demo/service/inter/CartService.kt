package co.com.tul.ecommerce.demo.service.inter

import co.com.tul.ecommerce.demo.dto.AddProductToCartDTO
import co.com.tul.ecommerce.demo.dto.CartDTO
import java.util.*

interface CartService {
    fun create(cartDTO: CartDTO):CartDTO
    fun addProducts(id:UUID, newItems: List<AddProductToCartDTO>):CartDTO
    fun updateProducts(id:UUID, itemsForUpdate: List<AddProductToCartDTO>):CartDTO
    fun deleteProducts(id:UUID,idProducts: List<UUID>)
    fun checkout(id: UUID):CartDTO
}