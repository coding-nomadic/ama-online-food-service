package com.email.service.mailsender;

import com.email.service.models.OrderDtoResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
public class EmailSender {

    private final JavaMailSender mailSender;
    private static final String SENDER = "tnzngdw@gmail.com";
    private static final String UTF = "utf-8";

    @Autowired
    public EmailSender(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Async
    public void sendMail(OrderDtoResponse orderDtoResponse) {
        try {
            List<Long> prices = orderDtoResponse.getItems().stream().map(s -> Long.valueOf(s.getPrice())).collect(Collectors.toList());
            Long totalPrices = prices.stream().collect(Collectors.summingLong(Long::intValue));
            String emailText = "Thank you for ordering from Ama Food. Your order ID " + orderDtoResponse.getId() + " amounting $" + totalPrices + " CAD will be delivered shortly!";
            MimeMessage mimeMessage = getMimeMessage(emailText, orderDtoResponse);
            mailSender.send(mimeMessage);
            log.info("Email sent to the customer successfully for order ID: {}", orderDtoResponse.getId());
        } catch (MessagingException e) {
            log.error("Failed to send email for order ID: {}", orderDtoResponse.getId(), e);
            throw new IllegalArgumentException("Failed to send email for order ID: " + orderDtoResponse.getId(), e);
        }
    }

    private MimeMessage getMimeMessage(String emailText, OrderDtoResponse orderDtoResponse) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, UTF);
        helper.setText(emailText, true);
        helper.setTo(orderDtoResponse.getEmail());
        helper.setSubject("Ama Food Delivery Confirmation!");
        helper.setFrom(SENDER);
        return mimeMessage;
    }
}
