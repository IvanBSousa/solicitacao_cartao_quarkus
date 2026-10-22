package br.edu.fiap.cartoes.application.ports;

import br.edu.fiap.cartoes.domain.model.SolicitacaoCartao;
import br.edu.fiap.cartoes.domain.model.StatusSolicitacao;

import java.util.List;
import java.util.Optional;

/**
 * Interface de persistência a completar pelo grupo. Não há Spring Data: declarar o
 * nome de um método não cria sua implementação. Escrevam os contratos usados pelos
 * use cases e implementem a classe concreta. Ao migrar, movam o contrato para uma
 * porta da aplicação, sem tipos de EntityManager, ResultSet ou DTO HTTP.
 */
public interface SolicitacaoCartaoRepository {
    SolicitacaoCartao salvar(SolicitacaoCartao solicitacao);
    Optional<SolicitacaoCartao> buscarPorId(Long id);
    List<SolicitacaoCartao> listarPorUsuario(Long usuarioId);
    List<SolicitacaoCartao> listarPorUsuarioEStatus(Long usuarioId, StatusSolicitacao status);
}
