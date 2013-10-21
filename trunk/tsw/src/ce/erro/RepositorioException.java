package ce.erro;

import ce.util.LogGenerator;
/**
 *
 * @author professor
 * @author André Luiz S. Teotônio
 */
public class RepositorioException extends Exception{
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
    public RepositorioException(){
        super();
    }
    
    /**
     * 
     * @param e 
     */
    public RepositorioException(Exception e){
        super(e);
    }
    
    /**
     * Abilita o rastreamento da(s) classe(s) chamadora(s)
     * @param nameClassCall
     * Nome da classe que está lançando a exceção
     * */
    public RepositorioException(Exception e, String nameClassCall){
        super(e);
        if (pathClassCall.compareTo("") == 0){
            pathClassCall= nameClassCall;
        }else{
            pathClassCall= nameClassCall + "." + pathClassCall;
        }
        log.log(pathClassCall, getMessage());
    }
    
    /**
     * 
     * @param userName
     * @param e
     * @param nameClassCall 
     */
    public RepositorioException(String userName, Exception e, String nameClassCall){
        super(e);
        if (pathClassCall.compareTo("") == 0){
            pathClassCall= nameClassCall;
        }else{
            pathClassCall= nameClassCall + "." + pathClassCall;
        }
        log.log(userName,  pathClassCall, getMessage());
    }
    
    /*Estava dando conflito devido de assinatura
    public RepositorioException(String userName, String s){
        super(s);
        if (pathClassCall.compareTo("") == 0){
            log.log(userName,  RepositorioException.class.getName(), getMessage());
        }else{
            log.log(userName,  pathClassCall, getMessage());
        }
    }*/
    
    /**
     * 
     * @param s 
     */
    public RepositorioException(String s){
        super(s);
        /*if (pathClassCall.compareTo("") == 0){
            log.log(RepositorioException.class.getName(), getMessage());
        }else{
            log.log(pathClassCall, getMessage());
        }*/
    }
    
    public RepositorioException(String s, String nameClassCall){
        super(s);
        if (pathClassCall.compareTo("") == 0){
            pathClassCall= nameClassCall;
        }else{
            pathClassCall= nameClassCall + "." + pathClassCall;
        }
        log.log(pathClassCall, getMessage());
    }
    
    /**
     * Abilita o rastreamento da(s) classe(s) chamadora(s)
     * @param s
     * Mensagem
     * @param nameClassCall 
     * Nome da classe que está lançando a exceção
     */
    public RepositorioException(String userName, String s, String nameClassCall){
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
    public RepositorioException(String userName, Throwable t){
        super(t);
        log.log(userName,  RepositorioException.class.getName(), getMessage());
    }
    
    public void addClassCallToPath(String name){
        if (pathClassCall.compareTo("") == 0){
            pathClassCall= name;
        }else{
            pathClassCall= name + "." + pathClassCall;
        }
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
