package br.com.vr.miniautorizador.application.service;

import java.math.BigDecimal;

import br.com.vr.miniautorizador.application.dto.CartaoPostDTO;
import br.com.vr.miniautorizador.application.dto.CartaoDTO;
import br.com.vr.miniautorizador.application.dto.TransacaoPostDTO;

public interface CartaoService {

    void transacionar(TransacaoPostDTO transacao);

    CartaoDTO criar(CartaoPostDTO cartao);

    BigDecimal encontrarPeloNumero(String numero);
}
