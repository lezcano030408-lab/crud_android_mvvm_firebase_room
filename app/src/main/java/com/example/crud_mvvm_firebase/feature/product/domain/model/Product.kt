package com.example.crud_mvvm_firebase.feature.product.domain.model

data class Product(
    val id: String,
    val name: String,
    val description: String,
    val priceCop: Long,
    val quantity: Int,
    val updatedAt: Long = System.currentTimeMillis()
)

data class SyncReport(
    val uploaded: Int,
    val downloaded: Int
)
