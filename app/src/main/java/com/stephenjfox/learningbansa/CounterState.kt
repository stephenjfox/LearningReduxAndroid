package com.stephenjfox.learningbansa

import com.brianegan.bansa.Action
import com.brianegan.bansa.BaseStore
import com.brianegan.bansa.Reducer
import java.util.UUID
import java.util.ArrayList

/**
 * The types that are relevant for our applications data
 *
 * Created by Stephen on 9/3/2016.
 */
data class ApplicationState(val counters: List<Counter> = listOf())

data class Counter(val id: UUID = UUID.randomUUID(), val value: Int = 0, val selected: Boolean = false)

val store = BaseStore<ApplicationState>(ApplicationState(), CounterReducer())

/**
 * A concise explanation of the basic transformations of our app.
 * Mapping [List] mutations is memory "intensive" (rapid creation and destruction
 * of [ArrayList]).
 * Considering implementing lazy Sequences (but I'm not sure that will work)
 */
class CounterReducer : Reducer<ApplicationState> {
  override fun reduce(state: ApplicationState, action: Action): ApplicationState = when (action) {
    is StateTransforms.INIT -> action.state

    is StateTransforms.REMOVE -> state.copy(counters = state.counters.filter {
      !it.selected
    })

    is StateTransforms.ADD -> state.copy(counters = state.counters + action.counter)

    is StateTransforms.INCREMENT -> state.copy(counters = state.counters.map {
      when {
        it.id.equals(action.id) -> it.copy(value = it.value + 1)
        else -> it
      }
    })

    is StateTransforms.DECREMENT -> state.copy(counters = state.counters.map {
      when {
        it.id.equals(action.id) -> it.copy(value = it.value - 1)
        else -> it
      }
    })

    is StateTransforms.SELECT -> state.copy(counters = state.counters.map {
      when {
        it.id.equals(action.id) -> it.copy(selected = true)
        else -> it.copy(selected = false)
      }
    })

    else -> state
  }

}
