package co.com.tul.ecommerce.demo.utils.mapper

import co.com.tul.ecommerce.demo.dto.CartDTO
import co.com.tul.ecommerce.demo.model.Cart
import co.com.tul.ecommerce.demo.utils.pricing.inter.PricingHelper
import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.util.*

@Service
class CartMapper (
        private val cartItemMapper: CartItemMapper,
        private val pricingHelper: PricingHelper
        ): Mapper<CartDTO, Cart>{

    override fun fromEntity(entity: Cart): CartDTO {
        val items = entity.items.map {
            cartItemMapper.fromEntity(it)
        }
        return CartDTO(UUID.fromString(entity.id), items, entity.status, pricingHelper.calculateTotalCart(items))
    }

    override fun toEntity(dto: CartDTO): Cart {
        val items = dto.items.map {
            cartItemMapper.toEntity(it)
        }
        return Cart(dto.id.toString(), items, dto.status)
    }
}