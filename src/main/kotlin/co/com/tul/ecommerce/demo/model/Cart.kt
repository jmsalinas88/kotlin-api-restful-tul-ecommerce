package co.com.tul.ecommerce.demo.model

import javax.persistence.*

@Entity
class Cart (
        @Id
        val id: String,
        @OneToMany(fetch=FetchType.LAZY, cascade = [CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE])
        var items : List<CartItem>,
        @Enumerated(EnumType.STRING)
        var status: CartStatus
)