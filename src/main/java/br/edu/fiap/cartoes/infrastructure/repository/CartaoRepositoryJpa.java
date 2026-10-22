package br.edu.fiap.cartoes.infrastructure.repository;
import br.edu.fiap.cartoes.application.ports.CartaoRepository;
import br.edu.fiap.cartoes.domain.model.Cartao;
import br.edu.fiap.cartoes.presenter.dto.CartaoResponse;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

import java.util.List;

/**
 * Adaptador JPA: injete EntityManager, escreva JPQL parametrizada e retorne entidades.
 * Não chamar controller nem gerar token nesta camada. Gravações participam da transação aberta pelo use case.
 */
@ApplicationScoped
public class CartaoRepositoryJpa implements CartaoRepository {

    @Inject
    EntityManager em;

    @Override
    public Cartao salvar(Cartao cartao) {
        if(cartao.getId() == null) {
            em.persist(cartao);
            return cartao;
        }
        return em.merge(cartao);
    }

    @Override
    public List<Cartao> listarPorUsuario(Long usuarioId) {
        return em.createQuery("""
            SELECT c FROM Cartao c
            WHERE c.solicitacao.usuario.id = :usuarioId
            """, Cartao.class)
                .setParameter("usuarioId", usuarioId)
                .getResultList();
    }
}
