package br.edu.fiap.cartoes.infrastructure.exception;

public class EmailJaCadastradoException extends RegraNegocioException {
    public EmailJaCadastradoException(String email) {super("O email " + email + " já está cadastrado");}
}
