package br.edu.fiap.cartoes.application.ports;

import br.edu.fiap.cartoes.domain.model.TipoCartao;

import java.util.List;

/** Contrato de leitura a completar: listarDisponiveis; exclusivo da rota JDBC. */
public interface ConsultaTiposDisponiveis {
    List<TipoCartao> listarDisponiveis();
}
