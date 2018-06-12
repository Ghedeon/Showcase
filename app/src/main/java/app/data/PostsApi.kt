package app.data

import app.data.Api.POSTS_PATH
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query


interface PostsApi {
	@GET(POSTS_PATH)
	fun getPosts(@Query("number") count: Int): Single<ApiPosts>
}
