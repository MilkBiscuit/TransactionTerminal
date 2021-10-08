package com.cheng.transactionterminal.usecase

import org.junit.Assert
import org.junit.Test

class ExpiryTimeUtilTest {

    @Test
    fun testFormatExpiryTime() {
        var input = ""
        var result = ExpiryTimeUtil.formatExpiryTime(input)
        Assert.assertEquals("", result)
        input = "0"
        result = ExpiryTimeUtil.formatExpiryTime(input)
        Assert.assertEquals("0", result)
        input = "1"
        result = ExpiryTimeUtil.formatExpiryTime(input)
        Assert.assertEquals("1", result)
        input = "3"
        result = ExpiryTimeUtil.formatExpiryTime(input)
        Assert.assertEquals("3/", result)
        input = "a"
        result = ExpiryTimeUtil.formatExpiryTime(input)
        Assert.assertEquals("a", result)
        input = "12"
        result = ExpiryTimeUtil.formatExpiryTime(input)
        Assert.assertEquals("12/", result)
        input = "13"
        result = ExpiryTimeUtil.formatExpiryTime(input)
        Assert.assertEquals("1/3", result)
        input = "04"
        result = ExpiryTimeUtil.formatExpiryTime(input)
        Assert.assertEquals("04/", result)
        input = "20"
        result = ExpiryTimeUtil.formatExpiryTime(input)
        Assert.assertEquals("2/0", result)
        input = "a1"
        result = ExpiryTimeUtil.formatExpiryTime(input)
        Assert.assertEquals("a1", result)
        input = "1a"
        result = ExpiryTimeUtil.formatExpiryTime(input)
        Assert.assertEquals("1a", result)
        input = "001"
        result = ExpiryTimeUtil.formatExpiryTime(input)
        Assert.assertEquals("001", result)
        input = "022"
        result = ExpiryTimeUtil.formatExpiryTime(input)
        Assert.assertEquals("02/2", result)
        input = "122"
        result = ExpiryTimeUtil.formatExpiryTime(input)
        Assert.assertEquals("12/2", result)
        input = "132"
        result = ExpiryTimeUtil.formatExpiryTime(input)
        Assert.assertEquals("1/32", result)
        input = "203"
        result = ExpiryTimeUtil.formatExpiryTime(input)
        Assert.assertEquals("2/03", result)
        input = "333"
        result = ExpiryTimeUtil.formatExpiryTime(input)
        Assert.assertEquals("3/33", result)
        input = "a11"
        result = ExpiryTimeUtil.formatExpiryTime(input)
        Assert.assertEquals("a11", result)
        input = "1a1"
        result = ExpiryTimeUtil.formatExpiryTime(input)
        Assert.assertEquals("1a1", result)
        input = "11a"
        result = ExpiryTimeUtil.formatExpiryTime(input)
        Assert.assertEquals("11a", result)
        input = "0102"
        result = ExpiryTimeUtil.formatExpiryTime(input)
        Assert.assertEquals("01/02", result)
        input = "1120"
        result = ExpiryTimeUtil.formatExpiryTime(input)
        Assert.assertEquals("11/20", result)
        input = "1320"
        result = ExpiryTimeUtil.formatExpiryTime(input)
        Assert.assertEquals("1320", result)
        input = "a111"
        result = ExpiryTimeUtil.formatExpiryTime(input)
        Assert.assertEquals("a111", result)
        input = "1a11"
        result = ExpiryTimeUtil.formatExpiryTime(input)
        Assert.assertEquals("1a11", result)
        input = "11a1"
        result = ExpiryTimeUtil.formatExpiryTime(input)
        Assert.assertEquals("11a1", result)
        input = "111a"
        result = ExpiryTimeUtil.formatExpiryTime(input)
        Assert.assertEquals("111a", result)
        input = "11111"
        result = ExpiryTimeUtil.formatExpiryTime(input)
        Assert.assertEquals("11111", result)
    }

    @Test
    fun testValidateMonthYear() {
        var monthInt: Int? = null
        var yearInt: Int? = null
        var result = ExpiryTimeUtil.validateMonthYear(monthInt, yearInt)
        Assert.assertEquals(ExpiryTimeValidationResult.INVALID_TIME, result)
        monthInt = null
        yearInt = 11
        result = ExpiryTimeUtil.validateMonthYear(monthInt, yearInt)
        Assert.assertEquals(ExpiryTimeValidationResult.INVALID_TIME, result)
        monthInt = 11
        yearInt = null
        result = ExpiryTimeUtil.validateMonthYear(monthInt, yearInt)
        Assert.assertEquals(ExpiryTimeValidationResult.INVALID_TIME, result)
        monthInt = 13
        yearInt = null
        result = ExpiryTimeUtil.validateMonthYear(monthInt, yearInt)
        Assert.assertEquals(ExpiryTimeValidationResult.INVALID_TIME, result)
        monthInt = 0
        yearInt = 20
        result = ExpiryTimeUtil.validateMonthYear(monthInt, yearInt)
        Assert.assertEquals(ExpiryTimeValidationResult.INVALID_TIME, result)
        monthInt = 0
        yearInt = 22
        result = ExpiryTimeUtil.validateMonthYear(monthInt, yearInt)
        Assert.assertEquals(ExpiryTimeValidationResult.INVALID_TIME, result)
        monthInt = 13
        yearInt = 20
        result = ExpiryTimeUtil.validateMonthYear(monthInt, yearInt)
        Assert.assertEquals(ExpiryTimeValidationResult.INVALID_TIME, result)
        monthInt = 13
        yearInt = 22
        result = ExpiryTimeUtil.validateMonthYear(monthInt, yearInt)
        Assert.assertEquals(ExpiryTimeValidationResult.INVALID_TIME, result)
        monthInt = 11
        yearInt = -1
        result = ExpiryTimeUtil.validateMonthYear(monthInt, yearInt)
        Assert.assertEquals(ExpiryTimeValidationResult.INVALID_TIME, result)
        monthInt = 11
        yearInt = 100
        result = ExpiryTimeUtil.validateMonthYear(monthInt, yearInt)
        Assert.assertEquals(ExpiryTimeValidationResult.INVALID_TIME, result)
        monthInt = 11
        yearInt = 2000
        result = ExpiryTimeUtil.validateMonthYear(monthInt, yearInt)
        Assert.assertEquals(ExpiryTimeValidationResult.INVALID_TIME, result)

        monthInt = 1
        yearInt = 20
        result = ExpiryTimeUtil.validateMonthYear(monthInt, yearInt)
        Assert.assertEquals(ExpiryTimeValidationResult.CARD_EXPIRED, result)
        monthInt = 11
        yearInt = 20
        result = ExpiryTimeUtil.validateMonthYear(monthInt, yearInt)
        Assert.assertEquals(ExpiryTimeValidationResult.CARD_EXPIRED, result)

        monthInt = 1
        yearInt = 55
        result = ExpiryTimeUtil.validateMonthYear(monthInt, yearInt)
        Assert.assertEquals(ExpiryTimeValidationResult.SUCCESS, result)
        monthInt = 8
        yearInt = 55
        result = ExpiryTimeUtil.validateMonthYear(monthInt, yearInt)
        Assert.assertEquals(ExpiryTimeValidationResult.SUCCESS, result)
        monthInt = 11
        yearInt = 55
        result = ExpiryTimeUtil.validateMonthYear(monthInt, yearInt)
        Assert.assertEquals(ExpiryTimeValidationResult.SUCCESS, result)
    }

    @Test
    fun testAddSlashAfterMonth() {
        var input = ""
        var result = ExpiryTimeUtil.addSlashAfterMonth(input)
        Assert.assertEquals("", result)
        input = "0"
        result = ExpiryTimeUtil.addSlashAfterMonth(input)
        Assert.assertEquals("0", result)
        input = "1"
        result = ExpiryTimeUtil.addSlashAfterMonth(input)
        Assert.assertEquals("1", result)
        input = "2"
        result = ExpiryTimeUtil.addSlashAfterMonth(input)
        Assert.assertEquals("2/", result)
        input = "3"
        result = ExpiryTimeUtil.addSlashAfterMonth(input)
        Assert.assertEquals("3/", result)
        input = "00"
        result = ExpiryTimeUtil.addSlashAfterMonth(input)
        Assert.assertEquals("00", result)
        input = "01"
        result = ExpiryTimeUtil.addSlashAfterMonth(input)
        Assert.assertEquals("01/", result)
        input = "10"
        result = ExpiryTimeUtil.addSlashAfterMonth(input)
        Assert.assertEquals("10/", result)
        input = "13"
        result = ExpiryTimeUtil.addSlashAfterMonth(input)
        Assert.assertEquals("1/3", result)
        input = "20"
        result = ExpiryTimeUtil.addSlashAfterMonth(input)
        Assert.assertEquals("2/0", result)
        input = "000"
        result = ExpiryTimeUtil.addSlashAfterMonth(input)
        Assert.assertEquals("000", result)
        input = "001"
        result = ExpiryTimeUtil.addSlashAfterMonth(input)
        Assert.assertEquals("001", result)
        input = "120"
        result = ExpiryTimeUtil.addSlashAfterMonth(input)
        Assert.assertEquals("120", result)
        input = "0000"
        result = ExpiryTimeUtil.addSlashAfterMonth(input)
        Assert.assertEquals("0000", result)
        input = "1111"
        result = ExpiryTimeUtil.addSlashAfterMonth(input)
        Assert.assertEquals("1111", result)
    }
}
