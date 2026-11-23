package com.example.crud_mvvm_firebase.feature.product.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.crud_mvvm_firebase.feature.product.domain.model.Product
import com.example.crud_mvvm_firebase.feature.product.domain.repository.ProductRepository
import com.example.crud_mvvm_firebase.feature.product.domain.usecase.SaveProductUseCase
import com.example.crud_mvvm_firebase.feature.product.presentation.state.ProductFormUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class ProductFormViewModel @Inject constructor(
    private val saveProductUseCase: SaveProductUseCase,
    private val repository: ProductRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProductFormUiState())
    val uiState: StateFlow<ProductFormUiState> = _uiState.asStateFlow()

    private var loadedProductId: String? = null

    fun load(id: String?) {
        if (id.isNullOrEmpty() || loadedProductId == id) return
        loadedProductId = id

        viewModelScope.launch {
            val existing = repository.findById(id)
            if (existing != null) {
                _uiState.update {
                    it.copy(
                        name = existing.name,
                        description = existing.description,
                        price = existing.priceCop.toString(),
                        quantity = existing.quantity.toString()
                    )
                }
            }
        }
    }

    fun updateName(name: String) {
        _uiState.update { it.copy(name = name, nameError = null) }
    }

    fun updateDescription(description: String) {
        _uiState.update { it.copy(description = description) }
    }

    fun updatePrice(price: String) {
        if (price.isEmpty() || price.all { it.isDigit() }) {
            _uiState.update { it.copy(price = price, priceError = null) }
        }
    }

    fun updateQuantity(quantity: String) {
        if (quantity.isEmpty() || quantity.all { it.isDigit() }) {
            _uiState.update { it.copy(quantity = quantity, quantityError = null) }
        }
    }

    fun save(id: String?) {
        val currentState = _uiState.value
        val name = currentState.name.trim()
        val description = currentState.description.trim()
        val priceLong = currentState.price.toLongOrNull()
        val quantityInt = currentState.quantity.toIntOrNull()

        var hasError = false
        if (name.isBlank()) {
            _uiState.update { it.copy(nameError = "El nombre es obligatorio") }
            hasError = true
        }
        if (priceLong == null || priceLong <= 0) {
            _uiState.update { it.copy(priceError = "El precio debe ser un número mayor a 0") }
            hasError = true
        }
        if (quantityInt == null || quantityInt < 0) {
            _uiState.update { it.copy(quantityError = "La cantidad debe ser un número positivo") }
            hasError = true
        }

        if (hasError || priceLong == null || quantityInt == null) return

        viewModelScope.launch {
            _uiState.update { it.copy(saving = true, error = null) }
            val productId = id ?: UUID.randomUUID().toString()
            val productToSave = Product(
                id = productId,
                name = name,
                description = description,
                priceCop = priceLong,
                quantity = quantityInt
            )

            try {
                saveProductUseCase(productToSave)
                _uiState.update { it.copy(saving = false, saved = true) }
            } catch (e: Exception) {
                _uiState.update { it.copy(saving = false, error = e.localizedMessage ?: "Error al guardar el producto") }
            }
        }
    }
}
