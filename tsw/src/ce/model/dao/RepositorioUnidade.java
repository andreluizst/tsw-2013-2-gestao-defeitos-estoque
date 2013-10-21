/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ce.model.dao;

import ce.erro.ConexaoException;
import ce.erro.RepositorioException;
import ce.erro.RepositorioInserirException;
import ce.erro.RepositorioAlterarException;
import ce.erro.RepositorioExcluirException;
import ce.erro.RepositorioForeignKeyException;
import ce.erro.RepositorioListarException;
import ce.erro.RepositorioPesquisarException;
import ce.model.basica.Unidade;
import ce.util.GerenciadorConexao;
import ce.util.IGerenciadorConexao;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author Andre
 */
public class RepositorioUnidade implements IRepositorioUnidade{
    private IGerenciadorConexao gerenciadorConexao;
    /**
     * Constgrutor padrão
     */
    public RepositorioUnidade(){
        gerenciadorConexao= GerenciadorConexao.getInstancia();
    }
    /**
     * Inclui uma nova unidade.
     * @param u
     * Objeto da classe Unidade que deseja incluir.
     * @throws ConexaoException
     * Se houver algum problema com a conexão será lançada uma ConexaoException 
     * @throws RepositorioInserirException 
     * Se houver algum erro na execução do SQL será lançada uma exceção.
     */
    @Override
    public void inserir(Unidade u) throws ConexaoException,
            RepositorioInserirException{
        Connection c= gerenciadorConexao.conectar();
        String sql = "insert into unidade(descricao) values(?)";
        try{
            PreparedStatement pstmt = c.prepareStatement(sql);
            pstmt.setString(1, u.getDescricao());
            pstmt.execute();
            pstmt.close();
        }
        catch(SQLException e){
            throw new RepositorioInserirException(e, 
                    RepositorioUnidade.class.getName()+".inserir()");
        }
        finally{
            gerenciadorConexao.desconectar(c);
        }
    }
    /**
     * Altera uma unidade.
     * @param u
     * Objeto da classe Unidade com as alterações desejadas. O código constante
     * neste objeto deve ser o código da unidade que sofrerá as alterações
     * e os demais atribudos devem conter os valores que foram modificados.
     * @throws ConexaoException
     * Se houver algum problema com a conexão será lançada uma ConexaoException 
     * @throws RepositorioAlterarException 
     * Se houver algum erro na execução do SQL será lançada uma exceção.
     */
    @Override
    public void alterar(Unidade u)throws ConexaoException, 
            RepositorioAlterarException{
        Connection c= gerenciadorConexao.conectar();
        String sql = "update unidade set descricao=? where codUnid=?";
        try{
            PreparedStatement pstmt = c.prepareStatement(sql);
            pstmt.setString(1, u.getDescricao());
            pstmt.setInt(2, u.getCodUnid());
            pstmt.execute();
            pstmt.close();
        }
        catch(SQLException e){
            throw new RepositorioAlterarException(e, 
                    RepositorioUnidade.class.getName()+".alterar()");
        }
        finally{
            gerenciadorConexao.desconectar(c);
        }
    }
    /**
     * Exclui uma unidade.
     * @param codUnid
     * Código da unidade que deseja exclluir
     * @throws ConexaoException
     * Se houver algum problema com a conexão será lançada uma ConexaoException 
     * @throws RepositorioForeignKeyException
     * Se houver algum erro de chave estrangeira como por exemplo, ao tentar excluir
     * uma unidade que está sendo referenciado por uma outra tabela do banco 
     * como a tabela Produto, será lançada uma exceção.
     * @throws RepositorioExcluirException 
     * Se houver algum erro na execução do SQL será lançada uma exceção.
     */
    @Override
    public void excluir(Integer codUnid)throws ConexaoException, 
            RepositorioForeignKeyException, RepositorioExcluirException{
        Connection c= gerenciadorConexao.conectar();
        String sql = "delete from unidade where codUnid=?";
        try{
            PreparedStatement pstmt = c.prepareStatement(sql);
            pstmt.setInt(1, codUnid);
            pstmt.execute();
            pstmt.close();
        }
        catch(SQLException e){
            String msg= e.getMessage().toLowerCase();
            if (msg!=null && msg.contains("foreign key constraint fails")){
                throw new RepositorioForeignKeyException(e,
                        RepositorioUnidade.class.getName()+".excluir()");
            }
            throw new RepositorioExcluirException(e, 
                    RepositorioUnidade.class.getName()+".excluir()");
        }
        finally{
            gerenciadorConexao.desconectar(c);
        }
    }
    /**
     * Lista todas as unidades existentes.
     * @return
     * Retorna uma lista com as unidades.
     * @throws ConexaoException
     * Se houver algum problema com a conexão será lançada uma ConexaoException
     * @throws RepositorioListarException 
     * Se houver algum erro na execução do SQL será lançada uma exceção.
     */
    @Override
    public List<Unidade> listar() throws ConexaoException,
            RepositorioListarException{
        List<Unidade> lista = new ArrayList();
        Unidade u;
        Connection c= gerenciadorConexao.conectar();
        String sql= "select * from unidade order by descricao";
        try{
            Statement stmt= c.createStatement();
            ResultSet rs= stmt.executeQuery(sql);
            while (rs.next()){
                u = new Unidade(rs.getInt("codUnid"), rs.getString("descricao")); 
                lista.add(u);
            }
            stmt.close();
            return lista;
        }
        catch(SQLException e){
            throw new RepositorioListarException(e, 
                    RepositorioUnidade.class.getName()+".listar()");
        }
        finally{
            gerenciadorConexao.desconectar(c);
        }
    }
    /**
     * Pesquisa unidade(s) pela descrição.
     * @param descricao
     * Descrição da unidade desejada. É possível a utilização de caracteres coringa
     * no parâmetro nome dando maior flexibilidade a pesquisa. Ex: "Pe%".
     * @return
     * Retorna uma lista com a(s) unidade(s) encontrada(s).
     * @throws ConexaoException
     * Se houver algum problema com a conexão será lançada uma ConexaoException
     * @throws RepositorioPesquisarException 
     * Se houver algum erro na execução do SQL será lançada uma exceção.
     */
    @Override
    public List<Unidade> pesquisar(String descricao) throws ConexaoException,
            RepositorioPesquisarException{
                List<Unidade> lista = new ArrayList();
        Unidade u;
        Connection c= gerenciadorConexao.conectar();
        String sql= "select * from unidade where descricao like ? order by descricao";
        try{
            PreparedStatement pstmt= c.prepareStatement(sql);
            pstmt.setString(1, descricao);
            ResultSet rs= pstmt.executeQuery();
            while (rs.next()){
                u = new Unidade(rs.getInt("codUnid"), rs.getString("descricao")); 
                lista.add(u);
            }
            pstmt.close();
            return lista;
        }
        catch(SQLException e){
            throw new RepositorioPesquisarException(e, 
                    RepositorioUnidade.class.getName()+".pesquisar()");
        }
        finally{
            gerenciadorConexao.desconectar(c);
        }
    }
    /**
     * Pesquisa unidade pelo código.
     * @param codUnid
     * Código da unidade.
     * @return
     * Retorna um objeto Unidade.
     * @throws ConexaoException
     * Se houver algum problema com a conexão será lançada uma ConexaoException
     * @throws RepositorioPesquisarException 
     * Se houver algum erro na execução do SQL será lançada uma exceção.
     */
    @Override
    public Unidade pesqCod(Integer codUnid) throws ConexaoException, 
            RepositorioPesquisarException{
        Unidade u= null;
        Connection c= gerenciadorConexao.conectar();
        String sql= "SELECT * FROM unidade WHERE codUnid=?";        
        try{
            PreparedStatement pstmt= c.prepareStatement(sql);
            pstmt.setInt(1, codUnid);
            //pstmt.setLong(1, codUnid);
            ResultSet rs = pstmt.executeQuery();
            if(rs.next()){
                u = new Unidade(rs.getInt("codUnid"), rs.getString("descricao"));
            }
            pstmt.close();
            rs.close();
            return u;
        }
        catch(SQLException e){
            throw new RepositorioPesquisarException(e, 
                    RepositorioUnidade.class.getName()+".pesqCod()");
        }
        finally{
            gerenciadorConexao.desconectar(c);
        }
    }
}
