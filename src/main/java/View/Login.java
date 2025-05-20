package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class Login extends JFrame {

    private JTextField userField;
    private JPasswordField passField;
    private JButton loginButton;
    private JButton cadastrarButton;

    public Login() {
        super("SpotiFEI - Login");

        Color backgroundColor = new Color(18, 18, 18);
        Color greenColor = new Color(30, 215, 96);  
        Color textColor = Color.WHITE;

        Font font = new Font("SansSerif", Font.PLAIN, 16);

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel userLabel = new JLabel("Usuário:");
        JLabel passLabel = new JLabel("Senha:");
        userLabel.setForeground(textColor);
        passLabel.setForeground(textColor);
        userLabel.setFont(font);
        passLabel.setFont(font);

        userField = new JTextField(15);
        passField = new JPasswordField(15);
        loginButton = new JButton("Entrar");
        cadastrarButton = new JButton("Cadastrar-se");

        userField.setBackground(Color.DARK_GRAY);
        userField.setForeground(textColor);
        userField.setCaretColor(textColor);
        userField.setBorder(BorderFactory.createLineBorder(greenColor));
        userField.setFont(font);

        passField.setBackground(Color.DARK_GRAY);
        passField.setForeground(textColor);
        passField.setCaretColor(textColor);
        passField.setBorder(BorderFactory.createLineBorder(greenColor));
        passField.setFont(font);

        styleButton(loginButton, greenColor, backgroundColor, font);
        styleButton(cadastrarButton, greenColor, backgroundColor, font);

        getContentPane().setBackground(backgroundColor);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.LINE_END;
        add(userLabel, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.LINE_START;
        add(userField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.LINE_END;
        add(passLabel, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.LINE_START;
        add(passField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(loginButton, gbc);

        gbc.gridy = 3;
        add(cadastrarButton, gbc);

        loginButton.addActionListener(e -> fazerLogin());
        cadastrarButton.addActionListener(e -> abrirCadastro());

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void styleButton(JButton button, Color fg, Color bg, Font font) {
        button.setBackground(bg);
        button.setForeground(fg);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(fg));
        button.setFont(font);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

    private void fazerLogin() {
        String usuario = userField.getText();
        String senha = new String(passField.getPassword());

        if (validaUsuario(usuario, senha)) {
            JOptionPane.showMessageDialog(this, "Login efetuado com sucesso!");
            this.dispose();
            new MainView();
        } else {
            JOptionPane.showMessageDialog(this, "Usuário ou senha incorretos!", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private boolean validaUsuario(String usuario, String senha) {
        String url = "jdbc:postgresql://localhost:5432/SpotiFEI";
        String userDB = "postgres";
        String passwordDB = "hN@060106";

        try (Connection conn = DriverManager.getConnection(url, userDB, passwordDB)) {
            String sql = "SELECT * FROM usuarios WHERE usuario = ? AND senha = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, usuario);
            ps.setString(2, senha);

            ResultSet rs = ps.executeQuery();
            return rs.next();

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void abrirCadastro() {
        this.dispose();
        new Cadastro();
    }

    public static void main(String[] args) {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Driver do PostgreSQL não encontrado.");
            System.exit(1);
        }

        SwingUtilities.invokeLater(Login::new);
    }


    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents



    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
