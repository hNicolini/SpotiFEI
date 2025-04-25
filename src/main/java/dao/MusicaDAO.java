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

    public void insertr(Musicas musica) throws SQLException {
        String sql = "INSERT INTO musicas(titulo,autor,genero) "
            + "values(\""+ musica.getTitulo() +"\", \""+ musica.getAutor() +"\""
            + ", \""+ musica.getGenero() +"\")";
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.execute();
        conn.close();
    }
    public ResultSet consultar(Musicas musica) throws SQLException {
    String sql = "SELECT * FROM musicas WHERE titulo = ?";
    PreparedStatement statement = conn.prepareStatement(sql);
    statement.setString(1, musica.getTitulo());
    statement.execute();
    ResultSet resultado = statement.getResultSet();
    conn.close();
    return resultado;
}
}