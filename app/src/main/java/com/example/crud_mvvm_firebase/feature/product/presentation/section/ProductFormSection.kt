package com.example.crud_mvvm_firebase.feature.product.presentation.section

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.crud_mvvm_firebase.feature.product.presentation.composables.ProductFormField

@Composable
fun ProductFormSection(
    name: String,
    onNameChange: (String) -> Unit,
    nameError: String?,
    description: String,
    onDescriptionChange: (String) -> Unit,
    price: String,
    onPriceChange: (String) -> Unit,
    priceError: String?,
    quantity: String,
    onQuantityChange: (String) -> Unit,
    quantityError: String?,
    saving: Boolean,
    error: String?,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        ProductFormField(
            value = name,
            onValueChange = onNameChange,
            label = "Nombre",
            errorText = nameError
        )

        Spacer(modifier = Modifier.height(12.dp))

        ProductFormField(
            value = description,
            onValueChange = onDescriptionChange,
            label = "Descripción",
            singleLine = false
        )

        Spacer(modifier = Modifier.height(12.dp))

        ProductFormField(
            value = price,
            onValueChange = onPriceChange,
            label = "Precio (COP)",
            errorText = priceError,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        Spacer(modifier = Modifier.height(12.dp))

        ProductFormField(
            value = quantity,
            onValueChange = onQuantityChange,
            label = "Cantidad",
            errorText = quantityError,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        if (error != null) {
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = error,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodyMedium
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = onSaveClick,
            enabled = !saving,
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
        ) {
            if (saving) {
                CircularProgressIndicator(
                    color = MaterialTheme.colorScheme.onPrimary,
                    strokeWidth = 2.dp
                )
            } else {
                Text(text = "Guardar Producto")
            }
        }
    }
}
