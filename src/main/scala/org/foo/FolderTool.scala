package org.foo
import java.io._
import java.util.Random
import java.util.Date
import scala.actors._

case class DeleteAll
case class ListAll
case class Cap
case class ParallellDelete


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
  val workers = Array(
		  new DeleteFileActor().start,
		  new DeleteFileActor().start,
		  new DeleteFileActor().start)
  
  def act() {
    while (true) {
      receive {
        case DeleteAll => println("Deleting: " + removeAll(target) + " files")
        case ListAll => sort(new File(target).listFiles).foreach(f => println(f.getName + ", \tlastModified: " + f.lastModified))
        case Cap => 
          println("cap")	
          val result = removeFrom(from, target)
          if (result > 0) println("Deleting " + result + " from: " + target + ", " + from + " left : " + new Date)
        case ParallellDelete => removeFromInParallell(from, target)
          
      }
    }
  }
  
  
  def removeAll(folder: String) = {
    val files = new File(folder).listFiles
    files.foreach(f => f.delete)
    files.size
  }
  def removeFrom(from: Int, folder: String) = {
	println(folder)
    val files = sort(new File(folder).listFiles)
    println("listFiles: " + files)
    for (i <- 0 until files.size if i >= from) yield files(i).delete
    files.size - from
  }
  def removeFromInParallell(from: Int, folder: String) = {
    val files = sort(new File(folder).listFiles)
    for (i <- 0 until files.size if i >= from) yield workers(i%3) ! DeleteFile(files(i))
  }
  def sort(unsorted: Array[File]) = List.fromArray(unsorted).sort((s, t) => s.lastModified > t.lastModified)
}
