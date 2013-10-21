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
import ce.model.basica.Perfil;
import java.util.List;
/**
 *
 * @author Andre
 */
public interface IRepositorioPerfil {
    public void inserir(Perfil p) throws ConexaoException, 
            RepositorioInserirException;
    public void alterar(Perfil p) throws ConexaoException, 
            RepositorioAlterarException;
    public void excluir(Perfil p) throws ConexaoException, 
            RepositorioForeignKeyException, RepositorioExcluirException;
    public List<Perfil> listar() throws ConexaoException, 
            RepositorioListarException;
    public Perfil pesquisar(String nome) throws ConexaoException, 
            RepositorioPesquisarException;
    public Perfil pesqCod(int codPerfil) throws ConexaoException, 
            RepositorioPesquisarException;
}
