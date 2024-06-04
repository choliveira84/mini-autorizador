package br.com.vr.miniautorizador.application.interfaces.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import br.com.vr.miniautorizador.application.dto.TransacaoPostDTO;
import br.com.vr.miniautorizador.application.service.CartaoService;
import br.com.vr.miniautorizador.domain.exceptions.CartaoInexistenteException;
import br.com.vr.miniautorizador.domain.exceptions.SaldoInsuficienteException;
import br.com.vr.miniautorizador.domain.exceptions.SenhaInvalidaException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TransacaoControllerTest {

    private CartaoService cartaoService;
    private TransacaoController transacaoController;

    @BeforeEach
    public void setUp() {
        cartaoService = Mockito.mock(CartaoService.class);
        transacaoController = new TransacaoController(cartaoService);
    }

    @Test
    void testRealizarTransacaoCartaoInexistente() {
        // Given
        TransacaoPostDTO transacao = new TransacaoPostDTO();
        Mockito.doThrow(new CartaoInexistenteException()).when(cartaoService).transacionar(transacao);

        // When
        ResponseEntity<String> response = transacaoController.realizarTransacao(transacao);

        // Then
        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, response.getStatusCode());
        assertEquals("CARTAO_INEXISTENTE", response.getBody());
    }

    @Test
    void testRealizarTransacaoSaldoInsuficiente() {
        // Given
        TransacaoPostDTO transacao = new TransacaoPostDTO();
        Mockito.doThrow(new SaldoInsuficienteException()).when(cartaoService).transacionar(transacao);

        // When
        ResponseEntity<String> response = transacaoController.realizarTransacao(transacao);

        // Then
        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, response.getStatusCode());
        assertEquals("SALDO_INSUFICIENTE", response.getBody());
    }

    @Test
    void testRealizarTransacaoSenhaInvalida() {
        // Given
        TransacaoPostDTO transacao = new TransacaoPostDTO();
        Mockito.doThrow(new SenhaInvalidaException()).when(cartaoService).transacionar(transacao);

        // When
        ResponseEntity<String> response = transacaoController.realizarTransacao(transacao);

        // Then
        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, response.getStatusCode());
        assertEquals("SENHA_INVALIDA", response.getBody());
    }

    @Test
    void testRealizarTransacaoSucesso() {
        // Given
        TransacaoPostDTO transacao = new TransacaoPostDTO();

        // When
        ResponseEntity<String> response = transacaoController.realizarTransacao(transacao);

        // Then
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("OK", response.getBody());
    }
}
