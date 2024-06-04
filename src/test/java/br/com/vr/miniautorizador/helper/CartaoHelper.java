package br.com.vr.miniautorizador.helper;

import java.math.BigDecimal;
import java.util.Random;

import br.com.vr.miniautorizador.domain.model.Cartao;

public class CartaoHelper {

    private static final String SENHA_PADRAO = "1234";

    private CartaoHelper() {
    }

    public static Cartao criarCartao() {
        return new Cartao(gerarNumeroCartao(), SENHA_PADRAO);
    }

    public static Cartao criarCartaoDuplicado() {
        return new Cartao(gerarNumeroCartao(), SENHA_PADRAO);
    }

    public static Cartao criarCartaoSaldoInsuficiente() {
        Cartao cartao = new Cartao(gerarNumeroCartao(), SENHA_PADRAO);

        cartao.subtrairSaldo(BigDecimal.valueOf(400));

        return cartao;
    }

    // Método para gerar automaticamente números de cartões seguindo um padrão
    private static String gerarNumeroCartao() {
        // Aqui você pode implementar a lógica para gerar o número do cartão
        // Por exemplo, você pode concatenar um prefixo com um número aleatório
        String prefixo = "6549"; // Prefixo do número do cartão
        String numeroAleatorio = gerarNumeroAleatorio(); // Método para gerar um número aleatório
        String numeroCartao = prefixo + numeroAleatorio;
        return numeroCartao;
    }

    // Método para gerar um número aleatório com uma determinada quantidade de
    // dígitos
    private static String gerarNumeroAleatorio() {
        Random random = new Random();
        // Gera um número aleatório entre 1000 e 9999 (4 dígitos)
        int numero = random.nextInt(9000) + 1000;
        return String.valueOf(numero);
    }
}
