package br.edu.fiap.cartoes.infrastructure.repository;
import br.edu.fiap.cartoes.application.ports.CartaoRepository;
import jakarta.enterprise.context.ApplicationScoped;
/**
 * Adaptador JPA: injete EntityManager, escreva JPQL parametrizada e retorne entidades.
 * Não chamar controller nem gerar token nesta camada. Gravações participam da transação aberta pelo use case.
 */
@ApplicationScoped
public class CartaoRepositoryJpa implements CartaoRepository {
    // TODO: implementar a interface depois de declarar suas assinaturas.
}
