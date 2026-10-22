package br.edu.fiap.cartoes.infrastructure.repository;
import br.edu.fiap.cartoes.application.ports.SolicitacaoCartaoRepository;
import br.edu.fiap.cartoes.domain.model.SolicitacaoCartao;
import br.edu.fiap.cartoes.domain.model.StatusSolicitacao;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

import java.util.List;
import java.util.Optional;

/**
 * Adaptador JPA: injete EntityManager, escreva JPQL parametrizada e retorne entidades.
 * Não chamar controller nem gerar token nesta camada. Gravações participam da transação aberta pelo use case.
 */
@ApplicationScoped
public class SolicitacaoCartaoRepositoryJpa implements SolicitacaoCartaoRepository {

    @Inject
    EntityManager em;

    @Override
    public SolicitacaoCartao salvar(SolicitacaoCartao solicitacao) {
        if(solicitacao.getId() == null) {
            em.persist(solicitacao);
            return solicitacao;
        }
        return em.merge(solicitacao);
    }

    @Override
    public Optional<SolicitacaoCartao> buscarPorId(Long id) {
        return Optional.ofNullable(
                em.find(SolicitacaoCartao.class, id)
        );
    }

    @Override
    public List<SolicitacaoCartao> listarPorUsuario(Long usuarioId) {
        return em.createQuery("""
            SELECT s FROM SolicitacaoCartao s
            WHERE s.usuario.id = :usuarioId
            ORDER BY s.solicitadaEm DESC
            """, SolicitacaoCartao.class)
                .setParameter("usuarioId", usuarioId)
                .getResultList();
    }

    @Override
    public List<SolicitacaoCartao> listarPorUsuarioEStatus(Long usuarioId, StatusSolicitacao status) {
        return em.createQuery("""
            SELECT s FROM SolicitacaoCartao s
            WHERE s.usuario.id = :usuarioId
            AND s.status = :status
            ORDER BY s.solicitadaEm DESC
            """, SolicitacaoCartao.class)
                .setParameter("usuarioId", usuarioId)
                .setParameter("status", status)
                .getResultList();
    }
}
