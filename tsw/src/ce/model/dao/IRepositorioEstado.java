/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ce.model.dao;

import ce.erro.ConexaoException;
import ce.erro.RepositorioInserirException;
import ce.erro.RepositorioAlterarException;
import ce.erro.RepositorioExcluirException;
import ce.erro.RepositorioForeignKeyException;
import ce.erro.RepositorioListarException;
import ce.erro.RepositorioPesquisarException;
import ce.model.basica.Estado;
import java.util.List;

/**
 *
 * @author Andre
 */
public interface IRepositorioEstado {
    public void inserir(Estado e) throws ConexaoException, 
            RepositorioInserirException;
    public void alterar(Estado e) throws ConexaoException, 
            RepositorioAlterarException;
    public void excluir(String uf) throws ConexaoException, 
            RepositorioForeignKeyException, RepositorioExcluirException;
    public List<Estado> listar() throws ConexaoException, 
            RepositorioListarException;
    public List<Estado> pesquisar(String descricao) throws ConexaoException, 
            RepositorioPesquisarException;
    public Estado pesqUf(String uf) throws ConexaoException, 
            RepositorioPesquisarException; 
}
