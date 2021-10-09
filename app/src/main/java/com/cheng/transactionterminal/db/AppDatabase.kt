package com.cheng.transactionterminal.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.cheng.transactionterminal.entity.BankCard
import com.cheng.transactionterminal.entity.TransactionRecord

@Database(entities = [TransactionRecord::class, BankCard::class], version = 1)
@TypeConverters(Converter::class)
abstract class AppDatabase : RoomDatabase() {

    companion object {
        @Volatile
        private var instace: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return instace ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "terminal_db"
                ).build().also {
                    instace = it
                }

                // return instance
                instance
            }
        }
    }

    abstract fun transactionDao(): TransactionRecordDao
    abstract fun bankCardDao(): BankCardDao

}
