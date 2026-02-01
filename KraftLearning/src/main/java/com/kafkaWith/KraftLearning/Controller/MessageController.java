package com.kafkaWith.KraftLearning.Controller;

import com.kafkaWith.KraftLearning.Entity.Student;
import com.kafkaWith.KraftLearning.KafkaProducer.JsonKafkaProducer;
import com.kafkaWith.KraftLearning.KafkaProducer.KafkaProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/kafka")
@RequiredArgsConstructor
@Slf4j
public class MessageController {

    private final KafkaProducer kafkaProducer ;
    private final JsonKafkaProducer jsonKafkaProducer ;
    //http://localhost:8080/api/1/kafka/publish?message=hello kafka
    @GetMapping("/publish")
    public ResponseEntity<?> publishMessage(@RequestParam("message") String message){
        kafkaProducer.sendMessage(message);
        return new ResponseEntity<>("Message Sent....", HttpStatus.OK) ;
    }

    @PostMapping("/publish")
    public ResponseEntity<?> publishJsonMessage(@RequestBody Student student){
        log.info("Sending Message .....");
        jsonKafkaProducer.sendMessage(student);
        log.info("Message Sent....");
        return new ResponseEntity<>("Message Sent....", HttpStatus.OK) ;
    }
}
