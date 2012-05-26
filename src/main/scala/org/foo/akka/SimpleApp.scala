package org.foo.simple
import akka.actor.{ ActorSystem, Actor, Props}
import akka.event._
case class Message(who: String)

class FirstActor extends Actor {
  val second = context.actorOf(Props[SecondActor], name = "second")
  def receive = {
    case msg: Message => 
      println("First")
      second ! msg
  }
}
class SecondActor extends Actor {
  def receive = {
    case Message(who) => println("Hellooo: " + who)
  }
}

object SimpleApp extends App {
  val sys = ActorSystem("test")
  sys.eventStream.setLogLevel(Logging.DebugLevel)
  val first = sys.actorOf(Props[FirstActor], name = "first")
  first ! Message("Kaiyser Söze")
  sys.awaitTermination()
}
