package com.cheng.transactionterminal.contract

interface ICardEntryView {

}


enum class ExpiryTimeValidationResult {
    SUCCESS,
    // Temporary state, happens ONLY for 2 digits, e.g. when user input "13", it should be treated as "1/3"
    JUMP_TO_YEAR_FIELD,
    // Card has already expired, e.g. "1021" when now is already Nov/2021
    CARD_EXPIRED,
    INVALID_TIME
}

interface ICardEntryPresenter {
    fun formatCardNumber(input: String): String
    fun validateExpiryTime(input: String): ExpiryTimeValidationResult
    fun formatExpiryTime(input: String): String
}
