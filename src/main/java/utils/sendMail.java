package utils;



import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Properties;
public class sendMail {
    public static void send(Map<String, String> data) throws MessagingException {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.ssl.protocols", "TLSv1.2");
        props.put("mail.smtp.host", "smtp.mailtrap.io");
        props.put("mail.smtp.port", "2525");
        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("3b5e98c2033924", "1b5ae4c8833f2f");
            }
        });
        String directoryPath = "."; // Current directory

        try {
            // Get the directory path as a Path object
            Path dir = Paths.get(directoryPath);

            // Get a stream of all the files in the directory
            Files.list(dir)
                    .forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String htmlFilePath = ".\\src\\main\\java\\utils\\emailTemplate.html";
        try {
            String htmlContent = new String(Files.readAllBytes(Paths.get(htmlFilePath)));

            htmlContent = htmlContent.replace("[titlePlaceholder]", data.get("titlePlaceholder"));
            htmlContent = htmlContent.replace("[msgPlaceholder]", data.get("msgPlaceholder"));
            htmlContent = htmlContent.replace("[codePlaceholder]",data.get("emailSubject"));
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("Contact@esprit.tn"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("toumi.mohamedamine@esprit.tn"));
            message.setSubject(data.get("emailSubject"));
            // message.setText("Hello, this is a test email from JavaFX.");

            MimeBodyPart htmlBodyPart = new MimeBodyPart();
            htmlBodyPart.setContent(htmlContent, "text/html");

            String pdfFilePath = "./output.pdf";
            MimeBodyPart attachmentBodyPart = new MimeBodyPart();
            DataSource source = new FileDataSource(pdfFilePath);
            attachmentBodyPart.setDataHandler(new DataHandler(source));
            attachmentBodyPart.setFileName("recu.pdf");
            Multipart multipart = new MimeMultipart();

            multipart.addBodyPart(htmlBodyPart);
            multipart.addBodyPart(attachmentBodyPart);

            message.setContent(multipart);
            message.setContent(multipart,"pdf");
            Transport.send(message);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public static void main(String[] args)
    {
        Map<String, String> data = new HashMap<>();
        data.put("titlePlaceholder", "Email Title");
        data.put("msgPlaceholder", "Hello, this is the email message.");
        data.put("emailSubject", "Test Email with Attachment");

        try {
            // Call the sendMail.send() method with the data map
            sendMail.send(data);
            System.out.println("Email sent successfully!");
        } catch (MessagingException e) {
            System.out.println("Failed to send email: " + e.getMessage());
        }
    }

}
