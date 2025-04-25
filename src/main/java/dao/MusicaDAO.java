package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import model.Musicas;

public class MusicaDAO {
    private Connection conn;

    public MusicaDAO(Connection conn) {
        this.conn = conn;
    }

public void inserir(Musicas musica) throws SQLException {
    String sql = "INSERT INTO musicas(titulo, autor, genero) VALUES (?, ?, ?)";
    try (PreparedStatement statement = conn.prepareStatement(sql)) {
        statement.setString(1, musica.getTitulo());
        statement.setString(2, musica.getAutor());
        statement.setString(3, musica.getGenero());
        statement.executeUpdate();
    }
}
public ResultSet consultar(String titulo) throws SQLException {
    PreparedStatement statement = conn.prepareStatement(
        "SELECT * FROM musicas WHERE titulo = ?",
        ResultSet.TYPE_FORWARD_ONLY,
        ResultSet.CONCUR_READ_ONLY);
    
    statement.setString(1, titulo);
    return statement.executeQuery();
    // OBS: Quem chamar este método será responsável por fechar o Statement e ResultSet
}
}