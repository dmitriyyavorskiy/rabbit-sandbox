package com.mpsdevelopment.uavsim.mongodb;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.mpsdevelopment.uavsim.mongodb.config.IntegrationTest;
import com.mpsdevelopment.uavsim.mongodb.config.annotations.SpringBootDockerTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertTrue;

@Slf4j
@SpringBootDockerTest
public class IdGeneratorTest extends IntegrationTest {

	private List<Long> ids = Collections.synchronizedList(new ArrayList<Long>());

	private static final int THREAD_COUNT = 20;

	private int threadFinished = 0;
	@Autowired
	private IdGenerator generator;


	@Test
	public void testNextId() {
		List<Thread> threads = new ArrayList<Thread>(THREAD_COUNT);
		for (int i = 0; i < THREAD_COUNT; i++) {
			threads.add(new Thread(new GeneratorThread()));
		}
		for (Thread thread : threads) {
			thread.start();
			log.info("Thread {} started", thread.getName());
		}
		while (threadFinished < THREAD_COUNT) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				log.error("", e);
			}
		}
	}

	public class GeneratorThread implements Runnable {

		@Override
		public void run() {
			for (int i = 0; i < 1000; i++) {
				long id = generator.nextId();
				if (ids.contains(id)){
					assertTrue(false);
				} else {
					ids.add(id);
				}
			}
			threadFinished++;
		}
	}
}
