package com.example.unittest.data

import androidx.lifecycle.LiveData
import com.example.unittest.data.local.ShoppingDao
import com.example.unittest.data.local.ShoppingItem
import com.example.unittest.data.remote.PixabayAPI
import com.example.unittest.data.remote.response.ImageResponse
import com.example.unittest.domain.ShoppingRepository
import com.example.unittest.other.Resource
import com.example.unittest.other.onResponse
import retrofit2.Response
import java.lang.Exception
import javax.inject.Inject

class ShoppingRepositoryImpl @Inject constructor(
    private val shoppingDao: ShoppingDao,
    private val pixabayAPI: PixabayAPI
) : ShoppingRepository {

    override suspend fun insertShoppingItem(shoppingItem: ShoppingItem) {
        return shoppingDao.insertShoppingItem(shoppingItem)
    }

    override suspend fun deleteShoppingItem(shoppingItem: ShoppingItem) {
        return shoppingDao.deleteShoppingItem(shoppingItem)
    }

    override fun getAllShoppingItems(): LiveData<List<ShoppingItem>> {
        return shoppingDao.getAllShoppingItems()
    }

    override fun getTotalPrice(): LiveData<Float> {
        return shoppingDao.getTotalPrice()
    }

    override suspend fun searchForImage(imageQuery: String): Resource<ImageResponse> {
        return try {
            val response = pixabayAPI.searchForImage(imageQuery)
            if (response.isSuccessful) {
                response.body()?.let {
                    return@let Resource.success(it)
                } ?: Resource.error("무슨 에런지 몰라", null)
            } else {
                Resource.error("무슨 에런지 몰라", null)
            }
        } catch (e: Exception) {
            Resource.error("무슨 에런지 몰라", null)
        }
    }

}