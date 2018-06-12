package app.ui.posts

import android.arch.lifecycle.ViewModelProvider
import android.content.Intent
import android.content.Intent.ACTION_VIEW
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import app.common.*
import app.domain.Post
import com.ghedeon.showcase.R
import com.yqritc.scalablevideoview.ScalableVideoView
import dagger.android.support.DaggerFragment
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers.mainThread
import kotlinx.android.synthetic.main.posts_fragment.*
import javax.inject.Inject


class PostsFragment : DaggerFragment() {

	@Inject
	lateinit var viewModels: ViewModelProvider.Factory
	private val viewModel by bindViewModel<PostsViewModel> { viewModels }
	private val adapter = PostsAdapter()

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
		inflater.inflate(R.layout.posts_fragment, container, false)

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		viewModel.bind(::bindEvents, lifecycle)
	}

	private fun bindEvents(models: Observable<Model>): Observable<Event> {
		val disposable = models
			.observeOn(mainThread())
			.subscribe { model -> render(model) }

		return events().doOnDispose(disposable::dispose)
	}

	private fun events(): Observable<Event> = adapter.itemClickObservable.map { ItemClickedEvent(position = it) }

	private fun render(model: Model) {
		when (model) {
			is InitModel -> init()
			is DisplayPostsModel -> displayPosts(model.posts)
			is BrowsePostModel -> browsePost(model.url)
			is ErrorModel -> context?.toast(model.msg.resolve(context))
		}
	}

	private fun init() {
		showLoadingVideo()
		actionBar.hide()
		posts_recycler.adapter = adapter
	}

	private fun showLoadingVideo() {
		catchAll({ "playVideo" }) {
			(loading_video as ScalableVideoView).apply {
				setRawData(R.raw.loading)
				isLooping = true
				prepareAsync { start() }
			}
		}
	}

	private fun hideLoading() {
		(loading_video as ScalableVideoView).stop()
		loading_video.isVisible = false
	}

	private fun displayPosts(posts: List<Post>) {
		hideLoading()
		actionBar.show()
		adapter.items = posts
	}

	private fun browsePost(url: String) {
		startActivity(Intent(ACTION_VIEW, url.toUri()))
	}

}
