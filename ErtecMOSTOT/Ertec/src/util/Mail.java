package util;
 

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Mail {
  private final Properties properties = new Properties();
  
  private static String password = "ertec1787";
  private static String username = "mailertec";
  
   

  
  public static void sendEmail(String destino,String asunto, String mensaje){
    Properties props = new Properties();
    props.put("mail.smtp.auth", "true");
    props.put("mail.smtp.starttls.enable", "true");
    props.put("mail.smtp.host", "smtp.gmail.com");
    props.put("mail.smtp.port", "587");

    Session session = Session.getInstance(props,
            new javax.mail.Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(username, password);
                }
            });

    try {
        System.out.println(">>>mail para:"+ destino);
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(username));
        message.setRecipients(Message.RecipientType.TO,
                InternetAddress.parse(destino));
        message.setSubject(asunto);
        message.setText(mensaje);

        Transport.send(message);
 

    } catch (MessagingException e) {
        //throw new RuntimeException(e);
      e.printStackTrace();
    }
  }
  
  
  
  public static void main(String[] args) {
    Mail.sendEmail("elieljuegos@gmail.com", "probando", "Ertec");
    System.out.println("mail");
  }

}