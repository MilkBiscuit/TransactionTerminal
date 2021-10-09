package com.cheng.transactionterminal.util

import org.junit.Assert
import org.junit.Test

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

}
