package com.spoonart.firstlistviewapp

import android.app.Application
import com.spoonart.firstlistviewapp.database.AnimalFakeDatabase

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        AnimalFakeDatabase.instance
    }
}