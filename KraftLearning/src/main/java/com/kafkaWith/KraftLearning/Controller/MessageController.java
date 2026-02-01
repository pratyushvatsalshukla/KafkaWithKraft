package com.kafkaWith.KraftLearning.Controller;

import com.kafkaWith.KraftLearning.KafkaProducer.KafkaProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/kafka")
@RequiredArgsConstructor
public class MessageController {

    private final KafkaProducer kafkaProducer ;
    //http://localhost:8080/api/1/kafka/publish?message=hello kafka
    @GetMapping("/publish")
    public ResponseEntity<?> publishMessage(@RequestParam("message") String message){
        kafkaProducer.sendMessage(message);
        return new ResponseEntity<>("Message Sent....", HttpStatus.OK) ;
    }
}
