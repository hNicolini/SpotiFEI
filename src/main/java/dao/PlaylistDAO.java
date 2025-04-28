package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import model.Musicas;
import model.Playlist;
import model.Usuarios;

public class PlaylistDAO {
    private Connection conn;

    public PlaylistDAO(Connection conn) {
        this.conn = conn;
    }

public void inserir(Usuarios Usuarios , Musicas Musica) throws SQLException {
    String sql = "INSERT INTO Playlist(usuario, id_musica) VALUES (?, ?)";
    try (PreparedStatement statement = conn.prepareStatement(sql)) {
        statement.setString(1, Usuarios.getUsuario());
        statement.setInt(2, Musica.getId());
        statement.executeUpdate();
    }
}
public ResultSet consultar(String titulo) throws SQLException {
    PreparedStatement statement = conn.prepareStatement(
        "SELECT * FROM playlist WHERE usuario = ?",
        ResultSet.TYPE_FORWARD_ONLY,
        ResultSet.CONCUR_READ_ONLY);
    
    statement.setString(1, titulo);
    return statement.executeQuery();
}
}