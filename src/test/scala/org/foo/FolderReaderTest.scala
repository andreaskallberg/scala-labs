package org.foo

import org.scalatest.FeatureSpec
import org.scalatest.junit.JUnitRunner
import org.junit.runner.RunWith
import org.scalatest.GivenWhenThen

object FolderReaderRunner {
   def main(args : Array[String]) : Unit = {
    (new FolderReaderTest).execute()
  }
}
 
@RunWith(classOf[JUnitRunner]) 
class FolderReaderTest extends FeatureSpec with GivenWhenThen {
 
  feature("Enumerating database files from folder") {
 
    scenario("Only sql files in folder") {
 
      given("a folder with 5 sql files") 
      	val folderReader = new FolderReader("src/test/resources/onlysql")        
      when("when the files are enumerated")
      	val fileList = folderReader.list()
      then("then the list size should be 5")
      	assert(fileList.length == 5)
      and("the order should be based on file name")
        assert(fileList(0).getName().startsWith("1"))
        assert(fileList(1).getName().startsWith("2"))
        assert(fileList(2).getName().startsWith("3"))
        assert(fileList(3).getName().startsWith("4"))
        assert(fileList(4).getName().startsWith("5"))
    }
  }

}