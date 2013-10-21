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
import ce.model.basica.Usuario;
import ce.model.dao.IRepositorioPerfil;
import ce.model.dao.RepositorioPerfil;
import ce.model.dao.IRepositorioUsuario;
import ce.model.dao.RepositorioUsuario;
import java.util.ResourceBundle;
import java.util.List;
/**
 *
 * @author Andre
 */
public class ControladorUsuario {
    private Usuario user;
    private IRepositorioUsuario rpUsr= new RepositorioUsuario();
    private IRepositorioPerfil rpPer= new RepositorioPerfil();
    private ResourceBundle rb= ResourceBundle.getBundle("ce.erro.Erro");
    private ControladorPerfil ctrlPer= new ControladorPerfil();
    
    public ControladorUsuario(){
        user= new Usuario();
    }
    
    public ControladorUsuario(Usuario user){
        this.user=user;
    }
    
    public void validarDados(Usuario u) throws ControladorException{
        if (u.getNome() == null){
            throw new ControladorException(user.getNome(),
                    rb.getString("CtrlErroValInvalido") + " [nome]",
                    ControladorUsuario.class.getName()+".validarDados()");
        }
        if (u.getSenha().length() < 6){
            throw new ControladorException(user.getNome(),
                    rb.getString("CtrlErroValInvalido")
                    +"\nA senha deve ter seis ou mais caracteres.",
                    ControladorUsuario.class.getName()+".validarDados()");
        }
        if ((u.getFuncionario() == null) || 
                (u.getFuncionario().getCpf().compareTo("")==0)){
            throw new ControladorException(user.getNome(),
                    rb.getString("CtrlErroValInvalido") + " [funcionário]",
                    ControladorUsuario.class.getName()+".validarDados()");
        }
        if ((u.getPerfil() == null) || (u.getPerfil().getCodPerfil() <= 0)){
            throw new ControladorException(user.getNome(),
                    rb.getString("CtrlErroValInvalido") + " [perfil]",
                    ControladorUsuario.class.getName()+".validarDados()");
        }
    }
    
    public void verificarSePodeInserir(Usuario u) throws ControladorException{
        List<Usuario> lista;
        String msgComp=null;
        try {
            Usuario usu = rpUsr.pesqCpf(u.getFuncionario().getCpf());
            if (usu != null){
                msgComp= u.getFuncionario().getCpf();
            }
            lista= rpUsr.pesquisar(u.getNome());
            if (lista.size() > 0){
                msgComp= lista.get(0).getNome();
            }
            //if((usu != null) || (lista.size() > 0)){
            if (msgComp != null){
                throw new ControladorException(user.getNome(),
                        rb.getString("CtrlErroUsuExiste") + "= "+msgComp,
                        ControladorUsuario.class.getName()+".verificarSePodeInserir()");
            }
        } catch (ConexaoException ex) {
            throw new ControladorException(user.getNome(),
                    rb.getString("CtrlErroVerifIndisp") + " usuário.",
                    ControladorUsuario.class.getName()+".verificarSePodeInserir()");
        } catch (RepositorioException ex) {
            throw new ControladorException(user.getNome(),
                    rb.getString("CtrlErroVerificar") + " usuário.",
                    ControladorUsuario.class.getName()+".verificarSePodeInserir()");
        }
    }
    
    public void inserir(Usuario u) throws ControladorException {
        try {
            rpUsr.inserir(u);
        } catch (ConexaoException ce) {
            throw new ControladorException(user.getNome(),
                    rb.getString("CtrlErroInsIndisp") + " usuário.",
                    ControladorUsuario.class.getName()+".inserir()");
        } catch (RepositorioInserirException re) {
            throw new ControladorException(user.getNome(),
                    rb.getString("CtrlErroInserir") + " usuário.",
                    ControladorUsuario.class.getName()+".inserir()");
        }
    }
    
    public void alterar(Usuario u) throws ControladorException {
        try{
            rpUsr.alterar(u);
        }
        catch(ConexaoException ce){
            throw new ControladorException(user.getNome(),
                    rb.getString("CtrlErroAltIndisp") + " usuário.",
                    ControladorUsuario.class.getName()+".alterar()");
        }
        catch(RepositorioAlterarException rae){
            throw new ControladorException(user.getNome(),
                    rb.getString("CtrlErroAlterar") + " usuário.",
                    ControladorUsuario.class.getName()+".alterar()");
        }
    }
    
    public void verificarSeExiste(String cpf) throws ControladorException{
        Usuario usu=null;
        try{
            usu= rpUsr.pesqCpf(cpf);
            if (usu == null){
                throw new ControladorException(user.getNome(),
                        rb.getString("CtrlUsuNaoExiste"),
                        ControladorUsuario.class.getName()+".verificarSeExiste()");
            }
        }
        catch(ConexaoException e){
            throw new ControladorException(user.getNome(),
                    rb.getString("CtrlErroVerifIndisp") + " usuário.",
                    ControladorUsuario.class.getName()+".verificarSeExiste()");
        }
        catch(RepositorioPesquisarException ie){
            throw new ControladorException(user.getNome(),
                    rb.getString("CtrlErroVerificar") + " usuário.",
                    ControladorUsuario.class.getName()+".verificarSeExiste()");
        }
    }
    
    public void verificarSeExiste(Integer cod) throws ControladorException{
        Usuario usu=null;
        try{
            usu= rpUsr.pesqCod(cod);
            if (usu == null){
                throw new ControladorException(user.getNome(),
                        rb.getString("CtrlUsuNaoExiste"),
                        ControladorUsuario.class.getName()+".verificarSeExiste()");
            }
        }
        catch(ConexaoException e){
            throw new ControladorException(user.getNome(),
                    rb.getString("CtrlErroVerifIndisp") + " usuário.",
                    ControladorUsuario.class.getName()+".verificarSeExiste()");
        }
        catch(RepositorioPesquisarException ie){
            throw new ControladorException(user.getNome(),
                    rb.getString("CtrlErroVerificar") + " usuário.",
                    ControladorUsuario.class.getName()+".verificarSeExiste()");
        }
    }
    
    public void verificarSeExiste(Usuario u) throws ControladorException{
        verificarSeExiste(u.getCodUsuario());
    }
    
    public void excluir(Usuario u) throws ControladorException{
        try{
            rpUsr.excluir(u);
        }
        catch(ConexaoException ce){
            throw new ControladorException(user.getNome(),
                    rb.getString("CtrlErroDelIndisp") + " usuário.",
                    ControladorUsuario.class.getName()+".excluir()");
        }
        catch(RepositorioForeignKeyException rfke){
            throw new ControladorException(user.getNome(),
                    rb.getString("CtrlNaoPodeExcluirUsu"),
                    ControladorUsuario.class.getName()+".excluir()");
        }
        catch(RepositorioExcluirException re){
            throw new ControladorException(user.getNome(),
                    rb.getString("CtrlErroExcluir") + " usuário.",
                    ControladorUsuario.class.getName()+".excluir()");
        }
    }
    
    public List<Usuario> listar() throws ControladorException{
        try{
            return rpUsr.listar();
        }
        catch(ConexaoException e){
            throw new ControladorException(user.getNome(),
                    rb.getString("CtrlErroListIndisp") + " usuários.",
                    ControladorUsuario.class.getName()+".listar()");
        }
        catch(RepositorioListarException re){
            throw new ControladorException(user.getNome(),
                    rb.getString("CtrlErroListar") + " usuários.",
                    ControladorUsuario.class.getName()+".listar()");
        }
    }
    
    public Usuario trazer(String cpf) throws ControladorException{
        try{
            Usuario usu= rpUsr.pesqCpf(cpf);
            return usu;
        }
        catch(ConexaoException ce){
            throw new ControladorException(user.getNome(),
                    rb.getString("CtrlErroTrazerIndisp") + " usuário.",
                    ControladorUsuario.class.getName()+".trazer()");
        }
        catch(RepositorioPesquisarException re){
            throw new ControladorException(user.getNome(),
                    rb.getString("CtrlErroTrazer") + " funcionario.",
                    ControladorUsuario.class.getName()+".trazer()");
        }
    }
    
    public Usuario trazer(Integer cod) throws ControladorException{
        try{
            Usuario usu= rpUsr.pesqCod(cod);
            return usu;
        }
        catch(ConexaoException ce){
            throw new ControladorException(user.getNome(),
                    rb.getString("CtrlErroTrazerIndisp") + " usuário.",
                    ControladorUsuario.class.getName()+".trazer()");
        }
        catch(RepositorioPesquisarException re){
            throw new ControladorException(user.getNome(),
                    rb.getString("CtrlErroTrazer") + " usuário.",
                    ControladorUsuario.class.getName()+".trazer()");
        }
    }
    
    public List<Usuario> pesquisar(String nome) throws ControladorException{
        try{
            return rpUsr.pesquisar(nome);
        }
        catch(ConexaoException e){
            throw new ControladorException(user.getNome(),
                    rb.getString("CtrlErroPesqIndisp") + " usuário.",
                    ControladorUsuario.class.getName()+".pesquisar()");
        }
        catch(RepositorioPesquisarException re){
            throw new ControladorException(user.getNome(),
                    rb.getString("CtrlErroPesquisar") + " usuário.",
                    ControladorUsuario.class.getName()+".pesquisar()");
        }
    }
    
    public void validarLogin(Usuario u) throws ControladorException{
        List<Usuario> lista;
        try{
            lista= rpUsr.pesquisar(u.getNome());
            if (lista.isEmpty()){
                throw new ControladorException(u.getNome(),
                    rb.getString("CtrlUsuInvalido"),
                    ControladorUsuario.class.getName()+".validarLogin()");
            }
            if (lista.get(0).getSenha().compareTo(u.getSenha()) != 0){
                throw new ControladorException(u.getNome(),
                    rb.getString("CtrlUsuSenhaInvalida"),
                    ControladorUsuario.class.getName()+".validarLogin()");
            }
            u.setCodUsuario(lista.get(0).getCodUsuario());
            u.setPerfil(lista.get(0).getPerfil());
            u.setFuncionario(lista.get(0).getFuncionario());
            setUser(u);
        }
        catch(ConexaoException e){
            throw new ControladorException(u.getNome(),
                    rb.getString("CtrlErroVldLoginIndisp"),
                    ControladorUsuario.class.getName()+".validarLogin()");
        }
        catch(RepositorioPesquisarException re){
            throw new ControladorException(u.getNome(),
                    rb.getString("CtrlErroVldLogin"),
                    ControladorUsuario.class.getName()+".validarLogin()");
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
