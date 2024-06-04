package br.com.vr.miniautorizador.application.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import br.com.vr.miniautorizador.application.dto.CartaoDTO;
import br.com.vr.miniautorizador.application.dto.CartaoPostDTO;
import br.com.vr.miniautorizador.application.dto.TransacaoPostDTO;
import br.com.vr.miniautorizador.domain.exceptions.CartaoExistenteException;
import br.com.vr.miniautorizador.domain.exceptions.CartaoInexistenteException;
import br.com.vr.miniautorizador.domain.model.Cartao;
import br.com.vr.miniautorizador.helper.CartaoHelper;
import br.com.vr.miniautorizador.infrastructure.persistence.CartaoRepository;

class CartaoServiceImplTest {

    private CartaoRepository cartaoRepository;
    private CartaoServiceImpl cartaoService;

    @BeforeEach
    public void setUp() {
        cartaoRepository = mock(CartaoRepository.class);
        cartaoService = new CartaoServiceImpl(cartaoRepository);
    }

    @Test
    void testCriarCartaoComSucesso() {
        // Given
        Cartao cartao = CartaoHelper.criarCartao();
        CartaoPostDTO post = new CartaoPostDTO();
        post.setNumero(cartao.getNumero());
        post.setSenha(cartao.getSenha());

        when(cartaoRepository.encontrarPeloNumero(cartao.getNumero())).thenReturn(Optional.empty());

        // When
        CartaoDTO result = cartaoService.criar(post);

        // Then
        assertNotNull(result);
        assertEquals(cartao.getNumero(), result.getNumero());
        assertEquals(cartao.getSenha(), result.getSenha());
        verify(cartaoRepository, times(1)).encontrarPeloNumero(cartao.getNumero());
        verify(cartaoRepository, times(1)).criar(any(Cartao.class));
    }

    @Test
    void testCriarCartaoExistente() {
        // Given
        String numeroCartao = "1234567890123456";
        String senhaCartao = "1234";
        CartaoPostDTO cartaoPostDTO = new CartaoPostDTO();
        cartaoPostDTO.setNumero(numeroCartao);
        cartaoPostDTO.setSenha(senhaCartao);

        when(cartaoRepository.encontrarPeloNumero(numeroCartao))
                .thenReturn(Optional.of(new Cartao(numeroCartao, senhaCartao)));

        // When / Then
        assertThrows(CartaoExistenteException.class, () -> cartaoService.criar(cartaoPostDTO));
        verify(cartaoRepository, times(1)).encontrarPeloNumero(numeroCartao);
        verify(cartaoRepository, never()).criar(any(Cartao.class));
    }

    @Test
    void testTransacionarComSucesso() {
        // Given
        String numeroCartao = "1234567890123456";
        BigDecimal valorTransacao = new BigDecimal("50.00");

        TransacaoPostDTO transacaoPostDTO = new TransacaoPostDTO();
        transacaoPostDTO.setNumero(numeroCartao);
        transacaoPostDTO.setSenha("1234");
        transacaoPostDTO.setValor(valorTransacao);

        Cartao cartao = new Cartao(numeroCartao, "1234");
        when(cartaoRepository.encontrarPeloNumero(numeroCartao)).thenReturn(Optional.of(cartao));

        // When
        cartaoService.transacionar(transacaoPostDTO);

        // Then
        ArgumentCaptor<Cartao> cartaoCaptor = ArgumentCaptor.forClass(Cartao.class);
        verify(cartaoRepository, times(1)).encontrarPeloNumero(numeroCartao);
        verify(cartaoRepository, times(1)).transacionar(cartaoCaptor.capture());
        Cartao cartaoAtualizado = cartaoCaptor.getValue();
        assertEquals(BigDecimal.valueOf(450).setScale(2), cartaoAtualizado.getSaldo());
    }

    @Test
    void testTransacionarComCartaoInexistente() {
        // Given
        String numeroCartao = "1234567890123456";
        BigDecimal valorTransacao = new BigDecimal("50.00");

        TransacaoPostDTO transacaoPostDTO = new TransacaoPostDTO();
        transacaoPostDTO.setNumero(numeroCartao);
        transacaoPostDTO.setSenha("1234");
        transacaoPostDTO.setValor(valorTransacao);

        when(cartaoRepository.encontrarPeloNumero(numeroCartao)).thenReturn(Optional.empty());

        // When / Then
        assertThrows(CartaoInexistenteException.class, () -> cartaoService.transacionar(transacaoPostDTO));
        verify(cartaoRepository, times(1)).encontrarPeloNumero(numeroCartao);
        verify(cartaoRepository, never()).transacionar(any(Cartao.class));
    }
}
