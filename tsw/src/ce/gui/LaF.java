/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ce.gui;

import ce.erro.GeralException;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author andreluiz
 */
public class LaF {
    public static void setNativeLookAndFeel() throws GeralException {
        try{
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch (ClassNotFoundException | InstantiationException | 
                IllegalAccessException | UnsupportedLookAndFeelException e){
            throw new GeralException(e.getMessage(),
                    LaF.class.getName()+".setNativeLookAndFeel()");
        }
    }
    
    public static void setCrossLookAndFeel() throws GeralException {
        try{
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        }
        catch (ClassNotFoundException | InstantiationException | 
                IllegalAccessException | UnsupportedLookAndFeelException e){
            throw new GeralException(e.getMessage(),
                    LaF.class.getName()+".setNativeLookAndFeel()");
        }
    }
    
    public static void setLookAndFeel(String estilo) throws GeralException{
        try{
            String nome= UIManager.getCrossPlatformLookAndFeelClassName();
            String s2="";
            for (javax.swing.UIManager.LookAndFeelInfo info: javax.swing.UIManager.getInstalledLookAndFeels()){
                //if (estilo.toLowerCase().equals(info.getClassName().toLowerCase())){
                
                if (estilo.equals(info.getClassName())){
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    JOptionPane.showMessageDialog(null, info.getClassName() + " encontrado!");
                    break;
                }
                s2+=info.getClassName()+"\n";
            }
            System.out.println(nome);
            System.out.println(s2);
            if (estilo.equals("cross")){
                JOptionPane.showMessageDialog(null, "cross encontrado!");
                UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
            }
            if (estilo.equals("gtk")){
                UIManager.setLookAndFeel("com.sun.java.swing.plaf.gtk.GTKLookAndFeel");
            }
        }
        catch(ClassNotFoundException | InstantiationException |
                IllegalAccessException | UnsupportedLookAndFeelException e){
            throw new GeralException(e.getMessage(),
                    LaF.class.getName()+".SetLookAndFeel()");
        }
    }
}
