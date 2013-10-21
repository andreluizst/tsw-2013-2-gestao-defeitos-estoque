package ce.model.dao;

import ce.erro.ConexaoException;
import ce.erro.RepositorioException;
import ce.erro.RepositorioForeignKeyException;
import ce.erro.RepositorioListarException;
import ce.erro.RepositorioInserirException;
import ce.erro.RepositorioAlterarException;
import ce.erro.RepositorioExcluirException;
import ce.erro.RepositorioPesquisarException;
import ce.model.basica.Categoria;
import ce.model.basica.Usuario;
import ce.util.GerenciadorConexao;
import ce.util.IGerenciadorConexao;
import ce.util.LogGenerator;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Andre
 * 
 * RepositorioCategoria implementada a interface IRepositorioCategoria e é
 * reponsável pela atualização e manipulação dos dados da tabela Categoria
 */
public class RepositorioCategoria implements IRepositorioCategoria{
    private Usuario user;
    private LogGenerator log;
    private IGerenciadorConexao gc;

    public RepositorioCategoria(){
         gc = GerenciadorConexao.getInstancia();
    }
    
    public RepositorioCategoria(Usuario user){
        this.user=user;
        log= LogGenerator.getInstancia();
        gc = GerenciadorConexao.getInstancia();
    }
    
    /**
     * Lista todas as categorias existentes.
     * @return
     * Retorna uma lista com as categorias
     * 
     * @throws ConexaoException
     * Se houver algum problema com a conexão será lançada uma ConexaoException
     * 
     * @throws RepositorioListarException 
     * Se houver algum erro na execução do SQL será lançada uma exceção do tipo RepositorioListarException
     */
    @Override
    public List<Categoria> listar()throws ConexaoException,
            RepositorioListarException{
        List<Categoria> lista = new ArrayList();
        Categoria cat = null;
        Connection c = gc.conectar();
        String sql = "SELECT codCateg, Descricao FROM categoria order by Descricao";
        try{
            Statement stm = c.createStatement();
            ResultSet rs = stm.executeQuery(sql);

            while( rs.next() ){
                cat = new Categoria();
                cat.setCodCateg( rs.getInt("codCateg") );
                cat.setDescricao( rs.getString("Descricao") );
                lista.add(cat);
            }
            rs.close();
            stm.close();
            return lista;
        }catch(SQLException e){
            throw new RepositorioListarException(e,
                    RepositorioCategoria.class.getName()+".listar()");
        }finally{
            gc.desconectar(c);
        }
    }
    /**
     * Inclui uma nova categoria.
     * 
     * @param obj
     * Objeto da classe Categoria que deverá ser incluido
     * 
     * @throws ConexaoException
     * Se houver algum problema com a conexão será lançada uma ConexaoException
     * 
     * @throws RepositorioInserirException 
     * Se houver algum erro na execução do SQL será lançada uma exceção.
     */
    @Override
    public void incluir(Categoria obj)throws ConexaoException,
            RepositorioInserirException{
        Connection c = gc.conectar();
        String sql = "INSERT INTO categoria (Descricao) VALUES (?)";
        try{
            PreparedStatement pstm = c.prepareStatement(sql);
            pstm.setString( 1, obj.getDescricao() );
            pstm.execute();
            pstm.close();
        }catch(SQLException e){
            throw new RepositorioInserirException(e, 
                    RepositorioCategoria.class.getName()+".incluir()");
        }finally{
            gc.desconectar(c);
        }
    }
    /**
     * Altera uma categoria.
     * @param obj
     * Objeto da classe Categoria que deverá ser incluido
     * 
     * @throws ConexaoException
     * Se houver algum problema com a conexão será lançada uma ConexaoException 
     * @throws RepositorioAlterarException 
     * Se houver algum erro na execução do SQL será lançada uma exceção. 
     */
    @Override
    public void alterar(Categoria obj)throws ConexaoException,
            RepositorioAlterarException{
        Connection c = gc.conectar();
        String sql = "UPDATE categoria SET descricao=? WHERE codCateg=?";
        try{
            PreparedStatement pstm = c.prepareStatement(sql);
            pstm.setString( 1, obj.getDescricao() );
            pstm.setInt(2, obj.getCodCateg() );
            pstm.execute();
            pstm.close();
        }catch(SQLException e){
            throw new RepositorioAlterarException(e, 
                    RepositorioCategoria.class.getName()+".alterar()");
        }finally{
            gc.desconectar(c);
        }
    }
    /**
     * Exclui uma categoria
     * 
     * @param codCateg
     * Código da cagetoria que deverá ser excluida.
     * 
     * @throws ConexaoException
     * Se houver algum problema com a conexão será lançada uma ConexaoException 
     * 
     * @throws RepositorioForeignKeyException
     * Se houver algum erro de chave estrangeira como por exemplo, ao tentar excluir
     * uma Categoria que está sendo referenciada por uma outra tabela do banco como
     * a tabela de Produtos, será lançada uma exceção.
     * 
     * @throws RepositorioExcluirException
     * Se houver algum erro na execução do SQL será lançada uma exceção. 
     */
    @Override
    public void excluir(Integer codCateg)throws ConexaoException,
            RepositorioForeignKeyException, RepositorioExcluirException{
        Connection c = gc.conectar();
        String sql = "DELETE FROM categoria WHERE codCateg=?";
        try{
            PreparedStatement pstm = c.prepareStatement(sql);
            pstm.setInt( 1, codCateg);
            pstm.execute();
            pstm.close();
        }catch(SQLException e){
            String msg= e.getMessage().toLowerCase();
            if (msg!=null && msg.contains("foreign key constraint fails")){
                throw new RepositorioForeignKeyException(e,
                    RepositorioCategoria.class.getName()+".excluir()");
            }
            throw new RepositorioExcluirException(e, 
                    RepositorioCategoria.class.getName()+".exluir()");
        }finally{
            gc.desconectar(c);
        }
    }
    /**
     * Pesquisa uma categoria pela descrição
     * 
     * @param descricao
     * @return
     * Retorna a Categoria encontrada
     * 
     * @throws ConexaoException
     * Se houver algum problema com a conexão será lançada uma ConexaoException 
     * 
     * @throws RepositorioPesquisarException 
     * Se houver algum erro na execução do SQL será lançada uma exceção. 
     */
    @Override
    public Categoria pesquisar(String descricao)throws ConexaoException,
            RepositorioPesquisarException{
        Categoria cat = null;
        Connection c = gc.conectar();
        String sql = "SELECT codCateg, descricao FROM categoria WHERE descricao=?";
        try{
            PreparedStatement pstm = c.prepareStatement(sql);
            pstm.setString(1, descricao);
            ResultSet rs = pstm.executeQuery();

            if( rs.next() ){
                cat = new Categoria();
                cat.setCodCateg( rs.getInt("codCateg") );
                cat.setDescricao( rs.getString("Descricao") );
            }
            rs.close();
            pstm.close();
            return cat;
        }catch(SQLException e){
            throw new RepositorioPesquisarException(e, 
                    RepositorioCategoria.class.getName()+".pesquisar()");
        }finally{
            gc.desconectar(c);
        }
    }
    /**
     * Pesquisa uma categoria pelo código
     * 
     * @param codCateg
     * @return
     * Retorna a Categoria encontrada
     * 
     * @throws ConexaoException
     * Se houver algum problema com a conexão será lançada uma ConexaoException 
     * 
     * @throws RepositorioPesquisarException 
     * Se houver algum erro na execução do SQL será lançada uma exceção. 
     */
    @Override
    public Categoria pesqPorCod(Integer codCateg)throws ConexaoException,
            RepositorioPesquisarException{
        Categoria categoria = null;
        Connection c = gc.conectar();
        String sql = "select codCateg, descricao from categoria where codCateg=?";
        try{
            PreparedStatement pstm = c.prepareStatement(sql);
            pstm.setInt(1, codCateg);
            ResultSet resultSet = pstm.executeQuery();

            if(resultSet.next()){
                categoria = new Categoria();
                categoria.setCodCateg( resultSet.getInt("codCateg") );
                categoria.setDescricao( resultSet.getString("Descricao") );
            }
            resultSet.close();
            pstm.close();
            return categoria;
        }
        catch(SQLException e){
            throw new RepositorioPesquisarException(e, 
                    RepositorioCategoria.class.getName()+".pesqPorCod()");
        }
        finally{
            gc.desconectar(c);
        }
    }

    /**
     * @return the user
     */
    public Usuario getUser() {
        return user;
    }

    /**
     * @param user the user to set
     */
    public void setUser(Usuario user) {
        this.user = user;
    }
}
