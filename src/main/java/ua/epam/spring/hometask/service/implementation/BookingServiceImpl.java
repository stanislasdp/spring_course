package ua.epam.spring.hometask.service.implementation;

import ua.epam.spring.hometask.dao.EventRepository;
import ua.epam.spring.hometask.dao.UserRepository;
import ua.epam.spring.hometask.domain.Auditorium;
import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.domain.Ticket;
import ua.epam.spring.hometask.domain.User;
import ua.epam.spring.hometask.service.BookingService;
import ua.epam.spring.hometask.service.EventService;
import ua.epam.spring.hometask.service.UserService;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

public class BookingServiceImpl implements BookingService {
    private static final double VIP_PRICE_COEFF = 1.5;

    private EventService eventService;
    private UserService userService;
    private UserRepository userRepository;
    private EventRepository eventRepository;

    @Override
    public double getTicketsPrice(Event event, LocalDateTime dateTime, User user, Set<Long> seats) {
        Event plannedEvent = eventService.getByName(event.getName());
        if (isNull(plannedEvent)) {
            throw new RuntimeException("No event");
        }

        User plannedUser = userService.getUserByEmail(user.getEmail());
        if (isNull(plannedUser)) {
            throw new RuntimeException("No user");
        }

        if (!plannedEvent.getAirDates().contains(dateTime)) {
            throw new RuntimeException("No event on specified ");
        }
        Set<Long> allSeats = plannedEvent
            .getAuditoriums()
            .get(dateTime)
            .getAllSeats();

        Set<Long> vipSeats = plannedEvent
            .getAuditoriums()
            .get(dateTime)
            .getVipSeats();

        double baseEventPrice = event.getBasePrice() * event.getRating().getRatingCoeff();

        double allPrice = 0;
        for (Long seat : seats) {
            if (vipSeats.contains(seat)) {
                allPrice += baseEventPrice * VIP_PRICE_COEFF;
            } else if (allSeats.contains(seat)) {
                allPrice += baseEventPrice;
            } else {
                throw new RuntimeException(String.format("There is no %s seat available", seat));
            }
        }
        return allPrice;
    }

    @Override
    public void bookTickets(Set<Ticket> tickets) {
        for (Ticket ticket : tickets) {
            Event event = ticket.getEvent();
            if (isNull(event.getAuditoriums().get(ticket.getDateTime()))) {
                throw new RuntimeException("no event with date/time on ticket");
            }

            Event savedEvent = eventRepository.getEventById(event.getId());
            if (isNull(savedEvent)) {
                throw new RuntimeException(String.format("no such event with id %s", event.getId()));
            }
            eventRepository.removeEvent(event);
            Set<Long> seats = event.getAuditoriums().get(ticket.getDateTime()).getAllSeats();
            seats.remove(ticket.getSeat());
            eventRepository.saveEvent(event);

            User existingUser = userRepository.getUserByEmail(ticket.getUser().getEmail());
            if (nonNull(existingUser)) {
                existingUser.getTickets().addAll(tickets);
                userRepository.remove(existingUser);
                userRepository.save(existingUser);
            }
        }
    }

    @Override
    public Set<Ticket> getPurchasedTicketsForEvent(Event event, LocalDateTime dateTime) {
        Auditorium auditorium = event.getAuditoriums().get(dateTime);
        if (isNull(auditorium)) {
            throw new RuntimeException("no such event with passed date/time");
        }
        Set<Ticket> eventTickets = new HashSet<>();
        Collection<User> users = userRepository.getAllUsers();
        for (User user : users) {
            for (Ticket ticket :  user.getTickets()) {
                if (ticket.getEvent().equals(event)) {
                    eventTickets.add(ticket);
                }
            }
        }
        return eventTickets;
    }

    public void setEventService(EventService eventService) {
        this.eventService = eventService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void setEventRepository(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }
}
