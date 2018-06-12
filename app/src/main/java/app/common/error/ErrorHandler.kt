package app.common.error

import com.ghedeon.showcase.R
import io.reactivex.functions.Function
import timber.log.Timber

class ErrorHandler<T>(private val block: (msg: ErrorMsg) -> T) : Function<Throwable, T> {
	override fun apply(throwable: Throwable): T {
		Timber.e(throwable)
		val ex = throwable as? ServerException
		val msg = when (ex) {
			is ServerException.HttpError -> ErrorMsg(ex.error)
			is ServerException.Network -> ErrorMsg(msgRes = R.string.no_connection)
			is ServerException.Unknown, null -> ErrorMsg(msgRes = R.string.generic_error)
		}

		return block.invoke(msg)
	}
}
