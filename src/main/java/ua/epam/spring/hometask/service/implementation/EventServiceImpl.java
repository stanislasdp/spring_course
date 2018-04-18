package ua.epam.spring.hometask.service.implementation;

import ua.epam.spring.hometask.dao.EventRepository;
import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.service.EventService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class EventServiceImpl implements EventService {

    private EventRepository eventRepository;

    public EventServiceImpl(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Override
    public Event getByName(String name) {
        return eventRepository.getEventByName(name);
    }

    @Override
    public Set<Event> getForDateRange(LocalDate from, LocalDate to) {
        return eventRepository.getAllEvents().stream()
            .filter(event -> event.airsOnDates(from, to))
            .collect(Collectors.toSet());
    }

    @Override
    public Set<Event> getNextEvents(LocalDateTime to) {
        return eventRepository.getAllEvents().stream()
            .filter(event -> {
               List<LocalDateTime> eventDates =  event.getAirDates().stream()
                    .filter(localDateTime -> localDateTime.isBefore(to))
                    .collect(Collectors.toList());
               return eventDates.size() == 0;
            }).collect(Collectors.toSet());
    }

    @Override
    public Event save(Event event) {
        eventRepository.saveEvent(event);
        return eventRepository.getEventByName(event.getName());
    }

    @Override
    public void remove(Event object) {
        eventRepository.removeEvent(object);
    }

    @Override
    public Event getById(Long id) {
        return eventRepository.getEventById(id);
    }

    @Override
    public Collection<Event> getAll() {
        return eventRepository.getAllEvents();
    }
}
