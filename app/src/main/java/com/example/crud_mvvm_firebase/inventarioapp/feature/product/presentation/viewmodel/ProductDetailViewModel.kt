package com.example.crud_mvvm_firebase.inventarioapp.feature.product.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.crud_mvvm_firebase.inventarioapp.feature.product.domain.repository.ProductRepository
import com.example.crud_mvvm_firebase.inventarioapp.feature.product.presentation.state.ProductDetailUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductDetailViewModel @Inject constructor(
    private val repository: ProductRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProductDetailUiState())
    val uiState: StateFlow<ProductDetailUiState> = _uiState.asStateFlow()

    private var loadedId: String? = null

    fun loadProduct(id: String) {
        if (loadedId == id) return
        loadedId = id

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            val product = repository.findById(id)
            if (product != null) {
                _uiState.update { it.copy(product = product, isLoading = false) }
            } else {
                _uiState.update { it.copy(isLoading = false, error = "Producto no encontrado") }
            }
        }
    }
}
