package br.com.vr.miniautorizador.infrastructure.persistence;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import br.com.vr.miniautorizador.domain.model.Cartao;
import br.com.vr.miniautorizador.infrastructure.persistence.jpa.CartaoRepositoryJPA;

@Repository
class CartaoRepositoryImpl implements CartaoRepository {

    Logger logger = LoggerFactory.getLogger(CartaoRepositoryImpl.class);

    private final CartaoRepositoryJPA cartaoRepositoryJPA;

    public CartaoRepositoryImpl(CartaoRepositoryJPA cartaoRepositoryJPA) {
        this.cartaoRepositoryJPA = cartaoRepositoryJPA;
    }

    @Override
    public void criar(Cartao cartao) {
        cartaoRepositoryJPA.save(cartao);
    }

    @Override
    public Optional<Cartao> encontrarPeloNumero(String numero) {
        return cartaoRepositoryJPA.findByNumero(numero);
    }

    @Override
    public void transacionar(Cartao cartao) {
        cartaoRepositoryJPA.save(cartao);
    }

}
