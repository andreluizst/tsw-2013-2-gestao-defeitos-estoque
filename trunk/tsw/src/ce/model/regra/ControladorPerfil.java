/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ce.model.regra;

import ce.erro.ConexaoException;
import ce.erro.ControladorException;
import ce.erro.RepositorioException;
import ce.erro.RepositorioInserirException;
import ce.erro.RepositorioAlterarException;
import ce.erro.RepositorioExcluirException;
import ce.erro.RepositorioForeignKeyException;
import ce.erro.RepositorioListarException;
import ce.erro.RepositorioPesquisarException;
import ce.model.basica.Perfil;
import ce.model.basica.Usuario;
import ce.model.dao.IRepositorioPerfil;
import ce.model.dao.RepositorioPerfil;
import java.util.ResourceBundle;
import java.util.List;
/**
 *
 * @author Andre
 */
public class ControladorPerfil {
    private Usuario user;
    private IRepositorioPerfil rpPer= new RepositorioPerfil();
    private ResourceBundle rb= ResourceBundle.getBundle("ce.erro.Erro");
    
    
    public ControladorPerfil(){
        user= new Usuario();
    }
    
    public ControladorPerfil(Usuario user){
        this.user=user;
    }
    
    public void validarDados(Perfil p) throws ControladorException{
        if (p.getNome() == null){
            throw new ControladorException(user.getNome(),
                    rb.getString("CtrlErroValInvalido"),
                    ControladorPerfil.class.getName()+".validarDados()");
        }
    }
    
    public void verificarSePodeInserir(Perfil p) throws ControladorException{
        try {
            Perfil per = rpPer.pesquisar(p.getNome());
            if(per != null){
                throw new ControladorException(user.getNome(),
                        rb.getString("CtrlErroPerExiste"),
                        ControladorPerfil.class.getName()+".verificarSePodeInserir()");
            }
        } catch (ConexaoException ex) {
            throw new ControladorException(user.getNome(),
                    rb.getString("CtrlErroVerifIndisp") + " perfil.",
                    ControladorPerfil.class.getName()+".verificarSePodeInserir()");
        } catch (RepositorioException ex) {
            throw new ControladorException(user.getNome(),
                    rb.getString("CtrlErroVerificar") + " perfil.",
                    ControladorPerfil.class.getName()+".verificarSePodeInserir()");
        }
    }
    
    public void inserir(Perfil p) throws ControladorException {
        try {
            rpPer.inserir(p);
        } catch (ConexaoException ce) {
            throw new ControladorException(user.getNome(),
                    rb.getString("CtrlErroInsIndisp") + " perfil.",
                    ControladorPerfil.class.getName()+".inserir()");
        } catch (RepositorioInserirException re) {
            throw new ControladorException(user.getNome(),
                    rb.getString("CtrlErroInserir") + " perfil.",
                    ControladorPerfil.class.getName()+".inserir()");
        }
    }
    
    public void alterar(Perfil p) throws ControladorException {
        try{
            rpPer.alterar(p);
        }
        catch(ConexaoException ce){
            throw new ControladorException(user.getNome(),
                    rb.getString("CtrlErroAltIndisp") + " perfil.",
                    ControladorPerfil.class.getName()+".alterar()");
        }
        catch(RepositorioAlterarException rae){
            throw new ControladorException(user.getNome(),
                    rb.getString("CtrlErroAlterar") + " perfil.",
                    ControladorPerfil.class.getName()+".alterar()");
        }
    }
    
    public void verificarSeExiste(Integer cod) throws ControladorException{
        Perfil per=null;
        try{
            per= rpPer.pesqCod(cod);
            if (per == null){
                throw new ControladorException(user.getNome(),
                        rb.getString("CtrlPerNaoExiste"),
                        ControladorPerfil.class.getName()+".verificarSeExiste()");
            }
        }
        catch(ConexaoException e){
            throw new ControladorException(user.getNome(),
                    rb.getString("CtrlErroVerifIndisp") + " perfil.",
                    ControladorPerfil.class.getName()+".verificarSeExiste()");
        }
        catch(RepositorioPesquisarException ie){
            throw new ControladorException(user.getNome(),
                    rb.getString("CtrlErroVerificar") + " perfil.",
                    ControladorPerfil.class.getName()+".verificarSeExiste()");
        }
    }
    
    public void verificarSeExiste(String nome) throws ControladorException{
        Perfil per=null;
        try{
            per= rpPer.pesquisar(nome);
            if (per == null){
                throw new ControladorException(user.getNome(),
                        rb.getString("CtrlPerNaoExiste"),
                        ControladorPerfil.class.getName()+".verificarSeExiste()");
            }
        }
        catch(ConexaoException e){
            throw new ControladorException(user.getNome(),
                    rb.getString("CtrlErroVerifIndisp") + " perfil.",
                    ControladorPerfil.class.getName()+".verificarSeExiste()");
        }
        catch(RepositorioPesquisarException ie){
            throw new ControladorException(user.getNome(),
                    rb.getString("CtrlErroVerificar") + " perfil.",
                    ControladorPerfil.class.getName()+".verificarSeExiste()");
        }
    }
    
    public void verificarSeExiste(Perfil p) throws ControladorException{
        verificarSeExiste(p.getCodPerfil());
    }
    
    public void excluir(Perfil p) throws ControladorException{
        try{
            rpPer.excluir(p);
        }
        catch(ConexaoException ce){
            throw new ControladorException(user.getNome(),
                    rb.getString("CtrlErroDelIndisp") + " perfil.",
                    ControladorPerfil.class.getName()+".excluir()");
        }
        catch(RepositorioForeignKeyException rfke){
            throw new ControladorException(user.getNome(),
                    rb.getString("CtrlNaoPodeExcluirPer"),
                    ControladorPerfil.class.getName()+".excluir()");
        }
        catch(RepositorioExcluirException re){
            throw new ControladorException(user.getNome(),
                    rb.getString("CtrlErroExcluir") + " perfil.",
                    ControladorPerfil.class.getName()+".excluir()");
        }
    }
    
    public List<Perfil> listar() throws ControladorException{
        try{
            return rpPer.listar();
        }
        catch(ConexaoException ce){
            throw new ControladorException(user.getNome(),
                    rb.getString("CtrlErroListIndisp") + " perfil.",
                    ControladorPerfil.class.getName()+".listar()");
        }
        catch(RepositorioListarException re){
            throw new ControladorException(user.getNome(),
                    rb.getString("CtrlErroListar") + " perfil.",
                    ControladorPerfil.class.getName()+".listar()");
        }
    }
    
    public Perfil pesquisar(String nome) throws ControladorException{
        try{
            return rpPer.pesquisar(nome);
        }
        catch(ConexaoException ce){
            throw new ControladorException(user.getNome(),
                    rb.getString("CtrlErroPesqIndisp") + " perfil.",
                    ControladorPerfil.class.getName()+".pesquisar()");
        }
        catch(RepositorioPesquisarException re){
            throw new ControladorException(user.getNome(),
                    rb.getString("CtrlErroPesquisar") + " perfil.",
                    ControladorPerfil.class.getName()+".pesquisar()");
        }
    }
    
    public Perfil trazer(Integer cod) throws ControladorException{
        try{
            return rpPer.pesqCod(cod);
        }
        catch(ConexaoException ce){
            throw new ControladorException(user.getNome(),
                    rb.getString("CtrlErroTrazerIndisp") + " perfil.",
                    ControladorPerfil.class.getName()+".trazer()");
        }
        catch(RepositorioPesquisarException re){
            throw new ControladorException(user.getNome(),
                    rb.getString("CtrlErroTrazer") + " perfil.",
                    ControladorPerfil.class.getName()+".trazer()");
        }
    }
    
    public Perfil trazer(String descricao) throws ControladorException{
        try{
            return rpPer.pesquisar(descricao);
        }
        catch(ConexaoException ce){
            throw new ControladorException(user.getNome(),
                    rb.getString("CtrlErroTrazerIndisp") + " perfil.",
                    ControladorPerfil.class.getName()+".trazer()");
        }
        catch(RepositorioPesquisarException re){
            throw new ControladorException(user.getNome(),
                    rb.getString("CtrlErroTrazer") + " perfil.",
                    ControladorPerfil.class.getName()+".trazer()");
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
