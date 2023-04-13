package com.icemobile.rabbitsandbox.rabbit.service;

import com.icemobile.rabbitsandbox.commons.dto.UserDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

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

    public UserDto getUser(String login) {
        return users.get(login);
    }

    public int getUserCount() {
        return users.size();
    }

    public Collection<UserDto> getUsers() {
        return users.values();
    }
}
