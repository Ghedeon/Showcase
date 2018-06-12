package app.ui.posts

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleRegistry
import app.domain.GetPostsUseCase
import app.domain.Post
import com.spotify.mobius.runners.WorkRunners
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.SpyK
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.observers.TestObserver
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class PostsViewModelTest {

	@MockK
	lateinit var getPosts: GetPostsUseCase
	@InjectMockKs
	lateinit var viewModel: PostsViewModel
	@SpyK
	var eventRunner = WorkRunners.immediate()
	@SpyK
	var effectRunner = WorkRunners.immediate()

	val lifecycle = LifecycleRegistry(mockk())

	@BeforeEach
	internal fun setUp() {
		lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_RESUME)
	}

	@Test
	fun should_load_posts_when_bind() {
		// given
		val posts = listOf<Post>(mockk())
		every { getPosts.posts() } returns Single.just(posts)
		val observer = TestObserver.create<Model>()

		// when
		viewModel.bind({ models ->
			models.subscribe(observer)
			Observable.empty()
		}, lifecycle)

		// then
		observer.assertValue(DisplayPostsModel(posts))
		verify { getPosts.posts() }
	}
}
