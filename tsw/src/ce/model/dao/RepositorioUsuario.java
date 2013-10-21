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
import ce.model.basica.Perfil;
import ce.model.basica.Usuario;
import ce.model.basica.Funcionario;
import ce.util.GerenciadorConexao;
import ce.util.IGerenciadorConexao;
import ce.model.dao.IRepositorioPerfil;
import ce.model.dao.RepositorioPerfil;
import ce.model.dao.IRepositorioFuncionario;
import ce.model.dao.RepositorioFuncionario;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author Andre
 */
public class RepositorioUsuario implements IRepositorioUsuario{
    //private Usuario user=null;
    private IGerenciadorConexao gerenciadorConexao;
    /**
     * Construtor padrão
     */
    public RepositorioUsuario(){
        gerenciadorConexao= GerenciadorConexao.getInstancia();
    }
    /**
     * Inclui um novo usuário.
     * @param u
     * Objeto da classe Usuario que deseja incluir
     * @throws ConexaoException
     * Se houver algum problema com a conexão será lançada uma ConexaoException 
     * @throws RepositorioInserirException 
     * Se houver algum erro na execução do SQL será lançada uma exceção.
     */
    @Override
    public void inserir(Usuario u) throws ConexaoException, 
            RepositorioInserirException{
        Connection c= gerenciadorConexao.conectar();
        String sql= "Insert into Usuario(nome, codPerfil, cpf, senha)"
                + " values(?,?,?, ?)";
        try{
            PreparedStatement pstmt= c.prepareStatement(sql);
            pstmt.setString(1, u.getNome());
            pstmt.setInt(2, u.getPerfil().getCodPerfil());
            pstmt.setString(3, u.getFuncionario().getCpf());
            pstmt.setString(4, u.getSenha());
            pstmt.executeUpdate();
            pstmt.close();
        }
        catch(SQLException e){
            throw new RepositorioInserirException(e,
                    RepositorioUsuario.class.getName()+".inserir()");
        }
        finally{
            gerenciadorConexao.desconectar(c);
        }
    }
    /**
     * Altera um usuário.
     * @param u
     * Objeto da classe Usuario com as alterações desejadas. O código constante
     * neste objeto deve ser o código do usuário que sofrerá as alterações
     * e os demais atribudos devem conter os valores que foram modificados.
     * @throws ConexaoException
     * Se houver algum problema com a conexão será lançada uma ConexaoException 
     * @throws RepositorioAlterarException 
     * Se houver algum erro na execução do SQL será lançada uma exceção.
     */
    @Override
    public void alterar(Usuario u) throws ConexaoException, 
            RepositorioAlterarException{
        Connection c= gerenciadorConexao.conectar();
        String sql= "Update Usuario set nome=?, codPerfil=?, senha=?, cpf=?"
                + " where codUsr=?";
        try{
            PreparedStatement pstmt= c.prepareStatement(sql);
            pstmt.setString(1, u.getNome());
            pstmt.setInt(2, u.getPerfil().getCodPerfil());
            pstmt.setString(3, u.getSenha());
            pstmt.setString(4, u.getFuncionario().getCpf());
            pstmt.setInt(5, u.getCodUsuario());
            pstmt.executeUpdate();
            pstmt.close();
        }
        catch(SQLException e){
            throw new RepositorioAlterarException(e,
                    RepositorioUsuario.class.getName()+".alterar()");
        }
        finally{
            gerenciadorConexao.desconectar(c);
        }
    }
    /**
     * Exclui um usuário.
     * @param u
     * Usuário que deseja exclluir
     * @throws ConexaoException
     * Se houver algum problema com a conexão será lançada uma ConexaoException 
     * @throws RepositorioForeignKeyException
     * Se houver algum erro de chave estrangeira como por exemplo, ao tentar excluir
     * um usuário que está sendo referenciado por uma outra tabela do banco 
     * como a tabela Entrada ou Saida, será lançada uma exceção.
     * @throws RepositorioExcluirException 
     * Se houver algum erro na execução do SQL será lançada uma exceção.
     */
    @Override
    public void excluir(Usuario u) throws ConexaoException, 
            RepositorioForeignKeyException, RepositorioExcluirException{
        Connection c= gerenciadorConexao.conectar();
        String sql= "delete from usuario where codUsr=?";
        try{
            PreparedStatement pstmt= c.prepareStatement(sql);
            pstmt.setInt(1, u.getCodUsuario());
            pstmt.executeUpdate();
            pstmt.close();
        }
        catch(SQLException e){
            String msg= e.getMessage().toLowerCase();
            if (msg!=null && msg.contains("foreign key constraint fails")){
                throw new RepositorioForeignKeyException(e,
                    RepositorioUsuario.class.getName()+".excluir()");
            }
            throw new RepositorioExcluirException(e,
                    RepositorioUsuario.class.getName()+".excluir()");
        }
        finally{
            gerenciadorConexao.desconectar(c);
        }
    }
    /**
     * Lista todos os usuários existentes.
     * @return
     * Retorna uma lista com os usuários.
     * @throws ConexaoException
     * Se houver algum problema com a conexão será lançada uma ConexaoException
     * @throws RepositorioListarException 
     * Se houver algum erro na execução do SQL será lançada uma exceção.
     */
    @Override
    public List<Usuario> listar() throws ConexaoException, 
            RepositorioListarException{
        List<Usuario> lista= new ArrayList();
        Usuario u= null;
        String sql= "Select * from Usuario order by nome";
        Connection c= gerenciadorConexao.conectar();
        try{
            Statement stmt= c.createStatement();
            ResultSet rs= stmt.executeQuery(sql);
            IRepositorioPerfil rpPerfil= new RepositorioPerfil();
            IRepositorioFuncionario rpFun= new RepositorioFuncionario();
            while (rs.next()){
                u= new Usuario(rs.getInt("codUsr"), rs.getString("nome"),
                        rpPerfil.pesqCod(rs.getInt("codPerfil")),
                        rpFun.pesqCpf(rs.getString("cpf")),
                        rs.getString("senha"));
                
                lista.add(u);
            }
            rs.close();
            stmt.close();
            return lista;
        }
        catch(SQLException e){
            throw new RepositorioListarException(e,
                    RepositorioUsuario.class.getName()+".listar()");
        }
        catch(RepositorioException ex){
            throw new RepositorioListarException(ex,
                    RepositorioUsuario.class.getName()+".listar()."+ex.getPathClassCall());
        }
        finally{
            gerenciadorConexao.desconectar(c);
        }
    }
    /**
     * Pesquisa usuários pelo nome
     * @param nome
     * Nome do usuário desejado.
     * É possível a utilização de caracteres coringa no parâmetro nome dando
     * maior flexibilidade a pesquisa. Ex: "%Silva".
     * @return
     * Retorna uma lista com o(s) usuário(s) encontrado(s).
     * @throws ConexaoException
     * Se houver algum problema com a conexão será lançada uma ConexaoException
     * @throws RepositorioPesquisarException 
     * Se houver algum erro na execução do SQL será lançada uma exceção.
     */
    @Override
    public List<Usuario> pesquisar(String nome) throws ConexaoException, 
            RepositorioPesquisarException{
        List<Usuario> lista= new ArrayList();
        Usuario u= null;
        String sql= "Select * from Usuario where nome like ? order by nome";
        Connection c= gerenciadorConexao.conectar();
        try{
            PreparedStatement pstmt= c.prepareStatement(sql);
            pstmt.setString(1, nome);
            ResultSet rs= pstmt.executeQuery();
            IRepositorioPerfil rpPerfil= new RepositorioPerfil();
            IRepositorioFuncionario rpFun= new RepositorioFuncionario();
            while (rs.next()){
                u= new Usuario(rs.getInt("codUsr"), rs.getString("nome"),
                        rpPerfil.pesqCod(rs.getInt("codPerfil")),
                        rpFun.pesqCpf(rs.getString("cpf")),
                        rs.getString("senha"));
                
                lista.add(u);
            }
            rs.close();
            pstmt.close();
            return lista;
        }
        catch(SQLException e){
            throw new RepositorioPesquisarException(e,
                    RepositorioUsuario.class.getName()+".pesquisar()");
        }
        catch(RepositorioException ex){
            throw new RepositorioPesquisarException(ex,
                    RepositorioUsuario.class.getName()+".pesquisar()."+ex.getPathClassCall());
        }
        finally{
            gerenciadorConexao.desconectar(c);
        }
    }
    /**
     * Pesquisa usuário pelo código.
     * @param cod
     * Código do usuário.
     * @return
     * Retorna um objeto Usuario.
     * @throws ConexaoException
     * Se houver algum problema com a conexão será lançada uma ConexaoException
     * @throws RepositorioPesquisarException 
     * Se houver algum erro na execução do SQL será lançada uma exceção.
     */
    @Override
    public Usuario pesqCod(int cod)throws ConexaoException, 
            RepositorioPesquisarException{
        Usuario u= null;
        String sql= "Select * from Usuario where codUsr=?";
        Connection c= gerenciadorConexao.conectar();
        try{
            PreparedStatement pstmt= c.prepareStatement(sql);
            pstmt.setInt(1, cod);
            ResultSet rs= pstmt.executeQuery();
            IRepositorioPerfil rpPerfil= new RepositorioPerfil();
            IRepositorioFuncionario rpFun= new RepositorioFuncionario();
            while (rs.next()){
                u= new Usuario(rs.getInt("codUsr"), rs.getString("nome"),
                        rpPerfil.pesqCod(rs.getInt("codPerfil")),
                        rpFun.pesqCpf(rs.getString("cpf")),
                        rs.getString("senha"));
            }
            rs.close();
            pstmt.close();
            /*if (u==null){
                throw new RepositorioPesquisarException("Usuario."+cod+" não encontrado!",
                        "RepositorioUsuario.pesqCod()");
            }*/
            return u;
        }
        catch(SQLException e){
            throw new RepositorioPesquisarException(e,
                    RepositorioUsuario.class.getName()+".pesqCod()");
        }
        catch(RepositorioException ex){
            throw new RepositorioPesquisarException(ex,
                    RepositorioUsuario.class.getName()+".pesqCod()."+ex.getPathClassCall());
        }
        finally{
            gerenciadorConexao.desconectar(c);
        }
    }
    /**
     * Pesquisa usuário pelo CPF.
     * @param cpf
     * CPF do usuário.
     * @return
     * Retorna um objeto Usuario.
     * @throws ConexaoException
     * Se houver algum problema com a conexão será lançada uma ConexaoException
     * @throws RepositorioPesquisarException 
     * Se houver algum erro na execução do SQL será lançada uma exceção.
     */
    @Override
    public Usuario pesqCpf(String cpf)throws ConexaoException, 
            RepositorioPesquisarException{
        Usuario u= null;
        String sql= "Select * from Usuario where cpf=?";
        Connection c= gerenciadorConexao.conectar();
        try{
            PreparedStatement pstmt= c.prepareStatement(sql);
            pstmt.setString(1, cpf);
            ResultSet rs= pstmt.executeQuery();
            IRepositorioPerfil rpPerfil= new RepositorioPerfil();
            IRepositorioFuncionario rpFun= new RepositorioFuncionario();
            while (rs.next()){
                u= new Usuario(rs.getInt("codUsr"), rs.getString("nome"),
                        rpPerfil.pesqCod(rs.getInt("codPerfil")),
                        rpFun.pesqCpf(rs.getString("cpf")),
                        rs.getString("senha"));
            }
            rs.close();
            pstmt.close();
            /*if (u==null){
                throw new RepositorioPesquisarException("Usuario."+cpf+" não encontrado!",
                        "RepositorioUsuario.pesqCod()");
            }*/
            return u;
        }
        catch(SQLException e){
            throw new RepositorioPesquisarException(e,
                    RepositorioUsuario.class.getName()+".pesqCpf()");
        }
        catch(RepositorioException ex){
            throw new RepositorioPesquisarException(ex,
                    RepositorioUsuario.class.getName()+".pesqCpf()."+ex.getPathClassCall());
        }
        finally{
            gerenciadorConexao.desconectar(c);
        }
    }

}
