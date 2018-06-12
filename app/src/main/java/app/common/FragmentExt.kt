package app.common

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.support.v4.app.Fragment
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity

fun noSetter(): Nothing = throw IllegalAccessException("Property does not have a setter")

inline fun <reified VIEW_MODEL : ViewModel> Fragment.bindViewModel(crossinline factory: () -> ViewModelProvider.Factory) =
	lazy {
		ViewModelProviders.of(this, factory.invoke())[VIEW_MODEL::class.java]
	}

inline var Fragment.actionBar: ActionBar
	get() = checkNotNull((activity as AppCompatActivity).supportActionBar, { "ActionBar is null!" })
	set(value) = noSetter()
