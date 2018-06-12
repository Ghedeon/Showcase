package app.domain

import io.reactivex.Single


interface PostsRepository {
	fun posts(count: Int): Single<List<Post>>
}
