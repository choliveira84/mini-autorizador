package br.com.vr.miniautorizador.application.dto;

import java.math.BigDecimal;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class TransacaoPostDTO {

    @NotNull
    @Size(min = 16, message = "O cartão deve ter 16 números")
    @Size(max = 16, message = "O cartão deve ter 16 números")
    private String numero;

    @NotBlank
    private String senha;

    @NotNull
    private BigDecimal valor;

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }
    
}
