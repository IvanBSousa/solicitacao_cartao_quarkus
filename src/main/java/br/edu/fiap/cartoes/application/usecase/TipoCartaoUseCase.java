package br.edu.fiap.cartoes.application.usecase;

import br.edu.fiap.cartoes.presenter.dto.TipoCartaoResponse;

import java.util.List;

public interface TipoCartaoUseCase {
    List<TipoCartaoResponse> listarDisponiveis();
}
