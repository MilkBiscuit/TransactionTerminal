package com.cheng.transactionterminal.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TransactionRecord(
    val amountInCents: Int,
    val bankCardId: Long,
    val moToType: MoToType,
    val noCvvReason: NoCvvReason? = null,
    val isCardStored: Boolean = false,
    @PrimaryKey(autoGenerate = true) val recordId: Long = 0
)
