package com.cheng.transactionterminal

import android.app.Application
import com.cheng.transactionterminal.db.AppDatabase
import com.cheng.transactionterminal.db.AppRepository
import com.facebook.stetho.Stetho

class MainApplication : Application() {

    companion object {
        var appRepository: AppRepository? = null
    }

    private val database by lazy { AppDatabase.getDatabase(this) }

    override fun onCreate() {
        super.onCreate()

        AppRepository(database.bankCardDao(), database.transactionDao()).also {
            appRepository = it
        }
        Stetho.initializeWithDefaults(this);
    }

}
