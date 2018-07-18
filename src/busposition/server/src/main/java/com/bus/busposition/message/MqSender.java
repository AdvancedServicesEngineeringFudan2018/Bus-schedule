package com.bus.busposition.message;

import com.bus.busposition.DTO.Position;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author zzy
 * @date 2018/7/17下午3:29
 */
@Component
public class MqSender{

    @Autowired
    private AmqpTemplate amqpTemplate;

    public void send(Position position){
        amqpTemplate.convertAndSend("myQueue",position);
    }
}
