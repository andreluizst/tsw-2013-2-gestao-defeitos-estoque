/*
 * Classe responsável pela criação e atualização do arquivo de log.
 * 
 */
package ce.util;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
/**
 *
 * @author andreluiz
 */
public class LogGenerator {
    private static LogGenerator instancia=null;
    private static String fileName="erros.log";
    private ArrayList erros;
    
    /**
     * Construtor padrão
     */
    private LogGenerator(){
        
        erros= new ArrayList();
        String slinha="";
        File file= new File(fileName);
        if (file.exists()){
            try{
                FileReader fileReader= new FileReader(fileName);
                BufferedReader buffer= new BufferedReader(fileReader);
                if (erros.size() > 0){
                    erros.clear();
                }
                while ((slinha = buffer.readLine()) != null){
                    erros.add(slinha);
                }
                buffer.close();
                fileReader.close();
            }
            catch(SecurityException | IOException e){
            }
        }
    }
    
    /**
     * Retorna uma instância do gerador de log
     * @return LogGenerator
     */
    public static LogGenerator getInstancia(){
        if (instancia == null){
            instancia= new LogGenerator();
        }
        return instancia;
    }
    /**
     * Configura um novo nome do arquivo de log
     * @param fileName 
     */
    public static void setFileName(String fileName){
        LogGenerator.fileName= fileName;
    }
    /**
     * Carrega o arquivo de log
     */
    public void loadFile(){
        String slinha="";
        File file= new File(fileName);
        if (file.exists()){
            try{
                FileReader fileReader= new FileReader(fileName);
                BufferedReader buffer= new BufferedReader(fileReader);
                if (erros.size() > 0){
                    erros.clear();
                }
                while ((slinha = buffer.readLine()) != null){
                    erros.add(slinha);
                }
                buffer.close();
                fileReader.close();
            }
            catch(SecurityException | IOException e){
            }
        }
    }
    
    /**
     * Escreve no arquivo de log
     * @param userName
     * @param classPath
     * @param msg 
     */
    public void log(String userName, String classPath, String msg){
        erros.add(userName + " -> " 
            + new SimpleDateFormat("dd/MM/yyyy - HH:mm:ss").format(new Date()));
        erros.add("\t" + classPath + ": " + msg);
        saveLog();
    }
    
    /**
     * Escreve no arquivo de log
     * @param classPath
     * @param msg 
     */
    public void log(String classPath, String msg){
        erros.add("Sistema -> " 
            + new SimpleDateFormat("dd/MM/yyyy - HH:mm:ss").format(new Date()));
        erros.add("\t" + classPath + ": " + msg);
        saveLog();
    }
    
    /**
     * Escreve no arquivo de log
     * @param e
     * Exceção que deverá ser registrada
     */
    public void log(Exception e){
        StackTraceElement[] stackTraceElements= e.getStackTrace();
        erros.add("Sistema -> " 
            + new SimpleDateFormat("dd/MM/yyyy - HH:mm:ss").format(new Date()));
        /*StackTraceElement[] elements= e.getStackTrace();
        erros.add("\t"+elements[0].getClassName()+"."+elements[0].getMethodName()+"()"
                    + " {linha "+elements[0].getLineNumber()+"}: " + e.getMessage());*/
        erros.add("\tErro: "+e.getMessage());
        //erros.add("\tCaminho");
        for (StackTraceElement element:stackTraceElements){
            erros.add("\t\t"+element.getClassName()+"."+element.getMethodName()+"()"
                    + " {linha "+element.getLineNumber()+"}");
        }
        saveLog();
    }
    
    /**
     * Método de uso interno da classe para gravar o log no arquivo.
     */
    private void saveLog(){
        BufferedWriter buffer;
        try {
            FileWriter fileWriter= new FileWriter(fileName);
            buffer = new BufferedWriter(fileWriter);
            for (int i=0;i<erros.size();i++){
                buffer.write(erros.get(i).toString());
                buffer.newLine();
            }
            buffer.close();
            fileWriter.close();
        } catch (IOException ex) {
            //Logger.getLogger(LogGenerator.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
