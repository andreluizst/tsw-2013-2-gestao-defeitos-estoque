/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ce.gui;

import ce.erro.GeralException;
import ce.model.fachada.Fachada;
import java.beans.PropertyVetoException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JInternalFrame;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

/**
 *
 * @author Andre
 */
public class MainMDIApplication extends javax.swing.JFrame {
    private static JInternalFrame activeWindow;
    private Fachada f;
    private Resource res;
    private ImageIcon fundo;
    private List<JInternalFrame> janelas;
    private boolean userIsAdm;
    
    /**
     * Creates new form MainMDIApplication
     */
    public MainMDIApplication() {
        initComponents();
        res= Resource.getInstancia();
        lblImgShell.setVisible(false);
        try {
            fundo= res.get("\\images\\Fundo4.jpg");
        } catch (GeralException ex) {
            fundo=null;
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        janelas= new ArrayList();
        f= Fachada.getInstancia();
        userIsAdm= f.getUser().getPerfil().getNome().toLowerCase().compareTo("administrador") == 0;
        miPerfil.setVisible(userIsAdm);
        miUsuario.setVisible(userIsAdm);
        mnUser.setText(f.getUser().getNome());
        miUnidade.setVisible(false);
    }
    
    /**
     * Registra a janela (JInternalFrame) que está sendo criado e vincula a mesma
     * a um item do menu janela
     * @param janela 
     * Janela que está sendo criada
     */
    public void registrarJanela(JInternalFrame janela, JMenuItem openedMenu){
        String sTexto="";
        if (openedMenu != null){
            openedMenu.setName("abrir"+((IActionsGui)janela).getActivationName());
            openedMenu.setEnabled(false);
        }
        janelas.add(janela);
        JMenuItem novoMenuItem= new JMenuItem();
        sTexto= ((IActionsGui)janela).getActivationName();
        novoMenuItem.setName("mi"+sTexto);
        novoMenuItem.setText(sTexto);
        novoMenuItem.setMnemonic(sTexto.charAt(0));
        novoMenuItem.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                activeWindow(evt);
            }
        });
        mnJanela.add(novoMenuItem);
        desktopPane.add(janela, javax.swing.JLayeredPane.DEFAULT_LAYER);
        janela.setVisible(true);
        setActiveWindow(janela);
        atlzMenu();
    }
    
    /*
    private void ordMenuJanela(){
        Integer j=0;
        String s;
        for (int i=3;i<mnJanela.getItemCount();i++){
            j= i-1;
            s= j.toString();
            mnJanela.getItem(i).setText(j.toString() + " " 
                    +((IActionsGui)janelas.get(j-1)).getActivationName());
            mnJanela.getItem(i).setMnemonic(s.charAt(0));
        }
    }*/
    
    /**
     * Desregistra uma janela (JInternalFrame) da aplicação. Também remove o
     * item do menu janela.
     * @param janela 
     * Janela que esta sendo fechada e destruida
     */
    public void desregistrarJanela(JInternalFrame janela){
        for(int i=0;i<janelas.size();i++){
            if (janelas.get(i).getTitle().compareTo(janela.getTitle()) == 0){
                janelas.remove(i);
                activeLastWindow();
                break;
            }
        }
        /*Deve-se começar do index 3 porque dá erro inesplicável (bug) ao tentar
         * ler o index 2 que é um Separator.
         * 
         */
        try{
            for(int i=3;i<mnJanela.getItemCount();i++){
                if (mnJanela.getItem(i).getText().contains(((IActionsGui)janela).getActivationName())){
                    mnJanela.remove(i);
                    break;
                }
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "Erro ao remover submenu do menu Janela");
        }
        try{
            for(int j=0;j<miAbrir.getItemCount();j++){
                if (miAbrir.getItem(j).getText().contains(((IActionsGui)janela).getActivationName())){
                    miAbrir.getItem(j).setEnabled(true);
                    break;
                }
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "Erro reabilitar submenu do submenu Abrir");
        }
        atlzMenu();
    }
    /**
     * Ativa a janela criada e registrada com o activationName especificado
     * @param activationName 
     * Nome de ativação da janela registrada, também é o texo do item do
     * menu janela
     */
    private void activeWindow(String activationName){
        //JOptionPane.showMessageDialog(null, "activeWindows(String activationName): " + activationName);
        JInternalFrame jifJanela;
        for(int i=0;i<janelas.size();i++){
            if (((IActionsGui)janelas.get(i)).getActivationName().compareTo(activationName) == 0){
                janelas.get(i).setVisible(false);
                janelas.get(i).setVisible(true);
                jifJanela= janelas.get(i);
                janelas.remove(i);
                janelas.add(jifJanela);
                break;
            }
        }
    }
    
    /**
     * Activa a próxima janela
     */
    private void activeNextWindow(){
        if (janelas.size() > 1){
            activeWindow(((IActionsGui)janelas.get(0)).getActivationName());
        }
    }
    
    /**
     * Ativa a última janela registrada
     */
    private void activeLastWindow(){
        if (janelas.size() > 0){
            janelas.get(janelas.size()-1).setVisible(false);
            janelas.get(janelas.size()-1).setVisible(true);
        }
    }
    
    /**
     * 
     * @param evt 
     */
    private void activeWindow(java.awt.event.ActionEvent evt){
        activeWindow(evt.getActionCommand());
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        desktopPane = new javax.swing.JDesktopPane();
        lblImgShell = new javax.swing.JLabel();
        menuBar = new javax.swing.JMenuBar();
        mnArquivo = new javax.swing.JMenu();
        miAbrir = new javax.swing.JMenu();
        miPerfil = new javax.swing.JMenuItem();
        miUsuario = new javax.swing.JMenuItem();
        miFuncionario = new javax.swing.JMenuItem();
        miCategoria = new javax.swing.JMenuItem();
        miProduto = new javax.swing.JMenuItem();
        miFornecedor = new javax.swing.JMenuItem();
        miLocalE = new javax.swing.JMenuItem();
        miEntrada = new javax.swing.JMenuItem();
        miSaida = new javax.swing.JMenuItem();
        miUnidade = new javax.swing.JMenuItem();
        jSeparator4 = new javax.swing.JPopupMenu.Separator();
        miSair = new javax.swing.JMenuItem();
        mnEditar = new javax.swing.JMenu();
        mnListar = new javax.swing.JMenuItem();
        mnPesquisar = new javax.swing.JMenuItem();
        jSeparator3 = new javax.swing.JPopupMenu.Separator();
        mnNovo = new javax.swing.JMenuItem();
        mnAlterar = new javax.swing.JMenuItem();
        mnExcluir = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        mnIrPara = new javax.swing.JMenu();
        mnPrimeiro = new javax.swing.JMenuItem();
        mnAnterior = new javax.swing.JMenuItem();
        mnProximo = new javax.swing.JMenuItem();
        mnUltimo = new javax.swing.JMenuItem();
        mnJanela = new javax.swing.JMenu();
        jmnFecharAtual = new javax.swing.JMenuItem();
        jmnProximaJanela = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        mnUser = new javax.swing.JMenu();
        miUserAlterarSenha = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Sistema simples de estoque");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                MainMDIApplicationOpened(evt);
            }
        });

        desktopPane.setBackground(new java.awt.Color(105, 105, 105));
        desktopPane.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                desktopPaneResized(evt);
            }
        });

        lblImgShell.setText("lblImgShell");
        lblImgShell.setBounds(10, 10, 60, 14);
        desktopPane.add(lblImgShell, javax.swing.JLayeredPane.DEFAULT_LAYER);

        mnArquivo.setMnemonic('a');
        mnArquivo.setText("Arquivo");

        miAbrir.setMnemonic('a');
        miAbrir.setText("Abrir");
        miAbrir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miAbrirActionPerformed(evt);
            }
        });

        miPerfil.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_P, java.awt.event.InputEvent.ALT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        miPerfil.setMnemonic('p');
        miPerfil.setText("Perfil");
        miPerfil.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miPerfilActionPerformed(evt);
            }
        });
        miAbrir.add(miPerfil);

        miUsuario.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_U, java.awt.event.InputEvent.ALT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        miUsuario.setMnemonic('u');
        miUsuario.setText("Usuário");
        miUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miUsuarioActionPerformed(evt);
            }
        });
        miAbrir.add(miUsuario);

        miFuncionario.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.ALT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        miFuncionario.setMnemonic('o');
        miFuncionario.setText("Funcionário");
        miFuncionario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miFuncionarioActionPerformed(evt);
            }
        });
        miAbrir.add(miFuncionario);

        miCategoria.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_C, java.awt.event.InputEvent.ALT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        miCategoria.setMnemonic('c');
        miCategoria.setText("Categoria");
        miCategoria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miCategoriaActionPerformed(evt);
            }
        });
        miAbrir.add(miCategoria);

        miProduto.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_D, java.awt.event.InputEvent.ALT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        miProduto.setMnemonic('d');
        miProduto.setText("Produto");
        miProduto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miProdutoActionPerformed(evt);
            }
        });
        miAbrir.add(miProduto);

        miFornecedor.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F, java.awt.event.InputEvent.ALT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        miFornecedor.setMnemonic('f');
        miFornecedor.setText("Fornecedor");
        miFornecedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miFornecedorActionPerformed(evt);
            }
        });
        miAbrir.add(miFornecedor);

        miLocalE.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_L, java.awt.event.InputEvent.ALT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        miLocalE.setMnemonic('l');
        miLocalE.setText("Local de estoque");
        miLocalE.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miLocalEActionPerformed(evt);
            }
        });
        miAbrir.add(miLocalE);

        miEntrada.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_E, java.awt.event.InputEvent.ALT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        miEntrada.setMnemonic('e');
        miEntrada.setText("Entrada");
        miEntrada.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miEntradaActionPerformed(evt);
            }
        });
        miAbrir.add(miEntrada);

        miSaida.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.ALT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        miSaida.setMnemonic('s');
        miSaida.setText("Saída");
        miSaida.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miSaidaActionPerformed(evt);
            }
        });
        miAbrir.add(miSaida);

        miUnidade.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_I, java.awt.event.InputEvent.ALT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        miUnidade.setMnemonic('i');
        miUnidade.setText("Unidade");
        miAbrir.add(miUnidade);

        mnArquivo.add(miAbrir);
        mnArquivo.add(jSeparator4);

        miSair.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F12, java.awt.event.InputEvent.CTRL_MASK));
        miSair.setMnemonic('s');
        miSair.setText("Sair");
        miSair.setName("miSair"); // NOI18N
        miSair.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miSairActionPerformed(evt);
            }
        });
        mnArquivo.add(miSair);
        miSair.getAccessibleContext().setAccessibleDescription("");

        menuBar.add(mnArquivo);

        mnEditar.setMnemonic('e');
        mnEditar.setText("Editar");
        mnEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnEditarActionPerformed(evt);
            }
        });

        mnListar.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_L, java.awt.event.InputEvent.CTRL_MASK));
        mnListar.setMnemonic('l');
        mnListar.setText("Listar/Atualizar");
        mnListar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnListarActionPerformed(evt);
            }
        });
        mnEditar.add(mnListar);

        mnPesquisar.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F5, 0));
        mnPesquisar.setMnemonic('p');
        mnPesquisar.setText("Pesquisar");
        mnPesquisar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnPesquisarActionPerformed(evt);
            }
        });
        mnEditar.add(mnPesquisar);
        mnEditar.add(jSeparator3);

        mnNovo.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_INSERT, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        mnNovo.setMnemonic('n');
        mnNovo.setText("Novo...");
        mnNovo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnNovoActionPerformed(evt);
            }
        });
        mnEditar.add(mnNovo);

        mnAlterar.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_A, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        mnAlterar.setMnemonic('a');
        mnAlterar.setText("Alterar...");
        mnAlterar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnAlterarActionPerformed(evt);
            }
        });
        mnEditar.add(mnAlterar);

        mnExcluir.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_DELETE, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        mnExcluir.setMnemonic('e');
        mnExcluir.setText("Excluir");
        mnExcluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnExcluirActionPerformed(evt);
            }
        });
        mnEditar.add(mnExcluir);
        mnEditar.add(jSeparator2);

        mnIrPara.setMnemonic('i');
        mnIrPara.setText("Ir para");

        mnPrimeiro.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_UP, java.awt.event.InputEvent.ALT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        mnPrimeiro.setMnemonic('p');
        mnPrimeiro.setText("Primeiro");
        mnPrimeiro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnPrimeiroActionPerformed(evt);
            }
        });
        mnIrPara.add(mnPrimeiro);

        mnAnterior.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_LEFT, java.awt.event.InputEvent.ALT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        mnAnterior.setMnemonic('a');
        mnAnterior.setText("Anterior");
        mnAnterior.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnAnteriorActionPerformed(evt);
            }
        });
        mnIrPara.add(mnAnterior);

        mnProximo.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_RIGHT, java.awt.event.InputEvent.ALT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        mnProximo.setMnemonic('p');
        mnProximo.setText("Próximo");
        mnProximo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnProximoActionPerformed(evt);
            }
        });
        mnIrPara.add(mnProximo);

        mnUltimo.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_DOWN, java.awt.event.InputEvent.ALT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        mnUltimo.setMnemonic('m');
        mnUltimo.setText("Último");
        mnUltimo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnUltimoActionPerformed(evt);
            }
        });
        mnIrPara.add(mnUltimo);

        mnEditar.add(mnIrPara);

        menuBar.add(mnEditar);

        mnJanela.setMnemonic('j');
        mnJanela.setText("Janela");

        jmnFecharAtual.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F4, java.awt.event.InputEvent.CTRL_MASK));
        jmnFecharAtual.setMnemonic('f');
        jmnFecharAtual.setText("Fechar");
        jmnFecharAtual.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmnFecharAtualActionPerformed(evt);
            }
        });
        mnJanela.add(jmnFecharAtual);

        jmnProximaJanela.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_PAGE_DOWN, java.awt.event.InputEvent.CTRL_MASK));
        jmnProximaJanela.setText("Próxima");
        jmnProximaJanela.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmnProximaJanelaActionPerformed(evt);
            }
        });
        mnJanela.add(jmnProximaJanela);
        mnJanela.add(jSeparator1);

        menuBar.add(mnJanela);

        mnUser.setText("UsuarioLogado");

        miUserAlterarSenha.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F10, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        miUserAlterarSenha.setMnemonic('a');
        miUserAlterarSenha.setText("Alterar senha");
        miUserAlterarSenha.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miUserAlterarSenhaActionPerformed(evt);
            }
        });
        mnUser.add(miUserAlterarSenha);

        menuBar.add(mnUser);

        setJMenuBar(menuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(desktopPane, javax.swing.GroupLayout.DEFAULT_SIZE, 987, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(desktopPane, javax.swing.GroupLayout.DEFAULT_SIZE, 505, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void miSairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miSairActionPerformed
        System.exit(0);
    }//GEN-LAST:event_miSairActionPerformed

    private void desktopPaneResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_desktopPaneResized
        atlzFundo();
    }//GEN-LAST:event_desktopPaneResized

    private void MainMDIApplicationOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_MainMDIApplicationOpened
        atlzFundo();
        atlzMenu();
    }//GEN-LAST:event_MainMDIApplicationOpened

    private void miProdutoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miProdutoActionPerformed
        JIFProduto jifProduto= new JIFProduto();
        registrarJanela(jifProduto, miProduto);
         try {
            jifProduto.setMaximum(true);
        } catch (PropertyVetoException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }//GEN-LAST:event_miProdutoActionPerformed

    private void miAbrirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miAbrirActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_miAbrirActionPerformed

    private void miCategoriaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miCategoriaActionPerformed
        CategoriaJif jifCategoria= new CategoriaJif();
        registrarJanela(jifCategoria, miCategoria);
    }//GEN-LAST:event_miCategoriaActionPerformed

    private void mnNovoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnNovoActionPerformed
        ((IActionsGui)activeWindow).novo();
    }//GEN-LAST:event_mnNovoActionPerformed

    private void mnExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnExcluirActionPerformed
        ((IActionsGui)activeWindow).excluir();
    }//GEN-LAST:event_mnExcluirActionPerformed

    private void mnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnEditarActionPerformed
        mnNovo.setEnabled(activeWindow!=null);
        mnExcluir.setEnabled(activeWindow!=null);
        mnAlterar.setEnabled(activeWindow!=null);
    }//GEN-LAST:event_mnEditarActionPerformed

    private void jmnFecharAtualActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmnFecharAtualActionPerformed
        if (activeWindow != null){
            activeWindow.doDefaultCloseAction();
        }
    }//GEN-LAST:event_jmnFecharAtualActionPerformed

    private void mnPrimeiroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnPrimeiroActionPerformed
        if (activeWindow != null){
            ((IActionsGui)activeWindow).firstRecord();
        }
    }//GEN-LAST:event_mnPrimeiroActionPerformed

    private void mnAnteriorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnAnteriorActionPerformed
        if (activeWindow != null){
            ((IActionsGui)activeWindow).priorRecord();
        }
    }//GEN-LAST:event_mnAnteriorActionPerformed

    private void mnProximoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnProximoActionPerformed
        if (activeWindow != null){
            ((IActionsGui)activeWindow).nextRecord();
        }
    }//GEN-LAST:event_mnProximoActionPerformed

    private void mnUltimoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnUltimoActionPerformed
        if (activeWindow != null){
            ((IActionsGui)activeWindow).lastRecord();
        }
    }//GEN-LAST:event_mnUltimoActionPerformed

    private void mnAlterarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnAlterarActionPerformed
        if (activeWindow != null){
            ((IActionsGui)activeWindow).alterar();
        }
    }//GEN-LAST:event_mnAlterarActionPerformed

    private void mnPesquisarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnPesquisarActionPerformed
        if (activeWindow != null){
            ((IActionsGui)activeWindow).pesquisar();
        }
    }//GEN-LAST:event_mnPesquisarActionPerformed

    private void miFuncionarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miFuncionarioActionPerformed
        JIFFuncionario jifFun= new JIFFuncionario();
        registrarJanela(jifFun, miFuncionario);
        try {
            jifFun.setMaximum(true);
        } catch (PropertyVetoException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }//GEN-LAST:event_miFuncionarioActionPerformed

    private void miPerfilActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miPerfilActionPerformed
        JIFPerfil jifPerfil= new JIFPerfil();
        registrarJanela(jifPerfil, miPerfil);
    }//GEN-LAST:event_miPerfilActionPerformed

    private void miUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miUsuarioActionPerformed
        JIFUsuario jifUsuario= new JIFUsuario();
        registrarJanela(jifUsuario, miUsuario);
    }//GEN-LAST:event_miUsuarioActionPerformed

    private void mnListarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnListarActionPerformed
        if (activeWindow != null){
            ((IActionsGui)activeWindow).listar();
        }
    }//GEN-LAST:event_mnListarActionPerformed

    private void miFornecedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miFornecedorActionPerformed
        JIFFornecedor jifFornecedor = new JIFFornecedor();
        registrarJanela(jifFornecedor, miFornecedor);
        try {
            jifFornecedor.setMaximum(true);
        } catch (PropertyVetoException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }//GEN-LAST:event_miFornecedorActionPerformed

    private void jmnProximaJanelaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmnProximaJanelaActionPerformed
        activeNextWindow();
    }//GEN-LAST:event_jmnProximaJanelaActionPerformed

    private void miLocalEActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miLocalEActionPerformed
        JIFLocalEstoque jifLocalEstoque= new JIFLocalEstoque();
        registrarJanela(jifLocalEstoque, miLocalE);
    }//GEN-LAST:event_miLocalEActionPerformed

    private void miEntradaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miEntradaActionPerformed
        JIFEntrada jifEntrada= new JIFEntrada();
        registrarJanela(jifEntrada, miEntrada);
        try {
            jifEntrada.setMaximum(true);
        } catch (PropertyVetoException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }//GEN-LAST:event_miEntradaActionPerformed

    private void miUserAlterarSenhaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miUserAlterarSenhaActionPerformed
        UsuarioAlterarSenha dlgAlterarSenha= new UsuarioAlterarSenha(null, true);
        dlgAlterarSenha.setLocationRelativeTo(null);
        dlgAlterarSenha.setVisible(true);
    }//GEN-LAST:event_miUserAlterarSenhaActionPerformed

    private void miSaidaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miSaidaActionPerformed
        JIFSaida jifSaida= new JIFSaida();
        registrarJanela(jifSaida, miSaida);
        try {
            jifSaida.setMaximum(true);
        } catch (PropertyVetoException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }//GEN-LAST:event_miSaidaActionPerformed

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
            java.util.logging.Logger.getLogger(MainMDIApplication.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainMDIApplication.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainMDIApplication.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainMDIApplication.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /*
         * Create and display the form
         */
        java.awt.EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                new MainMDIApplication().setVisible(true);
            }
        });
    }
    
    /**
     * Atualiza a imagem de fundo da aplicação
     */
    private void atlzFundo(){
        lblImgShell.setBounds(0, 0, this.getWidth(), this.getHeight());
        if (fundo != null){
            lblImgShell.setIcon(res.stretchImage(fundo, this.getWidth(), this.getHeight()));
            lblImgShell.setVisible(true);
        }
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JDesktopPane desktopPane;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JPopupMenu.Separator jSeparator3;
    private javax.swing.JPopupMenu.Separator jSeparator4;
    private javax.swing.JMenuItem jmnFecharAtual;
    private javax.swing.JMenuItem jmnProximaJanela;
    private javax.swing.JLabel lblImgShell;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JMenu miAbrir;
    private javax.swing.JMenuItem miCategoria;
    private javax.swing.JMenuItem miEntrada;
    private javax.swing.JMenuItem miFornecedor;
    private javax.swing.JMenuItem miFuncionario;
    private javax.swing.JMenuItem miLocalE;
    private javax.swing.JMenuItem miPerfil;
    private javax.swing.JMenuItem miProduto;
    private javax.swing.JMenuItem miSaida;
    private javax.swing.JMenuItem miSair;
    private javax.swing.JMenuItem miUnidade;
    private javax.swing.JMenuItem miUserAlterarSenha;
    private javax.swing.JMenuItem miUsuario;
    private javax.swing.JMenuItem mnAlterar;
    private javax.swing.JMenuItem mnAnterior;
    private javax.swing.JMenu mnArquivo;
    private javax.swing.JMenu mnEditar;
    private javax.swing.JMenuItem mnExcluir;
    private javax.swing.JMenu mnIrPara;
    private javax.swing.JMenu mnJanela;
    private javax.swing.JMenuItem mnListar;
    private javax.swing.JMenuItem mnNovo;
    private javax.swing.JMenuItem mnPesquisar;
    private javax.swing.JMenuItem mnPrimeiro;
    private javax.swing.JMenuItem mnProximo;
    private javax.swing.JMenuItem mnUltimo;
    private javax.swing.JMenu mnUser;
    // End of variables declaration//GEN-END:variables

    /**
     * Atribui a janela atualmente ativa para que a aplicação possa processar
     * as atualização e chamadas de menu para a janela ativa.
     * @param activeWindow the activeWindow to set
     */
    public static void setActiveWindow(JInternalFrame actvWin) {
        activeWindow = actvWin;
    }
    
    /**
     * Atualiza os menus da aplicação. Deve ser chamado sempre que uma janela
     * for criada, ativada ou destruida
     */
    public void atlzMenu(){
        mnListar.setEnabled(activeWindow!=null);
        mnPesquisar.setEnabled(activeWindow!=null);
        mnPesquisar.setVisible(activeWindow!=null?((IActionsGui)activeWindow).pesquisarExiste():true);
        mnNovo.setEnabled(activeWindow!=null);
        mnExcluir.setEnabled(activeWindow!=null);
        mnAlterar.setEnabled(activeWindow!=null);
        mnIrPara.setEnabled(activeWindow!=null);
        jmnFecharAtual.setEnabled(activeWindow!=null);
        jmnProximaJanela.setEnabled(activeWindow!=null?janelas.size()>1:false);
    }
}
