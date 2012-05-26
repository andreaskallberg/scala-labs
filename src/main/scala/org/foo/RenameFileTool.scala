package org.foo

class RenameFileTool(val folderName: String) {
  
  def renameFiles(find: String, replacement: String) : List[(String, String)] = {
    List(("foo", "bar"))
  }
  def renameFiles(files: List[String], find: String, replacement: String) : List[(String, String)] = {
    val renamedFiles = files.foldLeft(List[(String, String)]()) ((b, a) => if (a.startsWith(find)) b :+ (replacement, a) else b)
    renamedFiles
  }
}