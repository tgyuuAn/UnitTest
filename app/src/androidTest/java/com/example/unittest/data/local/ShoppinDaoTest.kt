package com.example.unittest.data.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.example.unittest.other.LiveDataUtil.getOrAwaitValue
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@SmallTest
class ShoppinDaoTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: ShoppingItemDatabase
    private lateinit var dao: ShoppingDao

    @Before
    fun setUp() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            ShoppingItemDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        dao = database.getShoppingDao()
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun `쇼핑_아이템을_추가한다`() = runTest{
        //given
        val shoppingItem = ShoppingItem(
            name = "루비 3000개",
            amount = 1,
            price = 30000F,
            imageUrl = "",
            id = 1
        )

        //when
        dao.insertShoppingItem(shoppingItem)

        //then
        val allShoppingItems = dao.getAllShoppingItems().getOrAwaitValue()
        assertThat(allShoppingItems).contains(shoppingItem)
    }

    @Test
    fun `쇼핑_아이템을_삭제한다`() = runTest{
        val shoppingItem = ShoppingItem(
            name = "루비 3000개",
            amount = 1,
            price = 30000F,
            imageUrl = "",
            id = 1
        )

        //when
        dao.insertShoppingItem(shoppingItem)
        dao.deleteShoppingItem(shoppingItem)

        //then
        val allShoppingItems = dao.getAllShoppingItems().getOrAwaitValue()
        assertThat(allShoppingItems).doesNotContain(shoppingItem)
    }

    @Test
    fun `모든_쇼핑_아이템의_가격을_계산한다`() = runTest{
        //given
        val shoppingItem1 = ShoppingItem(
            name = "루비 3000개",
            amount = 1,
            price = 30000F,
            imageUrl = "",
            id = 1
        )
        val shoppingItem2 = ShoppingItem(
            name = "루비 6000개",
            amount = 1,
            price = 60000F,
            imageUrl = "",
            id = 2
        )
        val shoppingItem3 = ShoppingItem(
            name = "루비 13000개",
            amount = 1,
            price = 109000F,
            imageUrl = "",
            id = 3
        )

        //when
        dao.insertShoppingItem(shoppingItem1)
        dao.insertShoppingItem(shoppingItem2)
        dao.insertShoppingItem(shoppingItem3)

        //then
        val totalPrice = dao.getTotalPrice().getOrAwaitValue()

        assertThat(totalPrice).isEqualTo(199000F)
    }
}