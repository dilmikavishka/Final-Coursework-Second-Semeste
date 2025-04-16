package lk.ijse.heladivaproject.util;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class Mail {
    @Autowired
    private JavaMailSender mailSender;

    public void sendOrderConfirmation(String userEmail, String orderId, Double totalPrice, String productDetails) {
        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(userEmail);
            helper.setSubject("🛍️ Order Confirmation - " + orderId);

            String emailBody = "<div style='font-family: Arial, sans-serif; padding: 20px; color: #333; max-width: 600px; margin: auto;'>"
                    + "<h2 style='color: #007BFF; text-align: center;'>🎉 Thank You for Your Order!</h2>"
                    + "<p style='font-size: 16px;'>Dear Customer,</p>"
                    + "<p style='font-size: 16px;'>Your order <b>(ID: " + orderId + ")</b> has been placed successfully. Here’s your order summary:</p>"
                    + "<table style='width: 100%; border-collapse: collapse; margin-top: 20px; border: 1px solid #ddd;'>"
                    + "<thead>"
                    + "<tr style='background-color: #f8f9fa; text-align: left;'>"
                    + "<th style='padding: 12px; border-bottom: 2px solid #ddd;'>Product</th>"
                    + "<th style='padding: 12px; border-bottom: 2px solid #ddd;'>Quantity</th>"
                    + "<th style='padding: 12px; border-bottom: 2px solid #ddd;'>Price (LKR)</th>"
                    + "</tr>"
                    + "</thead>"
                    + "<tbody>"
                    + productDetails
                    + "</tbody>"
                    + "</table>"
                    + "<h3 style='color: #28a745; text-align: right; margin-top: 20px;'>💰 Total Price: LKR " + totalPrice + "</h3>"
                    + "<p style='font-size: 16px;'>📦 Your order is being processed and will be shipped soon.</p>"
                    + "<div style='text-align: center; margin-top: 20px;'>"
                    + "<a href='https://yourwebsite.com/track-order/" + orderId
                    + "' style='background-color: #007BFF; color: white; padding: 12px 20px; text-decoration: none; font-size: 16px; border-radius: 5px; display: inline-block;'>📍 Track Your Order</a>"
                    + "</div>"
                    + "<p style='font-size: 16px; margin-top: 20px;'>Thank you for shopping with us!<br><b>- Heladiva Team</b></p>"
                    + "</div>";

            helper.setText(emailBody, true);

            mailSender.send(message);
            System.out.println("Order confirmation email sent successfully to: " + userEmail);
        } catch (MessagingException e) {
            System.err.println("Error sending email: " + e.getMessage());
        }
    }

    public void sendOrderUpdate(String userEmail, String orderId, String status, String emailBody) {
        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(userEmail);
            helper.setSubject("📦 Order Update - " + orderId + " (" + status + ")");
            helper.setText(emailBody, true);
            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("Failed to send order update email", e);
        }
    }

    public void sendContactMessage(String senderName, String senderEmail, String senderMessage) {
        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo("raycooray32@gmail.com");
            helper.setSubject("📩 New Contact Message from " + senderName);

            String emailBody = "<div style='font-family: Arial, sans-serif; padding: 20px;'>"
                    + "<h3>New Contact Form Submission</h3>"
                    + "<p><strong>Name:</strong> " + senderName + "</p>"
                    + "<p><strong>Email:</strong> " + senderEmail + "</p>"
                    + "<p><strong>Message:</strong></p>"
                    + "<p>" + senderMessage + "</p>"
                    + "</div>";

            helper.setText(emailBody, true);
            mailSender.send(message);
            System.out.println("Contact form message sent successfully from: " + senderEmail);
        } catch (MessagingException e) {
            System.err.println("Error sending contact message: " + e.getMessage());
        }
    }


}
