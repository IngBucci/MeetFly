package sample.util;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;

/**
 * A utility class for sending e-mail with attachment.
 *
 * @author www.codejava.net
 *
 */
public class Email {


    /**
     * This methos is used to set  parameters for sending Email
     *
     * @param host
     * @param port
     * @param userName : the email of the sender
     * @param password
     * @param email
     * @param subject  : the subject of the email
     * @param message  : message
     * @throws AddressException
     * @throws MessagingException
     */

    public static void sendEmail(String host, String port,
                                 final String userName, final String password, String email,
                                 String subject, String message) throws AddressException,
            MessagingException {
        // sets SMTP server properties
        Properties properties = new Properties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", port);
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.user", userName);
        properties.put("mail.password", password);

        // creates a new session with an authenticator
        Authenticator auth = new Authenticator() {
            public PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(userName, password);
            }
        };
        Session session = Session.getInstance(properties, auth);

        // creates a new e-mail message
        Message msg = new MimeMessage(session);

        msg.setFrom(new InternetAddress(userName));
        InternetAddress[] toAddresses = {new InternetAddress(email)};
        msg.setRecipients(Message.RecipientType.TO, toAddresses);
        msg.setSubject(subject);
        msg.setSentDate(new Date());
        msg.setContent(message, "text/plain");


        // sends the e-mail
        Transport.send(msg);

    }
}
