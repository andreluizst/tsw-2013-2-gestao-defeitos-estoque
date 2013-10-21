/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ce.gui;

import javax.swing.JOptionPane;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

/**
 *
 * @author Andre
 */
public class TeclasPermitidas extends PlainDocument{
    private String teclas;
    private String notRepeat;
    public TeclasPermitidas(String teclas){
        this.teclas=teclas;
    }
    public TeclasPermitidas(String teclas, String notRepeat){
        this.teclas=teclas;
        this.notRepeat=notRepeat;
    }
    @Override
    public void insertString(int offset, String str, AttributeSet attr) 
            throws BadLocationException{
        String strFinal=str;
        if (notRepeat != null){
            String txt= super.getText(0, super.getLength());
            if ((txt.contains(notRepeat)) && (str.contains(notRepeat))){
                strFinal= str.replaceAll("["+notRepeat+"]", "");
            }
        }
        super.insertString(offset, strFinal.replaceAll(teclas, ""), attr);
    }
    
    public void replace(int offset, String str, AttributeSet attr) 
            throws BadLocationException{
        //super.insertString(offset, str.replaceAll(teclas, ""), attr);
        String strFinal=str;
        if (notRepeat != null){
            String txt= super.getText(0, super.getLength());
            JOptionPane.showMessageDialog(null, txt);
            if ((txt.contains(notRepeat)) && (str.contains(notRepeat))){
                strFinal= str.replaceAll("["+notRepeat+"]", "");
            }
        }
        super.insertString(offset, strFinal.replaceAll(teclas, ""), attr);
    }
}
