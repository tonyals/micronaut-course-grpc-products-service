package br.com.tony.dto

data class ProductUpdateReq(
    val id: Long,
    val name: String,
    val price: Double,
    val quantityInStock: Int
)
