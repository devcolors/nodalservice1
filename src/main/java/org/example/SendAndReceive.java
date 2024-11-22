package org.example;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SendAndReceive implements CommandLineRunner {
    @Autowired
    private final RabbitTemplate rabbitTemplate;

    public SendAndReceive() {
        this.rabbitTemplate = new RabbitTemplate();
    }

    @Override
    public void run(String... args) throws InterruptedException {
        while (true) {
            System.out.println("S1: Sending 'ping'");
            try {
                rabbitTemplate.convertAndSend("amq.direct", "routing-key", "ping");
            } catch (Exception e) {
                System.out.print("Error sending message to RMQ: ");
                System.out.println(e.getMessage());
            }
            Thread.sleep(10000);
        }
    }

    @RabbitListener(queues = "myQueue2")
    public void listen(String message) {
        System.out.println("S1: Received message - " + message);
        if (message.equals("ping")) {
            rabbitTemplate.convertAndSend("amq.direct", "routing-key", "pong");
            System.out.println("S1: Sending 'pong'");
        }
    }
}
