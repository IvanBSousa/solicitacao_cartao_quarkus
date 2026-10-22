package br.edu.fiap.cartoes.infrastructure.repository;
import br.edu.fiap.cartoes.application.ports.UsuarioRepository;
import br.edu.fiap.cartoes.domain.model.Usuario;
import br.edu.fiap.cartoes.infrastructure.exception.EmailJaCadastradoException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

import java.sql.SQLException;
import java.util.Optional;

/**
 * Adaptador JPA: injete EntityManager, escreva JPQL parametrizada e retorne entidades.
 * Não chamar controller nem gerar token nesta camada. Gravações participam da transação aberta pelo use case.
 */
@ApplicationScoped
public class UsuarioRepositoryJpa implements UsuarioRepository {
    private static final String BUSCA_POR_EMAIL = """
        SELECT usuario FROM Usuario usuario
        WHERE lower(usuario.email) = lower(:email)
    """;

    private final EntityManager entityManager;

    @Inject
    public UsuarioRepositoryJpa(EntityManager entityManager){
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    public Optional<Usuario> buscarPorId(Long id) {
        return Optional.ofNullable(entityManager.find(Usuario.class, id));
    }

    @Override
    @Transactional
    public boolean existePorEmail(String email){
        return buscaPorEmail(email).isPresent();
    }

    @Override
    @Transactional
    public Optional<Usuario> buscaPorEmail(String email){
        return entityManager.createQuery(BUSCA_POR_EMAIL, Usuario.class)
                .setParameter("email", email)
                .getResultStream()
                .findFirst();
    }

    @Override
    @Transactional
    public Usuario cadastrar(Usuario usuario){
        // No JDBC o ISERT lista colunas e usa aquele SETSTRING
        //PERSISTE copia o objeto mapeado


        try{
            entityManager.persist(usuario);

            //Flush força o InSERT, No JDBC isso é o executeUpdate
            // O identity só devolve o id depous que o banco executa o insert
            entityManager.flush();
            return usuario;

        }catch(RuntimeException erro){
            if(violouChaveUnica(erro)){
                throw new EmailJaCadastradoException(usuario.getEmail());
            }
            throw new IllegalStateException("Falhou", erro);
        }
    }

    private boolean violouChaveUnica(Throwable erro){
        Throwable atual = erro;
        while(atual != null){
            if(atual instanceof SQLException sql && "23505".equals(sql.getSQLState())){
                return true;
            }
            atual = atual.getCause();
        }
        return false;
    }

}
