/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ce.erro;

/**
 *
 * @author Andre
 */
public class RepositorioExcluirException extends RepositorioException{
    /**
     * Contrutor padrão
     */
    public RepositorioExcluirException(){
        super();
    }
    
    /**
     * 
     * @param e 
     */
    public RepositorioExcluirException(Exception e){
        super(e);
    }

    /**
     * 
     * @param userName
     * Usuário conectado ao sistema
     * @param e 
     */
    public RepositorioExcluirException(String userName, Exception e){
        super(userName, e);
    }
    
    /**
     * 
     * @param e
     * @param nameClassCall 
     * Nome da classe que está lançando a exceção
     */
    public RepositorioExcluirException(Exception e, String nameClassCall){
        super(e, nameClassCall);
    }
    
    /**
     * 
     * @param userName
     * Usuário conectado ao sistema
     * @param e
     * @param nameClassCall 
     * Nome da classe que está lançando a exceção
     */
    public RepositorioExcluirException(String userName, Exception e, String nameClassCall){
        super(userName, e, nameClassCall);
    }
    
    /**
     * 
     * @param userName
     * Usuário conectado ao sistema
     * @param s 
     * Mensagem
     */
    public RepositorioExcluirException(String userName, String s){
        super(userName, s);
    }
    
    /**
     * Abilita o rastreamento da(s) classe(s) chamadora(s)
     * @param userName
     * Usuário conectado ao sistema
     * @param s
     * Mensagem
     * @param nameClassCall 
     * Nome da classe que está lançando a exceção
     */
    public RepositorioExcluirException(String userName, String s, String nameClassCall){
        super(userName, s, nameClassCall);
    }
    
    /**
     * 
     * @param userName
     * Usuário conectado ao sistema
     * @param t 
     */
    public RepositorioExcluirException(String userName, Throwable t){
        super(userName, t);
    }
}
