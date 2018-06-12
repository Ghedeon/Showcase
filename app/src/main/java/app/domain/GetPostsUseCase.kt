package app.domain

import app.domain.Config.POSTS_COUNT
import io.reactivex.Single
import javax.inject.Inject


class GetPostsUseCase @Inject constructor(private val repository: PostsRepository) {

	fun posts(): Single<List<Post>> = repository.posts(POSTS_COUNT)
}
