/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ce.model.dao;

import ce.erro.ConexaoException;
//import ce.erro.RepositorioException;
import ce.erro.RepositorioInserirException;
import ce.erro.RepositorioAlterarException;
import ce.erro.RepositorioExcluirException;
import ce.erro.RepositorioListarException;
import ce.erro.RepositorioPesquisarException;
import ce.erro.RepositorioForeignKeyException;
import ce.model.basica.Produto;
import java.util.List;

/**
 *
 * @author Andre
 */
public interface IRepositorioProduto {
    public void inserir(Produto p) throws ConexaoException, 
            RepositorioInserirException;
    public void alterar(Produto p) throws ConexaoException, 
            RepositorioAlterarException;
    public void excluir(Integer codProd) throws ConexaoException, 
            RepositorioForeignKeyException, RepositorioExcluirException;
    public List<Produto> listar() throws ConexaoException, 
            RepositorioListarException;
    public List<Produto> pesquisar(String descProd) throws ConexaoException, 
            RepositorioPesquisarException;
    public Produto pesqCodProd(Integer codProd, boolean comForns) 
            throws ConexaoException, RepositorioPesquisarException; 
    public List<Produto> pesquisarProdsQueNaoSaoDoForn(Integer codForn) 
            throws ConexaoException, RepositorioPesquisarException;
    public void atualizarQtde(Produto p) throws ConexaoException, 
            RepositorioAlterarException;
}
