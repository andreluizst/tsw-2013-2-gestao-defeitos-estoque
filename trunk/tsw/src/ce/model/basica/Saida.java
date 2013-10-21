/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ce.model.basica;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 *
 * @author aluno
 */
public class Saida {

    private Integer codSaida;
    private Double qtde;
    private String dataSaida;
    private Entrada entrada;

    public Saida(){
        entrada= new Entrada();
    }
    
    /**
     * 
     * @param codSaida
     * @param qtde
     * @param dataSaida
     * Data no formato de String ou java.sql.Date.
     * @param entrada 
     * Objeto do tipo Entrada.
     */
    public Saida(Integer codSaida, Double qtde, Object dataSaida, Entrada entrada){
        this.codSaida = codSaida;
        this.qtde = qtde;
        //this.dataSaida = dataSaida;
        java.util.Date dt;
        if (dataSaida instanceof String){
            try{
                dt= new SimpleDateFormat("dd/MM/yyy").parse((String)dataSaida);
                this.dataSaida= new SimpleDateFormat("dd/MM/yyyy").format(dt);
            }catch(Exception e){
                this.dataSaida= "";
            }
        }
        if (dataSaida instanceof java.sql.Date){
            dt= new java.util.Date(((java.sql.Date)dataSaida).getTime());
            this.dataSaida= new SimpleDateFormat("dd/MM/yyyy").format(dt);
        }
        this.entrada=entrada;
    }
    
    /**
     * 
     * @param codSaida
     * @param qtde
     * @param dataSaida 
     */
    public Saida(Integer codSaida, Double qtde, String dataSaida){
        this(codSaida, qtde, dataSaida, new Entrada());
    }
    
    /**
     * 
     * @param qtde
     * @param dataSaida
     * Data no formato de String ou java.sql.Date.
     * @param entrada 
     * Objeto do tipo Entrada.
     */
    public Saida(Double qtde, Object dataSaida, Entrada entrada){
        this(0, qtde, dataSaida, entrada);
    }
    
    /**
     * 
     * @param codSaida
     * @param dataSaida
     * @param qtde
     * @param entrada 
     */
    public Saida(Integer codSaida, String dataSaida, Double qtde, Entrada entrada){
        this(codSaida, qtde, dataSaida, entrada);
    }
    
    /**
     * 
     * @param codSaida
     * @param dataSaida
     * @param qtde
     * @param entrada 
     */
    public Saida(Integer codSaida, java.sql.Date dataSaida, Double qtde, Entrada entrada){
        this(codSaida, qtde, dataSaida, entrada);
    }
    
    /**
     * 
     * @param qtde
     * @param dataSaida 
     */
    public Saida(Double qtde, String dataSaida){
        this(0, qtde, dataSaida, new Entrada());
    }

    /**
     * @return the codSaida
     */
    public Integer getCodSaida() {
        return codSaida;
    }

    /**
     * @param codSaida the codSaida to set
     */
    public void setCodSaida(Integer codSaida) {
        this.codSaida = codSaida;
    }

    /**
     * @return the qtde
     */
    public Double getQtde() {
        return qtde;
    }

    /**
     * @param qtde the qtde to set
     */
    public void setQtde(Double qtde) {
        this.qtde = qtde;
    }
    
    /**
     * Retorn a data invertida (yyyy/MM/dd) no formado texto para manter a
     * compatibilidade com o MySQL
     * @return 
     */
    public String getStrDataInvertida(){
        String dt="";
        dt= getDataToMySqlDate().toString();
        return dt.replaceAll("[-]", "/");
    }
    
    /**
     * Retorn a data invertida (yyyy-MM-dd) no formato suportado pelo MySQL.
     * @return 
     * java.sql.Date
     */
    public java.sql.Date getDataToMySqlDate(){
        java.sql.Date data;
        try {
            data= new java.sql.Date(new SimpleDateFormat("dd/MM/yyyy").parse(dataSaida).getTime());
        } catch (ParseException ex) {
            data= null;
        }
        return data;
    }

    /**
     * @return the dataSaida
     */
    public String getDataSaida() {
        return dataSaida;
    }

    /**
     * @param dataSaida the dataSaida to set
     */
    public void setDataSaida(String dataSaida) {
        this.dataSaida = dataSaida;
    }

    /**
     * @return the entrada
     */
    public Entrada getEntrada() {
        return entrada;
    }

    /**
     * @param entrada the entrada to set
     */
    public void setEntrada(Entrada entrada) {
        this.entrada = entrada;
    }
    
    /**
     * 
     * @return 
     * Texto contendo o código e a data de saída
     */
    @Override
    public String toString(){
        return codSaida + " - " + dataSaida;
    }
    
    /**
     * 
     * @return 
     * Texto do conteúdo de todos os atribudos do objeto
     */
    public String toStringAll(){
        return codSaida + " - " + dataSaida + " - " + entrada.getNumero()
                + " - " + entrada.getProduto().getDescProd() 
                + " - " + qtde;
    }
    
}
