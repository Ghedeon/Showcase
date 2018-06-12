package app.ui.posts

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.ViewModel
import app.common.error.ErrorHandler
import app.common.mobius.MobiusLogger
import app.common.mobius.bind
import app.di.ForEffect
import app.di.ForEvent
import app.domain.GetPostsUseCase
import app.domain.Post
import com.spotify.mobius.First
import com.spotify.mobius.Mobius
import com.spotify.mobius.MobiusLoop
import com.spotify.mobius.Next
import com.spotify.mobius.runners.WorkRunner
import com.spotify.mobius.rx2.RxConnectables
import com.spotify.mobius.rx2.RxMobius
import io.reactivex.Observable
import io.reactivex.ObservableSource
import javax.inject.Inject


class PostsViewModel @Inject constructor(
	private val getPosts: GetPostsUseCase,
	@ForEvent private val eventRunner: WorkRunner,
	@ForEffect private val effectRunner: WorkRunner
) : ViewModel() {

	private val loop: MobiusLoop.Factory<Model, Event, Effect> = RxMobius.loop(::update, ::effectHandler)
		.init { First.first(InitModel, setOf(InitEffect, LoadPostsEffect)) }
		.eventRunner { eventRunner }
		.effectRunner { effectRunner }
		.logger(MobiusLogger())

	private var controller: MobiusLoop.Controller<Model, Event> = Mobius.controller(loop, InitModel)

	private var posts: List<Post> = emptyList()

	fun bind(modelToEvent: (Observable<Model>) -> Observable<Event>, lifecycle: Lifecycle) {
		controller.connect(RxConnectables.fromTransformer(modelToEvent))
		controller.bind(lifecycle)
	}

	private fun update(model: Model, event: Event): Next<Model, Effect> = when (event) {
		is InitEvent -> Next.next(InitModel)
		is PostsLoadedEvent.Success -> Next.next(DisplayPostsModel(event.posts))
		is PostsLoadedEvent.Failure -> Next.next(ErrorModel(event.msg))
		is ItemClickedEvent -> Next.next(BrowsePostModel(posts[event.position].url))
	}

	private fun effectHandler(effects: Observable<Effect>): Observable<Event> = effects.flatMap { effect ->
		when (effect) {
			is InitEffect -> Observable.just(InitEvent)
			is LoadPostsEffect -> loadPosts()
		}
	}

	private fun loadPosts(): ObservableSource<PostsLoadedEvent> = getPosts.posts()
		.flatMapObservable { posts ->
			this.posts = posts
			Observable.just(PostsLoadedEvent.Success(posts))
		}
		.cast(PostsLoadedEvent::class.java)
		.onErrorReturn(ErrorHandler { msg ->
			PostsLoadedEvent.Failure(msg)
		})
}
