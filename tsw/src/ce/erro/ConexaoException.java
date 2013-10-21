package ce.erro;

/**
 *
 * @author professor
 */
public class ConexaoException extends GeralException{
    public ConexaoException(){
        super();
    }
    
    /**
     * 
     * @param e 
     */
    public ConexaoException(Exception e){
        super(e);
    }
    
    /**
     * Abilita o rastreamento da(s) classe(s) chamadora(s)
     * @param nameClassCall
     * Nome da classe que está lançando a exceção
     * */
    public ConexaoException(String userName, Exception e, String nameClassCall){
        super(userName, e, nameClassCall);
    }    

    public ConexaoException(String userName, String s){
        super(userName, s);
    }
    
   /**
     * Abilita o rastreamento da(s) classe(s) chamadora(s)
     * @param s
     * Mensagem
     * @param nameClassCall 
     * Nome da classe que está lançando a exceção
     */
    public ConexaoException(String userName, String s, String nameClassCall){
        super(userName, s, nameClassCall);
    }    
    
    /**
     * 
     * @param userName
     * @param t 
     */
    public ConexaoException(String userName, Throwable t){
        super(userName, t);
    }
}
