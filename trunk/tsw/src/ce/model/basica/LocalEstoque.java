/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ce.model.basica;

/**
 *
 * @author Andre
 */
public class LocalEstoque {
    private int codLocal;
    private String descricao;
    
    public LocalEstoque(){
        
    }
    
    public LocalEstoque(int codLocal, String descricao){
        this.codLocal=codLocal;
        this.descricao=descricao;
    }
    
    public LocalEstoque(String descricao){
        this.codLocal=0;
        this.descricao=descricao;
    }

    /**
     * @return the codLocal
     */
    public int getCodLocal() {
        return codLocal;
    }

    /**
     * @param codLocal the codLocal to set
     */
    public void setCodLocal(int codLocal) {
        this.codLocal = codLocal;
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
     * Texto contendo a descrição do local de estoque.
     */
    @Override
    public String toString(){
        return descricao;
    }
    
    /**
     * 
     * @return 
     * Texto contendo os valores de todos os atribudos do objeto.
     */
    public String toStringAll(){
        return codLocal + " - " + descricao;
    }
    
}
