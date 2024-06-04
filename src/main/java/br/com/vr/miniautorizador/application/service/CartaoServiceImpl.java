package br.com.vr.miniautorizador.application.service;

import java.math.BigDecimal;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import br.com.vr.miniautorizador.application.dto.CartaoDTO;
import br.com.vr.miniautorizador.application.dto.CartaoPostDTO;
import br.com.vr.miniautorizador.application.dto.TransacaoPostDTO;
import br.com.vr.miniautorizador.domain.exceptions.CartaoExistenteException;
import br.com.vr.miniautorizador.domain.exceptions.CartaoInexistenteException;
import br.com.vr.miniautorizador.domain.model.Cartao;
import br.com.vr.miniautorizador.infrastructure.persistence.CartaoRepository;

@Service
public class CartaoServiceImpl implements CartaoService {

    Logger logger = LoggerFactory.getLogger(CartaoServiceImpl.class);

    private final CartaoRepository cartaoRepository;

    public CartaoServiceImpl(CartaoRepository cartaoRepository) {
        this.cartaoRepository = cartaoRepository;
    }

    @Override
    public void transacionar(TransacaoPostDTO transacao) {
        Objects.requireNonNull(transacao, "A transação está inválida");

        logger.debug("Requisição para efetuar a transação para o cartão de número {}", transacao.getNumero());

        Cartao cartao = cartaoRepository.encontrarPeloNumero(transacao.getNumero())
                .orElseThrow(CartaoInexistenteException::new);

        synchronized (cartao) {
            cartao.subtrairSaldo(transacao.getValor());

            cartaoRepository.transacionar(cartao);
        }
    }

    @Override
    public CartaoDTO criar(CartaoPostDTO cartao) {
        Objects.requireNonNull(cartao, "O cartão está inválido");

        logger.debug("Requisição para criar o cartão de número {}", cartao.getNumero());

        if (cartaoRepository.encontrarPeloNumero(cartao.getNumero()).isPresent()) {
            throw new CartaoExistenteException();
        }

        cartaoRepository.criar(new Cartao(cartao.getNumero(), cartao.getSenha()));

        return new CartaoDTO(cartao.getNumero(), cartao.getSenha());
    }

    @Override
    public BigDecimal encontrarPeloNumero(String numero) {
        Objects.requireNonNull(numero, "O número do cartão está inválido");

        logger.debug("Requisição para encontrar o cartão de número {}", numero);

        return cartaoRepository.encontrarPeloNumero(numero).orElseThrow(CartaoInexistenteException::new).getSaldo();
    }

}
