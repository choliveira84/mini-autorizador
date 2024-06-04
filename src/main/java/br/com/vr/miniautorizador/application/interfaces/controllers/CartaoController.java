package br.com.vr.miniautorizador.application.interfaces.controllers;

import java.math.BigDecimal;

import javax.validation.Valid;

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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Cartão", description = "Cartões de benefícios")
@RestController
@RequestMapping("/cartoes")
public class CartaoController {

    Logger logger = LoggerFactory.getLogger(CartaoController.class);

    private final CartaoService cartaoService;

    public CartaoController(CartaoService cartaoService) {
        this.cartaoService = cartaoService;
    }

    @Operation(summary = "Criar novo cartão")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created", content = @Content(schema = @Schema(implementation = CartaoDTO.class))),
            @ApiResponse(responseCode = "422", description = "Unprocessable Entity", content = @Content(schema = @Schema(implementation = CartaoDTO.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @PostMapping
    public ResponseEntity<CartaoDTO> criarCartao(@RequestBody @Valid CartaoPostDTO cartao) {
        logger.debug("Requisição REST para criar o cartão de número {}", cartao.getNumero());
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(cartaoService.criar(cartao));
        } catch (CartaoExistenteException e) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                    .body(new CartaoDTO(cartao.getNumero(), cartao.getSenha()));
        }
    }

    @Operation(summary = "Consultar saldo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = BigDecimal.class))),
            @ApiResponse(responseCode = "404", description = "Not Found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @GetMapping("/{numeroCartao}")
    public ResponseEntity<String> obterSaldo(@PathVariable String numeroCartao) {
        logger.debug("Requisição REST para obter o saldo para o cartão de número {}", numeroCartao);
        try {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(String.valueOf(cartaoService.encontrarPeloNumero(numeroCartao)));
        } catch (CartaoInexistenteException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(null);
        }
    }
}
