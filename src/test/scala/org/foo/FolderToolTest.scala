package org.foo

import org.junit._
import Assert._

class FolderToolTest {
  @Test
  def shouldGenerateSevenFiles() {
	val folder = "target/foo-" + System.currentTimeMillis
	val ft = new FolderActor(folder, 3)
	ft.generateFiles(7, folder) 
    assertEquals(7, new java.io.File(folder).list.size)
  }
  
  @Test
  def shouldSortFiles() {
	  val folder = "target/foo-" + System.currentTimeMillis
  }
}