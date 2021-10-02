package co.com.tul.ecommerce.demo.dto

import co.com.tul.ecommerce.demo.model.ProductType
import java.math.BigDecimal
import java.util.*
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Positive

data class ProductDTO(
        var sku: UUID = UUID.randomUUID(),
        @field:NotBlank(message = "The name is required can't be blank")
        var name: String,
        @field:NotBlank(message = "The description is required can't be blank")
        var description: String,
        @field:Positive(message = "The price must be positive")
        var price : BigDecimal,
        var type: ProductType
)

