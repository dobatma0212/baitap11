package com.dado.project24162025.util;

import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import java.security.SecureRandom;
import java.util.Properties;

public class MailUtil_24162025 {

    private static final String SMTP_HOST = "smtp.gmail.com";
    private static final String SMTP_PORT = "587";
    private static final String SMTP_USER = "example@gmail.com";
    private static final String SMTP_PASS = "app password";

    public static String generateOtp() {
        SecureRandom random = new SecureRandom();
        int otp = 100000 + random.nextInt(900000);
        return String.valueOf(otp);
    }

    public static void sendOtpMail(String toEmail, String otpCode) {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", SMTP_HOST);
        props.put("mail.smtp.port", SMTP_PORT);

        Session session = Session.getInstance(props, new jakarta.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(SMTP_USER, SMTP_PASS);
            }
        });

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(SMTP_USER, "Online Shop - De 06"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject("Ma xac thuc OTP - Kich hoat tai khoan");
            message.setText("Xin chao,\n\nMa OTP kich hoat tai khoan cua ban la: " + otpCode
                    + "\nMa co hieu luc trong 5 phut.\n\nTran trong,\nOnline Shop - De 06.");
            Transport.send(message);
        } catch (Exception e) {

            System.out.println("[MailUtil_24162025] Khong gui duoc mail that, OTP debug cho " + toEmail + " la: " + otpCode);
            e.printStackTrace();
        }
    }
}
