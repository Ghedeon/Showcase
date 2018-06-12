package app.di

import android.annotation.SuppressLint
import app.common.X509TrustManagerAdapter
import com.facebook.stetho.okhttp3.StethoInterceptor
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Singleton
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSocketFactory
import javax.net.ssl.X509TrustManager

@Module(includes = [OkHttpBuilderModule::class])
abstract class OkHttpModule {

	@Module
	companion object {

		/**
		 * Debug version of OkHttpClient for easy debugging
		 *
		 * Request logging is enabled
		 * Stetho interceptor is added
		 * Certificate Pinning is disabled
		 * Hostname verification is disabled
		 */
		@Provides
		@Singleton
		@JvmStatic
		fun provideOkHttpClient(
			builder: OkHttpClient.Builder, sslSocketFactory: SSLSocketFactory, trustManager: X509TrustManager
		): OkHttpClient {
			val loggingInterceptor = HttpLoggingInterceptor()
			loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
			builder.addInterceptor(loggingInterceptor)
			builder.addNetworkInterceptor(StethoInterceptor())
			builder.sslSocketFactory(sslSocketFactory, trustManager)
			builder.hostnameVerifier { _, _ -> true }
			return builder.build()
		}

		@Provides
		@Singleton
		@JvmStatic
		fun provideSslFactory(trustManager: X509TrustManager): SSLSocketFactory {
			val sslContext = SSLContext.getInstance("SSL")
			sslContext.init(null, arrayOf(trustManager), java.security.SecureRandom())
			return sslContext.socketFactory
		}

		@Provides
		@Singleton
		@JvmStatic
		@SuppressLint("TrustAllX509TrustManager")
		fun provideTrustManager(): X509TrustManager = X509TrustManagerAdapter()
	}
}
