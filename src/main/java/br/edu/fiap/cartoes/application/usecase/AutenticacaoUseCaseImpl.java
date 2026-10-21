package br.edu.fiap.cartoes.application.usecase;
import br.edu.fiap.cartoes.application.ports.UsuarioRepository;
import br.edu.fiap.cartoes.domain.model.Usuario;
import br.edu.fiap.cartoes.infrastructure.exception.CredenciaisInvalidasException;
import br.edu.fiap.cartoes.infrastructure.security.JwtService;
import br.edu.fiap.cartoes.infrastructure.security.SenhaBCrypt;
import br.edu.fiap.cartoes.presenter.dto.LoginRequest;
import br.edu.fiap.cartoes.presenter.dto.TokenResponse;
import jakarta.enterprise.context.ApplicationScoped;

/**
 * Camada de aplicação a implementar: coordena uma operação, suas regras e transação.
 * O controller já chama estas assinaturas. Na migração para Clean, substituam
 * a dependência de DTO HTTP por entradas/saídas da aplicação e portas de saída.
 */
@ApplicationScoped
public class AutenticacaoUseCaseImpl implements AutenticacaoUseCase {
    private final UsuarioRepository repository;
    private final SenhaBCrypt senha;
    private final JwtService token;

    public AutenticacaoUseCaseImpl(UsuarioRepository repository, SenhaBCrypt senha, JwtService token) {
        this.repository = repository;
        this.senha = senha;
        this.token = token;
    }

    /** Buscar e-mail normalizado, conferir BCrypt, negar genericamente ou emitir JWT. */
    @Override
    public TokenResponse login(LoginRequest request) {
        Usuario usuario = repository.buscaPorEmail(request.email())
                .orElseThrow(CredenciaisInvalidasException::new);

        if(!senha.confere(request.senha(), usuario.getSenhaHash())) {
            throw new CredenciaisInvalidasException();
        }

        return token.emitir(usuario);
    }
}
