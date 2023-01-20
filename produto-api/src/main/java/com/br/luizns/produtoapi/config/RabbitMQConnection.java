package com.br.luizns.produtoapi.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConnection {
    public static final String NOME_EXCHANGE = "produto.direct";
    public static final String FILA_ESTOQUE = "produto.estoque";
    public static final String ROUTING_KEY = "produto.routingKey";

    @Bean
    public DirectExchange directExchange(){
        return ExchangeBuilder.directExchange(NOME_EXCHANGE).durable(true).build();
    }

    @Bean
    public Queue filaprodutoQueue(){
        return QueueBuilder.durable(FILA_ESTOQUE ).build();
    }

    @Bean
    public Binding binding(Queue queue, DirectExchange directExchange) {
        return BindingBuilder.bind(queue).to(directExchange).with(ROUTING_KEY);
    }

}