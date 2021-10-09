package com.cheng.transactionterminal.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "transaction_record")
data class Transaction(
    @ColumnInfo(name = "amount_in_cents") val amountInCents: Int,
    @ColumnInfo(name = "bank_card_number") val bankCard: String,
//    val moToType: MoToType,
//    val noCvvReason: NoCvvReason,
    @ColumnInfo(name = "is_card_stored") val isCardStored: Boolean = false,
    @PrimaryKey(autoGenerate = true) val uid: Int = 0,
)
