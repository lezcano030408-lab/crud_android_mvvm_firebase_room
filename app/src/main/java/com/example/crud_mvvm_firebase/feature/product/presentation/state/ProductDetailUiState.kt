package com.example.crud_mvvm_firebase.feature.product.presentation.state

import com.example.crud_mvvm_firebase.feature.product.domain.model.Product

data class ProductDetailUiState(
    val product: Product? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)
