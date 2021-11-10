package br.com.tony.dto

data class ProductRes(
    val id: Long,
    val name: String,
    val price: Double,
    val quantityInStock: Int
)
