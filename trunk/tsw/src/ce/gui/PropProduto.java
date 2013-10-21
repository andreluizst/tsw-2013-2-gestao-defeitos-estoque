/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ce.gui;
import ce.erro.GeralException;
import ce.model.basica.Categoria;
import ce.model.basica.Fornecedor;
import ce.model.basica.Produto;
import ce.model.basica.Unidade;
import ce.model.fachada.Fachada;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.LinkedList;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

/**
 *
 * @author Ricardo
 */
public class PropProduto extends javax.swing.JDialog {
      private Fachada f;
      private Produto produto;
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
     * Creates new form PropProduto
     */
    public PropProduto(java.awt.Frame parent, boolean modal, Produto prod) {
        super(parent, modal);
        initComponents();

        // Close the dialog when Esc is pressed
        String cancelName = "cancel";
        InputMap inputMap = getRootPane().getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), cancelName);
        ActionMap actionMap = getRootPane().getActionMap();
        actionMap.put(cancelName, new AbstractAction() {
             @Override
            public void actionPerformed(ActionEvent e) {
                doClose(RET_CANCEL);
            }
        });
        jtxtQtde.setDocument(new TeclasPermitidas("[^0-9|^,]", ","));
        jtxtQtdeMin.setDocument(new TeclasPermitidas("[^0-9|^,]", ","));
        f= Fachada.getInstancia();
        res= Resource.getInstancia();
        try{
            lstCategoria.addAll(f.listarCategoria());
            lstUnidade.addAll(f.listarUnidade());
        }catch(GeralException ex){
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        //atlzProdutos(); 
         if (prod == null){
            isIns= true;
            this.setTitle("INCLUIR produto");
            try {
                lblImg.setIcon(res.get("\\images\\Arquivo-Novo.jpg", lblImg.getWidth(), lblImg.getHeight()));
            } catch (GeralException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage());
            }
            try {
                lstForns.addAll(f.listarFornecedor());
            } catch (GeralException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage());
            }
        }else{
            isIns= false;
            setFields(prod);
            this.setTitle("ALTERAR produto");
            try {
                lblImg.setIcon(res.get("\\images\\Arquivo-Alterar3.jpg", lblImg.getWidth(), lblImg.getHeight()));
            } catch (GeralException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage());
            }
        }
    }

    private PropProduto(JFrame jFrame, boolean b) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

     /**
     * Preenche os campos do formulário com os dados do objeto Produto
     * @param prod 
     * Objeto do tipo Produto cujos dados serão exibidos nos campos texto da tela.
     */
    private void setFields(Produto prod){
        jtxtCodProduto.setText(prod.getCodProd().toString());
        jtxtDescProduto.setText(prod.getDescProd());
        jtxtQtde.setText(prod.getQtdeEstoq().toString().replaceAll("[.]", ","));
        jtxtQtdeMin.setText(prod.getQtdeMin().toString().replaceAll("[.]", ","));
        //jtxtQtdeIdeal.setText(prod.getQtdeIdeal().toString().replaceAll("[.]", ","));
        if (prod.getStatus() == 0){
            jcbxStatus.setSelectedIndex(0);
        }else{
            jcbxStatus.setSelectedIndex(1);
        }
        lstFornsProd.clear();
        lstFornsProd.addAll(prod.getFornecedores());
        lstForns.clear();
        try{
            lstForns.addAll(f.pesqFornsQueNaoFornecemEsteProd(prod.getCodProd()));
        }catch(GeralException e){
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        if (lstForns.size() > 0){
            jlstForns.setSelectedIndex(0);
        }
        if (lstFornsProd.size()>0){
            jlstFornsProd.setSelectedIndex(0);
        }
        if (lstCategoria.size()>0){
            for (int i=0;i<lstCategoria.size();i++){
                if (lstCategoria.get(i).getCodCateg() == prod.getCategoria().getCodCateg()){
                    jcbxCateg.setSelectedIndex(i);
                    break;
                }
            }
        }
        if (lstUnidade.size()>0){
            for (int i=0;i<lstUnidade.size();i++){
                if (lstUnidade.get(i).getCodUnid() == prod.getUnidade().getCodUnid()){
                    jcbxUnid.setSelectedIndex(i);
                    break;
                }
            }
        }
    }
        
    /**
     * Preenche o objeto do tipo Produto com os dados constantes nos campos
     * do produto
     * @param prod 
     * Objeto do tipo Fornecedor que receberá os dados dos campos da tela.
     */
    private void setProduto(Produto prod){
        Double dbQtde=0.00;
        if (!isIns){
            prod.setCodProd(Integer.parseInt(jtxtCodProduto.getText()));
        }
        prod.setUnidade(lstUnidade.get(jcbxUnid.getSelectedIndex()));
        prod.setCategoria(lstCategoria.get(jcbxCateg.getSelectedIndex()));
        //prod.setFornecedores(lstFornsProd);
        prod.setDescProd(jtxtDescProduto.getText());
        prod.setStatusProd(jcbxStatus.getItemAt(jcbxStatus.getSelectedIndex()).toString());
        try{
            dbQtde= Double.parseDouble(jtxtQtde.getText().replaceAll("[,]", "."));
            prod.setQtdeEstoq(dbQtde);
        }catch(Exception e){
            prod.setQtdeEstoq(0.00);
        }
        try{
            dbQtde= Double.parseDouble(jtxtQtdeMin.getText().replaceAll("[,]", "."));
            prod.setQtdeMin(dbQtde);
        }catch(Exception e){
            prod.setQtdeEstoq(0.00);
        }
        prod.setQtdeIdeal(0.00);
        prod.getFornecedores().clear();
        if (lstFornsProd.size()>0){
            prod.getFornecedores().addAll(lstFornsProd);
        }
    }
    
    /**
     * Ordena as listas de fornecedores.
     * @param l 
     * Lista a ser ordenada
     */
    private void ordenarLista(List<Fornecedor> l){
        Fornecedor obj;
        int idx= l.size();
        if (l.size() > 1){
            for (int i=0;i<idx-1;i++){
                for (int j=i+1;j<idx;j++){
                    if (l.get(i).toString().compareTo(l.get(j).toString())>0){
                        obj= l.get(i);
                        l.set(i, l.get(j));
                        l.set(j, obj);
                    }
                }
            }
        }
    }
   
    /**
     * @return the return status of this dialog - one of RET_OK or RET_CANCEL
     */
    public int getReturnStatus() {
        return returnStatus;
    }
    
    /**
     * Retorna um objeto Produto preenchido.
     * @return 
     */
    public Produto getProperties(){
        return produto;
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

        lstForns = new LinkedList<Fornecedor>();
        lstFornsProd = new LinkedList<Fornecedor>();
        lstCategoria = new LinkedList<Categoria>();
        lstUnidade = new LinkedList<Unidade>();
        jPopupMenu1 = new javax.swing.JPopupMenu();
        pupmnNvUnid = new javax.swing.JMenuItem();
        btnCancelar = new javax.swing.JButton();
        jtxtCodProduto = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jtxtDescProduto = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jtxtQtde = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jtxtQtdeMin = new javax.swing.JTextField();
        btnSalvar = new javax.swing.JButton();
        lblImg = new javax.swing.JLabel();
        btnRemoveUmForn = new javax.swing.JButton();
        btnRemoveTodosForns = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jlstFornsProd = new javax.swing.JList<Fornecedor>();
        jLabel14 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        btnAdiconaTodosForns = new javax.swing.JButton();
        btnAdicionaUmForn = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jlstForns = new javax.swing.JList<Fornecedor>();
        jcbxCateg = new javax.swing.JComboBox();
        jcbxUnid = new javax.swing.JComboBox();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jcbxStatus = new javax.swing.JComboBox();
        jLabel5 = new javax.swing.JLabel();

        lstForns= org.jdesktop.observablecollections.ObservableCollections.observableList(lstForns);

        lstFornsProd= org.jdesktop.observablecollections.ObservableCollections.observableList(lstFornsProd);

        lstCategoria= org.jdesktop.observablecollections.ObservableCollections.observableList(lstCategoria);

        lstUnidade= org.jdesktop.observablecollections.ObservableCollections.observableList(lstUnidade);

        pupmnNvUnid.setText("Nova unidade...");
        pupmnNvUnid.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pupmnNvUnidActionPerformed(evt);
            }
        });
        jPopupMenu1.add(pupmnNvUnid);

        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                closeDialog(evt);
            }
        });

        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        jtxtCodProduto.setEditable(false);
        jtxtCodProduto.setEnabled(false);

        jLabel1.setText("Código Produto");

        jLabel2.setText("Descrição do Produto");

        jLabel3.setText("Qtde estoque");

        jtxtQtde.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        jLabel4.setText("Qtde mínima");

        jtxtQtdeMin.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        btnSalvar.setText("Salvar");
        btnSalvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalvarActionPerformed(evt);
            }
        });

        lblImg.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ce/gui/images/Arquivo-Alterar3.jpg"))); // NOI18N
        lblImg.setText("lblImg");

        btnRemoveUmForn.setText("<");

        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, jlstFornsProd, org.jdesktop.beansbinding.ELProperty.create("${selectedElement!=null}"), btnRemoveUmForn, org.jdesktop.beansbinding.BeanProperty.create("enabled"));
        bindingGroup.addBinding(binding);

        btnRemoveUmForn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemoveUmFornActionPerformed(evt);
            }
        });

        btnRemoveTodosForns.setText("<<");

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, jlstFornsProd, org.jdesktop.beansbinding.ELProperty.create("${selectedElement!=null}"), btnRemoveTodosForns, org.jdesktop.beansbinding.BeanProperty.create("enabled"));
        bindingGroup.addBinding(binding);

        btnRemoveTodosForns.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemoveTodosFornsActionPerformed(evt);
            }
        });

        org.jdesktop.swingbinding.JListBinding jListBinding = org.jdesktop.swingbinding.SwingBindings.createJListBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, lstFornsProd, jlstFornsProd);
        bindingGroup.addBinding(jListBinding);

        jScrollPane2.setViewportView(jlstFornsProd);

        jLabel14.setText("Fornecedores do produto");

        jLabel13.setText("Lista de fornecedores");

        btnAdiconaTodosForns.setText(">>");

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, jlstForns, org.jdesktop.beansbinding.ELProperty.create("${selectedElement!=null}"), btnAdiconaTodosForns, org.jdesktop.beansbinding.BeanProperty.create("enabled"));
        bindingGroup.addBinding(binding);

        btnAdiconaTodosForns.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAdiconaTodosFornsActionPerformed(evt);
            }
        });

        btnAdicionaUmForn.setText(">");

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, jlstForns, org.jdesktop.beansbinding.ELProperty.create("${selectedElement!=null}"), btnAdicionaUmForn, org.jdesktop.beansbinding.BeanProperty.create("enabled"));
        bindingGroup.addBinding(binding);

        btnAdicionaUmForn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAdicionaUmFornActionPerformed(evt);
            }
        });

        jListBinding = org.jdesktop.swingbinding.SwingBindings.createJListBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, lstForns, jlstForns);
        bindingGroup.addBinding(jListBinding);

        jScrollPane1.setViewportView(jlstForns);

        org.jdesktop.swingbinding.JComboBoxBinding jComboBoxBinding = org.jdesktop.swingbinding.SwingBindings.createJComboBoxBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, lstCategoria, jcbxCateg);
        bindingGroup.addBinding(jComboBoxBinding);

        jcbxUnid.setComponentPopupMenu(jPopupMenu1);

        jComboBoxBinding = org.jdesktop.swingbinding.SwingBindings.createJComboBoxBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, lstUnidade, jcbxUnid);
        bindingGroup.addBinding(jComboBoxBinding);

        jLabel8.setText("Categoria");

        jLabel9.setText("Unidade");

        jcbxStatus.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Normal", "BLOQUEADO" }));

        jLabel5.setText("Situação");

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
                                .addComponent(jLabel1)
                                .addGap(71, 71, 71)
                                .addComponent(jLabel8))
                            .addComponent(jLabel2)
                            .addComponent(jtxtDescProduto, javax.swing.GroupLayout.PREFERRED_SIZE, 366, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addGap(43, 43, 43)
                                .addComponent(jLabel4)
                                .addGap(61, 61, 61)
                                .addComponent(jLabel9))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jtxtCodProduto, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(62, 62, 62)
                                .addComponent(jcbxCateg, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(186, 186, 186)
                        .addComponent(lblImg, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel13)
                        .addGap(179, 179, 179)
                        .addComponent(jLabel14))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(6, 6, 6)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btnAdicionaUmForn, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnRemoveUmForn, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnAdiconaTodosForns)
                                    .addComponent(btnRemoveTodosForns))
                                .addGap(6, 6, 6)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(jtxtQtde, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jtxtQtdeMin, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(26, 26, 26)
                                .addComponent(jcbxUnid, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(26, 26, 26)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel5)
                                    .addComponent(jcbxStatus, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                        .addGap(34, 34, 34)
                        .addComponent(btnSalvar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCancelar))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel8))
                        .addGap(6, 6, 6)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jtxtCodProduto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jcbxCateg, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(11, 11, 11)
                        .addComponent(jLabel2)
                        .addGap(6, 6, 6)
                        .addComponent(jtxtDescProduto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(11, 11, 11)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel9)
                                .addComponent(jLabel5))))
                    .addComponent(lblImg, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jtxtQtde, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jtxtQtdeMin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jcbxUnid, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jcbxStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel13)
                    .addComponent(jLabel14))
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnAdicionaUmForn)
                        .addGap(18, 18, 18)
                        .addComponent(btnRemoveUmForn)
                        .addGap(20, 20, 20)
                        .addComponent(btnAdiconaTodosForns)
                        .addGap(18, 18, 18)
                        .addComponent(btnRemoveTodosForns))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(125, 125, 125)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnSalvar)
                            .addComponent(btnCancelar))))
                .addContainerGap(16, Short.MAX_VALUE))
        );

        getRootPane().setDefaultButton(btnSalvar);

        bindingGroup.bind();

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
     /**
     * Salva as alterações ou o novo Produto.
     */
    private void salvar() {                                          
        
    }                              
       /**
     * Cancela a operação de inclusão ou alteração
     * @param evt 
     */   
    
    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        doClose(RET_CANCEL);
    }//GEN-LAST:event_btnCancelarActionPerformed

    /**
     * Closes the dialog
     */
    private void closeDialog(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_closeDialog
        doClose(RET_CANCEL);
    }//GEN-LAST:event_closeDialog

    private void btnRemoveUmFornActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoveUmFornActionPerformed
        int idx;
        if (lstFornsProd.size() > 0) {
            if (jlstFornsProd.isSelectionEmpty()) {
                idx = lstFornsProd.size() - 1;
            } else {
                idx = jlstFornsProd.getSelectedIndex();
            }
            lstForns.add(lstFornsProd.get(idx));
            ordenarLista(lstForns);
            jlstForns.setSelectedIndex(lstForns.size() - 1);
            lstFornsProd.remove(idx);
            if (lstFornsProd.size() > 0) {
                ordenarLista(lstFornsProd);
                jlstFornsProd.setSelectedIndex(lstFornsProd.size() - 1);
            }
        }
    }//GEN-LAST:event_btnRemoveUmFornActionPerformed

    private void btnRemoveTodosFornsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoveTodosFornsActionPerformed
        lstForns.addAll(lstFornsProd);
        ordenarLista(lstForns);
        lstFornsProd.clear();
        jlstForns.setSelectedIndex(lstForns.size() - 1);
    }//GEN-LAST:event_btnRemoveTodosFornsActionPerformed

    private void btnAdiconaTodosFornsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAdiconaTodosFornsActionPerformed
        lstFornsProd.addAll(lstForns);
        ordenarLista(lstFornsProd);
        lstForns.clear();
        jlstFornsProd.setSelectedIndex(lstFornsProd.size() - 1);
    }//GEN-LAST:event_btnAdiconaTodosFornsActionPerformed

    private void btnAdicionaUmFornActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAdicionaUmFornActionPerformed
        lstFornsProd.add(lstForns.get(jlstForns.getSelectedIndex()));
        lstForns.remove(jlstForns.getSelectedIndex());
        if (lstFornsProd.size() > 0) {
            ordenarLista(lstFornsProd);
            jlstFornsProd.setSelectedIndex(lstFornsProd.size() - 1);
        }
        if (lstForns.size() > 0) {
            ordenarLista(lstForns);
            jlstForns.setSelectedIndex(lstForns.size() - 1);
        }
    }//GEN-LAST:event_btnAdicionaUmFornActionPerformed

    private void btnSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalvarActionPerformed
        produto = new Produto();
        setProduto(produto);
        try{
            if (isIns){
                f.incluir(produto);
            }else{
                f.alterar(produto);
            }
            doClose(RET_OK);
        }catch(GeralException ex){
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }//GEN-LAST:event_btnSalvarActionPerformed

    private void pupmnNvUnidActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pupmnNvUnidActionPerformed
        Unidade unid= new Unidade();
        Unidade tmpUnid= new Unidade();
        unid.setDescricao(JOptionPane.showInputDialog("Digite a descricação da nova unidade"));
        try{
            f.incluir(unid);
            tmpUnid.setCodUnid(lstUnidade.get(jcbxUnid.getSelectedIndex()).getCodUnid());
            tmpUnid.setDescricao(lstUnidade.get(jcbxUnid.getSelectedIndex()).getDescricao());
            lstUnidade.clear();
            lstUnidade.addAll(f.listarUnidade());
            for(int i=0;i<lstUnidade.size();i++){
                if (lstUnidade.get(i).getCodUnid() == tmpUnid.getCodUnid()){
                    jcbxUnid.setSelectedIndex(i);
                    break;
                }
            }
        }catch(GeralException ex){
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }//GEN-LAST:event_pupmnNvUnidActionPerformed
    
    
    /**
     * Fecha a janela de diálogo.
     * @param retStatus 
     */
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
            java.util.logging.Logger.getLogger(PropProduto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PropProduto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PropProduto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PropProduto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                PropProduto dialog = new PropProduto(new javax.swing.JFrame(), true, null);
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
    private javax.swing.JButton btnAdicionaUmForn;
    private javax.swing.JButton btnAdiconaTodosForns;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnRemoveTodosForns;
    private javax.swing.JButton btnRemoveUmForn;
    private javax.swing.JButton btnSalvar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JComboBox jcbxCateg;
    private javax.swing.JComboBox jcbxStatus;
    private javax.swing.JComboBox jcbxUnid;
    private javax.swing.JList<Fornecedor> jlstForns;
    private javax.swing.JList<Fornecedor> jlstFornsProd;
    private javax.swing.JTextField jtxtCodProduto;
    private javax.swing.JTextField jtxtDescProduto;
    private javax.swing.JTextField jtxtQtde;
    private javax.swing.JTextField jtxtQtdeMin;
    private javax.swing.JLabel lblImg;
    private java.util.List<Categoria> lstCategoria;
    private java.util.List<Fornecedor> lstForns;
    private java.util.List<Fornecedor> lstFornsProd;
    private java.util.List<Unidade> lstUnidade;
    private javax.swing.JMenuItem pupmnNvUnid;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables
    private int returnStatus = RET_CANCEL;
}
