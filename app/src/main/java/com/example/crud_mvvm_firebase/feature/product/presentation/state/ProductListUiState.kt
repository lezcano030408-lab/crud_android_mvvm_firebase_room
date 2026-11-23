package com.example.crud_mvvm_firebase.feature.product.presentation.state

import com.example.crud_mvvm_firebase.feature.product.domain.model.Product

data class ProductListUiState(
    val products: List<Product> = emptyList(),
    val isSyncing: Boolean = false,
    val message: String? = null,
    val productToDelete: Product? = null
)
