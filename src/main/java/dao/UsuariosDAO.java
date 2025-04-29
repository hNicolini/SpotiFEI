package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import model.Musicas;
import model.Usuarios;

public class UsuariosDAO {
    private Connection conn;

    public UsuariosDAO(Connection conn) {
        this.conn = conn;
    }

public void inserir(Usuarios usuario) throws SQLException {
    String sql = "INSERT INTO usuarios(usuario,senha) VALUES (?, ?)";
    try (PreparedStatement statement = conn.prepareStatement(sql)) {
        statement.setString(1, usuario.getUsuario());
        statement.setString(2, usuario.getSenha());
        statement.executeUpdate();
    }
}
public ResultSet consultar(String titulo) throws SQLException {
    PreparedStatement statement = conn.prepareStatement(
        "SELECT * FROM usuarios WHERE usuario = ?",
        ResultSet.TYPE_FORWARD_ONLY,
        ResultSet.CONCUR_READ_ONLY);
    
    statement.setString(1, titulo);
    return statement.executeQuery();
}
    public ResultSet consultar_login(String usuario, String senha) throws SQLException {
        PreparedStatement statement = conn.prepareStatement(
            "SELECT * FROM usuarios WHERE usuario = ? and senha = ?",
            ResultSet.TYPE_FORWARD_ONLY,
            ResultSet.CONCUR_READ_ONLY);

        statement.setString(1, usuario);
        statement.setString(2, senha);
        return statement.executeQuery();
}
}