package com.example.unittest.di

import android.content.Context
import androidx.room.Room
import com.example.unittest.data.ShoppingRepositoryImpl
import com.example.unittest.data.local.ShoppingDao
import com.example.unittest.data.local.ShoppingItemDatabase
import com.example.unittest.data.remote.PixabayAPI
import com.example.unittest.domain.ShoppingRepository
import com.example.unittest.other.Constant.DATABASE_NAME
import com.example.unittest.other.Constant.PIXABAY_BASE_URL
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideShoppingItemDatabase(
        @ApplicationContext context: Context
    ) : ShoppingItemDatabase = Room.databaseBuilder(context, ShoppingItemDatabase::class.java, DATABASE_NAME).build()

    @Provides
    @Singleton
    fun provideShoppingDao(
        database: ShoppingItemDatabase
    ) = database.getShoppingDao()

    @Provides
    @Singleton
    fun providePixabayApi(): PixabayAPI = Retrofit.Builder()
        .baseUrl(PIXABAY_BASE_URL)
        .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
        .build()
        .create(PixabayAPI::class.java)

    @Provides
    @Singleton
    fun provideShoppingRepository(
        shoppingDao: ShoppingDao,
        pixabayAPI: PixabayAPI
    ): ShoppingRepository = ShoppingRepositoryImpl(shoppingDao, pixabayAPI)
}