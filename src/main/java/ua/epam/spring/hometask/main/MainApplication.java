package ua.epam.spring.hometask.main;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ua.epam.spring.hometask.domain.Auditorium;
import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.domain.Ticket;
import ua.epam.spring.hometask.domain.User;
import ua.epam.spring.hometask.service.AuditoriumService;
import ua.epam.spring.hometask.service.BookingService;

import ua.epam.spring.hometask.service.EventService;
import ua.epam.spring.hometask.service.UserService;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class MainApplication {

    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("application-context.xml");
        AuditoriumService auditoriumService = (AuditoriumService) applicationContext.getBean("auditoriumService");
        BookingService bookingService = (BookingService) applicationContext.getBean("bookingService");
        EventService eventService =  (EventService) applicationContext.getBean("eventService");
        UserService userService = (UserService) applicationContext.getBean("userService");
        Auditorium mathAuditorium = (Auditorium) applicationContext.getBean("mathAuditorium");

        LocalDateTime dateTime = LocalDateTime.now().truncatedTo(ChronoUnit.DAYS);

        User user = User.builder()
            .firstName("first")
            .lastName("last")
            .email("forempty@gmail.com")
            .tickets(new HashSet<>())
            .build();
        user.setId(1L);

        //save user
        userService.save(user);

        Event event = Event.builder()
            .name("first_concert")
            .basePrice(20.0)
            .airDates(ImmutableSet.of(dateTime))
            .auditoriums(ImmutableMap.of(dateTime, mathAuditorium))
            .build();
        event.setId(1L);
        eventService.save(event);

        Ticket ticket = Ticket.builder()
            .event(event)
            .dateTime(LocalDateTime.now().truncatedTo(ChronoUnit.DAYS))
            .seat(1)
            .user(user)
            .build();
        bookingService.bookTickets(ImmutableSet.of(ticket));

       Set<Ticket> purchasedTickets =  bookingService.getPurchasedTicketsForEvent(event, dateTime);
        System.out.println(purchasedTickets.size());


    }
}
