package com.cheng.transactionterminal.util

object StringUtil {

    fun matchesRegex(input: String, regString: String): Boolean {
        val regex = Regex(regString)
        return regex.matches(input)
    }

}
