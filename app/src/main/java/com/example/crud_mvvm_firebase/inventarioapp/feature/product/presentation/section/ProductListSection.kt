package com.example.crud_mvvm_firebase.inventarioapp.feature.product.presentation.section

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.crud_mvvm_firebase.inventarioapp.feature.product.domain.model.Product
import com.example.crud_mvvm_firebase.inventarioapp.feature.product.presentation.composables.ProductCard

@Composable
fun ProductListSection(
    products: List<Product>,
    onDetailClick: (Product) -> Unit,
    onEditClick: (Product) -> Unit,
    onDeleteClick: (Product) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier.fillMaxSize()) {
        if (products.isEmpty()) {
            Text(
                text = "No hay productos disponibles.",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.align(Alignment.Center)
            )
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(top = 8.dp, bottom = 88.dp)
            ) {
                items(
                    items = products,
                    key = { it.id }
                ) { product ->
                    ProductCard(
                        product = product,
                        onDetailClick = onDetailClick,
                        onEditClick = onEditClick,
                        onDeleteClick = onDeleteClick
                    )
                }
            }
        }
    }
}
