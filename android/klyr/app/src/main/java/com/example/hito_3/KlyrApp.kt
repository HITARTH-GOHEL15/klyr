package com.example.hito_3

import android.app.Application
import com.example.hito_3.di.initkoin

class KlyrApp : Application() {
    override fun onCreate() {
        super.onCreate()
        initkoin()
    }
}