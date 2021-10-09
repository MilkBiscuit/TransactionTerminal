package com.cheng.transactionterminal.usecase

import com.cheng.transactionterminal.util.StringUtil

object CardNumberUtil {

    private const val CARD_NUMBER_MAX_LENGTH = 19
    private const val CARD_NUMBER_SECTION_LENGTH = 4
    private const val CARD_NUMBER_FORMAT_REGEX = "^\\d{0,4}\$" +
            "|^\\d{4} \\d{0,4}\$" +
            "|^\\d{4} \\d{4} \\d{0,4}\$" +
            "|^\\d{4} \\d{4} \\d{4} \\d{0,4}\$" +
            "|^\\d{4} \\d{4} \\d{4} \\d{4} \\d{0,3}\$"

    fun isFormattedCardNumber(input: String): Boolean {
        return StringUtil.matchesRegex(input, CARD_NUMBER_FORMAT_REGEX)
    }

    fun formatCardNumber(input: String): String {
        val inputLen = input.length
        if (inputLen == 0 || inputLen > CARD_NUMBER_MAX_LENGTH) {
            return input
        }
        if (isFormattedCardNumber(input)) {
            return input
        }

        val withoutSpace = input.filterNot { it.isWhitespace() }
        val headSection = withoutSpace.substring(0, CARD_NUMBER_SECTION_LENGTH)
        val restString = formatCardNumber(withoutSpace.substring(CARD_NUMBER_SECTION_LENGTH))

        return "$headSection $restString"
    }

}
