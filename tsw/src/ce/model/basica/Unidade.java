/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ce.model.basica;

/**
 *
 * @author Andre
 */
public class Unidade {
    private int codUnid;
    private String descricao;
    
    public Unidade(){
        
    }
    
    public Unidade(int codUnid, String descricao){
        this.codUnid=codUnid;
        this.descricao=descricao;
    }

    public Unidade(String descricao){
        this.codUnid=0;
        this.descricao=descricao;
    }
    /**
     * @return the codUnid
     */
    public int getCodUnid() {
        return codUnid;
    }

    /**
     * @param codUnid the codUnid to set
     */
    public void setCodUnid(int codUnid) {
        this.codUnid = codUnid;
    }

    /**
     * @return the descricao
     */
    public String getDescricao() {
        return descricao;
    }

    /**
     * @param descricao the descricao to set
     */
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    
    /**
     * 
     * @return 
     * Texto contendo a descrição da unidade.
     */
    @Override
    public String toString(){
        return descricao;
    }
    
    /**
     * 
     * @return 
     * Texto contendo o valor de todos os atribudos do objeto.
     */
    public String toStringAll(){
        return codUnid + " - " + descricao;
    }
    
}
