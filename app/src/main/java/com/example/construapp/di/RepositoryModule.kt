package com.example.construapp.di

import com.example.construapp.data.repository.ProductoRepository
import com.example.construapp.data.repository.ProductoRepositoryImpl
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
    abstract fun bindProductoRepository(
        impl: ProductoRepositoryImpl
    ): ProductoRepository
}
