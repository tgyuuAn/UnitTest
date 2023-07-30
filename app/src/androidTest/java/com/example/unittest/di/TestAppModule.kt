package com.example.unittest.di

import android.content.Context
import androidx.room.Room
import com.example.unittest.data.local.ShoppingItemDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TestAppModule {

    @Provides
    @Singleton
    @Named("testDB")
    fun provideInMemoryDB(@ApplicationContext context: Context): ShoppingItemDatabase =
        Room.inMemoryDatabaseBuilder(
            context,
            ShoppingItemDatabase::class.java
        )
            .allowMainThreadQueries()
            .build()
}