package br.edu.fiap.cartoes.application.usecase;
import br.edu.fiap.cartoes.application.ports.ConsultaTiposDisponiveis;
import br.edu.fiap.cartoes.presenter.dto.TipoCartaoResponse;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;

/**
 * Camada de aplicação a implementar: coordena uma operação, suas regras e transação.
 * O controller já chama estas assinaturas. Na migração para Clean, substituam
 * a dependência de DTO HTTP por entradas/saídas da aplicação e portas de saída.
 */
@ApplicationScoped
public class TipoCartaoUseCaseImpl implements TipoCartaoUseCase{

    private final ConsultaTiposDisponiveis tiposDisponiveis;

    public TipoCartaoUseCaseImpl(ConsultaTiposDisponiveis tiposDisponiveis) {
        this.tiposDisponiveis = tiposDisponiveis;
    }

    /** Delegar à única consulta JDBC e mapear a resposta. */
    @Override
    public List<TipoCartaoResponse> listarDisponiveis() {
        return tiposDisponiveis.listarDisponiveis().stream()
                .map(TipoCartaoResponse::de)
                .toList();
    }
}
