package co.com.tul.ecommerce.demo.utils.calculator.impl

import co.com.tul.ecommerce.demo.model.Product
import co.com.tul.ecommerce.demo.model.ProductType
import co.com.tul.ecommerce.demo.utils.calculator.inter.DiscuntCalculator
import org.springframework.stereotype.Component
import java.math.BigDecimal

@Component
class DiscountCalculatorFiftyPercent : DiscuntCalculator {

    val DIVISOR = 2.0

    override fun calculateDiscount(product: Product): Product {
        when(product.type){
            ProductType.WITH_DISCOUNT -> product.price = product.price.divide(BigDecimal(DIVISOR))
            ProductType.SIMPLE -> product.price = product.price
        }
        return product
    }

}