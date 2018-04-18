package ua.epam.spring.hometask.domain;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;
import java.util.NavigableSet;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;

/**
 * @author Yuriy_Tkach
 */
@Data
@EqualsAndHashCode(exclude = "tickets")
@Builder
public class User extends DomainObject {

    private String firstName;

    private String lastName;

    private String email;

    private Set<Ticket> tickets = new TreeSet<>();


}
