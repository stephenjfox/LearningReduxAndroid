package com.stephenjfox.learningbansa

import android.content.Context
import android.view.View
import com.brianegan.bansa.Store
import com.brianegan.bansa.Subscription
import trikita.anvil.Anvil
import java.util.UUID

/**
 * Practical copy-paste of Brian's, but I'm still learning
 * todo: change this comment to be useful
 * Created by Stephen on 9/2/2016.
 */
class RootCounterView(context: Context, val dataStore: Store<ApplicationState>)
: RenderableDrawerLayout(context) {

  var subscription: Subscription? = null

  override fun view() {
    counterDebugScreen(dataStore.state.counters.keys.map { id ->
      buildViewModel(id)
    })
  }

  override fun onAttachedToWindow() {
    super.onAttachedToWindow()

    subscription = dataStore.subscribe {
      Anvil.render()
    }
  }

  override fun onDetachedFromWindow() {
    super.onDetachedFromWindow()
    subscription?.unsubscribe()
  }

  fun buildViewModel(id: UUID): CounterViewModel {

    val increment = View.OnClickListener {
      dataStore.dispatch(StateActions.INCREMENT(id))
    }

    val decrement = View.OnClickListener {
      dataStore.dispatch(StateActions.DECREMENT(id))
    }

    return CounterViewModel(
        count = dataStore.state.counters[id]!!,
        incrementHandle = increment,
        decrementHandle = decrement)
  }
}