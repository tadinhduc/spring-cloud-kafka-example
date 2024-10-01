package com.example.springcloudkafkaretry;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.util.retry.Retry;

import java.util.List;

@RestController
@Slf4j
public class TestController {
    @Autowired
    StreamBridge streamBridge;

    @GetMapping("test")
    public void test() {
        boolean sent = streamBridge.send("consumerTest-0-in", "test");
        log.info("sent: {}", sent);
    }

    public static void main(String[] args) {
        Flux.fromIterable(List.of(1, 2, 3, 4, 5))
                .filter(i -> i % 2 == 0)
                .map(i -> i * 2)
                .map(integer -> {
                    if (integer == 8) {
                        throw new RuntimeException("Error");
                    }
                    return integer;
                })
                .onErrorResume(e -> {
                    log.error("Error processing integer: {}", e.getMessage());
                    return Flux.empty();
                })
                .retryWhen(Retry.backoff(3, java.time.Duration.ofSeconds(2)))
                .log()
                .subscribe(System.out::println);
    }
}
