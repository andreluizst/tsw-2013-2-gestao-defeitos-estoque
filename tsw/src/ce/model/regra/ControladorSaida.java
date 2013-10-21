/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ce.model.regra;

import ce.erro.ConexaoException;
import ce.erro.ControladorException;
import ce.erro.RepositorioAlterarException;
import ce.erro.RepositorioInserirException;
import ce.erro.RepositorioExcluirException;
import ce.erro.RepositorioPesquisarException;
import ce.erro.RepositorioListarException;
import ce.model.basica.Saida;
import ce.model.basica.Usuario;
import ce.model.dao.RepositorioSaida;
import ce.model.dao.IRepositorioSaida;
import ce.model.dao.RepositorioProduto;
import ce.model.dao.IRepositorioProduto;
import ce.util.ValidarStringData;
import java.util.List;
import java.util.ResourceBundle;
/**
 *
 * @author Andre
 */
public class ControladorSaida {
    private Usuario user;
    private IRepositorioSaida rpSaida= new RepositorioSaida();
    private IRepositorioProduto rpProd= new RepositorioProduto();
    private ResourceBundle rb= ResourceBundle.getBundle("ce.erro.Erro");
    
    public ControladorSaida(){
        user= new Usuario();
    }
    
    
    public ControladorSaida(Usuario user){
        this.user=user;
    }
    
    public void validarDados(Saida s) throws ControladorException{
        if (!ValidarStringData.execute(s.getDataSaida())){
            throw new ControladorException(user.getNome(),
                    rb.getString("CtrlErroValInvalido"),
                    ControladorSaida.class.getName()+".validarDados()");
        }
        if ((s.getQtde() == null) || (s.getQtde() <= 0)){
            throw new ControladorException(user.getNome(),
                    rb.getString("CtrlErroValInvalido"),
                    ControladorSaida.class.getName()+".validarDados()");
        }
        if ((s.getEntrada() == null) || (s.getEntrada().getNumero() == 0)){
            throw new ControladorException(user.getNome(),
                    rb.getString("CtrlErroValInvalido"),
                    ControladorSaida.class.getName()+".validarDados()");
        }
        if (s.getQtde() > s.getEntrada().getSaldo()){
            throw new ControladorException(user.getNome(),
                    "A quantidade da saída não pode ser maior que a quantade em estoque para este lote!",
                    ControladorSaida.class.getName()+".validarDados()");
        }
    }
    
    public void inserir(Saida s) throws ControladorException{
        try{
            rpSaida.inserir(s);
        }
        catch(ConexaoException ce){
            throw new ControladorException(user.getNome(),
                    rb.getString("CtrlErroInsIndisp") + " saída.",
                    ControladorSaida.class.getName()+".inserir()");
        }
        catch(RepositorioInserirException re){
            throw new ControladorException(user.getNome(),
                    rb.getString("CtrlErroInserir") + " saída.",
                    ControladorSaida.class.getName()+".inserir()");
        }
    }
    
    public void verificarSePodeAlterar(Saida s) throws ControladorException{
        if (s.getQtde() > s.getEntrada().getSaldo()){
            throw new ControladorException(user.getNome(),
                    "A quantidade da saída não pode ser maior que a quantade em estoque para este lote!",
                    ControladorSaida.class.getName()+".verificarSePodeAlterar()");
        }
    }
    
    public void alterar(Saida s) throws ControladorException{
        verificarSePodeAlterar(s);
        try{
            rpSaida.alterar(s);
        }
        catch(ConexaoException ce){
            throw new ControladorException(user.getNome(),
                    rb.getString("CtrlErroAltIndisp") + " saída.",
                    ControladorSaida.class.getName()+".alterar()");
        }
        catch(RepositorioAlterarException rae){
            String msg= rae.getMessage();
            if (msg == null || msg.contains(rb.getString("CtrlErroAtlzQtde"))){
                throw new ControladorException(user.getNome(), rae,
                        ControladorSaida.class.getName()+".alterar()");
            }
            throw new ControladorException(user.getNome(),
                    rb.getString("CtrlErroAlterar" + " saída."),
                    ControladorSaida.class.getName()+".alterar()");
        }
    }
    
    public void verificarSeExiste(Integer num) throws ControladorException {
        try{
            Saida saida= rpSaida.pesqNum(num);
            if (saida == null){
                throw new ControladorException(user.getNome(),
                        rb.getString("CtrlSaiNaoExiste"),
                        ControladorSaida.class.getName()+".verificarSeExiste()");
            }
        }
        catch(ConexaoException ce){
            throw new ControladorException(user.getNome(),
                    rb.getString("CtrlErroVerifIndisp") + " saída.",
                    ControladorSaida.class.getName()+".verificarSeExiste()");
        }
        catch(RepositorioPesquisarException pe){
            throw new ControladorException(user.getNome(),
                    rb.getString("CtrlErroVerificar") + " saída.",
                    ControladorSaida.class.getName()+".verificarSeExiste()");
        }
    }
    
    public void verificarSeExiste(Saida s) throws ControladorException {
        verificarSeExiste(s.getCodSaida());
    }
    
    public void excluir(Saida s) throws ControladorException{
        try{
            rpSaida.excluir(s);
        }
        catch(ConexaoException ce){
            throw new ControladorException(user.getNome(),
                    rb.getString("CtrlErroDelIndisp") + " saída.",
                    ControladorSaida.class.getName()+".excluir()");
        }
        /*catch(RepositorioForeignKeyException rfke){
            throw new ControladorException(
                    rb.getString("CtrlNaoPodeExcluirEnt"),
                    "ControladorSaida.excluir()");
        }*/
        catch(RepositorioExcluirException re){
            String msg= re.getMessage();
            if (msg == null || msg.contains(rb.getString("CtrlErroAtlzQtde"))){
                throw new ControladorException(user.getNome(), re,
                        ControladorSaida.class.getName()+".excluir()");
            }
            throw new ControladorException(user.getNome(),
                    rb.getString("CtrlErroExcluir") + " saída.",
                    ControladorSaida.class.getName()+".excluir()");
        }
    }
    
    public List<Saida> listar() throws ControladorException{
        try{
            return rpSaida.listar();
        }
        catch(ConexaoException e){
            throw new ControladorException(user.getNome(),
                    rb.getString("CtrlErroListIndisp") + " saída.",
                    ControladorSaida.class.getName()+".listar()");
        }
        catch(RepositorioListarException re){
            throw new ControladorException(user.getNome(),
                    rb.getString("CtrlErroListar") + " saída.",
                    ControladorSaida.class.getName()+".listar()");
        }
    }
    
    public Saida trazer(Integer num) throws ControladorException{
        try{
            Saida saida= rpSaida.pesqNum(num);
            return saida;
        }
        catch(ConexaoException ce){
            throw new ControladorException(user.getNome(),
                    rb.getString("CtrlErroTrazerIndisp") + " saída.",
                    ControladorSaida.class.getName()+".trazer()");
        }
        catch(RepositorioPesquisarException re){
            throw new ControladorException(user.getNome(),
                    rb.getString("CtrlErroTrazer") + " saída.",
                    ControladorSaida.class.getName()+".trazer()");
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
