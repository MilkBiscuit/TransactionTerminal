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

    fun validateExpiryTime(input: String): ExpiryTimeValidationResult {
        return when(input.length) {
            0, 1 -> ExpiryTimeValidationResult.SUCCESS
            2 -> {
                when (val monthInt = input.toIntOrNull()) {
                    null -> ExpiryTimeValidationResult.INVALID_TIME
                    0 -> ExpiryTimeValidationResult.JUMP_TO_YEAR_FIELD
                    in 1..12 -> ExpiryTimeValidationResult.SUCCESS
                    else -> ExpiryTimeValidationResult.JUMP_TO_YEAR_FIELD
                }
            }
            3 -> {
                val firstTwoDigitsValidation = validateExpiryTime(input.substring(0, 2))
                when (firstTwoDigitsValidation) {
                    ExpiryTimeValidationResult.JUMP_TO_YEAR_FIELD -> {
                        val monthInt = input.substring(0, 1).toIntOrNull()
                        val yearInt = input.substring(1, 3).toIntOrNull()
                        validateMonthYear(monthInt, yearInt)
                    }
                    ExpiryTimeValidationResult.SUCCESS -> ExpiryTimeValidationResult.SUCCESS
                    else -> ExpiryTimeValidationResult.INVALID_TIME
                }
            }
            4 -> {
                val firstTwoDigitsValidation = validateExpiryTime(input.substring(0, 2))
                when (firstTwoDigitsValidation) {
                    ExpiryTimeValidationResult.SUCCESS -> {
                        val monthInt = input.substring(0, 2).toIntOrNull()
                        val yearInt = input.substring(2, 4).toIntOrNull()
                        validateMonthYear(monthInt, yearInt)
                    }
                    else -> ExpiryTimeValidationResult.INVALID_TIME
                }
            }
            else -> ExpiryTimeValidationResult.INVALID_TIME
        }
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
