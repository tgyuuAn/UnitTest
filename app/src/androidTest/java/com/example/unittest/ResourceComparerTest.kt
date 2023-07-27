package com.example.unittest

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.unittest.etc.ResourceComparer
import com.google.common.truth.Truth.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test


class ResourceComparerTest {

    private lateinit var resourceComparer: ResourceComparer

    @Before
    fun setUp(){
        resourceComparer = ResourceComparer()
    }

    @After
    fun tearDown(){

    }

    @Test
    fun `리소스_아이디의_이름과_String값이_같으면_True를_리턴한다`() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val result =
            resourceComparer.isEqual(
                context = context,
                resId = R.string.app_name,
                string = "UnitTest"
            )
        assertThat(result).isTrue()
    }

    @Test
    fun `리소스_아이디의_이름과_String값이_다르면_False를_리턴한다`() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val result =
            resourceComparer.isEqual(
                context = context,
                resId = R.string.app_name,
                string = "123"
            )
        assertThat(result).isFalse()
    }
}