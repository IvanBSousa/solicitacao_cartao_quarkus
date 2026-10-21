package br.edu.fiap.cartoes.domain.exception;

import br.edu.fiap.cartoes.infrastructure.exception.RegraNegocioException;

public class SolicitacaoNaoPermitidaException extends RegraNegocioException {
    public SolicitacaoNaoPermitidaException(String solicitacao) {
        super("Cancelamento não permitido devido ao status: " + solicitacao);
    }
}
