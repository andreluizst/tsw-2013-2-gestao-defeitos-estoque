/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ce.erro;

/**
 *
 * @author Andre
 */
public class RepositorioInserirException extends RepositorioException{
    public RepositorioInserirException(){
        super();
    }
    
    /**
     * 
     * @param e 
     */
    public RepositorioInserirException(Exception e){
        super(e);
    }
    
    /**
     * 
     * @param e
     * @param nameClassCall 
     * Nome da classe que está lançando a exceção
     */
    public RepositorioInserirException(Exception e, String nameClassCall){
        super(e, nameClassCall);
    }
    
    /**
     * 
     * @param userName
     * @param e
     * @param nameClassCall 
     * Nome da classe que está lançando a exceção
     */
    public RepositorioInserirException(String userName, Exception e, String nameClassCall){
        super(userName, e, nameClassCall);
    }

    /*public RepositorioInserirException(String userName, String s){
        super(userName, s);
    }*/
    
    /**
     * Abilita o rastreamento da(s) classe(s) chamadora(s)
     * @param s
     * Mensagem
     * @param nameClassCall 
     * Nome da classe que está lançando a exceção
     */
    public RepositorioInserirException(String userName, String s, String nameClassCall){
        super(userName, s, nameClassCall);
    }
    
    /**
     * 
     * @param s
     * Mensagem
     * @param nameClassCall 
     * Nome da classe que está lançando a exceção
     */
    public RepositorioInserirException(String s, String nameClassCall){
        super(s, nameClassCall);
    }
    
    /**
     * 
     * @param userName
     * @param t 
     */
    public RepositorioInserirException(String userName, Throwable t){
        super(userName, t);
    }
}
