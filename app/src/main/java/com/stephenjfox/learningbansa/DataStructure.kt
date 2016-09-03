package com.stephenjfox.learningbansa

import android.view.View
import com.brianegan.bansa.Action
import com.brianegan.bansa.Reducer
import com.brianegan.bansaDevTools.DevToolsStore
import java.util.*

/**
 * Data driven elements that coax the problem of data
 * Created by Stephen on 9/2/2016.
 */
  data class ApplicationState(val counters: Map<UUID, Int> = linkedMapOf(
    UUID.randomUUID() to 0,
    UUID.randomUUID() to 0
))

sealed class StateActions {
  data class INIT(val applicationState: ApplicationState = ApplicationState()) : Action
  data class INCREMENT(val id: UUID) : Action
  data class DECREMENT(val id: UUID) : Action
}

class CounterReducer : Reducer<ApplicationState> {
  override fun reduce(state: ApplicationState, action: Action): ApplicationState = when (action) {
    is StateActions.INIT -> action.applicationState
    is StateActions.INCREMENT -> {
      val freshCounters = LinkedHashMap(state.counters)
      freshCounters[action.id] = state.counters[action.id]?.plus(1)

      state.copy(counters = freshCounters)
    }
    is StateActions.DECREMENT -> {
      val freshCounters = LinkedHashMap(state.counters)
      freshCounters[action.id] = state.counters[action.id]?.minus(1)

      state.copy(counters = freshCounters)
    }
    else -> state
  }
}

/**
 * The main data store for the application
 */
val mainStore = DevToolsStore<ApplicationState>(ApplicationState(), CounterReducer())


/**
 * pass-through for the presentation layer
 */
data class CounterViewModel(val count: Int,
                            val incrementHandle: View.OnClickListener,
                            val decrementHandle: View.OnClickListener)
