package co.com.tul.ecommerce.demo.utils.pricing.inter

import co.com.tul.ecommerce.demo.dto.CarItemDTO
import java.math.BigDecimal

interface PricingHelper {
    fun calculateTotalCart(items : List<CarItemDTO>) : BigDecimal
}