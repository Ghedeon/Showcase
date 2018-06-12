package app.ui.posts

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import app.common.inflate
import app.common.isVisible
import app.domain.Post
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.ghedeon.showcase.R
import com.jakewharton.rxbinding2.view.RxView
import com.jakewharton.rxrelay2.PublishRelay
import io.reactivex.Observable
import kotlinx.android.synthetic.main.post_list_item.view.*


class PostsAdapter : RecyclerView.Adapter<PostsAdapter.ViewHolder>() {

	var items: List<Post> = emptyList()
		set(value) {
			field = value
			notifyDataSetChanged()
		}

	val itemClickObservable: Observable<Int>
		get() = itemClickRelay

	private val itemClickRelay = PublishRelay.create<Int>()

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
		ViewHolder(parent.inflate(R.layout.post_list_item))

	override fun getItemCount(): Int = items.size

	override fun onBindViewHolder(holder: ViewHolder, position: Int) {
		val item = items[position]
		holder.bind(item)
		RxView.clicks(holder.itemView)
			.map { position }
			.subscribe(itemClickRelay)
	}

	private val fade = DrawableTransitionOptions().crossFade()
	private val circleCrop = RequestOptions().circleCrop()
	private val View.glide: RequestManager
		get() = Glide.with(context)

	inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
		fun bind(post: Post) {
			with(itemView) {
				title.text = post.title
				author.text = post.author
				summary.text = post.summary
				glide.load(post.avatarUrl)
					.transition(fade)
					.apply(circleCrop)
					.into(avatar)
				post.imageUrl?.let {
					image.isVisible = true
					glide.load(post.imageUrl)
						.transition(fade)
						.into(image)
				} ?: run {
					image.isVisible = false
				}
			}
		}
	}
}
