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
import ce.model.basica.Usuario;
import java.util.List;
/**
 *
 * @author Andre
 */
public interface IRepositorioUsuario {
    public void inserir(Usuario u) throws ConexaoException, 
            RepositorioInserirException;
    public void alterar(Usuario u) throws ConexaoException, 
            RepositorioAlterarException;
    public void excluir(Usuario u) throws ConexaoException, 
            RepositorioForeignKeyException, RepositorioExcluirException;
    public List<Usuario> listar() throws ConexaoException, 
            RepositorioListarException;
    public List<Usuario> pesquisar(String nome) throws ConexaoException, 
            RepositorioPesquisarException;
    public Usuario pesqCod(int cod) throws ConexaoException, 
            RepositorioPesquisarException;
    public Usuario pesqCpf(String cpf) throws ConexaoException, 
            RepositorioPesquisarException;
}
