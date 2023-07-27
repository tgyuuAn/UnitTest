package com.example.unittest

import com.example.unittest.etc.Fibonacci
import com.google.common.truth.Truth.assertThat
import org.junit.Test


class FibonacciTest{

    @Test
    fun `첫 번째 피보나치 수는 1이다`(){
        val result = Fibonacci.fib(1)
        assertThat(result).isEqualTo(1)
    }

    @Test
    fun `두 번째 피보나치 수는 1이다`(){
        val result = Fibonacci.fib(2)
        assertThat(result).isEqualTo(1)
    }

    @Test
    fun `세 번째 피보나치 수는 2이다`(){
        val result = Fibonacci.fib(3)
        assertThat(result).isEqualTo(2)
    }

    @Test
    fun `닫는 괄호개수가 여는 괄호개수 보다 많으면 Flase를 반환한다`(){
        val result = Fibonacci.checkBraces("())")
        assertThat(result).isFalse()
    }

    @Test
    fun `여는 괄호개수가 닫는 괄호개수 보다 많으면 Flase를 반환한다`(){
        val result = Fibonacci.checkBraces("(()")
        assertThat(result).isFalse()
    }

    @Test
    fun `닫는 괄호가 여는 괄호보다 먼저 나오면 Flase를 반환한다`(){
        val result = Fibonacci.checkBraces(")(")
        assertThat(result).isFalse()
    }
}