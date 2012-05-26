package org.foo.akka
import akka.camel.{Consumer, CamelMessage}
import akka.actor.{Props, ActorSystem, ActorLogging}
import akka.util.Timeout
import akka.util.duration._
import akka.pattern.ask
import akka.actor.Actor
import akka.actor.Status.Failure
import akka.camel.{CamelMessage, Producer}
import akka.camel.MessageResult
import org.apache.camel.{ Exchange, ExchangePattern, AsyncCallback }
import akka.dispatch.Await


object HttpWatcherApp extends App {
  val sys = ActorSystem("HttpWatcher")
  sys.actorOf(Props[HttpConsumer], "HttpConsumer")
  sys.awaitTermination()
}

class HttpConsumer extends Consumer {
  def endpointUri = "jetty://http://127.0.0.1:8888/foo"

  implicit val timeout = Timeout(30 seconds)

  protected def receive = {
    case msg : CamelMessage => {
      val name = msg.header("name").getOrElse("unknown")
      val message = "Hello %s" format name
      sender ! message
    }
  }
}

