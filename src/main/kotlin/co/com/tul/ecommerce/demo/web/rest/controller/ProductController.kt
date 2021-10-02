package co.com.tul.ecommerce.demo.web.rest.controller

import co.com.tul.ecommerce.demo.dto.ProductDTO
import co.com.tul.ecommerce.demo.service.inter.ProductService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*
import javax.validation.Valid


@RestController
@RequestMapping("/api/products")
class ProductController(
        private  val productService: ProductService
) {

    @PostMapping
    fun create(@Valid @RequestBody productDto:ProductDTO):ResponseEntity<ProductDTO> =
        ResponseEntity(productService.create(productDto), HttpStatus.CREATED)

    @GetMapping
    fun getAll() : ResponseEntity<List<ProductDTO>> =
            ResponseEntity.ok(productService.findAll())

    @PutMapping("/{id}")
    fun update(@PathVariable id : UUID, @Valid @RequestBody productDto: ProductDTO):ResponseEntity<ProductDTO> =
            ResponseEntity.ok(productService.update(id,productDto))

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id : UUID): ResponseEntity<Unit> =
            ResponseEntity(productService.delete(id), HttpStatus.NO_CONTENT)
}