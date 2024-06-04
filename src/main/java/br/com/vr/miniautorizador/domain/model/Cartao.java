package br.com.vr.miniautorizador.domain.model;

import java.math.BigDecimal;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import br.com.vr.miniautorizador.domain.exceptions.SaldoInsuficienteException;
import br.com.vr.miniautorizador.domain.exceptions.SenhaInvalidaException;

@Entity
public class Cartao {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id", columnDefinition = "VARCHAR(36)")
    @Type(type = "uuid-char")
    private UUID id;

    @NotNull
    @Column(length = 16, nullable = false)
    private String numero;

    @NotNull
    @Column(nullable = false)
    private String senha;

    @NotNull
    @Column(nullable = false)
    private BigDecimal saldo;

    public UUID getId() {
        return id;
    }

    public String getNumero() {
        return numero;
    }

    public String getSenha() {
        return senha;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void verificarSenha(String senha) {
        if (!this.senha.equals(senha)) {
            throw new SenhaInvalidaException();
        }
    }

    public void subtrairSaldo(BigDecimal valor) {
        if ((this.saldo.subtract(valor).signum() == -1)) {
            throw new SaldoInsuficienteException();
        }

        this.saldo = this.saldo.subtract(valor);
    }

    public Cartao() {
    }

    public Cartao(String numero, String senha) {
        this.id = UUID.randomUUID();
        this.numero = numero;
        this.senha = senha;
        this.saldo = BigDecimal.valueOf(500).setScale(2);
    }

}
