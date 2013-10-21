/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ce.gui;

/**
 *
 * @author Andre
 */
public interface IActionsGui {
    /**
     * Inclui um novo registro
     */
    public void novo();
    /**
     * Altera o registro selecionado
     */
    public void alterar();
    /**
     * Exclui o registro selecionado. Será exibida uma mensagem pedindo confirmação.
     */
    public void excluir();
    /**
     * Realiza uma presquisa de dados
     */
    public void pesquisar();
    /**
     * Lista todos os registros na tabela.
     */
    public void listar();
    /**
     * Seleciona o primeiro registro da tabela
     */
    public void firstRecord();
    /**
     * Seleciona o registro anterior ao atualmente selecionado na tabela.
     */
    public void priorRecord();
    /**
     * Seleciona o próximo registro da tabela.
     */
    public void nextRecord();
    /**
     * seleciona o último registro da tabela
     */
    public void lastRecord();
    /**
     * Informa o nome usado para ativar a janela após ser criada. Também pode ser
     * usado como texto do menu que executará a ação.
     * @return 
     */
    public String getActivationName();
    /**
     * Informa que o método pesquisar está implementado e disponível.
     * @return 
     */
    public boolean pesquisarExiste();
}
