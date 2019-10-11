package com.worldturtlemedia.dfmtest

import com.google.android.play.core.splitcompat.SplitCompatApplication
import timber.log.Timber

class DFMTestApp : SplitCompatApplication() {

    override fun onCreate() {
        super.onCreate()

        // Ideally you wouldn't plant this in a release build
        Timber.plant(Timber.DebugTree())
    }
}