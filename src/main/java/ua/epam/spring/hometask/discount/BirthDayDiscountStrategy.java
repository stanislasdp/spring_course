package ua.epam.spring.hometask.discount;

import org.springframework.stereotype.Component;
import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.domain.Ticket;
import ua.epam.spring.hometask.domain.User;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Component
public class BirthDayDiscountStrategy implements DiscountStrategy {

    private static final int DISCOUNT_NUMBER_DAYS = 5;
    private static final byte DISCOUNT_PERCENT_AMOUNT = 5;


    @Override
    public byte getDiscountPercent(User user, Event event, LocalDateTime airDateTime, long numberOfTickets) {
       Ticket ticket = user.getTickets().stream()
            .filter(tk -> tk.getDateTime().equals(airDateTime))
            .findFirst()
           .orElseThrow(() -> new RuntimeException("no such user ticket for passed date time"));
        LocalDate ticketDate = ticket.getDateTime().toLocalDate();
        if (airDateTime.toLocalDate().plus(DISCOUNT_NUMBER_DAYS, ChronoUnit.DAYS).compareTo(ticketDate) <= 0) {
            return DISCOUNT_PERCENT_AMOUNT;
        }
        return 0;
    }
}
