package br.com.vr.miniautorizador.domain.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class PrincipalException extends RuntimeException {

    public PrincipalException(String message, Throwable e) {
        super(message, e);
    }
}