package com.example.crud_mvvm_firebase.feature.product.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "products")
data class ProductEntity(
    @PrimaryKey val id: String,
    val name: String,
    val description: String,
    val priceCop: Long,
    val quantity: Int,
    val updatedAt: Long,
    val pending: Boolean = false,
    val isDeleted: Boolean = false
)
