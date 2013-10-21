/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ce.erro;

/**
 *
 * @author Andre
 */
public class RepositorioAlterarException extends RepositorioException {
    public RepositorioAlterarException(){
        super();
    }
    
    /**
     * 
     * @param e 
     */
    public RepositorioAlterarException(Exception e){
        super(e);
    }
    
    /**
     * 
     * @param userName
     * Nome do usuário conectado
     * @param s
     * Mensagem
     * @param nameClassCall 
     * Nome da classe que está lançando a exceção
     */
    public RepositorioAlterarException(String userName, String s, String nameClassCall){
        super(userName, s, nameClassCall);
    }
    
    /**
     * 
     * @param e
     * @param nameClassCall 
     * Nome da classe que está lançando a exceção
     */
    public RepositorioAlterarException(Exception e, String nameClassCall){
        super(e, nameClassCall);
    }
    
    /**
     * 
     * @param userName
     * Nome do usuário conectado
     * @param e
     * @param nameClassCall 
     * Nome da classe que está lançando a exceção
     */
    public RepositorioAlterarException(String userName, Exception e, String nameClassCall){
        super(userName, e, nameClassCall);
    }
    
    /**
     * 
     * @param userName
     * Nome do usuário conectado
     * @param s 
     * Mensagem
     */
    public RepositorioAlterarException(String userName, String s){
        super(userName, s);
    }
    
    /**
     * 
     * @param userName
     * Nome do usuário conectado
     * @param t 
     */
    public RepositorioAlterarException(String userName, Throwable t){
        super(userName, t);
    }
}
