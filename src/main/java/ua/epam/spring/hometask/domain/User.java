package ua.epam.spring.hometask.domain;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Set;

/**
 * @author Yuriy_Tkach
 */
@Data
@Builder
@EqualsAndHashCode(exclude = "tickets")
public class User extends DomainObject {

    private String firstName;

    private String lastName;

    private String email;

    private Set<Ticket> tickets;


}
