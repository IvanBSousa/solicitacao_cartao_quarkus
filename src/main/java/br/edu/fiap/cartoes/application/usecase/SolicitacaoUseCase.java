package br.edu.fiap.cartoes.application.usecase;

import br.edu.fiap.cartoes.domain.model.StatusSolicitacao;
import br.edu.fiap.cartoes.presenter.dto.SolicitacaoRequest;
import br.edu.fiap.cartoes.presenter.dto.SolicitacaoResponse;

import java.util.List;

public interface SolicitacaoUseCase {
    SolicitacaoResponse criar(Long usuarioId, SolicitacaoRequest request);
    List<SolicitacaoResponse> listar(Long usuarioId, StatusSolicitacao status);
    SolicitacaoResponse buscar(Long usuarioId, Long id);
    SolicitacaoResponse cancelar(Long usuarioId, Long id);
}
