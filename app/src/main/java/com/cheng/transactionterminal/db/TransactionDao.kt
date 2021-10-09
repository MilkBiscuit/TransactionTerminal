package com.cheng.transactionterminal.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.cheng.transactionterminal.entity.Transaction

@Dao
interface TransactionDao {
    @Query("SELECT * FROM transaction_record")
    fun getAll(): List<Transaction>

//    @Query("SELECT * FROM transaction_record WHERE bank_card_number LIKE :bankCardNum LIMIT 1")
//    fun findByBankCardNum(bankCardNum: String): Transaction

    @Insert
    fun insert(vararg transactions: Transaction)

    @Delete
    fun delete(transaction: Transaction)
}
