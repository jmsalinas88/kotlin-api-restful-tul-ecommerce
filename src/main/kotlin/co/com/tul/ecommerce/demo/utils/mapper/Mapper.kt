package co.com.tul.ecommerce.demo.utils.mapper

interface Mapper<D, E> {
    fun fromEntity(entity: E):D
    fun toEntity(dto:D):E
}