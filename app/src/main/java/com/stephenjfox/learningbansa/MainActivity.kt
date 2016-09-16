package com.stephenjfox.learningbansa

import android.os.Bundle
import android.support.v7.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(RootView(this, store))
  }
  /**
   * With these first three commits, do I now know enough to create a calculator?
   * I can draw a view and re-render in a healthy fashion.
   *
   * I think I need to try a few things, beforehand:
   * 1) Multiple stores to act against (used in: calculator screen and buttons)
   * 2) Multiple views working off of one store (for a text editor)
   * Implementation) Key-press event is an action against the data store?
   *
   * Re-watch this: [Clean architecture](https://www.youtube.com/watch?v=Nsjsiz2A9mg)
   */

  /**
   * Currently (2016-9-3) the list view is done with an Android "ListView", which is discouraged.
   * I would like to implement with a Recycler view, but I don't know if Anvil supports it
   */

  /**
   * 2016-9-15
   * Took me a few minutes to remember how the state reducers worked. I think I should use a different
   * reducer for each level of abstraction: one for the counter-list-representation, one for the
   * Counter-tier of abstraction. Maybe Dan Abrahmov has an explanation on which is better practice
   */
}
