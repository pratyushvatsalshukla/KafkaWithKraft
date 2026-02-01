package com.kafkaWith.KraftLearning.KafkaConsumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class KafkaConsumer {

    @KafkaListener(topics = "kafkaLearningTopic", groupId = "myGroup")
    public void consumeMessage(String message){
        log.info("Receied Message : {}", message);
    }
}
