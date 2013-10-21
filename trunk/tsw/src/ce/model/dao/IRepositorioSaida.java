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
import ce.model.basica.Saida;
import java.util.List;
/**
 *
 * @author Andre
 */
public interface IRepositorioSaida {
    public void inserir(Saida s) throws ConexaoException, 
            RepositorioInserirException;
    public void alterar(Saida s) throws ConexaoException, 
            RepositorioAlterarException;
    public void excluir(Saida s) throws ConexaoException, 
            RepositorioExcluirException;
    public List<Saida> listar() throws ConexaoException, 
            RepositorioListarException;
    public Saida pesqNum(Integer Num) 
            throws ConexaoException, RepositorioPesquisarException;
    public List<Saida> pesquisar(Saida s, String dataInicial, String dataFinal)
            throws ConexaoException, RepositorioPesquisarException;
}
