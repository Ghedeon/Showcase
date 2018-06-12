package app.domain

import app.domain.Config.POSTS_COUNT
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.Single
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class GetPostsUseCaseTest {

	@MockK
	lateinit var postsRepository: PostsRepository
	@InjectMockKs
	lateinit var getPosts: GetPostsUseCase

	@Test
	fun should_call_repository_when_call_posts() {
		// given
		val post = mockk<Post>()
		every { postsRepository.posts(POSTS_COUNT) } returns Single.just(listOf(post))

		// when
		val test = getPosts.posts().test()

		// then
		test.assertValue(listOf(post))
		test.assertComplete()
		verify { postsRepository.posts(POSTS_COUNT) }
	}
}

