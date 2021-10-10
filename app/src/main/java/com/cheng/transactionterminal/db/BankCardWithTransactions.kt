package com.cheng.transactionterminal.db

import androidx.room.Embedded
import androidx.room.Relation
import com.cheng.transactionterminal.entity.BankCard
import com.cheng.transactionterminal.entity.TransactionRecord

// Assume BankCard and TransactionRecord has one-to-many relationship
data class BankCardWithTransactions(
    @Embedded
    val bankCard: BankCard,

    @Relation(
        parentColumn = "id",
        entityColumn = "bankCardId"
    )
    val transactionRecords: List<TransactionRecord> = emptyList()
)
