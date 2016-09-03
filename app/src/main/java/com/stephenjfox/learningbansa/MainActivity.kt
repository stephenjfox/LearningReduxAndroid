package com.stephenjfox.learningbansa

import android.os.Bundle
import android.support.v7.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(RootCounterView(this, mainStore))
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
}
