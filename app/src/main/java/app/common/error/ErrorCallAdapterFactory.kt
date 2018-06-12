package app.common.error


import io.reactivex.*
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import java.lang.reflect.Type

class ErrorCallAdapterFactory private constructor(private val originalFactory: RxJava2CallAdapterFactory) :
	CallAdapter.Factory() {

	override fun get(returnType: Type, annotations: Array<Annotation>, retrofit: Retrofit): CallAdapter<*, *> =
		RxCallAdapter(originalFactory.get(returnType, annotations, retrofit))

	private class RxCallAdapter<RESPONSE, TYPE>(val originalAdapter: CallAdapter<RESPONSE, TYPE>?) :
		CallAdapter<RESPONSE, TYPE> {

		override fun responseType() = originalAdapter?.responseType()

		@Suppress("UNCHECKED_CAST", "IMPLICIT_CAST_TO_ANY")
		override fun adapt(originalCall: Call<RESPONSE>): TYPE? {
			if (originalAdapter == null) return null

			val call = originalAdapter.adapt(originalCall)

			val errorMapper = { throwable: Throwable -> ServerException.from(throwable) }
			return when (call) {
				is Completable -> call.onErrorResumeNext { t: Throwable -> Completable.error(errorMapper(t)) }
				is Single<*> -> call.onErrorResumeNext { t: Throwable -> Single.error(errorMapper(t)) }
				is Maybe<*> -> call.onErrorResumeNext { t: Throwable -> Maybe.error(errorMapper(t)) }
				is Flowable<*> -> call.onErrorResumeNext { t: Throwable -> Flowable.error(errorMapper(t)) }
				is Observable<*> -> call.onErrorResumeNext { t: Throwable -> Observable.error(errorMapper(t)) }
				else -> throw RuntimeException("Rx type not supported: " + originalAdapter::class)
			} as TYPE
		}
	}

	companion object {
		@JvmStatic
		fun create(factory: RxJava2CallAdapterFactory) = ErrorCallAdapterFactory(factory)
	}
}
