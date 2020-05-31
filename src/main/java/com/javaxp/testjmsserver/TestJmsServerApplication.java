package com.javaxp.testjmsserver;

import java.time.LocalDateTime;
import java.util.Timer;
import java.util.TimerTask;

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
		Timer t = new Timer();
		t.schedule(new TimerTask() {
			@Override
			public void run() {
				LocalDateTime time = LocalDateTime.now();
				System.out.println("Sending message from server at " + time);
				TestJmsServerApplication.this.jmsMessagingTemplate.convertAndSend(TestJmsServerApplication.this.queue,
						"Hello JMS at " + time);

			}
		}, 0, 2000);

	}

}
