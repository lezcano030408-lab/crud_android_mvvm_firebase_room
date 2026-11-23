package com.example.crud_mvvm_firebase.feature.product.data.remote

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.Source
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirestoreProductDataSource @Inject constructor(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) {
    suspend fun getUid(): String {
        val currentUser = auth.currentUser
        if (currentUser != null) {
            return currentUser.uid
        }
        val authResult = auth.signInAnonymously().await()
        return authResult.user?.uid ?: error("Error al autenticar con Firebase")
    }

    suspend fun push(uid: String, dto: ProductDto) {
        firestore.collection("users")
            .document(uid)
            .collection("products")
            .document(dto.id)
            .set(dto, SetOptions.merge())
            .await()
    }

    suspend fun fetchFromServer(uid: String): List<ProductDto> {
        val snapshot = firestore.collection("users")
            .document(uid)
            .collection("products")
            .get(Source.SERVER)
            .await()

        return snapshot.documents.mapNotNull { doc ->
            doc.toObject(ProductDto::class.java)
        }
    }
}
