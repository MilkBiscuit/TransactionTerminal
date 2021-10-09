package com.cheng.transactionterminal.util

import android.util.Base64
import java.io.UnsupportedEncodingException


object StringUtil {

    const val REGEX_HEX_COLOUR = "^#[0-9a-fA-F]{8}\$|#[0-9a-fA-F]{6}\$|#[0-9a-fA-F]{3}\$"

    fun matchesRegex(input: String, regString: String): Boolean {
        val regex = Regex(regString)
        return regex.matches(input)
    }

}
