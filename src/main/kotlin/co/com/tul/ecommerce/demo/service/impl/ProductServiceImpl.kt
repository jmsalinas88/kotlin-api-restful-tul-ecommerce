package co.com.tul.ecommerce.demo.service.impl

import co.com.tul.ecommerce.demo.dto.ProductDTO
import co.com.tul.ecommerce.demo.exception.ProductException
import co.com.tul.ecommerce.demo.exception.ProductNotFoundException
import co.com.tul.ecommerce.demo.repository.ProductRepository
import co.com.tul.ecommerce.demo.service.inter.ProductService
import co.com.tul.ecommerce.demo.utils.calculator.inter.DiscuntCalculator
import co.com.tul.ecommerce.demo.utils.mapper.ProductMapper
import org.springframework.stereotype.Service
import java.util.*

@Service
class ProductServiceImpl(
     private val  productRepository: ProductRepository,
     private val productMapper: ProductMapper,
     private val discountCalculator: DiscuntCalculator
) : ProductService {

    override fun findAll(): List<ProductDTO> {
        val products = productRepository.getAllProducts();
        if(products.isEmpty())
            throw  ProductException("List of products is empty")
        return products.map {
            productMapper.fromEntity(it)
        }
    }

    override fun create(productDto: ProductDTO):ProductDTO {
        var product = discountCalculator.calculateDiscount(productMapper.toEntity(productDto))
        return productMapper.fromEntity(productRepository.save(product));
    }

    override fun delete(id: UUID) {
        checkIfProductExists(id)
        checkIfExistsAssociatedCart(id)
        productRepository.deleteById(id.toString())
    }

    override fun update(id: UUID, productDto: ProductDTO): ProductDTO {
        checkIfProductExists(id)
        productDto.sku = id
        return productMapper.fromEntity(productRepository.save(productMapper.toEntity(productDto)));
    }

    override fun findProductsByCartId(id: UUID): List<ProductDTO> {
        val products = productRepository.getAllProductsByCartId(id.toString())
        if(products.isEmpty())
            throw  ProductException("List of products for the cart ${id.toString()} is empty")
        return products.map {
            productMapper.fromEntity(it)
        }
    }

    private fun checkIfExistsAssociatedCart(id: UUID){
            var idStr = id.toString()
            if(productRepository.existsAssociatedCart(idStr) > 0)
                throw  ProductException("Can't delete product $idStr it's associated to some cart ")
    }

    private fun checkIfProductExists(id: UUID){
        var idStr = id.toString()
        val exists = productRepository.existsById(idStr)
        if(!exists)
            throw ProductNotFoundException("Product with sku ${idStr} doesn't exist")
    }
}