/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ce.gui;

import ce.erro.GeralException;
import ce.model.basica.Estado;
import ce.model.basica.Funcionario;
import ce.model.fachada.Fachada;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.DefaultComboBoxModel;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

/**
 *
 * @author Andre Luiz
 */
public class PropFuncionario extends javax.swing.JDialog {
    private Fachada f;
    private Funcionario func;
    private Resource res;
    private boolean isIns;

    /**
     * A return status code - returned if Cancel button has been pressed
     */
    public static final int RET_CANCEL = 0;
    /**
     * A return status code - returned if OK button has been pressed
     */
    public static final int RET_OK = 1;

    /**
     * Construtor - Cria o diálogo de propriedades do funcionário para inclusão ou alteração
     * @param parent
     * @param modal
     * @param funcionario 
     * Objeto do tipo Fornecedor. Se null o diálogo iniciará em modo de inclusão,
     * caso contrário, iniciará em modo de alteração
     */
    public PropFuncionario(java.awt.Frame parent, boolean modal, Funcionario funcionario) {
        super(parent, modal);
        initComponents();

        // Close the dialog when Esc is pressed
        String cancelName = "cancel";
        InputMap inputMap = getRootPane().getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), cancelName);
        ActionMap actionMap = getRootPane().getActionMap();
        actionMap.put(cancelName, new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                doClose(RET_CANCEL);
            }
        });
        f= Fachada.getInstancia();
        res= Resource.getInstancia();
        
        try {
            for (Estado es : f.listarEstado()){
                jcbxEstados.addItem(es);
            }
        } catch (GeralException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        
        if (funcionario == null ){
            this.setTitle("INCLUIR funcionário");
            isIns= true;
            try {
                lblImg.setIcon(res.get("\\images\\Arquivo-Novo.jpg", lblImg.getWidth(), lblImg.getHeight()));
            } catch (GeralException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage());
            }
        }else{
            this.setTitle("ALTERAR funcionário");
            isIns= false;
            setFields(funcionario);
            try {
                lblImg.setIcon(res.get("\\images\\Arquivo-Alterar3.jpg", lblImg.getWidth(), lblImg.getHeight()));
            } catch (GeralException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage());
            }
        }
    }
    
    /**
     * Atualiza a lista de estados
     */
    private void atlzEstados(){
        List<Estado> lista= new ArrayList();
        try{
            lista= f.listarEstado();
            DefaultComboBoxModel<Estado> dcbxm= new DefaultComboBoxModel();
            for (Estado est:lista){
                dcbxm.addElement(est);
            }
            jcbxEstados.setModel(dcbxm);
        }catch(GeralException ex){
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }
    
    /**
     * Preenche os campos do formulário com os dados do objeto Funcionario
     * @param fun 
     * Objeto do tipo Funcionario cujos dados serão exibidos nos campos texto da tela.
     */
    private void setFields(Funcionario fun){
        jftfCpf.setText(fun.getCpf());
        jtxtNome.setText(fun.getNome());
        jftfDataNasc.setText(fun.getDtNasc());
        jtxtLogradouro.setText(fun.getLogradouro());
        jtxtNum.setText(((Integer)fun.getNum()).toString());
        jtxtComp.setText(fun.getComp());
        jtxtBairro.setText(fun.getBairro());
        jtxtMunicipio.setText(fun.getMunicipio());
        if (jcbxEstados.getItemCount() > 0){
            for (int i=0;i<jcbxEstados.getItemCount();i++){
                if (jcbxEstados.getItemAt(i).getUf().compareTo(fun.getEstado().getUf()) == 0){
                    jcbxEstados.setSelectedIndex(i);
                    break;
                }
            }
        }
        jftfCep.setText(fun.getCep());
        jftfFone.setText(fun.getFone());
        jtxtEmail.setText(fun.getEmail());
    }
    
    /**
     * Preenche o objeto do tipo Funcionario com os dados constantes nos campos
     * do formulário
     * @param fun 
     * Objeto do tipo Funcionario que receberá os dados dos campos da tela.
     */
    private void setFuncionario(Funcionario fun){
        fun.setCpf(jftfCpf.getText());
        fun.setNome(jtxtNome.getText());
        fun.setDtNasc(jftfDataNasc.getText());
        fun.setLogradouro(jtxtLogradouro.getText());
        try{
            fun.setNum(Integer.parseInt(jtxtNum.getText()));
        }catch (Exception e){
            fun.setNum(0);
        }
        fun.setBairro(jtxtBairro.getText());
        fun.setMunicipio(jtxtMunicipio.getText());
        fun.setEstado((Estado)jcbxEstados.getSelectedItem());
        fun.setCep(jftfCep.getText());
        fun.setFone(jftfFone.getText());
        fun.setEmail(jtxtEmail.getText());
    }

    /**
     * @return the return status of this dialog - one of RET_OK or RET_CANCEL
     */
    public int getReturnStatus() {
        return returnStatus;
    }
    
    /**
     * 
     * @return 
     * Retorna um objeto do tipo Funcionario com as propriedades salvas.
     */
    public Funcionario getProperties(){
        return func;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lstUF = new LinkedList<Estado>();
        btnSalvar = new javax.swing.JButton();
        cancelButton = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jtxtNome = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jtxtLogradouro = new javax.swing.JTextField();
        javax.swing.text.MaskFormatter maskCpf= null;
        try{
            maskCpf = new javax.swing.text.MaskFormatter("###########");
            maskCpf.setPlaceholderCharacter('_');
        }
        catch(java.text.ParseException e){

        }
        jftfCpf = new javax.swing.JFormattedTextField(maskCpf);
        jLabel4 = new javax.swing.JLabel();
        jtxtNum = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jtxtComp = new javax.swing.JTextField();
        lblImg = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jtxtBairro = new javax.swing.JTextField();
        jtxtMunicipio = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        javax.swing.text.MaskFormatter maskCep= null;
        try{
            maskCep = new javax.swing.text.MaskFormatter("##.###-###");
            maskCep.setPlaceholderCharacter('_');
        }
        catch(java.text.ParseException e){

        }
        jftfCep = new javax.swing.JFormattedTextField(maskCep);
        jLabel10 = new javax.swing.JLabel();
        javax.swing.text.MaskFormatter maskFone= null;
        try{
            maskFone = new javax.swing.text.MaskFormatter("(##)####-####");
            maskFone.setPlaceholderCharacter('_');
        }
        catch(java.text.ParseException e){

        }
        jftfFone = new javax.swing.JFormattedTextField(maskFone);
        jLabel11 = new javax.swing.JLabel();
        jtxtEmail = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        javax.swing.text.MaskFormatter maskData= null;
        try{
            maskData = new javax.swing.text.MaskFormatter("##/##/####");
            maskData.setPlaceholderCharacter('_');
        }
        catch(java.text.ParseException e){

        }
        jftfDataNasc = new javax.swing.JFormattedTextField(maskData);
        jcbxEstados = new javax.swing.JComboBox<Estado>();

        lstUF= org.jdesktop.observablecollections.ObservableCollections.observableList(lstUF);

        setBounds(new java.awt.Rectangle(0, 0, 614, 320));
        setPreferredSize(new java.awt.Dimension(614, 320));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                closeDialog(evt);
            }
        });

        btnSalvar.setText("Salvar");
        btnSalvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalvarActionPerformed(evt);
            }
        });

        cancelButton.setText("Cancelar");
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });

        jLabel1.setText("CPF");

        jLabel2.setText("Nome");

        jLabel3.setText("Logradouro:");

        jftfCpf.setHorizontalAlignment(javax.swing.JFormattedTextField.RIGHT);

        jLabel4.setText("Número");

        jLabel5.setText("Complemento");

        lblImg.setText("lblImg");

        jLabel6.setText("Bairro");

        jLabel7.setText("Município");

        jLabel8.setText("UF");

        jLabel9.setText("CEP");

        jLabel10.setText("Fone");

        jLabel11.setText("E-mail");

        jLabel12.setText("Data de nascimento");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(101, 101, 101)
                                .addComponent(jLabel12))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jftfCpf, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(35, 35, 35)
                                .addComponent(jftfDataNasc, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel2)
                            .addComponent(jtxtNome, javax.swing.GroupLayout.PREFERRED_SIZE, 344, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(122, 122, 122)
                        .addComponent(lblImg, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(303, 303, 303)
                        .addComponent(jLabel4)
                        .addGap(26, 26, 26)
                        .addComponent(jLabel5))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jtxtLogradouro, javax.swing.GroupLayout.PREFERRED_SIZE, 344, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jtxtNum, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jtxtComp, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addGap(180, 180, 180)
                        .addComponent(jLabel7)
                        .addGap(183, 183, 183)
                        .addComponent(jLabel8))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(jtxtBairro, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(jtxtMunicipio, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(jcbxEstados, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jftfCep, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel9))
                            .addGap(18, 18, 18)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(jLabel10)
                                    .addGap(119, 119, 119)
                                    .addComponent(jLabel11))
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(jftfFone, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addComponent(jtxtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                            .addComponent(btnSalvar, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(20, 20, 20)
                            .addComponent(cancelButton, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(19, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel12))
                        .addGap(6, 6, 6)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jftfCpf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jftfDataNasc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(11, 11, 11)
                        .addComponent(jLabel2)
                        .addGap(5, 5, 5)
                        .addComponent(jtxtNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(lblImg, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(2, 2, 2)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5))
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jtxtLogradouro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jtxtNum, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jtxtComp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(11, 11, 11)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7)
                    .addComponent(jLabel8))
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jtxtBairro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jtxtMunicipio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jcbxEstados, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(13, 13, 13)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jftfCep, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel10)
                            .addComponent(jLabel11))
                        .addGap(6, 6, 6)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jftfFone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jtxtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(28, 28, 28)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnSalvar)
                    .addComponent(cancelButton))
                .addContainerGap(36, Short.MAX_VALUE))
        );

        getRootPane().setDefaultButton(btnSalvar);

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    /**
     * Salva as alterações ou o novo Fornecedor.
     * @param evt 
     */
    private void btnSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalvarActionPerformed
        func= new Funcionario();
        setFuncionario(func);
        try {
            if (isIns){
                f.incluir(func);
            }else{
                f.alterar(func);
            }
            doClose(RET_OK);
        } catch (GeralException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }//GEN-LAST:event_btnSalvarActionPerformed
    
    /**
     * Cancela a operação de inclusão ou alteração
     * @param evt 
     */
    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
        doClose(RET_CANCEL);
    }//GEN-LAST:event_cancelButtonActionPerformed

    /**
     * Closes the dialog
     */
    private void closeDialog(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_closeDialog
        doClose(RET_CANCEL);
    }//GEN-LAST:event_closeDialog
    
    private void doClose(int retStatus) {
        returnStatus = retStatus;
        setVisible(false);
        dispose();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(PropFuncionario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PropFuncionario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PropFuncionario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PropFuncionario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                PropFuncionario dialog = new PropFuncionario(new javax.swing.JFrame(), true, null);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnSalvar;
    private javax.swing.JButton cancelButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JComboBox<Estado> jcbxEstados;
    private javax.swing.JFormattedTextField jftfCep;
    private javax.swing.JFormattedTextField jftfCpf;
    private javax.swing.JFormattedTextField jftfDataNasc;
    private javax.swing.JFormattedTextField jftfFone;
    private javax.swing.JTextField jtxtBairro;
    private javax.swing.JTextField jtxtComp;
    private javax.swing.JTextField jtxtEmail;
    private javax.swing.JTextField jtxtLogradouro;
    private javax.swing.JTextField jtxtMunicipio;
    private javax.swing.JTextField jtxtNome;
    private javax.swing.JTextField jtxtNum;
    private javax.swing.JLabel lblImg;
    private java.util.List<Estado> lstUF;
    // End of variables declaration//GEN-END:variables
    private int returnStatus = RET_CANCEL;
}
