package com.javaxp.testjmsserver;

import java.time.LocalDateTime;

import javax.jms.Queue;

import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.core.JmsMessagingTemplate;

@SpringBootApplication
@EnableJms
public class TestJmsServerApplication implements CommandLineRunner {

	@Autowired
	private JmsMessagingTemplate jmsMessagingTemplate;

	@Autowired
	private Queue queue;

	public static void main(String[] args) {
		SpringApplication.run(TestJmsServerApplication.class, args);
	}

	@Bean
	public Queue queue() {
		return new ActiveMQQueue("myQueue");
	}

	@Override
	public void run(String... args) throws Exception {
		while(true) {
			Thread.sleep(2000);
			LocalDateTime time = LocalDateTime.now();
			System.out.println("Sending message from server at " + time);
			this.jmsMessagingTemplate.convertAndSend(this.queue,
					"Hello JMS at " + time);
		}
	}
}
