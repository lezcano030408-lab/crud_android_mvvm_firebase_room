package com.example.crud_mvvm_firebase.feature.product.data.remote

import kotlin.jvm.JvmField

data class ProductDto(
    val id: String = "",
    val name: String = "",
    val description: String = "",
    val priceCop: Long = 0L,
    val quantity: Int = 0,
    val updatedAt: Long = 0L,
    @JvmField
    val isDeleted: Boolean = false
)
