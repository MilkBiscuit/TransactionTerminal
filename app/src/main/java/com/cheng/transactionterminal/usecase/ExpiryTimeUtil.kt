package com.cheng.transactionterminal.usecase

import com.cheng.transactionterminal.presenter.CardEntryPresenter

object ExpiryTimeUtil {
    fun formatExpiryTime(input: String): String {
        input.toIntOrNull()
            ?: return input

        val inputLength = input.length
        return when {
            inputLength == 0 -> ""
            inputLength <= 2 -> {
                addSlashAfterMonth(input)
            }
            inputLength <= 3 -> {
                val monthString = input.substring(0, 2)
                val monthAddSlash = addSlashAfterMonth(monthString)
                "$monthAddSlash${input.substring(2)}"
            }
            inputLength == 4 -> {
                val monthString = input.substring(0, 2)
                val monthAddSlash = addSlashAfterMonth(monthString)
                val slashIndex = monthAddSlash.indexOf('/')
                if (slashIndex == 1) {
                    // e.g. "1311"
                    input
                } else {
                    "$monthAddSlash${input.substring(2)}"
                }
            }
            else -> input
        }
    }

    /**
     * Validate input string which may contain '/'
     */
    fun validateExpiryTime(input: String): ExpiryTimeValidationResult {
        val inputLen = input.length
        if (inputLen < 4) {
            return ExpiryTimeValidationResult.SUCCESS
        }
        val slashIndex = input.indexOf('/')
        if (slashIndex == -1) {
            return ExpiryTimeValidationResult.INVALID_TIME
        }

        val monthInt = input.substring(0, slashIndex).toIntOrNull()
        val yearInt = input.substring(slashIndex + 1).toIntOrNull()

        return validateMonthYear(monthInt, yearInt)
    }

    internal fun validateMonthYear(monthInt: Int?, yearInt: Int?): ExpiryTimeValidationResult {
        if (monthInt == null || yearInt == null || monthInt == 0 || monthInt > 12 || yearInt < 0 || yearInt > 99) {
            return ExpiryTimeValidationResult.INVALID_TIME
        }

        return when (yearInt) {
            in 0 until CardEntryPresenter.CURRENT_YEAR -> ExpiryTimeValidationResult.CARD_EXPIRED
            CardEntryPresenter.CURRENT_YEAR -> {
                if (monthInt < CardEntryPresenter.CURRENT_MONTH) ExpiryTimeValidationResult.CARD_EXPIRED
                else ExpiryTimeValidationResult.SUCCESS
            }
            else -> ExpiryTimeValidationResult.SUCCESS
        }
    }

    /**
     * Add '/' after the 'month' digit.
     * If input string length is more than 2, or current input is an invalid month value, return the original string.
     */
    internal fun addSlashAfterMonth(input: String): String {
        val stringLen = input.length
        if (stringLen > 2) {
            return input
        }

        return when (input.toIntOrNull()) {
            null, 0 -> input
            1 -> if (stringLen == 1) input else "$input/"
            in 2..12 -> "$input/"
            else -> {
                val monthString = input.substring(0, 1)
                "$monthString/${input.substring(1)}"
            }
        }
    }
}
