package com.example.springdemo.controller;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;

@RestController
public class SpringKafkaController {
    Logger logger = LoggerFactory.getLogger(SpringKafkaController.class);

    @Resource
    private KafkaTemplate<String, String> kafkaTemplate;

    @RequestMapping("/kafka/send")
    public String sendMessage() {
        List<String> list = new ArrayList<>();
        kafkaTemplate.send("test-topic", "test");
        return "success";
    }

    @KafkaListener(topics = {"test-topic"})
    public void listen(List<ConsumerRecord<String, String>> records) {
        logger.info("receive message");

        records.forEach(ConsumerRecord -> {
            logger.info("topic: {}, partition: {}, offset: {}, key: {}, value: {}", ConsumerRecord.topic(), ConsumerRecord.partition(), ConsumerRecord.offset(), ConsumerRecord.key(), ConsumerRecord.value());
        });
    }
}
