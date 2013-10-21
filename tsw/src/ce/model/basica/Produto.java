package ce.model.basica;

import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author Andre
 */
public class Produto {
    private Integer codProd;
    private String descProd;
    private Categoria categoria;
    private Double qtdeMin;
    private Double qtdeIdeal;
    private Double qtdeEstoq;
    private int statusProd;
    private List<Fornecedor> fornecedores;
    private Unidade unidade;

    public Produto(){
        this.categoria=new Categoria();
        this.fornecedores= new ArrayList();
        this.unidade= new Unidade();
    }
    
    public Produto(Integer codProd, String descProd, Double qtdeEstoq, 
            Double qtdeMin, Double qtdeIdeal, int statusProd, 
            Categoria categoria, Unidade unidade){
            //List<Fornecedor> fornecedores){
        this();
        this.codProd= codProd;
        this.descProd= descProd;
        this.qtdeEstoq= qtdeEstoq;
        this.qtdeMin= qtdeMin;
        this.qtdeIdeal= qtdeIdeal;
        this.categoria= categoria;
        this.unidade=unidade;
        this.statusProd=statusProd;
    }
    
    public Produto(String descProd, Double qtdeEstoq, 
            Double qtdeMin, Double qtdeIdeal, int statusProd, 
            Categoria categoria, Unidade unidade){
        this();
        this.codProd= 0;
        this.descProd= descProd;
        this.qtdeEstoq= qtdeEstoq;
        this.qtdeMin= qtdeMin;
        this.qtdeIdeal= qtdeIdeal;
        this.categoria= categoria;
        this.unidade=unidade;
        this.statusProd=statusProd;
    }

    /**
     * @return the codProd
     */
    public Integer getCodProd() {
        return codProd;
    }

    /**
     * @param codProd the codProd to set
     */
    public void setCodProd(Integer codProd) {
        this.codProd = codProd;
    }

    /**
     * @return the descProd
     */
    public String getDescProd() {
        return descProd;
    }

    /**
     * @param descProd the descProd to set
     */
    public void setDescProd(String descProd) {
        this.descProd = descProd;
    }

    /**
     * @return the categoria
     */
    public Categoria getCategoria() {
        return categoria;
    }

    /**
     * @param categoria the categoria to set
     */
    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    /**
     * @return the qtdeMin
     */
    public Double getQtdeMin() {
        return qtdeMin;
    }

    /**
     * @param qtdeMin the qtdeMin to set
     */
    public void setQtdeMin(Double qtdeMin) {
        this.qtdeMin = qtdeMin;
    }

    /**
     * @return the qtdeIdeal
     */
    public Double getQtdeIdeal() {
        return qtdeIdeal;
    }

    /**
     * @param qtdeIdeal the qtdeIdeal to set
     */
    public void setQtdeIdeal(Double qtdeIdeal) {
        this.qtdeIdeal = qtdeIdeal;
    }

    /**
     * @return the qtdeEstoq
     */
    public Double getQtdeEstoq() {
        return qtdeEstoq;
    }

    /**
     * @param qtdeEstoq the qtdeEstoq to set
     */
    public void setQtdeEstoq(Double qtdeEstoq) {
        this.qtdeEstoq = qtdeEstoq;
    }

    /**
     * @return the fornecedores
     */
    public List<Fornecedor> getFornecedores() {
        return fornecedores;
    }

    /**
     * @param fornecedores the fornecedores to set
     */
    public void setFornecedores(List<Fornecedor> fornecedores) {
        this.fornecedores = fornecedores;
    }

    /**
     * @return the statusProd
     */
    public String getStatusProd() {
        String s="";
        if (statusProd ==1){
            s= "BLOQUEADO";
        }
        if (statusProd ==0){
            s= "Normal";
        } 
        return s;//statusProd;
    }
    
    /**
     * Retorna o código do status. Seu principal uso é nas operação de inclusão
     * e alteração no repositório.
     * @return 
     */
    public int getStatus() {
        return statusProd;
    }

    /**
     * @param statusProd the statusProd to set
     */
    public void setStatusProd(String statusProd) {
        if ((statusProd.toLowerCase().compareTo("normal")==0)
                || (statusProd.toLowerCase().compareTo("0")==0)){
            this.statusProd= 0;
        }
        if ((statusProd.toLowerCase().compareTo("bloqueado")==0)
                || (statusProd.toLowerCase().compareTo("1")==0)){
            this.statusProd= 1;
        }
        //this.statusProd = statusProd;
    }

    /**
     * @return the unidade
     */
    public Unidade getUnidade() {
        return unidade;
    }

    /**
     * @param unidade the unidade to set
     */
    public void setUnidade(Unidade unidade) {
        this.unidade = unidade;
    }
    
    /**
     * 
     * @return 
     * Texto contendo a descrição do produto.
     */
    @Override
    public String toString(){
        return descProd;
    }
    
    /**
     * 
     * @return 
     * Texto contendo os valores de todos os atribudos do objeto.
     */
    public String toStringAll(){
        /*String s= "Normal";
        if (statusProd != 0){
            s= "Bloqueado";
        }*/
        return categoria.getDescricao() + " - " + codProd + " - " + descProd
                + " - " + qtdeEstoq + " - " + qtdeMin + " - " + qtdeIdeal
                + " - " + unidade.getDescricao()+ " - " + getStatusProd();
    }
  
}
