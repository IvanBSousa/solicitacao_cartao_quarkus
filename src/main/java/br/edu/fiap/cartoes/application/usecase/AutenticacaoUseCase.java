package br.edu.fiap.cartoes.application.usecase;
import br.edu.fiap.cartoes.presenter.dto.LoginRequest;
import br.edu.fiap.cartoes.presenter.dto.TokenResponse;
import jakarta.enterprise.context.ApplicationScoped;

import br.edu.fiap.cartoes.infrastructure.exception.EtapaNaoImplementadaException;
/**
 * Camada de aplicação a implementar: coordena uma operação, suas regras e transação.
 * O controller já chama estas assinaturas. Na migração para Clean, substituam
 * a dependência de DTO HTTP por entradas/saídas da aplicação e portas de saída.
 */
@ApplicationScoped
public class AutenticacaoUseCase {
    /** Buscar e-mail normalizado, conferir BCrypt, negar genericamente ou emitir JWT. */
    public TokenResponse login(LoginRequest request) {
        // TODO: implementar sem colocar SQL ou regras no controller.
        throw new EtapaNaoImplementadaException("AutenticacaoUseCase.login");
    }
}
