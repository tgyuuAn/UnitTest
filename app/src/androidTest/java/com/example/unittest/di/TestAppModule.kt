package com.example.unittest.di

import android.content.Context
import androidx.room.Room
import com.example.unittest.data.local.ShoppingItemDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Named
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [AppModule::class]
)
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