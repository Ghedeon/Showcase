package app.di

import android.arch.lifecycle.ViewModel
import dagger.MapKey
import javax.inject.Qualifier
import kotlin.annotation.AnnotationRetention.RUNTIME
import kotlin.reflect.KClass

@MustBeDocumented
@Target(AnnotationTarget.FUNCTION)
@Retention(RUNTIME)
@MapKey
annotation class ViewModelKey(val value: KClass<out ViewModel>)

@MustBeDocumented
@Qualifier
@Retention(RUNTIME)
annotation class IO

@MustBeDocumented
@Qualifier
@Retention(RUNTIME)
annotation class UI

@MustBeDocumented
@Qualifier
@Retention(RUNTIME)
annotation class ForEvent

@MustBeDocumented
@Qualifier
@Retention(RUNTIME)
annotation class ForEffect


