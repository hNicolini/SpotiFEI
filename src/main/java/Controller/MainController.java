
package Controller;

import View.MainView;
import dao.MusicaDAO;
import dao.PlaylistDAO;
import dao.UsuariosDAO;
import java.sql.Connection;



public class MainController {
    private MainView view;
    private MusicaDAO musicaDAO;
    private UsuariosDAO usuarioDAO;
    private PlaylistDAO playlistDAO;
    private String usuarioLogado;
    
    public MainController(MainView view, Connection conn) {
        this.view = view;
        this.musicaDAO = new MusicaDAO(conn);
        this.usuarioDAO = new UsuariosDAO(conn);
        this.playlistDAO = new PlaylistDAO(conn);
        
        setupListeners();
    }
    
    private void setupListeners() {
        // Configurar todos os ActionListener aqui
        view.getLoginButton().addActionListener(e -> handleLogin());
        // ... outros listeners
    }
    
    private void handleLogin() {
        String usuario = view.getUsuarioField().getText();
        String senha = new String(view.getSenhaField().getPassword());
        
        try {
            ResultSet busca = usuarioDAO.consultar_login(usuario, senha);
            if (busca.next()) {
                usuarioLogado = usuario;
                view.showScreen("menuUsuario");
            } else {
                JOptionPane.showMessageDialog(view, "Login falhou");
            }
        } catch (SQLException ex) {
            // Tratar erro
        }
    }
    
    // ... outros métodos de controle
}
}
