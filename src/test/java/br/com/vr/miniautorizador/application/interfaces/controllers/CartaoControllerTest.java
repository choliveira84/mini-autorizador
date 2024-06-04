package br.com.vr.miniautorizador.application.interfaces.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;

import br.com.vr.miniautorizador.application.dto.CartaoDTO;
import br.com.vr.miniautorizador.application.dto.CartaoPostDTO;
import br.com.vr.miniautorizador.application.service.CartaoService;
import br.com.vr.miniautorizador.domain.exceptions.CartaoExistenteException;
import br.com.vr.miniautorizador.domain.exceptions.CartaoInexistenteException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CartaoControllerTest {

    private CartaoService cartaoService;
    private CartaoController cartaoController;

    @BeforeEach
    public void setUp() {
        cartaoService = Mockito.mock(CartaoService.class);
        cartaoController = new CartaoController(cartaoService);
    }

    @Test
    void testCriarCartao() {
        // Given
        CartaoPostDTO cartaoPostDTO = new CartaoPostDTO();
        cartaoPostDTO.setNumero("1234567890");
        cartaoPostDTO.setSenha("1234");
        CartaoDTO cartaoDTO = new CartaoDTO("1234567890", "1234");
        Mockito.when(cartaoService.criar(cartaoPostDTO)).thenReturn(cartaoDTO);

        // When
        ResponseEntity<CartaoDTO> response = cartaoController.criarCartao(cartaoPostDTO);

        // Then
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(cartaoDTO, response.getBody());
    }

    @Test
    void testCriarCartaoExistente() {
        // Given
        CartaoPostDTO cartaoPostDTO = new CartaoPostDTO();
        cartaoPostDTO.setNumero("1234567890");
        cartaoPostDTO.setSenha("1234");
        Mockito.when(cartaoService.criar(cartaoPostDTO)).thenThrow(new CartaoExistenteException());

        // When
        ResponseEntity<CartaoDTO> response = cartaoController.criarCartao(cartaoPostDTO);

        // Then
        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, response.getStatusCode());
    }

    @Test
    void testObterSaldo() {
        // Given
        String numeroCartao = "1234567890";
        BigDecimal saldo = BigDecimal.valueOf(500);
        Mockito.when(cartaoService.encontrarPeloNumero(numeroCartao)).thenReturn(saldo);

        // When
        ResponseEntity<BigDecimal> response = cartaoController.obterSaldo(numeroCartao);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(saldo, response.getBody());
    }

    @Test
    void testObterSaldoCartaoInexistente() {
        // Given
        String numeroCartao = "1234567890";
        Mockito.when(cartaoService.encontrarPeloNumero(numeroCartao)).thenThrow(new CartaoInexistenteException());

        // When
        ResponseEntity<BigDecimal> response = cartaoController.obterSaldo(numeroCartao);

        // Then
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
