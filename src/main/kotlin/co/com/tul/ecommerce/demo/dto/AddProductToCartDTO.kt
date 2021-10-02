package co.com.tul.ecommerce.demo.dto

import javax.validation.constraints.Positive

data class AddProductToCartDTO(
        val product: ProductDTO,
        @field:Positive(message = "The quantity must be positive")
        val quantity:Long
)
