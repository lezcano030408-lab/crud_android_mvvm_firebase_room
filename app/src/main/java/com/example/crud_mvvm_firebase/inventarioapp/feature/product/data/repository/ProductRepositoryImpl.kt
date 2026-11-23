package com.example.crud_mvvm_firebase.inventarioapp.feature.product.data.repository

import com.example.crud_mvvm_firebase.inventarioapp.feature.product.data.local.ProductDao
import com.example.crud_mvvm_firebase.inventarioapp.feature.product.data.mapper.toDomain
import com.example.crud_mvvm_firebase.inventarioapp.feature.product.data.mapper.toDto
import com.example.crud_mvvm_firebase.inventarioapp.feature.product.data.mapper.toEntity
import com.example.crud_mvvm_firebase.inventarioapp.feature.product.data.remote.FirestoreProductDataSource
import com.example.crud_mvvm_firebase.inventarioapp.feature.product.domain.model.Product
import com.example.crud_mvvm_firebase.inventarioapp.feature.product.domain.model.SyncReport
import com.example.crud_mvvm_firebase.inventarioapp.feature.product.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val dao: ProductDao,
    private val remoteDataSource: FirestoreProductDataSource
) : ProductRepository {

    private val syncMutex = Mutex()

    override fun observeProducts(): Flow<List<Product>> {
        return dao.observeVisible().map { list ->
            list.map { it.toDomain() }
        }
    }

    override suspend fun findById(id: String): Product? {
        return dao.find(id)?.takeIf { !it.isDeleted }?.toDomain()
    }

    override suspend fun save(product: Product) {
        val updatedProduct = product.copy(updatedAt = System.currentTimeMillis())
        dao.upsert(updatedProduct.toEntity(pending = true, isDeleted = false))
    }

    override suspend fun delete(id: String) {
        val existing = dao.find(id)
        if (existing != null) {
            val softDeleted = existing.copy(
                isDeleted = true,
                pending = true,
                updatedAt = System.currentTimeMillis()
            )
            dao.upsert(softDeleted)
        }
    }

    override suspend fun syncNow(): SyncReport {
        return syncMutex.withLock {
            val uid = remoteDataSource.getUid()

            val pendingList = dao.getPending()
            var uploadedCount = 0
            for (local in pendingList) {
                remoteDataSource.push(uid, local.toDto())
                dao.markSynced(local.id, local.updatedAt)
                uploadedCount++
            }

            val cloudItems = remoteDataSource.fetchFromServer(uid)
            for (cloud in cloudItems) {
                val local = dao.find(cloud.id)
                val shouldUpdate = local?.pending != true && (local == null || cloud.updatedAt > local.updatedAt)
                if (shouldUpdate) {
                    dao.upsert(cloud.toEntity(pending = false))
                }
            }

            SyncReport(uploaded = uploadedCount, downloaded = cloudItems.size)
        }
    }
}
