@file:Suppress("HasPlatformType")

package app.di

import app.common.error.ErrorCallAdapterFactory
import app.data.Api
import app.data.PostsApi
import app.data.PostsDataRepository
import app.domain.PostsRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module(includes = [OkHttpModule::class])
abstract class DataModule {

	@Binds
	@Singleton
	abstract fun bindPostsRepository(repository: PostsDataRepository): PostsRepository

	@Module
	companion object {

		@Provides
		@Singleton
		@JvmStatic
		fun providePostsApi(retrofit: Retrofit) = retrofit.create(PostsApi::class.java)

		@Provides
		@Singleton
		@JvmStatic
		fun provideRetrofit(okHttpClient: OkHttpClient) = Retrofit.Builder()
			.client(okHttpClient)
			.baseUrl(Api.API_URL)
			.addConverterFactory(MoshiConverterFactory.create())
			.addCallAdapterFactory(ErrorCallAdapterFactory.create(RxJava2CallAdapterFactory.create()))
			.build()
	}
}

@Module
abstract class OkHttpBuilderModule {

	@Module
	companion object {

		@Provides
		@Singleton
		@JvmStatic
		fun provideOkHttpClientBuilder() = OkHttpClient.Builder()
			.followRedirects(true)
			.followSslRedirects(true)
	}
}
