package org.foo

import org.junit._
import Assert._

class Bar {
  @Test
  def bar() { 
    assertEquals(42, 6 * 7)
  }
}