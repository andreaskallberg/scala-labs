package org.foo

import java.io._
import java.util.Random
import java.util.Date
import scala.actors._


case class DeleteFile(name: File)
class DeleteFileActor extends Actor {
 def act() {
    while (true) {
      receive {
    	  case DeleteFile(fileName) => fileName.delete
      }
    }
  }
}