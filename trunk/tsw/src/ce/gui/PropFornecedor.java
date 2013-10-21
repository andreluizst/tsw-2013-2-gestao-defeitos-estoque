/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ce.gui;

import ce.erro.GeralException;
import ce.model.basica.Estado;
import ce.model.basica.Fornecedor;
import ce.model.basica.Produto;
import ce.model.fachada.Fachada;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import javax.swing.*;

/**
 *
 * @author Andre
 */
public class PropFornecedor extends javax.swing.JDialog {
    private Fachada f;
    private boolean isIns;
    private Resource res;
    private Fornecedor fornecedor;
    private List<Estado> estados;
    /**
     * A return status code - returned if Cancel button has been pressed
     */
    public static final int RET_CANCEL = 0;
    /**
     * A return status code - returned if OK button has been pressed
     */
    public static final int RET_OK = 1;

    /**
     * Construtor - Cria o diálogo de propriedades do Fornecedor para inclusão ou alteração
     * @param parent
     * @param modal
     * @param forn 
     * Objeto do tipo Fornecedor. Se null o diálogo iniciará em modo de inclusão,
     * caso contrário, iniciará em modo de alteração
     */
    public PropFornecedor(java.awt.Frame parent, boolean modal, Fornecedor forn) {
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
        f= Fachada.getInstancia();
        res= Resource.getInstancia();
        atlzEstados();
        if (forn == null){
            isIns= true;
            this.setTitle("INCLUIR fornecedor");
            try {
                lblImg.setIcon(res.get("\\images\\Arquivo-Novo.jpg", lblImg.getWidth(), lblImg.getHeight()));
            } catch (GeralException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage());
            }
            try {
                lstProdsNaoForn.addAll(f.listarProduto());
            } catch (GeralException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage());
            }
        }else{
            isIns= false;
            setFields(forn);
            this.setTitle("ALTERAR fornecedor");
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
     * Preenche os campos do formulário com os dados do objeto Fornecedor
     * @param forn 
     * Objeto do tipo Fornecedor cujos dados serão exibidos nos campos texto da tela.
     */
    private void setFields(Fornecedor forn){
        jtxtCod.setText(forn.getCodForn().toString());
        jftfCnpj.setText(forn.getCnpj());
        jtxtNome.setText(forn.getNome());
        jtxtLogradouro.setText(forn.getLogradouro());
        jtxtComp.setText(forn.getComp());
        jtxtNum.setText(((Integer)forn.getNum()).toString());
        jtxtBairro.setText(forn.getBairro());
        jtxtMunicipio.setText(forn.getMunicipio());
        if (jcbxEstados.getItemCount() > 0){
            for (int i=0;i<jcbxEstados.getItemCount();i++){
                if (jcbxEstados.getItemAt(i).getUf().compareTo(forn.getEstado().getUf()) == 0){
                    jcbxEstados.setSelectedIndex(i);
                    break;
                }
            }
        }
        
        jftfCep.setText(forn.getCep());
        jftfFone.setText(forn.getFone());
        jtxtEmail.setText(forn.getEmail());
        /*DefaultListModel<Produto> dlm= new DefaultListModel();
        for (Produto prod: forn.getProdutos()){
            dlm.addElement(prod);
        }
        jlstProdsForn.setModel(dlm);*/
        lstProdsForn.clear();
        lstProdsForn.addAll(forn.getProdutos());
        try {
            lstProdsNaoForn.addAll(f.pesquisarProdsQueNaoSaoDoForn(forn.getCodForn()));
        } catch (GeralException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        if (lstProdsNaoForn.size() > 0){
            jlstProdsNaoFornecidos.setSelectedIndex(0);
        }
        if (lstProdsForn.size()>0){
            jlstProdsForn.setSelectedIndex(0);
        }
    }
    
    /**
     * Preenche o objeto do tipo Fornecedor com os dados constantes nos campos
     * do formulário
     * @param forn 
     * Objeto do tipo Fornecedor que receberá os dados dos campos da tela.
     */
    private void setFornecedor(Fornecedor forn){
        String mask= "[()_./-]";
        if (!isIns){
            forn.setCodForn(Integer.parseInt(jtxtCod.getText()));
        }
        forn.setCnpj(jftfCnpj.getText().replaceAll(mask, ""));
        forn.setNome(jtxtNome.getText());
        forn.setLogradouro(jtxtLogradouro.getText());
        try{
            forn.setNum(Integer.parseInt(jtxtNum.getText()));
        }catch(Exception e){
            forn.setNum(0);
        }
        forn.setComp(jtxtComp.getText());
        forn.setBairro(jtxtBairro.getText());
        forn.setMunicipio(jtxtMunicipio.getText());
        forn.setEstado((Estado)jcbxEstados.getSelectedItem());
        forn.setCep(jftfCep.getText().replaceAll(mask, ""));
        forn.setFone(jftfFone.getText().replaceAll(mask, ""));
        forn.setEmail(jtxtEmail.getText());
        forn.getProdutos().clear();
        if (lstProdsForn.size()>0){
            forn.getProdutos().addAll(lstProdsForn);
        }
    }
    
    /**
     * Ordena as listas de produtos fornecidos e nao fornecidos
     * @param l 
     * Lista a ser ordenada
     */
    private void ordenarLista(List<Produto> l){
        Produto prod;
        int idx= l.size();
        if (l.size() > 1){
            for (int i=0;i<idx-1;i++){
                for (int j=i+1;j<idx;j++){
                    if (l.get(i).toString().compareTo(l.get(j).toString())>0){
                        prod= l.get(i);
                        l.set(i, l.get(j));
                        l.set(j, prod);
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
     * 
     * @return 
     * Retorna um objeto do tipo Fornecedor com as propriedades salvas.
     */
    public Fornecedor getProperties(){
        return fornecedor;
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

        lstProdsNaoForn = new LinkedList<Produto>();
        lstProdsForn = new LinkedList<Produto>();
        btnSalvar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        lblImg = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jtxtCod = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        javax.swing.text.MaskFormatter maskCnpj=null;
        try{
            maskCnpj= new javax.swing.text.MaskFormatter("##.###.###/####-##");
            maskCnpj.setPlaceholderCharacter('_');
        }catch(java.text.ParseException e){

        }
        jftfCnpj = new javax.swing.JFormattedTextField(maskCnpj);
        jLabel3 = new javax.swing.JLabel();
        jtxtNome = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jtxtLogradouro = new javax.swing.JTextField();
        jtxtNum = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jtxtComp = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jtxtBairro = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jtxtMunicipio = new javax.swing.JTextField();
        jcbxEstados = new javax.swing.JComboBox<Estado>();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        javax.swing.text.MaskFormatter maskCep=null;
        try{
            maskCep= new javax.swing.text.MaskFormatter("##.###-###");
            maskCep.setPlaceholderCharacter('_');
        }catch(java.text.ParseException e){

        }
        jftfCep = new javax.swing.JFormattedTextField(maskCep);
        javax.swing.text.MaskFormatter maskFone=null;
        try{
            maskFone= new javax.swing.text.MaskFormatter("(##)####-####");
            maskFone.setPlaceholderCharacter('_');
        }catch(java.text.ParseException e){

        }
        jftfFone = new javax.swing.JFormattedTextField(maskFone);
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jtxtEmail = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jlstProdsNaoFornecidos = new javax.swing.JList<Produto>();
        jLabel13 = new javax.swing.JLabel();
        btnAdicionaUmProd = new javax.swing.JButton();
        btnAdiconaTodosProds = new javax.swing.JButton();
        btnRemoveTodosProds = new javax.swing.JButton();
        btnRemoveUmProd = new javax.swing.JButton();
        jLabel14 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jlstProdsForn = new javax.swing.JList<Produto>();

        lstProdsNaoForn= org.jdesktop.observablecollections.ObservableCollections.observableList(lstProdsNaoForn);

        lstProdsForn= org.jdesktop.observablecollections.ObservableCollections.observableList(lstProdsForn);

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

        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        lblImg.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ce/gui/images/Arquivo-Alterar3.jpg"))); // NOI18N
        lblImg.setText("lblImg");

        jLabel1.setText("Código");

        jtxtCod.setEditable(false);
        jtxtCod.setEnabled(false);

        jLabel2.setText("CNPJ");

        jftfCnpj.setHorizontalAlignment(javax.swing.JFormattedTextField.RIGHT);

        jLabel3.setText("Nome (Razão social)");

        jLabel4.setText("Logradouro");

        jLabel5.setText("Número");

        jLabel6.setText("Complemento");

        jLabel7.setText("Bairro");

        jLabel8.setText("Município");

        jLabel9.setText("Estado");

        jLabel10.setText("CEP");

        jLabel11.setText("Fone");

        jLabel12.setText("E-mail");

        org.jdesktop.swingbinding.JListBinding jListBinding = org.jdesktop.swingbinding.SwingBindings.createJListBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, lstProdsNaoForn, jlstProdsNaoFornecidos, "");
        bindingGroup.addBinding(jListBinding);

        jScrollPane1.setViewportView(jlstProdsNaoFornecidos);

        jLabel13.setText("Lista de produtos não fornecidos");

        btnAdicionaUmProd.setText(">");

        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, jlstProdsNaoFornecidos, org.jdesktop.beansbinding.ELProperty.create("${selectedElement!=null}"), btnAdicionaUmProd, org.jdesktop.beansbinding.BeanProperty.create("enabled"));
        bindingGroup.addBinding(binding);

        btnAdicionaUmProd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAdicionaUmProdActionPerformed(evt);
            }
        });

        btnAdiconaTodosProds.setText(">>");

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, jlstProdsNaoFornecidos, org.jdesktop.beansbinding.ELProperty.create("${selectedElement!=null}"), btnAdiconaTodosProds, org.jdesktop.beansbinding.BeanProperty.create("enabled"));
        bindingGroup.addBinding(binding);

        btnAdiconaTodosProds.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAdiconaTodosProdsActionPerformed(evt);
            }
        });

        btnRemoveTodosProds.setText("<<");

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, jlstProdsForn, org.jdesktop.beansbinding.ELProperty.create("${selectedElement!=null}"), btnRemoveTodosProds, org.jdesktop.beansbinding.BeanProperty.create("enabled"));
        bindingGroup.addBinding(binding);

        btnRemoveTodosProds.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemoveTodosProdsActionPerformed(evt);
            }
        });

        btnRemoveUmProd.setText("<");

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, jlstProdsForn, org.jdesktop.beansbinding.ELProperty.create("${selectedElement!=null}"), btnRemoveUmProd, org.jdesktop.beansbinding.BeanProperty.create("enabled"));
        bindingGroup.addBinding(binding);

        btnRemoveUmProd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemoveUmProdActionPerformed(evt);
            }
        });

        jLabel14.setText("Produtos fornecidos");

        jListBinding = org.jdesktop.swingbinding.SwingBindings.createJListBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, lstProdsForn, jlstProdsForn);
        bindingGroup.addBinding(jListBinding);

        jScrollPane2.setViewportView(jlstProdsForn);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jtxtBairro, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jtxtMunicipio, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jcbxEstados, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addGap(109, 109, 109)
                        .addComponent(jLabel11)
                        .addGap(134, 134, 134)
                        .addComponent(jLabel12))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jftfCep, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jftfFone, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(21, 21, 21)
                        .addComponent(jtxtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 265, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addGap(113, 113, 113)
                                .addComponent(jLabel2))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jtxtCod, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(47, 47, 47)
                                .addComponent(jftfCnpj, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel3)
                            .addComponent(jtxtNome, javax.swing.GroupLayout.PREFERRED_SIZE, 370, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addGap(247, 247, 247)
                                .addComponent(jLabel5)
                                .addGap(29, 29, 29)
                                .addComponent(jLabel6))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jtxtLogradouro, javax.swing.GroupLayout.PREFERRED_SIZE, 292, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10)
                                .addComponent(jtxtNum, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10)
                                .addComponent(jtxtComp, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel7)
                                .addGap(169, 169, 169)
                                .addComponent(jLabel8)
                                .addGap(154, 154, 154)
                                .addComponent(jLabel9))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel13))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(btnAdicionaUmProd, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(btnAdiconaTodosProds)
                                        .addComponent(btnRemoveTodosProds))
                                    .addComponent(btnRemoveUmProd, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel14)
                                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(27, 27, 27)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnSalvar, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(6, 6, 6)
                                .addComponent(btnCancelar))
                            .addComponent(lblImg, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(11, 11, 11)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2))
                        .addGap(5, 5, 5)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jtxtCod, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jftfCnpj, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(11, 11, 11)
                        .addComponent(jLabel3)
                        .addGap(6, 6, 6)
                        .addComponent(jtxtNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(11, 11, 11)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6))
                        .addGap(6, 6, 6)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jtxtLogradouro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jtxtNum, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jtxtComp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(12, 12, 12)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7)
                            .addComponent(jLabel8)
                            .addComponent(jLabel9)))
                    .addComponent(lblImg, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jtxtBairro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jtxtMunicipio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jcbxEstados, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(11, 11, 11)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel10)
                            .addComponent(jLabel11)
                            .addComponent(jLabel12))
                        .addGap(6, 6, 6)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jftfCep, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jftfFone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jtxtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(14, 14, 14)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel13)
                            .addComponent(jLabel14))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnAdicionaUmProd)
                                .addGap(18, 18, 18)
                                .addComponent(btnRemoveUmProd)
                                .addGap(20, 20, 20)
                                .addComponent(btnAdiconaTodosProds)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 15, Short.MAX_VALUE)
                                .addComponent(btnRemoveTodosProds))
                            .addComponent(jScrollPane1)))
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnSalvar)
                            .addComponent(btnCancelar))))
                .addContainerGap())
        );

        getRootPane().setDefaultButton(btnSalvar);

        bindingGroup.bind();

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Salva as alterações ou o novo Fornecedor.
     * @param evt 
     */
    private void btnSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalvarActionPerformed
        fornecedor= new Fornecedor();
        setFornecedor(fornecedor);
        try{
            if (isIns){
                f.incluir(fornecedor);
            }else{
                f.alterar(fornecedor);
            }
            doClose(RET_OK);
        }catch(GeralException ex){
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }//GEN-LAST:event_btnSalvarActionPerformed
    
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
    
    /**
     * Adiciona um produto a lista de produtos fornecidos pelo fornecedor.
     * @param evt 
     */
    private void btnAdicionaUmProdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAdicionaUmProdActionPerformed
        lstProdsForn.add(lstProdsNaoForn.get(jlstProdsNaoFornecidos.getSelectedIndex()));
        lstProdsNaoForn.remove(jlstProdsNaoFornecidos.getSelectedIndex());
        if (lstProdsForn.size()>0){
            ordenarLista(lstProdsForn);
            jlstProdsForn.setSelectedIndex(lstProdsForn.size()-1);
        }
        if (lstProdsNaoForn.size()>0){
            ordenarLista(lstProdsNaoForn);
            jlstProdsNaoFornecidos.setSelectedIndex(lstProdsNaoForn.size()-1);
        }
    }//GEN-LAST:event_btnAdicionaUmProdActionPerformed
    
    /**
     * Remove um produto da lista de produtos fornecidos pelo fornecedor.
     * @param evt 
     */
    private void btnRemoveUmProdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoveUmProdActionPerformed
        int idx;
        if (lstProdsForn.size() > 0){
            if (jlstProdsForn.isSelectionEmpty()){
                idx= lstProdsForn.size()-1;
            }else{
                idx= jlstProdsForn.getSelectedIndex();
            }
            lstProdsNaoForn.add(lstProdsForn.get(idx));
            ordenarLista(lstProdsNaoForn);
            jlstProdsNaoFornecidos.setSelectedIndex(lstProdsNaoForn.size()-1);
            lstProdsForn.remove(idx);
            if (lstProdsForn.size()>0){
                ordenarLista(lstProdsForn);
                jlstProdsForn.setSelectedIndex(lstProdsForn.size()-1);
            }
        }
    }//GEN-LAST:event_btnRemoveUmProdActionPerformed
    
    /**
     * Adiciona todos os produtos a lista de produtos fornecidos pelo fornecedor.
     * @param evt 
     */
    private void btnAdiconaTodosProdsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAdiconaTodosProdsActionPerformed
        lstProdsForn.addAll(lstProdsNaoForn);
        ordenarLista(lstProdsForn);
        lstProdsNaoForn.clear();
        jlstProdsForn.setSelectedIndex(lstProdsForn.size()-1);
    }//GEN-LAST:event_btnAdiconaTodosProdsActionPerformed
    
    /**
     * Remove todos os produtos fornecidos pelo fornecedor.
     * @param evt 
     */
    private void btnRemoveTodosProdsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoveTodosProdsActionPerformed
        lstProdsNaoForn.addAll(lstProdsForn);
        ordenarLista(lstProdsNaoForn);
        lstProdsForn.clear();
        jlstProdsNaoFornecidos.setSelectedIndex(lstProdsNaoForn.size()-1);
    }//GEN-LAST:event_btnRemoveTodosProdsActionPerformed
    
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
        /*
         * Set the Nimbus look and feel
         */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /*
         * If Nimbus (introduced in Java SE 6) is not available, stay with the
         * default look and feel. For details see
         * http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(PropFornecedor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PropFornecedor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PropFornecedor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PropFornecedor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /*
         * Create and display the dialog
         */
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                PropFornecedor dialog = new PropFornecedor(new javax.swing.JFrame(), true, null);
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
    private javax.swing.JButton btnAdicionaUmProd;
    private javax.swing.JButton btnAdiconaTodosProds;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnRemoveTodosProds;
    private javax.swing.JButton btnRemoveUmProd;
    private javax.swing.JButton btnSalvar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JComboBox<Estado> jcbxEstados;
    private javax.swing.JFormattedTextField jftfCep;
    private javax.swing.JFormattedTextField jftfCnpj;
    private javax.swing.JFormattedTextField jftfFone;
    private javax.swing.JList<Produto> jlstProdsForn;
    private javax.swing.JList<Produto> jlstProdsNaoFornecidos;
    private javax.swing.JTextField jtxtBairro;
    private javax.swing.JTextField jtxtCod;
    private javax.swing.JTextField jtxtComp;
    private javax.swing.JTextField jtxtEmail;
    private javax.swing.JTextField jtxtLogradouro;
    private javax.swing.JTextField jtxtMunicipio;
    private javax.swing.JTextField jtxtNome;
    private javax.swing.JTextField jtxtNum;
    private javax.swing.JLabel lblImg;
    private java.util.List<Produto> lstProdsForn;
    private java.util.List<Produto> lstProdsNaoForn;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables
    private int returnStatus = RET_CANCEL;
}
