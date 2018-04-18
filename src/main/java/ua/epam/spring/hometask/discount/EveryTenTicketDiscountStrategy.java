package ua.epam.spring.hometask.discount;

import org.springframework.stereotype.Component;
import ua.epam.spring.hometask.dao.UserRepository;
import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.domain.Ticket;
import ua.epam.spring.hometask.domain.User;

import java.time.LocalDateTime;
import java.util.Objects;

import static java.util.Objects.nonNull;

@Component
public class EveryTenTicketDiscountStrategy implements DiscountStrategy {

    private static final int DISCOUNT_TICKETS_AMOUNT = 10;
    private static final byte DISCOUNT_PERCENT = 50;

    private UserRepository userRepository;

    public EveryTenTicketDiscountStrategy(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public byte getDiscountPercent(User user, Event event, LocalDateTime airDateTime, long numberOfTickets) {
        User dbUser = userRepository.getUserById(user.getId());
        long allTicketSize;
        if (nonNull(dbUser)) {
            allTicketSize = user.getTickets().size() + numberOfTickets;
        } else {
            allTicketSize = numberOfTickets;
        }

        if (allTicketSize == DISCOUNT_TICKETS_AMOUNT) {
            return DISCOUNT_PERCENT;
        }
        return 0;
    }
}
