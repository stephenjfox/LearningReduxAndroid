package com.stephenjfox.learningbansa

import android.content.Context
import android.support.v4.widget.DrawerLayout
import android.util.AttributeSet
import android.widget.LinearLayout
import com.brianegan.bansaDevToolsUi.BansaDevToolsPresenter
import trikita.anvil.Anvil
import trikita.anvil.DSL.*

/**
 * Base class that handles essential lifecycle hooks for us
 */
abstract class RenderableDrawerLayout : DrawerLayout, Anvil.Renderable {

  constructor(context: Context) : super(context)

  constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

  constructor(context: Context,
              attrs: AttributeSet,
              defStyleAttr: Int) : super(context, attrs, defStyleAttr)

  /**
   * This is the Android-Anvil lifecycle hook that I've missed in the past
   */
  public override fun onAttachedToWindow() {
    super.onAttachedToWindow()
    Anvil.mount(this, this)
  }

  public override fun onDetachedFromWindow() {
    super.onDetachedFromWindow()
    Anvil.unmount(this)
  }

  abstract override fun view()
}


val devToolsPresent = BansaDevToolsPresenter<ApplicationState>(mainStore)

fun counterTemplate(model: CounterViewModel) {
  
  val (count, increment, decrement) = model
  
  linearLayout {
    size(FILL, WRAP)
    orientation(LinearLayout.VERTICAL)

    textView {
      text(count.toString())
      gravity(CENTER_HORIZONTAL)
      textSize(sip(100F))
      padding(0, dip(40))
    }

    button {
      size(FILL, WRAP)
      text("+")
      padding(dip(10))
      margin(dip(12), 0)
      onClick(increment)
    }

    button {
      size(FILL, WRAP)
      text("-")
      padding(dip(10))
      margin(dip(12), 0)
      onClick(decrement)
    }
  }
}

/**
 * Inserts [models] transformed into a [counterTemplate]
 * just above the timeTravel debugger
 */
fun counterDebugScreen(models: List<CounterViewModel>) {
  scrollView {
    linearLayout {
      orientation(LinearLayout.VERTICAL)
      id(R.id.counter_view)

      models.forEach { counterModel ->
        counterTemplate(counterModel)
      }

      xml(R.layout.bansa_dev_tools) {

        init {
          devToolsPresent.unbind()
          devToolsPresent.bind(Anvil.currentView())
        }
      }
    }
  }
}