/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ce.gui;

import ce.Main;
import ce.erro.GeralException;
import ce.model.basica.LocalEstoque;
import ce.model.fachada.Fachada;
import java.util.LinkedList;
import javax.swing.JOptionPane;

/**
 *
 * @author Andre
 */
public class JIFLocalEstoque extends javax.swing.JInternalFrame implements IActionsGui{
    private Fachada f;
    private String activationName;
    /**
     * Creates new form JIFLocalEstoque
     */
    public JIFLocalEstoque() {
        initComponents();
        f= Fachada.getInstancia();
        activationName= "Local de estoque";
    }
    
    /**
     * Seleciona um local de estoque na tabela
     * @param obj 
     * Local que deseja selecionar
     */
    private void selectObj(LocalEstoque obj){
        for(int i=0;i<lstLocalEst.size();i++){
            if (obj.getDescricao().compareTo(lstLocalEst.get(i).getDescricao())==0){
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
        LocalEstoque obj;
        PropLocalEstoque propLocal= new PropLocalEstoque(null, true, null); 
        propLocal.setLocationRelativeTo(null);
        propLocal.setVisible(true);
        obj= propLocal.getProperties();
        if (obj != null){
            lstLocalEst.clear();
            try {
                lstLocalEst.addAll(f.listarLocalEstoque());
                selectObj(obj);
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
        LocalEstoque obj;
        int row= jTable1.getSelectedRow();
        if ((row == jTable1.getRowCount()) || (row > 0)){
            row--;
        }else{
            row=0;
        }
        obj= lstLocalEst.get(jTable1.getSelectedRow());
        String [] opcoes= new String[] {"Sim", "Não"};
        String msg= "Deseja excluir " + obj.toString() + "?";
        int result= JOptionPane.showOptionDialog(null, msg, "Confirmação",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
                null, opcoes, opcoes[0]);
        if (result == 0){
            try {
                f.excluir(obj);
                if (jTable1.getRowCount() > 0){
                    lstLocalEst.clear();
                    lstLocalEst.addAll(f.listarLocalEstoque());
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
        LocalEstoque obj;
        obj= lstLocalEst.get(jTable1.getSelectedRow());
        PropLocalEstoque propObj= new PropLocalEstoque(null, true, obj); 
        propObj.setLocationRelativeTo(null);
        propObj.setVisible(true);
        obj= propObj.getProperties();
        if (obj != null){
            try {
                lstLocalEst.clear();
                lstLocalEst.addAll(f.listarLocalEstoque());
                selectObj(obj);
            } catch (GeralException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage());
            }
        }
    }
    
    /**
     * Informa se o método pesquisar está implementado e fundcional para esta tela.
     * @return 
     * 
     */
    @Override
    public boolean pesquisarExiste(){
        return false;
    }
    /**
     * Realiza uma presquisa de todos os dados
     */
    @Override
    public void pesquisar(){
        listar();
    }
    
    /**
     * Lista todos os registros na tabela.
     */
    @Override
    public void listar(){
        lstLocalEst.clear();
        try {
            lstLocalEst.addAll(f.listarLocalEstoque());
        } catch (GeralException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        firstRecord();
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

        lstLocalEst = new LinkedList<LocalEstoque>();
        jPanel2 = new javax.swing.JPanel();
        btnNovo = new javax.swing.JButton();
        btnExcluir = new javax.swing.JButton();
        btnAlterar = new javax.swing.JButton();
        btnListar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

        lstLocalEst= org.jdesktop.observablecollections.ObservableCollections.observableList(lstLocalEst);

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Local de estoque");
        addInternalFrameListener(new javax.swing.event.InternalFrameListener() {
            public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt) {
                JIFLocalEstoqueActivated(evt);
            }
            public void internalFrameClosed(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosing(javax.swing.event.InternalFrameEvent evt) {
                JIFLocalEstoqueClosing(evt);
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

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnNovo)
                .addGap(42, 42, 42)
                .addComponent(btnExcluir)
                .addGap(44, 44, 44)
                .addComponent(btnAlterar)
                .addGap(52, 52, 52)
                .addComponent(btnListar)
                .addContainerGap(30, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(52, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnNovo)
                    .addComponent(btnExcluir)
                    .addComponent(btnAlterar)
                    .addComponent(btnListar))
                .addGap(19, 19, 19))
        );

        org.jdesktop.swingbinding.JTableBinding jTableBinding = org.jdesktop.swingbinding.SwingBindings.createJTableBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, lstLocalEst, jTable1);
        org.jdesktop.swingbinding.JTableBinding.ColumnBinding columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${codLocal}"));
        columnBinding.setColumnName("Cod Local");
        columnBinding.setColumnClass(Integer.class);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${descricao}"));
        columnBinding.setColumnName("Descricao");
        columnBinding.setColumnClass(String.class);
        bindingGroup.addBinding(jTableBinding);
        jTableBinding.bind();
        jScrollPane1.setViewportView(jTable1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 528, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 260, Short.MAX_VALUE)
                .addContainerGap())
        );

        bindingGroup.bind();

        pack();
    }// </editor-fold>//GEN-END:initComponents

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

    private void JIFLocalEstoqueActivated(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_JIFLocalEstoqueActivated
        Main.atlzShellMenu(this);
    }//GEN-LAST:event_JIFLocalEstoqueActivated

    private void JIFLocalEstoqueClosing(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_JIFLocalEstoqueClosing
        Main.atlzShellMenu(null);
        Main.desregistrarJanela(this);
        dispose();
    }//GEN-LAST:event_JIFLocalEstoqueClosing

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAlterar;
    private javax.swing.JButton btnExcluir;
    private javax.swing.JButton btnListar;
    private javax.swing.JButton btnNovo;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private java.util.List<LocalEstoque> lstLocalEst;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables
}
