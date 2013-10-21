/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ce.erro;

import ce.util.LogGenerator;

/**
 *
 * @author andreluiz
 */
public class GeralException extends Exception {
    private LogGenerator log= LogGenerator.getInstancia();
    /**
     * Esta propriedade contém a classe que lançou a exceção. Se a classe B
     * capturar a exceção da classe A e por sua vez relançar para uma outra
     * class, por exemplo C, que ao tratar esta exceção poderá verificar que
     * esta propriedade terá o seguinte caminho: A.B
     *
     */
    private String pathClassCall= "";
    
    /**
     * Construtor padrão
     */
    public GeralException(){
        super();
    }
    
    /**
     * 
     * @param e 
     */
    public GeralException(Exception e){
        super(e);
    }
    

    /**
     * Abilita o rastreamento da(s) classe(s) chamadora(s)
     * @param nameClassCall
     * Nome da classe que está lançando a exceção
     * */
    public GeralException(String userName, Exception e, String nameClassCall){
        super(e);
        if (pathClassCall.compareTo("") == 0){
            pathClassCall= nameClassCall;
        }else{
            pathClassCall= nameClassCall + "." + pathClassCall;
        }
        log.log(userName,  pathClassCall, getMessage());
    }
    
    /**
     * 
     * @param s 
     */
    public GeralException(String userName, String s){
        super(s);
        if (pathClassCall.compareTo("") == 0){
            log.log(userName,  GeralException.class.getName(), getMessage());
        }else{
            log.log(userName,  pathClassCall, getMessage());
        }
    }
    
    /**
     * Abilita o rastreamento da(s) classe(s) chamadora(s)
     * @param s
     * Mensagem
     * @param nameClassCall 
     * Nome da classe que está lançando a exceção
     */
    public GeralException(String userName, String s, String nameClassCall){
        super(s);
        if (pathClassCall.compareTo("") == 0){
            pathClassCall= nameClassCall;
        }else{
            pathClassCall= nameClassCall + "." + pathClassCall;
        }
        log.log(userName,  pathClassCall, getMessage());
    }
    
    /**
     * 
     * @param userName
     * @param t 
     */
    public GeralException(String userName, Throwable t){
        super(t);
        log.log(userName,  GeralException.class.getName(), getMessage());
    }

    /**
     * @return the pathClassCall
     */
    public String getPathClassCall() {
        return pathClassCall;
    }

    /**
     * @param pathClassCall the pathClassCall to set
     */
    public void setPathClassCall(String pathClassCall) {
        this.pathClassCall = pathClassCall;
    }
}
