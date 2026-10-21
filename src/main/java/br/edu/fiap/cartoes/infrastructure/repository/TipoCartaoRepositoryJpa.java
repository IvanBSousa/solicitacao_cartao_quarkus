package br.edu.fiap.cartoes.infrastructure.repository;
import br.edu.fiap.cartoes.application.ports.TipoCartaoRepository;
import jakarta.enterprise.context.ApplicationScoped;
/** Adaptador JPA a implementar: buscar o tipo por ID ao criar uma solicitação. */
@ApplicationScoped
public class TipoCartaoRepositoryJpa implements TipoCartaoRepository {
    // TODO: implementar a busca com EntityManager, sem chamar o adaptador JDBC.
}
