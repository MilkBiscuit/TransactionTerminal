package com.cheng.transactionterminal.usecase

import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(manifest= Config.NONE)
class StringUtilTest {

    @Test
    fun testMatchesRegex() {
        val REG_EX_HEX_COLOUR = "^#[0-9a-fA-F]{8}\$|#[0-9a-fA-F]{6}\$|#[0-9a-fA-F]{3}\$"
        var input = ""
        var result = StringUtil.matchesRegex(input, REG_EX_HEX_COLOUR)
        Assert.assertFalse(result)
        input = "#Apple"
        result = StringUtil.matchesRegex(input, REG_EX_HEX_COLOUR)
        Assert.assertFalse(result)
        input = "#12345"
        result = StringUtil.matchesRegex(input, REG_EX_HEX_COLOUR)
        Assert.assertFalse(result)
        input = "#UVWXYZ"
        result = StringUtil.matchesRegex(input, REG_EX_HEX_COLOUR)
        Assert.assertFalse(result)
        input = "#0000"
        result = StringUtil.matchesRegex(input, REG_EX_HEX_COLOUR)
        Assert.assertFalse(result)

        input = "#abc"
        result = StringUtil.matchesRegex(input, REG_EX_HEX_COLOUR)
        Assert.assertTrue(result)
        input = "#000"
        result = StringUtil.matchesRegex(input, REG_EX_HEX_COLOUR)
        Assert.assertTrue(result)
        input = "#FFF"
        result = StringUtil.matchesRegex(input, REG_EX_HEX_COLOUR)
        Assert.assertTrue(result)
        input = "#778899"
        result = StringUtil.matchesRegex(input, REG_EX_HEX_COLOUR)
        Assert.assertTrue(result)
        input = "#FFffff"
        result = StringUtil.matchesRegex(input, REG_EX_HEX_COLOUR)
        Assert.assertTrue(result)
        input = "#FFFFFFff"
        result = StringUtil.matchesRegex(input, REG_EX_HEX_COLOUR)
        Assert.assertTrue(result)
    }

    @Test
    fun testEncrypt() {
        var encryptedResult = StringUtil.encrypt("Hello World", "123456")
        Assert.assertEquals("9at1l20/przDf2xNYBrzdQ==", encryptedResult)

        encryptedResult = StringUtil.encrypt("", "123456")
        Assert.assertEquals("kVd2XaZ/Ne5Op34TUjfFOQ==", encryptedResult)
        encryptedResult = StringUtil.encrypt("Hello World", "")
        Assert.assertEquals("uqkPULOpnFuJoBU0UuYq0w==", encryptedResult)
    }

    @Test
    fun testDecrypt() {
        var decryptedResult = StringUtil.decrypt("9at1l20/przDf2xNYBrzdQ==", "123456")
        Assert.assertEquals("Hello World", decryptedResult)

        decryptedResult = StringUtil.decrypt("kVd2XaZ/Ne5Op34TUjfFOQ==", "123456")
        Assert.assertEquals("", decryptedResult)
        decryptedResult = StringUtil.decrypt("uqkPULOpnFuJoBU0UuYq0w==", "")
        Assert.assertEquals("Hello World", decryptedResult)
    }

    @Test
    fun testAdjustLength() {
        var input = "123456"
        var result = StringUtil.adjustLength(input, 4)
        Assert.assertEquals("1234", result)
        input = "1234"
        result = StringUtil.adjustLength(input, 4)
        Assert.assertEquals("1234", result)
        input = "12"
        result = StringUtil.adjustLength(input, 4)
        Assert.assertEquals("1200", result)
        input = ""
        result = StringUtil.adjustLength(input, 4)
        Assert.assertEquals("0000", result)

        input = "12"
        result = StringUtil.adjustLength(input, 0)
        Assert.assertEquals("", result)
        result = StringUtil.adjustLength(input, -1)
        Assert.assertEquals("", result)
    }

}
