/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ce.gui;

import ce.erro.GeralException;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ResourceBundle;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/**
 *
 * @author Andre
 */
public class Resource {
    private static Resource instancia;
    //private Fachada f;
    private ResourceBundle rb;
    /**
     * Construtor padrão
     */
    private Resource(){
        //f= Fachada.getInstancia();
        rb= ResourceBundle.getBundle("ce.erro.Erro");
    }
    
    /**
     * O método implemento o padrão sigleton
     * @return 
     * O objeto resource responsável pela manipulação dos recursos de imagens
     * da aplicação
     */
    public static Resource getInstancia(){
        if (instancia == null){
            instancia= new Resource();
        }
        return instancia;
    }
    
    /**
     * Carrega um arquivo de imagem na memória
     * @param fileName
     * Nome do arquivo de imagem que deve ser no pacote de imagem do sistema
     * @return
     * BufferedImage
     * @throws GeralException 
     * Se houver alguma problema de entrada/saída ou se for passado um valor
     * de parâmetro inválido será lançada uma excepão
     */
    public BufferedImage getToBuffer(String fileName)throws GeralException{
        try {
            BufferedImage imagem= ImageIO.read(getClass().getResource(fileName));
            return imagem;
        } 
        catch (IOException | IllegalArgumentException ex) {
            throw new GeralException("Resource", 
                    rb.getString("ResourceError")+ ": "+ fileName,
                    Resource.class.getName()+".get()");
        }
    }
    
    /**
     * Ajusta o tamanho da imagem
     * @param image
     * ImageIcon contendo a imagem a ser ajustada
     * @param width
     * Largura desejada
     * @param height
     * Altura desejada
     * @return 
     * ImageIcon com a imagem reajustada
     */
    public ImageIcon stretchImage(ImageIcon image, Integer width, Integer height){
        return new ImageIcon(image.getImage().getScaledInstance(
                width, height, Image.SCALE_DEFAULT));
    }
    
    /**
     * Retorna uma ImageIcon com a imagem do arquivo especificado no parâmetro fileName
     * @param fileName
     * @return
     * @throws GeralException 
     * Se houver alguma problema de entrada/saída ou se for passado um valor
     * de parâmetro inválido será lançada uma excepão
     */
    public ImageIcon get(String fileName)throws GeralException{
        try {
            BufferedImage imagem= ImageIO.read(getClass().getResource(fileName));
            return new ImageIcon(imagem);
        } 
        catch (IOException | IllegalArgumentException ex) {
            throw new GeralException("Resource", 
                    rb.getString("ResourceError")+ ": "+ fileName,
                    Resource.class.getName()+".get()");
        }
    }
    
    /**
     * Retorna a imagem de um arquivo e a redimensiona conforme especificado nos
     * parâmetros width e height
     * @param fileName
     * @param width
     * @param height
     * @return
     * @throws GeralException 
     * Se houver alguma problema de entrada/saída ou se for passado um valor
     * de parâmetro inválido será lançada uma excepão
     */
    public ImageIcon get(String fileName, Integer width, Integer height) 
            throws GeralException{
        try {
            BufferedImage imagem= ImageIO.read(getClass().getResource(fileName));
            return new ImageIcon(imagem.getScaledInstance(width, height, Image.SCALE_DEFAULT));
        } 
        catch (IOException | IllegalArgumentException ex) {
            throw new GeralException("Resource", 
                    rb.getString("ResourceError")+ ": "+ fileName,
                    Resource.class.getName()+".get()");
            //Logger.getLogger(Resource.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
