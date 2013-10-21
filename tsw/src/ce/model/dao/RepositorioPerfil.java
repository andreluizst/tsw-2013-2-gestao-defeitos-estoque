/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ce.model.dao;

import ce.erro.ConexaoException;
import ce.erro.RepositorioInserirException;
import ce.erro.RepositorioAlterarException;
import ce.erro.RepositorioExcluirException;
import ce.erro.RepositorioForeignKeyException;
import ce.erro.RepositorioListarException;
import ce.erro.RepositorioPesquisarException;
import ce.model.basica.Perfil;
import ce.util.GerenciadorConexao;
import ce.util.IGerenciadorConexao;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author Andre
 */
public class RepositorioPerfil implements IRepositorioPerfil {
    private IGerenciadorConexao gerenciadorConexao;
    /**
     * Contrutor padrão
     */
    public RepositorioPerfil(){
        gerenciadorConexao= GerenciadorConexao.getInstancia();
    }
    /**
     * Inclui um novo perfil
     * @param p
     * Objeto da classe Perfil que deseja incluir
     * @throws ConexaoException
     * Se houver algum problema com a conexão será lançada uma ConexaoException 
     * @throws RepositorioInserirException 
     * Se houver algum erro na execução do SQL será lançada uma exceção.
     */
    @Override
    public void inserir(Perfil p) throws ConexaoException, 
            RepositorioInserirException{
        Connection c= gerenciadorConexao.conectar();
        String sql= "Insert into Perfil (nome) values(?) ";
        try{
            PreparedStatement pstmt= c.prepareStatement(sql);
            pstmt.setString(1, p.getNome());
            pstmt.executeUpdate();
            pstmt.close();
        }
        catch(SQLException e){
            throw new RepositorioInserirException(e, 
                    RepositorioPerfil.class.getName()+".inserir()");
        }
        finally{
            gerenciadorConexao.desconectar(c);
        }
        
    }
    /**
     * Altera um perfil
     * @param p
     * Objeto da classe Perfil com as alterações desejadas. O código constante
     * neste objeto deve ser o código do perfil que sofrerá as alterações
     * e os demais atribudos devem conter os valores que foram modificados.
     * @throws ConexaoException
     * Se houver algum problema com a conexão será lançada uma ConexaoException 
     * @throws RepositorioAlterarException 
     * Se houver algum erro na execução do SQL será lançada uma exceção.
     */
    @Override
    public void alterar(Perfil p)throws ConexaoException, 
            RepositorioAlterarException{
        Connection c= gerenciadorConexao.conectar();
        String sql= "Update Perfil set nome=? where codPerfil=?";
        try{
            PreparedStatement pstmt= c.prepareStatement(sql);
            pstmt.setString(1, p.getNome());
            pstmt.setInt(2, p.getCodPerfil());
            pstmt.executeUpdate();
            pstmt.close();
        }
        catch(SQLException e){
            throw new RepositorioAlterarException(e, 
                    RepositorioPerfil.class.getName()+".alterar()");
        }
        finally{
            gerenciadorConexao.desconectar(c);
        }
    }
    /**
     * Exclui um local de estoque
     * @param p
     * Perfil que deseja exclluir
     * @throws ConexaoException
     * Se houver algum problema com a conexão será lançada uma ConexaoException 
     * @throws RepositorioForeignKeyException
     * Se houver algum erro de chave estrangeira como por exemplo, ao tentar excluir
     * um perfil que está sendo referenciado por uma outra tabela do banco 
     * como a tabela Usuario, será lançada uma exceção.
     * @throws RepositorioExcluirException 
     * Se houver algum erro na execução do SQL será lançada uma exceção.
     */
    @Override
    public void excluir(Perfil p)throws ConexaoException, 
            RepositorioForeignKeyException, RepositorioExcluirException{
        Connection c= gerenciadorConexao.conectar();
        String sql= "Delete from Perfil where codPerfil=?";
        try{
            PreparedStatement pstmt= c.prepareStatement(sql);
            pstmt.setInt(1, p.getCodPerfil());
            pstmt.executeUpdate();
            pstmt.close();
        }
        catch(SQLException e){
            String msg= e.getMessage().toLowerCase();
            if (msg!=null && msg.contains("foreign key constraint fails")){
                throw new RepositorioForeignKeyException(e,
                        RepositorioPerfil.class.getName()+".excluir()");
            }
            throw new RepositorioExcluirException(e, 
                    RepositorioPerfil.class.getName()+".excluir()");
        }
        finally{
            gerenciadorConexao.desconectar(c);
        }
    }
    /**
     * Lista todos os perfis existentes.
     * @return
     * Retorna uma lista com os perfis.
     * @throws ConexaoException
     * Se houver algum problema com a conexão será lançada uma ConexaoException
     * @throws RepositorioListarException 
     * Se houver algum erro na execução do SQL será lançada uma exceção.
     */
    @Override
    public List<Perfil> listar()throws ConexaoException, 
            RepositorioListarException{
        List<Perfil> lista= new ArrayList();
        Perfil p= null;
        Connection c= gerenciadorConexao.conectar();
        String sql= "Select * from Perfil";
        try{
            Statement stmt= c.createStatement();
            ResultSet rs= stmt.executeQuery(sql);
            while (rs.next()){
                p= new Perfil(rs.getInt("codPerfil"), rs.getString("nome"));
                if (p != null){
                    lista.add(p);
                }
            }
            rs.close();
            stmt.close();
            return lista;
        }
        catch(SQLException e){
            throw new RepositorioListarException(e, 
                    RepositorioPerfil.class.getName()+".listar()");
        }
        finally{
            gerenciadorConexao.desconectar(c);
        }
        
    }
    /**
     * Pesquisa perfil pelo código.
     * @param codPerfil
     * Código do perfil.
     * @return
     * Retorna um objeto Perfil
     * @throws ConexaoException
     * Se houver algum problema com a conexão será lançada uma ConexaoException
     * @throws RepositorioPesquisarException 
     * Se houver algum erro na execução do SQL será lançada uma exceção.
     */
    @Override
    public Perfil pesqCod(int codPerfil)throws ConexaoException, 
            RepositorioPesquisarException{
        Connection c= gerenciadorConexao.conectar();
        Perfil p=null;
        String sql= "Select * from Perfil where codPerfil=?";
        try{
            PreparedStatement pstmt= c.prepareStatement(sql);
            pstmt.setInt(1, codPerfil);
            ResultSet rs= pstmt.executeQuery();
            while (rs.next()){
                p= new Perfil(rs.getInt("codPerfil"), rs.getString("nome"));
            }
            rs.close();
            pstmt.close();
            /*if (p==null){
                throw new RepositorioPesquisarException("Perfl."+codPerfil+" não encontrado!",
                        "RepositorioPerfil.pesqCod()");
            }*/
            return p;
        }
        catch(SQLException e){
            throw new RepositorioPesquisarException(e, 
                    RepositorioPerfil.class.getName()+".pesqCod()");
        }
        finally{
            gerenciadorConexao.desconectar(c);
        }
    }
    /**
     * Pesquisa perfil pelo nome
     * @param nome
     * Nome do perfil desejado
     * @return
     * Retorna um objeto Perfil
     * @throws ConexaoException
     * Se houver algum problema com a conexão será lançada uma ConexaoException
     * @throws RepositorioPesquisarException 
     * Se houver algum erro na execução do SQL será lançada uma exceção.
     */
    @Override
    public Perfil pesquisar(String nome) throws ConexaoException, 
            RepositorioPesquisarException{
        Connection c= gerenciadorConexao.conectar();
        Perfil p=null;
        String sql= "Select * from Perfil where nome=?";
        try{
            PreparedStatement pstmt= c.prepareStatement(sql);
            pstmt.setString(1, nome);
            ResultSet rs= pstmt.executeQuery();
            while (rs.next()){
                p= new Perfil(rs.getInt("codPerfil"), rs.getString("nome"));
            }
            rs.close();
            pstmt.close();
            /*if (p==null){
                throw new RepositorioPesquisarException("Perfl."+nome+" não encontrado!",
                        "RepositorioPerfil.pesqCod()");
            }*/
            return p;
        }
        catch(SQLException e){
            throw new RepositorioPesquisarException(e, 
                    RepositorioPerfil.class.getName()+".pesquisar()");
        }
        finally{
            gerenciadorConexao.desconectar(c);
        }
    }
    
}
