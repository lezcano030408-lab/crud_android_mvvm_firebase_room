package com.example.crud_mvvm_firebase.inventarioapp.feature.product.domain.usecase

import com.example.crud_mvvm_firebase.inventarioapp.feature.product.domain.model.Product
import com.example.crud_mvvm_firebase.inventarioapp.feature.product.domain.repository.ProductRepository
import javax.inject.Inject

class SaveProductUseCase @Inject constructor(
    private val repository: ProductRepository
) {
    suspend operator fun invoke(product: Product) {
        require(product.name.isNotBlank()) { "El nombre no puede estar vacío" }
        require(product.name.length <= 80) { "El nombre no puede superar los 80 caracteres" }
        require(product.description.length <= 500) { "La descripción no puede superar los 500 caracteres" }
        require(product.priceCop > 0) { "El precio debe ser mayor a 0" }
        require(product.quantity >= 0) { "La cantidad no puede ser negativa" }

        repository.save(product)
    }
}
