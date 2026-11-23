package com.example.crud_mvvm_firebase.feature.product.domain.repository

import com.example.crud_mvvm_firebase.feature.product.domain.model.Product
import com.example.crud_mvvm_firebase.feature.product.domain.model.SyncReport
import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    fun observeProducts(): Flow<List<Product>>
    suspend fun findById(id: String): Product?
    suspend fun save(product: Product)
    suspend fun delete(id: String)
    suspend fun syncNow(): SyncReport
}
