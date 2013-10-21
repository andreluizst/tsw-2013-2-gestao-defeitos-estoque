/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ce.erro;

/**
 *
 * @author Andre
 */
public class RepositorioListarException extends RepositorioException{
    /**
     * Construtor padrão
     */
    public RepositorioListarException(){
        super();
    }
    
    /**
     * 
     * @param e 
     */
    public RepositorioListarException(Exception e){
        super(e);
    }
    
    /**
     * 
     * @param userName
     * Usuário conectado ao sistema
     * @param e 
     */
    public RepositorioListarException(String userName, Exception e){
       super(userName, e) ;
    }
    
    /**
     * 
     * @param e
     * @param nameClassCall 
     * Nome da classe que está lançando a exceção
     */
    public RepositorioListarException(Exception e, String nameClassCall){
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
    public RepositorioListarException(String userName, Exception e, String nameClassCall){
        super(userName, e, nameClassCall);
    }
    
    /**
     * 
     * @param userName
     * Usuário conectado ao sistema
     * @param s 
     * Mensagem
     */
    public RepositorioListarException(String userName, String s){
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
    public RepositorioListarException(String userName, String s, String nameClassCall){
        super(userName, s, nameClassCall);
    }
    
    /**
     * 
     * @param userName
     * Usuário conectado ao sistema
     * @param t 
     */
    public RepositorioListarException(String userName, Throwable t){
        super(userName, t);
    }
}
