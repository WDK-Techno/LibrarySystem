package library.librarysystem.Function;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class MailSender {

    public void send(String toEmail,String subject,String content){
        System.out.println("Senders : " + toEmail);
        System.out.println("Subject : " + subject);
        System.out.println("Content : " + content);

//        String toEmail = "wdilshankavindra@gmail.com , dkavindraweerasinghe@gmail.com";
        String fromEmail = "wdkprogramtest@gmail.com";
//        String subject = "WDK Program Test 2";
//        String content = "Hello I am WDK. This is testing msg";


        // Assuming you are sending email from through gmails smtp
        String host = "smtp.gmail.com";

        // Get system properties
        Properties properties = System.getProperties();

        // Setup mail server
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");

        // Get the Session object.// and pass username and password
        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {

            protected PasswordAuthentication getPasswordAuthentication() {

                return new PasswordAuthentication("wdkprogramtest@gmail.com", "jejxfenatwlcldsx");

            }

        });

        // Used to debug SMTP issues
        session.setDebug(true);

        try {


            // Create a default MimeMessage object.
            MimeMessage message = new MimeMessage(session);

            // Set From: header field of the header.
            message.setFrom(new InternetAddress(fromEmail));

            // Set To: header field of the header.
            message.addRecipients(Message.RecipientType.BCC,InternetAddress.parse(toEmail));

            // Set Subject: header field
            message.setSubject(subject);

            // Now set the actual message
            message.setText(content);
            System.out.println("sending...");
            // Send message
            Transport.send(message);
            System.out.println("Sent message successfully....");
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }

    }

    public static void main(String[] args) {
        MailSender mailSender = new MailSender();

        mailSender.send("wdilshankavindra@gmail.com,dkavindraWeerasinghe,","Hello testing 3","Hello I am wdk programing test");
    }

}