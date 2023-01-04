package com.br.luizns.produtoapi.resources.exceptions;

import lombok.*;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.persistence.Entity;
import java.io.Serializable;
import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StandardError implements Serializable {
    private static final long serialVersionUID = 1L;

    private Instant timestamp;
    private Integer status;
    private String error;
    private String message;
    private String path;

}
