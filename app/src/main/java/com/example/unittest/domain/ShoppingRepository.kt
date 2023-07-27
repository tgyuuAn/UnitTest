package com.example.unittest.domain

import androidx.lifecycle.LiveData
import com.example.unittest.data.local.ShoppingItem
import com.example.unittest.data.remote.response.ImageResponse
import com.example.unittest.other.Resource
import retrofit2.Response

interface ShoppingRepository {

    suspend fun insertShoppingItem(shoppingItem : ShoppingItem)

    suspend fun deleteShoppingItem(shoppingItem: ShoppingItem)

    fun getAllShoppingItems() : LiveData<List<ShoppingItem>>

    fun getTotalPrice() : LiveData<Float>

    suspend fun searchForImage(imageQuery : String) : Resource<ImageResponse>
}