package com.example.unittest.etc

object Fibonacci {

    /**
     * n번째 피보나치 수를 리턴하라.
     * fib(0) = 0,
     * fib(1) = 1,
     * fib(n) = fib(n-2) + fib(n-1)
     */
    fun fib(n: Int) : Long{
        return when(n){
            0 -> 0
            1 -> 1
            else -> fib(n-2) + fib(n-1)
        }
    }

    fun checkBraces(string : String) : Boolean{

        if(string.count{ it == '('} == string.count { it == ')'}){
            return true
        }

        return false
    }
}