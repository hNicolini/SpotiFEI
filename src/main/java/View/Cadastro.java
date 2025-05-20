package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class Cadastro extends JFrame {
    private JTextField userField;
    private JPasswordField passField;
    private JButton cadastrarButton;
    private JButton voltarButton;

    public Cadastro() {
        super("SpotiFEI - Cadastro");

        Color backgroundColor = new Color(18, 18, 18); // Preto
        Color greenColor = new Color(30, 215, 96);     // Verde Spotify
        Color textColor = Color.WHITE;

        Font font = new Font("SansSerif", Font.PLAIN, 16);

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel userLabel = new JLabel("Novo Usuário:");
        JLabel passLabel = new JLabel("Nova Senha:");
        userLabel.setForeground(textColor);
        passLabel.setForeground(textColor);
        userLabel.setFont(font);
        passLabel.setFont(font);

        userField = new JTextField(15);
        passField = new JPasswordField(15);
        cadastrarButton = new JButton("Cadastrar");
        voltarButton = new JButton("Voltar");

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

        styleButton(cadastrarButton, greenColor, backgroundColor, font);
        styleButton(voltarButton, greenColor, backgroundColor, font);

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
        add(cadastrarButton, gbc);

        gbc.gridy = 3;
        add(voltarButton, gbc);

        cadastrarButton.addActionListener(e -> cadastrarUsuario());
        voltarButton.addActionListener(e -> {
            this.dispose();
            new Login();
        });

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

    private void cadastrarUsuario() {
        String usuario = userField.getText().trim();
        String senha = new String(passField.getPassword()).trim();

        if (usuario.isEmpty() || senha.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha todos os campos.");
            return;
        }

        String url = "jdbc:postgresql://localhost:5432/SpotiFEI";
        String userDB = "postgres";
        String passwordDB = "hN@060106";

        try (Connection conn = DriverManager.getConnection(url, userDB, passwordDB)) {

            String verificaSQL = "SELECT * FROM usuarios WHERE usuario = ?";
            try (PreparedStatement psVerifica = conn.prepareStatement(verificaSQL)) {
                psVerifica.setString(1, usuario);
                ResultSet rs = psVerifica.executeQuery();
                if (rs.next()) {
                    JOptionPane.showMessageDialog(this, "Usuário já existe. Escolha outro nome.");
                    return;
                }
            }

            String inserirSQL = "INSERT INTO usuarios (usuario, senha) VALUES (?, ?)";
            try (PreparedStatement psInsere = conn.prepareStatement(inserirSQL)) {
                psInsere.setString(1, usuario);
                psInsere.setString(2, senha);
                psInsere.executeUpdate();
                JOptionPane.showMessageDialog(this, "Cadastro realizado com sucesso!");
                this.dispose();
                new Login();
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao cadastrar:\n" + e.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Cadastro::new);
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
