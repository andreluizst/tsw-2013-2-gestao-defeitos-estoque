package ce.model.basica;

/**
 *
 * @author professor
 */
public class Categoria {
    private Integer codCateg;
    private String descricao;

    public Categoria(){
        //
    }

    public Categoria(Integer codCateg, String descricao){
        this.codCateg=codCateg;
        this.descricao=descricao;
    }

    public Categoria(String descricao) {
        this.descricao=descricao;
    }

    /**
     * @return the codCateg
     */
    public Integer getCodCateg() {
        return codCateg;
    }

    /**
     * @param codCateg the codCateg to set
     */
    public void setCodCateg(Integer codCateg) {
        this.codCateg = codCateg;
    }

    /**
     * @return the nome
     */
    public String getDescricao() {
        return descricao;
    }

    /**
     * @param nome the nome to set
     */
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    
    /**
     * 
     * @return 
     * Texto contendo a descrição da categoria.
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
        return codCateg + " - " + descricao;
    }
}
