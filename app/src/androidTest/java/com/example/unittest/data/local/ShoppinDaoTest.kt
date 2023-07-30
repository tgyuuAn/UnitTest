package com.example.unittest.data.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.SmallTest
import com.example.unittest.other.LiveDataUtil.getOrAwaitValue
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import javax.inject.Named


@SmallTest
@HiltAndroidTest
class ShoppinDaoTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Inject
    @Named("testDB")
    lateinit var database: ShoppingItemDatabase
    private lateinit var dao: ShoppingDao

    @Before
    fun setUp() {
        hiltRule.inject()
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