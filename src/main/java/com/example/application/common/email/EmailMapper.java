package com.example.application.common.email;

import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

@Component
public final class EmailMapper {
    private final SpringTemplateEngine templateEngine;

    public EmailMapper(SpringTemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    public String getBodyEmail(ConfirmationEmail notification){
        final Context ctx = new Context();
        ctx.setVariable("name", notification.getName());
        ctx.setVariable("confirmationUrl", notification.getConfirmationUrl());
        return this.templateEngine.process("confirmationEmail.html",ctx);
    }
    public String getBodyEmail(ForgotPasswordEmail notification){
        final Context ctx = new Context();
        ctx.setVariable("name", notification.getName());
        ctx.setVariable("forgotPasswordUrl", notification.getForgotPasswordUrl());
        return this.templateEngine.process("forgotPassword.html",ctx);
    }

    public String getBodyEmail(PasswordChangedEmail notification){
        final Context ctx = new Context();
        ctx.setVariable("name", notification.getName());
        ctx.setVariable("frontendBaseUrl", notification.getFrontendBaseUrl());
        return this.templateEngine.process("passwordChanged.html",ctx);
    }

    public String getBodyEmail(ContactUsEmail notification){
        final Context ctx = new Context();
        ctx.setVariable("name", notification.getName());
        ctx.setVariable("email", notification.getEmail());
        ctx.setVariable("message", notification.getMessage());
        return this.templateEngine.process("contactUs.html",ctx);
    }

    public String getBodyEmail(BookingNotificationHotel notification){
        final Context ctx = new Context();
        ctx.setVariable("userEmail", notification.getUserEmail());
        ctx.setVariable("quests", notification.getGuests());
        ctx.setVariable("checkin", notification.getCheckin());
        ctx.setVariable("checkout", notification.getCheckout());
        return this.templateEngine.process("bookingNotificationHotel.html",ctx);
    }

    public String getBodyEmail(BookingNotificationUser notification){
        final Context ctx = new Context();
        ctx.setVariable("hotelName", notification.getHotelName());
        ctx.setVariable("numberOfRooms", notification.getNumberOfRooms());
        ctx.setVariable("checkin", notification.getCheckin());
        ctx.setVariable("checkout", notification.getCheckout());
        ctx.setVariable("address", notification.getAddress());
        return this.templateEngine.process("bookingNotificationUser.html",ctx);
    }
}
