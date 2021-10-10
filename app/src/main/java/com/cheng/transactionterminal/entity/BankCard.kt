package com.cheng.transactionterminal.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

// TODO: card number is saved as clear text
@Entity
data class BankCard(
    val cardNumber: String,
    val expiryDate: String = "",
    val cvv: String = "",
    @PrimaryKey(autoGenerate = true) val id: Long = 0
)
