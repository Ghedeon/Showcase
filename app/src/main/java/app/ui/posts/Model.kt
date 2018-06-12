package app.ui.posts

import app.common.error.ErrorMsg
import app.domain.Post

sealed class Model

object InitModel : Model()
data class DisplayPostsModel(val posts: List<Post>) : Model()
data class BrowsePostModel(val url: String) : Model()
data class ErrorModel(val msg: ErrorMsg) : Model()
