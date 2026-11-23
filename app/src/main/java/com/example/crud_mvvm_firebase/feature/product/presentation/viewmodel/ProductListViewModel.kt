package com.example.crud_mvvm_firebase.feature.product.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.crud_mvvm_firebase.feature.product.domain.model.Product
import com.example.crud_mvvm_firebase.feature.product.domain.repository.ProductRepository
import com.example.crud_mvvm_firebase.feature.product.presentation.state.ProductListUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductListViewModel @Inject constructor(
    private val repository: ProductRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProductListUiState())
    val uiState: StateFlow<ProductListUiState> = _uiState.asStateFlow()

    init {
        observeProducts()
        sync()
    }

    private fun observeProducts() {
        viewModelScope.launch {
            repository.observeProducts()
                .catch { exception ->
                    _uiState.update {
                        it.copy(message = "Error al cargar datos locales: ${exception.localizedMessage}")
                    }
                }
                .collect { productsList ->
                    _uiState.update {
                        it.copy(products = productsList)
                    }
                }
        }
    }

    fun sync() {
        viewModelScope.launch {
            _uiState.update { it.copy(isSyncing = true, message = null) }
            try {
                val report = repository.syncNow()
                _uiState.update {
                    it.copy(
                        isSyncing = false,
                        message = "Sincronizado (Subidos: ${report.uploaded}, Descargados: ${report.downloaded})"
                    )
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isSyncing = false,
                        message = "Sin conexion: datos locales disponibles"
                    )
                }
            }
        }
    }

    fun onDeleteClicked(product: Product) {
        _uiState.update { it.copy(productToDelete = product) }
    }

    fun dismissDeleteDialog() {
        _uiState.update { it.copy(productToDelete = null) }
    }

    fun confirmDelete() {
        val product = _uiState.value.productToDelete ?: return
        viewModelScope.launch {
            _uiState.update { it.copy(productToDelete = null) }
            repository.delete(product.id)
            sync()
        }
    }

    fun clearMessage() {
        _uiState.update { it.copy(message = null) }
    }
}
