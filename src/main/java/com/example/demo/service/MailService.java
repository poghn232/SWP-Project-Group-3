// src/main/java/com/example/demo/service/MailService.java

package com.example.demo.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage; // Dùng cho email đơn giản (text thuần)
import org.springframework.mail.javamail.JavaMailSender; // Interface chính để gửi email
import org.springframework.mail.javamail.MimeMessageHelper; // Dùng để tạo email phức tạp (HTML, file đính kèm)
import org.springframework.stereotype.Service;

@Service
public class MailService {

    @Autowired
    private JavaMailSender javaMailSender; // Spring sẽ tự động inject bean này

    /**
     * Gửi một email với nội dung HTML.
     * @param toEmail Địa chỉ email người nhận.
     * @param subject Tiêu đề email.
     * @param htmlContent Nội dung email dưới dạng HTML.
     */
    public void sendHtmlEmail(String toEmail, String subject, String htmlContent) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8"); // true cho multipart, UTF-8 cho encoding

            helper.setFrom("your-gmail-account@gmail.com"); // Email người gửi
            helper.setTo(toEmail);
            helper.setSubject(subject);
            helper.setText(htmlContent, true); // true để đánh dấu nội dung là HTML

            javaMailSender.send(message);
            System.out.println("Email HTML đã được gửi thành công đến: " + toEmail);
        } catch (MessagingException | MailException e) {
            System.err.println("Lỗi khi gửi email HTML đến " + toEmail + ": " + e.getMessage());
            throw new RuntimeException("Không thể gửi email HTML.", e);
        }
    }

    /**
     * Gửi mã OTP (One-Time Password) đến địa chỉ email của người dùng.
     * @param toEmail Địa chỉ email người nhận OTP.
     * @param otpCode Mã OTP được tạo ra.
     */
    public void sendOtpEmail(String toEmail, String otpCode) {
        String subject = "Mã OTP đặt lại mật khẩu của bạn";
        String htmlContent = "<div style=\"font-family: Arial, sans-serif; line-height: 1.6; color: #333;\">"
                + "<h2 style=\"color: #0056b3;\">Yêu cầu đặt lại mật khẩu</h2>"
                + "<p>Xin chào,</p>"
                + "<p>Chúng tôi đã nhận được yêu cầu đặt lại mật khẩu cho tài khoản của bạn. Vui lòng sử dụng mã OTP dưới đây để hoàn tất quá trình:</p>"
                + "<h3 style=\"color: #d9534f; font-size: 24px; text-align: center; margin: 20px 0; padding: 10px; border: 1px dashed #d9534f; display: inline-block;\">" + otpCode + "</h3>"
                + "<p>Mã này sẽ hết hạn sau vài phút. Vui lòng không chia sẻ mã này với bất kỳ ai.</p>"
                + "<p>Nếu bạn không yêu cầu đặt lại mật khẩu, vui lòng bỏ qua email này hoặc liên hệ với bộ phận hỗ trợ.</p>"
                + "<p>Trân trọng,</p>"
                + "<p>Đội ngũ hỗ trợ của bạn</p>"
                + "</div>";
        sendHtmlEmail(toEmail, subject, htmlContent);
    }

    // Bạn có thể thêm các phương thức khác để gửi email có file đính kèm, v.v.
}
