package br.edu.fiap.cartoes.application.usecase;
import br.edu.fiap.cartoes.application.ports.UsuarioRepository;
import br.edu.fiap.cartoes.domain.model.Usuario;
import br.edu.fiap.cartoes.infrastructure.exception.EmailJaCadastradoException;
import br.edu.fiap.cartoes.infrastructure.security.SenhaBCrypt;
import br.edu.fiap.cartoes.presenter.dto.UsuarioRequest;
import br.edu.fiap.cartoes.presenter.dto.UsuarioResponse;
import jakarta.enterprise.context.ApplicationScoped;

import br.edu.fiap.cartoes.infrastructure.exception.EtapaNaoImplementadaException;
/**
 * Camada de aplicação a implementar: coordena uma operação, suas regras e transação.
 * O controller já chama estas assinaturas. Na migração para Clean, substituam
 * a dependência de DTO HTTP por entradas/saídas da aplicação e portas de saída.
 */
@ApplicationScoped
public class UsuarioUseCaseImpl implements UsuarioUseCase{

    private final UsuarioRepository repository;
    private final SenhaBCrypt senhas;

    public UsuarioUseCaseImpl(UsuarioRepository repository, SenhaBCrypt senhas) {
        this.repository = repository;
        this.senhas = senhas;
    }

    /** Normalizar e-mail, rejeitar duplicado, gerar hash BCrypt, persistir e mapear sem senha. */
    @Override
    public UsuarioResponse cadastrar(UsuarioRequest request) {
        if(repository.existePorEmail(request.email())) {
            throw new EmailJaCadastradoException(request.email());
        }

        return UsuarioResponse.de(new Usuario(
                request.nome(),
                request.email().toLowerCase(),
                senhas.criarHash(request.senha()))
        );
    }
}
