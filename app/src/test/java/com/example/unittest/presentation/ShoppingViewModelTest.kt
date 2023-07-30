package com.example.unittest.presentation

import android.util.Log
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.unittest.MainCoroutineRule
import com.example.unittest.other.LiveDataUtil.getOrAwaitValue
import com.example.unittest.other.Status
import com.example.unittest.repositories.FakeShoppingRepository
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ShoppingViewModelTest{

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private lateinit var viewModel : ShoppingViewModel

    @Before
    fun setUp() {
        viewModel = ShoppingViewModel(FakeShoppingRepository())
    }

    @Test
    fun `빈 칸으로 쇼핑 아이템을 넣을 경우, Error를 반환한다`(){
        viewModel.insertShoppingItem("name","","3.0")

        val value = viewModel.insertShoppingItemStatus.getOrAwaitValue()

        assertThat(value.getContentIfNotHandled()?.status).isEqualTo(Status.ERROR)
    }

    @Test
    fun `쇼핑 아이템 이름이 매우 길 경우, Error를 반환한다`(){
        viewModel.insertShoppingItem("abcdefghijklmnopqrstyvwxyz","","3.0")

        val value = viewModel.insertShoppingItemStatus.getOrAwaitValue()

        assertThat(value.getContentIfNotHandled()?.status).isEqualTo(Status.ERROR)
    }

    @Test
    fun `쇼핑 아이템 가격이 매우 길 경우, Error를 반환한다`(){
        viewModel.insertShoppingItem("name","","10000000000000")

        val value = viewModel.insertShoppingItemStatus.getOrAwaitValue()

        assertThat(value.getContentIfNotHandled()?.status).isEqualTo(Status.ERROR)
    }

    @Test
    fun `쇼핑 아이템을 매우 많이 담을 경우, Error를 반환한다`(){
        viewModel.insertShoppingItem("name","9999999999999999999","3.0")

        val value = viewModel.insertShoppingItemStatus.getOrAwaitValue()

        assertThat(value.getContentIfNotHandled()?.status).isEqualTo(Status.ERROR)
    }

    @Test
    fun `쇼핑 아이템을 양식에 맞게 담을 경우, Success를 반환한다`() {
        viewModel.insertShoppingItem("name","5","3.0")

        val value = viewModel.insertShoppingItemStatus.getOrAwaitValue()
        assertThat(value.getContentIfNotHandled()?.status).isEqualTo(Status.SUCCESS)
    }
}