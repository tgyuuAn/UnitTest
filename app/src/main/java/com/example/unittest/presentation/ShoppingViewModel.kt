package com.example.unittest.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.unittest.data.local.ShoppingItem
import com.example.unittest.data.remote.response.ImageResponse
import com.example.unittest.domain.ShoppingRepository
import com.example.unittest.other.Event
import com.example.unittest.other.Resource
import com.example.unittest.other.Constant
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShoppingViewModel @Inject constructor(
    private val shoppingRepository: ShoppingRepository
) : ViewModel() {

    val shoppingItems = shoppingRepository.getAllShoppingItems()
    val totalPrice = shoppingRepository.getTotalPrice()

    private val _images = MutableLiveData<Event<Resource<ImageResponse>>>()
    val images: LiveData<Event<Resource<ImageResponse>>> = _images

    private val _curImageUrl = MutableLiveData<String>()
    val curImageUrl: LiveData<String> = _curImageUrl

    private val _insertShoppingItemStatus = MutableLiveData<Event<Resource<ShoppingItem>>>()
    val insertShoppingItemStatus: LiveData<Event<Resource<ShoppingItem>>> =
        _insertShoppingItemStatus

    fun setCurrentImageUrl(url: String) {
        _curImageUrl.postValue(url)
    }

    fun deleteShoppingItem(shoppingItem: ShoppingItem) = viewModelScope.launch {
        shoppingRepository.deleteShoppingItem(shoppingItem)
    }

    fun insertShoppingItemIntoDB(shoppingItem: ShoppingItem) = viewModelScope.launch {
        shoppingRepository.insertShoppingItem(shoppingItem)
    }

    fun insertShoppingItem(name: String, amountString: String, priceString: String) {
        if (name.isEmpty() || amountString.isEmpty() || priceString.isEmpty()) {
            _insertShoppingItemStatus.postValue(Event(Resource.error("데이터 양식이 잘못 되었습니다.", null)))
            return
        }

        if (name.length > Constant.MAX_NAME_LENGTH || priceString.length > Constant.MAX_PRICE_LENGTH) {
            _insertShoppingItemStatus.postValue(Event(Resource.error("에러", null)))
            return
        }
        val amount = try {
            amountString.toInt()
        } catch (e: Exception) {
            _insertShoppingItemStatus.postValue(Event(Resource.error("에러", null)))
            return
        }

        if (amount is Int && amount > 100) {
            _insertShoppingItemStatus.postValue(Event(Resource.error("에러", null)))
            return
        }

        val shoppingItem = ShoppingItem(
            name = name,
            amount = amountString.toInt(),
            price = priceString.toFloat(),
            _curImageUrl.value ?: ""
        )
        insertShoppingItemIntoDB(shoppingItem)
        setCurrentImageUrl("")
        _insertShoppingItemStatus.postValue(Event(Resource.success(shoppingItem)))
        return
    }

    fun searchForImage(imageQuery: String) {
        if(imageQuery.isEmpty()){
            return
        }
        _images.value = Event(Resource.loading(null))
        viewModelScope.launch{
            val response = shoppingRepository.searchForImage(imageQuery)
            _images.value = Event(response)
        }
    }

}