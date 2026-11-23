package com.example.crud_mvvm_firebase.inventarioapp.feature.product.presentation.screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.crud_mvvm_firebase.inventarioapp.feature.product.presentation.composables.ProductDeleteDialog
import com.example.crud_mvvm_firebase.inventarioapp.feature.product.presentation.section.ProductListSection
import com.example.crud_mvvm_firebase.inventarioapp.feature.product.presentation.viewmodel.ProductListViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductListScreen(
    onNavigateToDetail: (String) -> Unit,
    onNavigateToForm: (String?) -> Unit,
    viewModel: ProductListViewModel
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(uiState.message) {
        uiState.message?.let { msg ->
            snackbarHostState.showSnackbar(msg)
            viewModel.clearMessage()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "InventarioApp") },
                actions = {
                    IconButton(
                        onClick = { viewModel.sync() },
                        enabled = !uiState.isSyncing
                    ) {
                        Icon(
                            imageVector = Icons.Default.Refresh,
                            contentDescription = "Sincronizar"
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { onNavigateToForm(null) }) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Agregar"
                )
            }
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { innerPadding ->
        ProductListSection(
            products = uiState.products,
            onDetailClick = { product -> onNavigateToDetail(product.id) },
            onEditClick = { product -> onNavigateToForm(product.id) },
            onDeleteClick = { product -> viewModel.onDeleteClicked(product) },
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        )

        uiState.productToDelete?.let { product ->
            ProductDeleteDialog(
                product = product,
                onConfirm = { viewModel.confirmDelete() },
                onDismiss = { viewModel.dismissDeleteDialog() }
            )
        }
    }
}
