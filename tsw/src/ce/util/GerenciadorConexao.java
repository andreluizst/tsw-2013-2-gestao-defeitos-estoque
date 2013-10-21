package ce.util;

import ce.erro.ConexaoException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 *
 * @author professor / André
 */
public class GerenciadorConexao implements IGerenciadorConexao {
    private static IGerenciadorConexao instancia;
    private String driver;
    private String local;
    private String usuario;
    private String senha;
    
    
    /**
     * Construtor padrão
     * Ao executar carrega as configurações de conexão com o banco do arquivo
     * Banco.properties
     */
    private GerenciadorConexao(){
        super();
        ResourceBundle rp= ResourceBundle.getBundle("ce.util.Banco");
        driver = rp.getString("driver"); //"com.mysql.jdbc.Driver";
        local = rp.getString("local");//"jdbc:mysql://localhost:3306/estoque";
        usuario = rp.getString("usuario");//"root";
        senha = rp.getString("senha");//"root";
    }

    /**
     * Este método implementa do padrão singleton ao GerenciadorConexao, garantindo
     * que haja apenas uma instância do mesmo no sistema.
     * 
     * @return 
     * Retorna a interface do gerenciador de conexão
     */
    public static IGerenciadorConexao getInstancia(){
        if(instancia==null){
            instancia=new GerenciadorConexao();
        }
        return instancia;
    }

    /**
     * Estabelece uma conexão com o banco de dados usando os parâmetros de configuração
     * existentes no arquivo Banco.properties (incluindo o usuário e senha).
     * 
     * @return
     * Se o processo por bem sucedido retona uma conexão que foi estabelecida.
     * 
     * @throws ConexaoException 
     * Caso ocorra algum problema na execução do método será lançada a exceção ConexaoException
     */
    @Override
    public Connection conectar() throws ConexaoException{
        try{
            Class.forName(driver);
            Connection c = DriverManager.getConnection(local, usuario, senha);
            return c;
        }catch(  ClassNotFoundException | SQLException e){
            throw new ConexaoException(usuario, e,
                    GerenciadorConexao.class.getName()+".conectar()");
        }
    }

    /**
     * Estabelece uma conexão com o banco de dados usando os parâmetros de configuração
     * driver e local constantes no arquivo Banco.properties mas o usuário a senha
     * devem ser informados no momento da conexão
     * 
     * @param nomeUsuario
     * @param senha
     * @return
     * Se o processo por bem sucedido retona uma conexão que foi estabelecida.
     * @throws ConexaoException 
     * Caso ocorra algum problema na execução do método será lançada a exceção ConexaoException
     */
    @Override
    public Connection conectar(String nomeUsuario, String senha) 
            throws ConexaoException{
        try{
            Class.forName(driver);
            Connection c = DriverManager.getConnection(local, nomeUsuario, senha);
            this.usuario=nomeUsuario;
            this.senha=senha;
            return c;
        }catch(  ClassNotFoundException | SQLException e){
            throw new ConexaoException(nomeUsuario, e,
                    GerenciadorConexao.class.getName()+".conectar()");
        }
    }
    
    /**
     * Desconecta do banco
     * @param c
     * Conexão que está sendo usada para a comunicação com o banco
     * 
     * @throws ConexaoException 
     * Caso ocorra algum problema na execução do método será lançada a exceção ConexaoException
     */
    @Override
    public void desconectar(Connection c) throws ConexaoException{
        try{
            c.close();
        }catch(SQLException e){
            throw new ConexaoException(usuario, e,
                    GerenciadorConexao.class.getName()+".desconectar()");
        }
    }
    
    /**
     * 
     * @param driver
     * @param local
     * @param usuario
     * @param senha 
     */
    public void setConfig(String driver, String local, String usuario,
            String senha){
        this.driver=driver;
        this.local=local;
        this.usuario=usuario;
        this.senha=senha;
    }
    
    /**
     * 
     * @param userName
     * @param senha 
     */
    public void setLogin(String userName, String senha){
        this.usuario=userName;
        this.senha=senha;
    }
    
}
