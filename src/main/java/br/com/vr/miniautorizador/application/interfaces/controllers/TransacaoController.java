package br.com.vr.miniautorizador.application.interfaces.controllers;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.vr.miniautorizador.application.dto.TransacaoPostDTO;
import br.com.vr.miniautorizador.application.service.CartaoService;
import br.com.vr.miniautorizador.domain.exceptions.CartaoInexistenteException;
import br.com.vr.miniautorizador.domain.exceptions.SaldoInsuficienteException;
import br.com.vr.miniautorizador.domain.exceptions.SenhaInvalidaException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Transações", description = "Realizar transações com o cartão.")
@RestController
@RequestMapping("/transacoes")
public class TransacaoController {

    Logger logger = LoggerFactory.getLogger(TransacaoController.class);

    private final CartaoService cartaoService;

    public TransacaoController(CartaoService cartaoService) {
        this.cartaoService = cartaoService;
    }

     @Operation(summary = "Realizar uma transação")
    @ApiResponses(value =  {
            @ApiResponse(responseCode = "201", description = "Created", content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "422", description = "Unprocessable Entity", content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @PostMapping
    public ResponseEntity<String> realizarTransacao(@RequestBody @Valid TransacaoPostDTO transacao) {
        try {
            cartaoService.transacionar(transacao);
        } catch (CartaoInexistenteException e) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                    .body("CARTAO_INEXISTENTE");
        } catch (SaldoInsuficienteException e) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                    .body("SALDO_INSUFICIENTE");
        } catch (SenhaInvalidaException e) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                    .body("SENHA_INVALIDA");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body("OK");
    }
}
