/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ce.gui;

import ce.Main;
import ce.erro.GeralException;
import ce.model.basica.Funcionario;
import ce.model.basica.Usuario;
import ce.model.fachada.Fachada;
import java.util.LinkedList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author andreluiz
 */
public class JIFUsuario extends javax.swing.JInternalFrame implements IActionsGui{
    private Fachada f;
    private Usuario u;
    private String activationName;
    /**
     * Creates new form JIFUsuario
     */
    public JIFUsuario() {
        initComponents();
        f= Fachada.getInstancia();
        u= new Usuario();
        activationName= "Usuário";
    }
    
    /**
     * Seleciona um funcionário na tabela
     * @param fun
     * Funcionário que deseja selecionar
     */
    private void selectUsuario(Usuario u){
        for(int i=0;i<lista.size();i++){
            if (u.getNome().compareTo(lista.get(i).getNome()) == 0){
                jTable1.setRowSelectionInterval(i, i);
                break;
            }
        }
    }
    
    /**
     * Inclui um novo registro
     */
    @Override
    public void novo(){
        Usuario obj;
        PropUsuario propUsu= new PropUsuario(null, true, null); 
        propUsu.setLocationRelativeTo(null);
        propUsu.setVisible(true);
        obj= propUsu.getProperties();
        //propFun.getReturnStatus();
        if (obj != null){
            lista.clear();
            try {
                lista.addAll(f.listarUsuario());
                selectUsuario(obj);
            } catch (GeralException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage());
            }
        }
    }
    
    /**
     * Exclui o registro selecionado. Será exibida uma mensagem pedindo confirmação.
     */
    @Override
    public void excluir(){
        Usuario obj;
        int row= jTable1.getSelectedRow();
        if ((row == jTable1.getRowCount()) || (row > 0)){
            row--;
        }else{
            row=0;
        }
        obj= lista.get(jTable1.getSelectedRow());
        String [] opcoes= new String[] {"Sim", "Não"};
        String msg= "Deseja excluir " + obj.toString() + "?";
        int result= JOptionPane.showOptionDialog(null, msg, "Confirmação",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
                null, opcoes, opcoes[0]);
        if (result ==0){
            try {
                f.excluir(obj);
                if (jTable1.getRowCount() > 0){
                    lista.clear();
                    lista.addAll(f.listarUsuario());
                }
                if (jTable1.getRowCount() > 0){
                    jTable1.setRowSelectionInterval(row, row);
                }
            } catch (GeralException ex) {
                System.out.println(ex.getMessage());
                JOptionPane.showMessageDialog(null, ex.getMessage());
            }
        }
    }
    
    /**
     * Altera o registro selecionado
     */
    @Override
    public void alterar(){
        Usuario obj;
        obj= lista.get(jTable1.getSelectedRow());
        PropUsuario propUsu= new PropUsuario(null, true, obj); 
        propUsu.setLocationRelativeTo(null);
        propUsu.setVisible(true);
        obj= propUsu.getProperties();
        if (obj != null){
        //if (propUsu.getReturnStatus() == 0){
            try {
                //f.alterar(obj);
                lista.clear();
                lista.addAll(f.listarUsuario());
                selectUsuario(obj);
            } catch (GeralException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage());
            }
        }
    }
    
    /**
     * Informa se o método pesquisar é válido para este frame
     * @return 
     */
    @Override
    public boolean pesquisarExiste(){
        return true;
    }
    
    /**
     * Realiza uma presquisa de dados
     */
    @Override
    public void pesquisar(){
        List<Usuario> tmpLista;
        try{
            tmpLista= f.pesquisarUsuario(jtxtNome.getText());
            lista.clear();
            lista.addAll(tmpLista);
        }
        catch (GeralException e){
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    
    /**
     * Lista todos os registros na tabela.
     */
    @Override
    public void listar(){
        lista.clear();
        try {
            lista.addAll(f.listarUsuario());
        } catch (GeralException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }
    
    /**
     * Seleciona o primeiro registro da tabela
     */
    @Override
    public void firstRecord(){
        if (jTable1.getRowCount() > 0){
            jTable1.setRowSelectionInterval(0, 0);
        }
    }
    
    /**
     * Seleciona o registro anterior ao atualmente selecionado na tabela.
     */
    @Override
    public void priorRecord(){
        int line= jTable1.getSelectedRow();
        if (line > 0){
            jTable1.setRowSelectionInterval(line-1, line-1);
        }
    }
    
    /**
     * Seleciona o próximo registro da tabela.
     */
    @Override
    public void nextRecord(){
        if (jTable1.getRowCount() > 0){
            if (jTable1.getSelectedRow() < jTable1.getRowCount()-1){
                jTable1.setRowSelectionInterval(jTable1.getSelectedRow()+1, jTable1.getSelectedRow()+1);
            }
        }
    }
    
    /**
     * seleciona o último registro da tabela
     */
    @Override
    public void lastRecord(){
        if (jTable1.getRowCount() > 0){
            jTable1.setRowSelectionInterval(jTable1.getRowCount()-1, jTable1.getRowCount()-1);
        }
    }
    
    /**
     * Informa o nome usado para ativar a janela após ser criada. Também pode ser
     * usado como texto do menu que executará a ação.
     * @return 
     */
    @Override
    public String getActivationName(){
        return activationName;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        bindingGroup = new org.jdesktop.beansbinding.BindingGroup();

        lista = new LinkedList<Usuario>();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        btnNovo = new javax.swing.JButton();
        btnExcluir = new javax.swing.JButton();
        btnAlterar = new javax.swing.JButton();
        btnListar = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jtxtNome = new javax.swing.JTextField();
        btnPesquisar = new javax.swing.JButton();

        lista= org.jdesktop.observablecollections.ObservableCollections.observableList(lista);

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        addInternalFrameListener(new javax.swing.event.InternalFrameListener() {
            public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt) {
                JIFUsuarioActivated(evt);
            }
            public void internalFrameClosed(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosing(javax.swing.event.InternalFrameEvent evt) {
                JIFUsuarioClosing(evt);
            }
            public void internalFrameDeactivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeiconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameIconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameOpened(javax.swing.event.InternalFrameEvent evt) {
            }
        });

        org.jdesktop.swingbinding.JTableBinding jTableBinding = org.jdesktop.swingbinding.SwingBindings.createJTableBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, lista, jTable1, "");
        org.jdesktop.swingbinding.JTableBinding.ColumnBinding columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${codUsuario}"));
        columnBinding.setColumnName("Cod Usuario");
        columnBinding.setColumnClass(Integer.class);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${perfil}"));
        columnBinding.setColumnName("Perfil");
        columnBinding.setColumnClass(ce.model.basica.Perfil.class);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${funcionario}"));
        columnBinding.setColumnName("Funcionario");
        columnBinding.setColumnClass(ce.model.basica.Funcionario.class);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${nome}"));
        columnBinding.setColumnName("Nome");
        columnBinding.setColumnClass(String.class);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${senha}"));
        columnBinding.setColumnName("Senha");
        columnBinding.setColumnClass(String.class);
        bindingGroup.addBinding(jTableBinding);
        jTableBinding.bind();
        jScrollPane1.setViewportView(jTable1);

        btnNovo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ce/gui/images/btnNovo.jpg"))); // NOI18N
        btnNovo.setText("Novo");
        btnNovo.setDisabledIcon(new javax.swing.ImageIcon(getClass().getResource("/ce/gui/images/btnNovoDisable2.png"))); // NOI18N
        btnNovo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNovoActionPerformed(evt);
            }
        });

        btnExcluir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ce/gui/images/btnApagar.jpg"))); // NOI18N
        btnExcluir.setText("Excluir");
        btnExcluir.setDisabledIcon(new javax.swing.ImageIcon(getClass().getResource("/ce/gui/images/btnApagarDisable2.png"))); // NOI18N

        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, jTable1, org.jdesktop.beansbinding.ELProperty.create("${selectedElement!=null}"), btnExcluir, org.jdesktop.beansbinding.BeanProperty.create("enabled"));
        bindingGroup.addBinding(binding);

        btnExcluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExcluirActionPerformed(evt);
            }
        });

        btnAlterar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ce/gui/images/btnAlterar.jpg"))); // NOI18N
        btnAlterar.setText("Alterar");
        btnAlterar.setDisabledIcon(new javax.swing.ImageIcon(getClass().getResource("/ce/gui/images/btnAlterarDisable.png"))); // NOI18N

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, jTable1, org.jdesktop.beansbinding.ELProperty.create("${selectedElement!=null}"), btnAlterar, org.jdesktop.beansbinding.BeanProperty.create("enabled"));
        bindingGroup.addBinding(binding);

        btnAlterar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAlterarActionPerformed(evt);
            }
        });

        btnListar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ce/gui/images/btnAtualizarHot.png"))); // NOI18N
        btnListar.setText("Listar/Atualizar");
        btnListar.setDisabledIcon(new javax.swing.ImageIcon(getClass().getResource("/ce/gui/images/btnAtualizarDisable.png"))); // NOI18N
        btnListar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnListarActionPerformed(evt);
            }
        });

        jLabel2.setText("Nome:");

        jtxtNome.setToolTipText("");

        btnPesquisar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ce/gui/images/btnLupa.png"))); // NOI18N
        btnPesquisar.setText("Pesquisar");
        btnPesquisar.setDisabledIcon(new javax.swing.ImageIcon(getClass().getResource("/ce/gui/images/btnLupaDisable.png"))); // NOI18N
        btnPesquisar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPesquisarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(btnNovo)
                        .addGap(42, 42, 42)
                        .addComponent(btnExcluir)
                        .addGap(44, 44, 44)
                        .addComponent(btnAlterar)
                        .addGap(52, 52, 52)
                        .addComponent(btnListar))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(18, 18, 18)
                        .addComponent(jtxtNome, javax.swing.GroupLayout.PREFERRED_SIZE, 246, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30)
                        .addComponent(btnPesquisar)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtxtNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnPesquisar)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 35, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnNovo)
                    .addComponent(btnExcluir)
                    .addComponent(btnAlterar)
                    .addComponent(btnListar))
                .addGap(19, 19, 19))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 590, Short.MAX_VALUE)
                .addContainerGap())
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 215, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        bindingGroup.bind();

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void JIFUsuarioActivated(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_JIFUsuarioActivated
        Main.atlzShellMenu(this);
    }//GEN-LAST:event_JIFUsuarioActivated

    private void JIFUsuarioClosing(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_JIFUsuarioClosing
        Main.atlzShellMenu(null);
        Main.desregistrarJanela(this);
        dispose();
    }//GEN-LAST:event_JIFUsuarioClosing

    private void btnNovoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNovoActionPerformed
        novo();
    }//GEN-LAST:event_btnNovoActionPerformed

    private void btnExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcluirActionPerformed
        excluir();
    }//GEN-LAST:event_btnExcluirActionPerformed

    private void btnAlterarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAlterarActionPerformed
        alterar();
    }//GEN-LAST:event_btnAlterarActionPerformed

    private void btnListarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnListarActionPerformed
        listar();
    }//GEN-LAST:event_btnListarActionPerformed

    private void btnPesquisarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPesquisarActionPerformed
        pesquisar();
    }//GEN-LAST:event_btnPesquisarActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAlterar;
    private javax.swing.JButton btnExcluir;
    private javax.swing.JButton btnListar;
    private javax.swing.JButton btnNovo;
    private javax.swing.JButton btnPesquisar;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jtxtNome;
    private java.util.List<Usuario> lista;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables
}
