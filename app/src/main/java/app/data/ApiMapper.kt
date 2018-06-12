package app.data

import app.common.toSpanned
import app.domain.Post


fun ApiPost.toDomain() = Post(
	title = title.capitalize().toSpanned(),
	imageUrl = if (!imageUrl.isEmpty()) imageUrl else null,
	summary = summary.trim().toSpanned(),
	author = author.name,
	avatarUrl = author.avatarUrl,
	url = url
)
