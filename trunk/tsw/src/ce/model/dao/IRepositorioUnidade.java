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
import ce.model.basica.Unidade;
import java.util.List;

/**
 *
 * @author Andre
 */
public interface IRepositorioUnidade {
    public void inserir(Unidade u) throws ConexaoException, 
            RepositorioInserirException;
    public void alterar(Unidade u) throws ConexaoException, 
            RepositorioAlterarException;
    public void excluir(Integer codUnid) throws ConexaoException, 
            RepositorioForeignKeyException, RepositorioExcluirException;
    public List<Unidade> listar() throws ConexaoException, 
            RepositorioListarException;
    public List<Unidade> pesquisar(String descricao) throws ConexaoException, 
            RepositorioPesquisarException;
    public Unidade pesqCod(Integer codUnid) throws ConexaoException, 
            RepositorioPesquisarException; 
}
