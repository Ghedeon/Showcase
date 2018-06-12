package app.common

import android.net.Uri
import android.os.Build
import android.text.Html
import android.text.Spanned


fun String.toSpanned(): Spanned {
	if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
		return Html.fromHtml(this, Html.FROM_HTML_MODE_LEGACY)
	} else {
		@Suppress("DEPRECATION")
		return Html.fromHtml(this)
	}
}

fun String.toUri(): Uri = Uri.parse(this)
