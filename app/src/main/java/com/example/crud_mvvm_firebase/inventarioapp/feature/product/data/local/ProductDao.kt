package com.example.crud_mvvm_firebase.inventarioapp.feature.product.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {
    @Query("SELECT * FROM products WHERE isDeleted = 0 ORDER BY updatedAt DESC")
    fun observeVisible(): Flow<List<ProductEntity>>

    @Query("SELECT * FROM products WHERE id = :id LIMIT 1")
    suspend fun find(id: String): ProductEntity?

    @Query("SELECT * FROM products WHERE pending = 1")
    suspend fun getPending(): List<ProductEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(entity: ProductEntity)

    @Query("UPDATE products SET pending = 0 WHERE id = :id AND updatedAt = :version")
    suspend fun markSynced(id: String, version: Long)
}
