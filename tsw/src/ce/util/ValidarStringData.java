/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ce.util;

import java.text.ParseException;
import java.util.Date;
import java.text.SimpleDateFormat;
/**
 *
 * @author Andre
 */
public class ValidarStringData {
    public static boolean execute(String data){
        Date dt= null;
        SimpleDateFormat formatador= new SimpleDateFormat("dd/MM/yyyy");
        try{
            formatador.setLenient(false);
            dt= formatador.parse(data);
            return true;
        }
        catch(ParseException pe){
            return false;
        }
    }
}
