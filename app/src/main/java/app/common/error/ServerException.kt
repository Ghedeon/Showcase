package app.common.error

import retrofit2.HttpException
import java.io.IOException

sealed class ServerException : RuntimeException() {
	data class HttpError(val error: String) : ServerException()
	object Network : ServerException()
	object Unknown : ServerException()

	companion object {
		fun from(throwable: Throwable) = when (throwable) {
			is HttpException -> {
				val errorBody = throwable.response().errorBody()
				HttpError(error = errorBody?.string() ?: throwable.message())
			}
			is IOException -> Network
			else -> Unknown
		}
	}
}
