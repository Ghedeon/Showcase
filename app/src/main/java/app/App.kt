package app

import android.annotation.SuppressLint
import app.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication


@SuppressLint("Registered")
open class App : DaggerApplication() {

	override fun applicationInjector(): AndroidInjector<App> =
		DaggerAppComponent.builder().create(this)
}
