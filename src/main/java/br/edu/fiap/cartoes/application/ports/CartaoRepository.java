package br.edu.fiap.cartoes.application.ports;

import br.edu.fiap.cartoes.domain.model.Cartao;
import br.edu.fiap.cartoes.presenter.dto.CartaoResponse;

import java.util.List;

/**
 * Interface de persistência a completar pelo grupo. Não há Spring Data: declarar o
 * nome de um método não cria sua implementação. Escrevam os contratos usados pelos
 * use cases e implementem a classe concreta. Ao migrar, movam o contrato para uma
 * porta da aplicação, sem tipos de EntityManager, ResultSet ou DTO HTTP.
 */
public interface CartaoRepository {
    Cartao salvar(Cartao cartao);
    List<Cartao> listarPorUsuario(Long usuarioId);
}
