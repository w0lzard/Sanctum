package com.ryuken.sanctum.androidApp

import android.app.Application
import com.ryuken.sanctum.di.initKoin
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger

class SanctumApp : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidContext(this@SanctumApp)
            androidLogger()
        }
    }
}
