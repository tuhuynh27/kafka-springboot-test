package com.tuhuynh.kafka.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/test")
@Slf4j
public class TestController {
    private final KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    public TestController(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @GetMapping
    public String test() {
        return "Hello World";
    }

    @GetMapping("/kafka-send")
    public String kafkaSend() {
        kafkaTemplate.send("test", "Hello " + System.currentTimeMillis());
        return "OK";
    }

    @KafkaListener(topics = "test", groupId = "group-id")
    public void listen(String message) {
        log.info("Received: " + message);
    }
}
