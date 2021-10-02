package co.com.tul.ecommerce.demo.dto

import co.com.tul.ecommerce.demo.model.Product
import java.util.*

data class CarItemDTO(
        val id: UUID = UUID.randomUUID(),
        var quantity: Long,
        var product: Product
)
