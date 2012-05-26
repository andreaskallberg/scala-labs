package org.foo.akka
import akka.camel.{Consumer, CamelMessage, Producer}
import akka.actor.Actor

class FileConsumer extends Consumer {
  import akka.util.Timeout
  import akka.util.duration._
  def endpointUri = "file:src/tmp"

  implicit val timeout = Timeout(30 seconds)

  def receive = {
    case msg: CamelMessage => 
      println("received %s" format msg.bodyAs[String])
      context.system.eventStream.publish(NewFileEvent(msg.bodyAs[String]))
  }
}

object FileWatcherApp extends App {
  import akka.actor.{Props, ActorSystem}
  val sys = ActorSystem("FileWatcher")
  sys.actorOf(Props[FileConsumer], "FileConsumer")
  sys.eventStream.subscribe(sys.actorOf(Props[LogListener], "LogListener"), classOf[NewFileEvent])
  sys.awaitTermination()
}
case class NewFileEvent(name: String)
case class EmailerConfig(gmailPassword: String)
case class Email(from: String, to: String, subject: String, body: String)

class LogListener extends Actor {
  def receive = {
    case newFile: NewFileEvent => println("New file: " + newFile.name)
  }
}


class Emailer(cfg: EmailerConfig) extends Actor with Producer {
  import akka.actor.Status.Failure
  def endpointUri = "smtps://smtp.gmail.com:465?username=andreas.kallberg@gmail.com&password=%s&debugMode=true" format cfg.gmailPassword

  override protected def transformOutgoingMessage(msg: Any) = msg match {
    case Email(from, to, subject, body) =>
      new CamelMessage(body, Map("from" -> from,  "to" -> to,  "subject"-> subject))
  }

  override protected def transformResponse(msg : Any) = msg match {
    case resp: Failure => resp
    case _ => msg
  }
}

