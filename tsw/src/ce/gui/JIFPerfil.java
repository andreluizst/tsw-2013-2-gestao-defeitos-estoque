/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ce.gui;

import ce.Main;
import ce.erro.GeralException;
import ce.model.basica.Perfil;
import ce.model.fachada.Fachada;
import java.util.LinkedList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author andreluiz
 */
public class JIFPerfil extends javax.swing.JInternalFrame implements IActionsGui{
    private Fachada f;
    private String activationName;
    /**
     * Creates new form JIFPerfil
     */
    public JIFPerfil() {
        initComponents();
        f= Fachada.getInstancia();
        activationName= "Perfil";
        DefaultTableCellRenderer dtcrEsq= new DefaultTableCellRenderer();
        dtcrEsq.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jTable1.getColumnModel().getColumn(0).setCellRenderer(dtcrEsq);
    }
    
    /**
     * Seleciona na tabela o perfil informado.
     * @param p 
     */
    private void selectPerfil(Perfil p){
        for(int i=0;i<lstPerfis.size();i++){
            if (p.getCodPerfil() == lstPerfis.get(i).getCodPerfil()){
                jTable1.setRowSelectionInterval(i, i);
                break;
            }
        }
    }
    
    /**
     * Inclui um novo registro.
     */
    @Override
    public void novo(){
        Perfil p;
        PropPerfil propPerfil= new PropPerfil(null, true, null); 
        propPerfil.setLocationRelativeTo(null);
        propPerfil.setVisible(true);
        p= propPerfil.getProperties();
        //propFun.getReturnStatus();
        if (p != null){
            lstPerfis.clear();
            try {
                lstPerfis.addAll(f.listarPerfil());
                selectPerfil(p);
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
        Perfil obj;
        int row= jTable1.getSelectedRow();
        if ((row == jTable1.getRowCount()) || (row > 0)){
            row--;
        }else{
            row=0;
        }
        obj= lstPerfis.get(jTable1.getSelectedRow());
        String [] opcoes= new String[] {"Sim", "Não"};
        String msg= "Deseja excluir " + obj.getNome()+ "?";
        int ret= JOptionPane.showOptionDialog(null, msg, "Confirmação",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
                null, opcoes, opcoes[0]);
        //JOptionPane.showMessageDialog(null, "Botão escolhido: " + ret);
        if (ret == 0){
            try {
                f.excluir(obj);
                if (jTable1.getRowCount() > 0){
                    lstPerfis.clear();
                    lstPerfis.addAll(f.listarPerfil());
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
        Perfil obj;
        obj= lstPerfis.get(jTable1.getSelectedRow());
        PropPerfil propObj= new PropPerfil(null, true, obj); 
        propObj.setLocationRelativeTo(null);
        propObj.setVisible(true);
        obj= propObj.getProperties();
        if (obj != null){
            try {
                f.alterar(obj);
                lstPerfis.clear();
                lstPerfis.addAll(f.listarPerfil());
                selectPerfil(obj);
            } catch (GeralException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage());
            }
        }
    }
    
    /**
     * Informa se o método pesquisar está implementado e disponível.
     * @return 
     */
    @Override
    public boolean pesquisarExiste(){
        return false;
    }
    
    /**
     * Retorna todas os perfis existentes.
     */
    @Override
    public void pesquisar(){
        listar();
    }
    
    @Override
    public void firstRecord(){
        if (jTable1.getRowCount() > 0){
            jTable1.setRowSelectionInterval(0, 0);
        }
    }
    
    /**
     * Seleciona o registro anterior
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
     * Seleciona o último registro da tabela.
     */
    @Override
    public void lastRecord(){
        if (jTable1.getRowCount() > 0){
            jTable1.setRowSelectionInterval(jTable1.getRowCount()-1, jTable1.getRowCount()-1);
        }
    }
    
    /**
     * Lista todos os registros.
     */
    @Override
    public void listar(){
        lstPerfis.clear();
        try {
            lstPerfis.addAll(f.listarPerfil());
        } catch (GeralException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        priorRecord();
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

        lstPerfis = new LinkedList<Perfil>();
        btnNovo = new javax.swing.JButton();
        btnExcluir = new javax.swing.JButton();
        btnAlterar = new javax.swing.JButton();
        btnListar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

        lstPerfis= org.jdesktop.observablecollections.ObservableCollections.observableList(lstPerfis);

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setNormalBounds(new java.awt.Rectangle(0, 0, 573, 374));
        addInternalFrameListener(new javax.swing.event.InternalFrameListener() {
            public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt) {
                JIFPerfilActivated(evt);
            }
            public void internalFrameClosed(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosing(javax.swing.event.InternalFrameEvent evt) {
                JIFPerfilClosing(evt);
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

        org.jdesktop.swingbinding.JTableBinding jTableBinding = org.jdesktop.swingbinding.SwingBindings.createJTableBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, lstPerfis, jTable1);
        org.jdesktop.swingbinding.JTableBinding.ColumnBinding columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${codPerfil}"));
        columnBinding.setColumnName("Cod Perfil");
        columnBinding.setColumnClass(Integer.class);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${nome}"));
        columnBinding.setColumnName("Nome");
        columnBinding.setColumnClass(String.class);
        bindingGroup.addBinding(jTableBinding);
        jTableBinding.bind();
        jScrollPane1.setViewportView(jTable1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnNovo)
                        .addGap(42, 42, 42)
                        .addComponent(btnExcluir)
                        .addGap(44, 44, 44)
                        .addComponent(btnAlterar)
                        .addGap(52, 52, 52)
                        .addComponent(btnListar)
                        .addGap(0, 29, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(15, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnNovo)
                    .addComponent(btnExcluir)
                    .addComponent(btnAlterar)
                    .addComponent(btnListar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 287, javax.swing.GroupLayout.PREFERRED_SIZE)
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

    private void JIFPerfilClosing(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_JIFPerfilClosing
        Main.atlzShellMenu(null);
        Main.desregistrarJanela(this);
        dispose();
    }//GEN-LAST:event_JIFPerfilClosing

    private void JIFPerfilActivated(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_JIFPerfilActivated
        Main.atlzShellMenu(this);
    }//GEN-LAST:event_JIFPerfilActivated

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAlterar;
    private javax.swing.JButton btnExcluir;
    private javax.swing.JButton btnListar;
    private javax.swing.JButton btnNovo;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private java.util.List<Perfil> lstPerfis;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables
}
