package br.edu.fiap.cartoes.application.usecase;

import br.edu.fiap.cartoes.presenter.dto.UsuarioRequest;
import br.edu.fiap.cartoes.presenter.dto.UsuarioResponse;

public interface UsuarioUseCase {
    UsuarioResponse cadastrar(UsuarioRequest request);
}
