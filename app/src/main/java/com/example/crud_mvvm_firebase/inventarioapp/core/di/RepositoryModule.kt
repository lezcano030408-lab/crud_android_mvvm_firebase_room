package com.example.crud_mvvm_firebase.inventarioapp.core.di

import com.example.crud_mvvm_firebase.inventarioapp.feature.product.data.repository.ProductRepositoryImpl
import com.example.crud_mvvm_firebase.inventarioapp.feature.product.domain.repository.ProductRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindProductRepository(
        impl: ProductRepositoryImpl
    ): ProductRepository
}
