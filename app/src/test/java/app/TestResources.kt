package app

import com.squareup.moshi.Moshi

object TestResources {

	inline fun <reified T> fromJson(file: String): T =
		Moshi.Builder().build().adapter(T::class.java).fromJson(file.asString())!!

	fun String.asString(): String = this.javaClass::class.java.getResource(this).readText()
}
