package com.kafkaWith.KraftLearning.KafkaProducer;

import com.kafkaWith.KraftLearning.Entity.Student;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

@Service
@Slf4j
@RequiredArgsConstructor
public class JsonKafkaProducer {
    ObjectMapper objectMapper = new ObjectMapper() ;
    private final KafkaTemplate<String, String> kafkaTemplate ;

    public void sendMessage(Student message){
        String studentJson = objectMapper.writeValueAsString(message);
        log.info("Sending .... {}", studentJson);
        kafkaTemplate.send("kafkaLearningTopic",studentJson) ;
    }
}
