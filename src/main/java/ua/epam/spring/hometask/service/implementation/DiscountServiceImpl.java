package ua.epam.spring.hometask.service.implementation;

import ua.epam.spring.hometask.discount.DiscountStrategy;
import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.domain.User;
import ua.epam.spring.hometask.service.DiscountService;

import java.time.LocalDateTime;
import java.util.List;

public class DiscountServiceImpl implements DiscountService {

    private List<DiscountStrategy> discountStrategies;

    @Override
    public byte getDiscount(User user, Event event, LocalDateTime airDateTime) {

       return (byte) discountStrategies.stream()
            .map(discountStrategy -> discountStrategy.getDiscountPercent(user, event, airDateTime, user.getTickets().size()))
           .mapToInt(value -> value).max().orElse(0);
    }

    public void setDiscountStrategies(List<DiscountStrategy> discountStrategies) {
        this.discountStrategies = discountStrategies;
    }
}
