package com.kafka.order.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kafka.order.constants.AppConstants;
import com.kafka.order.entity.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class OrderProducer {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void produceOrder(Order order){
        ObjectMapper mapper = new ObjectMapper();
        String orderJsonString = null;
        try {
            orderJsonString = mapper.writeValueAsString(order);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        System.out.println(
                String.format("##########\nProduced Order-> %s\n##########", orderJsonString));
        this.kafkaTemplate.send(AppConstants.ORDER_TOPIC, orderJsonString);
    }
}
