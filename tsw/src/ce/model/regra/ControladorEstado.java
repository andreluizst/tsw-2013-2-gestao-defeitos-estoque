/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ce.model.regra;

import ce.erro.ConexaoException;
import ce.erro.ControladorException;
import ce.erro.RepositorioAlterarException;
import ce.erro.RepositorioException;
import ce.erro.RepositorioExcluirException;
import ce.erro.RepositorioForeignKeyException;
import ce.erro.RepositorioInserirException;
import ce.erro.RepositorioListarException;
import ce.erro.RepositorioPesquisarException;
import ce.model.basica.Estado;
import ce.model.basica.Usuario;
import ce.model.dao.IRepositorioEstado;
import ce.model.dao.RepositorioEstado;
import java.util.List;
import java.util.ResourceBundle;

/**
 *
 * @author andreluiz
 */
public class ControladorEstado {
    private Usuario user;
    private IRepositorioEstado rpEst= new RepositorioEstado();
    private ResourceBundle rb= ResourceBundle.getBundle("ce.erro.Erro");
    
    public ControladorEstado(){
        user= new Usuario();
    }
    
    public void validarDados(Estado est) throws ControladorException{
        if(est.getDescricao()==null){
            throw new ControladorException(getUser().getNome(), 
                    rb.getString("CtrlErroValInvalido"),
                    ControladorEstado.class.getName()+".validarDados()");
        }
        if(est.getDescricao().compareTo("") == 0){
            throw new ControladorException(getUser().getNome(),
                    rb.getString("CtrlErroValInvalido"),
                    ControladorEstado.class.getName()+".validarDados()");
        }
        
        if ((est.getUf() == null) || (est.getUf().compareTo("")==0)){
            throw new ControladorException(getUser().getNome(),
                    rb.getString("CtrlErroValInvalido"),
                    ControladorEstado.class.getName()+".validarDados()");
        }
    }
    
    public void verificarSePodeInserir(Estado e) throws ControladorException{
            List<Estado> lista;
        try {
            lista = rpEst.pesquisar(e.getDescricao());
            if(lista!=null){
                throw new ControladorException(getUser().getNome(),
                        rb.getString("CtrlEstExiste"),
                        ControladorEstado.class.getName()+".verificarSePodeInserir()");
            }
        } catch (ConexaoException ex) {
            throw new ControladorException(getUser().getNome(),
                    rb.getString("CtrlErroVerifIndisp") + " estado.",
                    ControladorEstado.class.getName()+".verificarSePodeInserir()");
        } catch (RepositorioException ex) {
            throw new ControladorException(getUser().getNome(),
                    rb.getString("CtrlErroVerificar") + " estado.",
                    ControladorEstado.class.getName()+".verificarSePodeInserir()");
        }
    }
    
    public void inserir(Estado e) throws ControladorException {
        try {
            rpEst.inserir(e);
        } catch (ConexaoException ex) {
            throw new ControladorException(getUser().getNome(),
                    rb.getString("CtrlErroInsIndisp") + " estado.",
                    ControladorEstado.class.getName()+".inserir()");
        } catch (RepositorioInserirException ex) {
            throw new ControladorException(getUser().getNome(),
                    rb.getString("CtrlErroInserir") + " estado.",
                    ControladorEstado.class.getName()+".inserir()");
        }
    }
    
    public void alterar(Estado e) throws ControladorException {
        try{
            rpEst.alterar(e);
        }
        catch(ConexaoException ce){
            throw new ControladorException(getUser().getNome(),
                    rb.getString("CtrlErroAltIndisp") + " estado.",
                    ControladorEstado.class.getName()+".alterar()");
        }
        catch(RepositorioAlterarException rae){
            throw new ControladorException(getUser().getNome(),
                    rb.getString("CtrlErroAlterar") + " estado.",
                    ControladorEstado.class.getName()+".alterar()");
        }
    }
    
    public void verificarSeExiste(String uf) throws ControladorException{
        Estado est=null;
        try{
            est= rpEst.pesqUf(uf);
            if (est == null){
                throw new ControladorException(getUser().getNome(),
                        rb.getString("CtrlEstNaoExiste"),
                        ControladorEstado.class.getName()+".verificarSeExiste()");
            }
        }
        catch(ConexaoException ce){
            throw new ControladorException(getUser().getNome(),
                    rb.getString("CtrlErroPesqIndisp") + " estado.",
                    ControladorEstado.class.getName()+".verificarSeExiste()");
        }
        catch(RepositorioPesquisarException re){
            throw new ControladorException(getUser().getNome(),
                    rb.getString("CtrlErroPesquisar") + " estado.",
                    ControladorEstado.class.getName()+".verificarSeExiste()");
        }
    }
    
    public void verificarSeExiste(Estado e) throws ControladorException{
        verificarSeExiste(e.getUf());
    }
    
    public void excluir(Estado e) throws ControladorException{
        try{
            rpEst.excluir(e.getUf());
        }
        catch(ConexaoException ce){
            throw new ControladorException(getUser().getNome(),
                    rb.getString("CtrlErroDelIndisp") + " estado.",
                    ControladorEstado.class.getName()+".excluir()");
        }
        catch(RepositorioForeignKeyException rfke){
            throw new ControladorException(getUser().getNome(),
                    rb.getString("CtrlNaoPodeExcluirEst"),
                    ControladorEstado.class.getName()+".excluir()");
        }
        catch(RepositorioExcluirException re){
            throw new ControladorException(getUser().getNome(),
                    rb.getString("CtrlErroExcluir") + " estado.",
                    ControladorEstado.class.getName()+".excluir()");
        }
    }
    
    public List<Estado> pesquisar(String descricao) throws ControladorException{
        try{
            return rpEst.pesquisar(descricao);
        }
        catch(ConexaoException ce){
            throw new ControladorException(getUser().getNome(),
                    rb.getString("CtrlErroPesqIndisp") + " estado.",
                    ControladorEstado.class.getName()+".pesquisar()");
        }
        catch(RepositorioPesquisarException re){
            throw new ControladorException(getUser().getNome(),
                    rb.getString("CtrlErroPesquisar") + " estado.",
                    ControladorEstado.class.getName()+".pesquisar()");
        }
    }
    
    public Estado trazer(String uf) throws ControladorException{
        try{
            return rpEst.pesqUf(uf);
        }
        catch(ConexaoException ce){
            throw new ControladorException(getUser().getNome(),
                    rb.getString("CtrlErroTrazerIndisp") + " estado.",
                    ControladorEstado.class.getName()+".trazer()");
        }
        catch(RepositorioPesquisarException re){
            throw new ControladorException(getUser().getNome(),
                    rb.getString("CtrlErroTrazer") + " estado.",
                    ControladorEstado.class.getName()+".trazer()");
        }
    }
    
    public List<Estado> listar() throws ControladorException{
        try{
            return rpEst.listar();
        }
        catch(ConexaoException ce){
            throw new ControladorException(getUser().getNome(),
                    rb.getString("CtrlErroListIndisp") + " estado.",
                    ControladorEstado.class.getName()+".listar()");
        }
        catch(RepositorioListarException re){
            throw new ControladorException(getUser().getNome(),
                    rb.getString("CtrlErroListar") + " estado.",
                    ControladorEstado.class.getName()+".listar()");
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
