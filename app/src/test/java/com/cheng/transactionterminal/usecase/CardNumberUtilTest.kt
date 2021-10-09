package com.cheng.transactionterminal.usecase

import org.junit.Assert
import org.junit.Test

class CardNumberUtilTest {

    @Test
    fun testIsFormattedCardNumber() {
        // Given already formatted card number
        var input = "1234"
        var result = CardNumberUtil.isFormattedCardNumber(input)
        Assert.assertTrue(result)
        input = "1234 "
        result = CardNumberUtil.isFormattedCardNumber(input)
        Assert.assertTrue(result)
        input = "1234 5"
        result = CardNumberUtil.isFormattedCardNumber(input)
        Assert.assertTrue(result)
        input = "1234 5678"
        result = CardNumberUtil.isFormattedCardNumber(input)
        Assert.assertTrue(result)
        input = "1234 5678 "
        result = CardNumberUtil.isFormattedCardNumber(input)
        Assert.assertTrue(result)
        input = "1234 5678 9"
        result = CardNumberUtil.isFormattedCardNumber(input)
        Assert.assertTrue(result)
        input = "1234 5678 90"
        result = CardNumberUtil.isFormattedCardNumber(input)
        Assert.assertTrue(result)
        input = "1234 5678 901"
        result = CardNumberUtil.isFormattedCardNumber(input)
        Assert.assertTrue(result)
        input = "1234 5678 9012"
        result = CardNumberUtil.isFormattedCardNumber(input)
        Assert.assertTrue(result)
        input = "1234 5678 9012 3"
        result = CardNumberUtil.isFormattedCardNumber(input)
        Assert.assertTrue(result)
        input = "1234 5678 9012 34"
        result = CardNumberUtil.isFormattedCardNumber(input)
        Assert.assertTrue(result)
        input = "1234 5678 9012 345"
        result = CardNumberUtil.isFormattedCardNumber(input)
        Assert.assertTrue(result)
        input = "1234 5678 9012 3456"
        result = CardNumberUtil.isFormattedCardNumber(input)
        Assert.assertTrue(result)
        input = "1234 5678 9012 3456 "
        result = CardNumberUtil.isFormattedCardNumber(input)
        Assert.assertTrue(result)
        input = "1234 5678 9012 3456 7"
        result = CardNumberUtil.isFormattedCardNumber(input)
        Assert.assertTrue(result)
        input = "1234 5678 9012 3456 78"
        result = CardNumberUtil.isFormattedCardNumber(input)
        Assert.assertTrue(result)
        input = "1234 5678 9012 3456 789"
        result = CardNumberUtil.isFormattedCardNumber(input)
        Assert.assertTrue(result)

        // Given wrongly formatted card number
        input = "1 2"
        result = CardNumberUtil.isFormattedCardNumber(input)
        Assert.assertFalse(result)
        input = "12 3"
        result = CardNumberUtil.isFormattedCardNumber(input)
        Assert.assertFalse(result)
        input = "123 4"
        result = CardNumberUtil.isFormattedCardNumber(input)
        Assert.assertFalse(result)
        input = "12345 "
        result = CardNumberUtil.isFormattedCardNumber(input)
        Assert.assertFalse(result)
        input = "1234 5 "
        result = CardNumberUtil.isFormattedCardNumber(input)
        Assert.assertFalse(result)
        input = "1234 56 "
        result = CardNumberUtil.isFormattedCardNumber(input)
        Assert.assertFalse(result)
        input = "1234 567 "
        result = CardNumberUtil.isFormattedCardNumber(input)
        Assert.assertFalse(result)
        input = "1234 567 8"
        result = CardNumberUtil.isFormattedCardNumber(input)
        Assert.assertFalse(result)
        input = "1234 56789"
        result = CardNumberUtil.isFormattedCardNumber(input)
        Assert.assertFalse(result)
        input = "1234 56789 "
        result = CardNumberUtil.isFormattedCardNumber(input)
        Assert.assertFalse(result)
        input = "1234 5678 9 "
        result = CardNumberUtil.isFormattedCardNumber(input)
        Assert.assertFalse(result)

        // Given card number exceeds max length
        input = "1234 5678 9012 3456 7890"
        result = CardNumberUtil.isFormattedCardNumber(input)
        Assert.assertFalse(result)
        input = "1234 5678 9012 3456 789 "
        result = CardNumberUtil.isFormattedCardNumber(input)
        Assert.assertFalse(result)
    }

    @Test
    fun testFormatCardNumber() {
        var input = ""
        var result = CardNumberUtil.formatCardNumber(input)
        Assert.assertEquals("", result)
        input = "0"
        result = CardNumberUtil.formatCardNumber(input)
        Assert.assertEquals("0", result)
        input = "12"
        result = CardNumberUtil.formatCardNumber(input)
        Assert.assertEquals("12", result)
        input = "123"
        result = CardNumberUtil.formatCardNumber(input)
        Assert.assertEquals("123", result)
        input = "1 2"
        result = CardNumberUtil.formatCardNumber(input)
        Assert.assertEquals("12", result)

        // Given input length is 4
        input = "1234"
        result = CardNumberUtil.formatCardNumber(input)
        Assert.assertEquals("1234", result)
        input = "1 23"
        result = CardNumberUtil.formatCardNumber(input)
        Assert.assertEquals("123", result)
        input = "1  2"
        result = CardNumberUtil.formatCardNumber(input)
        Assert.assertEquals("12", result)
        input = "   2"
        result = CardNumberUtil.formatCardNumber(input)
        Assert.assertEquals("2", result)

        // Given input length is 5
        input = "12345"
        result = CardNumberUtil.formatCardNumber(input)
        Assert.assertEquals("1234 5", result)

        // Given input length is 6
        input = "1234 5"
        result = CardNumberUtil.formatCardNumber(input)
        Assert.assertEquals("1234 5", result)
        input = "123456"
        result = CardNumberUtil.formatCardNumber(input)
        Assert.assertEquals("1234 56", result)

        // Given input length is 7
        input = "1234 56"
        result = CardNumberUtil.formatCardNumber(input)
        Assert.assertEquals("1234 56", result)
        input = "123 456"
        result = CardNumberUtil.formatCardNumber(input)
        Assert.assertEquals("1234 56", result)
        input = "1234567"
        result = CardNumberUtil.formatCardNumber(input)
        Assert.assertEquals("1234 567", result)

        // Given input length is 8
        input = "1234 567"
        result = CardNumberUtil.formatCardNumber(input)
        Assert.assertEquals("1234 567", result)
        input = "123 45 6"
        result = CardNumberUtil.formatCardNumber(input)
        Assert.assertEquals("1234 56", result)
        input = "12345678"
        result = CardNumberUtil.formatCardNumber(input)
        Assert.assertEquals("1234 5678", result)

        // Given input length is 9
        input = "1234 5678"
        result = CardNumberUtil.formatCardNumber(input)
        Assert.assertEquals("1234 5678", result)
        input = "12345 678"
        result = CardNumberUtil.formatCardNumber(input)
        Assert.assertEquals("1234 5678", result)
        input = "123 45 67"
        result = CardNumberUtil.formatCardNumber(input)
        Assert.assertEquals("1234 567", result)
        input = "123456789"
        result = CardNumberUtil.formatCardNumber(input)
        Assert.assertEquals("1234 5678 9", result)

        // Given input length is 10
        input = "1234 5678 "
        result = CardNumberUtil.formatCardNumber(input)
        Assert.assertEquals("1234 5678 ", result)
        input = "1234567890"
        result = CardNumberUtil.formatCardNumber(input)
        Assert.assertEquals("1234 5678 90", result)

        // Given input length is 11
        input = "1234 5678 9"
        result = CardNumberUtil.formatCardNumber(input)
        Assert.assertEquals("1234 5678 9", result)
        input = "12345678901"
        result = CardNumberUtil.formatCardNumber(input)
        Assert.assertEquals("1234 5678 901", result)

        // Given input length is 12
        input = "1234 5678 90"
        result = CardNumberUtil.formatCardNumber(input)
        Assert.assertEquals("1234 5678 90", result)
        input = "123456789012"
        result = CardNumberUtil.formatCardNumber(input)
        Assert.assertEquals("1234 5678 9012", result)

        // Given input length is 13
        input = "1234 5678 901"
        result = CardNumberUtil.formatCardNumber(input)
        Assert.assertEquals("1234 5678 901", result)
        input = "1 23 45678901"
        result = CardNumberUtil.formatCardNumber(input)
        Assert.assertEquals("1234 5678 901", result)
        input = "123 45678901 "
        result = CardNumberUtil.formatCardNumber(input)
        Assert.assertEquals("1234 5678 901", result)
        input = "1 2 3 4 5 6 7"
        result = CardNumberUtil.formatCardNumber(input)
        Assert.assertEquals("1234 567", result)
        input = "1234567890123"
        result = CardNumberUtil.formatCardNumber(input)
        Assert.assertEquals("1234 5678 9012 3", result)

        // Given input length is 14
        input = "1234 5678 9012"
        result = CardNumberUtil.formatCardNumber(input)
        Assert.assertEquals("1234 5678 9012", result)
        input = "12345678901234"
        result = CardNumberUtil.formatCardNumber(input)
        Assert.assertEquals("1234 5678 9012 34", result)

        // Given input length is 15
        input = "1234 5678 9012 "
        result = CardNumberUtil.formatCardNumber(input)
        Assert.assertEquals("1234 5678 9012 ", result)
        input = "123456789012345"
        result = CardNumberUtil.formatCardNumber(input)
        Assert.assertEquals("1234 5678 9012 345", result)

        // Given input length is 16
        input = "1234 5678 9012 3"
        result = CardNumberUtil.formatCardNumber(input)
        Assert.assertEquals("1234 5678 9012 3", result)
        input = "1234567890123456"
        result = CardNumberUtil.formatCardNumber(input)
        Assert.assertEquals("1234 5678 9012 3456", result)

        // Given input length is 17
        input = "1234 5678 9012 34"
        result = CardNumberUtil.formatCardNumber(input)
        Assert.assertEquals("1234 5678 9012 34", result)
        input = "1 2 3 4 5 6 7 8 9"
        result = CardNumberUtil.formatCardNumber(input)
        Assert.assertEquals("1234 5678 9", result)
        input = "1 2 3 45678901234"
        result = CardNumberUtil.formatCardNumber(input)
        Assert.assertEquals("1234 5678 9012 34", result)
        input = "1234 56789012  34"
        result = CardNumberUtil.formatCardNumber(input)
        Assert.assertEquals("1234 5678 9012 34", result)
        input = "1234567890   1234"
        result = CardNumberUtil.formatCardNumber(input)
        Assert.assertEquals("1234 5678 9012 34", result)
        input = "12345678901234567"
        result = CardNumberUtil.formatCardNumber(input)
        Assert.assertEquals("1234 5678 9012 3456 7", result)

        // Given input length is 18
        input = "1234 5678 9012 345"
        result = CardNumberUtil.formatCardNumber(input)
        Assert.assertEquals("1234 5678 9012 345", result)
        input = "123456789012345678"
        result = CardNumberUtil.formatCardNumber(input)
        Assert.assertEquals("1234 5678 9012 3456 78", result)

        // Given input length is 19
        input = "1234 5678 9012 3456"
        result = CardNumberUtil.formatCardNumber(input)
        Assert.assertEquals("1234 5678 9012 3456", result)
        input = "1234567890123456789"
        result = CardNumberUtil.formatCardNumber(input)
        Assert.assertEquals("1234 5678 9012 3456 789", result)
    }

    @Test
    fun testIsMasterCard() {
        // Given not enough digits
        var input = "54561111"
        var result = CardNumberUtil.isMasterCard(input)
        Assert.assertFalse(result)
        input = "545622223333"
        result = CardNumberUtil.isMasterCard(input)
        Assert.assertFalse(result)
        // Given string exceeds limit
        input = "54112222333344445555"
        result = CardNumberUtil.isMasterCard(input)
        Assert.assertFalse(result)
        // Given string with letters
        input = "541122223333444a"
        result = CardNumberUtil.isMasterCard(input)
        Assert.assertFalse(result)

        input = "5456789012345670"
        result = CardNumberUtil.isMasterCard(input)
        Assert.assertTrue(result)
    }

    @Test
    fun testIsVisaCard() {
        // Given not enough digits
        var input = "4444"
        var result = CardNumberUtil.isVisaCard(input)
        Assert.assertFalse(result)
        input = "44445555"
        result = CardNumberUtil.isVisaCard(input)
        Assert.assertFalse(result)
        // Given string exceeds limit
        input = "44445555111122223333"
        result = CardNumberUtil.isVisaCard(input)
        Assert.assertFalse(result)
        // Given string with letters
        input = "444455551111222a"
        result = CardNumberUtil.isVisaCard(input)
        Assert.assertFalse(result)

        input = "4444555511112222"
        result = CardNumberUtil.isVisaCard(input)
        Assert.assertTrue(result)
    }

}
