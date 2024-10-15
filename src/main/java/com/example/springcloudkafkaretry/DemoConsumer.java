package com.example.springcloudkafkaretry;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.backoff.BackOff;
import org.springframework.util.backoff.FixedBackOff;

import java.util.function.Consumer;

@Slf4j
@Component
public class DemoConsumer {


    @Bean
    public Consumer<String> consumerTest() {
        return o -> {
            log.info("Consumed {}", o);
//            throw new ProcessingException();
        };
    }

//    @Bean
//    public ConcurrentKafkaListenerContainerFactory<String, Object> greetingKafkaListenerContainerFactory() {
//        ConcurrentKafkaListenerContainerFactory<String, Object> factory = new ConcurrentKafkaListenerContainerFactory<>();
//        // Other configurations
//        factory.setCommonErrorHandler(errorHandler());
//        factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.RECORD);
//        return factory;
//    }
//
//    public DefaultErrorHandler errorHandler() {
//        BackOff fixedBackOff = new FixedBackOff(1000, 3);
//        DefaultErrorHandler errorHandler = new DefaultErrorHandler((consumerRecord, exception) -> {
//            // logic to execute when all the retry attemps are exhausted
//            log.info("consumerRecord {}", consumerRecord);
//            log.info("exception {}", exception);
//        }, fixedBackOff);
//        return errorHandler;
//    }
}
