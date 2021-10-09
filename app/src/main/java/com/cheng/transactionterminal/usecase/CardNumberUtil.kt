package com.cheng.transactionterminal.usecase

import com.cheng.transactionterminal.util.StringUtil

object CardNumberUtil {

    // Min and Max length of card number after formatting
    const val CARD_NUMBER_MIN_LENGTH = 19
    const val CARD_NUMBER_MAX_LENGTH = 23
    const val CARD_NUMBER_SECTION_LENGTH = 4
    private const val CARD_NUMBER_FORMAT_REGEX = "^\\d{0,4}\$" +
            "|^\\d{4} \\d{0,4}\$" +
            "|^\\d{4} \\d{4} \\d{0,4}\$" +
            "|^\\d{4} \\d{4} \\d{4} \\d{0,4}\$" +
            "|^\\d{4} \\d{4} \\d{4} \\d{4} \\d{0,3}\$"
    private const val MASTER_CARD_REGEX = "^(5[1-5][0-9]{14}|2(22[1-9][0-9]{12}|2[3-9][0-9]{13}|[3-6][0-9]{14}|7[0-1][0-9]{13}|720[0-9]{12}))\$"
    private const val VISA_CARD_REGEX = "^4[0-9]{12}(?:[0-9]{3})?\$"

    fun isFormattedCardNumber(input: String): Boolean {
        return StringUtil.matchesRegex(input, CARD_NUMBER_FORMAT_REGEX)
    }

    fun isMasterCard(input: String): Boolean {
        return StringUtil.matchesRegex(input, MASTER_CARD_REGEX)
    }

    fun isVisaCard(input: String): Boolean {
        return StringUtil.matchesRegex(input, VISA_CARD_REGEX)
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
        if (withoutSpace.length < CARD_NUMBER_SECTION_LENGTH) {
            return withoutSpace
        }

        val headSection = withoutSpace.substring(0, CARD_NUMBER_SECTION_LENGTH)
        val restString = formatCardNumber(withoutSpace.substring(CARD_NUMBER_SECTION_LENGTH))

        return "$headSection $restString"
    }
}
