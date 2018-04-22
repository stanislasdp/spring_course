package ua.epam.spring.hometask.service;

import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.domain.User;

import java.time.LocalDateTime;

/**
 * @author Yuriy_Tkach
 */
public interface DiscountService {


    byte getDiscount(User user, Event event, LocalDateTime airDateTime);

}
