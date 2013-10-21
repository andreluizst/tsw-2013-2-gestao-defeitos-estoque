/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ce.model.regra;

import ce.erro.ConexaoException;
import ce.erro.ControladorException;
import ce.erro.RepositorioException;
import ce.erro.RepositorioForeignKeyException;
import ce.erro.RepositorioInserirException;
import ce.erro.RepositorioAlterarException;
import ce.erro.RepositorioExcluirException;
import ce.erro.RepositorioListarException;
import ce.erro.RepositorioPesquisarException;
import ce.model.basica.Categoria;
import ce.model.basica.Usuario;
import ce.model.dao.RepositorioCategoria;
import ce.model.dao.IRepositorioCategoria;
import java.util.ResourceBundle;
import java.util.List;

/**
 *
 * @author Andre
 */
public class ControladorCategoria {
    private Usuario user;
    private IRepositorioCategoria rpCateg= new RepositorioCategoria();
    private ResourceBundle rb= ResourceBundle.getBundle("ce.erro.Erro");
    
    public ControladorCategoria(){
        user= new Usuario();
    }
    
    public ControladorCategoria(Usuario user){
        this.user=user;
    }
    
    public void validarDados(Categoria c) throws ControladorException{
        if(c.getDescricao()==null){
            throw new ControladorException(getUser().getNome(), 
                    rb.getString("CtrlErroValInvalido"),
                    ControladorCategoria.class.getName()+".validarDados()");
        }
        if(c.getDescricao().compareTo("") == 0){
            throw new ControladorException(getUser().getNome(),
                    rb.getString("CtrlErroValInvalido"),
                    ControladorCategoria.class.getName()+".validarDados()");
        }
    }

    public void verificarSePodeInserir(Categoria c) throws ControladorException{
        try {
            Categoria aux = rpCateg.pesquisar(c.getDescricao());
            if(aux!=null){
                throw new ControladorException(getUser().getNome(),
                        rb.getString("CtrlCategExiste"),
                        ControladorCategoria.class.getName()+".verificarSePodeInserir()");
            }
        } catch (ConexaoException ex) {
            throw new ControladorException(getUser().getNome(),
                    rb.getString("CtrlErroVerifIndisp") + " categoria.",
                    ControladorCategoria.class.getName()+".verificarSePodeInserir()");
        } catch (RepositorioException ex) {
            throw new ControladorException(getUser().getNome(),
                    rb.getString("CtrlErroVerificar") + " categoria.",
                    ControladorCategoria.class.getName()+".verificarSePodeInserir()");
        }
    }

    public void inserir(Categoria c) throws ControladorException {
        try {
            rpCateg.incluir(c);
        } catch (ConexaoException ex) {
            throw new ControladorException(getUser().getNome(),
                    rb.getString("CtrlErroInsIndisp") + " categoria.",
                    ControladorCategoria.class.getName()+".inserir()");
        } catch (RepositorioInserirException ex) {
            throw new ControladorException(getUser().getNome(),
                    rb.getString("CtrlErroInserir") + " categoria.",
                    ControladorCategoria.class.getName()+".inserir()");
        }
    }
    
    public void alterar(Categoria c) throws ControladorException {
        try{
            rpCateg.alterar(c);
        }
        catch(ConexaoException ce){
            throw new ControladorException(getUser().getNome(),
                    rb.getString("CtrlErroAltIndisp") + " categoria.",
                    ControladorCategoria.class.getName()+".alterar()");
        }
        catch(RepositorioAlterarException rae){
            throw new ControladorException(getUser().getNome(),
                    rb.getString("CtrlErroAlterar") + " categoria.",
                    ControladorCategoria.class.getName()+".alterar()");
        }
    }
    
    public void verificarSeExiste(Integer cod) throws ControladorException{
        Categoria categ=null;
        try{
            categ= rpCateg.pesqPorCod(cod);
            if (categ == null){
                throw new ControladorException(getUser().getNome(),
                        rb.getString("CtrlCategNaoExiste"),
                        ControladorCategoria.class.getName()+".verificarSeExiste()");
            }
        }
        catch(ConexaoException ce){
            throw new ControladorException(getUser().getNome(),
                    rb.getString("CtrlErroPesqIndisp") + " categoria.",
                    ControladorCategoria.class.getName()+".verificarSeExiste()");
        }
        catch(RepositorioPesquisarException re){
            throw new ControladorException(getUser().getNome(),
                    rb.getString("CtrlErroPesquisar") + " categoria.",
                    ControladorCategoria.class.getName()+".verificarSeExiste()");
        }
    }
    
    public void verificarSeExiste(Categoria c) throws ControladorException{
        verificarSeExiste(c.getCodCateg());
    }
    
    public void excluir(Categoria c) throws ControladorException{
        try{
            rpCateg.excluir(c.getCodCateg());
        }
        catch(ConexaoException ce){
            throw new ControladorException(getUser().getNome(),
                    rb.getString("CtrlErroDelIndisp") + " categoria.",
                    ControladorCategoria.class.getName()+".excluir()");
        }
        catch(RepositorioForeignKeyException rfke){
            throw new ControladorException(getUser().getNome(),
                    rb.getString("CtrlNaoPodeExcluirCateg"),
                    ControladorCategoria.class.getName()+".excluir()");
        }
        catch(RepositorioExcluirException re){
            throw new ControladorException(getUser().getNome(),
                    rb.getString("CtrlErroExcluir") + " categoria.",
                    ControladorCategoria.class.getName()+".excluir()");
        }
    }
    
    public Categoria pesquisar(String descCateg) throws ControladorException{
        try{
            return rpCateg.pesquisar(descCateg);
        }
        catch(ConexaoException ce){
            throw new ControladorException(getUser().getNome(),
                    rb.getString("CtrlErroPesqIndisp") + " categoria.",
                    ControladorCategoria.class.getName()+".pesquisar()");
        }
        catch(RepositorioPesquisarException re){
            throw new ControladorException(getUser().getNome(),
                    rb.getString("CtrlErroPesquisar") + " categoria.",
                    ControladorCategoria.class.getName()+".pesquisar()");
        }
    }
    
    public Categoria trazer(Integer cod) throws ControladorException{
        try{
            return rpCateg.pesqPorCod(cod);
        }
        catch(ConexaoException ce){
            throw new ControladorException(getUser().getNome(),
                    rb.getString("CtrlErroTrazerIndisp") + " categoria.",
                    ControladorCategoria.class.getName()+".trazer()");
        }
        catch(RepositorioPesquisarException re){
            throw new ControladorException(getUser().getNome(),
                    rb.getString("CtrlErroTrazer") + " categoria.",
                    ControladorCategoria.class.getName()+".trazer()");
        }
    }
    
    public List<Categoria> listar() throws ControladorException{
        try{
            return rpCateg.listar();
        }
        catch(ConexaoException ce){
            throw new ControladorException(getUser().getNome(),
                    rb.getString("CtrlErroListIndisp") + " categoria.",
                    ControladorCategoria.class.getName()+".listar()");
        }
        catch(RepositorioListarException re){
            throw new ControladorException(getUser().getNome(),
                    rb.getString("CtrlErroListar") + " categoria.",
                    ControladorCategoria.class.getName()+".listar()");
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
