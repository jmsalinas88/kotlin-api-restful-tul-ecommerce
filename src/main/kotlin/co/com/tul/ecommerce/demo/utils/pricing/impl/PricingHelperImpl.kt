package co.com.tul.ecommerce.demo.utils.pricing.impl

import co.com.tul.ecommerce.demo.dto.CarItemDTO
import co.com.tul.ecommerce.demo.utils.pricing.inter.PricingHelper
import org.springframework.stereotype.Component
import java.math.BigDecimal

@Component
class PricingHelperImpl : PricingHelper {

    override fun calculateTotalCart(items: List<CarItemDTO>): BigDecimal {
        var totalCost : BigDecimal = BigDecimal.ZERO
        for(item : CarItemDTO in items){
            totalCost = totalCost.add(item.product.price.multiply(BigDecimal.valueOf(item.quantity)))
        }
        return totalCost
    }
}