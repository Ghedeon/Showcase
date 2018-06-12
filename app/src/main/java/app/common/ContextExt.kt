package app.common

import android.content.Context
import android.widget.Toast


fun Context.toast(resourceId: Int) = toast(getString(resourceId))
fun Context.toast(message: CharSequence) =
	Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
