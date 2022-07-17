package library.librarysystem.Function;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class MailSender {

//    public static void send(String toEmail,String subject,String content){
    
    public String toEmail;
    public String subject;
    public String content;

    public void send() {

        System.out.println("Senders : " + toEmail);
        System.out.println("Subject : " + subject);
        System.out.println("Content : " + content);


        String fromEmail = "wdkprogramtest@gmail.com";
        String host = "smtp.gmail.com";

        Properties properties = System.getProperties();

        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");

        // set username and password
        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {

            protected PasswordAuthentication getPasswordAuthentication() {

                return new PasswordAuthentication("wdkprogramtest@gmail.com", "jejxfenatwlcldsx");

            }

        });

        session.setDebug(true);

        try {


            MimeMessage message = new MimeMessage(session);
            // from email
            message.setFrom(new InternetAddress(fromEmail));
            // sending emails
            message.addRecipients(Message.RecipientType.BCC, InternetAddress.parse(toEmail));
            // Set subject
            message.setSubject(subject);
            // Email content
            message.setText(content);
            System.out.println("sending...");
            Transport.send(message);
            System.out.println("Sent message successfully....");

        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }

}
