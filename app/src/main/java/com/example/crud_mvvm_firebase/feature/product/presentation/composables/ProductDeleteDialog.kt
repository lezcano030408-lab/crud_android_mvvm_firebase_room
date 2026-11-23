package com.example.crud_mvvm_firebase.feature.product.presentation.composables

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import com.example.crud_mvvm_firebase.feature.product.domain.model.Product

@Composable
fun ProductDeleteDialog(
    product: Product,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = "Eliminar producto") },
        text = { Text(text = "¿Está seguro de eliminar '${product.name}'?") },
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text(text = "Eliminar")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(text = "Cancelar")
            }
        }
    )
}
