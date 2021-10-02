package co.com.tul.ecommerce.demo.web.rest.controller

import co.com.tul.ecommerce.demo.dto.AddProductToCartDTO
import co.com.tul.ecommerce.demo.dto.CartDTO
import co.com.tul.ecommerce.demo.dto.ProductDTO
import co.com.tul.ecommerce.demo.service.inter.CartService
import co.com.tul.ecommerce.demo.service.inter.ProductService
import co.com.tul.ecommerce.demo.utils.Utils
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*
import javax.validation.Valid

@RestController
@RequestMapping("/api/carts")
class CartController(
        private val cartService: CartService,
        private val productService: ProductService
) {

    @PostMapping
    fun create(@Valid @RequestBody cartDTO: CartDTO):ResponseEntity<CartDTO> =
            ResponseEntity(cartService.create(cartDTO), HttpStatus.CREATED)

    @PostMapping("/{id}/items")
    fun addProducts(@PathVariable id : UUID , @Valid @RequestBody products: List<AddProductToCartDTO>) =
            ResponseEntity.ok(cartService.addProducts(id, products))

    @PutMapping("/{id}/items")
    fun updateProducts(@PathVariable id : UUID , @Valid @RequestBody products: List<AddProductToCartDTO>) =
            ResponseEntity.ok(cartService.updateProducts(id, products))

    @GetMapping("{id}/products")
    fun findProductsByCartId(@PathVariable id :UUID):ResponseEntity<List<ProductDTO>> =
            ResponseEntity.ok(productService.findProductsByCartId(id))

    @DeleteMapping("{id}/items/")
    fun deleteProducts(@PathVariable id : UUID, @RequestParam("ids") ids : String) =
            ResponseEntity(cartService.deleteProducts(id,Utils.strIdsToUUIDList(ids)), HttpStatus.NO_CONTENT)

    @GetMapping("{id}/checkout")
    fun checkout (@PathVariable id : UUID) : ResponseEntity<CartDTO> =
            ResponseEntity.ok(cartService.checkout(id))
}