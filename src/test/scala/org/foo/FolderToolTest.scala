package org.foo

import org.junit._
import Assert._

class FolderToolTest {
  val folderName = "target/foo-" + System.currentTimeMillis
  @Test
  def shouldGenerateSevenFiles() {
    generate(7)
    assertEquals(7, new java.io.File(folderName).list.size)
  }

  @Test
  def shouldSortFiles() {
  	
  }
  def generate(size: Int) {
    val ft = new FolderActor(folderName, 3)
    ft.generateFiles(7, folderName)
  }
}