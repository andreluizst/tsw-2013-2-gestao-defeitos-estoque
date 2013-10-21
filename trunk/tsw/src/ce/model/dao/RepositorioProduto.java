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
import ce.model.basica.Fornecedor;
import ce.model.basica.Produto;
import ce.util.GerenciadorConexao;
import ce.util.IGerenciadorConexao;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author Andre
 */
public class RepositorioProduto implements IRepositorioProduto{
    private IGerenciadorConexao gc;
    /**
     * Construtor padrão
     */
    public RepositorioProduto(){
        gc= GerenciadorConexao.getInstancia();
    }
    /**
     * Inclui um novo produto
     * @param p
     * Objeto da classe Produto que deseja incluir
     * @throws ConexaoException
     * Se houver algum problema com a conexão será lançada uma ConexaoException 
     * @throws RepositorioInserirException 
     * Se houver algum erro na execução do SQL será lançada uma exceção.
     */
    @Override
    public void inserir(Produto p) throws ConexaoException, RepositorioInserirException{
        Connection conexao = gc.conectar();
        String sql= "Insert into Produto(descProd, qtdeEstoq, qtdeMin,"
            + " qtdeIdeal, codCateg, statusProd, codUnid)"
            + " values(?, ?, ?, ?, ?, ?, ?)";
        int codForn=0;
        String sqlForns= "insert into FornXProd(codProd, codForn) values(?,?)";
        Produto novoProd;
        List<Produto> lstProd;
        try{
            PreparedStatement pstmt= conexao.prepareStatement(sql);
            pstmt.setString(1, p.getDescProd());
            pstmt.setDouble(2, p.getQtdeEstoq());
            pstmt.setDouble(3, p.getQtdeMin());
            if (p.getQtdeIdeal() != null){
                pstmt.setDouble(4, p.getQtdeIdeal());
            }else{
                pstmt.setDouble(4, 0.00);
            }
            pstmt.setInt(5, p.getCategoria().getCodCateg());
            pstmt.setInt(6, p.getStatus());
            pstmt.setInt(7, p.getUnidade().getCodUnid());
            pstmt.execute();
            lstProd= pesquisar(p.getDescProd());
            novoProd=  lstProd.get(0);
            if (p.getFornecedores().size() >= 1){
                PreparedStatement pstmtFornProd;//= conexao.prepareStatement(sqlForns);
                for(int i=0;i<p.getFornecedores().size();i++){
                    pstmtFornProd= conexao.prepareStatement(sqlForns);
                    pstmtFornProd.setInt(1, novoProd.getCodProd());//p.getCodProd());
                    pstmtFornProd.setInt(2, p.getFornecedores().get(i).getCodForn());
                    pstmtFornProd.executeUpdate();
                    pstmtFornProd.close();
                }
            }
            pstmt.close();
        }
        catch (SQLException | RepositorioPesquisarException e){
            throw new RepositorioInserirException(e, 
                    RepositorioProduto.class.getName()+".inserir()");
        }
        finally{
            gc.desconectar(conexao);
        }
    }
    /**
     * Altera um produto
     * @param p
     * Objeto da classe Produto com as alterações desejadas. O código constante
     * neste objeto deve ser o código do produto que sofrerá as alterações
     * e os demais atribudos devem conter os valores que foram modificados.
     * @throws ConexaoException
     * Se houver algum problema com a conexão será lançada uma ConexaoException 
     * @throws RepositorioAlterarException 
     * Se houver algum erro na execução do SQL será lançada uma exceção.
     */
    @Override
    public void alterar(Produto p) throws ConexaoException, RepositorioAlterarException{
        Connection conexao = gc.conectar();
        boolean atualizaFornXProd= false;
        List<Fornecedor> fornsDB= new ArrayList();
        Fornecedor f= null;
        String sql= "update Produto set descProd=?, qtdeEstoq=?, qtdeMin=?,"
                + " qtdeIdeal=?, codCateg=?, statusProd=?, codUnid=?"
                + " where codProd=?";
        String sqlFornsDB= "select distinct codProd, codForn from FornXProd"
                + " where codProd=?";
        try{
            PreparedStatement pstmt = conexao.prepareStatement(sql);
            pstmt.setString(1, p.getDescProd());
            pstmt.setDouble(2, p.getQtdeEstoq());
            pstmt.setDouble(3, p.getQtdeMin());
            if (p.getQtdeIdeal() != null){
                pstmt.setDouble(4, p.getQtdeIdeal());
            }else{
                pstmt.setDouble(4, 0.00);
            }
            pstmt.setDouble(5, p.getCategoria().getCodCateg());
            pstmt.setDouble(6, p.getStatus());
            pstmt.setInt(7, p.getUnidade().getCodUnid());
            pstmt.setInt(8, p.getCodProd());
            pstmt.execute();
            pstmt.close();
            PreparedStatement pstmtFornsDB= conexao.prepareStatement(sqlFornsDB);
            pstmtFornsDB.setInt(1, p.getCodProd());
            ResultSet rsFornsDB= pstmtFornsDB.executeQuery();
            IRepositorioFornecedor rpForn= new RepositorioFornecedor();
            while (rsFornsDB.next()){
                f= rpForn.pesqCodForn(rsFornsDB.getInt("codForn"), false);
                fornsDB.add(f);
            }
            if (fornsDB.size() != p.getFornecedores().size()){
                atualizaFornXProd=true;
            }else{
                if (p.getFornecedores().size() > 0){
                    for (int i=0;i<p.getFornecedores().size();i++){
                        if (fornsDB.get(i).getCodForn() != 
                                p.getFornecedores().get(i).getCodForn()){
                            atualizaFornXProd= true;
                            break;
                        }
                    }
                }
            }
            rsFornsDB.close();
            pstmtFornsDB.close();
            if (atualizaFornXProd){
                sqlFornsDB= "delete from FornXProd where codProd=?";
                pstmtFornsDB= conexao.prepareStatement(sqlFornsDB);
                pstmtFornsDB.setInt(1, p.getCodProd());
                pstmtFornsDB.execute();
                pstmtFornsDB.close();
                if (p.getFornecedores().size() > 0){
                    sqlFornsDB= "insert into FornXProd (codProd, codForn)"
                        + " values(?, ?)";
                    //pstmtFornsDB= conexao.prepareStatement(sqlFornsDB);
                    //pstmtFornsDB.setInt(1, p.getCodProd());
                    for (int i=0;i<p.getFornecedores().size();i++){
                        pstmtFornsDB= conexao.prepareStatement(sqlFornsDB);
                        pstmtFornsDB.setInt(1, p.getCodProd());
                        pstmtFornsDB.setInt(2, p.getFornecedores().get(i).getCodForn());
                        pstmtFornsDB.execute();
                        pstmtFornsDB.close();
                    }
                }
            }
            
        }
        catch(SQLException e){
            throw new RepositorioAlterarException(e, 
                    RepositorioProduto.class.getName()+".alterar()");
        }
         catch(RepositorioException ex){
            throw new RepositorioAlterarException(ex, 
                    RepositorioProduto.class.getName()+".alterar()."+ex.getPathClassCall());
        }
        finally{
            gc.desconectar(conexao);
        }
    }
    /**
     * Exclui um produto
     * @param codProd
     * Código do produto que deseja exclluir
     * @throws ConexaoException
     * Se houver algum problema com a conexão será lançada uma ConexaoException 
     * @throws RepositorioForeignKeyException
     * Se houver algum erro de chave estrangeira como por exemplo, ao tentar excluir
     * um produto que está sendo referenciado por uma outra tabela do banco 
     * como a tabela Entrada, será lançada uma exceção.
     * @throws RepositorioExcluirException 
     * Se houver algum erro na execução do SQL será lançada uma exceção.
     */
    @Override
    public void excluir(Integer codProd)throws ConexaoException, 
        RepositorioForeignKeyException, RepositorioExcluirException{
        Connection conexao = gc.conectar();
        String sql= "delete from Produto where codProd=?";
        String sqlFrons= "delete from FornXProd where codProd=?";
        try{
            PreparedStatement pstmt = conexao.prepareStatement(sql);
            pstmt.setInt(1, codProd);
            PreparedStatement pstmtFp= conexao.prepareStatement(sqlFrons);
            pstmtFp.setInt(1, codProd);
            pstmtFp.execute();
            pstmt.execute();
            pstmt.close();
            pstmtFp.close();
        }
        catch(SQLException e){
            String msg= e.getMessage().toLowerCase();
            if (msg!=null && msg.contains("foreign key constraint fails")){
                throw new RepositorioForeignKeyException(e,
                        RepositorioProduto.class.getName()+".excluir()");
            }
            throw new RepositorioExcluirException(e, 
                    RepositorioProduto.class.getName()+".excluir()");
        }
        finally{
            gc.desconectar(conexao);
        }
    }
    /**
     * Lista todos os produtos existentes.
     * @return
     * Retorna uma lista com os produtos.
     * @throws ConexaoException
     * Se houver algum problema com a conexão será lançada uma ConexaoException
     * @throws RepositorioListarException 
     * Se houver algum erro na execução do SQL será lançada uma exceção.
     */
    @Override
    public List<Produto> listar()throws ConexaoException, RepositorioListarException{
        List<Produto> lista = new ArrayList();
        Produto p= null;
        Fornecedor f= null;
        //Unidade u= null;
        Connection c = gc.conectar();
        String sql= "Select c.Descricao, p.codProd, p.descProd, p.qtdeEstoq, "
                + "p.qtdeMin, p.qtdeIdeal, p.statusProd, p.codCateg, "
                + "p.codUnid from Categoria as c "
                + "inner join Produto as p on p.codCateg = c.codCateg "
                + "order by c.Descricao, p.descProd";
        String sqlForns= "SELECT fp.codProd, fp.codForn from FornXProd as fp"
                + " inner join Fornecedor as f on fp.codProd=?"
                + " and f.codForn = fp.codForn"
                + " order by f.nome, f.Cnpj";
        try{
            Statement statement = c.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            
            IRepositorioCategoria rpCateg = new RepositorioCategoria();
            IRepositorioUnidade rpUnid= new RepositorioUnidade();
            IRepositorioFornecedor rpForn= new RepositorioFornecedor();
            while (rs.next()){
                p= new Produto(rs.getInt("codProd"), rs.getString("descProd"),
                        rs.getDouble("qtdeEstoq"), rs.getDouble("qtdeMin"),
                        rs.getDouble("qtdeIdeal"), rs.getInt("statusProd"),
                        rpCateg.pesqPorCod(rs.getInt("codCateg")),
                        rpUnid.pesqCod(rs.getInt("codUnid")));
                //PreparedStatement pstmtForns= c.prepareStatement(sqlForns);
                PreparedStatement pstmtForns= c.prepareStatement(sqlForns);
                pstmtForns.setInt(1, p.getCodProd());
                ResultSet rsForns= pstmtForns.executeQuery();
                List<Fornecedor> fornecedores= new ArrayList();
                while (rsForns.next()){
                    f= rpForn.pesqCodForn(rsForns.getInt("codForn"), false);
                    fornecedores.add(f);
                }
                rsForns.close();
                pstmtForns.close();
                p.setFornecedores(fornecedores);
                lista.add(p);
            }
            rs.close();
            statement.close();
            //pstmtForns.close();
            return lista;
        }
        catch(SQLException e){
            throw new RepositorioListarException(e, 
                    RepositorioProduto.class.getName()+".listar()");
        }
        catch(RepositorioException ex){
            throw new RepositorioListarException(ex,
                    RepositorioProduto.class.getName()+".listar()."+ex.getPathClassCall());
        }
        finally{
            gc.desconectar(c);
        }
    }
    
    /**
     * Pesquisa os produtos que não são fornecdidos por um determinado fornecedor.
     * @param codForn
     * Código do fornecedor cujos produtos fornecidos não constarão na lista. 
     * @return
     * Retorna uma lista com o(s) produto(s) encontrado(s).
     * @throws ConexaoException
     * Se houver algum problema com a conexão será lançada uma ConexaoException
     * @throws RepositorioPesquisarException 
     * Se houver algum erro na execução do SQL será lançada uma exceção.
     */
    @Override
    public List<Produto> pesquisarProdsQueNaoSaoDoForn(Integer codForn) 
            throws ConexaoException, RepositorioPesquisarException{
        List<Produto> lista = new ArrayList();
        Produto p= null;
        //Fornecedor f= null;
        Connection c = gc.conectar();
        String sql= "Select c.Descricao, p.codProd, p.descProd, p.qtdeEstoq,"
                + " p.qtdeMin, p.qtdeIdeal, p.statusProd, p.codCateg,"
                + " p.codUnid from Categoria as c "
                + " inner join Produto as p on p.codCateg = c.codCateg"
                + " and p.codProd not in (select distinct fp.codProd from FornXProd as fp"
                + " where fp.codForn = ?)"
                + " order by c.Descricao, p.descProd";
        /*String sqlForns= "SELECT fp.codProd, fp.codForn from FornXProd as fp"
                + " inner join Fornecedor as f on fp.codProd=?"
                + " and f.codForn = fp.codForn"
                + " order by f.nome, f.Cnpj";*/
        try{
            PreparedStatement pstmt = c.prepareStatement(sql);
            pstmt.setInt(1, codForn);
            ResultSet rs = pstmt.executeQuery();
            IRepositorioCategoria rpCateg = new RepositorioCategoria();
            IRepositorioUnidade rpUnid= new RepositorioUnidade();
            //IRepositorioFornecedor rpForn= new RepositorioFornecedor();
            while (rs.next()){
                p= new Produto(rs.getInt("codProd"), rs.getString("descProd"),
                        rs.getDouble("qtdeEstoq"), rs.getDouble("qtdeMin"),
                        rs.getDouble("qtdeIdeal"), rs.getInt("statusProd"),
                        rpCateg.pesqPorCod(rs.getInt("codCateg")),
                        rpUnid.pesqCod(rs.getInt("codUnid")));
                /*PreparedStatement pstmtForns= c.prepareStatement(sqlForns);
                pstmtForns.setInt(1, p.getCodProd());
                ResultSet rsForns= pstmtForns.executeQuery();
                List<Fornecedor> fornecedores= new ArrayList();
                while (rsForns.next()){
                    f= rpForn.pesqCodForn(rsForns.getInt("codForn"), false);
                    fornecedores.add(f);
                }
                rsForns.close();
                pstmtForns.close();
                p.setFornecedores(fornecedores);*/
                lista.add(p);
            }
            rs.close();
            pstmt.close();
            return lista;
        }
        catch(SQLException e){
            throw new RepositorioPesquisarException(e, 
                    RepositorioProduto.class.getName()+".pesquisarProdsQueNaoSaoDoForn()");
        }
        catch(RepositorioException ex){
            throw new RepositorioPesquisarException(ex,
                    RepositorioProduto.class.getName()+".pesquisarProdsQueNaoSaoDoForn()."+ex.getPathClassCall());
        }
        finally{
            gc.desconectar(c);
        }
    }
    /**
     * Pesquisa produto(s) pela descrição.
     * @param descProd
     * Descrição do produto desejado. É possível a utilização de caracteres coringa
     * no parâmetro nome dando maior flexibilidade a pesquisa. Ex: "%Samsung%".
     * @return
     * Retorna uma lista com o(s) produto(s) encontrado(s).
     * @throws ConexaoException
     * Se houver algum problema com a conexão será lançada uma ConexaoException
     * @throws RepositorioPesquisarException 
     * Se houver algum erro na execução do SQL será lançada uma exceção.
     */
    @Override
    public List<Produto> pesquisar(String descProd) throws ConexaoException, 
            RepositorioPesquisarException{
        List<Produto> lista = new ArrayList();
        Produto p = null;
        Fornecedor f= null;
        Connection c = gc.conectar();
        String sql= "Select * from Produto where descprod like ?";
        String sqlForns= "SELECT DISTINCT codProd, codForn"
                + " from FornXProd where codProd=?";
        try{
            PreparedStatement pstmt = c.prepareStatement(sql);
            PreparedStatement pstmtForns= c.prepareStatement(sqlForns);
            pstmt.setString(1, descProd);
            ResultSet rs = pstmt.executeQuery();
            
            IRepositorioCategoria rpCateg = new RepositorioCategoria();
            IRepositorioFornecedor rpForn= new RepositorioFornecedor();
            IRepositorioUnidade rpUnid= new RepositorioUnidade();
            while (rs.next()){
                p= new Produto(rs.getInt("codprod"), rs.getString("descProd"),
                        rs.getDouble("qtdeEstoq"), rs.getDouble("qtdeMin"),
                        rs.getDouble("qtdeIdeal"), rs.getInt("statusProd"),
                        rpCateg.pesqPorCod(rs.getInt("codCateg")),
                        rpUnid.pesqCod(rs.getInt("codUnid")));
                pstmtForns.setInt(1, p.getCodProd());
                ResultSet rsForns= pstmtForns.executeQuery();
                List<Fornecedor> fornecedores= new ArrayList();
                while (rsForns.next()){
                   f= rpForn.pesqCodForn(rsForns.getInt("codForn"), false);
                   fornecedores.add(f);
                }
                p.setFornecedores(fornecedores);
                lista.add(p);
            }
            pstmt.close();
            pstmtForns.close();
            return lista;
        }
        catch(SQLException e){
            throw new RepositorioPesquisarException(e, 
                    RepositorioProduto.class.getName()+".pesquisar()");
        }
        catch(RepositorioException ex){
            throw new RepositorioPesquisarException(ex, 
                    RepositorioProduto.class.getName()+".pesquisar()."+ex.getPathClassCall());
        }
        finally{
            gc.desconectar(c);
        }
    }
    /**
     * Pesquisa produto pelo código
     * @param codProd
     * Código do Produto desejado
     * @param comForns
     * Se false a lista de fornecedores do Produto não será preenchida. Este
     * parametro deve ser false se o método estiver sendo chamado de dentro
     * do repositório de fornecedores, pois gerará erro de conexão:
     *      com.mysql.jdbc.exceptions.jdbc4.MySQLNonTransientConnectionException: 
     *              Data source rejected establishment of connection,  message from server: "Too many connections"
     * @return
     * Retorna um Produto com todos os seus atributos preenchidos
     * @throws ConexaoException
     * Se houver algum problema com a conexão será lançada uma ConexaoException
     * @throws RepositorioPesquisarException 
     * Se houver algum erro na execução do SQL será lançada uma exceção.
     */
    @Override
    public Produto pesqCodProd(Integer codProd, boolean comForns) throws ConexaoException, 
            RepositorioPesquisarException{
        Produto p= null;
        Fornecedor f= null;
        Connection c = gc.conectar();
        String sql= "Select * from Produto where codProd=?";
        String sqlForns = "SELECT DISTINCT codProd, codForn"
                + " from fornxprod where codProd=?";
        try{
            PreparedStatement pstmt = c.prepareStatement(sql);
            pstmt.setInt(1, codProd);
            ResultSet rs = pstmt.executeQuery();
            IRepositorioCategoria rpCateg = new RepositorioCategoria();
            IRepositorioUnidade rpUnid= new RepositorioUnidade();
            IRepositorioFornecedor rpForn= new RepositorioFornecedor();
            if (rs.next()){
                p= new Produto(rs.getInt("codprod"), rs.getString("descProd"),
                        rs.getDouble("qtdeEstoq"), rs.getDouble("qtdeMin"),
                        rs.getDouble("qtdeIdeal"), rs.getInt("statusProd"),
                        rpCateg.pesqPorCod(rs.getInt("codCateg")),
                        rpUnid.pesqCod(rs.getInt("codUnid")));
                if (comForns){
                    PreparedStatement pstmtForns= c.prepareStatement(sqlForns);
                    pstmtForns.setInt(1, codProd);
                    ResultSet rsForns= pstmtForns.executeQuery();
                    List<Fornecedor> fornecedores= new ArrayList();
                    while (rsForns.next()){
                        f= rpForn.pesqCodForn(rsForns.getInt("codForn"), false);
                        fornecedores.add(f);
                    }
                    p.setFornecedores(fornecedores);
                    rsForns.close();
                    pstmtForns.close();
                }
            }
            rs.close();
            pstmt.close();
            /*if (f==null){
                throw new RepositorioPesquisarException("Produto."+codProd+" não encontrado!",
                        "RepositoroiFornecedor.pesqCodProd()");
            }*/
            return p;
        }
        catch(SQLException e){
            throw new RepositorioPesquisarException(e, 
                    RepositorioProduto.class.getName()+".pesqCodProd()");
        }
        catch(RepositorioException ex){
            throw new RepositorioPesquisarException(ex, 
                    RepositorioProduto.class.getName()+".pesqCodProd()."+ex.getPathClassCall());
        }
        finally{
            gc.desconectar(c);
        }
    }
    /**
     * Atualiza a quantidade do produto.
     * @param p
     * @throws ConexaoException
     * Se houver algum problema com a conexão será lançada uma ConexaoException
     * @throws RepositorioAlterarException 
     * Se houver algum erro na execução do SQL será lançada uma exceção.
     */
    @Override
    public void atualizarQtde(Produto p) throws ConexaoException, 
            RepositorioAlterarException{
        Double qtdeAtual= 0.00;
        Connection c = gc.conectar();
        String sql= "Update Produto set qtdeEstoq=? where codProd=?";
        String sqlEntrada= "SELECT SUM(qtde) as qtdeTotal FROM Entrada"
                + " WHERE codProd=?";
        String sqlSaida= "SELECT SUM(s.qtde) as saidaTotal FROM Saida as s "
                + "inner join entrada as e on e.codEnt = s.codEnt and e.codProd=? ";
        try{
            PreparedStatement pstmtEnt= c.prepareStatement(sqlEntrada);
            pstmtEnt.setInt(1, p.getCodProd());
            ResultSet rsEnt= pstmtEnt.executeQuery();
            while (rsEnt.next()){
                qtdeAtual= rsEnt.getDouble("qtdeTotal");
            }
            rsEnt.close();
            pstmtEnt.close();
            PreparedStatement pstmtSai= c.prepareStatement(sqlSaida);
            pstmtSai.setInt(1, p.getCodProd());
            ResultSet rsSai= pstmtSai.executeQuery();
            while (rsSai.next()){
                qtdeAtual-= rsSai.getDouble("saidaTotal");
            }
            rsSai.close();
            pstmtSai.close();
            PreparedStatement pstmt= c.prepareStatement(sql);
            pstmt.setDouble(1, qtdeAtual);
            pstmt.setInt(2, p.getCodProd());
            pstmt.executeUpdate();
            pstmt.close();
        }
        catch(SQLException e){
            throw new RepositorioAlterarException(e, 
                    RepositorioProduto.class.getName()+".atualizarQtde()");
        }
        finally{
            gc.desconectar(c);
        }
    }
}
