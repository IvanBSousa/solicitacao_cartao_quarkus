package br.edu.fiap.cartoes.entity;

import br.edu.fiap.cartoes.domain.model.SolicitacaoCartao;
import br.edu.fiap.cartoes.domain.model.StatusSolicitacao;
import br.edu.fiap.cartoes.domain.model.TipoCartao;
import br.edu.fiap.cartoes.domain.model.Usuario;
import br.edu.fiap.cartoes.infrastructure.exception.RegraNegocioException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/** Estrutura de teste do grupo; não contém teste resolvido nem @Test de exemplo. */
class SolicitacaoCartaoTest {

    @Test
    void deveTransicionarDePendenteParaCancelada() {
        SolicitacaoCartao solicitacao = novaSolicitacao();

        solicitacao.cancelar();

        assertEquals(StatusSolicitacao.CANCELADA, solicitacao.getStatus());
    }

    @Test
    void naoDeveCancelarSolicitacaoQueJaFoiCancelada() {
        SolicitacaoCartao solicitacao = novaSolicitacao();
        solicitacao.setStatus(StatusSolicitacao.CANCELADA);

        assertThrows(RegraNegocioException.class, solicitacao::cancelar);
        assertEquals(StatusSolicitacao.CANCELADA, solicitacao.getStatus());
    }

    private SolicitacaoCartao novaSolicitacao() {
        return new SolicitacaoCartao(
                new Usuario("Cliente", "cliente@example.com", "hash"),
                new TipoCartao("ESSENCIAL", "Cartão essencial", true)
        );
    }
}
