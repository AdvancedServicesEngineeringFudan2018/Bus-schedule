package com.bus.schedule.message;

import com.bus.schedule.Calculator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.text.ParseException;

@Slf4j
@Component
public class MqReceiver {
    Calculator calculator = new Calculator();
    @Autowired
    private AmqpTemplate amqpTemplate;
    @Value("classpath:static/bus_data.json")
    private Resource areaRes;
    //    @RabbitListener(queuesToDeclare = @Queue("myQueue"))
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue("myQueue"),
            exchange = @Exchange("MyExchange")
    ))
    public void process(String  message)
    {
        log.info("MqReceiver: {}", message);
        //获得
        //将输入的信息同计划做对比
        //获取当前位置信息
        String[] data = message.split(";");
        String[] currentpositionx = data[0].split(":");
        String x = currentpositionx[1];
        Long currentLon = Long.parseLong(x);
        String[] currentpositionxy = data[1].split(":");
        String y = currentpositionxy[1];
        Long currentLa = Long.parseLong(y);
        String currenttime = data[2].split(":",2)[1];
        Long planx = getPlanX();
        Long plany = getPlanY();
        String result = null;
        try {
            result = Calculator.calculator(currentLon, currentLa,planx, plany,currenttime, getScheduleTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        amqpTemplate.convertAndSend("resultQueue",result);
        //
        //
//        return "Fast";
    }

    public Long getPlanX()
    {
        return 50l;
    }
    public Long getPlanY()
    {
        return 50l;
    }

    public String getScheduleTime()
    {
        return "11:42:46";
    }


}

