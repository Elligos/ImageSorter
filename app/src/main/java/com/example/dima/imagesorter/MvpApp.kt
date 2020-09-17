package com.example.dima.imagesorter

import android.app.Activity
import android.app.Application
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject
import com.example.dima.imagesorter.di.component.DaggerAppComponent


//The App could extend DaggerApplication instead of implementing HasActivityInjector. However,
//inheritance should be avoided so that the option to inherit from something else later on is open.
//
//The base framework type DaggerApplication contains a lot more code than what we have, which is
//not necessary unless we need to inject a Service, IntentService, BroadcastReceiver, or
//ContentProvider (especially ContentProvider). In the case that we do need to inject other types
//besides Activity and Fragment or if you know that your Application does not need to extend something
//else, then it may be worth it to just extend DaggerApplication instead of writing more dagger code
//ourselves.

class MvpApp : Application(), HasActivityInjector{
    @Inject
    lateinit internal var activityDispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    override fun activityInjector() = activityDispatchingAndroidInjector

    override fun onCreate() {
        super.onCreate()
        DaggerAppComponent.builder()
                .application(this)
                .build()
                .inject(this)
    }

}