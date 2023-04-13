package com.icemobile.rabbitsandbox.rabbit.service;

import com.icemobile.rabbitsandbox.commons.dto.UserDto;
import com.icemobile.rabbitsandbox.commons.exception.UserNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@Slf4j
public class UserService {

    private final Map<String, UserDto> users = new HashMap<>();

    public void createUser(UserDto user) {
        log.info("Adding user {}", user);
        users.put(user.getLogin(), user);
    }

    public void updateUser(UserDto user) {
        users.put(user.getLogin(), user);
    }

    public Optional<UserDto> getUser(String login) {
        return Optional.ofNullable(users.get(login));
    }

    public int getUserCount() {
        return users.size();
    }

    public Collection<UserDto> getUsers() {
        return users.values();
    }

    public UserDto deactivateUser(String login) throws UserNotFoundException {
        return getUser(login).map(user -> user.setActive(false)).orElseThrow(() -> new UserNotFoundException(login));
    }
}
