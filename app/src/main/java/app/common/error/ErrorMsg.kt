package app.common.error

import android.content.Context
import android.support.annotation.StringRes
import com.ghedeon.showcase.R

data class ErrorMsg(private val msg: String? = null, @StringRes private val msgRes: Int = R.string.generic_error) {
	fun resolve(context: Context?): String = msg ?: context?.getString(msgRes) ?: ""
}

