package com.example.springcloudkafkaretry;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
