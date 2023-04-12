package com.mpsdevelopment.uavsim.mongodb.config;

import com.mpsdevelopment.uavsim.mongodb.config.database.MongoSharedDockerContainer;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

@Slf4j
public abstract class IntegrationTest implements MongoSharedDockerContainer {

    @Autowired
    private List<MongoRepository<?, ?>> repositories;

    @AfterEach
    public void deleteAllData() {
        log.info("Deleting all data from repositories");
        repositories.forEach(CrudRepository::deleteAll);
    }

}
