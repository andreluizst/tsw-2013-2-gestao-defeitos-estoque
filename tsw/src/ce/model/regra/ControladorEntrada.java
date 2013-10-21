/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ce.model.regra;

import ce.erro.ConexaoException;
import ce.erro.ControladorException;
import ce.erro.RepositorioException;
import ce.erro.RepositorioAlterarException;
import ce.erro.RepositorioInserirException;
import ce.erro.RepositorioExcluirException;
import ce.erro.RepositorioPesquisarException;
import ce.erro.RepositorioListarException;
import ce.erro.RepositorioForeignKeyException;
import ce.model.basica.Entrada;
import ce.model.basica.Usuario;
import ce.model.dao.RepositorioEntrada;
import ce.model.dao.IRepositorioEntrada;
import ce.model.dao.RepositorioProduto;
import ce.model.dao.IRepositorioProduto;
import ce.model.dao.RepositorioFornecedor;
import ce.model.dao.IRepositorioFornecedor;
import ce.util.ValidarStringData;
import java.util.List;
import java.util.ResourceBundle;
/**
 *
 * @author Andre
 */
public class ControladorEntrada {
    private Usuario user;
    private IRepositorioEntrada rpEnt= new RepositorioEntrada();
    private IRepositorioProduto rpProd= new RepositorioProduto();
    private IRepositorioFornecedor rpForn= new RepositorioFornecedor();
    private ResourceBundle rb= ResourceBundle.getBundle("ce.erro.Erro");
    
    public ControladorEntrada(){
        user= new Usuario();
    }
    
    public ControladorEntrada(Usuario u){
        user=u;
    }
    
    public void validarDados(Entrada e) throws ControladorException{
        /*O código/número da entrada é autoincrement
        if (e.getCodEntrada() == null || (e.getCodEntrada() == 0)){
            throw new ControladorException(rb.getString("CtrlErroValInvalido"));
        }*/
        if (!ValidarStringData.execute(e.getDataEntrada())){
            throw new ControladorException(user.getNome(),
                    rb.getString("CtrlErroValInvalido"),
                    ControladorEntrada.class.getName()+".validarDados");
        }
        if ((e.getQtde() == null) || (e.getQtde() <= 0)){
            throw new ControladorException(user.getNome(),
                    rb.getString("CtrlErroValInvalido"),
                    ControladorEntrada.class.getName()+".validarDados");
        }
        if ((e.getFornecedor() == null) || (e.getFornecedor().getCodForn()==0)){
            throw new ControladorException(user.getNome(),
                    rb.getString("CtrlErroValInvalido"),
                    ControladorEntrada.class.getName()+".validarDados");
        }
        if ((e.getProduto()==null) || (e.getProduto().getCodProd()==0)){
            throw new ControladorException(user.getNome(),
                    rb.getString("CtrlErroValInvalido"),
                    ControladorEntrada.class.getName()+".validarDados");
        }
        if ((e.getLote() == null) || (e.getLote().compareTo("")==0)){
            throw new ControladorException(user.getNome(),
                    rb.getString("CtrlErroValInvalido"),
                    ControladorEntrada.class.getName()+".validarDados");
        }
    }
    
    public void inserir(Entrada e) throws ControladorException{
        try{
            rpEnt.inserir(e);
        }
        catch(ConexaoException ce){
            throw new ControladorException(user.getNome(),
                    rb.getString("CtrlErroInsIndisp") + " entrada.",
                    ControladorEntrada.class.getName()+".inserir()");
        }
        catch(RepositorioInserirException re){
            throw new ControladorException(user.getNome(),
                    rb.getString("CtrlErroInserir") + " entrada.",
                    ControladorEntrada.class.getName()+".inserir()");
        }
    }
    
    public void alterar(Entrada e) throws ControladorException{
        try{
            rpEnt.alterar(e);
        }
        catch(ConexaoException ce){
            throw new ControladorException(
                    rb.getString("CtrlErroAltIndisp") + " entrada.",
                    "ControladorEntrada.alterar()");
        }
        catch(RepositorioAlterarException rae){
            String msg= rae.getMessage();
            if (msg == null || msg.contains(rb.getString("CtrlErroAtlzQtde"))){
                throw new ControladorException(user.getNome(), rae, 
                        ControladorEntrada.class.getName()+".alterar()");
            }
            throw new ControladorException(user.getNome(),
                    rb.getString("CtrlErroAlterar" + " entrada."),
                    ControladorEntrada.class.getName()+".alterar()");
        }
    }
    
    public void verificarSeExiste(Integer num) throws ControladorException {
        try{
            Entrada ent= rpEnt.pesquisar(num);
            if (ent == null){
                throw new ControladorException(user.getNome(),
                        rb.getString("CtrlEntNaoExiste"),
                        ControladorEntrada.class.getName()+".verificarSeExiste()");
            }
        }
        catch(ConexaoException ce){
            throw new ControladorException(user.getNome(),
                    rb.getString("CtrlErroVerifIndisp") + " entrada.",
                    ControladorEntrada.class.getName()+".verificarSeExiste()");
        }
        catch(RepositorioPesquisarException pe){
            throw new ControladorException(user.getNome(),
                    rb.getString("CtrlErroVerificar") + " entrada.",
                    ControladorEntrada.class.getName()+".verificarSeExiste()");
        }
    }
    
    public void verificarSeExiste(Entrada e) throws ControladorException {
        verificarSeExiste(e.getNumero());
    }
    
    public void excluir(Entrada e) throws ControladorException{
        try{
            rpEnt.excluir(e);
        }
        catch(ConexaoException ce){
            throw new ControladorException(user.getNome(),
                    rb.getString("CtrlErroDelIndisp") + " entrada.",
                    ControladorEntrada.class.getName()+".excluir()");
        }
        catch(RepositorioForeignKeyException rfke){
            throw new ControladorException(user.getNome(),
                    rb.getString("CtrlNaoPodeExcluirEnt"),
                    ControladorEntrada.class.getName()+".excluir()");
        }
        catch(RepositorioExcluirException re){
            String msg= re.getMessage();
            if (msg == null || msg.contains(rb.getString("CtrlErroAtlzQtde"))){
                throw new ControladorException(user.getNome(), re,
                        ControladorEntrada.class.getName()+".excluir()");
            }
            throw new ControladorException(user.getNome(),
                    rb.getString("CtrlErroExcluir") + " entrada.",
                    ControladorEntrada.class.getName()+".excluir()");
        }
    }
    
    public List<Entrada> listar() throws ControladorException{
        try{
            return rpEnt.listar();
        }
        catch(ConexaoException e){
            throw new ControladorException(user.getNome(),
                    rb.getString("CtrlErroListIndisp") + " entrada.",
                    ControladorEntrada.class.getName()+".listar()");
        }
        catch(RepositorioListarException re){
            throw new ControladorException(user.getNome(),
                    rb.getString("CtrlErroListar") + " entrada.",
                    ControladorEntrada.class.getName()+".listar()");
        }
    }
    
    public List<Entrada> listarDisponiveis() throws ControladorException{
        try{
            return rpEnt.listarComSaldo();
        }
        catch(ConexaoException e){
            throw new ControladorException(user.getNome(),
                    rb.getString("CtrlErroListIndisp") + " entrada.",
                    ControladorEntrada.class.getName()+".listarDisponiveis()");
        }
        catch(RepositorioListarException re){
            throw new ControladorException(user.getNome(),
                    rb.getString("CtrlErroListar") + " entrada.",
                    ControladorEntrada.class.getName()+".listarDisponiveis()");
        }
    }
    
    public List<Entrada> pesquisar(Entrada entrada, String dataInicial, 
            String dataFinal) throws ControladorException{
        try{
            return rpEnt.pesquisar(entrada, dataInicial, dataFinal);
        }
        catch(ConexaoException e){
            throw new ControladorException(user.getNome(),
                    rb.getString("CtrlErroPesqIndisp") + " entrada.",
                    ControladorEntrada.class.getName()+".pesquisar()");
        }
        catch(RepositorioPesquisarException re){
            throw new ControladorException(user.getNome(),
                    rb.getString("CtrlErroPesquisar") + " entrada.",
                    ControladorEntrada.class.getName()+".pesquisar()");
        }
    }
    
    public List<Entrada> pesquisar(String dataInicial, String dataFinal)
            throws ControladorException{
        try{
            return rpEnt.pesquisar(dataInicial, dataFinal);
        }
        catch(ConexaoException e){
            throw new ControladorException(user.getNome(),
                    rb.getString("CtrlErroPesqIndisp") + " entrada.",
                    ControladorEntrada.class.getName()+".pesquisar()");
        }
        catch(RepositorioPesquisarException re){
            throw new ControladorException(user.getNome(),
                    rb.getString("CtrlErroPesquisar") + " entrada.",
                    ControladorEntrada.class.getName()+".pesquisar()");
        }
    }
    
    public Entrada trazer(Integer num) throws ControladorException{
        try{
            Entrada ent= rpEnt.pesquisar(num);
            /*Para deixar o método atômico o mesmo não deve executar verificações
             * Usar o metódo verificarSeExiste(Entrada e) antes.
             * if (ent == null){
                throw new ControladorException(rb.getString("CtrlEntNaoExiste"),
                        "ControladorEntrada.trazer()");
            }*/
            return ent;
        }
        catch(ConexaoException ce){
            throw new ControladorException(user.getNome(),
                    rb.getString("CtrlErroTrazerIndisp") + " entrada.",
                    ControladorEntrada.class.getName()+".trazer()");
        }
        catch(RepositorioPesquisarException re){
            throw new ControladorException(user.getNome(),
                    rb.getString("CtrlErroTrazer") + " entrada.",
                    ControladorEntrada.class.getName()+".trazer()");
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
