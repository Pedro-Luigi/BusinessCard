package com.meu.businesscard

import android.app.Application
import com.meu.businesscard.data.AppDatabase
import com.meu.businesscard.data.BusinessCardRepository

class App : Application() {
    private val database by lazy { AppDatabase.getDatabase(this) }
    val repository by lazy { BusinessCardRepository(database.businessDao()) }
}