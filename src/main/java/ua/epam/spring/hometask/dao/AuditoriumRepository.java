package ua.epam.spring.hometask.dao;

import com.google.common.collect.ImmutableSet;
import org.springframework.stereotype.Repository;
import ua.epam.spring.hometask.domain.Auditorium;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class AuditoriumRepository {

    private static Map<String, Auditorium> fakeAuditoriums = new HashMap<>();

    static {
        fakeAuditoriums.put("Math", Auditorium.builder().name("Math").numberOfSeats(10).vipSeats(ImmutableSet.of(1L, 2L, 3L)).build());
        fakeAuditoriums.put("Physics", Auditorium.builder().name("Physics").numberOfSeats(15).vipSeats(ImmutableSet.of(6L, 7L, 8L)).build());
    }

    public void saveAuditorium(Auditorium auditorium) {
        fakeAuditoriums.put(auditorium.getName(), auditorium);
    }

    Collection<Auditorium> getAllAuditoriums() {
        return fakeAuditoriums.values();
    }

    Auditorium getAuditoriumByName(String name) {
        return fakeAuditoriums.get(name);
    }
}
