package com.example.unittest.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.unittest.data.local.ShoppingItem
import com.example.unittest.data.remote.response.ImageResponse
import com.example.unittest.domain.ShoppingRepository
import com.example.unittest.other.Resource
import org.junit.After
import org.junit.Before

class FakeShoppingRepository : ShoppingRepository{

    private val shoppingItems = mutableListOf<ShoppingItem>()
    private val observableShoppingItems = MutableLiveData<List<ShoppingItem>>(shoppingItems)
    private val observableTotalPrice = MutableLiveData<Float>()
    private var shouldReturnNetworkError = false

    @Before
    fun setUp(){

    }

    @After
    fun tearDown(){

    }

    private fun refreshLiveData() {
        observableShoppingItems.postValue(shoppingItems)
        observableTotalPrice.postValue(getTotalPriceTest())
    }

    private fun getTotalPriceTest() : Float{
        return shoppingItems.sumByDouble { it.price.toDouble() }.toFloat()
    }

    override suspend fun insertShoppingItem(shoppingItem: ShoppingItem) {
        shoppingItems.add(shoppingItem)
    }

    override suspend fun deleteShoppingItem(shoppingItem: ShoppingItem) {
        shoppingItems.remove(shoppingItem)
        refreshLiveData()
    }

    override fun getAllShoppingItems(): LiveData<List<ShoppingItem>> {
        return observableShoppingItems
    }

    override fun getTotalPrice(): LiveData<Float> {
        return observableTotalPrice
    }

    override suspend fun searchForImage(imageQuery: String): Resource<ImageResponse> {
        return if(shouldReturnNetworkError){
            Resource.error("에러 발생",null)
        } else {
            Resource.success(ImageResponse(listOf(),0,0))
        }
    }
}