package com.fernandocejas.sample

import android.app.Application
import com.fernandocejas.sample.core.di.ApplicationComponent
import com.fernandocejas.sample.core.di.ApplicationModule
import com.fernandocejas.sample.core.di.DaggerApplicationComponent
import com.squareup.leakcanary.LeakCanary
import timber.log.Timber

class AndroidApplication : Application() {

    val appComponent: ApplicationComponent by lazy(mode = LazyThreadSafetyMode.NONE) {
        DaggerApplicationComponent
            .builder()
            .applicationModule(ApplicationModule(this))
            .build()
    }

    override fun onCreate() {
        super.onCreate()
        this.injectMembers()
        this.initializeLeakDetection()
        if(BuildConfig.DEBUG){
            Timber.plant(Timber.DebugTree())
            Timber.d("Debug Message");
            Timber.e("Error Message");
        }
    }

    private fun injectMembers() = appComponent.inject(this)

    private fun initializeLeakDetection() {
        if (BuildConfig.DEBUG) LeakCanary.install(this)
    }
}