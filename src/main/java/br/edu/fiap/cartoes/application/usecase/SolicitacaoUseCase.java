package br.edu.fiap.cartoes.application.usecase;

import br.edu.fiap.cartoes.domain.model.StatusSolicitacao;
import br.edu.fiap.cartoes.presenter.dto.CartaoResponse;
import br.edu.fiap.cartoes.presenter.dto.SolicitacaoRequest;
import br.edu.fiap.cartoes.presenter.dto.SolicitacaoResponse;

import java.util.List;

public interface SolicitacaoUseCase {
    SolicitacaoResponse criar(Long usuarioId, SolicitacaoRequest request);
    List<SolicitacaoResponse> listarComStatus(Long usuarioId, StatusSolicitacao status);
    List<SolicitacaoResponse> listarSemStatus(Long usuarioId);
    SolicitacaoResponse buscar(Long usuarioId, Long id);
    SolicitacaoResponse cancelar(Long usuarioId, Long id);
    CartaoResponse emitir(Long usuarioId, Long id);
}
