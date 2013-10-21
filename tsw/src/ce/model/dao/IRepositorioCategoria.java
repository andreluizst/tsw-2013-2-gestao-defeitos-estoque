package ce.model.dao;

import ce.erro.ConexaoException;
import ce.erro.RepositorioListarException;
import ce.erro.RepositorioInserirException;
import ce.erro.RepositorioAlterarException;
import ce.erro.RepositorioExcluirException;
import ce.erro.RepositorioForeignKeyException;
import ce.erro.RepositorioPesquisarException;
import ce.model.basica.Categoria;
import java.util.List;

/**
 *
 * @author professor
 */
public interface IRepositorioCategoria {
    public List listar() throws ConexaoException, RepositorioListarException;
    public void incluir(Categoria obj) throws ConexaoException,RepositorioInserirException;
    public void alterar(Categoria obj) throws ConexaoException,RepositorioAlterarException;
    public void excluir(Integer codCateg) throws ConexaoException,
            RepositorioForeignKeyException, RepositorioExcluirException;
    public Categoria pesquisar(String descricao) throws ConexaoException,RepositorioPesquisarException;
    public Categoria pesqPorCod(Integer codCateg) throws ConexaoException,RepositorioPesquisarException;
}
