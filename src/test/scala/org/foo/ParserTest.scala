package org.foo
import org.junit._
import Assert._
import java.io._
import scala.collection.mutable._
class ParserTest {
  @Test
  def shouldCountDomains() {

	  val truth = Map("online.no" -> 9, "hotmail.no" -> 1, "yahoo.com" -> 4, "live.no" -> 3, "gmail.com" -> 9, "olavsgaard.no" -> 1)
	  val parser = new Parser()
	  val ret =  parser.parse(new File("src/test/resources/test.txt"))
	  assertEquals(truth, ret)
	  println(ret)
  }
}