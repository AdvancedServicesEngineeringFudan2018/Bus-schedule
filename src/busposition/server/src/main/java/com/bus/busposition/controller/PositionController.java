package com.bus.busposition.controller;

import com.bus.busposition.DTO.Position;


import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author zzy
 * @date 2018/7/17下午11:04
 */
@RestController
@Slf4j
public class PositionController {

    @Autowired
    private AmqpTemplate amqpTemplate;

    String res = "";

    @PostMapping(value = "/sendposition")
    public String sendPosition(@RequestBody Position position) throws ParseException{


        //amqpTemplate.convertAndSend("myQueue",position);
        String positionstr="x:"+position.getX()+";y:"+position.getY()+";time:"+position.getTime();

//        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
//        log.info(sdf.parse(position.getTime())+"");
        JSONObject jsonObject = JSONObject.fromObject(position);
        log.info(jsonObject.toString());
        amqpTemplate.convertAndSend("myQueue",positionstr);
//        amqpTemplate.convertSendAndReceive("myQueue",positionstr);
        log.info(res);
        String result="{\"result\":\""+res+"\"}";
        return result;
    }

    @RabbitListener(queuesToDeclare = @Queue("resultQueue"))
    public void setResult(String message)
    {
        res = message;
    }
}
