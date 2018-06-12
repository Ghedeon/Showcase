package app

import com.facebook.stetho.Stetho
import timber.log.Timber

class DebugApp : App() {

	override fun onCreate() {
		super.onCreate()
		initTimber()
		initStetho()
	}

	private fun initStetho() {
		Stetho.initialize(
			Stetho.newInitializerBuilder(this)
				.enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
				.enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this)).build()
		)
	}

	private fun initTimber() {
		Timber.plant(Timber.DebugTree())
	}
}
