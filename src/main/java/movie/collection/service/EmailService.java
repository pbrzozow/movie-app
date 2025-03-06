package movie.collection.service;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class EmailService implements EmailSender{

    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendEmail(String to, String subject, String link) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setFrom("test@example.com");
        helper.setTo(to);
        helper.setSubject(subject);

        String htmlContent = "<p>Hello,</p>"
                + "<p>Please click the link below to verify your account:</p>"
                + "<p><a href=\"" + link + "\">Verify Account</a></p>"
                + "<p>Thank you!</p>";

        helper.setText(htmlContent, true);

        mailSender.send(message);
    }
}
