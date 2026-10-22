package br.edu.fiap.cartoes.application.usecase;
import br.edu.fiap.cartoes.application.exception.TipoCartaoNaoEncontradoException;
import br.edu.fiap.cartoes.application.exception.UsuarioNaoEncontradoException;
import br.edu.fiap.cartoes.application.ports.CartaoRepository;
import br.edu.fiap.cartoes.application.ports.SolicitacaoCartaoRepository;
import br.edu.fiap.cartoes.application.ports.TipoCartaoRepository;
import br.edu.fiap.cartoes.application.ports.UsuarioRepository;
import br.edu.fiap.cartoes.domain.model.Cartao;
import br.edu.fiap.cartoes.domain.model.SolicitacaoCartao;
import br.edu.fiap.cartoes.domain.model.TipoCartao;
import br.edu.fiap.cartoes.domain.model.Usuario;
import br.edu.fiap.cartoes.infrastructure.config.UsuarioAtual;
import br.edu.fiap.cartoes.infrastructure.exception.RecursoNaoEncontradoException;
import br.edu.fiap.cartoes.presenter.dto.CartaoResponse;
import br.edu.fiap.cartoes.presenter.dto.SolicitacaoRequest;
import br.edu.fiap.cartoes.presenter.dto.SolicitacaoResponse;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;
import java.util.Objects;

import br.edu.fiap.cartoes.domain.model.StatusSolicitacao;

/**
 * Camada de aplicação a implementar: coordena uma operação, suas regras e transação.
 * O controller já chama estas assinaturas. Na migração para Clean, substituam
 * a dependência de DTO HTTP por entradas/saídas da aplicação e portas de saída.
 */
@ApplicationScoped
public class SolicitacaoUseCaseImpl implements SolicitacaoUseCase{

    private final SolicitacaoCartaoRepository solicitacaoRepository;
    private final TipoCartaoRepository tipoCartaoRepository;
    private final UsuarioRepository usuarioRepository;
    private final UsuarioAtual usuarioAtual;
    private final CartaoRepository cartaoRepository;

    public SolicitacaoUseCaseImpl(SolicitacaoCartaoRepository solicitacaoRepository, TipoCartaoRepository tipoCartaoRepository, UsuarioRepository usuarioRepository, UsuarioAtual usuarioAtual, CartaoRepository cartaoRepository) {
        this.solicitacaoRepository = solicitacaoRepository;
        this.tipoCartaoRepository = tipoCartaoRepository;
        this.usuarioRepository = usuarioRepository;
        this.usuarioAtual = usuarioAtual;
        this.cartaoRepository = cartaoRepository;
    }

    /** Buscar titular e tipo por JPA; tipo indisponível ou pedido pendente duplicado gera 409. */
    @Override
    public SolicitacaoResponse criar(Long usuarioId, SolicitacaoRequest request) {
        Usuario usuario = usuarioRepository.buscarPorId(usuarioId)
                .orElseThrow(() -> new UsuarioNaoEncontradoException(usuarioId));
        TipoCartao tipoCartao = tipoCartaoRepository.buscarPorId(request.tipoCartaoId())
                .orElseThrow(TipoCartaoNaoEncontradoException::new);
        SolicitacaoCartao solicitacao = new SolicitacaoCartao(usuario, tipoCartao);

        return SolicitacaoResponse.de(solicitacaoRepository.salvar(solicitacao));
    }

    /** JPQL própria com filtro de titular e status opcional, nunca listarPorUsuario todos os usuários. */
    @Override
    public List<SolicitacaoResponse> listar(Long usuarioId, StatusSolicitacao status) {
        return solicitacaoRepository.listarPorUsuarioEStatus(usuarioId, status).stream()
                .map(SolicitacaoResponse::de)
                .toList();
    }

    /** Buscar por id E titular; retornar 404 para pedido de outro usuário. */
    @Override
    public SolicitacaoResponse buscar(Long usuarioId, Long id) {

        return SolicitacaoResponse.de(encontraSolicitacao(usuarioId, id));
    }

    /** Em transação, buscar o próprio pedido e chamar cancelar; estado diferente de PENDENTE gera 409. */
    @Override
    public SolicitacaoResponse cancelar(Long usuarioId, Long id) {
        SolicitacaoCartao solicitacao = encontraSolicitacao(usuarioId, id);

        solicitacao.cancelar();

        return SolicitacaoResponse.de(solicitacaoRepository.salvar(solicitacao));
    }

    /** Em uma transação, bloquear o pedido para escrita, conferir titular/estado, criar cartão e marcar EMITIDA. */
    public CartaoResponse emitir(Long usuarioId, Long id) {
        SolicitacaoCartao solicitacao = encontraSolicitacao(usuarioId, id);

        Cartao cartao = new Cartao(solicitacao);
        CartaoResponse response = CartaoResponse.de(cartaoRepository.salvar(cartao));

        solicitacao.marcarEmitida();

        return response;
    }

    private SolicitacaoCartao encontraSolicitacao(Long usuarioId, Long id) {
        if(!Objects.equals(usuarioAtual.id(), usuarioId)) {
            throw new RecursoNaoEncontradoException("Solicitação não encontrada");
        }

        return solicitacaoRepository.listarPorUsuario(usuarioId).stream()
                .findFirst()
                .orElseThrow(() -> new RecursoNaoEncontradoException("Solicitação não encontrada"));
    }
}
