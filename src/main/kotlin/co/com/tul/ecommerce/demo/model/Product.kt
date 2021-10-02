package co.com.tul.ecommerce.demo.model

import org.hibernate.annotations.GenericGenerator
import java.math.BigDecimal
import java.util.*
import javax.persistence.*

@Entity
data class Product (
    @Id
    @Column(name = "id")
    val sku: String,
    var name: String,
    var description: String,
    var price : BigDecimal,
    @Enumerated(EnumType.STRING)
    var type: ProductType
)