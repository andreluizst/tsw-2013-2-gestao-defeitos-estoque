/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ce.model.basica;

/**
 *
 * @author Andre
 */
public class Estado {
    private String uf;
    private String descricao;
    
    public Estado(){
        
    }
    public Estado(String uf, String descricao){
        this.uf=uf;
        this.descricao=descricao;
    }

    /**
     * @return the uf
     */
    public String getUf() {
        return uf;
    }

    /**
     * @param uf the uf to set
     */
    public void setUf(String uf) {
        this.uf = uf;
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
     * Texto com a sigla e o nome do estado
     */
    @Override
    public String toString(){
        return uf + " - " + descricao;
    }
    
}
