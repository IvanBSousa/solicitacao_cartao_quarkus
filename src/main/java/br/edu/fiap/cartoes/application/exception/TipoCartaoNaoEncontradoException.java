package br.edu.fiap.cartoes.application.exception;

import br.edu.fiap.cartoes.infrastructure.exception.RecursoNaoEncontradoException;

public class TipoCartaoNaoEncontradoException extends RecursoNaoEncontradoException {
    public TipoCartaoNaoEncontradoException() {
        super("Tipo de cartão não encontrado");
    }
}
