package com.kafkaWith.KraftLearning.KafkaConsumer;

import com.kafkaWith.KraftLearning.Entity.Student;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaConsumer {

    private final ObjectMapper jsonMapper ;

    @KafkaListener(topics = "kafkaLearningTopic", groupId = "myGroup")
    public void consumeMessage(String message){
        if (isJson(message)) {
            try {
                Student event = jsonMapper.readValue(message, Student.class);
                log.info("JSON Object → {}",event);
            } catch (Exception e) {
                log.info("Invalid JSON format {}", message);
            }
        } else {
            log.info("Plain String → {}",message);
        }
        log.info("Receied Message : {}", message);
    }

    private boolean isJson(String msg) {
        msg = msg.trim();
        return msg.startsWith("{") || msg.startsWith("[");
    }

}
