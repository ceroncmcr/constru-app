package com.example.construapp.di

import android.content.Context
import androidx.room.Room
import com.example.construapp.data.local.ConstruAppDatabase
import com.example.construapp.data.local.ProductoDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): ConstruAppDatabase = Room.databaseBuilder(
        context,
        ConstruAppDatabase::class.java,
        ConstruAppDatabase.DATABASE_NAME
    ).build()

    @Provides
    fun provideProductoDao(db: ConstruAppDatabase): ProductoDao = db.productoDao()
}
