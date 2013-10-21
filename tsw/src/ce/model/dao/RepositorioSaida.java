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
import ce.erro.RepositorioListarException;
import ce.erro.RepositorioPesquisarException;
import ce.model.basica.Fornecedor;
import ce.model.basica.Produto;
import ce.model.basica.Entrada;
import ce.model.basica.Saida;
import ce.util.GerenciadorConexao;
import ce.util.IGerenciadorConexao;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author Andre
 */
public class RepositorioSaida implements IRepositorioSaida{
    private IGerenciadorConexao gerenciadorConexao;
    /**
     * Construtor padrão
     */
    public RepositorioSaida(){
        gerenciadorConexao= GerenciadorConexao.getInstancia();
    }
    /**
     * Inclui uma nova saída.
     * @param s
     * Objeto da classe Saida que deseja incluir
     * @throws ConexaoException
     * Se houver algum problema com a conexão será lançada uma ConexaoException 
     * @throws RepositorioInserirException 
     * Se houver algum erro na execução do SQL será lançada uma exceção.
     */
    @Override
    public void inserir(Saida s) throws ConexaoException, 
            RepositorioInserirException{
        Connection c= gerenciadorConexao.conectar();
        String sql="Insert into saida (codEnt, dataSaida, qtde) values(?,?,?)";
        try{
            PreparedStatement pstmt= c.prepareStatement(sql);
            pstmt.setInt(1, s.getEntrada().getNumero());
            pstmt.setDate(2, s.getDataToMySqlDate());
            pstmt.setDouble(3, s.getQtde());
            pstmt.executeUpdate();
            pstmt.close();
            s.getEntrada().setSaldo(s.getEntrada().getQtde()-s.getQtde());
            IRepositorioEntrada rpEnt= new RepositorioEntrada();
            rpEnt.alterar(s.getEntrada());
        }
        catch(SQLException | RepositorioAlterarException e){
            throw new RepositorioInserirException(e, 
                    RepositorioSaida.class.getName()+".inserir()");
        }
        finally{
            gerenciadorConexao.desconectar(c);
        }
    }
    /**
     * Altera uma saída.
     * @param s
     * Objeto da classe Saida com as alterações desejadas.
     * O número da saída constante neste objeto deve ser o número da saída
     * que sofrerá as alterações e os demais atribudos devem conter os valores
     * que foram modificados.
     * @throws ConexaoException
     * Se houver algum problema com a conexão será lançada uma ConexaoException 
     * @throws RepositorioAlterarException 
     * Se houver algum erro na execução do SQL será lançada uma exceção.
     */
    @Override
    public void alterar(Saida s) throws ConexaoException, 
            RepositorioAlterarException{
        Connection c= gerenciadorConexao.conectar();
        String sql="Update saida set codEnt=?, dataSaida=?, qtde=? where codSaida=?";
        try{
            PreparedStatement pstmt= c.prepareStatement(sql);
            pstmt.setInt(1, s.getEntrada().getNumero());
            pstmt.setDate(2, s.getDataToMySqlDate());
            pstmt.setDouble(3, s.getQtde());
            pstmt.setInt(4, s.getCodSaida());
            pstmt.executeUpdate();
            pstmt.close();
            s.getEntrada().setSaldo(s.getEntrada().getQtde()-s.getQtde());
            IRepositorioEntrada rpEnt= new RepositorioEntrada();
            rpEnt.alterar(s.getEntrada());
        }
        catch(SQLException e){
            throw new RepositorioAlterarException(e, 
                    RepositorioSaida.class.getName()+".alterar()");
        }
        finally{
            gerenciadorConexao.desconectar(c);
        }
    }
    /**
     * Excui uma saída.
     * @param s
     * Objeto da classe Saida que deseja exluir.
     * @throws ConexaoException
     * Se houver algum problema com a conexão será lançada uma ConexaoException 
     * @throws RepositorioExcluirException 
     * Se houver algum erro na execução do SQL será lançada uma exceção.
     */
    @Override
    public void excluir(Saida s) throws ConexaoException, 
            RepositorioExcluirException{
        Connection c= gerenciadorConexao.conectar();
        String sql="Delete form saida where codSaida=?";
        try{
            PreparedStatement pstmt= c.prepareStatement(sql);
            pstmt.setInt(1, s.getCodSaida());
            pstmt.executeUpdate();
            pstmt.close();
        }
        catch(SQLException e){
            throw new RepositorioExcluirException(e, 
                    RepositorioSaida.class.getName()+".excluir()");
        }
        finally{
            gerenciadorConexao.desconectar(c);
        }
    }
    /**
     * Lista todas as saídas existentes.
     * @return
     * Retorna uma lista com as saídas.
     * @throws ConexaoException
     * Se houver algum problema com a conexão será lançada uma ConexaoException
     * @throws RepositorioListarException 
     * Se houver algum erro na execução do SQL será lançada uma exceção.
     */
    @Override
    public List<Saida> listar() throws ConexaoException, 
            RepositorioListarException{
        List<Saida> lista= new ArrayList();
        Saida s= null;
        Entrada e= null;
        Produto p= null;
        Fornecedor f= null;
        Connection c= gerenciadorConexao.conectar();
        /*String sql= "select s.codSaida, s.codEnt, s.dtSaida, s.qtde, e.codProd,"
                + " e.codForn, e.qtde as qtdeEnt, e.lote, p.descProd, f.nome, f.CNPJ"
                + " from Saida as s inner join Entrada as e where e.codEnt = s.codEnt"
                + " inner join Produto as p where p.codProd = e.codProd"
                + " inner join Fornecedor as f where f.codForn = e.codForn";*/
        String sql= "select codSaida, codEnt, dataSaida, qtde from saida "
                + "order by dataSaida desc, codSaida asc";
        try{
            Statement stmt= c.createStatement();
            ResultSet rs= stmt.executeQuery(sql);
            IRepositorioEntrada rpEnt= new RepositorioEntrada();
            while (rs.next()){
                s= new Saida(rs.getInt("codSaida"), rs.getDouble("qtde"), 
                        rs.getDate("dataSaida"),
                        rpEnt.pesquisar(rs.getInt("codEnt")));
                /*e= new Entrada();
                e.setNumero(rs.getInt("codEnt"));
                e.setLote(rs.getString("lote"));
                e.setQtde(rs.getDouble("qtdeEnt"));
                e.setDataEntrada(rs.getDate("dataEnt"));
                p= new Produto();
                p.setCodProd(rs.getInt("codProd"));
                p.setDescProd(rs.getString("descprod"));
                f= new Fornecedor();
                f.setCodForn(rs.getInt("codForn"));
                f.setNome(rs.getString("nome"));
                f.setCnpj(rs.getString("CNPJ"));
                e.setFornecedor(f);
                e.setProduto(p);
                s.setEntrada(e);*/
                //s.setEntrada(rpEnt.pesquisar(rs.getInt("codEnt")));
                s.getEntrada().setComportamentoToString(Entrada.TO_STRING_PROD_LOTE_SALDO);
                lista.add(s);
                
            }
            return lista;
        }
        catch(SQLException | RepositorioPesquisarException ex){
            throw new RepositorioListarException(ex, 
                    RepositorioSaida.class.getName()+".listar()");
        }
        finally{
            gerenciadorConexao.desconectar(c);
        }
    }
    /**
     * Pesquisa saída pelo número.
     * @param num
     * Número da saída.
     * @return
     * Retorna um objeto Saida.
     * @throws ConexaoException
     * Se houver algum problema com a conexão será lançada uma ConexaoException
     * @throws RepositorioPesquisarException 
     * Se houver algum erro na execução do SQL será lançada uma exceção.
     */
    @Override
    public Saida pesqNum(Integer num) 
            throws ConexaoException, RepositorioPesquisarException{
        Connection c= gerenciadorConexao.conectar();
        Saida s= null;
        Entrada e= null;
        Fornecedor f= null;
        Produto p= null;
        /*String sql= "select s.codSaida, s.codEnt, s.dtSaida, s.qtde, e.codProd,"
                + " e.codForn, e.qtde as qtdeEnt, e.lote, p.descProd, f.nome, f.CNPJ"
                + " from Saida as s inner join entrada as e"
                + " where s.codSaida = ? and e.codEnt = s.codEnt"
                + " inner join produto as p where p.codProd = e.codProd"
                + " inner join Fornecedor as f where f.codForn = e.codForn";*/
        String sql= "select codSaida, codEnt, dataSaida, qtde"
                + "from saida order by dataSaida desc, codSaida asc";
        try{
            PreparedStatement pstmt= c.prepareStatement(sql);
            pstmt.setInt(1, num);
            ResultSet rs= pstmt.executeQuery();
            IRepositorioEntrada rpEnt= new RepositorioEntrada();
            while (rs.next()){
                s= new Saida(rs.getInt("codSaida"), rs.getDouble("qtde"),
                        rs.getDate("dataSaida"),
                        rpEnt.pesquisar(rs.getInt("codEnt")));
                /*e= new Entrada();
                e.setCodEntrada(rs.getInt("codEnt"));
                e.setLote(rs.getString("lote"));
                e.setQtde(rs.getDouble("qtdeEnt"));
                p= new Produto();
                p.setCodProd(rs.getInt("codProd"));
                p.setDescProd(rs.getString("descprod"));
                f= new Fornecedor();
                f.setCodForn(rs.getInt("codForn"));
                f.setNome(rs.getString("nome"));
                f.setCnpj(rs.getString("CNPJ"));
                e.setFornecedor(f);
                e.setProduto(p);
                s.setEntrada(e);*/
                //s.setEntrada(rpEnt.pesquisar(rs.getInt("codEnt")));
            }
            return s;
        }
        catch(SQLException ex){
            throw new RepositorioPesquisarException(ex, 
                    RepositorioSaida.class.getName()+".pesNum()");
        }
        finally{
            gerenciadorConexao.desconectar(c);
        }
    }
    
    /**
     * Pesquisa entradas no período de datas informado.
     * 
     * @param saida
     * Objeto do tipo Saida que terá seus atributos utilizados para filtrar a 
     * pesquisa com exceção de data de entrada e quantidade.
     * Se o objeto não for nulo, os atributos abaixo serão válidos se:
     * Número da entrada for maior que zero;
     * Código do fornecedor for maior que zero;
     * Código do produto for maior que zero;
     * Lote se não for vazio. (pode conter coringa SQL como o caracter %)
     * 
     * @param dataInicial
     * Data inicial no formato texto (dd/MM/yyyy).
     * 
     * @param dataFinal
     * Data final no formato texto (dd/MM/yyyy).
     * 
     * @return
     * Retorna uma lista com as saídas.
     * 
     * @throws ConexaoException
     * Se houver algum problema com a conexão será lançada uma ConexaoException
     * 
     * @throws RepositorioPesquisarException 
     * Se houver algum erro na execução do SQL ou com as datas informadas será lançada uma exceção.
     */
    @Override
    public List<Saida> pesquisar(Saida saida, String dataInicial, String dataFinal)
            throws ConexaoException, RepositorioPesquisarException{
        Saida s= null;
        int nFields=0;
        int pesqCod= 0;
        int pesqForn= 0;
        int pesqProd= 0;
        int pesqLote= 0;
        List<Saida> lista = new ArrayList();
        java.sql.Date sqlDateInicial;
        java.sql.Date sqlDateFinal;
        try{
            sqlDateInicial= new java.sql.Date(new SimpleDateFormat("dd/MM/yyyy").parse(dataInicial).getTime());
            sqlDateFinal= new java.sql.Date(new SimpleDateFormat("dd/MM/yyyy").parse(dataFinal).getTime());
        }catch(java.text.ParseException pe){
            throw new RepositorioPesquisarException(pe,
                    RepositorioSaida.class.getName()+".pesquisar(saida, dataInicial, dataFinal)");
        }
        Entrada e=null;
        Connection c= gerenciadorConexao.conectar();
        String sql= "select * from Saida as s ";
        //boolean hasInnerJoin= false;
        String sqlInnerJoin="";
        if (saida != null){
            if ((saida.getEntrada().getFornecedor().getCodForn()!=null) 
                    && (saida.getEntrada().getFornecedor().getCodForn() > 0)){
                sql+= " inner join entrada as e on e.codEnt = s.codEnt and e.codForn=?";
                //hasInnerJoin= true;
                nFields++;
                pesqForn= nFields;
            }
            if ((saida.getEntrada().getProduto().getCodProd()!=null) 
                    && (saida.getEntrada().getProduto().getCodProd() > 0)){
                if (nFields>0){
                    sql+= " and e.codProd=?";
                }else{
                    sql+= " inner join entrada as e on e.codEnt = s.codEnt and e.codProd=?";
                }
                nFields++;
                pesqProd= nFields;
            }
            if ((saida.getEntrada().getLote()!= null) 
                    && (saida.getEntrada().getLote().length() > 0)){
                if (nFields>0){
                    sql+= " and e.lote like ?";
                }
                sql+= " inner join entrada as e on e.codEnt = s.codEnt and e.lote like ?";
                nFields++;
                pesqLote= nFields;
            }
            if ((saida.getCodSaida()!=null) && (saida.getCodSaida() > 0)){
                /*if (nFields>0){
                    sql+= " and s.codSaida=?";
                }else{
                    sql+= " inner join entrada as e on e.codEnt = s.codEnt and e.lote like ?";
                }*/
                nFields++;
                pesqCod= nFields+2;
            }
        }
        nFields+=2;
        sql+= " where (dataEnt >= ? and dataEnt <= ?)";
        if (pesqCod>0){
            sql+= " and s.codSaida=?";
        }
        sql+= " order by s.dataSaida desc, s.codSaida asc";
        try{
            PreparedStatement pstmt= c.prepareStatement(sql);
            if (pesqForn >0){
                pstmt.setInt(pesqForn, saida.getEntrada().getFornecedor().getCodForn());
            }
            if (pesqProd > 0){
                pstmt.setInt(pesqProd, saida.getEntrada().getProduto().getCodProd());
            }
            if (pesqLote > 0){
                pstmt.setString(pesqLote, saida.getEntrada().getLote());
            }
            if (nFields>0){
                pstmt.setDate(nFields-2, sqlDateInicial);
                pstmt.setDate(nFields-1, sqlDateFinal);
            }
            if (pesqCod>0){
                //pstmt.setDate(pesqCod-2, sqlDateInicial);
                //pstmt.setDate(pesqCod-1, sqlDateFinal);
                pstmt.setInt(pesqCod, saida.getCodSaida());
            }
            
            ///CONTINUAR CODIFICAÇÃO
            
            ResultSet rs= pstmt.executeQuery();
            IRepositorioEntrada rpEnt= new RepositorioEntrada();
            while (rs.next()){
                s= new Saida(rs.getInt("codSaida"), rs.getDouble("qtde"),
                        rs.getDate("dataSaida"),
                        rpEnt.pesquisar(rs.getInt("codEnt")));
            }
            return lista;
        }
        catch(SQLException ex){
            throw new RepositorioPesquisarException(ex, 
                    RepositorioSaida.class.getName()+".pesquisar(saida, dataInicial, dataFinal)");
        }
        finally{
            gerenciadorConexao.desconectar(c);
        }
    }
}
