package co.com.tul.ecommerce.demo.dto

import co.com.tul.ecommerce.demo.model.CartStatus
import java.math.BigDecimal
import java.util.*
import javax.validation.constraints.PositiveOrZero

data class CartDTO(
        var id: UUID = UUID.randomUUID(),
        var items : List<CarItemDTO>,
        var status: CartStatus,
        @field:PositiveOrZero(message = "The total cost must be ZERO or positive")
        var total: BigDecimal = BigDecimal.ZERO
)
