package com.cheng.transactionterminal

import android.app.Application
import com.cheng.transactionterminal.db.AppDatabase
import com.facebook.stetho.Stetho

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        Stetho.initializeWithDefaults(this);
        AppDatabase.getDatabase(this)
    }

}
