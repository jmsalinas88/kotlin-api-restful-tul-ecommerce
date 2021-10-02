package co.com.tul.ecommerce.demo.utils.mapper

import co.com.tul.ecommerce.demo.dto.ProductDTO
import co.com.tul.ecommerce.demo.model.Product
import org.springframework.stereotype.Service
import java.util.*

@Service
class ProductMapper : Mapper<ProductDTO, Product> {

    override fun fromEntity(entity: Product): ProductDTO = ProductDTO(
            UUID.fromString(entity.sku),
            entity.name,
            entity.description,
            entity.price,
            entity.type
    )

    override fun toEntity(dto: ProductDTO): Product = Product(
            dto.sku.toString(),
            dto.name,
            dto.description,
            dto.price,
            dto.type
    )
}