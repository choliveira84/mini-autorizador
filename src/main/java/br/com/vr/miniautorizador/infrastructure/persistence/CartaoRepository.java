package br.com.vr.miniautorizador.infrastructure.persistence;

import java.util.Optional;

import br.com.vr.miniautorizador.domain.model.Cartao;

public interface CartaoRepository {

    void criar(Cartao cartao);

    Optional<Cartao> encontrarPeloNumero(String numero);

    void transacionar(Cartao cartao);

}
