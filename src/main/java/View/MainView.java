package View;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class MainView extends javax.swing.JFrame {
    private JTable tabela;
    private DefaultTableModel modeloTabela;
    private JTextField campoPesquisa;

    public MainView() {
        setTitle("SpotiFEI");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel painelPrincipal = new JPanel();
        painelPrincipal.setLayout(new BoxLayout(painelPrincipal, BoxLayout.Y_AXIS));
        painelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titulo = new JLabel("SpotiFEI");
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        titulo.setFont(new Font("SansSerif", Font.BOLD, 24));
        titulo.setForeground(Color.green);
        painelPrincipal.add(titulo);
        painelPrincipal.add(Box.createRigidArea(new Dimension(0, 15)));

        JPanel painelPesquisa = new JPanel(new FlowLayout(FlowLayout.CENTER));
        campoPesquisa = new JTextField(20);
        JButton btnBuscar = new JButton("Buscar");
        btnBuscar.addActionListener(e -> buscarMusicas());

        painelPesquisa.add(new JLabel("Buscar título:"));
        painelPesquisa.add(campoPesquisa);
        painelPesquisa.add(btnBuscar);
        painelPrincipal.add(painelPesquisa);

        painelPrincipal.add(Box.createRigidArea(new Dimension(0, 10)));

        
        JButton btnCarregar = new JButton("Carregar Todas");
        btnCarregar.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnCarregar.addActionListener(e -> carregarMusicas());
        painelPrincipal.add(btnCarregar);
        painelPrincipal.add(Box.createRigidArea(new Dimension(0, 10)));

        
        String[] colunas = {"ID", "Título", "Autor", "Gênero"};
        modeloTabela = new DefaultTableModel(colunas, 0);
        tabela = new JTable(modeloTabela);
        JScrollPane scrollPane = new JScrollPane(tabela);
        scrollPane.setPreferredSize(new Dimension(700, 300));
        painelPrincipal.add(scrollPane);

        add(painelPrincipal);
        setVisible(true);
    }

    private void carregarMusicas() {
        campoPesquisa.setText(""); 
        modeloTabela.setRowCount(0);

        String url = "jdbc:postgresql://localhost:5432/SpotiFEI";
        String usuario = "postgres";
        String senha = "hN@060106";
        String sql = "SELECT id, titulo, autor, genero FROM musicas";

        try (Connection conn = DriverManager.getConnection(url, usuario, senha);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Object[] linha = {
                        rs.getInt("id"),
                        rs.getString("titulo"),
                        rs.getString("autor"),
                        rs.getString("genero")
                };
                modeloTabela.addRow(linha);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao acessar o banco:\n" + e.getMessage());
        }
    }

    private void buscarMusicas() {
        modeloTabela.setRowCount(0);
        String termoBusca = campoPesquisa.getText().trim();

        if (termoBusca.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Digite o nome da música para buscar.");
            return;
        }

        String url = "jdbc:postgresql://localhost:5432/SpotiFEI";
        String usuario = "postgres";
        String senha = "hN@060106";
        String sql = "SELECT id, titulo, autor, genero FROM musicas WHERE titulo ILIKE ?";

        try (Connection conn = DriverManager.getConnection(url, usuario, senha);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, "%" + termoBusca + "%");
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Object[] linha = {
                     rs.getInt("id"),
                     rs.getString("titulo"),
                     rs.getString("autor"),
                     rs.getString("genero")
                };
            modeloTabela.addRow(linha);
            }

            if (modeloTabela.getRowCount() == 0) {
                JOptionPane.showMessageDialog(this, "Nenhuma música encontrada com esse título.");
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao buscar no banco:\n" + e.getMessage());
        }
        

    }
    
    
//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(() -> new MainView().setVisible(true));
//    }

   
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 737, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 455, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

   

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
