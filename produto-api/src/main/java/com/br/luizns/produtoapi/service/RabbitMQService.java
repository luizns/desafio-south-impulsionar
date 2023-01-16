package com.br.luizns.produtoapi.service;

import com.br.luizns.produtoapi.config.RabbitMQConnection;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQService {

    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private ObjectMapper objectMapper;

    private static final String PRODUTO_EVENT_HEADER_NAME = "PRODUCT_CHANGED";

    public void enviarMensagem(Object object) {
        try {
            String objectJson = this.objectMapper.writeValueAsString(object);

            MessagePostProcessor messagePostProcessor = message -> {
                MessageProperties messageProperties = message.getMessageProperties();
                messageProperties.setHeader("EVENTO",PRODUTO_EVENT_HEADER_NAME);
                return  message;
            };
            this.rabbitTemplate.convertAndSend(RabbitMQConnection.NOME_EXCHANGE, RabbitMQConnection.ROUTING_KEY, objectJson,messagePostProcessor);

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

}