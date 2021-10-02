package co.com.tul.ecommerce.demo.utils.mapper

import co.com.tul.ecommerce.demo.dto.CarItemDTO
import co.com.tul.ecommerce.demo.model.CartItem
import org.springframework.stereotype.Service
import java.util.*

@Service
class CartItemMapper : Mapper<CarItemDTO, CartItem> {

    override fun fromEntity(entity: CartItem): CarItemDTO = CarItemDTO(
               UUID.fromString(entity.id),
               entity.quantity,
               entity.product
    )

    override fun toEntity(dto: CarItemDTO): CartItem = CartItem(
            dto.id.toString(),
            dto.quantity,
            dto.product
    )
}