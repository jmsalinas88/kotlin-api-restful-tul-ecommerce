package co.com.tul.ecommerce.demo.model

import java.util.*
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.ManyToOne

@Entity
data class CartItem(
        @Id
        val id: String ? = UUID.randomUUID().toString(),
        var quantity: Long,
        @ManyToOne
        var product: Product
)
