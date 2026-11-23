package com.example.crud_mvvm_firebase.inventarioapp.feature.product.presentation.state

data class ProductFormUiState(
    val name: String = "",
    val description: String = "",
    val price: String = "",
    val quantity: String = "0",
    val nameError: String? = null,
    val priceError: String? = null,
    val quantityError: String? = null,
    val saving: Boolean = false,
    val error: String? = null,
    val saved: Boolean = false
)
