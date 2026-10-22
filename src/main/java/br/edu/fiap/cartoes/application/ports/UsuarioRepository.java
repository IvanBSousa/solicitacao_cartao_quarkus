package br.edu.fiap.cartoes.application.ports;

import br.edu.fiap.cartoes.domain.model.Usuario;

import java.util.Optional;

/**
 * Interface de persistência a completar pelo grupo. Não há Spring Data: declarar o
 * nome de um método não cria sua implementação. Escrevam os contratos usados pelos
 * use cases e implementem a classe concreta. Ao migrar, movam o contrato para uma
 * porta da aplicação, sem tipos de EntityManager, ResultSet ou DTO HTTP.
 */
public interface UsuarioRepository {
    Optional<Usuario> buscarPorId(Long id);
    boolean existePorEmail(String email);
    Usuario cadastrar(Usuario usuario);
    Optional<Usuario> buscaPorEmail(String email);
}
