package br.com.vr.miniautorizador.application.dto;

import java.math.BigDecimal;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import io.swagger.v3.oas.annotations.media.Schema;

public class TransacaoPostDTO {

    @Schema(description = "Número do cartão", example = "6549873025634501", required = true)
    @NotNull
    @Size(min = 16, message = "O cartão deve ter 16 números")
    @Size(max = 16, message = "O cartão deve ter 16 números")
    private String numero;

    @Schema(description = "Senha do cartão", example = "1234", required = true)
    @NotBlank
    private String senha;

    @Schema(description = "Valor da transação", example = "100.00", required = true)
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
