
package com.homeprod.homeaccounting

import android.app.Activity
import android.support.test.rule.ActivityTestRule


class RestartActivityRule<T : Activity> : ActivityTestRule<T> {
    constructor(activityClass: Class<T>) : super(activityClass, false) {}

    constructor(activityClass: Class<T>, initialTouchMode: Boolean) : super(activityClass, initialTouchMode, true) {}

    constructor(activityClass: Class<T>, initialTouchMode: Boolean, launchActivity: Boolean) : super(
        activityClass,
        initialTouchMode,
        launchActivity
    ) {
    }

    fun finish() {
        finishActivity()
    }

    fun relaunchActivity() {
        finishActivity()

        sleep(1)
        launchActivity()

        sleep(1)
    }

    fun sleep(seconds: Int) {
        if (seconds > 0) {
            try {
                Thread.sleep((seconds * 1000).toLong())
            } catch (ex: Exception) {
            }

        }
    }

    fun launchActivity() {
        launchActivity(activityIntent)
    }


}