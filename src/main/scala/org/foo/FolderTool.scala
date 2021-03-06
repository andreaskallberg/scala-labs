package org.foo
import java.io._
import java.util.Random
import java.util.Date
import scala.actors._

case class Generate
case class DeleteAll
case class ListAll
case class Cap


class FolderJobScheduler(foo: FolderActor, timeout: Int) extends Thread {
  var doRun = true
  def end = doRun = false
  override def run = {
    while (doRun) {
      foo ! Cap
      Thread.sleep(timeout)
    }
    println("Shutting down...")
  }
}
class FolderActor(target: String, from: Int) extends Actor {

  def act() {
    while (true) {
      receive {
        case Generate => println("Generating: " + generateFiles(10, target) + " files")
        case DeleteAll => println("Deleting: " + removeAll(target) + " files")
        case ListAll => sort(new File(target).listFiles).foreach(f => println(f.getName + ", \tlastModified: " + f.lastModified))
        case Cap => 
          val result = removeFrom(from, target)
          if (result > 0) println("Deleting " + result + " from: " + target + ", " + from + " left : " + new Date)
      }
    }
  }
  
  def generateFiles(noOfFiles: Int, parentFolder: String) = {
    val parent = new File(parentFolder)
    val random = new Random(System.currentTimeMillis)
    if (!parent.exists) parent.mkdir
    for (i <- 0 until noOfFiles) {
      new File(parentFolder + "/" + random.nextLong + ".txt").createNewFile
      Thread.sleep(100)
    }
    noOfFiles
  }
  def removeAll(folder: String) = {
    val files = new File(folder).listFiles
    files.foreach(f => f.delete)
    files.size
  }
  def removeFrom(from: Int, folder: String) = {
    val files = sort(new File(folder).listFiles)
    for (i <- 0 until files.size if i >= from) yield files(i).delete
    files.size - from
  }
  def sort(unsorted: Array[File]) = List.fromArray(unsorted).sort((s, t) => s.lastModified > t.lastModified)
}
