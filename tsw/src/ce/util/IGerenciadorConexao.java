package ce.util;

import ce.erro.ConexaoException;
import java.sql.Connection;

/**
 *
 * @author professor
 */
public interface IGerenciadorConexao {
    public Connection conectar() throws ConexaoException;
    public Connection conectar(String nomeUsuario, String senha) throws ConexaoException;
    public void desconectar(Connection c) throws ConexaoException;
}
