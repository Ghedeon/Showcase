package app.data

import app.TestResources
import app.common.toSpanned
import io.mockk.*
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.SpyK
import io.mockk.junit5.MockKExtension
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers.trampoline
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class PostsDataRepositoryTest {

	@MockK
	lateinit var api: PostsApi
	@SpyK
	var scheduler = trampoline()

	@InjectMockKs
	lateinit var postsRepository: PostsDataRepository

	@BeforeEach
	fun setUp() {
		mockSpanned()
	}

	@Test
	fun should_call_api_and_map_to_domain_when_call_posts() {
		// when
		val apiPost = API_POSTS.posts.first()
		every { api.getPosts(any()) } returns Single.just(API_POSTS)

		// when
		val test = postsRepository.posts(1).test()

		// then
		test.assertValue { posts -> posts.first() == apiPost.toDomain() }
		verify { api.getPosts(any()) }
	}

	private fun mockSpanned() {
		mockkStatic("app.common.StringExtKt")
		every { capture(slot<String>()).toSpanned() } returns mockk()
	}
}

private val API_POSTS = TestResources.fromJson<ApiPosts>("/get_posts_response_1.json")
