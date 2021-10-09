package com.cheng.transactionterminal.db

import androidx.room.*
import com.cheng.transactionterminal.entity.TransactionRecord

@Dao
interface TransactionRecordDao {
    @Query("SELECT * FROM TransactionRecord")
    fun getAll(): List<TransactionRecord>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg transactionRecords: TransactionRecord): List<Long>

    @Delete
    fun delete(transactionRecord: TransactionRecord)
}
