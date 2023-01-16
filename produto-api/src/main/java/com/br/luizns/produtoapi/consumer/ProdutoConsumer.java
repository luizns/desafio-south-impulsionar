package com.br.luizns.produtoapi.consumer;

import com.br.luizns.produtoapi.config.RabbitMQConnection;
import com.br.luizns.produtoapi.entity.Produto;
import com.br.luizns.produtoapi.mapper.ProdutoMapper;
import com.br.luizns.produtoapi.repository.ProdutoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.io.IOException;


@Slf4j
@Component
public class ProdutoConsumer {
    @Autowired
    ProdutoRepository repository;

    @Autowired
    ObjectMapper objectMapper;

    @RabbitListener(queues = RabbitMQConnection.FILA_ESTOQUE)
    public void receive(@Payload Message<String> message) throws IOException {

        Produto request = objectMapper.readValue(message.getPayload(), Produto.class);
        var headers = message.getHeaders();
        var chaveHeader = String.valueOf(message.getHeaders().get("EVENTO"));

        if (headers.containsValue(chaveHeader)) {
            var produtoAlterado = ProdutoMapper.INSTANCE.entidadeParaDto(this.repository.save(request));
            log.info(produtoAlterado.toString());

        }

    }
}
