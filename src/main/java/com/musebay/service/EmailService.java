package com.musebay.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendVerificationEmail(String to, String subject, String content) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(content, true); // HTML 허용

        mailSender.send(message);
    }
    public void sendLoginInfoEmail(String toEmail, String id, String password) throws MessagingException {
        String subject = "Your MuseBay Login Info";

        String content = String.format("""
        <div style="font-family: Arial, sans-serif; line-height: 1.6;">
            <h2>🎉 You're officially a Muse!</h2>
            <p>The spotlight is on you — it's time to dazzle your audience.</p>
            <p><strong>🆔 ID:</strong> %s</p>
            <p><strong>🔑 Password:</strong> %s</p>
            <p>🎯 Start your journey here: 
                <a href="http://yourdomain.com/login" style="color: hotpink;">Log In</a>
            </p>
            <p>With excitement,<br/>Team MuseBay</p>
        </div>
        """, id, password);

        sendVerificationEmail(toEmail, subject, content);
    }
}
