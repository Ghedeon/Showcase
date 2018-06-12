package app.common.mobius

import com.spotify.mobius.First
import com.spotify.mobius.MobiusLoop
import com.spotify.mobius.Next
import timber.log.Timber


class MobiusLogger<MODEL, EVENT, EFFECT> : MobiusLoop.Logger<MODEL, EVENT, EFFECT> {

	override fun beforeInit(model: MODEL) = Timber.d("Initializing loop")

	override fun afterInit(model: MODEL, result: First<MODEL, EFFECT>) {
		Timber.d("Loop initialized, starting from model: ${result.model()}")
		for (effect in result.effects()) {
			Timber.d("Effect dispatched: $effect")
		}
	}

	override fun exceptionDuringInit(model: MODEL, exception: Throwable?) =
		Timber.d("FATAL ERROR: exception during initialization from model $model")

	override fun beforeUpdate(model: MODEL, event: EVENT) = Timber.d("Event received: $event")

	override fun exceptionDuringUpdate(model: MODEL, event: EVENT, exception: Throwable?) =
		Timber.d(exception, "FATAL ERROR: exception updating model $model with event $event")

	override fun afterUpdate(model: MODEL, event: EVENT, result: Next<MODEL, EFFECT>) {
		if (result.hasModel()) {
			Timber.d("Model updated: ${result.modelUnsafe()}")
		}

		for (effect in result.effects()) {
			Timber.d("Effect dispatched: $effect")
		}
	}
}
