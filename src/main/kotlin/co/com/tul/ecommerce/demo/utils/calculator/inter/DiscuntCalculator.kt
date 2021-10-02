package co.com.tul.ecommerce.demo.utils.calculator.inter

import co.com.tul.ecommerce.demo.model.Product

interface DiscuntCalculator {
    fun calculateDiscount(product: Product):Product
}