package br.edu.fiap.cartoes.infrastructure.repository;
import br.edu.fiap.cartoes.application.ports.ConsultaTiposDisponiveis;
import br.edu.fiap.cartoes.domain.model.TipoCartao;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Adaptador JDBC: use AgroalDataSource, PreparedStatement e try-with-resources.
 * Não chamar controller nem gerar token nesta camada. Esta é a única implementação JDBC obrigatória.
 */
@ApplicationScoped
public class TipoCartaoRepositoryJdbc implements ConsultaTiposDisponiveis {

    @Inject
    DataSource dataSource;

    @Override
    public List<TipoCartao> listarDisponiveis() {

        String sql = """
                SELECT id, nome, descricao, disponivel
                FROM tipos_cartao
                WHERE disponivel = ?
                ORDER BY nome
                """;

        List<TipoCartao> tiposDisponiveis = new ArrayList<>();

        try(
                Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
                ) {
            statement.setBoolean(1, true);

            try(
                    ResultSet resultSet = statement.executeQuery()
                    ) {
                while (resultSet.next()) {
                    TipoCartao tipoCartao = new TipoCartao(
                            resultSet.getString("nome"),
                            resultSet.getString("descricao"),
                            resultSet.getBoolean("disponivel")
                    );

                    tipoCartao.setId(resultSet.getLong("id"));
                    tiposDisponiveis.add(tipoCartao);
                }
            }
            return tiposDisponiveis;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao consultar tipos de cartão disponíveis", e);
        }
    }
}
