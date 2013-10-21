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
import ce.model.basica.LocalEstoque;
import java.util.List;

/**
 *
 * @author Andre
 */
public interface IRepositorioLocalEstoque {
    public void inserir(LocalEstoque le) throws ConexaoException, 
            RepositorioInserirException;
    public void alterar(LocalEstoque le) throws ConexaoException, 
            RepositorioAlterarException;
    public void excluir(int codLocal) throws ConexaoException, 
            RepositorioForeignKeyException, RepositorioExcluirException;
    public List<LocalEstoque> listar() throws ConexaoException, 
            RepositorioListarException;
    public List<LocalEstoque> pesquisar(String descricao) throws ConexaoException, 
            RepositorioPesquisarException;
    public LocalEstoque pesqCod(int codLocal) throws ConexaoException, 
            RepositorioPesquisarException;
}
