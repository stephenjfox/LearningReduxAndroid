package com.stephenjfox.learningbansa

import android.content.Context
import android.view.View
import android.widget.LinearLayout
import com.brianegan.bansa.Store
import com.brianegan.bansa.Subscription
import trikita.anvil.Anvil
import trikita.anvil.RenderableAdapter
import trikita.anvil.RenderableView
import trikita.anvil.DSL.*

/**
 * Created by Stephen on 9/3/2016.
 */

class CounterAdapter<M, VM>(var models: List<M>,
                            val mapModelToViewModel: (M) -> VM,
                            val renderableItem: RenderableAdapter.Item<VM>) : RenderableAdapter() {
  override fun getItem(position: Int): VM = mapModelToViewModel(models[position])

  override fun getCount(): Int = models.size

  override fun view(index: Int) {
    renderableItem.view(index, this.getItem(index))
  }

  fun update(newModels: List<M>): CounterAdapter<M, VM> {
    if (!models.equals(newModels)) {
      this.models = newModels
      notifyDataSetChanged()
    }

    return this
  }
}

class RootView(c: Context, store: Store<ApplicationState>) : RenderableView(c) {

  val stateChangedSubscription: Subscription? = store.subscribe { Anvil.render() }

  val mapCounterToViewModel = buildInteractiveCounterViewModel(store)

  val simpleAdapter = CounterAdapter<Counter, CounterViewModel>(
      listOf(),
      mapCounterToViewModel,
      RenderableAdapter.Item { index, counterViewModel -> counterView(counterViewModel) }
  )

  val add: (View) -> Unit = {
    store.dispatch(StateTransforms.ADD())
  }

  val remove: (View) -> Unit = {
    store.dispatch((StateTransforms.REMOVE))
  }

  override fun onDetachedFromWindow() {
    super.onDetachedFromWindow()
    stateChangedSubscription?.unsubscribe()
  }

  override fun view() {
    frameLayout {
      // setup the control panel
      linearLayout {
        size(FILL, dip(50))
        orientation(LinearLayout.VERTICAL)

        // the actual buttons
        linearLayout {
          orientation(LinearLayout.HORIZONTAL)

          button {
            text("Add")
            size(0, WRAP)
            weight(1F)
            padding(dip(10))

            onClick(add)
          }

          button {
            text("Remove")
            size(0, WRAP)
            weight(1F)
            padding(dip(10))

            onClick(remove)
          }
        }
      }

      listView {

        margin(dip(0), dip(50), dip(0), dip(0))
        size(FILL, FILL)

        adapter(simpleAdapter.update(store.state.counters))
      }
    }
  }
}