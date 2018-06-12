package app.common

import android.support.annotation.LayoutRes
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup


fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = false): View =
	LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)

inline var View.isVisible: Boolean
	get() = visibility == View.VISIBLE
	set(value) {
		visibility = if (value) View.VISIBLE else View.GONE
	}

inline var View.isInvisible: Boolean
	get() = visibility == View.INVISIBLE
	set(value) {
		visibility = if (value) View.INVISIBLE else View.VISIBLE
	}

inline var View.isGone: Boolean
	get() = visibility == View.GONE
	set(value) {
		visibility = if (value) View.GONE else View.VISIBLE
	}
