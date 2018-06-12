package app.domain

import android.text.Spanned


data class Post(
	val title: Spanned,
	val imageUrl: String?,
	val summary: Spanned,
	val url: String,
	val author: String,
	val avatarUrl: String
)
