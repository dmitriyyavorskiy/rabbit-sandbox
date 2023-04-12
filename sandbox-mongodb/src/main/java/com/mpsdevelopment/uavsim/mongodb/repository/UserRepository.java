package com.mpsdevelopment.uavsim.mongodb.repository;

import com.mpsdevelopment.uavsim.mongodb.entities.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends MongoRepository<User, Long> {

    Optional<User> findByLogin(String value);

    Optional<User> findByApiKey(String value);

    Optional<User> findByIdAndActiveTrue(Long id);

    Optional<User> findByLoginAndActiveTrue(String value);

    List<User> findAllByActiveIsTrue();

}
