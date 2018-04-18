package ua.epam.spring.hometask.service.implementation;

import ua.epam.spring.hometask.domain.Auditorium;
import ua.epam.spring.hometask.service.AuditoriumService;

import java.util.Set;

public class AuditoriumServiceImpl implements AuditoriumService {

    private Set<Auditorium> auditoriums;

    @Override
    public Set<Auditorium> getAll() {
        return auditoriums;
    }

    @Override
    public Auditorium getByName(String name) {
        return auditoriums.stream()
            .filter(auditorium -> auditorium.getName().equals(name))
            .findFirst()
            .orElse(null);
    }

    public void setAuditoriums(Set<Auditorium> auditoriums) {
        this.auditoriums = auditoriums;
    }
}
