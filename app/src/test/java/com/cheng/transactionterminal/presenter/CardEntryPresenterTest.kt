package com.cheng.transactionterminal.presenter

import com.cheng.transactionterminal.contract.ExpiryTimeValidationResult
import org.junit.Assert
import org.junit.Test

class CardEntryPresenterTest {

    private val presenter = CardEntryPresenter()

    @Test
    fun testFormatExpiryTime() {
        var input = ""
        var result = presenter.formatExpiryTime(input)
        Assert.assertEquals("", result)
        input = "0"
        result = presenter.formatExpiryTime(input)
        Assert.assertEquals("0", result)
        input = "1"
        result = presenter.formatExpiryTime(input)
        Assert.assertEquals("1/", result)
        input = "12"
        result = presenter.formatExpiryTime(input)
        Assert.assertEquals("12/", result)
        input = "13"
        result = presenter.formatExpiryTime(input)
        Assert.assertEquals("1/3", result)
        input = "04"
        result = presenter.formatExpiryTime(input)
        Assert.assertEquals("04/", result)
        input = "20"
        result = presenter.formatExpiryTime(input)
        Assert.assertEquals("2/0", result)
        input = "001"
        result = presenter.formatExpiryTime(input)
        Assert.assertEquals("001", result)
        input = "022"
        result = presenter.formatExpiryTime(input)
        Assert.assertEquals("02/2", result)
        input = "122"
        result = presenter.formatExpiryTime(input)
        Assert.assertEquals("12/2", result)
        input = "132"
        result = presenter.formatExpiryTime(input)
        Assert.assertEquals("1/32", result)
        input = "203"
        result = presenter.formatExpiryTime(input)
        Assert.assertEquals("2/03", result)
        input = "333"
        result = presenter.formatExpiryTime(input)
        Assert.assertEquals("3/33", result)
        input = "0102"
        result = presenter.formatExpiryTime(input)
        Assert.assertEquals("01/02", result)
        input = "1120"
        result = presenter.formatExpiryTime(input)
        Assert.assertEquals("11/20", result)
        input = "1320"
        result = presenter.formatExpiryTime(input)
        Assert.assertEquals("1320", result)
        input = "11111"
        result = presenter.formatExpiryTime(input)
        Assert.assertEquals("11111", result)
    }

    @Test
    fun testIsExpiryTimeValid() {
        var input = ""
        var result = presenter.validateExpiryTime(input)
        Assert.assertEquals(ExpiryTimeValidationResult.SUCCESS, result)
        input = "0"
        result = presenter.validateExpiryTime(input)
        Assert.assertEquals(ExpiryTimeValidationResult.SUCCESS, result)
        input = "1"
        result = presenter.validateExpiryTime(input)
        Assert.assertEquals(ExpiryTimeValidationResult.SUCCESS, result)
        input = "9"
        result = presenter.validateExpiryTime(input)
        Assert.assertEquals(ExpiryTimeValidationResult.SUCCESS, result)
        input = "00"
        result = presenter.validateExpiryTime(input)
        Assert.assertEquals(ExpiryTimeValidationResult.JUMP_TO_YEAR_FIELD, result)
        input = "12"
        result = presenter.validateExpiryTime(input)
        Assert.assertEquals(ExpiryTimeValidationResult.SUCCESS, result)
        input = "13"
        result = presenter.validateExpiryTime(input)
        Assert.assertEquals(ExpiryTimeValidationResult.JUMP_TO_YEAR_FIELD, result)
        input = "04"
        result = presenter.validateExpiryTime(input)
        Assert.assertEquals(ExpiryTimeValidationResult.SUCCESS, result)
        input = "20"
        result = presenter.validateExpiryTime(input)
        Assert.assertEquals(ExpiryTimeValidationResult.JUMP_TO_YEAR_FIELD, result)
        input = "001"
        result = presenter.validateExpiryTime(input)
        Assert.assertEquals(ExpiryTimeValidationResult.INVALID_TIME, result)
        input = "024"
        result = presenter.validateExpiryTime(input)
        Assert.assertEquals(ExpiryTimeValidationResult.SUCCESS, result)
        input = "122"
        result = presenter.validateExpiryTime(input)
        Assert.assertEquals(ExpiryTimeValidationResult.SUCCESS, result)
        input = "132"
        result = presenter.validateExpiryTime(input)
        Assert.assertEquals(ExpiryTimeValidationResult.SUCCESS, result)
        input = "203"
        result = presenter.validateExpiryTime(input)
        Assert.assertEquals(ExpiryTimeValidationResult.CARD_EXPIRED, result)
        // Will FAIL after 1/Apr/2022
        input = "322"
        result = presenter.validateExpiryTime(input)
        Assert.assertEquals(ExpiryTimeValidationResult.SUCCESS, result)
        input = "333"
        result = presenter.validateExpiryTime(input)
        Assert.assertEquals(ExpiryTimeValidationResult.SUCCESS, result)
        input = "921"
        result = presenter.validateExpiryTime(input)
        Assert.assertEquals(ExpiryTimeValidationResult.CARD_EXPIRED, result)
        input = "0001"
        result = presenter.validateExpiryTime(input)
        Assert.assertEquals(ExpiryTimeValidationResult.INVALID_TIME, result)
        input = "0099"
        result = presenter.validateExpiryTime(input)
        Assert.assertEquals(ExpiryTimeValidationResult.INVALID_TIME, result)
        input = "0102"
        result = presenter.validateExpiryTime(input)
        Assert.assertEquals(ExpiryTimeValidationResult.CARD_EXPIRED, result)
        input = "0921"
        result = presenter.validateExpiryTime(input)
        Assert.assertEquals(ExpiryTimeValidationResult.CARD_EXPIRED, result)
        // Will FAIL after 1/Nov/2021
        input = "1021"
        result = presenter.validateExpiryTime(input)
        Assert.assertEquals(ExpiryTimeValidationResult.SUCCESS, result)
        input = "1120"
        result = presenter.validateExpiryTime(input)
        Assert.assertEquals(ExpiryTimeValidationResult.CARD_EXPIRED, result)
        input = "1133"
        result = presenter.validateExpiryTime(input)
        Assert.assertEquals(ExpiryTimeValidationResult.SUCCESS, result)
        input = "1320"
        result = presenter.validateExpiryTime(input)
        Assert.assertEquals(ExpiryTimeValidationResult.INVALID_TIME, result)
        input = "11111"
        result = presenter.validateExpiryTime(input)
        Assert.assertEquals(ExpiryTimeValidationResult.INVALID_TIME, result)
    }
}
