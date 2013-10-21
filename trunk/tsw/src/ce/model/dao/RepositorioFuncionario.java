/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ce.model.dao;

import ce.erro.ConexaoException;
import ce.erro.RepositorioInserirException;
import ce.erro.RepositorioAlterarException;
import ce.erro.RepositorioException;
import ce.erro.RepositorioExcluirException;
import ce.erro.RepositorioForeignKeyException;
import ce.erro.RepositorioListarException;
import ce.erro.RepositorioPesquisarException;
import ce.model.basica.Funcionario;
import ce.util.GerenciadorConexao;
import ce.util.IGerenciadorConexao;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author Andre
 */
public class RepositorioFuncionario implements IRepositorioFuncionario{
    private IGerenciadorConexao gerenciadorConexao;
    /**
     * Construtor padrão
     */
    public RepositorioFuncionario(){
        gerenciadorConexao= GerenciadorConexao.getInstancia();
    }
    /**
     * Inclui um novo funcionário
     * @param f
     * Objeto da classe Funcionario que deseja incluir
     * @throws ConexaoException
     * Se houver algum problema com a conexão será lançada uma ConexaoException 
     * @throws RepositorioInserirException 
     * Se houver algum erro na execução do SQL será lançada uma exceção.
     */
    @Override
    public void inserir(Funcionario f) throws ConexaoException, 
            RepositorioInserirException{
        Connection c= gerenciadorConexao.conectar();
        String sql = "insert into funcionario(cpf, nome, logradouro,"
                + "num, comp, bairro, municipio, uf, cep, fone, email, dtNasc)"
                + " values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try{
            PreparedStatement pstmt = c.prepareStatement(sql);
            pstmt.setString(1, f.getCpf());
            pstmt.setString(2, f.getNome());
            pstmt.setString(3, f.getLogradouro());
            pstmt.setInt(4, f.getNum());
            pstmt.setString(5, f.getComp());
            pstmt.setString(6, f.getBairro());
            pstmt.setString(7, f.getMunicipio());
            pstmt.setString(8, f.getEstado().getUf());
            pstmt.setString(9, f.getCep());
            pstmt.setString(10, f.getFone());
            pstmt.setString(11, f.getEmail());
            pstmt.setString(12, f.getDtNasc());
            pstmt.executeUpdate();
            pstmt.close();
        }
        catch(SQLException e){
            throw new RepositorioInserirException(e, 
                    RepositorioFuncionario.class.getName()+".inserir()");
        }
        finally{
            gerenciadorConexao.desconectar(c);
        }
    }
    /**
     * Altera um funcionário
     * @param f
     * Objeto da classe Funcionario com as alterações desejadas. O código constante
     * neste objeto deve ser o código do funcionário que sofrerá as alterações
     * e os demais atribudos devem conter os valores que foram modificados.
     * @throws ConexaoException
     * Se houver algum problema com a conexão será lançada uma ConexaoException 
     * @throws RepositorioAlterarException 
     * Se houver algum erro na execução do SQL será lançada uma exceção.
     */
    @Override
    public void alterar(Funcionario f)throws ConexaoException, 
            RepositorioAlterarException{
        Connection c= gerenciadorConexao.conectar();
        String sql = "update Funcionario set DtNasc=?, nome=?, logradouro=?,"
                + "num=?,comp=?,bairro=?, municipio=?, uf=?, cep=?, fone=?,"
                + "email=? where cpf=?";
        try{
            PreparedStatement pstmt = c.prepareStatement(sql);
            pstmt.setString(1, f.getDtNasc());
            pstmt.setString(2, f.getNome());
            pstmt.setString(3, f.getLogradouro());
            pstmt.setInt(4, f.getNum());
            pstmt.setString(5, f.getComp());
            pstmt.setString(6, f.getBairro());
            pstmt.setString(7, f.getMunicipio());
            pstmt.setString(8, f.getEstado().getUf());
            pstmt.setString(9, f.getCep());
            pstmt.setString(10, f.getFone());
            pstmt.setString(11, f.getEmail());
            pstmt.setString(12, f.getCpf());
            pstmt.executeUpdate();
            pstmt.close();
        }
        catch(SQLException e){
            throw new RepositorioAlterarException(e, 
                    RepositorioFuncionario.class.getName()+".alterar()");
        }
        finally{
            gerenciadorConexao.desconectar(c);
        }
    }
    /**
     * Exclui um funcionário
     * @param cpf
     * CPF do funcionário que deseja exclluir
     * @throws ConexaoException
     * Se houver algum problema com a conexão será lançada uma ConexaoException 
     * @throws RepositorioForeignKeyException
     * Se houver algum erro de chave estrangeira como por exemplo, ao tentar excluir
     * um Funcionario que está sendo referenciado por uma outra tabela do banco como
     * a tabela de Usuario, será lançada uma exceção.
     * @throws RepositorioExcluirException 
     * Se houver algum erro na execução do SQL será lançada uma exceção.
     */
    @Override
    public void excluir(String cpf)throws ConexaoException, 
            RepositorioForeignKeyException, RepositorioExcluirException{
        Connection c= gerenciadorConexao.conectar();
        String sql = "delete from funcionario where cpf=?";
        try{
            PreparedStatement pstmt = c.prepareStatement(sql);
            pstmt.setString(1, cpf);
            pstmt.executeUpdate();
            pstmt.close();
        }
        catch(SQLException e){
            String msg= e.getMessage().toLowerCase();
            if (msg!=null && msg.contains("foreign key constraint fails")){
                throw new RepositorioForeignKeyException(e,
                        RepositorioFuncionario.class.getName()+".excluir()");
            }
            throw new RepositorioExcluirException(e, 
                    RepositorioFuncionario.class.getName()+".excluir()");
        }
        finally{
            gerenciadorConexao.desconectar(c);
        }
    }
    /**
     * Lista todos os funcionário existentes.
     * @return
     * Retorna uma lista com os funcionáios
     * @throws ConexaoException
     * Se houver algum problema com a conexão será lançada uma ConexaoException
     * @throws RepositorioListarException 
     * Se houver algum erro na execução do SQL será lançada uma exceção.
     */
    @Override
    public List<Funcionario> listar() throws ConexaoException,
            RepositorioListarException{
        List<Funcionario> lista = new ArrayList();
        Funcionario f;
        Connection c= gerenciadorConexao.conectar();
        String sql= "select * from funcionario order by nome";
        try{
            Statement stmt= c.createStatement();
            ResultSet rs= stmt.executeQuery(sql);
            IRepositorioEstado rpEst= new RepositorioEstado();
            while (rs.next()){
                f = new Funcionario(rs.getString("CPF"), rs.getString("nome"), 
                        rs.getString("DtNasc"), rs.getString("logradouro"),
                        rs.getInt("Num"), rs.getString("Comp"), 
                        rs.getString("Bairro"), rs.getString("Municipio"),
                        rpEst.pesqUf(rs.getString("UF")), rs.getString("CEP"), 
                        rs.getString("Fone"), rs.getString("Email"));
                lista.add(f);
            }
            rs.close();
            stmt.close();
            return lista;
        }
        catch(SQLException e){
            throw new RepositorioListarException(e, 
                    RepositorioFuncionario.class.getName()+".listar()");
        }
        catch(RepositorioException ex){
            throw new RepositorioListarException(ex, 
                    RepositorioFuncionario.class.getName()+".listar()."+ex.getPathClassCall());
        }
        finally{
            gerenciadorConexao.desconectar(c);
        }
    }
    /**
     * Pesquisa funcionários por nome
     * @param nome
     * Nome do funcionário desejado.
     * É possível a utilização de caracteres coringa no parâmetro nome dando
     * maior flexibilidade a pesquisa. Ex: "Comer%".
     * @return
     * Retorna uma lista com o(s) funcionário(s) encontrado(s).
     * @throws ConexaoException
     * Se houver algum problema com a conexão será lançada uma ConexaoException
     * @throws RepositorioPesquisarException 
     * Se houver algum erro na execução do SQL será lançada uma exceção.
     */
    @Override
    public List<Funcionario> pesquisar(String nome) throws ConexaoException,
            RepositorioPesquisarException{
                List<Funcionario> lista = new ArrayList();
        Funcionario f;
        Connection c= gerenciadorConexao.conectar();
        String sql= "select * from funcionario where nome like ? order by nome";
        try{
            PreparedStatement pstmt= c.prepareStatement(sql);
            pstmt.setString(1, nome);
            ResultSet rs= pstmt.executeQuery();
            IRepositorioEstado rpEst= new RepositorioEstado();
            while (rs.next()){
                f = new Funcionario(rs.getString("CPF"), rs.getString("nome"), 
                        rs.getString("DtNasc"), rs.getString("logradouro"),
                        rs.getInt("Num"), rs.getString("Comp"), 
                        rs.getString("Bairro"), rs.getString("Municipio"),
                        rpEst.pesqUf(rs.getString("UF")), rs.getString("CEP"), 
                        rs.getString("Fone"), rs.getString("Email"));
                lista.add(f);
            }
            rs.close();
            pstmt.close();
            return lista;
        }
        catch(SQLException e){
            throw new RepositorioPesquisarException(e, 
                    RepositorioFuncionario.class.getName()+".pesquisar()");
        }
        catch(RepositorioException ex){
            throw new RepositorioPesquisarException(ex, 
                    RepositorioFuncionario.class.getName()+".pesquisar()."+ex.getPathClassCall());
        }
        finally{
            gerenciadorConexao.desconectar(c);
        }
    }
    /**
     * Pesquisa um funcionário
     * @param cpf
     * CPF do funcionário que deseja
     * @return
     * Retorna um objeto Funcionario
     * @throws ConexaoException
     * Se houver algum problema com a conexão será lançada uma ConexaoException
     * @throws RepositorioPesquisarException 
     * Se houver algum erro na execução do SQL será lançada uma exceção.
     */
    @Override
    public Funcionario pesqCpf(String cpf) throws ConexaoException, 
            RepositorioPesquisarException{
        Funcionario f= null;
        Connection c= gerenciadorConexao.conectar();
        String sql= "select * from funcionario where cpf=?";        
        try{
            PreparedStatement pstmt= c.prepareStatement(sql);
            pstmt.setString(1, cpf);
            ResultSet rs= pstmt.executeQuery();
            IRepositorioEstado rpEst= new RepositorioEstado();
            if(rs.next()){
                f = new Funcionario(rs.getString("CPF"), rs.getString("nome"), 
                        rs.getString("DtNasc"), rs.getString("logradouro"),
                        rs.getInt("Num"), rs.getString("Comp"), 
                        rs.getString("Bairro"), rs.getString("Municipio"),
                        rpEst.pesqUf(rs.getString("UF")), rs.getString("CEP"), 
                        rs.getString("Fone"), rs.getString("Email"));
            }
            rs.close();
            pstmt.close();
            /*if (f==null){
                throw new RepositorioPesquisarException("Funcionario."+cpf+" não encontrado!" );
            }*/
            return f;
        }
        catch(SQLException e){
            throw new RepositorioPesquisarException(e, 
                    RepositorioFuncionario.class.getName()+".pesqCpf()");
        }
        catch(RepositorioException ex){
            throw new RepositorioPesquisarException(ex, 
                    RepositorioFuncionario.class.getName()+".pesqCpf()."+ex.getPathClassCall());
        }
        finally{
            gerenciadorConexao.desconectar(c);
        }
    }
    
}
