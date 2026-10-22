package br.edu.fiap.cartoes.application.usecase;

import br.edu.fiap.cartoes.presenter.dto.CartaoResponse;

import java.util.List;

public interface CartaoUseCase {
    List<CartaoResponse> listar(Long usuarioId);
}
