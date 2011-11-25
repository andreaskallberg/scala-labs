package org.foo

import java.io._
import java.util.Random
import java.util.Date
class FolderReader(folder: String) {
  def list(): Array[File] = {
	  sort(new File(folder).listFiles)    
  }
  def sort(unsorted: Array[File]) = unsorted
}
