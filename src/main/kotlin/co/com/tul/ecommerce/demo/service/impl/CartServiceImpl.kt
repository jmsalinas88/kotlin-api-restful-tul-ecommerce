package co.com.tul.ecommerce.demo.service.impl

import co.com.tul.ecommerce.demo.dto.AddProductToCartDTO
import co.com.tul.ecommerce.demo.dto.CartDTO
import co.com.tul.ecommerce.demo.exception.CartException
import co.com.tul.ecommerce.demo.exception.CartNotFoundException
import co.com.tul.ecommerce.demo.model.Cart
import co.com.tul.ecommerce.demo.model.CartItem
import co.com.tul.ecommerce.demo.model.CartStatus
import co.com.tul.ecommerce.demo.repository.CartRepository
import co.com.tul.ecommerce.demo.repository.ProductRepository
import co.com.tul.ecommerce.demo.service.inter.CartService
import co.com.tul.ecommerce.demo.utils.mapper.CartMapper
import org.springframework.stereotype.Service
import java.util.*

@Service
class CartServiceImpl(
    private val cartRepository: CartRepository,
    private val productRepository: ProductRepository,
    private val cartMapper: CartMapper
) : CartService {

    override fun create(cartDTO: CartDTO): CartDTO {
        val cart = cartMapper.toEntity(cartDTO)
        return cartMapper.fromEntity(cartRepository.save(cart))
    }

    private fun checkExistsProducts(newItems: List<AddProductToCartDTO>){
        var chekProductIds : MutableList<String> = mutableListOf()
        for(newItem : AddProductToCartDTO in newItems){
            var productId = newItem.product.sku.toString()
            var productExists = productRepository.existsById(productId)
            if(!productExists)
                chekProductIds.add(productId)
        }
        if(chekProductIds.isNotEmpty())
            throw CartException("Can't add, products: $chekProductIds don't exist")
    }

    private fun checkCartStatusNotCompleted(cart : Cart) {
        if(CartStatus.COMPLETED == cart.status)
            throw CartException("Can't add products the cart is ${CartStatus.COMPLETED}")
    }

    private fun getCartWithNewItems(cart: Cart, newItems: List<AddProductToCartDTO>) : Cart{
        var newCartItems : MutableList<CartItem> = mutableListOf()
        for(newItem : AddProductToCartDTO in newItems){
            var optProduct = productRepository.findById(newItem.product.sku.toString())
            optProduct.map {
                newCartItems.add(CartItem(UUID.randomUUID().toString(),newItem.quantity, it))
            }
        }
        var cartItems = cart.items.toMutableList()
        cartItems.addAll(newCartItems)
        cart.items = cartItems;
        return  cart
    }

    private fun getCartWithUpdatedItems(cart: Cart, itemsForUpdate: List<AddProductToCartDTO>) : Cart{
        var cartItems = cart.items.toMutableList()
        for(itemForUpdate : AddProductToCartDTO in itemsForUpdate){
            var idProductForUpdate = itemForUpdate.product.sku.toString()
            for (item : CartItem in cartItems){
                var productItem = item.product
                var productId = productItem.sku.toString()
                if(productId == idProductForUpdate){
                    item.quantity = itemForUpdate.quantity
                }
            }
        }
        cart.items = cartItems;
        return  cart
    }

    override fun addProducts(id: UUID, newItems: List<AddProductToCartDTO>): CartDTO {
        val idStr = id.toString()
        var optCart = cartRepository.findById(idStr)
        return optCart.map {
            checkCartStatusNotCompleted(it)
            checkExistsProducts(newItems)
            cartMapper.fromEntity(cartRepository.save(getCartWithNewItems(it, newItems)))
        }.orElseThrow { CartNotFoundException("Cart $idStr doesn't exist") }
    }

    override fun updateProducts(id: UUID, itemsForUpdate: List<AddProductToCartDTO>): CartDTO {
        val idStr = id.toString()
        var optCart = cartRepository.findById(idStr)
        return optCart.map {
            checkCartStatusNotCompleted(it)
            checkExistsProducts(itemsForUpdate)
            cartMapper.fromEntity(cartRepository.save(getCartWithUpdatedItems(it, itemsForUpdate)))
        }.orElseThrow { CartNotFoundException("Cart $idStr doesn't exist") }
    }

    override fun deleteProducts(id: UUID, idProducts: List<UUID>){
        val idStr = id.toString()
        val optCart = cartRepository.findById(idStr)
        optCart.map {
            val carItems = it.items
            var newCartItems : MutableList<CartItem> = mutableListOf()
            for (idProducto : UUID in idProducts){
                cartRepository.deleteProduct(idProducto.toString())
                for(carItem : CartItem in carItems){
                    if(idProducto.toString() != carItem.product.sku.toString()){
                        newCartItems.add(carItem)
                    }
                }
            }
            it.items = newCartItems
            cartRepository.save(it)
        }.orElseThrow{CartNotFoundException("Cart $idStr doesn't exist")}
    }

    override fun checkout(id: UUID): CartDTO {
        val idStr = id.toString()
        val optCart = cartRepository.findById(idStr)
        return optCart.map {
            it.status = CartStatus.COMPLETED
            cartMapper.fromEntity(cartRepository.save(it))
        }.orElseThrow{CartNotFoundException("Cart $idStr doesn't exist")}
    }
}