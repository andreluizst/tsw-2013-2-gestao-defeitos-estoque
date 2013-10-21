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
import ce.model.basica.LocalEstoque;
import ce.util.GerenciadorConexao;
import ce.util.IGerenciadorConexao;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Andre
 */
public class RepositorioLocalEstoque implements IRepositorioLocalEstoque{
    private IGerenciadorConexao gerenciadorConexao;
    /**
     * Construtor padrão
     */
    public RepositorioLocalEstoque(){
        gerenciadorConexao= GerenciadorConexao.getInstancia();
    }
    /**
     * Inclui um novo local de estoque
     * @param le
     * Objeto da classe LocalEstoque que deseja incluir
     * @throws ConexaoException
     * Se houver algum problema com a conexão será lançada uma ConexaoException 
     * @throws RepositorioInserirException 
     * Se houver algum erro na execução do SQL será lançada uma exceção.
     */
    @Override
    public void inserir(LocalEstoque le) throws ConexaoException, 
            RepositorioInserirException{
        Connection c= gerenciadorConexao.conectar();
        String sql = "insert into LocalEstoque(descricao) values(?)";
        try{
            PreparedStatement pstmt = c.prepareStatement(sql);
            pstmt.setString(1, le.getDescricao());
            pstmt.execute();
            pstmt.close();
        }
        catch(SQLException e){
            throw new RepositorioInserirException(e,
                    RepositorioLocalEstoque.class.getName()+".inserir()");
        }
        finally{
            gerenciadorConexao.desconectar(c);
        }
    }
    /**
     * Altera um local de estoque
     * @param le
     * Objeto da classe LocalEstoque com as alterações desejadas. O código constante
     * neste objeto deve ser o código do local que sofrerá as alterações
     * e os demais atribudos devem conter os valores que foram modificados.
     * @throws ConexaoException
     * Se houver algum problema com a conexão será lançada uma ConexaoException 
     * @throws RepositorioAlterarException 
     * Se houver algum erro na execução do SQL será lançada uma exceção.
     */
    @Override
    public void alterar(LocalEstoque le)throws ConexaoException, 
            RepositorioAlterarException{
        Connection c= gerenciadorConexao.conectar();
        String sql = "update LocalEstoque set descricao=? where codLocal=?";
        try{
            PreparedStatement pstmt = c.prepareStatement(sql);
            pstmt.setString(1, le.getDescricao());
            pstmt.setInt(2, le.getCodLocal());
            pstmt.execute();
            pstmt.close();
        }
        catch(SQLException e){
            throw new RepositorioAlterarException(e,
                    RepositorioLocalEstoque.class.getName()+".alterar()");
        }
        finally{
            gerenciadorConexao.desconectar(c);
        }
    }
    /**
     * Exclui um local de estoque
     * @param codLocal
     * Código do local de estoque que deseja exclluir
     * @throws ConexaoException
     * Se houver algum problema com a conexão será lançada uma ConexaoException 
     * @throws RepositorioForeignKeyException
     * Se houver algum erro de chave estrangeira como por exemplo, ao tentar excluir
     * um local de estoque que está sendo referenciado por uma outra tabela do banco 
     * como a tabela de Entrada, será lançada uma exceção.
     * @throws RepositorioExcluirException 
     * Se houver algum erro na execução do SQL será lançada uma exceção.
     */
    @Override
    public void excluir(int codLocal)throws ConexaoException, 
            RepositorioForeignKeyException, RepositorioExcluirException{
        Connection c= gerenciadorConexao.conectar();
        String sql = "delete from LocalEstoque where codLocal=?";
        try{
            PreparedStatement pstmt = c.prepareStatement(sql);
            pstmt.setInt(1, codLocal);
            pstmt.execute();
            pstmt.close();
        }
        catch(SQLException e){
            String msg= e.getMessage().toLowerCase();
            if (msg!=null && msg.contains("foreign key constraint fails")){
                throw new RepositorioForeignKeyException(e,
                        RepositorioLocalEstoque.class.getName()+".excluir()");
            }
            throw new RepositorioExcluirException(e,
                    RepositorioLocalEstoque.class.getName()+".excluir()");
        }
        finally{
            gerenciadorConexao.desconectar(c);
        }
    }
    /**
     * Lista todos os locais de estoque existentes.
     * @return
     * Retorna uma lista com os locais de estoque.
     * @throws ConexaoException
     * Se houver algum problema com a conexão será lançada uma ConexaoException
     * @throws RepositorioListarException 
     * Se houver algum erro na execução do SQL será lançada uma exceção.
     */
    @Override
    public List<LocalEstoque> listar() throws ConexaoException,
            RepositorioListarException{
        List<LocalEstoque> lista = new ArrayList();
        LocalEstoque le;
        Connection c= gerenciadorConexao.conectar();
        String sql= "select * from LocalEstoque order by descricao";
        try{
            Statement stmt= c.createStatement();
            ResultSet rs= stmt.executeQuery(sql);
            while (rs.next()){
                le = new LocalEstoque(rs.getInt("codLocal"),
                        rs.getString("descricao")); 
                lista.add(le);
            }
            stmt.close();
            return lista;
        }
        catch(SQLException e){
            throw new RepositorioListarException(e,
                    RepositorioLocalEstoque.class.getName()+".listar()");
        }
        finally{
            gerenciadorConexao.desconectar(c);
        }
    }
    /**
     * Pesquisa locais de estoque pela descrição.
     * @param descricao
     * Descrição do local desejado. É possível a utilização de caracteres coringa
     * no parâmetro nome dando maior flexibilidade a pesquisa. Ex: "%S-03%".
     * @return
     * Retorna uma lista com o(s) local(is) de estoque encontrado(s).
     * @throws ConexaoException
     * Se houver algum problema com a conexão será lançada uma ConexaoException
     * @throws RepositorioPesquisarException 
     * Se houver algum erro na execução do SQL será lançada uma exceção.
     */
    @Override
    public List<LocalEstoque> pesquisar(String descricao) throws ConexaoException,
            RepositorioPesquisarException{
                List<LocalEstoque> lista = new ArrayList();
        LocalEstoque le;
        Connection c= gerenciadorConexao.conectar();
        String sql= "select * from LocalEstoque where descricao like ? order by descricao";
        try{
            PreparedStatement pstmt= c.prepareStatement(sql);
            pstmt.setString(1, descricao);
            ResultSet rs= pstmt.executeQuery();
            while (rs.next()){
                le = new LocalEstoque(rs.getInt("codLocal"), rs.getString("descricao")); 
                lista.add(le);
            }
            pstmt.close();
            return lista;
        }
        catch(SQLException e){
            throw new RepositorioPesquisarException(e,
                    RepositorioLocalEstoque.class.getName()+".pesquisar()");
        }
        finally{
            gerenciadorConexao.desconectar(c);
        }
    }
    /**
     * Pesquisa local de estoque pelo código.
     * @param codLocal
     * Código do local de estoque.
     * @return
     * Retorna um objeto LocalEstoque
     * @throws ConexaoException
     * Se houver algum problema com a conexão será lançada uma ConexaoException
     * @throws RepositorioPesquisarException 
     * Se houver algum erro na execução do SQL será lançada uma exceção.
     */
    @Override
    public LocalEstoque pesqCod(int codLocal) throws ConexaoException, 
            RepositorioPesquisarException{
        LocalEstoque le= null;
        Connection c= gerenciadorConexao.conectar();
        String sql= "select * from LocalEstoque where codLocal=?";        
        try{
            PreparedStatement pstmt= c.prepareStatement(sql);
            pstmt.setInt(1, codLocal);
            ResultSet rs= pstmt.executeQuery();
            if(rs.next()){
                le = new LocalEstoque(rs.getInt("codLocal"), 
                        rs.getString("descricao"));
            }
            pstmt.close();
            return le;
        }
        catch(SQLException e){
            throw new RepositorioPesquisarException(e,
                    RepositorioLocalEstoque.class.getName()+".pesqCod()");
        }
        finally{
            gerenciadorConexao.desconectar(c);
        }
    }
    
}
