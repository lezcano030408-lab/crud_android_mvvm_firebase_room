package com.example.crud_mvvm_firebase.inventarioapp.feature.product.data.mapper

import com.example.crud_mvvm_firebase.inventarioapp.feature.product.data.local.ProductEntity
import com.example.crud_mvvm_firebase.inventarioapp.feature.product.domain.model.Product

fun ProductEntity.toDomain(): Product {
    return Product(
        id = id,
        name = name,
        description = description,
        priceCop = priceCop,
        quantity = quantity,
        updatedAt = updatedAt
    )
}

fun Product.toEntity(pending: Boolean = true, isDeleted: Boolean = false): ProductEntity {
    return ProductEntity(
        id = id,
        name = name,
        description = description,
        priceCop = priceCop,
        quantity = quantity,
        updatedAt = updatedAt,
        pending = pending,
        isDeleted = isDeleted
    )
}
