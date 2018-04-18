package ua.epam.spring.hometask.dao;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.domain.EventRating;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class EventRepository {

    private static Map<String, Event> fakeEvents = new HashMap<>();

    static {

    }

    public Event getEventByName(String name) {
        return fakeEvents.get(name);
    }

    public void saveEvent(Event event) {
        fakeEvents.put(event.getName(), event);
    }

    public Collection<Event> getAllEvents() {
        return fakeEvents.values();
    }

    public void removeEvent(Event event) {
        fakeEvents.remove(event.getName());
    }

    public Event getEventById(Long id) {
        return fakeEvents.values().stream()
            .filter(event -> event.getId().equals(id)).findFirst().orElse(null);
    }
}
