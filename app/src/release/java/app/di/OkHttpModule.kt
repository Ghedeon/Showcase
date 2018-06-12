package app.di

import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Module(includes = [OkHttpBuilderModule::class])
abstract class OkHttpModule {

	@Module
	companion object {

		@Provides
		@Singleton
		@JvmStatic
		fun provideOkHttpClient(builder: OkHttpClient.Builder): OkHttpClient =
			builder.build()
	}
}
