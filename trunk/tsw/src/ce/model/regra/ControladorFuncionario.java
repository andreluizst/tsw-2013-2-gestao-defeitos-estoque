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
import ce.erro.RepositorioForeignKeyException;
import ce.erro.RepositorioPesquisarException;
import ce.erro.RepositorioListarException;
import ce.model.basica.Funcionario;
import ce.model.basica.Usuario;
import ce.model.dao.RepositorioFuncionario;
import ce.model.dao.IRepositorioFuncionario;
import java.util.List;
import java.util.ResourceBundle;
import ce.util.ValidarStringData;
import ce.util.VerificarCpfCnpj;

/**
 *
 * @author Andre
 */
public class ControladorFuncionario {
    private Usuario user;
    private IRepositorioFuncionario rpFun= new RepositorioFuncionario();
    private ResourceBundle rb= ResourceBundle.getBundle("ce.erro.Erro");
    
    
    public ControladorFuncionario(){
        user= new Usuario();
    }
    
    public ControladorFuncionario(Usuario user){
        this.user=user;
    }
    
    public void validarDados(Funcionario f) throws ControladorException{
        String sPath= ControladorFuncionario.class.getName()+".validarDados()";
        if ((f.getNome() == null) || (f.getNome().compareTo("")==0)){
            throw new ControladorException(user.getNome(),
                    "O campo nome deve ser preenchido.",
                    sPath);
        }
        if ((f.getCpf() == null) || (f.getCpf().compareTo("")==0)){
            throw new ControladorException(user.getNome(),
                    "O campo CPF deve ser preenchido.",
                    sPath);
        }
        /*
        if (!VerificarCpfCnpj.executar(f.getCpf())){
            throw new ControladorException(user.getNome(),
                    "CPF inválido!", sPath);
        }
        */
        if (!ValidarStringData.execute(f.getDtNasc())){
           throw new ControladorException(user.getNome(),
                   "O campo data deve ser preenchido com uma data válida.",
                   sPath);
        }
        if ((f.getLogradouro() == null) || (f.getLogradouro().compareTo("")==0)){
            throw new ControladorException(user.getNome(),
                    "O campo logradouro deve ser preenchido.",
                    sPath);
        }
        if ((f.getBairro() == null) || (f.getBairro().compareTo("")==0)){
            throw new ControladorException(user.getNome(),
                    "O campo bairro deve ser preenchido.",
                    sPath);
        }
        if ((f.getMunicipio() == null) || (f.getMunicipio().compareTo("")==0)){
            throw new ControladorException(user.getNome(),
                    "O campo municipio deve ser preenchido.",
                    sPath);
        }
        if ((f.getEstado().getUf() == null) || (f.getEstado().getUf().compareTo("")==0)){
            throw new ControladorException(user.getNome(),
                    "O campo UF deve ser preenchido.",
                    sPath);
        }
        if ((f.getCep() == null) || (f.getCep().compareTo("")==0)){
            throw new ControladorException(user.getNome(), 
                    "O campo CEP deve ser preenchido.",
                    sPath);
        }
        if ((f.getFone() == null) || (f.getFone().compareTo("")==0)){
            throw new ControladorException(user.getNome(),
                    "O campo fone deve ser preenchido.",
                    sPath);
        }
    }
    
    public void verificarSePodeInserir(Funcionario f) throws ControladorException{
        try{
            Funcionario fun= rpFun.pesqCpf(f.getCpf());
            if (fun != null){
                throw new ControladorException(user.getNome(),
                        rb.getString("CtrlFunExiste"),
                        ControladorFuncionario.class.getName()+".verificarSePodeInserir()");
            }
        }
        catch(ConexaoException ce){
            throw new ControladorException(user.getNome(),
                    rb.getString("CtrlErroVerifIndisp") + " funcionário.",
                    ControladorFuncionario.class.getName()+".verificarSePodeInserir()");
        }
        catch(RepositorioPesquisarException re){
            throw new ControladorException(user.getNome(),
                    rb.getString("CtrlErroVerificar") + " funcionário.",
                    ControladorFuncionario.class.getName()+".verificarSePodeInserir()");
        }
    }
    
    public void inserir(Funcionario f) throws ControladorException{
        try{
            rpFun.inserir(f);
        }
        catch(ConexaoException ce){
            throw new ControladorException(user.getNome(),
                    rb.getString("CtrlErroInsIndisp") + " funcionário.",
                    ControladorFuncionario.class.getName()+".inserir()");
        }
        catch(RepositorioInserirException e){
            throw new ControladorException(user.getNome(),
                    rb.getString("CtrlErroInserir") + " funcionário.",
                    ControladorFuncionario.class.getName()+".inserir()");
        }
    }
    
    public void alterar(Funcionario f) throws ControladorException{
        try{
            rpFun.alterar(f);
        }
        catch(ConexaoException ce){
            throw new ControladorException(user.getNome(),
                    rb.getString("CtrlErroAltIndisp") + " funcionário.",
                    ControladorFuncionario.class.getName()+".alterar()");
        }
        catch(RepositorioAlterarException e){
            throw new ControladorException(user.getNome(),
                    rb.getString("CtrlErroAlterar") + " funcionário.",
                    ControladorFuncionario.class.getName()+".alterar()");
        }
    }
    
    public void verificarSeExiste(Funcionario f) throws ControladorException {
        verificarSeExiste(f.getCpf());
    }
    
    public void verificarSeExiste(String cpf) throws ControladorException {
        try{
            Funcionario fun= rpFun.pesqCpf(cpf);
            if (fun == null){
                throw new ControladorException(user.getNome(),
                        rb.getString("CtrlFunNaoExiste"),
                        ControladorFuncionario.class.getName()+".verificarSeExiste()");
            }
        }
        catch(ConexaoException e){
            throw new ControladorException(user.getNome(),
                    rb.getString("CtrlErroVerifIndisp") + " funcionário.",
                    ControladorFuncionario.class.getName()+".verificarSeExiste()");
        }
        catch(RepositorioPesquisarException ie){
            throw new ControladorException(user.getNome(),
                    rb.getString("CtrlErroVerificar") + " funcionário.",
                    ControladorFuncionario.class.getName()+".verificarSeExiste()");
        }
    }
    
    public void excluir(Funcionario f) throws ControladorException{
        try{
            rpFun.excluir(f.getCpf());
        }
        catch(ConexaoException e){
            throw new ControladorException(user.getNome(),
                    rb.getString("CtrlErroDelIndisp") + " funcionário.",
                    ControladorFuncionario.class.getName()+".excluir()");
        }
        catch(RepositorioForeignKeyException rfke){
            throw new ControladorException(user.getNome(),
                    rb.getString("CtrlNaoPodeExcluirFun"),
                    ControladorFuncionario.class.getName()+".excluir()");
        }
        catch(RepositorioExcluirException re){
            throw new ControladorException(user.getNome(),
                    rb.getString("CtrlErroExcluir") + " funcionário.",
                    ControladorFuncionario.class.getName()+".excluir()");
        }
    }
    
    public List<Funcionario> listar() throws ControladorException{
        try{
            return rpFun.listar();
        }
        catch(ConexaoException e){
            throw new ControladorException(user.getNome(),
                    rb.getString("CtrlErroListIndisp") + " funcionários.",
                    ControladorFuncionario.class.getName()+".listar()");
        }
        catch(RepositorioListarException re){
            throw new ControladorException(user.getNome(),
                    rb.getString("CtrlErroListar") + " funcionários.",
                    ControladorFuncionario.class.getName()+".listar()");
        }
    }
    
    public Funcionario trazer(String cpf) throws ControladorException{
        try{
            Funcionario fun= rpFun.pesqCpf(cpf);
            return fun;
        }
        catch(ConexaoException ce){
            throw new ControladorException(user.getNome(),
                    rb.getString("CtrlErroTrazerIndisp") + " funcionário.",
                    ControladorFuncionario.class.getName()+".trazer()");
        }
        catch(RepositorioPesquisarException re){
            throw new ControladorException(user.getNome(),
                    rb.getString("CtrlErroTrazer") + " funcionario.",
                    ControladorFuncionario.class.getName()+".trazer()");
        }
    }
    
    public List<Funcionario> pesquisar(String nome) throws ControladorException{
        try{
            return rpFun.pesquisar(nome);
        }
        catch(ConexaoException e){
            throw new ControladorException(user.getNome(),
                    rb.getString("CtrlErroPesqIndisp") + " funcionário.",
                    ControladorFuncionario.class.getName()+".pesquisar()");
        }
        catch(RepositorioPesquisarException re){
            throw new ControladorException(user.getNome(),
                    rb.getString("CtrlErroPesquisar") + " funcionário.",
                    ControladorFuncionario.class.getName()+".pesquisar()");
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
