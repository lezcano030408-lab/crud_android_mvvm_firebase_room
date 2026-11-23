package com.example.crud_mvvm_firebase.feature.product.data.mapper

import com.example.crud_mvvm_firebase.feature.product.data.local.ProductEntity
import com.example.crud_mvvm_firebase.feature.product.data.remote.ProductDto

fun ProductEntity.toDto(): ProductDto {
    return ProductDto(
        id = id,
        name = name,
        description = description,
        priceCop = priceCop,
        quantity = quantity,
        updatedAt = updatedAt,
        isDeleted = isDeleted
    )
}

fun ProductDto.toEntity(pending: Boolean = false): ProductEntity {
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
