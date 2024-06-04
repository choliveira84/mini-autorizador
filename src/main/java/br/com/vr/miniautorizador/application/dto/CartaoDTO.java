package br.com.vr.miniautorizador.application.dto;

public class CartaoDTO {

    private String numero;

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
