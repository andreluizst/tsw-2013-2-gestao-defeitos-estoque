/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ce.model.regra;

import ce.erro.*;
import ce.model.basica.Categoria;
import ce.model.basica.Produto;
import ce.model.basica.Usuario;
import ce.model.dao.IRepositorioCategoria;
import ce.model.dao.RepositorioCategoria;
import ce.model.dao.IRepositorioProduto;
import ce.model.dao.RepositorioProduto;
import java.util.List;
import java.util.ResourceBundle;

/**
 *
 * @author andreluiz
 */
public class ControladorProduto {
    private Usuario user;
    private IRepositorioCategoria rpCateg= new RepositorioCategoria();
    private IRepositorioProduto rpProd= new RepositorioProduto();
    private ResourceBundle rb= ResourceBundle.getBundle("ce.erro.Erro");
    
    public ControladorProduto(){
        user= new Usuario();
    }
    
    public ControladorProduto(Usuario user){
        this.user=user;
    }
    
    public void validarDados(Produto p) throws ControladorException{
        if (p.getDescProd() == null){
            throw new ControladorException(user.getNome(),
                    rb.getString("CtrlErroValInvalido"),
                    ControladorProduto.class.getName()+".validarDados()");
        }
    }
    
    /*public void verificarSePodeExcluir(Produto p) throws ControladorException {
        try{
            Produto prod= rpProd.pesqCodProd(p.getCodProd(), false);
            if (prod == null){
                throw new ControladorException(rb.getString("CtrlProdNaoExiste"));
            }
        }
        catch(ConexaoException e){
            throw new ControladorException(
                    rb.getString("CtrlErroDelIndisp") + " produto.",
                    "ControladorProduto.verificarSePodeExcluir()");
        }
        catch(RepositorioPesquisarException ie){
            throw new ControladorException(
                    rb.getString("CtrlErroExcluir") + " produto.",
                    "ControladorProduto.verificarSePodeExcluir()");
        }
    }*/
    
    public void inserir(Produto p) throws ControladorException{
        try{
            rpProd.inserir(p);
        }
        catch(ConexaoException e){
            throw new ControladorException(user.getNome(),
                    rb.getString("CtrlErroInsIndisp") + " produto.",
                    ControladorProduto.class.getName()+".inserir()");
        }
        catch(RepositorioInserirException ie){
            throw new ControladorException(user.getNome(),
                    rb.getString("CtrlErroInserir") + " produto.",
                    ControladorProduto.class.getName()+".inserir()");
        }
    }
    
    public void verificarSeExiste(Integer cod) throws ControladorException{
        Produto prod=null;
        try{
            prod= rpProd.pesqCodProd(cod, false);
            if (prod == null){
                throw new ControladorException(user.getNome(),
                        rb.getString("CtrlPordNaoExiste"),
                        ControladorProduto.class.getName()+".verificarSeExiste()");
            }
        }
        catch(ConexaoException e){
            throw new ControladorException(user.getNome(),
                    rb.getString("CtrlErroVerifIndisp") + " produto.",
                    ControladorProduto.class.getName()+".verificarSeExiste()");
        }
        catch(RepositorioPesquisarException ie){
            throw new ControladorException(user.getNome(),
                    rb.getString("CtrlErroVerificar") + " produto.",
                    ControladorProduto.class.getName()+".verificarSeExiste()");
        }
    }
    
    public void verificarSeExiste(Produto p) throws ControladorException{
        verificarSeExiste(p.getCodProd());
    }
    
    public void alterar(Produto p) throws ControladorException{
        try{
            rpProd.alterar(p);
        }
        catch(ConexaoException e){
            throw new ControladorException(user.getNome(),
                    rb.getString("CtrlErroAltIndisp") + " produto.",
                    ControladorProduto.class.getName()+".alterar()");
        }
        catch(RepositorioException ie){
            throw new ControladorException(user.getNome(),
                    rb.getString("CtrlErroAlterar") + " produto.",
                    ControladorProduto.class.getName()+".alterar()");
        }
    }
    
    public void atualizarQtde(Produto p) throws ControladorException{
        try{
            rpProd.atualizarQtde(p);
        }
        catch(ConexaoException e){
            throw new ControladorException(user.getNome(),
                    rb.getString("CtrlErroAtlzQtdeIndisp"),
                    ControladorProduto.class.getName()+".altualizarQtde()");
        }
        catch(RepositorioException ie){
            throw new ControladorException(user.getNome(),
                    rb.getString("CtrlErroAtlzQtde"),
                    ControladorProduto.class.getName()+".altualizarQtde()");
        }
    }
    
    public void excluir(Produto p) throws ControladorException{
        try{
            rpProd.excluir(p.getCodProd());
        }
        catch(ConexaoException e){
            throw new ControladorException(user.getNome(),
                    rb.getString("CtrlErroDelIndisp") + " produto.",
                    ControladorProduto.class.getName()+".excluir()");
        }
        catch(RepositorioForeignKeyException rfke){
            throw new ControladorException(user.getNome(),
                    rb.getString("CtrlNaoPodeExcluirProd"),
                    ControladorProduto.class.getName()+".excluir()");
        }
        catch(RepositorioException ie){
            throw new ControladorException(user.getNome(),
                    rb.getString("CtrlErroExcluir") + " produto.",
                    ControladorProduto.class.getName()+".excluir()");
        }
    }
    
    public List<Produto> listar() throws ControladorException{
        try{
            return rpProd.listar();
        }
        catch(ConexaoException e){
            throw new ControladorException(user.getNome(),
                    rb.getString("CtrlErroListIndisp") + " produto.",
                    ControladorProduto.class.getName()+".listar()");
        }
        catch(RepositorioException e){
            throw new ControladorException(user.getNome(),
                    rb.getString("CtrlErroListar") + " produto.",
                    ControladorProduto.class.getName()+".listar()");
        }
    }
    
    public List<Produto> pesquisar(String descricao) throws ControladorException{
        try{
            return rpProd.pesquisar(descricao);
        }
        catch(ConexaoException e){
            throw new ControladorException(user.getNome(),
                    rb.getString("CtrlErroPesqIndisp") + " produto.",
                    ControladorProduto.class.getName()+".pesquisar()");
        }
        catch(RepositorioException e){
            throw new ControladorException(user.getNome(),
                    rb.getString("CtrlErroPesquisar") + " produto.",
                    ControladorProduto.class.getName()+".pesquisar()");
        }
    }
    
    public List<Produto> pesquisarProdsQueNaoSaoDoForn(Integer codForn) 
            throws ControladorException{
        try{
            return rpProd.pesquisarProdsQueNaoSaoDoForn(codForn);
        }
        catch(ConexaoException e){
            throw new ControladorException(user.getNome(),
                    rb.getString("CtrlErroPesqIndisp") + " produto.",
                    ControladorProduto.class.getName()+".pesquisarProdsQueNaoSaoDoForn()");
        }
        catch(RepositorioException e){
            throw new ControladorException(user.getNome(),
                    rb.getString("CtrlErroPesquisar") + " produto.",
                    ControladorProduto.class.getName()+".pesquisarProdsQueNaoSaoDoForn()");
        }
    }
    
    public Produto trazer(Integer cod, boolean listarForns) throws ControladorException{
        try{
            return rpProd.pesqCodProd(cod, listarForns);
        }
        catch(ConexaoException e){
            throw new ControladorException(user.getNome(),
                    rb.getString("CtrlErroTrazerIndisp") + " produto.",
                    ControladorProduto.class.getName()+".trazer()");
        }
        catch(RepositorioException e){
            throw new ControladorException(user.getNome(),
                    rb.getString("CtrlErroTrazer") + " produto.",
                    ControladorProduto.class.getName()+".trazer()");
        }
    }
    
    public void verificarSePodeInserir(Produto p) throws ControladorException{
        try{
            List<Produto> lista= rpProd.pesquisar(p.getDescProd());
            if (lista.size() > 0){
                throw new ControladorException(user.getNome(),
                        rb.getString("CtrlProdExiste"),
                        ControladorProduto.class.getName()+".verificarSePodeInserir()");
            }
        }
        catch(ConexaoException ce){
            throw new ControladorException(user.getNome(),
                    rb.getString("CtrlErroVerifIndisp") + " produto.",
                    ControladorProduto.class.getName()+".verificarSePodeInserir()");
        }
        catch(RepositorioPesquisarException re){
            throw new ControladorException(user.getNome(),
                    rb.getString("CtrlErroVerificar") + " produto.",
                    ControladorProduto.class.getName()+".verificarSePodeInserir()");
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
