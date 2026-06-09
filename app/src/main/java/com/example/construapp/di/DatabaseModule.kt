package com.example.construapp.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.construapp.data.local.ConstruAppDatabase
import com.example.construapp.data.local.ProductoDao
import com.example.construapp.data.local.ProductoSeed
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import javax.inject.Provider
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppScope(): CoroutineScope =
        CoroutineScope(SupervisorJob() + Dispatchers.IO)

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context,
        daoProvider: Provider<ProductoDao>,
        scope: CoroutineScope
    ): ConstruAppDatabase = Room.databaseBuilder(
        context,
        ConstruAppDatabase::class.java,
        ConstruAppDatabase.DATABASE_NAME
    )
        .addCallback(object : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                // Se ejecuta una sola vez, al crear la BD: precarga el inventario inicial.
                scope.launch {
                    daoProvider.get().insertarTodos(ProductoSeed.PRODUCTOS)
                }
            }
        })
        .build()

    @Provides
    fun provideProductoDao(db: ConstruAppDatabase): ProductoDao = db.productoDao()
}
