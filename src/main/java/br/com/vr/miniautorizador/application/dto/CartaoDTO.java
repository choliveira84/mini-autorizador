package br.com.vr.miniautorizador.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public class CartaoDTO {

    @Schema(description = "Número do cartão")
    private String numero;

    @Schema(description = "Senha do cartão")
    private String senha;

    public CartaoDTO(String numero, String senha) {
        this.numero = numero;
        this.senha = senha;
    }

    public String getSenha() {
        return senha;
    }

    public String getNumero() {
        return numero;
    }

}
