package org.foo
import java.io._
import scala.collection.mutable._
import scala.io._
class Parser {
	def parse(file: File) : Map[String, Int]  = {
		val map = new HashMap[String, Int]
		for (line <- Source.fromFile(file, "UTF-8").getLines()) map += line.trim -> (map.getOrElse(line.trim, 0) + 1) 
		map
	}

}
object ParserApp {
	def main(args: Array[String]) {
		val files = new File("c:/j2ee/domains/").listFiles
		files.foreach(f => println(f.getName + ": " + (new Parser().parse(f).toList sortBy {_._2} takeRight(25) reverse)))
		//println(Source.fromFile("c:/j2ee/domains/Doman_NO.txt", "ISO-8859-1").getLines().size)
		//val map = new Parser().parse("c:/j2ee/domains/Doman_NO.txt")
		//println(map)
		//val lastTen = map.toList sortBy {_._2} takeRight 10
		//println(lastTen.reverse)
		
	}
	
}