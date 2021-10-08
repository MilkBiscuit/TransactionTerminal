package com.cheng.transactionterminal.entity

data class BankCard(
    val cardNumber: String,
    val expiryDate: String = "",
    val cvv: String = "",
)
