package com.cheng.transactionterminal.db

import androidx.room.*
import com.cheng.transactionterminal.entity.BankCard

@Dao
interface BankCardDao {
    @Query("SELECT * FROM BankCard")
    fun getAll(): List<BankCard>

    @Query("SELECT * FROM BankCard WHERE cardNumber LIKE :bankCardNum LIMIT 1")
    fun findByBankCardNum(bankCardNum: String): BankCard

    @Transaction
    @Query("SELECT * FROM BankCard")
    fun getBankCardAndTransactions(): List<BankCardAndTransaction>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg bankCard: BankCard): List<Long>

    @Delete
    fun delete(bankCard: BankCard)
}
