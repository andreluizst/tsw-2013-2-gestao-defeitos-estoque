/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ce.model.dao;

import ce.erro.ConexaoException;
import ce.erro.RepositorioAlterarException;
import ce.erro.RepositorioException;
import ce.erro.RepositorioExcluirException;
import ce.erro.RepositorioForeignKeyException;
import ce.erro.RepositorioInserirException;
import ce.erro.RepositorioListarException;
import ce.erro.RepositorioPesquisarException;
import ce.model.basica.Entrada;
import ce.util.GerenciadorConexao;
import ce.util.IGerenciadorConexao;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
/**
 *
 * @author Andre
 */
public class RepositorioEntrada implements IRepositorioEntrada{
    private IGerenciadorConexao gerenciadorConexao;
    private ResourceBundle rb= ResourceBundle.getBundle("ce.erro.Erro");
    /**
     * Construtor padrão
     */
    public RepositorioEntrada(){
        gerenciadorConexao = GerenciadorConexao.getInstancia();
    }
    /**
     * Inclui uma nova entrada.
     * @param e
     * Objeto da classe Entrada que deseja incluir
     * @throws ConexaoException
     * Se houver algum problema com a conexão será lançada uma ConexaoException 
     * @throws RepositorioInserirException 
     * Se houver algum erro na execução do SQL será lançada uma exceção.
     */
    @Override
    public void inserir(Entrada e) throws ConexaoException, 
            RepositorioInserirException{
        Connection c= gerenciadorConexao.conectar();
        String sql="Insert into entrada (codProd, codForn, dataEnt, lote, qtde, saldo)"
                + " values(?,?,?,?,?,?)";
        try{
            PreparedStatement pstmt = c.prepareStatement(sql);
            pstmt.setInt(1, e.getProduto().getCodProd());
            pstmt.setInt(2, e.getFornecedor().getCodForn());
            pstmt.setDate(3, e.getDataToMySqlDate());
            pstmt.setString(4, e.getLote());
            pstmt.setDouble(5, e.getQtde());
            pstmt.setDouble(6, e.getSaldo());
            pstmt.executeUpdate();
            pstmt.close();
            IRepositorioProduto rpProd= new RepositorioProduto();
            /*e.getProduto().setQtdeEstoq(e.getProduto().getQtdeEstoq()+e.getQtde());
            rpProd.alterar(e.getProduto());*/
            rpProd.atualizarQtde(e.getProduto());
        }
        catch(SQLException sqlE){
            throw new RepositorioInserirException(sqlE, 
                    RepositorioEntrada.class.getName()+".inserir()");
        }
        catch(RepositorioAlterarException ae){
            //if (reverterInserir(e))
                throw new RepositorioInserirException(
                        rb.getString("CtrlErroAtlzQtde"),
                        RepositorioEntrada.class.getName()+".inserir()");

        }
        finally{
            gerenciadorConexao.desconectar(c);
        }
    }
    /**
     * Altera uma entrada.
     * @param e
     * Objeto da classe Entrada com as alterações desejadas.
     * O número da entrada constante neste objeto deve ser o número da entrada
     * que sofrerá as alterações e os demais atribudos devem conter os valores
     * que foram modificados.
     * @throws ConexaoException
     * Se houver algum problema com a conexão será lançada uma ConexaoException 
     * @throws RepositorioAlterarException 
     * Se houver algum erro na execução do SQL será lançada uma exceção.
     */
    @Override
    public void alterar(Entrada e) throws ConexaoException, 
            RepositorioAlterarException{
        Connection c= gerenciadorConexao.conectar();
        String sql="Update entrada set codProd=?, codForn=?, dataEnt=?, lote=?,"
                + " qtde=?, saldo=? where codEnt=?";
        try{
            PreparedStatement pstmt = c.prepareStatement(sql);
            pstmt.setInt(1, e.getProduto().getCodProd());
            pstmt.setInt(2, e.getFornecedor().getCodForn());
            pstmt.setDate(3, e.getDataToMySqlDate());
            pstmt.setString(4, e.getLote());
            pstmt.setDouble(5, e.getQtde());
            pstmt.setDouble(6, e.getSaldo());
            pstmt.setInt(7, e.getNumero());
            pstmt.executeUpdate();
            pstmt.close();
            IRepositorioProduto rpProd= new RepositorioProduto();
            rpProd.atualizarQtde(e.getProduto());
        }
        catch(SQLException sqlE){
            throw new RepositorioAlterarException(sqlE, 
                    RepositorioEntrada.class.getName()+".alterar()");
        }
        catch(RepositorioAlterarException ae){
                throw new RepositorioAlterarException(
                        rb.getString("CtrlErroAtlzQtde"),
                        RepositorioEntrada.class.getName()+".alterar()");

        }
        finally{
            gerenciadorConexao.desconectar(c);
        }
    }
    /**
     * Exclui uma entrada.
     * @param e
     * Entrada que deseja exclluir
     * @throws ConexaoException
     * Se houver algum problema com a conexão será lançada uma ConexaoException 
     * @throws RepositorioForeignKeyException
     * Se houver algum erro de chave estrangeira como por exemplo, ao tentar excluir
     * uma entrada que está sendo referenciado por uma outra tabela do banco 
     * como a tabela Saida, será lançada uma exceção.
     * @throws RepositorioExcluirException 
     * Se houver algum erro na execução do SQL será lançada uma exceção.
     */
    @Override
    public void excluir(Entrada e) throws ConexaoException, 
            RepositorioForeignKeyException, RepositorioExcluirException{
        Connection c= gerenciadorConexao.conectar();
        String sql="delete from Entrada where codEnt=?";
        try{
            PreparedStatement pstmt = c.prepareStatement(sql);
            pstmt.setInt(1, e.getNumero());
            pstmt.executeUpdate();
            pstmt.close();
            IRepositorioProduto rpProd= new RepositorioProduto();
            rpProd.atualizarQtde(e.getProduto());
        }
        catch(SQLException sqlE){
            String msg= sqlE.getMessage().toLowerCase();
            if (msg!=null && msg.contains("foreign key constraint fails")){
                throw new RepositorioForeignKeyException(sqlE,
                        RepositorioEntrada.class.getName()+".excluir()");
            }
            throw new RepositorioExcluirException(sqlE, 
                    RepositorioEntrada.class.getName()+".excluir()");
        }
        catch(RepositorioAlterarException ae){
            throw new RepositorioExcluirException(
                    rb.getString("CtrlErroAtlzQtde"),
                    RepositorioEntrada.class.getName()+".excluir()");
        }
        finally{
            gerenciadorConexao.desconectar(c);
        }
    }
    
    /**
     * Método auxiliar do repositorio para dinamizar a listagem.
     * @param comportamentoToString
     * Define o comportamento dos objetos do tipo Entrada contidos na lista retornada.
     * @return
     * Retorna uma lista com as entradas.
     * @throws ConexaoException
     * Se houver algum problema com a conexão será lançada uma ConexaoException
     * @throws RepositorioListarException 
     * Se houver algum erro na execução do SQL será lançada uma exceção. 
     */
    private List<Entrada> listar(int comportamentoToString) 
            throws ConexaoException, RepositorioListarException{
        List<Entrada> lista = new ArrayList();
        Entrada e=null;
        Connection c= gerenciadorConexao.conectar();
        String sql= "select * from Entrada";
        if (comportamentoToString == Entrada.TO_STRING_PROD_LOTE_SALDO){
            sql= "select * from Entrada where saldo > 0";
        }
        try{
            Statement stmt= c.createStatement();
            ResultSet rs= stmt.executeQuery(sql);
            IRepositorioProduto rpProd= new RepositorioProduto();
            IRepositorioFornecedor rpForn= new RepositorioFornecedor();
            while (rs.next()){
                e = new Entrada(comportamentoToString, rs.getInt("codEnt"),
                        rpProd.pesqCodProd(rs.getInt("codProd"), false),
                        rpForn.pesqCodForn(rs.getInt("codForn"), false),
                        rs.getDate("dataEnt"), rs.getString("lote"),
                        rs.getDouble("qtde"), rs.getDouble("saldo"));
                
                lista.add(e);
            }
            rs.close();
            stmt.close();
            return lista;
        }
        catch(SQLException ex){
            throw new RepositorioListarException(ex,
                    RepositorioEntrada.class.getName()+".listar()");
        }
        catch(RepositorioException ex){
            throw new RepositorioListarException(ex, 
                    RepositorioEntrada.class.getName()+".listar()."+ex.getPathClassCall());
        }
        finally{
            gerenciadorConexao.desconectar(c);
        }
    }
    
    /**
     * Lista todas as entradas existentes.
     * @return
     * Retorna uma lista com as entradas.
     * @throws ConexaoException
     * Se houver algum problema com a conexão será lançada uma ConexaoException
     * @throws RepositorioListarException 
     * Se houver algum erro na execução do SQL será lançada uma exceção.
     */
    @Override
    public List<Entrada> listar() throws ConexaoException, 
            RepositorioListarException{
        return listar(Entrada.TO_STRING_DEFAULT);
    }
    
    /**
     * Lista todas as entradas com saldo > 0. 
     * @return
     * Retorna uma lista com as entradas.
     * @throws ConexaoException
     * Se houver algum problema com a conexão será lançada uma ConexaoException
     * @throws RepositorioListarException 
     * Se houver algum erro na execução do SQL será lançada uma exceção.
     */
    @Override
    public List<Entrada> listarComSaldo() throws ConexaoException, 
            RepositorioListarException{
        return listar(Entrada.TO_STRING_PROD_LOTE_SALDO);
    }
    
    /**
     * Pesquisa entrada pelo número.
     * @param num
     * Número da entrada.
     * @return
     * Retorna um objeto Entrada.
     * @throws ConexaoException
     * Se houver algum problema com a conexão será lançada uma ConexaoException
     * @throws RepositorioPesquisarException 
     * Se houver algum erro na execução do SQL será lançada uma exceção.
     */
    @Override
    public Entrada pesquisar(Integer num) throws ConexaoException, 
            RepositorioPesquisarException{
        Entrada e=null;
        Connection c= gerenciadorConexao.conectar();
        String sql= "select * from Entrada where codEnt=?";
        try{
            PreparedStatement pstmt= c.prepareStatement(sql);
            pstmt.setInt(1, num);
            ResultSet rs= pstmt.executeQuery();
            IRepositorioProduto rpProd= new RepositorioProduto();
            IRepositorioFornecedor rpForn= new RepositorioFornecedor();
            while (rs.next()){
                e = new Entrada(rs.getInt("codEnt"),
                        rpProd.pesqCodProd(rs.getInt("codProd"), false),
                        rpForn.pesqCodForn(rs.getInt("codForn"), false),
                        rs.getDate("dataEnt"), rs.getString("lote"),
                        rs.getDouble("qtde"), rs.getDouble("saldo"));
            }
            rs.close();
            pstmt.close();
            return e;
        }
        catch(SQLException ex){
            throw new RepositorioPesquisarException(ex,
                    RepositorioEntrada.class.getName()+".pesquisar(num)");
        }
        catch(RepositorioException ex){
            throw new RepositorioPesquisarException(ex, 
                    RepositorioEntrada.class.getName()+".pesquisar(num)."+ex.getPathClassCall());
        }
        finally{
            gerenciadorConexao.desconectar(c);
        }
    }
    
    /**
     * Pesquisa entradas no período de datas informado.
     * 
     * @param entrada
     * Objeto do tipo Entrada que terá seus atributos utilizados para filtrar a 
     * pesquisa com exceção de data de entrada e quantidade.
     * Se o objeto não for nulo, os atributos abaixo serão válidos se:
     * Número da entrada for maior que zero;
     * Código do fornecedor for maior que zero;
     * Código do produto for maior que zero;
     * Lote não for vazio. (pode conter coringa SQL como o caracter %)
     * 
     * @param dataInicial
     * Data inicial no formato texto (dd/MM/yyyy).
     * 
     * @param dataFinal
     * Data final no formato texto (dd/MM/yyyy).
     * 
     * @return
     * Retorna uma lista com as entradas.
     * 
     * @throws ConexaoException
     * Se houver algum problema com a conexão será lançada uma ConexaoException
     * 
     * @throws RepositorioPesquisarException 
     * Se houver algum erro na execução do SQL ou com as datas informadas será lançada uma exceção.
     */
    @Override
    public List<Entrada> pesquisar(Entrada entrada, String dataInicial, String dataFinal)
            throws ConexaoException, RepositorioPesquisarException{
        int nFields=2;
        int pesqCod= 0;
        int pesqForn= 0;
        int pesqProd= 0;
        int pesqLote= 0;
        List<Entrada> lista = new ArrayList();
        java.sql.Date sqlDateInicial;
        java.sql.Date sqlDateFinal;
        try{
            sqlDateInicial= new java.sql.Date(new SimpleDateFormat("dd/MM/yyyy").parse(dataInicial).getTime());
            sqlDateFinal= new java.sql.Date(new SimpleDateFormat("dd/MM/yyyy").parse(dataFinal).getTime());
        }catch(java.text.ParseException pe){
            throw new RepositorioPesquisarException(pe,
                    RepositorioEntrada.class.getName()+".pesquisar(dataInicial, dataFinal)");
        }
        Entrada e=null;
        Connection c= gerenciadorConexao.conectar();
        String sql= "select * from Entrada "
                + "where (dataEnt >= ? and dataEnt <= ?)";
        if (entrada != null){
            if ((entrada.getNumero()!=null) && (entrada.getNumero() > 0)){
                sql+= " and codEnt=?";
                nFields++;
                pesqCod= nFields;
            }
            if ((entrada.getFornecedor().getCodForn()!=null) 
                    && (entrada.getFornecedor().getCodForn() > 0)){
                sql+= " and codForn=?";
                nFields++;
                pesqForn= nFields;
            }
            if ((entrada.getProduto().getCodProd()!=null) 
                    && (entrada.getProduto().getCodProd() > 0)){
                sql+= " and codProd=?";
                nFields++;
                pesqProd= nFields;
            }
            if ((entrada.getLote()!= null) && (entrada.getLote().length() > 0)){
                sql+= " and lote like ?";
                nFields++;
                pesqLote= nFields;
            }
        }
        sql+= " order by dataEnt desc, codEnt asc";
        try{
            PreparedStatement pstmt= c.prepareStatement(sql);
            pstmt.setDate(1, sqlDateInicial);
            pstmt.setDate(2, sqlDateFinal);
            if (pesqCod > 0){
                pstmt.setInt(pesqCod, entrada.getNumero());
            }
            if (pesqForn > 0){
                pstmt.setInt(pesqForn, entrada.getFornecedor().getCodForn());
            }
            if (pesqProd > 0){
                pstmt.setInt(pesqProd, entrada.getProduto().getCodProd());
            }
            if (pesqLote > 0){
                pstmt.setString(pesqLote, entrada.getLote());
            }
            ResultSet rs= pstmt.executeQuery();
            IRepositorioProduto rpProd= new RepositorioProduto();
            IRepositorioFornecedor rpForn= new RepositorioFornecedor();
            while (rs.next()){
                e = new Entrada(rs.getInt("codEnt"),
                        rpProd.pesqCodProd(rs.getInt("codProd"), false),
                        rpForn.pesqCodForn(rs.getInt("codForn"), false),
                        rs.getDate("dataEnt"), rs.getString("lote"),
                        rs.getDouble("qtde"), rs.getDouble("saldo"));
                
                lista.add(e);
            }
            rs.close();
            pstmt.close();
            return lista;
        }
        catch(SQLException ex){
            throw new RepositorioPesquisarException(ex,
                    RepositorioEntrada.class.getName()+".pesquisar(dataInicial, dataFinal)");
        }
        catch(RepositorioException ex){
            throw new RepositorioPesquisarException(ex, 
                    RepositorioEntrada.class.getName()+".pesquisar(dataInicial, dataFinal)"+ex.getPathClassCall());
        }
        finally{
            gerenciadorConexao.desconectar(c);
        }
    }
    
    /**
     * Pesquisa entradas no período de datas informado.
     * @param dataInicial
     * Data inicial no formato texto (dd/MM/yyyy).
     * @param dataFinal
     * Data final no formato texto (dd/MM/yyyy).
     * @return
     * Retorna uma lista com as entradas.
     * @throws ConexaoException
     * Se houver algum problema com a conexão será lançada uma ConexaoException
     * @throws RepositorioPesquisarException 
     * Se houver algum erro na execução do SQL ou com as datas informadas será lançada uma exceção.
     */
    @Override
    public List<Entrada> pesquisar(String dataInicial, String dataFinal)
            throws ConexaoException, RepositorioPesquisarException{
        return pesquisar(null, dataInicial, dataFinal);
        /*List<Entrada> lista = new ArrayList();
        java.sql.Date sqlDateInicial;
        java.sql.Date sqlDateFinal;
        try{
            sqlDateInicial= new java.sql.Date(new SimpleDateFormat("yyyy-MM-dd").parse(dataInicial).getTime());
            sqlDateFinal= new java.sql.Date(new SimpleDateFormat("yyyy-MM-dd").parse(dataFinal).getTime());
        }catch(java.text.ParseException pe){
            throw new RepositorioPesquisarException(pe,
                    RepositorioEntrada.class.getName()+".pesquisarNoPeriodo()");
        }
        Entrada e=null;
        Connection c= gerenciadorConexao.conectar();
        String sql= "select * from Entrada "
                + "where dataEnt >= ? and dataEnt <= ?"
                + "order by dataEnt desc, codEnt asc";
        try{
            PreparedStatement pstmt= c.prepareStatement(sql);
            pstmt.setDate(1, sqlDateInicial);
            pstmt.setDate(2, sqlDateFinal);
            ResultSet rs= pstmt.executeQuery();
            IRepositorioProduto rpProd= new RepositorioProduto();
            IRepositorioFornecedor rpForn= new RepositorioFornecedor();
            while (rs.next()){
                e = new Entrada(rs.getInt("codEnt"),
                        rpProd.pesqCodProd(rs.getInt("codProd"), false),
                        rpForn.pesqCodForn(rs.getInt("codForn"), false),
                        rs.getDate("dataEnt"), rs.getString("lote"),
                        rs.getDouble("qtde"));
                
                lista.add(e);
            }
            rs.close();
            pstmt.close();
            return lista;
        }
        catch(SQLException ex){
            throw new RepositorioPesquisarException(ex,
                    RepositorioEntrada.class.getName()+".pesquisarNoPeriodo()");
        }
        catch(RepositorioException ex){
            throw new RepositorioPesquisarException(ex, 
                    RepositorioEntrada.class.getName()+".pesquisarNoPeriodo()."+ex.getPathClassCall());
        }
        finally{
            gerenciadorConexao.desconectar(c);
        }*/
    }
    
    
}
