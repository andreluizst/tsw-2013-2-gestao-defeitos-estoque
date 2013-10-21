/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ce.model.basica;

/**
 *
 * @author aluno
 */
public class Perfil {

    private String nome;
    private int codPerfil;

    public Perfil(){

    }

    public Perfil(int codPerfil, String nome){
        this.nome = nome;
        this.codPerfil = codPerfil;
    }
    
    public Perfil(String nome){
        this.nome= nome;
        this.codPerfil= 0;
    }
    
    public String getNome(){
        return this.nome;
    }
    public void setNome(String nome){
        this.nome = nome;
    }
    public int getCodPerfil(){
        return this.codPerfil;
    }
    public void setCodPerfil(int codPerfil){
        this.codPerfil = codPerfil;
    }
    
    /**
     * 
     * @return 
     * Texto contendo o nome do perfil.
     */
    @Override
    public String toString(){
        return nome;
    }
    
    /**
     * 
     * @return 
     * Texto contendo os valores de todos os atribudos do objeto.
     */
    public String toStringAll(){
        return codPerfil + " - " + nome;
    }
}
