package com.example.springboot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@SpringBootApplication
public class Application {
	@Autowired
	private SqlController sqlController;

	private static final Logger LOGGER = LoggerFactory.getLogger(Application.class);

	public static void main(String[] args) {

		ApplicationContext ctx = SpringApplication.run(Application.class, args);
	}

	@EventListener(ApplicationReadyEvent.class)
	public void EventListenerExecute(){
		System.out.println("Application Ready Event is successfully Started");
		String redisHost = System.getenv("REDIS_HOST");
		String redisPortStr = System.getenv("REDIS_PORT");
		int redisPort = Integer.parseInt(redisPortStr);
		String queueKey = System.getenv("QUEUE_KEY");
		ActionController actionCtr = new ActionController(sqlController);
		actionCtr.setConnectionRedis(redisHost, redisPort);
		actionCtr.listenToRedisAndSaveToDb(queueKey);
	}
}
