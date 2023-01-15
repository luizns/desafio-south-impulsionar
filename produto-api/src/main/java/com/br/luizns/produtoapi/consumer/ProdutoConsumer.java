package com.br.luizns.produtoapi.consumer;

import com.br.luizns.produtoapi.config.RabbitMQConnection;
import com.br.luizns.produtoapi.dto.ProdutoRequestDTO;
import com.br.luizns.produtoapi.mapper.ProdutoMapper;
import com.br.luizns.produtoapi.repository.ProdutoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.io.IOException;



@Component
public class ProdutoConsumer {
    @Autowired
    ProdutoRepository repository;

    @Autowired
    ObjectMapper objectMapper;

    @RabbitListener(queues = RabbitMQConnection.FILA_ESTOQUE)
    public void receive(@Payload Message<String> message) throws IOException {

        ProdutoRequestDTO request = objectMapper.readValue(message.getPayload(), ProdutoRequestDTO.class);
        var headers = message.getHeaders();
        var chaveHeader = String.valueOf(message.getHeaders().get("ultima"));

        if (headers.containsValue(chaveHeader)) {
            var produtoAlterado = ProdutoMapper.INSTANCE.entidadeParaDto(this.repository.save(ProdutoMapper.INSTANCE.dtoParaEntidade(request)));
            System.out.println(produtoAlterado);
        }

    }
}
