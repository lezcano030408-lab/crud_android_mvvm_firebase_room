package com.example.crud_mvvm_firebase.feature.product.presentation.screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.crud_mvvm_firebase.feature.product.presentation.section.ProductFormSection
import com.example.crud_mvvm_firebase.feature.product.presentation.viewmodel.ProductFormViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductFormScreen(
    productId: String?,
    onNavigateBack: () -> Unit,
    viewModel: ProductFormViewModel
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(productId) {
        viewModel.load(productId)
    }

    LaunchedEffect(uiState.saved) {
        if (uiState.saved) {
            onNavigateBack()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = if (productId == null) "Nuevo Producto" else "Editar Producto")
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        ProductFormSection(
            name = uiState.name,
            onNameChange = { viewModel.updateName(it) },
            nameError = uiState.nameError,
            description = uiState.description,
            onDescriptionChange = { viewModel.updateDescription(it) },
            price = uiState.price,
            onPriceChange = { viewModel.updatePrice(it) },
            priceError = uiState.priceError,
            quantity = uiState.quantity,
            onQuantityChange = { viewModel.updateQuantity(it) },
            quantityError = uiState.quantityError,
            saving = uiState.saving,
            error = uiState.error,
            onSaveClick = { viewModel.save(productId) },
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        )
    }
}
