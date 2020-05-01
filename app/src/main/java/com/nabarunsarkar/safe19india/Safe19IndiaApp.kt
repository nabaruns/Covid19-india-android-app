package com.nabarunsarkar.safe19india

import android.app.Application
import com.nabarunsarkar.safe19india.di.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
class Safe19IndiaApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(applicationContext)
            modules(networkModule, networkModuleDistrict, networkModuleEssential, networkModuleAppList, networkModuleFaq, viewModelModule, viewDistrictModule, viewEssentialModule, viewAppListModule, viewFaqModule)
        }
    }
}