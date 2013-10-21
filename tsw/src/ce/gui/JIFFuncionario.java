/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ce.gui;

import ce.Main;
import ce.erro.GeralException;
import ce.model.basica.Funcionario;
import ce.model.fachada.Fachada;
import java.util.LinkedList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author Andre Luiz
 */
public class JIFFuncionario extends javax.swing.JInternalFrame implements IActionsGui{
    private Fachada f;
    private Funcionario pesqFun;
    private String activationName;
    private boolean userIsAdm;

    /**
     * Creates new form JIFFuncionario
     */
    public JIFFuncionario() {
        initComponents();
        pesqFun= new Funcionario();
        f= Fachada.getInstancia();
        activationName= "Funcionário";
        userIsAdm= f.getUser().getPerfil().getNome().toLowerCase().compareTo("administrador") == 0;
    }
    
    /**
     * Preenche o objeto pesFun do tipo Funcionario com os dados para pesquisa.
     */
    private void preencherFun(){
        if (jtxtNome.getText().compareTo("") != 0){
            pesqFun.setNome(jtxtNome.getText());
        }
        lstFuncionarios.clear();
        try {
            lstFuncionarios.addAll(f.pesquisarFuncionario(pesqFun.getNome()));
        } catch (GeralException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }
    
    /**
     * Seleciona um funcionário na tabela
     * @param fun
     * Funcionário que deseja selecionar
     */
    private void selectFun(Funcionario f){
        for(int i=0;i<lstFuncionarios.size();i++){
            if (f.getCpf().compareTo(lstFuncionarios.get(i).getCpf()) == 0){
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
        Funcionario fun;
        PropFuncionario propFun= new PropFuncionario(null, true, null); 
        propFun.setLocationRelativeTo(null);
        propFun.setVisible(true);
        fun= propFun.getProperties();
        //propFun.getReturnStatus();
        if (fun != null){
            lstFuncionarios.clear();
            try {
                lstFuncionarios.addAll(f.listarFuncionario());
                selectFun(fun);
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
        Funcionario fun;
        int row= jTable1.getSelectedRow();
        if ((row == jTable1.getRowCount()) || (row > 0)){
            row--;
        }else{
            row=0;
        }
        fun= lstFuncionarios.get(jTable1.getSelectedRow());
        String [] opcoes= new String[] {"Sim", "Não"};
        String msg= "Deseja excluir " + fun.getNome()+", CPF "+fun.getCpf()+ "?";
        int result= JOptionPane.showOptionDialog(null, msg, "Confirmação",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
                null, opcoes, opcoes[0]);
        if (result ==0){
            try {
                f.excluir(fun);
                if (jTable1.getRowCount() > 0){
                    lstFuncionarios.clear();
                    lstFuncionarios.addAll(f.listarFuncionario());
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
        Funcionario fun;
        fun= lstFuncionarios.get(jTable1.getSelectedRow());
        PropFuncionario propFun= new PropFuncionario(null, true, fun); 
        propFun.setLocationRelativeTo(null);
        propFun.setVisible(true);
        fun= propFun.getProperties();
        if (fun != null){
            try {
                f.alterar(fun);
                lstFuncionarios.clear();
                lstFuncionarios.addAll(f.listarFuncionario());
                selectFun(fun);
            } catch (GeralException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage());
            }
        }
    }
    
    /**
     * Informa que o método pesquisar está implementado e disponível.
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
        List<Funcionario> lista;
        try{
            lista= f.pesquisarFuncionario(jtxtNome.getText());
            lstFuncionarios.clear();
            if (!userIsAdm){
                for (Funcionario fun : lista){
                    if (fun.getCpf().compareTo("00000000000") == 0){
                        lista.remove(fun);
                        break;
                    }
                }
            }
            lstFuncionarios.addAll(lista);
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
        List<Funcionario> lista;
        lstFuncionarios.clear();
        try {
            lista= f.listarFuncionario();
            if (!userIsAdm){
                for (Funcionario fun : lista){
                    if (fun.getCpf().compareTo("00000000000") == 0){
                        lista.remove(fun);
                        break;
                    }
                }
            }
            lstFuncionarios.addAll(lista);
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

        lstFuncionarios = new LinkedList<Funcionario>();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jtxtNome = new javax.swing.JTextField();
        btnPesquisar = new javax.swing.JButton();
        btnNovo = new javax.swing.JButton();
        btnExcluir = new javax.swing.JButton();
        btnAlterar = new javax.swing.JButton();
        btnAtualizar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

        lstFuncionarios= org.jdesktop.observablecollections.ObservableCollections.observableList(lstFuncionarios);

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Funcionários");
        setNormalBounds(new java.awt.Rectangle(0, 0, 700, 474));
        addInternalFrameListener(new javax.swing.event.InternalFrameListener() {
            public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt) {
                jifFuncionarioActivated(evt);
            }
            public void internalFrameClosed(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosing(javax.swing.event.InternalFrameEvent evt) {
                jifFuncionarioClosing(evt);
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

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

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

        btnNovo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ce/gui/images/btnNovo.jpg"))); // NOI18N
        btnNovo.setMnemonic('n');
        btnNovo.setText("Novo");
        btnNovo.setDisabledIcon(new javax.swing.ImageIcon(getClass().getResource("/ce/gui/images/btnNovoDisable2.png"))); // NOI18N
        btnNovo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNovoActionPerformed(evt);
            }
        });

        btnExcluir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ce/gui/images/btnApagar.jpg"))); // NOI18N
        btnExcluir.setMnemonic('x');
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
        btnAlterar.setMnemonic('t');
        btnAlterar.setText("Alterar");
        btnAlterar.setDisabledIcon(new javax.swing.ImageIcon(getClass().getResource("/ce/gui/images/btnAlterarDisable.png"))); // NOI18N

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, jTable1, org.jdesktop.beansbinding.ELProperty.create("${selectedElement!=null}"), btnAlterar, org.jdesktop.beansbinding.BeanProperty.create("enabled"));
        bindingGroup.addBinding(binding);

        btnAlterar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAlterarActionPerformed(evt);
            }
        });

        btnAtualizar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ce/gui/images/btnAtualizarHot.png"))); // NOI18N
        btnAtualizar.setText("Listar/Atualizar");
        btnAtualizar.setDisabledIcon(new javax.swing.ImageIcon(getClass().getResource("/ce/gui/images/btnAtualizarDisable.png"))); // NOI18N
        btnAtualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAtualizarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jtxtNome, javax.swing.GroupLayout.PREFERRED_SIZE, 321, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(btnNovo)
                                .addGap(31, 31, 31)
                                .addComponent(btnExcluir)
                                .addGap(28, 28, 28)
                                .addComponent(btnAlterar)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnAtualizar)
                            .addComponent(btnPesquisar))))
                .addContainerGap(190, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtxtNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnPesquisar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 35, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnNovo)
                    .addComponent(btnExcluir)
                    .addComponent(btnAlterar)
                    .addComponent(btnAtualizar))
                .addGap(19, 19, 19))
        );

        org.jdesktop.swingbinding.JTableBinding jTableBinding = org.jdesktop.swingbinding.SwingBindings.createJTableBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, lstFuncionarios, jTable1);
        org.jdesktop.swingbinding.JTableBinding.ColumnBinding columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${cpf}"));
        columnBinding.setColumnName("Cpf");
        columnBinding.setColumnClass(String.class);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${nome}"));
        columnBinding.setColumnName("Nome");
        columnBinding.setColumnClass(String.class);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${dtNasc}"));
        columnBinding.setColumnName("Dt Nasc");
        columnBinding.setColumnClass(String.class);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${logradouro}"));
        columnBinding.setColumnName("Logradouro");
        columnBinding.setColumnClass(String.class);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${num}"));
        columnBinding.setColumnName("Num");
        columnBinding.setColumnClass(Integer.class);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${comp}"));
        columnBinding.setColumnName("Comp");
        columnBinding.setColumnClass(String.class);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${bairro}"));
        columnBinding.setColumnName("Bairro");
        columnBinding.setColumnClass(String.class);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${municipio}"));
        columnBinding.setColumnName("Municipio");
        columnBinding.setColumnClass(String.class);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${uf}"));
        columnBinding.setColumnName("Uf");
        columnBinding.setColumnClass(String.class);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${cep}"));
        columnBinding.setColumnName("Cep");
        columnBinding.setColumnClass(String.class);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${email}"));
        columnBinding.setColumnName("Email");
        columnBinding.setColumnClass(String.class);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${fone}"));
        columnBinding.setColumnName("Fone");
        columnBinding.setColumnClass(String.class);
        bindingGroup.addBinding(jTableBinding);
        jTableBinding.bind();

        jScrollPane1.setViewportView(jTable1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 684, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 313, Short.MAX_VALUE)
                .addContainerGap())
        );

        bindingGroup.bind();

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnPesquisarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPesquisarActionPerformed
        pesquisar();
    }//GEN-LAST:event_btnPesquisarActionPerformed

    private void jifFuncionarioClosing(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_jifFuncionarioClosing
        Main.atlzShellMenu(null);
        Main.desregistrarJanela(this);
        dispose();
    }//GEN-LAST:event_jifFuncionarioClosing

    private void jifFuncionarioActivated(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_jifFuncionarioActivated
        Main.atlzShellMenu(this);
    }//GEN-LAST:event_jifFuncionarioActivated

    private void btnNovoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNovoActionPerformed
        novo();
    }//GEN-LAST:event_btnNovoActionPerformed

    private void btnExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcluirActionPerformed
        excluir();
    }//GEN-LAST:event_btnExcluirActionPerformed

    private void btnAlterarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAlterarActionPerformed
        alterar();
    }//GEN-LAST:event_btnAlterarActionPerformed

    private void btnAtualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAtualizarActionPerformed
        listar();
    }//GEN-LAST:event_btnAtualizarActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAlterar;
    private javax.swing.JButton btnAtualizar;
    private javax.swing.JButton btnExcluir;
    private javax.swing.JButton btnNovo;
    private javax.swing.JButton btnPesquisar;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jtxtNome;
    private java.util.List<Funcionario> lstFuncionarios;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables
}
