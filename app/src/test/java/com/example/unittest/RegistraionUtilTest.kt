package com.example.unittest

import com.example.unittest.etc.RegistraionUtil
import org.junit.Test
import com.google.common.truth.Truth.assertThat

class RegistraionUtilTest {

    @Test
    fun `사용자 이름이 비어있을 경우 false를 반환한다`(){
        val result = RegistraionUtil.validateRegistrationInput(
            userName = "",
            password = "123",
            confirmedPassword = "123"
        )
        assertThat(result).isFalse()
    }

    @Test
    fun `유효한 사용자 이름과 비밀번호를 정확히 반복했을 경우 true를 리턴한다`(){
        val result = RegistraionUtil.validateRegistrationInput(
            userName = "tgyuu",
            password = "123",
            confirmedPassword = "123"
        )
        assertThat(result).isTrue()
    }

    @Test
    fun `사용자 이름이 이미 사용 중일 경우 False를 리턴한다`(){
        val result = RegistraionUtil.validateRegistrationInput(
            userName = "Carl",
            password = "123",
            confirmedPassword = "123"
        )
        assertThat(result).isFalse()
    }

    @Test
    fun `실제 비밀번호와 비밀번호 체크용 반복 비밀번호가 같지 않을경우 False를 리턴한다`(){
        val result = RegistraionUtil.validateRegistrationInput(
            userName = "tgyuu",
            password = "123",
            confirmedPassword = "12"
        )
        assertThat(result).isFalse()
    }

    @Test
    fun `비밀번호에 최소 숫자가 2개이상 들어가지 않으면 False를 리턴한다`(){
        val result = RegistraionUtil.validateRegistrationInput(
            userName = "tgyuu",
            password = "abcde1",
            confirmedPassword = "abcde1"
        )
        assertThat(result).isFalse()
    }

    @Test
    fun `비밀번호가 비어있으면 False를 리턴한다`(){
        val result = RegistraionUtil.validateRegistrationInput(
            userName = "tgyuu",
            password = "",
            confirmedPassword = ""
        )
        assertThat(result).isFalse()
    }
}