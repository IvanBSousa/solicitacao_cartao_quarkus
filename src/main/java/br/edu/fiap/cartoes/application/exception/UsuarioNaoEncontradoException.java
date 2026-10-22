package br.edu.fiap.cartoes.application.exception;

import br.edu.fiap.cartoes.infrastructure.exception.RecursoNaoEncontradoException;

public class UsuarioNaoEncontradoException extends RecursoNaoEncontradoException {
    public UsuarioNaoEncontradoException(Long id) {
        super("O usuário com id " + id + " não existe");
    }
}
