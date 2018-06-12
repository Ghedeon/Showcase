@file:Suppress("HasPlatformType")

package app.di

import app.App
import dagger.Component
import dagger.Module
import dagger.Provides
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Singleton

@Singleton
@Component(
	modules = [
		AndroidSupportInjectionModule::class,
		AppModule::class,
		UiModule::class,
		DataModule::class
	]
)
interface AppComponent : AndroidInjector<App> {

	@Component.Builder
	abstract class Builder : AndroidInjector.Builder<App>()
}

@Module
abstract class AppModule {

	@Module
	companion object {

		@IO
		@Provides
		@JvmStatic
		fun ioScheduler() = Schedulers.io()

		@UI
		@Provides
		@JvmStatic
		fun uiScheduler() = AndroidSchedulers.mainThread()
	}
}
