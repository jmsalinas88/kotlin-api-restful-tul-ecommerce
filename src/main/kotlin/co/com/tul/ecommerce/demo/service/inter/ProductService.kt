package co.com.tul.ecommerce.demo.service.inter

import co.com.tul.ecommerce.demo.dto.ProductDTO
import java.util.*

interface ProductService {

     fun findAll():List<ProductDTO>
     fun create(productDto: ProductDTO):ProductDTO
     fun delete(id: UUID)
     fun update(id: UUID, productDto: ProductDTO):ProductDTO
     fun findProductsByCartId(id:UUID):List<ProductDTO>

}