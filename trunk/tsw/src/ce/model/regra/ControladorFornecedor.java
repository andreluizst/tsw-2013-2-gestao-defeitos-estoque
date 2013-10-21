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
import ce.model.basica.Fornecedor;
import ce.model.basica.Usuario;
//import ce.model.dao.RepositorioProduto;
import ce.model.dao.RepositorioFornecedor;
import ce.model.dao.IRepositorioFornecedor;
import ce.util.VerificarCpfCnpj;
import java.util.List;
import java.util.ResourceBundle;

/**
 *
 * @author andreluiz
 */
public class ControladorFornecedor {
    private Usuario user;
    private IRepositorioFornecedor rpForn= new RepositorioFornecedor();
    private ResourceBundle rb= ResourceBundle.getBundle("ce.erro.Erro");
    
    /**
     * Construtor padrão
     */
    public ControladorFornecedor(){
        user= new Usuario();
    }
    /**
     * 
     * @param user 
     */
    public ControladorFornecedor(Usuario user){
        this.user=user;
    }
    
    /**
     * Valida os dados do fornecedor especificado.
     * @param f
     * Objeto do tipo Fornecedor que será validado.
     * @throws ControladorException
     * Se houver algum erro na execução será lançada uma exceção.
     */
    public void validarDados(Fornecedor f) throws ControladorException{
        /*boolean contemErro= false;
        List fieldsName= new ArrayList();
        List fieldsMsg= new ArrayList();
        if (f.getNome() == null){
            fieldsName.add("Nome");
            fieldsMsg.add("O campo não pode estar em branco.");
            contemErro=true;
        }*/
        String sPath= ControladorFornecedor.class.getName()+".validarDados()";
        if ((f.getNome() == null) || (f.getNome().compareTo("")==0)){
            throw new ControladorException(user.getNome(),
                    "O campo nome deve ser preenchido.",
                    sPath);
        }
        //Substituir pela linha abaixo após os testes
        //if (!VerificarCpfCnpj.executar(f.getCnpj())){
        if ((f.getCnpj() == null) || (f.getCnpj().compareTo("")==0)){
            throw new ControladorException(user.getNome(),
                    "CNPJ inválido.", sPath);
        }
        if ((f.getLogradouro() == null)||(f.getLogradouro().compareTo("")==0)){
            throw new ControladorException(user.getNome(),
                    "Informe o logradouro do fornecedor",
                    sPath);
        }
        if ((f.getBairro() == null) || (f.getBairro().compareTo("")==0)){
            throw new ControladorException(user.getNome(),
                    "Informe o Bairro do fornecedor", 
                    sPath);
        }
        if ((f.getMunicipio() == null) || (f.getMunicipio().compareTo("")==0)){
            throw new ControladorException(user.getNome(),
                    "Informe o municipio do fornecedor", sPath);
        }
        if ((f.getEstado().getUf() == null) || (f.getEstado().getUf().compareTo("")==0)){
            throw new ControladorException(user.getNome(),
                    "Informe a sigla do estado do fornecedor", sPath);
        }
        if ((f.getCep() == null) || (f.getCep().compareTo("")==0)){
            throw new ControladorException(user.getNome(),
                    "Informe o CEP", sPath);
        }
        if ((f.getFone() == null) || (f.getFone().compareTo("")==0)){
            throw new ControladorException(user.getNome(),
                    "Informe o número do telefone do fornecedor", sPath);
        }
    }
    
    /**
     * Verifica se o fornecedor especificado pode ser incluido.
     * @param f
     * Objeto do tipo Fornecedor que será verificado.
     * @throws ControladorException
     * Se houver algum erro na execução será lançada uma exceção.
     */
    public void verificarSePodeInserir(Fornecedor f) throws ControladorException{
        try{
            Fornecedor forn= rpForn.pesqCnpj(f.getCnpj(), false);
            if (forn != null){
                throw new ControladorException(user.getNome(),
                        rb.getString("CtrlFornExiste"),
                        ControladorFornecedor.class.getName()+".verificarSePodeInserir()");
            }
        }
        catch(ConexaoException ce){
            throw new ControladorException(user.getNome(),
                    rb.getString("CtrlErroVerifIndisp") + " fornecedor.",
                    ControladorFornecedor.class.getName()+".verificarSePodeInserir()");
        }
        catch(RepositorioPesquisarException re){
            throw new ControladorException(user.getNome(),
                    rb.getString("CtrlErroVerificar") + " fornecedor.",
                    ControladorFornecedor.class.getName()+".verificarSePodeInserir()");
        }
    }
    
    /**
     * Inclui um fornecedor
     * @param f
     * Objeto do tipo Fornecedor que será incluido.
     * @throws ControladorException
     * Se houver algum erro na execução será lançada uma exceção.
     */
    public void inserir(Fornecedor f) throws ControladorException{
        try{
            rpForn.inserir(f);
        }
        catch(ConexaoException ce){
            throw new ControladorException(user.getNome(),
                    rb.getString("CtrlErroInsIndisp") + " fornecedor.",
                    ControladorFornecedor.class.getName()+".inserir()");
        }
        catch(RepositorioInserirException e){
            throw new ControladorException(user.getNome(),
                    rb.getString("CtrlErroInserir") + " fornecedor.",
                    ControladorFornecedor.class.getName()+".inserir()");
        }
    }
    
    /**
     * Altera um fornecedor
     * @param f
     * Objeto do tipo Fornecedor que será alterado.
     * @throws ControladorException
     * Se houver algum erro na execução será lançada uma exceção.
     */
    public void alterar(Fornecedor f) throws ControladorException{
        try{
            rpForn.alterar(f);
        }
        catch(ConexaoException ce){
            throw new ControladorException(user.getNome(),
                    rb.getString("CtrlErroAltIndisp") + " fornecedor.",
                    ControladorFornecedor.class.getName()+".alterar()");
        }
        catch(RepositorioAlterarException e){
            throw new ControladorException(user.getNome(),
                    rb.getString("CtrlErroAlterar") + " fornecedor.",
                    ControladorFornecedor.class.getName()+".alterar()");
        }
    }
    
    /**
     * Verificar se um determinado fornecedor existe.
     * @param cod
     * Código do fornecedor para verificação.
     * @throws ControladorException
     * Se houver algum erro na execução será lançada uma exceção.
     */
    public void verificarSeExiste(Integer cod) throws ControladorException {
        try{
            Fornecedor forn= rpForn.pesqCodForn(cod, false);
            if (forn == null){
                throw new ControladorException(user.getNome(),
                        rb.getString("CtrlFornNaoExiste"),
                        ControladorFornecedor.class.getName()+".verificarSeExiste()");
            }
        }
        catch(ConexaoException e){
            throw new ControladorException(user.getNome(),
                    rb.getString("CtrlErroVerifIndisp") + " fornecedor.",
                    ControladorFornecedor.class.getName()+".verificarSeExiste()");
        }
        catch(RepositorioPesquisarException ie){
            throw new ControladorException(user.getNome(),
                    rb.getString("CtrlErroVerificar") + " fornecedor.",
                    ControladorFornecedor.class.getName()+".verificarSeExiste()");
        }
    }
    
    /**
     * Verifica se um determinado fornecedor existe. 
     * @param f
     * Objeto do tipo Fornecedor que será verificado.
     * @throws ControladorException
     * Se houver algum erro na execução será lançada uma exceção.
     */
    public void verificarSeExiste(Fornecedor f) throws ControladorException {
        verificarSeExiste(f.getCodForn());
    }
    
    /**
     * Exclui um fornecedor
     * @param f
     * Objeto do tipo Fornecedor que deseja excluir
     * @throws ControladorException
     * Se houver algum erro na execução será lançada uma exceção.
     */
    public void excluir(Fornecedor f) throws ControladorException{
        try{
            rpForn.excluir(f.getCodForn());
        }
        catch(ConexaoException e){
            throw new ControladorException(user.getNome(),
                    rb.getString("CtrlErroDelIndisp") + " fornecedor.",
                    ControladorFornecedor.class.getName()+".excluir()");
        }
        catch(RepositorioForeignKeyException rfke){
            throw new ControladorException(user.getNome(),
                    rb.getString("CtrlNaoPodeExcluirForn"),
                    ControladorFornecedor.class.getName()+".excluir()");
        }
        catch(RepositorioExcluirException re){
            throw new ControladorException(user.getNome(),
                    rb.getString("CtrlErroExcluir") + " fornecedor.",
                    ControladorFornecedor.class.getName()+".excluir()");
        }
    }
    
    /**
     * Lista todos os fornecedores existentes.
     * @return
     * Retorna uma lista com os Fornecedores
     * @throws ControladorException
     * Se houver algum erro na execução será lançada uma exceção.
     */
    public List<Fornecedor> listar() throws ControladorException{
        try{
            return rpForn.listar();
        }
        catch(ConexaoException e){
            throw new ControladorException(user.getNome(),
                    rb.getString("CtrlErroListIndisp") + " fornecedor.",
                    ControladorFornecedor.class.getName()+".listar()");
        }
        catch(RepositorioListarException re){
            throw new ControladorException(user.getNome(),
                    rb.getString("CtrlErroListar") + " fornecedor.",
                    ControladorFornecedor.class.getName()+".listar()");
        }
    }
    
    /**
     * Retorna um fornecedor com ou sem os produtos fornecidos.
     * @param cod
     * @param comProds
     * Se false a lista de produtos do Fornecedor não será preenchida.
     * @return
     * Fornecedor
     * @throws ControladorException
     * Se houver algum erro na execução será lançada uma exceção.
     */
    public Fornecedor trazer(Integer cod, boolean comProds) throws ControladorException{
        try{
            Fornecedor forn= rpForn.pesqCodForn(cod, comProds);
            if (forn == null){
                throw new ControladorException(user.getNome(),
                        rb.getString("CtrlFornNaoExiste"),
                        ControladorFornecedor.class.getName()+".trazer()");
            }
            return forn;
        }
        catch(ConexaoException ce){
            throw new ControladorException(user.getNome(),
                    rb.getString("CtrlErroTrazerIndisp") + " fornecedor.",
                    ControladorFornecedor.class.getName()+".trazer()");
        }
        catch(RepositorioPesquisarException re){
            throw new ControladorException(user.getNome(),
                    rb.getString("CtrlErroTrazer") + " fornecedor.",
                    ControladorFornecedor.class.getName()+".trazer()");
        }
    }
    
    /**
     * Pesquisa fonrcedor pelo CNPJ
     * @param cnpj
     * CNPJ do Fornecedor desejado
     * @param comProds
     * Se false a lista de produtos do Fornecedor não será preenchida.
     * @return
     * Retorna um Fornecedor com todos os seus atributos preenchidos
     * @throws ControladorException
     * Se houver algum erro na execução será lançada uma exceção.
     */
    public Fornecedor trazer(String cnpj, boolean comProds) throws ControladorException{
        try{
            Fornecedor forn= rpForn.pesqCnpj(cnpj, comProds);
            return forn;
        }
        catch(ConexaoException ce){
            throw new ControladorException(user.getNome(),
                    rb.getString("CtrlErroTrazerIndisp") + " fornecedor.",
                    ControladorFornecedor.class.getName()+".trazer()");
        }
        catch(RepositorioPesquisarException re){
            throw new ControladorException(user.getNome(),
                    rb.getString("CtrlErroTrazer") + " fornecedor.",
                    ControladorFornecedor.class.getName()+".trazer()");
        }
    }
    
    /**
     * Pesquisa fornecedores por nome
     * @param nome
     * Nome do fornecedor desejado.
     * É possível a utilização de caracteres coringas no parâmetro nome dando
     * maior flexibilidade a pesquisa. Ex: "comer%"
     * @return
     * Retorna uma lista com o(s) Fornecedor(es) encontrado(s).
     * @throws ControladorException
     * Se houver algum erro na execução será lançada uma exceção.
     */
    public List<Fornecedor> pesquisar(String nome) throws ControladorException{
        try{
            return rpForn.pesquisar(nome);
        }
        catch(ConexaoException e){
            throw new ControladorException(user.getNome(),
                    rb.getString("CtrlErroPesqIndisp") + " fornecedor.",
                    ControladorFornecedor.class.getName()+".pesquisar()");
        }
        catch(RepositorioPesquisarException re){
            throw new ControladorException(user.getNome(),
                    rb.getString("CtrlErroPesquisar") + " fornecedor.",
                    ControladorFornecedor.class.getName()+".pesquisar()");
        }
    }
    
    /**
     * Pesquisa os fornecedores que não fornecem o produto especificado.
     * @param codProd
     * Código do produto cujos fornecedores não constarão na lista.
     * @return
     * @throws ControladorException 
     * Se houver algum erro na execução será lançada uma exceção.
     */
    public List<Fornecedor> pesqFornsQueNaoFornecemEsteProd(Integer codProd)
            throws ControladorException{
        try{
            return rpForn.pesqFornsQueNaoFornecemEsteProd(codProd);
        }
        catch(ConexaoException e){
            throw new ControladorException(user.getNome(),
                    rb.getString("CtrlErroPesqIndisp") + " fornecedor.",
                    ControladorFornecedor.class.getName()+".pesqFornsQueNaoFornecemEsteProd()");
        }
        catch(RepositorioPesquisarException re){
            throw new ControladorException(user.getNome(),
                    rb.getString("CtrlErroPesquisar") + " fornecedor.",
                    ControladorFornecedor.class.getName()+".pesqFornsQueNaoFornecemEsteProd()");
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
