/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ce.gui;

import ce.Main;
import ce.erro.GeralException;
import ce.model.basica.Fornecedor;
import ce.model.basica.Produto;
import ce.model.basica.Saida;
import ce.model.fachada.Fachada;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author Andre
 */
public class JIFSaida extends javax.swing.JInternalFrame implements IActionsGui {
    private Fachada f;
    private String activationName;
    private Date hj;
    /**
     * Creates new form JIFSaida
     */
    public JIFSaida() {
        initComponents();
        f= Fachada.getInstancia();
        activationName= "Saída";
        hj= new Date();
        jftfDtInicial.setText("01/01/1900");
        jftfDtFinal.setText(new java.text.SimpleDateFormat("dd/MM/yyyy").format(hj));
        Fornecedor fornIni= new Fornecedor();
        Produto prodIni= new Produto();
        fornIni.setCnpj("");
        fornIni.setNome("<Todos>");
        prodIni.setCodProd(0);
        prodIni.setDescProd("<Todos>");
        try {
            lstForns.addAll(f.listarFornecedor());
            lstForns.add(0, fornIni);
            jcbxFornecedores.setSelectedIndex(0);
            lstProds.add(prodIni);
            lstProds.addAll(f.listarProduto());
            jcbxProdutos.setSelectedIndex(0);
        } catch (GeralException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }
    
    /**
     * Seleciona um objeto na tabela
     * @param obj
     * Funcionário que deseja selecionar
     */
    private void selectObj(Saida obj){
        for(int i=0;i<lista.size();i++){
            if (lista.get(i).getCodSaida() == obj.getCodSaida()){
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
        Saida obj;
        PropSaida propObj= new PropSaida(null, true, null); 
        propObj.setLocationRelativeTo(null);
        propObj.setVisible(true);
        obj= propObj.getProperties();
        //propForn.getReturnStatus();
        if (obj != null){
            lista.clear();
            try {
                lista.addAll(f.listarSaida());
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
        Saida obj;
        int row= jTable1.getSelectedRow();
        if ((row == jTable1.getRowCount()) || (row > 0)){
            row--;
        }else{
            row=0;
        }
        obj= lista.get(jTable1.getSelectedRow());
        String [] opcoes= new String[] {"Sim", "Não"};
        String msg= "Deseja excluir a saída " + obj.toString() + "?";
        int result= JOptionPane.showOptionDialog(null, msg, "Confirmação",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
                null, opcoes, opcoes[0]);
        if (result ==0){
            try {
                f.excluir(obj);
                if (jTable1.getRowCount() > 0){
                    lista.clear();
                    lista.addAll(f.listarSaida());
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
        Saida obj;
        obj= lista.get(jTable1.getSelectedRow());
        PropSaida propObj= new PropSaida(null, true, obj); 
        propObj.setLocationRelativeTo(null);
        propObj.setVisible(true);
        obj= propObj.getProperties();
        if (obj != null){
            try {
                lista.clear();
                lista.addAll(f.listarSaida());
                selectObj(obj);
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
        List<Saida> tmpLista;
        Saida s= new Saida();
        try{
            try{
                s.setCodSaida(Integer.parseInt(jtxtNumero.getText()));
            }catch(Exception ex){
                s.setCodSaida(0);
            }
            s.getEntrada().setLote(jtxtLote.getText());
            if (jcbxFornecedores.getSelectedIndex() > 0){
                s.getEntrada().setFornecedor(lstForns.get(jcbxFornecedores.getSelectedIndex()));
            }
            /*if (jcbxProdutos.getSelectedIndex() > 0){
                e.setProduto(lstProds.get(jcbxProdutos.getSelectedIndex()));
            }*/
            tmpLista= f.pesquisarSaida(s, jftfDtInicial.getText(), jftfDtFinal.getText());
            lista.clear();
            lista.addAll(tmpLista);
        }
        catch (GeralException ex){
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        firstRecord();
    }
    
    /**
     * Lista todos os registros na tabela.
     */
    @Override
    public void listar(){
        List<Saida> tmpLista;
        lista.clear();
        try {
            tmpLista= f.listarSaida();
            lista.addAll(tmpLista);
        } catch (GeralException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        jtxtNumero.setText("");
        jtxtLote.setText("");
        jftfDtInicial.setText("01/01/1900");
        jftfDtFinal.setText(new java.text.SimpleDateFormat("dd/MM/yyyy").format(hj));
        jcbxFornecedores.setSelectedIndex(0);
        jcbxProdutos.setSelectedIndex(0);
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

        lista = new LinkedList<Saida>();
        lstForns = new LinkedList<Fornecedor>();
        lstProds = new LinkedList<Produto>();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jtxtNumero = new javax.swing.JTextField();
        btnPesquisar = new javax.swing.JButton();
        btnNovo = new javax.swing.JButton();
        btnExcluir = new javax.swing.JButton();
        btnAlterar = new javax.swing.JButton();
        btnAtualizar = new javax.swing.JButton();
        javax.swing.text.MaskFormatter maskDt=null;
        try{
            maskDt= new javax.swing.text.MaskFormatter("##/##/####");
            maskDt.setPlaceholderCharacter('_');
        }catch(java.text.ParseException e){}
        jftfDtFinal = new javax.swing.JFormattedTextField(maskDt);
        javax.swing.text.MaskFormatter maskData=null;
        try{
            maskData= new javax.swing.text.MaskFormatter("##/##/####");
            maskData.setPlaceholderCharacter('_');
        }catch(java.text.ParseException e){}
        jftfDtInicial = new javax.swing.JFormattedTextField(maskData);
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jcbxFornecedores = new javax.swing.JComboBox();
        jLabel1 = new javax.swing.JLabel();
        jcbxProdutos = new javax.swing.JComboBox();
        jLabel5 = new javax.swing.JLabel();
        jtxtLote = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

        lista= org.jdesktop.observablecollections.ObservableCollections.observableList(lista);

        lstForns= org.jdesktop.observablecollections.ObservableCollections.observableList(lstForns);

        lstProds= org.jdesktop.observablecollections.ObservableCollections.observableList(lstProds);

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Saída");
        addInternalFrameListener(new javax.swing.event.InternalFrameListener() {
            public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt) {
                JIFSaidaActivated(evt);
            }
            public void internalFrameClosed(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosing(javax.swing.event.InternalFrameEvent evt) {
                JIFSaidaClosing(evt);
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

        jLabel2.setText("Número");

        jtxtNumero.setToolTipText("");

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

        jLabel3.setText("Data inicial");

        jLabel4.setText("Data final");

        org.jdesktop.swingbinding.JComboBoxBinding jComboBoxBinding = org.jdesktop.swingbinding.SwingBindings.createJComboBoxBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, lstForns, jcbxFornecedores);
        bindingGroup.addBinding(jComboBoxBinding);

        jLabel1.setText("Fornecedor");

        jComboBoxBinding = org.jdesktop.swingbinding.SwingBindings.createJComboBoxBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, lstProds, jcbxProdutos);
        bindingGroup.addBinding(jComboBoxBinding);

        jLabel5.setText("Produto");

        jtxtLote.setToolTipText("");

        jLabel6.setText("Lote");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnNovo)
                            .addComponent(jLabel2)
                            .addComponent(jtxtNumero, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(31, 31, 31)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jftfDtInicial, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel3))
                                .addGap(27, 27, 27)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel4)
                                    .addComponent(jftfDtFinal, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(30, 30, 30)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel6)
                                    .addComponent(jtxtLote, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(49, 49, 49)
                                .addComponent(btnPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addGap(32, 32, 32)
                                .addComponent(btnExcluir)
                                .addGap(28, 28, 28)
                                .addComponent(btnAlterar)
                                .addGap(67, 67, 67)
                                .addComponent(btnAtualizar))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jcbxFornecedores, javax.swing.GroupLayout.PREFERRED_SIZE, 322, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(jcbxProdutos, javax.swing.GroupLayout.PREFERRED_SIZE, 322, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(177, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnPesquisar)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jtxtLote, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jtxtNumero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jftfDtFinal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jftfDtInicial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jcbxFornecedores, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jcbxProdutos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 64, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnNovo)
                    .addComponent(btnExcluir)
                    .addComponent(btnAlterar)
                    .addComponent(btnAtualizar))
                .addGap(19, 19, 19))
        );

        org.jdesktop.swingbinding.JTableBinding jTableBinding = org.jdesktop.swingbinding.SwingBindings.createJTableBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, lista, jTable1);
        org.jdesktop.swingbinding.JTableBinding.ColumnBinding columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${codSaida}"));
        columnBinding.setColumnName("Cod Saida");
        columnBinding.setColumnClass(Integer.class);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${dataSaida}"));
        columnBinding.setColumnName("Data Saida");
        columnBinding.setColumnClass(String.class);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${qtde}"));
        columnBinding.setColumnName("Qtde");
        columnBinding.setColumnClass(Double.class);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${entrada}"));
        columnBinding.setColumnName("Entrada");
        columnBinding.setColumnClass(ce.model.basica.Entrada.class);
        bindingGroup.addBinding(jTableBinding);
        jTableBinding.bind();

        jScrollPane1.setViewportView(jTable1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jScrollPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 249, Short.MAX_VALUE)
                .addContainerGap())
        );

        bindingGroup.bind();

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnPesquisarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPesquisarActionPerformed
        pesquisar();
    }//GEN-LAST:event_btnPesquisarActionPerformed

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

    private void JIFSaidaActivated(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_JIFSaidaActivated
        Main.atlzShellMenu(this);
    }//GEN-LAST:event_JIFSaidaActivated

    private void JIFSaidaClosing(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_JIFSaidaClosing
        Main.atlzShellMenu(null);
        Main.desregistrarJanela(this);
        dispose();
    }//GEN-LAST:event_JIFSaidaClosing

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAlterar;
    private javax.swing.JButton btnAtualizar;
    private javax.swing.JButton btnExcluir;
    private javax.swing.JButton btnNovo;
    private javax.swing.JButton btnPesquisar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JComboBox jcbxFornecedores;
    private javax.swing.JComboBox jcbxProdutos;
    private javax.swing.JFormattedTextField jftfDtFinal;
    private javax.swing.JFormattedTextField jftfDtInicial;
    private javax.swing.JTextField jtxtLote;
    private javax.swing.JTextField jtxtNumero;
    private java.util.List<Saida> lista;
    private java.util.List<Fornecedor> lstForns;
    private java.util.List<Produto> lstProds;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables
}
