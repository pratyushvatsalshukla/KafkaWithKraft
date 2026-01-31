package com.kafkaWith.KraftLearning.KafkaConfig;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopic {

    @Bean
    public NewTopic kafkaLearningTopic(){
        return TopicBuilder.name("kafkaLearningTopic")
//                .partitions(10)
                .build();
    }
}
