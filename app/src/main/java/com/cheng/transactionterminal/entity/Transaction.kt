package com.cheng.transactionterminal.entity

data class Transaction(
    val amountInCents: Int,
    val isCardStored: Boolean = false,
    val bankCard: BankCard,
    val moToType: MoToType,
    val noCvvReason: NoCvvReason
)
