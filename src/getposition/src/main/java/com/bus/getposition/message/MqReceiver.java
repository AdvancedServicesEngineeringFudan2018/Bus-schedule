package com.bus.getposition.message;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;



@Slf4j
@Component
public class MqReceiver {
    @RabbitListener(queues = "myquesue")
    public void process(String  message)
    {
        log.info("MqReceiver: {}", message);
    }
}
