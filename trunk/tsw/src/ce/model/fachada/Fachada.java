/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ce.model.fachada;

import ce.erro.GeralException;
import ce.model.basica.*;
import ce.model.regra.*;
import java.util.List;
/**
 *
 * @author andreluiz
 */
public class Fachada {
    private static Fachada instancia;
    private Usuario user;
    private ControladorCategoria ctrlCateg;
    private ControladorUnidade ctrlUnid;
    private ControladorProduto ctrlProd;
    private ControladorFornecedor ctrlForn;
    private ControladorFuncionario ctrlFun;
    private ControladorPerfil ctrlPer;
    private ControladorUsuario ctrlUsu;
    private ControladorLocalEstoque ctrlLocalE;
    private ControladorEntrada ctrlE;
    private ControladorSaida ctrlS;
    private ControladorEstado ctrlEst;
    
    private Fachada(){
        ctrlCateg= new ControladorCategoria();
        ctrlUnid= new ControladorUnidade();
        ctrlProd= new ControladorProduto();
        ctrlForn= new ControladorFornecedor();
        ctrlFun= new ControladorFuncionario();
        ctrlPer= new ControladorPerfil();
        ctrlUsu= new ControladorUsuario();
        ctrlLocalE= new ControladorLocalEstoque();
        ctrlE= new ControladorEntrada();
        ctrlS= new ControladorSaida();
        ctrlEst= new ControladorEstado();
        //user= new Usuario();
    }
    
    public static Fachada getInstancia(){
        if (instancia == null){
            instancia= new Fachada();
        }
        return instancia;
    }
    /*
    * *********************** C A T E G O R I A ******************************
    */
    public void incluir(Categoria c) throws GeralException{
        ctrlCateg.validarDados(c);
        ctrlCateg.verificarSePodeInserir(c);
        ctrlCateg.inserir(c);
    }
    
    public void alterar(Categoria c) throws GeralException{
        ctrlCateg.validarDados(c);
        ctrlCateg.verificarSeExiste(c);
        ctrlCateg.alterar(c);
    }
    
    public void excluir(Categoria c)throws GeralException{
        ctrlCateg.verificarSeExiste(c);
        ctrlCateg.excluir(c);
    }
    
    public List<Categoria> listarCategoria() throws GeralException{
        return ctrlCateg.listar();
    }
    
    public Categoria pesquisarCategoria(String descricao) throws GeralException{
        return ctrlCateg.pesquisar(descricao);
    }
    
    public Categoria trazerCategoria(Integer cod) throws GeralException{
        ctrlCateg.verificarSeExiste(cod);
        return ctrlCateg.trazer(cod);
    }
    
    
    /*
     * ********************** U N I D A D E *******************************
     */
    public void incluir(Unidade u) throws GeralException{
        ctrlUnid.validarDados(u);
        ctrlUnid.verificarSePodeInserir(u);
        ctrlUnid.inserir(u);
    }
    
    public void alterar(Unidade u) throws GeralException{
        ctrlUnid.validarDados(u);
        ctrlUnid.verificarSeExiste(u);
        ctrlUnid.alterar(u);
    }
    
    public void excluir(Unidade u)throws GeralException{
        ctrlUnid.verificarSeExiste(u);
        ctrlUnid.excluir(u);
    }
    
    public List<Unidade> listarUnidade() throws GeralException{
        return ctrlUnid.listar();
    }
    
    public List<Unidade> pesquisarUnidade(String descricao) throws GeralException{
        return ctrlUnid.pesquisar(descricao);
    }
    
    public Unidade trazerUnidade(Integer cod) throws GeralException{
        ctrlUnid.verificarSeExiste(cod);
        return ctrlUnid.trazer(cod);
    }
    
    
    /*
     * ********************** P R O D U T O  ******************************
     */
    public void incluir(Produto p) throws GeralException{
        ctrlProd.validarDados(p);
        ctrlProd.verificarSePodeInserir(p);
        ctrlProd.inserir(p);
    }
    
    public void alterar(Produto p) throws GeralException{
        ctrlProd.validarDados(p);
        ctrlProd.verificarSeExiste(p);
        ctrlProd.alterar(p);
    }
    
    public void excluir(Produto p)throws GeralException{
        ctrlProd.verificarSeExiste(p);
        ctrlProd.excluir(p);
    }
    
    public List<Produto> listarProduto() throws GeralException{
        return ctrlProd.listar();
    }
    
    public List<Produto> pesquisarProduto(String descricao) throws GeralException{
        return ctrlProd.pesquisar(descricao);
    }
    
    public List<Produto> pesquisarProdsQueNaoSaoDoForn(Integer codForn)
            throws GeralException{
        return ctrlProd.pesquisarProdsQueNaoSaoDoForn(codForn);
    }
    
    public Produto trazerProduto(Integer cod, boolean comFornecedores) 
            throws GeralException{
        ctrlProd.verificarSeExiste(cod);
        return ctrlProd.trazer(cod, comFornecedores);
    }
    
    public void atlzEstoqueDoProd(Produto p) throws GeralException{
        ctrlProd.verificarSeExiste(p);
        ctrlProd.atualizarQtde(p);
    }
    
    
    
    /*
     * ********************* F O R N E C E D O R ***************************
     */
    public void incluir(Fornecedor f) throws GeralException{
        ctrlForn.validarDados(f);
        ctrlForn.verificarSePodeInserir(f);
        ctrlForn.inserir(f);
    }
    
    public void alterar(Fornecedor f) throws GeralException{
        ctrlForn.validarDados(f);
        ctrlForn.verificarSeExiste(f);
        ctrlForn.alterar(f);
    }
    
    public void excluir(Fornecedor f)throws GeralException{
        ctrlForn.verificarSeExiste(f);
        ctrlForn.excluir(f);
    }
    
    public List<Fornecedor> listarFornecedor() throws GeralException{
        return ctrlForn.listar();
    }
    
    public List<Fornecedor> pesquisarFornecedor(String nome) throws GeralException{
        return ctrlForn.pesquisar(nome);
    }
    
    public List<Fornecedor> pesqFornsQueNaoFornecemEsteProd(Integer codProd)
            throws GeralException{
        return ctrlForn.pesqFornsQueNaoFornecemEsteProd(codProd);
    }
    
    public Fornecedor trazerFornecedor(Integer cod, boolean comProdutos)
            throws GeralException{
        ctrlForn.verificarSeExiste(cod);
        return ctrlForn.trazer(cod, comProdutos);
    }
    
    
    
    /*
     * ********************** F U N C I O N Á R I O ************************
     */
    public void incluir(Funcionario f) throws GeralException{
        ctrlFun.validarDados(f);
        ctrlFun.verificarSePodeInserir(f);
        ctrlFun.inserir(f);
    }
    
    public void alterar(Funcionario f) throws GeralException{
        ctrlFun.validarDados(f);
        ctrlFun.verificarSeExiste(f);
        ctrlFun.alterar(f);
    }
    
    public void excluir(Funcionario f)throws GeralException{
        ctrlFun.verificarSeExiste(f);
        ctrlFun.excluir(f);
    }
    
    public List<Funcionario> listarFuncionario() throws GeralException{
        return ctrlFun.listar();
    }
    
    public Funcionario trazerFuncionario(String cpf)throws GeralException{
        ctrlFun.verificarSeExiste(cpf);
        return ctrlFun.trazer(cpf);
    }
    
    public List<Funcionario> pesquisarFuncionario(String nome) 
            throws GeralException{
        return ctrlFun.pesquisar(nome);
    }
    
    
    /*
     * ************************ P E R F I L ********************************
     */
    public void incluir(Perfil p) throws GeralException{
        ctrlPer.validarDados(p);
        ctrlPer.verificarSePodeInserir(p);
        ctrlPer.inserir(p);
    }
    
    public void alterar(Perfil p) throws GeralException{
        ctrlPer.validarDados(p);
        ctrlPer.verificarSeExiste(p);
        ctrlPer.alterar(p);
    }
    
    public void excluir(Perfil p)throws GeralException{
        ctrlPer.verificarSeExiste(p);
        ctrlPer.excluir(p);
    }
    
    public List<Perfil> listarPerfil() throws GeralException{
        return ctrlPer.listar();
    }
    
    public Perfil trazerPerfil(Integer cod)throws GeralException{
        ctrlPer.verificarSeExiste(cod);
        return ctrlPer.trazer(cod);
    }
    
    public Perfil trazerPerfil(String descricao)throws GeralException{
        ctrlPer.verificarSeExiste(descricao);
        return ctrlPer.trazer(descricao);
    }
    
    
    /*
     * ************************ U S U Á R I O ***************************
     */
    public void login(Usuario u) throws GeralException{
        ctrlUsu.validarLogin(u);
        setUser(u);
    }
    
    public void alterarSenha(Usuario u, String novaSenha) throws GeralException{
        ctrlUsu.validarLogin(u);
        u.setSenha(novaSenha);
        ctrlUsu.validarDados(u);
        ctrlUsu.alterar(u);
        setUser(u);
    }
    
    public void incluir(Usuario u) throws GeralException{
        ctrlUsu.validarDados(u);
        ctrlUsu.verificarSePodeInserir(u);
        ctrlUsu.inserir(u);
    }
    
    public void alterar(Usuario u) throws GeralException{
        ctrlUsu.validarDados(u);
        ctrlUsu.verificarSeExiste(u);
        ctrlUsu.alterar(u);
    }
    
    public void excluir(Usuario u)throws GeralException{
        ctrlUsu.verificarSeExiste(u);
        ctrlUsu.excluir(u);
    }
    
    public List<Usuario> listarUsuario() throws GeralException{
        return ctrlUsu.listar();
    }
    
    public Usuario trazerUsuario(Integer cod)throws GeralException{
        ctrlUsu.verificarSeExiste(cod);
        return ctrlUsu.trazer(cod);
    }
    
    public Usuario trazerUsuario(String cpf)throws GeralException{
        ctrlUsu.verificarSeExiste(cpf);
        return ctrlUsu.trazer(cpf);
    }
    
    public List<Usuario> pesquisarUsuario(String nome) throws GeralException{
        return ctrlUsu.pesquisar(nome);
    }
    
    
    /*
     * ******************* L O C A L  E S T O Q U E **********************
     */
    public void incluir(LocalEstoque le) throws GeralException{
        ctrlLocalE.validarDados(le);
        ctrlLocalE.verificarSePodeInserir(le);
        ctrlLocalE.inserir(le);
    }
    
    public void alterar(LocalEstoque le) throws GeralException{
        ctrlLocalE.validarDados(le);
        ctrlLocalE.verificarSeExiste(le);
        ctrlLocalE.alterar(le);
    }
    
    public void excluir(LocalEstoque le)throws GeralException{
        ctrlLocalE.verificarSeExiste(le);
        ctrlLocalE.excluir(le);
    }
    
    public List<LocalEstoque> listarLocalEstoque() throws GeralException{
        return ctrlLocalE.listar();
    }
    
    public List<LocalEstoque> pesquisarLocalEstoque(String descricao)
            throws GeralException{
        return ctrlLocalE.pesquisar(descricao);
    }
    
    public LocalEstoque trazerLocalEstoque(Integer cod)throws GeralException{
        ctrlLocalE.verificarSeExiste(cod);
        return ctrlLocalE.trazer(cod);
    }
    
    
    /*
     * ************************ E N T R A D A **************************
     */
    public void incluir(Entrada e) throws GeralException{
        ctrlE.validarDados(e);
        //ctrlE.verificarSePodeInserir(e);
        ctrlE.inserir(e);
    }
    
    public void alterar(Entrada e) throws GeralException{
        ctrlE.validarDados(e);
        ctrlE.verificarSeExiste(e);
        ctrlE.alterar(e);
    }
    
    public void excluir(Entrada e)throws GeralException{
        ctrlE.verificarSeExiste(e);
        ctrlE.excluir(e);
    }
    
    public List<Entrada> listarEntrada() throws GeralException{
        return ctrlE.listar();
    }
    
    public List<Entrada> listarEntradasDisponiveis() throws GeralException{
        return ctrlE.listarDisponiveis();
    }
    
    public Entrada trazerEntrada(Integer num) throws GeralException{
        ctrlE.verificarSeExiste(num);
        return ctrlE.trazer(num);
    }
    
    public List<Entrada> pesquisarEntrada(Entrada entrada, String dataInicial, 
            String dataFinal) throws GeralException{
        return ctrlE.pesquisar(entrada, dataInicial, dataFinal);
    }
    
    
    /*
     * *********************** S A Í D A *********************************
     */
    public void incluir(Saida s) throws GeralException{
        ctrlS.validarDados(s);
        //ctrlS.verificarSePodeInserir(s);
        ctrlS.inserir(s);
    }

    public void alterar(Saida s) throws GeralException{
        ctrlS.validarDados(s);
        ctrlS.verificarSeExiste(s);
        ctrlS.alterar(s);
    }
    
    public void excluir(Saida s)throws GeralException{
        ctrlS.verificarSeExiste(s);
        ctrlS.excluir(s);
    }

    public List<Saida> listarSaida() throws GeralException{
        return ctrlS.listar();
    }
    
    public Saida trazerSaida(Integer num) throws GeralException{
        ctrlS.verificarSeExiste(num);
        return ctrlS.trazer(num);
    }
    
    public List<Saida> pesquisarSaida(Saida s, String dataInicial, String dataFinal)
            throws GeralException{
        return ctrlS.listar();
    }
    
    /*
     * *********************** E S T A D O *********************************
     */
    public void incluir(Estado e) throws GeralException{
        ctrlEst.validarDados(e);
        ctrlEst.verificarSePodeInserir(e);
        ctrlEst.inserir(e);
    }
    
    public void alterar(Estado e) throws GeralException{
        ctrlEst.validarDados(e);
        ctrlEst.verificarSeExiste(e);
        ctrlEst.alterar(e);
    }
    
    public void excluir(Estado e)throws GeralException{
        ctrlEst.verificarSeExiste(e);
        ctrlEst.excluir(e);
    }
    
    public List<Estado> listarEstado() throws GeralException{
        return ctrlEst.listar();
    }
    
    public Estado trazerEstado(String uf)throws GeralException{
        ctrlEst.verificarSeExiste(uf);
        return ctrlEst.trazer(uf);
    }
    
    

    /**
     * @return the user
     */
    public Usuario getUser() {
        return user;
    }
    
    private void setUser(Usuario u){
        user= u;
        ctrlCateg.setUser(getUser());
        ctrlUnid.setUser(getUser());
        ctrlProd.setUser(getUser());
        ctrlForn.setUser(getUser());
        ctrlPer.setUser(getUser());
        ctrlFun.setUser(getUser());
        ctrlLocalE.setUser(getUser());
        ctrlE.setUser(getUser());
        ctrlS.setUser(getUser());
        ctrlEst.setUser(getUser());
    }
    
}