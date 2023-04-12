package com.icemobile.rabbitsandbox.rabbit.config;

import com.icemobile.rabbitsandbox.rabbit.config.database.MongoSharedDockerContainer;
import com.icemobile.rabbitsandbox.rabbit.config.rabbit.RabbitSharedDockerContainer;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

@Slf4j
public abstract class IntegrationTest implements RabbitSharedDockerContainer {

//    @Autowired
//    private List<CrudRepository<?, ?>> repositories;

    @Autowired
    private RabbitAdmin rabbitAdmin;

    private List<String> allQueues;

    @PostConstruct
    public void getAllRabbitQueues() {
        allQueues = RabbitSharedDockerContainer.getAllQueues();
    }

    @AfterEach
    public void purgeQueues() {
        log.info("Purging all queues {}", allQueues);
        allQueues.forEach(rabbitAdmin::purgeQueue);
    }

//    @AfterEach
//    public void deleteAllData() {
//        log.info("Deleting all data from repositories");
//        long t1 = System.currentTimeMillis();
//        repositories.forEach(CrudRepository::deleteAll);
//        long t2 = System.currentTimeMillis();
//        log.info("Deleting all for {} ms", t2 - t1);
//
//    }

}
