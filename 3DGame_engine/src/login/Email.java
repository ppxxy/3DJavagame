package login;

import java.awt.Color;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.*;

import Localization.Localization;

public class Email {

	Window window;

	public boolean emailOk = true;
	public String emailaddr;

	public void isValidEmailAddress(String email) {

		window = new Window();

 	   try {
 	      InternetAddress emailAddr = new InternetAddress(email);
 	      emailAddr.validate();
 	      emailOk = true;
 	   } catch (AddressException ex) {
 	      emailOk = false;
 	      window.panel.add(window.warning);
           window.warning.setForeground(Color.RED);
           window.warning.setText(Localization.getBundle().getString("invalid_email"));
           window.warning.setBounds(window.createUser.getX()-20, window.createUser.getY()+window.createUser.getHeight()+10, 400, 30);
 	   }
 	}

	public void sendEmail(String mes, String emailaddr) {

        final String username = "raivogaming@gmail.com";
        final String password = "sa95vi99vaso";

        Properties props = new Properties();
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
          new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
          });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("raivogaming@gmail.com"));
            message.setRecipients(Message.RecipientType.TO,
                InternetAddress.parse(emailaddr));
            message.setSubject(Localization.getBundle().getString("email_Java3DAccount"));
            message.setText(mes);

            Transport.send(message);

            System.out.println(Localization.getBundle().getString("done"));

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
}

}
