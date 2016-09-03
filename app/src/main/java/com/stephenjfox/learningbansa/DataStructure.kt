package com.stephenjfox.learningbansa

import android.view.View
import com.brianegan.bansa.Action
import com.brianegan.bansa.BaseStore
import com.brianegan.bansa.Reducer

/**
 * Created by Stephen on 9/2/2016.
 */
data class ApplicationState(var counter: Int = 0)

sealed class StateActions {
  object INIT : Action
  object RESET : Action
  object INCREMENT : Action
  object DECREMENT : Action
}

class CounterReducer : Reducer<ApplicationState> {
  override fun reduce(state: ApplicationState, action: Action): ApplicationState = when (action) {
    is StateActions.INIT -> ApplicationState()
    is StateActions.INCREMENT -> state.copy(counter = state.counter + 1)
    is StateActions.DECREMENT -> state.copy(counter = state.counter - 1)
    is StateActions.RESET -> ApplicationState()
    else -> state
  }
}

/**
 * The main data store for the application
 */
val mainStore = BaseStore<ApplicationState>(ApplicationState(), CounterReducer())


/**
 * pass-through for the presentation layer
 */
data class CounterViewModel(val count: Int,
                            val incrementHandle: View.OnClickListener,
                            val decrementHandle: View.OnClickListener)
