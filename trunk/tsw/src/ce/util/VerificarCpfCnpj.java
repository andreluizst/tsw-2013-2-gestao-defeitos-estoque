/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ce.util;

import java.util.*;
import javax.swing.JOptionPane;
/**
 *
 * @author Andre
 * 
 * Como o nome da classe sugere, faz a verificação de CPF ou CNPJ
 */
public class VerificarCpfCnpj {
    /**
     * Executa o processo de verificação do CPF ou CNPJ
     * 
     * @param cpfOuCnpj
     * Texto numérico sem caracteres especiais para validação do CPF ou CNPJ
     * 
     * @return 
     * Caso o CPF ou CNPJ informado for válido será retornado true ou false caso contrário
     */
    public static boolean executar(String cpfOuCnpj){
        Integer k=0, dig=0;
        Integer soma=0;
        boolean isCNPJ=false;
        String sDig="", sNum="";
        
        try{
            sNum= cpfOuCnpj.substring(0, cpfOuCnpj.length()-2);
        }
        catch(StringIndexOutOfBoundsException e){
            return false;
        }
        
        switch(sNum.length()){
            case 9: isCNPJ=false;
                     break;
            case 12: isCNPJ=true;
                     break;
            default: return false;
        }
        for(int i=1;i<=2;i++){
            k=2;
            soma=0;
            for(int j=sNum.length();j>0;j--){
                try{
                    soma+= Integer.parseInt(sNum.substring(j-1, j))*k;
                }
                catch(StringIndexOutOfBoundsException e){
                    return false;
                }
                k++;
                if ((k > 9) && (isCNPJ)){
                    k= 2;
                }
            }
            dig= 11 - soma % 11;
            if (dig >= 10){
                dig= 0;
            }
            sNum+= dig.toString();
            sDig+= dig.toString();
        }
        //JOptionPane.showMessageDialog(null, "entrada = " + cpfOuCnpj + "|| saída = " + sNum);
        return sNum.compareTo(cpfOuCnpj)==0?true:false;
    }
}
