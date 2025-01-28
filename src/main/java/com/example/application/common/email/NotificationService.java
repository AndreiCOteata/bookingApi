package com.example.application.common.email;

import com.example.application.account.dao.dto.AccountDto;
import com.example.application.common.dtos.BookingEmailDto;
import com.example.application.common.dtos.ContactUsDto;
import com.example.application.config.security.model.CreateAccountRequest;
import io.swagger.annotations.Contact;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import javax.mail.MessagingException;

@Service
@Slf4j
public class NotificationService {
    private final EmailService emailService;
    private final EmailUtils emailUtils;
    private final String verifyEmailUrl;
    private final String forgotPasswordUrl;
    private final String frontendUrl;

    @Autowired
    public NotificationService(EmailService emailService, EmailUtils emailUtils, @Value("${app.frontend.verifyemail.url}") String verifyEmailUrl,
                               @Value("${app.frontend.forgotpassword.url}") String forgotPasswordUrl, @Value("${app.frontend.base.url}") String frontendUrl){
        this.emailService = emailService;
        this.emailUtils = emailUtils;
        this.verifyEmailUrl = verifyEmailUrl;
        this.forgotPasswordUrl = forgotPasswordUrl;
        this.frontendUrl = frontendUrl;
    }

    public void sendEmailConfirmation(CreateAccountRequest request) throws MessagingException {
        ConfirmationEmail email = new ConfirmationEmail();
        email.setName(request.getFirstName() + " " + request.getLastName());
        email.setConfirmationUrl(emailUtils.createUrlConfirmation(request.getEmail(),verifyEmailUrl));
        emailService.sendEmail(request.getEmail(), email);
    }

    public void resendEmailConfirmation(AccountDto accountDto) throws MessagingException {
        ConfirmationEmail email = new ConfirmationEmail();
        email.setName(accountDto.getFirstName() + " " + accountDto.getLastName());
        email.setConfirmationUrl(emailUtils.createUrlConfirmation(accountDto.getEmail(),verifyEmailUrl));
        emailService.sendEmail(accountDto.getEmail(), email);
    }

    public void sendForgotEmailPassword(AccountDto account) throws MessagingException {
        ForgotPasswordEmail email = new ForgotPasswordEmail();
        email.setName(account.getFirstName() + " " + account.getLastName());
        email.setForgotPasswordUrl(emailUtils.createResetPasswordEmailUrlConfirmation(account.getEmail(),forgotPasswordUrl));
        emailService.sendEmail(account.getEmail(), email);
    }

    public void sendPasswordChangedConfirmationEmail(AccountDto account) throws MessagingException {
        ForgotPasswordEmail email = new ForgotPasswordEmail();
        email.setName(account.getFirstName() + " " + account.getLastName());
        email.setForgotPasswordUrl(frontendUrl);
        emailService.sendEmail(account.getEmail(), email);
    }

    public void sendContactUsEmail(ContactUsDto contactUsDto) throws MessagingException {
        ContactUsEmail contactUsEmail = new ContactUsEmail();
        contactUsEmail.setName(contactUsDto.getName());
        contactUsEmail.setEmail(contactUsDto.getEmail());
        contactUsEmail.setMessage(contactUsDto.getMessage());
        emailService.sendEmail("sdaapp.email.sender@gmail.com", contactUsEmail);
    }

    public void sendBookingNotificationUser(BookingEmailDto bookingEmailDto) throws MessagingException {
        BookingNotificationUser bookingNotificationUser = new BookingNotificationUser();
        bookingNotificationUser.setHotelName(bookingEmailDto.getHotelName());
        bookingNotificationUser.setCheckin(bookingEmailDto.getCheckin());
        bookingNotificationUser.setCheckout(bookingEmailDto.getCheckout());
        bookingNotificationUser.setNumberOfRooms(bookingEmailDto.getNumbersOfRooms());
        bookingNotificationUser.setAddress(bookingEmailDto.getAddress());
        emailService.sendEmail(bookingEmailDto.getUserEmail(), bookingNotificationUser);
    }

    public void sendBookingNotificationHotel(BookingEmailDto bookingEmailDto) throws MessagingException {
        BookingNotificationHotel bookingNotificationHotel = new BookingNotificationHotel();
        bookingNotificationHotel.setUserEmail(bookingEmailDto.getUserEmail());
        bookingNotificationHotel.setCheckin(bookingEmailDto.getCheckin());
        bookingNotificationHotel.setCheckout(bookingEmailDto.getCheckout());
        bookingNotificationHotel.setGuests(bookingEmailDto.getGuests());
        emailService.sendEmail(bookingEmailDto.getHotelEmail(), bookingNotificationHotel);
    }

}
