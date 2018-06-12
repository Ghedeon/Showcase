package app.di

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import app.common.ViewModelFactory
import app.ui.MainActivity
import app.ui.posts.PostsFragment
import app.ui.posts.PostsViewModel
import com.spotify.mobius.runners.WorkRunner
import com.spotify.mobius.rx2.SchedulerWorkRunner
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap
import io.reactivex.schedulers.Schedulers
import javax.inject.Singleton

@Module(includes = [FragmentModule::class, ViewModelModule::class])
abstract class UiModule {

	@ContributesAndroidInjector
	abstract fun mainActivity(): MainActivity

	@Module
	companion object {

		@ForEvent
		@Provides
		@JvmStatic
		fun eventRunner(): WorkRunner = SchedulerWorkRunner(Schedulers.computation())

		@ForEffect
		@Provides
		@JvmStatic
		fun effectRunner(): WorkRunner = SchedulerWorkRunner(Schedulers.computation())
	}
}

@Module
abstract class FragmentModule {

	@ContributesAndroidInjector
	abstract fun postsFragment(): PostsFragment
}

@Module
interface ViewModelModule {

	@Binds
	@Singleton
	fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

	@Binds
	@IntoMap
	@ViewModelKey(PostsViewModel::class)
	fun bindPostsViewModel(viewModel: PostsViewModel): ViewModel


}
