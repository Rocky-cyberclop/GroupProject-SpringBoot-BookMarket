package com.groupproject.bookmarket.services;

import com.groupproject.bookmarket.models.*;
import com.groupproject.bookmarket.repositories.*;
import com.groupproject.bookmarket.requests.OrderRequest;
import com.groupproject.bookmarket.responses.*;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MailService {
    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private CodeTmpService codeTmpService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CartItemRepository cartItemRepository;
    @Value("${spring.mail.username}")
    private String fromMail;


    public void sendMail(String mail) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(fromMail);
        simpleMailMessage.setSubject("You valid code");
        simpleMailMessage.setText(codeTmpService.generateCodeTmp(mail));
        simpleMailMessage.setTo(mail);
        javaMailSender.send(simpleMailMessage);
    }
    public void sendDetailReceipt(OrderRequest orderRequest,Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            throw new RuntimeException("User not found");
        }
        User user = userOptional.get();
        OrderSummary orderSummary = getOrderSummary(orderRequest);
        if (orderSummary == null) {
            throw new RuntimeException("OrderSummary not found");
        }
        String emailContent = buildEmailContent(user, orderSummary);
        sendHtmlMail(user.getEmail(), emailContent);
    }

    private void sendHtmlMail(String to, String content) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setFrom(fromMail);
            helper.setTo(to);
            helper.setSubject("Your Order Information");
            helper.setText(content, true);

            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            throw new RuntimeException("Failed to send email", e);
        }
    }

    private String buildEmailContent(User user, OrderSummary orderSummary) {
        StringBuilder sb = new StringBuilder();
//        sb.append("<h1>Dear ").append(user.getFullName()).append(",</h1>")
        sb.append("<h1>Dear ").append(",</h1>")
                .append("<p>Thank you for your order! Here is your order information:</p>")
                .append("<table>")
                .append("<tr><th>Item</th><th>Quantity</th><th>Price</th></tr>");

        orderSummary.getItems().forEach(item ->
                sb.append("<tr>")
                        .append("<td>").append(item.getItemName()).append("</td>")
                        .append("<td>").append(item.getQuantity()).append("</td>")
                        .append("<td>").append(item.getPrice()).append("</td>")
                        .append("</tr>")
        );

        sb.append("</table>")
                .append("<p>Total Quantity: ").append(orderSummary.getTotalQuantity()).append("</p>")
                .append("<p>Total Amount: ").append(orderSummary.getTotalAmount()).append("</p>")
                .append("<p>Regards,</p>")
                .append("<p>Your Company Name</p>");

        return sb.toString();
    }

    public OrderSummary getOrderSummary(OrderRequest orderRequest) {
        List<CartItem> cartItems = cartItemRepository.findAllById(orderRequest.getCartItemIds());
        if (cartItems.isEmpty()) {
            return null;
        }
        List<ItemSummary> itemSummaries = cartItems.stream().map(cartItem ->
                new ItemSummary(cartItem.getBook().getTitle(),
                        cartItem.getQuantity(),
                        cartItem.getBook().getPrice())
        ).collect(Collectors.toList());

        long totalAmount = itemSummaries.stream()
                .mapToLong(item -> item.getPrice() * item.getQuantity())
                .sum();
        int totalQuantity = itemSummaries.stream()
                .mapToInt(ItemSummary::getQuantity)
                .sum();

        return new OrderSummary(itemSummaries, totalAmount, totalQuantity);
    }
}
