package br.com.vr.miniautorizador.application.interfaces.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.vr.miniautorizador.application.dto.CartaoPostDTO;
import br.com.vr.miniautorizador.application.service.CartaoService;
import br.com.vr.miniautorizador.domain.model.Cartao;
import br.com.vr.miniautorizador.helper.CartaoHelper;
import br.com.vr.miniautorizador.infrastructure.persistence.CartaoRepository;

@WebMvcTest(controllers = CartaoController.class)
@AutoConfigureMockMvc
class CartaoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CartaoService cartaoService;

    @MockBean
    private CartaoRepository cartaoRepository;

    // private final CriarCartao cartaoPadraoDuplicado =
    // CartaoHelper.criarCartaoDuplicado();

    // private final CriarCartao novoCartaoCorreto = CartaoHelper.criarCartao();

    // private final CriarCartao novoCartaoComAlfaNumerico =
    // CartaoHelper.criarCartaoSaldoInsuficiente();

    private final static String BASE_URL = "/cartoes";

    private final static String APPLICATION_JSON = "application/json";

    @Test
    void deveRetornar201() throws Exception {
        Cartao cartao = CartaoHelper.criarCartao();

        CartaoPostDTO post = new CartaoPostDTO();
        post.setNumero(cartao.getNumero());
        post.setSenha(cartao.getSenha());

        mockMvc.perform(post(BASE_URL)
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(post)))
                .andExpect(status().isCreated());
    }

    @Test
    void deveRetornar422Duplicado() throws Exception {
        Cartao cartao = CartaoHelper.criarCartao();

        CartaoPostDTO post = new CartaoPostDTO();
        post.setNumero(cartao.getNumero());
        post.setSenha(cartao.getSenha());

        // when(cartaoRepository.encontrarPeloNumero(cartao.getNumero())).thenThrow(CartaoExistenteException.class);
        when(cartaoService.criar(post)).thenThrow(RuntimeException.class);

        mockMvc.perform(post(BASE_URL)
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(post)))
                .andExpect(status().isUnprocessableEntity());
    }
}
