package br.com.vr.miniautorizador.infrastructure.persistence.jpa;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.vr.miniautorizador.domain.model.Cartao;

public interface CartaoRepositoryJPA extends JpaRepository<Cartao, UUID> {
 
    Optional<Cartao> findByNumero(String numero);
}
