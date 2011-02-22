package org.foo

import org.junit._
import Assert._
import java.io._
import java.util._

class FolderToolTest {
  val folderName = "target/foo-" + System.currentTimeMillis
  @Test
  @Ignore
  def shouldGenerateSevenFiles() {
    generate(7)
    assertEquals(7, new java.io.File(folderName).list.size)
  }

  @Test
  def deleteFilesInParallellShouldWork() {
  	generateFiles(100, folderName)
  	val folderActor = new FolderActor(folderName, 10)
  	folderActor.start
  	val now = System.currentTimeMillis
  	folderActor ! ParallellDelete
  	println("deleteFilesInParallellShouldWork took: " + (System.currentTimeMillis - now)) 
  }

  @Test
  def deleteFilesInSequenceShouldWork() {
  	
  	val folderActor = new FolderActor(folderName, 10)
  	folderActor.start
  	val now = System.currentTimeMillis
  	folderActor ! Cap
  	println("deleteFilesInSequenceShouldWork took: " + (System.currentTimeMillis - now)) 
  }
  def generate(size: Int) {
    println("Genrerated: " + generateFiles(size, folderName) + " files")
  }
  
  @Before
  def initFiles = generate(100)
  
  def generateFiles(noOfFiles: Int, parentFolder: String) : Int = {
    val parent = new File(parentFolder)
    val random = new Random(System.currentTimeMillis)
    if (!parent.exists) parent.mkdir
    for (i <- 0 until noOfFiles) {
      new File(parentFolder + "/" + random.nextLong + ".txt").createNewFile
      //Thread.sleep(1)
    }
    noOfFiles
  }
}