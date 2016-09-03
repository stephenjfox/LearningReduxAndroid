package com.stephenjfox.learningbansa

/**
 * Harness for the core of the app, not be to confused with the
 * entry point [MainActivity]
 * Created by Stephen on 9/2/2016.
 */
class MainApp : android.app.Application() {
  override fun onCreate() {
    super.onCreate()

    mainStore.dispatch(StateActions.INIT)
  }
}