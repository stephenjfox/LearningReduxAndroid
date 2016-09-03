package com.stephenjfox.learningbansa

import android.content.Context
import android.view.View
import android.widget.LinearLayout
import com.brianegan.bansa.Store
import com.brianegan.bansa.Subscription
import trikita.anvil.Anvil
import trikita.anvil.DSL.*

/**
 * Created by Stephen on 9/2/2016.
 */
class RootCounterView(context: Context, val dataStore: Store<ApplicationState>)
: RenderableDrawerLayout(context) {

  var subscription: Subscription? = null

  override fun view() {
    linearLayout {
      size(FILL, WRAP)
      orientation(LinearLayout.VERTICAL)

      textView {
        text("Counts: ${dataStore.state.counter}")
      }

      button {
        text("+")
        padding(dip(10))
        onClick {
          dataStore.dispatch(StateActions.INCREMENT)
        }
      }

      button {
        text("-")
        padding(dip(10))
        onClick {
          dataStore.dispatch(StateActions.DECREMENT)
        }
      }
    }
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

  val increment = View.OnClickListener {
    dataStore.dispatch(StateActions.INCREMENT)
  }

  val decrement = View.OnClickListener {
    dataStore.dispatch(StateActions.DECREMENT)
  }

  fun buildViewModel(): CounterViewModel {
    return CounterViewModel(
        count = dataStore.state.counter,
        incrementHandle = increment,
        decrementHandle = decrement)
  }

  /**
   * pass-through for the presentation layer
   */
  data class CounterViewModel(val count: Int,
                              val incrementHandle: OnClickListener,
                              val decrementHandle: OnClickListener)
}