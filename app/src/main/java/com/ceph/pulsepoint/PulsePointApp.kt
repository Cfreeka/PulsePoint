package com.ceph.pulsepoint

import android.app.Application
import com.ceph.pulsepoint.di.databaseModule
import com.ceph.pulsepoint.di.networkModule
import com.ceph.pulsepoint.di.repositoryModule
import com.ceph.pulsepoint.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class PulsePointApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@PulsePointApp)
            modules(viewModelModule, repositoryModule, networkModule, databaseModule)
        }
    }
}