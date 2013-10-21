package ce;

import ce.erro.GeralException;
import ce.gui.*;
import ce.model.basica.Usuario;
import ce.model.fachada.Fachada;
import ce.util.VerificarCpfCnpj;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author professor
 */
public class Main {
    private static Fachada f;
    private static MainMDIApplication mdiShell;
    public static Usuario user;//= new Usuario("root", null, null, "root");

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        f= Fachada.getInstancia();
        try {
            //LaF.setLookAndFeel("gtk");
            LaF.setNativeLookAndFeel(); // funciona ok
            //LaF.setCrossLookAndFeel(); //funciona ok
        } catch (GeralException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        LoginDialog loginDialog= new LoginDialog(null, true);
        loginDialog.setLocationRelativeTo(null);
        loginDialog.setVisible(true);
        if (loginDialog.getReturnStatus() == LoginDialog.RET_CANCEL){
            System.exit(0);
        }
        //<teste>
        user= f.getUser();
        /*System.out.println("Usuário: " + user.getNome()+"\n"
                + "Perfil: "+ user.getPerfil().getNome()+"\n"
                + "CPF: " + user.getFuncionario().getCpf());*/
        //</teste>
        mdiShell= new MainMDIApplication();
        mdiShell.setLocationRelativeTo(null);
        mdiShell.setVisible(true);
    }
    
    public static void atlzShellMenu(JInternalFrame sender){
        MainMDIApplication.setActiveWindow(sender);
        mdiShell.atlzMenu();
    }
    
    public static void registrarJanela(JInternalFrame janela){
        mdiShell.registrarJanela(janela, null);
    }
    
    public static void desregistrarJanela(JInternalFrame janela){
        mdiShell.desregistrarJanela(janela);
    }
    //Teste de verificação de CPF/CNPJ
    private static void verificarCpfOuCnpj(String s){
        if (VerificarCpfCnpj.executar(s)){
            System.out.println("CPF ou CNPJ OK!");
        }else{
            System.out.println("CPF ou CNPJ INVÁLIDO");
        }
    }

}
