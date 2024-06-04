package br.com.vr.miniautorizador.application.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class CartaoPostDTO {

    @NotNull
    @Size(min = 16, message = "O cartão deve ter 16 números")
    @Size(max = 16, message = "O cartão deve ter 16 números")
    private String numero;

    @NotBlank
    private String senha;

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
}
