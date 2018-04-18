package ua.epam.spring.hometask.discount;

import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.domain.User;

import java.time.LocalDateTime;

public interface DiscountStrategy {

    byte getDiscountPercent(User user, Event event, LocalDateTime airDateTime, long numberOfTickets);
}
