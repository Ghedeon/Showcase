package app.common

import android.util.Log


inline fun catchAll(message: () -> String, action: () -> Unit) {
	try {
		action()
	} catch (t: Throwable) {
		val msg = message()
		Log.w("Failed to $msg. ${t.message}", t)
	}
}
