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
        super("Cadastro de Usuário");
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        JLabel userLabel = new JLabel("Novo Usuário:");
        JLabel passLabel = new JLabel("Nova Senha:");

        userField = new JTextField(15);
        passField = new JPasswordField(15);
        cadastrarButton = new JButton("Cadastrar");
        voltarButton = new JButton("Voltar");

        gbc.gridx = 0;
        gbc.gridy = 0;
        add(userLabel, gbc);

        gbc.gridx = 1;
        add(userField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        add(passLabel, gbc);

        gbc.gridx = 1;
        add(passField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        add(cadastrarButton, gbc);

        gbc.gridy = 3;
        add(voltarButton, gbc);

        cadastrarButton.addActionListener(e -> cadastrarUsuario());
        voltarButton.addActionListener(e -> {
            this.dispose();
            new Login(); // Retorna para a tela de login
        });

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
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

            // Verifica se o usuário já existe
            String verificaSQL = "SELECT * FROM usuarios WHERE usuario = ?";
            try (PreparedStatement psVerifica = conn.prepareStatement(verificaSQL)) {
                psVerifica.setString(1, usuario);
                ResultSet rs = psVerifica.executeQuery();
                if (rs.next()) {
                    JOptionPane.showMessageDialog(this, "Usuário já existe. Escolha outro nome.");
                    return;
                }
            }

            // Insere novo usuário
            String inserirSQL = "INSERT INTO usuarios (usuario, senha) VALUES (?, ?)";
            try (PreparedStatement psInsere = conn.prepareStatement(inserirSQL)) {
                psInsere.setString(1, usuario);
                psInsere.setString(2, senha);
                psInsere.executeUpdate();
                JOptionPane.showMessageDialog(this, "Cadastro realizado com sucesso!");
                this.dispose();
                new Login(); // Volta para login
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao cadastrar:\n" + e.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Cadastro());
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
