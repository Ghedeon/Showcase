package app.data

import com.squareup.moshi.Json


data class ApiPosts(@field:Json(name = "posts") val posts: List<ApiPost>)

data class ApiPost(
	@field:Json(name = "title") val title: String,
	@field:Json(name = "author") val author: ApiAuthor,
	@field:Json(name = "excerpt") val summary: String,
	@field:Json(name = "featured_image") val imageUrl: String,
	@field:Json(name = "URL") val url: String
)

data class ApiAuthor(
	@field:Json(name = "name") val name: String,
	@field:Json(name = "avatar_URL") val avatarUrl: String
)


