package br.com.vr.miniautorizador.application.interfaces.controlleres;

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

@RestController
@RequestMapping("/transacoes")
public class TransacaoController {

    Logger logger = LoggerFactory.getLogger(TransacaoController.class);

    private final CartaoService cartaoService;

    public TransacaoController(CartaoService cartaoService) {
        this.cartaoService = cartaoService;
    }

    @PostMapping
    public ResponseEntity<String> realizarTransacao(@RequestBody TransacaoPostDTO transacao) {
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
