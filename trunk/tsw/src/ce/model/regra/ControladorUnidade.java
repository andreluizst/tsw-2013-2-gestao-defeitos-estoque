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
import ce.erro.RepositorioForeignKeyException;
import ce.erro.RepositorioPesquisarException;
import ce.erro.RepositorioListarException;
import ce.model.basica.Unidade;
import ce.model.basica.Usuario;
import ce.model.dao.IRepositorioUnidade;
import ce.model.dao.RepositorioUnidade;
import java.util.List;
import java.util.ResourceBundle;

/**
 *
 * @author Andre
 */
public class ControladorUnidade {
    private Usuario user;
    private IRepositorioUnidade rpUnid= new RepositorioUnidade();
    private ResourceBundle rb= ResourceBundle.getBundle("ce.erro.Erro");
    
    public ControladorUnidade(){
        user= new Usuario();
    }
    
    public ControladorUnidade(Usuario user){
        this.user=user;
    }
    
    public void validarDados(Unidade u) throws ControladorException{
        if (u.getDescricao() == null){
            throw new ControladorException(user.getNome(),
                    rb.getString("CtrlErroValInvalido"),
                    ControladorUnidade.class.getName()+".validarDados()");
        }
    }
    
    public void verificarSePodeInserir(Unidade u) throws ControladorException{
        List<Unidade> lista=null;
        try {
            lista = rpUnid.pesquisar(u.getDescricao());
            if(lista.size() > 0){
                throw new ControladorException(user.getNome(),
                        rb.getString("CtrlUnidExiste"),
                        ControladorUnidade.class.getName()+".verificarSePodeInserir()");
            }
        } catch (ConexaoException ex) {
            throw new ControladorException(user.getNome(),
                    rb.getString("CtrlErroVerifIndisp") + " unidade.",
                    ControladorUnidade.class.getName()+".verificarSePodeInserir()");
        } catch (RepositorioException ex) {
            throw new ControladorException(user.getNome(),
                    rb.getString("CtrlErroVerificar") + " unidade.",
                    ControladorUnidade.class.getName()+".verificarSePodeInserir()");
        }
    }
    
    public void inserir(Unidade u) throws ControladorException {
        try {
            rpUnid.inserir(u);
        } catch (ConexaoException ce) {
            throw new ControladorException(user.getNome(),
                    rb.getString("CtrlErroInsIndisp") + " unidade.",
                    ControladorUnidade.class.getName()+".inserir()");
        } catch (RepositorioInserirException re) {
            throw new ControladorException(user.getNome(),
                    rb.getString("CtrlErroInserir") + " unidade.",
                    ControladorUnidade.class.getName()+".inserir()");
        }
    }
    
    public void alterar(Unidade u) throws ControladorException {
        try{
            rpUnid.alterar(u);
        }
        catch(ConexaoException ce){
            throw new ControladorException(user.getNome(),
                    rb.getString("CtrlErroAltIndisp") + " unidade.",
                    ControladorUnidade.class.getName()+".alterar()");
        }
        catch(RepositorioAlterarException rae){
            throw new ControladorException(user.getNome(),
                    rb.getString("CtrlErroAlterar") + " unidade.",
                    ControladorUnidade.class.getName()+".alterar()");
        }
    }
    
    public void verificarSeExiste(Integer cod) throws ControladorException{
        Unidade unid=null;
        try{
            unid= rpUnid.pesqCod(cod);
            if (unid == null){
                throw new ControladorException(user.getNome(),
                        rb.getString("CtrlUnidNaoExiste"),
                        ControladorUnidade.class.getName()+".verificarSeExiste()");
            }
        }
        catch(ConexaoException e){
            throw new ControladorException(user.getNome(),
                    rb.getString("CtrlErroVerifIndisp") + " unidade.",
                    ControladorUnidade.class.getName()+".verificarSeExiste()");
        }
        catch(RepositorioPesquisarException ie){
            throw new ControladorException(user.getNome(),
                    rb.getString("CtrlErroVerificar") + " unidade.",
                    ControladorUnidade.class.getName()+".verificarSeExiste()");
        }
    }
    
    public void verificarSeExiste(Unidade u) throws ControladorException{
        verificarSeExiste(u.getCodUnid());
    }
    
    public void excluir(Unidade u) throws ControladorException{
        try{
            rpUnid.excluir(u.getCodUnid());
        }
        catch(ConexaoException ce){
            throw new ControladorException(user.getNome(),
                    rb.getString("CtrlErroDelIndisp") + " unidade.",
                    ControladorUnidade.class.getName()+".excluir()");
        }
        catch(RepositorioForeignKeyException rfke){
            throw new ControladorException(user.getNome(),
                    rb.getString("CtrlNaoPodeExcluirUnid"),
                    ControladorUnidade.class.getName()+".excluir()");
        }
        catch(RepositorioExcluirException re){
            throw new ControladorException(user.getNome(),
                    rb.getString("CtrlErroExcluir") + " unidade.",
                    ControladorUnidade.class.getName()+".excluir()");
        }
    }
    
    public List<Unidade> listar() throws ControladorException{
        try{
            return rpUnid.listar();
        }
        catch(ConexaoException ce){
            throw new ControladorException(user.getNome(),
                    rb.getString("CtrlErroListIndisp") + " unidade.",
                    ControladorUnidade.class.getName()+".listar()");
        }
        catch(RepositorioListarException re){
            throw new ControladorException(user.getNome(),
                    rb.getString("CtrlErroListar") + " unidade.",
                    ControladorUnidade.class.getName()+".listar()");
        }
    }
    
    public List<Unidade> pesquisar(String descricao) throws ControladorException{
        try{
            return rpUnid.pesquisar(descricao);
        }
        catch(ConexaoException ce){
            throw new ControladorException(user.getNome(),
                    rb.getString("CtrlErroPesqIndisp") + " unidade.",
                    ControladorUnidade.class.getName()+".pesquisar()");
        }
        catch(RepositorioPesquisarException re){
            throw new ControladorException(user.getNome(),
                    rb.getString("CtrlErroPesquisar") + " unidade.",
                    ControladorUnidade.class.getName()+".pesquisar()");
        }
    }
    
    public Unidade trazer(Integer cod) throws ControladorException{
        try{
            return rpUnid.pesqCod(cod);
        }
        catch(ConexaoException ce){
            throw new ControladorException(user.getNome(),
                    rb.getString("CtrlErroTrazerIndisp") + " unidade.",
                    ControladorUnidade.class.getName()+".trazer()");
        }
        catch(RepositorioPesquisarException re){
            throw new ControladorException(user.getNome(),
                    rb.getString("CtrlErroTrazer") + " unidade.",
                    ControladorUnidade.class.getName()+".trazer()");
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
