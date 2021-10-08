package com.cheng.transactionterminal.presenter

import com.cheng.transactionterminal.contract.ICardEntryPresenter
import com.cheng.transactionterminal.usecase.ExpiryTimeValidationResult

class CardEntryPresenter : ICardEntryPresenter {

    companion object {
        // TODO: hardcode
        const val CURRENT_YEAR = 21
        const val CURRENT_MONTH = 10
    }

    override fun formatCardNumber(input: String): String {
        TODO("Not yet implemented")
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

    private fun validateMonthYear(monthInt: Int?, yearInt: Int?): ExpiryTimeValidationResult {
        if (monthInt == null || yearInt == null || monthInt == 0 || monthInt > 13) {
            return ExpiryTimeValidationResult.INVALID_TIME
        }

        return when (yearInt) {
            in 0 until CURRENT_YEAR -> ExpiryTimeValidationResult.CARD_EXPIRED
            CURRENT_YEAR -> {
                if (monthInt < CURRENT_MONTH) ExpiryTimeValidationResult.CARD_EXPIRED
                else ExpiryTimeValidationResult.SUCCESS
            }
            else -> ExpiryTimeValidationResult.SUCCESS
        }
    }

}
