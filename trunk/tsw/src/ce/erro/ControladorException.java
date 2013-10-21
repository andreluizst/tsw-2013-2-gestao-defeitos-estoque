/*
 * Classe de exceção para os controladores
 * 
 */
package ce.erro;

/**
 *
 * @author Andre
 */
public class ControladorException extends GeralException {
    
    /**
     * Construtor padrão
     */
    public ControladorException(){
        super();
    }
    
    /**
     * 
     * @param e 
     */
    public ControladorException(String userName, Exception e){
        super(userName, e);
    }
    
    /**
     * Abilita o rastreamento da(s) classe(s) chamadora(s)
     * @param nameClassCall
     * Nome da classe que está lançando a exceção
     * */
    public ControladorException(String userName, Exception e, String nameClassCall){
        super(userName, e, nameClassCall);
    }
    
    /**
     * 
     * @param s 
     */
    public ControladorException(String userName, String s){
        super(userName, s);
    }
    
    /**
     * Abilita o rastreamento da(s) classe(s) chamadora(s)
     * @param s
     * Mensagem
     * @param nameClassCall 
     * Nome da classe que está lançando a exceção
     */
    public ControladorException(String userName, String s, String nameClassCall){
        super(userName, s, nameClassCall);
    }
    
    /**
     * 
     * @param userName
     * @param t 
     */
    public ControladorException(String userName, Throwable t){
        super(userName, t);
    }
    
}
