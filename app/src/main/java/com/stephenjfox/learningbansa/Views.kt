package com.stephenjfox.learningbansa

import android.content.Context
import android.support.v4.widget.DrawerLayout
import android.util.AttributeSet
import android.widget.LinearLayout
import trikita.anvil.Anvil
import trikita.anvil.DSL.*


abstract class RenderableDrawerLayout : DrawerLayout, Anvil.Renderable {

  constructor(context: Context) : super(context)

  constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

  constructor(context: Context,
              attrs: AttributeSet,
              defStyleAttr: Int) : super(context, attrs, defStyleAttr)

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

fun counterView(model: CounterViewModel) {
  
  val (count, increment, decrement) = model
  
  linearLayout {
    size(FILL, WRAP)
    orientation(LinearLayout.VERTICAL)
    id(R.id.counter_view)

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