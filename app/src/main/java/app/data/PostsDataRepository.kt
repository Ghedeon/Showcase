package app.data

import app.di.IO
import app.domain.Post
import app.domain.PostsRepository
import io.reactivex.Scheduler
import io.reactivex.Single
import javax.inject.Inject


class PostsDataRepository @Inject constructor(
	private val api: PostsApi,
	@IO private val io: Scheduler
) : PostsRepository {

	override fun posts(count: Int): Single<List<Post>> = api.getPosts(count)
		.flattenAsObservable { apiPosts -> apiPosts.posts }
		.map { apiPost -> apiPost.toDomain() }
		.toList()
		.subscribeOn(io)
}
