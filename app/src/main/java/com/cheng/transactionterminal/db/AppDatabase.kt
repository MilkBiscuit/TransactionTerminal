package com.cheng.transactionterminal.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.cheng.transactionterminal.entity.Transaction

@Database(entities = arrayOf(Transaction::class), version = 1)
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

    abstract fun transactionDao(): TransactionDao

}
