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
import ce.model.basica.Estado;
import ce.util.GerenciadorConexao;
import ce.util.IGerenciadorConexao;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author Andre
 */
public class RepositorioEstado implements IRepositorioEstado{
    private IGerenciadorConexao gerenciadorConexao;
    
    public RepositorioEstado(){
        gerenciadorConexao= GerenciadorConexao.getInstancia();
    }
    
    @Override
    public void inserir(Estado e) throws ConexaoException, 
                RepositorioInserirException{
        Connection c= gerenciadorConexao.conectar();
        String sql = "insert into estados(uf, descricao) values(?, ?)";
        try{
            PreparedStatement pstmt = c.prepareStatement(sql);
            pstmt.setString(1, e.getUf().toUpperCase());
            pstmt.setString(2, e.getDescricao());
            pstmt.execute();
            pstmt.close();
        }
        catch(SQLException ex){
            throw new RepositorioInserirException(ex, 
                    RepositorioEstado.class.getName()+".inserir()");
        }
        finally{
            gerenciadorConexao.desconectar(c);
        }
    }
    
    @Override
    public void alterar(Estado e)throws ConexaoException, 
            RepositorioAlterarException{
        Connection c= gerenciadorConexao.conectar();
        String sql = "update estados set descricao=? where uf=?";
        try{
            PreparedStatement pstmt = c.prepareStatement(sql);
            pstmt.setString(1, e.getUf().toUpperCase());
            pstmt.setString(2, e.getDescricao());
            pstmt.execute();
            pstmt.close();
        }
        catch(SQLException ex){
            throw new RepositorioAlterarException(ex, 
                    RepositorioEstado.class.getName()+".alterar()");
        }
        finally{
            gerenciadorConexao.desconectar(c);
        }
    }
    
    @Override
    public void excluir(String uf)throws ConexaoException, 
        RepositorioForeignKeyException, RepositorioExcluirException{
        Connection c= gerenciadorConexao.conectar();
        String sql = "delete from estados where uf=?";
        try{
            PreparedStatement pstmt = c.prepareStatement(sql);
            pstmt.setString(1, uf);
            pstmt.execute();
            pstmt.close();
        }
        catch(SQLException e){
            String msg= e.getMessage().toLowerCase();
            if (msg!=null && msg.contains("foreign key constraint fails")){
                throw new RepositorioForeignKeyException(e,
                    RepositorioEstado.class.getName()+".excluir()");
            }
            throw new RepositorioExcluirException(e, 
                    RepositorioEstado.class.getName()+".excluir()");
        }
        finally{
            gerenciadorConexao.desconectar(c);
        }
    }
    
    @Override
    public List<Estado> listar() throws ConexaoException,
            RepositorioListarException{
        List<Estado> lista = new ArrayList();
        Estado e;
        Connection c= gerenciadorConexao.conectar();
        String sql= "select * from estados order by uf";
        try{
            Statement stmt= c.createStatement();
            ResultSet rs= stmt.executeQuery(sql);
            while (rs.next()){
                e = new Estado(rs.getString("UF"), rs.getString("descricao")); 
                lista.add(e);
            }
            stmt.close();
            return lista;
        }
        catch(SQLException ex){
            throw new RepositorioListarException(ex, 
                    RepositorioEstado.class.getName()+".listar()");
        }
        finally{
            gerenciadorConexao.desconectar(c);
        }
    }
    
    @Override
    public List<Estado> pesquisar(String descricao) throws ConexaoException,
            RepositorioPesquisarException{
                List<Estado> lista = new ArrayList();
        Estado e;
        Connection c= gerenciadorConexao.conectar();
        String sql= "select * from estados where descricao like ? order by descricao";
        try{
            PreparedStatement pstmt= c.prepareStatement(sql);
            pstmt.setString(1, descricao);
            ResultSet rs= pstmt.executeQuery();
            while (rs.next()){
                e = new Estado(rs.getString("UF"), rs.getString("descricao"));
                lista.add(e);
            }
            pstmt.close();
            return lista;
        }
        catch(SQLException ex){
            throw new RepositorioPesquisarException(ex, 
                    RepositorioEstado.class.getName()+".pesquisar()");
        }
        finally{
            gerenciadorConexao.desconectar(c);
        }
    }
    
    @Override
    public Estado pesqUf(String uf) throws ConexaoException, 
            RepositorioPesquisarException{
        Estado e= null;
        Connection c= gerenciadorConexao.conectar();
        String sql= "select * from estados where uf=? order by uf";
        try{
            PreparedStatement pstmt= c.prepareStatement(sql);
            pstmt.setString(1, uf);
            ResultSet rs= pstmt.executeQuery();
            if(rs.next()){
                e = new Estado(rs.getString("UF"), rs.getString("descricao")); 
            }
            pstmt.close();
            return e;
        }
        catch(SQLException ex){
            throw new RepositorioPesquisarException(ex, 
                    RepositorioEstado.class.getName()+".pesqUf()");
        }
        finally{
            gerenciadorConexao.desconectar(c);
        }
    }
    
}
