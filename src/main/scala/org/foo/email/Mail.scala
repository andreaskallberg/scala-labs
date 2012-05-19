package org.foo.email
import java.util.Properties
import javax.mail._
import javax.mail.internet._

object Mail extends App {
  val props = new Properties()
  props.put("mail.smtp.host", "smtp.gmail.com")
  props.put("mail.smtp.socketFactory.port", "465")
  props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory")
  props.put("mail.smtp.auth", "true")
  props.put("mail.smtp.port", "465")
 
  val session = Session.getDefaultInstance(props,
      new javax.mail.Authenticator() {
          override def getPasswordAuthentication() : PasswordAuthentication = {
            return new PasswordAuthentication(System.getProperty("user"), System.getProperty("pwd"));
          }
      });
 
	
 
  val message = new MimeMessage(session)
  message.setFrom(new InternetAddress(System.getProperty("from")))
  message.setRecipients(Message.RecipientType.TO, System.getProperty("to"))
  message.setSubject(System.getProperty("subject"))
  message.setText(System.getProperty("body"))
  Transport.send(message)
  System.out.println("Done")
 
	
}