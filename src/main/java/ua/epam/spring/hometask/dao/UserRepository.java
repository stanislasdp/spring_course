package ua.epam.spring.hometask.dao;

import ua.epam.spring.hometask.domain.User;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class UserRepository {

    private static Map<String, User> users = new HashMap<>();

    public void save(User user) {
        users.put(user.getEmail(), user);
    }

    public User getUserById(Long id) {
        return users.values().stream()
            .filter(user -> user.getId().equals(id))
            .findFirst()
            .orElse(null);
    }

    public void remove(User user) {
        users.remove(user.getEmail());
    }

    public Collection<User> getAllUsers() {
        return users.values();
    }

    public User getUserByEmail(String email) {
        return users.get(email);
    }





}
