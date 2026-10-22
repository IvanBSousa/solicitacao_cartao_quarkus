package br.edu.fiap.cartoes.infrastructure.repository;
import br.edu.fiap.cartoes.application.ports.TipoCartaoRepository;
import br.edu.fiap.cartoes.domain.model.TipoCartao;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

import java.util.List;
import java.util.Optional;

/** Adaptador JPA a implementar: buscar o tipo por ID ao criar uma solicitação. */
@ApplicationScoped
public class TipoCartaoRepositoryJpa implements TipoCartaoRepository {

    @Inject
    EntityManager em;

    @Override
    public TipoCartao salvar(TipoCartao tipoCartao) {
        if(tipoCartao.getId() == null) {
            em.persist(tipoCartao);
            return tipoCartao;
        }
        return em.merge(tipoCartao);
    }

    @Override
    public Optional<TipoCartao> buscarPorId(Long id) {
        return Optional.ofNullable(
                em.find(TipoCartao.class,id)
        );
    }

    @Override
    public List<TipoCartao> listarTodos() {
        return em.createQuery(
                "SELECT t FROM TipoCartao t ORDER BY t.id",
                TipoCartao.class)
                .getResultList();
    }
}
