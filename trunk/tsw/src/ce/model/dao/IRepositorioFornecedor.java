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
import ce.model.basica.Fornecedor;
import java.util.List;

/**
 *
 * @author Andre
 */
public interface IRepositorioFornecedor {
    public void inserir(Fornecedor f) throws ConexaoException, 
            RepositorioInserirException;
    public void alterar(Fornecedor f) throws ConexaoException, 
            RepositorioAlterarException;
    public void excluir(Integer codForn) throws ConexaoException, 
            RepositorioForeignKeyException, RepositorioExcluirException;
    public List<Fornecedor> listar() throws ConexaoException, 
            RepositorioListarException;
    public List<Fornecedor> pesquisar(String descProd) throws ConexaoException, 
            RepositorioPesquisarException;
    public Fornecedor pesqCodForn(Integer codForn, boolean comProds) 
            throws ConexaoException, RepositorioPesquisarException;
    public Fornecedor pesqCnpj(String cnpj, boolean comProds) throws ConexaoException, 
            RepositorioPesquisarException;
    public List<Fornecedor> pesqFornsQueNaoFornecemEsteProd(Integer codProd)
            throws ConexaoException, RepositorioPesquisarException;
}
