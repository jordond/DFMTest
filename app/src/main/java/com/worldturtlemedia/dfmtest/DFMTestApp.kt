package com.worldturtlemedia.dfmtest

import android.util.Log
import com.google.android.play.core.splitcompat.SplitCompatApplication
import com.worldturtlemedia.dfmtest.common.di.FakeDI
import timber.log.Timber

class DFMTestApp : SplitCompatApplication() {

    override fun onCreate() {
        super.onCreate()

        Timber.plant(if (BuildConfig.DEBUG) Timber.DebugTree() else tree)

        FakeDI.init(applicationContext)
    }

    // Apparently the DebugTree won't work in release, so create a basic one.
    private val tree = object: Timber.Tree() {
        override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
            Log.println(priority, tag, t?.let { "$message\n$it" } ?: message)
        }
    }
}