/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ce.model.basica;

/**
 *
 * @author aluno
 */
public class Usuario {

    private String nome;
    private int codUsuario;
    private String senha;
    private Perfil perfil;
    private Funcionario funcionario;

    public Usuario(){
        funcionario= new Funcionario();
        perfil=new Perfil();
    }

    public Usuario(int codUsuario, String nome, Perfil perfil,
            Funcionario funcionario, String senha){
        this.nome = nome;
        this.perfil=perfil;
        this.codUsuario = codUsuario;
        this.funcionario= funcionario;
        this.senha=senha;
    }
    
    public Usuario(String nome, Perfil perfil,
            Funcionario funcionario, String senha){
        this.nome=nome;
        this.perfil=perfil;
        this.codUsuario=0;
        this.funcionario=funcionario;
        this.senha=senha;
    }

    /**
     * @return the nome
     */
    public String getNome() {
        return nome;
    }

    /**
     * @param nome the nome to set
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * @return the codUsuario
     */
    public int getCodUsuario() {
        return codUsuario;
    }

    /**
     * @param codUsuario the codUsuario to set
     */
    public void setCodUsuario(int codUsuario) {
        this.codUsuario = codUsuario;
    }

    /**
     * @return the funcionario
     */
    public Funcionario getFuncionario() {
        return funcionario;
    }

    /**
     * @param funcionario the funcionario to set
     */
    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }

    /**
     * @return the perfil
     */
    public Perfil getPerfil() {
        return perfil;
    }

    /**
     * @param perfil the perfil to set
     */
    public void setPerfil(Perfil perfil) {
        this.perfil = perfil;
    }

    /**
     * @return the senha
     */
    public String getSenha() {
        return senha;
    }

    /**
     * @param senha the senha to set
     */
    public void setSenha(String senha) {
        this.senha = senha;
    }
    
    /**
     * 
     * @return 
     * Texto contendo o nome e o CPF.
     */
    @Override
    public String toString(){
        return nome + ", CPF " + funcionario.getCpf();
    }
    
    /**
     * 
     * @return 
     * Texto contendo o valor de todos os atribudos do objeto.
     */
    public String toStringAll(){
        return codUsuario + " - CPF " + funcionario.getCpf()+ " - " + nome +
                " - " + perfil.getNome() + " - " + senha;
    }
    
}
