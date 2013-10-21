/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ce.model.dao;

import ce.erro.ConexaoException;
import ce.erro.RepositorioInserirException;
import ce.erro.RepositorioAlterarException;
import ce.erro.RepositorioExcluirException;
import ce.erro.RepositorioListarException;
import ce.erro.RepositorioPesquisarException;
import ce.erro.RepositorioForeignKeyException;
import ce.model.basica.Funcionario;
import java.util.List;

/**
 *
 * @author Andre
 */
public interface IRepositorioFuncionario {
    public void inserir(Funcionario f) throws ConexaoException, 
            RepositorioInserirException;
    public void alterar(Funcionario f) throws ConexaoException, 
            RepositorioAlterarException;
    public void excluir(String cpf) throws ConexaoException, 
            RepositorioForeignKeyException, RepositorioExcluirException;
    public List<Funcionario> listar() throws ConexaoException, 
            RepositorioListarException;
    public List<Funcionario> pesquisar(String nome) throws ConexaoException, 
            RepositorioPesquisarException;
    public Funcionario pesqCpf(String cpf) throws ConexaoException, 
            RepositorioPesquisarException;
}
