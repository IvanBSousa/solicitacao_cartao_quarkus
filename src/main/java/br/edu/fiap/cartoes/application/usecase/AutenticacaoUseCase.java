package br.edu.fiap.cartoes.application.usecase;

import br.edu.fiap.cartoes.presenter.dto.LoginRequest;
import br.edu.fiap.cartoes.presenter.dto.TokenResponse;

public interface AutenticacaoUseCase {
    TokenResponse login(LoginRequest request);
}
