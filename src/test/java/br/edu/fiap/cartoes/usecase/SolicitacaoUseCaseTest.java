package br.edu.fiap.cartoes.usecase;

import br.edu.fiap.cartoes.application.ports.CartaoRepository;
import br.edu.fiap.cartoes.application.ports.SolicitacaoCartaoRepository;
import br.edu.fiap.cartoes.application.ports.TipoCartaoRepository;
import br.edu.fiap.cartoes.application.ports.UsuarioRepository;
import br.edu.fiap.cartoes.application.usecase.SolicitacaoUseCaseImpl;
import br.edu.fiap.cartoes.domain.model.SolicitacaoCartao;
import br.edu.fiap.cartoes.domain.model.StatusSolicitacao;
import br.edu.fiap.cartoes.domain.model.TipoCartao;
import br.edu.fiap.cartoes.domain.model.Usuario;
import br.edu.fiap.cartoes.infrastructure.config.UsuarioAtual;
import br.edu.fiap.cartoes.infrastructure.exception.RecursoNaoEncontradoException;
import br.edu.fiap.cartoes.presenter.dto.SolicitacaoResponse;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

/** Estrutura de teste do grupo; não contém teste resolvido nem @Test de exemplo. */
class SolicitacaoUseCaseTest {

    @Test
    void deveCancelarSolicitacaoPendenteEAtualizarRepositorio() {
        SolicitacaoCartaoRepository solicitacaoRepository = mock(SolicitacaoCartaoRepository.class);
        TipoCartaoRepository tipoCartaoRepository = mock(TipoCartaoRepository.class);
        UsuarioRepository usuarioRepository = mock(UsuarioRepository.class);
        UsuarioAtual usuarioAtual = mock(UsuarioAtual.class);
        CartaoRepository cartaoRepository = mock(CartaoRepository.class);
        SolicitacaoCartao solicitacao = novaSolicitacao(StatusSolicitacao.PENDENTE);
        when(solicitacaoRepository.buscarPorId(10L)).thenReturn(Optional.of(solicitacao));

        SolicitacaoUseCaseImpl useCase = new SolicitacaoUseCaseImpl(
                solicitacaoRepository, tipoCartaoRepository, usuarioRepository, usuarioAtual, cartaoRepository
        );

        SolicitacaoResponse resposta = useCase.cancelar(1L, 10L);

        assertEquals(StatusSolicitacao.CANCELADA, resposta.status());
        verify(solicitacaoRepository).salvar(solicitacao);
    }

    @Test
    void naoDevePersistirCancelamentoDeSolicitacaoEmitida() {
        SolicitacaoCartaoRepository solicitacaoRepository = mock(SolicitacaoCartaoRepository.class);
        TipoCartaoRepository tipoCartaoRepository = mock(TipoCartaoRepository.class);
        UsuarioRepository usuarioRepository = mock(UsuarioRepository.class);
        UsuarioAtual usuarioAtual = mock(UsuarioAtual.class);
        CartaoRepository cartaoRepository = mock(CartaoRepository.class);
        SolicitacaoCartao solicitacao = novaSolicitacao(StatusSolicitacao.EMITIDA);
        when(solicitacaoRepository.buscarPorId(10L)).thenReturn(Optional.of(solicitacao));

        SolicitacaoUseCaseImpl useCase = new SolicitacaoUseCaseImpl(
                solicitacaoRepository, tipoCartaoRepository, usuarioRepository, usuarioAtual, cartaoRepository
        );

        assertThrows(RecursoNaoEncontradoException.class, () -> useCase.cancelar(1L, 10L));

        verifyNoInteractions(tipoCartaoRepository, usuarioRepository);
        verify(solicitacaoRepository, never()).salvar(solicitacao);
    }

    private SolicitacaoCartao novaSolicitacao(StatusSolicitacao status) {
        //Usuario usuario = new Usuario(1L, "Cliente", "cliente@example.com", "hash", Instant.parse("2007-12-03T10:15:30.00Z"));
        Usuario usuario = mock(Usuario.class);
        TipoCartao tipoCartao = new TipoCartao("ESSENCIAL", "Cartão essencial", true);
        tipoCartao.setId(2L);

        SolicitacaoCartao solicitacao = new SolicitacaoCartao(usuario, tipoCartao);
        solicitacao.setId(10L);
        solicitacao.setStatus(status);
        return solicitacao;
    }
}
