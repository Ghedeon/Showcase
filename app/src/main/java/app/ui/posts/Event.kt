package app.ui.posts

import app.common.error.ErrorMsg
import app.domain.Post


sealed class Event

object InitEvent : Event()
sealed class PostsLoadedEvent : Event() {
	data class Success(val posts: List<Post>) : PostsLoadedEvent()
	data class Failure(val msg: ErrorMsg) : PostsLoadedEvent()
}

data class ItemClickedEvent(val position: Int) : Event()

