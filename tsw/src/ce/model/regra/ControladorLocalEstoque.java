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
import ce.model.basica.LocalEstoque;
import ce.model.basica.Usuario;
import ce.model.dao.RepositorioLocalEstoque;
import ce.model.dao.IRepositorioLocalEstoque;
import java.util.List;
import java.util.ResourceBundle;

/**
 *
 * @author Andre
 */
public class ControladorLocalEstoque {
    private Usuario user;
    private IRepositorioLocalEstoque rpLocalE= new RepositorioLocalEstoque();
    private ResourceBundle rb= ResourceBundle.getBundle("ce.erro.Erro");
    
    public ControladorLocalEstoque(){
        user= new Usuario();
    }
    
    public ControladorLocalEstoque(Usuario user){
        this.user=user;
    }
    
    public void validarDados(LocalEstoque le) throws ControladorException{
        if (le.getDescricao() == null){
            throw new ControladorException(getUser().getNome(),
                    rb.getString("CtrlErroValInvalido"),
                    ControladorLocalEstoque.class.getName()+".validarDados()");
        }
    }
    
    public void verificarSePodeInserir(LocalEstoque le) throws ControladorException{
        List<LocalEstoque> lista=null;
        try {
            lista = rpLocalE.pesquisar(le.getDescricao());
            if(lista.size() > 0){
                throw new ControladorException(getUser().getNome(),
                        rb.getString("CtrlLocalEstExiste"),
                        ControladorLocalEstoque.class.getName()+".verificarSePodeInserir()");
            }
        } catch (ConexaoException ex) {
            throw new ControladorException(getUser().getNome(),
                    rb.getString("CtrlErroVerifIndisp") + " local de estoque.",
                    ControladorLocalEstoque.class.getName()+".verificarSePodeInserir()");
        } catch (RepositorioException ex) {
            throw new ControladorException(getUser().getNome(),
                    rb.getString("CtrlErroVerificar") + " local de estoque.",
                    ControladorLocalEstoque.class.getName()+".verificarSePodeInserir()");
        }
    }
    
    public void inserir(LocalEstoque le) throws ControladorException {
        try {
            rpLocalE.inserir(le);
        } catch (ConexaoException ce) {
            throw new ControladorException(getUser().getNome(),
                    rb.getString("CtrlErroInsIndisp") + " local de estoque.",
                    ControladorLocalEstoque.class.getName()+".inserir()");
        } catch (RepositorioInserirException re) {
            throw new ControladorException(getUser().getNome(),
                    rb.getString("CtrlErroInserir") + " local de estoque.",
                    ControladorLocalEstoque.class.getName()+".inserir()");
        }
    }
    
    public void alterar(LocalEstoque le) throws ControladorException {
        try{
            rpLocalE.alterar(le);
        }
        catch(ConexaoException ce){
            throw new ControladorException(getUser().getNome(),
                    rb.getString("CtrlErroAltIndisp") + " local de estoque.",
                    ControladorLocalEstoque.class.getName()+".alterar()");
        }
        catch(RepositorioAlterarException rae){
            throw new ControladorException(getUser().getNome(),
                    rb.getString("CtrlErroAlterar") + " local de estoque.",
                    ControladorLocalEstoque.class.getName()+".alterar()");
        }
    }
    
    public void verificarSeExiste(Integer cod) throws ControladorException{
        try{
            LocalEstoque local= rpLocalE.pesqCod(cod);
            if (local == null){
                throw new ControladorException(getUser().getNome(),
                        rb.getString("CtrlLocalEstNaoExiste"),
                        ControladorLocalEstoque.class.getName()+".verificarSeExiste()");
            }
        }
        catch(ConexaoException e){
            throw new ControladorException(getUser().getNome(),
                    rb.getString("CtrlErroVerifIndisp") + " local de estoque.",
                    ControladorLocalEstoque.class.getName()+".verificarSePodeExcluir()");
        }
        catch(RepositorioPesquisarException ie){
            throw new ControladorException(getUser().getNome(),
                    rb.getString("CtrlErroVerificar") + " local de estoque.",
                    ControladorLocalEstoque.class.getName()+".verificarSePodeExcluir()");
        }
    }
    
    public void verificarSeExiste(LocalEstoque le) throws ControladorException{
        verificarSeExiste(le.getCodLocal());
    }
    
    /*Marcado para exclusão futura
    public void verificarSeExiste(LocalEstoque le) throws ControladorException{
        try{
            List<LocalEstoque> lista= rpLocalE.pesquisar(le.getDescricao());
            if ((lista == null) || lista.isEmpty()){
                throw new ControladorException(rb.getString("CtrlLocalEstNaoExiste"),
                        "ControladorLocalEstoque.verificarSeExiste()");
            }
        }
        catch(ConexaoException e){
            throw new ControladorException(
                    rb.getString("CtrlErroVerifIndisp") + " local de estoque.",
                    "ControladorLocalEstoque.verificarSePodeExcluir()");
        }
        catch(RepositorioPesquisarException ie){
            throw new ControladorException(
                    rb.getString("CtrlErroVerificar") + " local de estoque.",
                    "ControladorLocalEstoque.verificarSePodeExcluir()");
        }
    }*/
    
    public void excluir(LocalEstoque le) throws ControladorException{
        try{
            rpLocalE.excluir(le.getCodLocal());
        }
        catch(ConexaoException ce){
            throw new ControladorException(getUser().getNome(),
                    rb.getString("CtrlErroDelIndisp") + " local de estoque.",
                    ControladorLocalEstoque.class.getName()+".excluir()");
        }
        catch(RepositorioForeignKeyException rfke){
            throw new ControladorException(getUser().getNome(),
                    rb.getString("CtrlNaoPodeExcluirLocalEst"),
                    ControladorLocalEstoque.class.getName()+".excluir()");
        }
        catch(RepositorioExcluirException re){
            throw new ControladorException(getUser().getNome(),
                    rb.getString("CtrlErroExcluir") + " local de estoque.",
                    ControladorLocalEstoque.class.getName()+".excluir()");
        }
    }
    
    public List<LocalEstoque> listar() throws ControladorException{
        try{
            return rpLocalE.listar();
        }
        catch(ConexaoException ce){
            throw new ControladorException(getUser().getNome(),
                    rb.getString("CtrlErroListIndisp") + " local de estoque.",
                    ControladorLocalEstoque.class.getName()+".listar()");
        }
        catch(RepositorioListarException re){
            throw new ControladorException(getUser().getNome(),
                    rb.getString("CtrlErroListar") + " local de estoque.",
                    ControladorLocalEstoque.class.getName()+".listar()");
        }
    }
    
    public List<LocalEstoque> pesquisar(String nome) throws ControladorException{
        try{
            return rpLocalE.pesquisar(nome);
        }
        catch(ConexaoException ce){
            throw new ControladorException(getUser().getNome(),
                    rb.getString("CtrlErroPesqIndisp") + " local de estoque.",
                    ControladorLocalEstoque.class.getName()+".pesquisar()");
        }
        catch(RepositorioPesquisarException re){
            throw new ControladorException(getUser().getNome(),
                    rb.getString("CtrlErroPesquisar") + " local de estoque.",
                    ControladorLocalEstoque.class.getName()+".pesquisar()");
        }
    }
    
    public LocalEstoque trazer(Integer cod) throws ControladorException{
        try{
            return rpLocalE.pesqCod(cod);
        }
        catch(ConexaoException ce){
            throw new ControladorException(getUser().getNome(),
                    rb.getString("CtrlErroTrazerIndisp") + " local de estoque.",
                    ControladorLocalEstoque.class.getName()+".trazer()");
        }
        catch(RepositorioPesquisarException re){
            throw new ControladorException(getUser().getNome(),
                    rb.getString("CtrlErroTrazer") + " local de estoque.",
                    ControladorLocalEstoque.class.getName()+".trazer()");
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
