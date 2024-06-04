package br.com.vr.miniautorizador.application.interfaces.controlleres;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.vr.miniautorizador.application.dto.CartaoDTO;
import br.com.vr.miniautorizador.application.dto.CartaoPostDTO;
import br.com.vr.miniautorizador.application.service.CartaoService;
import br.com.vr.miniautorizador.domain.exceptions.CartaoExistenteException;
import br.com.vr.miniautorizador.domain.exceptions.CartaoInexistenteException;

@RestController
@RequestMapping("/cartoes")
public class CartaoController {

    Logger logger = LoggerFactory.getLogger(CartaoController.class);

    private final CartaoService cartaoService;

    public CartaoController(CartaoService cartaoService) {
        this.cartaoService = cartaoService;
    }

    @PostMapping
    public ResponseEntity<CartaoDTO> criarCartao(@RequestBody CartaoPostDTO cartao) {
        logger.debug("Requisição REST para criar o cartão de número {}", cartao.getNumero());
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(cartaoService.criar(cartao));
        } catch (CartaoExistenteException e) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                    .body(new CartaoDTO(cartao.getNumero(), cartao.getSenha()));
        }
    }

    @GetMapping("/{numeroCartao}")
    public ResponseEntity<BigDecimal> obterSaldo(@PathVariable String numeroCartao) {
        logger.debug("Requisição REST para obter o saldo para o cartão de número {}", numeroCartao);
        try {
            return ResponseEntity.status(HttpStatus.OK).body(cartaoService.encontrarPeloNumero(numeroCartao));
        } catch (CartaoInexistenteException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(null);
        }
    }
}
