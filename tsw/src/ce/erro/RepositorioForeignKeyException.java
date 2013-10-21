/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ce.erro;

/**
 *
 * @author andreluiz
 */
public class RepositorioForeignKeyException extends RepositorioException{
    
    /**
     * Construtor padrão
     */
    public RepositorioForeignKeyException(){
        super();
    }
    
    /**
     * 
     * @param e 
     */
    public RepositorioForeignKeyException(Exception e){
        super(e);
    }
    
    /**
     * 
     * @param userName
     * Usuário conectado ao sistema
     * @param e 
     */
    public RepositorioForeignKeyException(String userName, Exception e){
        super(userName, e);
    }
    
    /**
     * 
     * @param e
     * @param nameClassCall 
     * Nome da classe que está lançando a exceção
     */
    public RepositorioForeignKeyException(Exception e, String nameClassCall){
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
    public RepositorioForeignKeyException(String userName, Exception e, String nameClassCall){
        super(userName, e, nameClassCall);
    }
    
    /**
     * 
     * @param userName
     * Usuário conectado ao sistema
     * @param s 
     * Mensagem
     */
    public RepositorioForeignKeyException(String userName, String s){
        super(userName, s);
    }
    
    /**
     * Abilita o rastreamento da(s) classe(s) chamadora(s)
     * @param s
     * Mensagem
     * @param nameClassCall 
     * Nome da classe que está lançando a exceção
     */
    public RepositorioForeignKeyException(String userName, String s, String nameClassCall){
        super(userName, s, nameClassCall);
    }
    
    /**
     * 
     * @param userName
     * @param t 
     */
    public RepositorioForeignKeyException(String userName, Throwable t){
        super(userName, t);
    }
    
}
