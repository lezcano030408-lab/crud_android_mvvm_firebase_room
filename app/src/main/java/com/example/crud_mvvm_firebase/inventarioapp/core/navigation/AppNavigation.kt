package com.example.crud_mvvm_firebase.inventarioapp.core.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.example.crud_mvvm_firebase.inventarioapp.feature.product.presentation.screen.ProductDetailScreen
import com.example.crud_mvvm_firebase.inventarioapp.feature.product.presentation.screen.ProductFormScreen
import com.example.crud_mvvm_firebase.inventarioapp.feature.product.presentation.screen.ProductListScreen
import com.example.crud_mvvm_firebase.inventarioapp.feature.product.presentation.viewmodel.ProductDetailViewModel
import com.example.crud_mvvm_firebase.inventarioapp.feature.product.presentation.viewmodel.ProductFormViewModel
import com.example.crud_mvvm_firebase.inventarioapp.feature.product.presentation.viewmodel.ProductListViewModel
import kotlinx.serialization.Serializable

@Serializable
data object ProductListRoute : NavKey

@Serializable
data class ProductDetailRoute(val id: String) : NavKey

@Serializable
data class ProductFormRoute(val id: String? = null) : NavKey

@Composable
fun AppNavigation() {
    val backStack = rememberNavBackStack(ProductListRoute)
    val saveableStateHolderNavEntryDecorator = rememberSaveableStateHolderNavEntryDecorator<NavKey>()
    val viewModelStoreNavEntryDecorator = rememberViewModelStoreNavEntryDecorator<NavKey>()

    NavDisplay(
        backStack = backStack,
        entryDecorators = listOf(
            saveableStateHolderNavEntryDecorator,
            viewModelStoreNavEntryDecorator
        ),
        entryProvider = { key ->
            when (key) {
                is ProductListRoute -> {
                    NavEntry(key) {
                        val viewModel: ProductListViewModel = hiltViewModel()
                        ProductListScreen(
                            onNavigateToDetail = { productId ->
                                backStack.add(ProductDetailRoute(id = productId))
                            },
                            onNavigateToForm = { productId ->
                                backStack.add(ProductFormRoute(id = productId))
                            },
                            viewModel = viewModel
                        )
                    }
                }
                is ProductDetailRoute -> {
                    NavEntry(key) {
                        val viewModel: ProductDetailViewModel = hiltViewModel()
                        ProductDetailScreen(
                            productId = key.id,
                            onNavigateBack = {
                                if (backStack.size > 1) {
                                    backStack.removeAt(backStack.lastIndex)
                                }
                            },
                            onNavigateToEdit = { productId ->
                                backStack.add(ProductFormRoute(id = productId))
                            },
                            viewModel = viewModel
                        )
                    }
                }
                is ProductFormRoute -> {
                    NavEntry(key) {
                        val viewModel: ProductFormViewModel = hiltViewModel()
                        ProductFormScreen(
                            productId = key.id,
                            onNavigateBack = {
                                if (backStack.size > 1) {
                                    backStack.removeAt(backStack.lastIndex)
                                }
                            },
                            viewModel = viewModel
                        )
                    }
                }
                else -> error("Ruta no reconocida: $key")
            }
        }
    )
}
