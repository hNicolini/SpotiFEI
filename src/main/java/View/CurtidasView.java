package View;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class CurtidasView extends JFrame {
    private JTable tabelaCurtidas;
    private DefaultTableModel modeloTabela;
    private String usuario;

    public CurtidasView(String usuario) {
        this.usuario = usuario;

        setTitle("Músicas Curtidas");
        setSize(700, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        Color backgroundColor = new Color(18, 18, 18);
        Color greenColor = new Color(30, 215, 96);
        Font fonteBase = new Font("SansSerif", Font.PLAIN, 16);
        JPanel painel = new JPanel();
        painel.setLayout(new BoxLayout(painel, BoxLayout.Y_AXIS));
        painel.setBackground(backgroundColor);
        painel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        String[] colunas = {"ID", "Título", "Autor"};
        modeloTabela = new DefaultTableModel(colunas, 0);
        tabelaCurtidas = new JTable(modeloTabela);
        tabelaCurtidas.setRowHeight(30);
        tabelaCurtidas.setBackground(backgroundColor);
        tabelaCurtidas.setForeground(Color.WHITE);
        tabelaCurtidas.setFont(fonteBase);
        tabelaCurtidas.getTableHeader().setBackground(greenColor);
        tabelaCurtidas.getTableHeader().setForeground(Color.BLACK);
        tabelaCurtidas.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 14));
        tabelaCurtidas.setSelectionBackground(new Color(50, 50, 50));
        tabelaCurtidas.setSelectionForeground(greenColor);
        tabelaCurtidas.setGridColor(greenColor);

        JScrollPane scroll = new JScrollPane(tabelaCurtidas);
        scroll.getViewport().setBackground(backgroundColor);
        scroll.setBorder(BorderFactory.createLineBorder(greenColor));
        scroll.setPreferredSize(new Dimension(660, 300));

        JButton btnDescurtir = new JButton("Descurtir");
        styleButton(btnDescurtir, greenColor, backgroundColor, fonteBase);
        btnDescurtir.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnDescurtir.addActionListener(e -> descurtirMusicaSelecionada());

        painel.add(scroll);
        painel.add(Box.createRigidArea(new Dimension(0, 10)));
        painel.add(btnDescurtir);

        add(painel);
        carregarCurtidas();
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

    private void carregarCurtidas() {
        modeloTabela.setRowCount(0); 

        String url = "jdbc:postgresql://localhost:5432/SpotiFEI";
        String usuarioDB = "postgres";
        String senha = "hN@060106";
        String sql = "SELECT id_musica, titulo, autor FROM curtidas WHERE usuario = ?";

        try (Connection conn = DriverManager.getConnection(url, usuarioDB, senha);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, usuario);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                modeloTabela.addRow(new Object[]{
                        rs.getInt("id_musica"),
                        rs.getString("titulo"),
                        rs.getString("autor")
                });
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar curtidas:\n" + e.getMessage());
        }
    }

    private void descurtirMusicaSelecionada() {
        int selectedRow = tabelaCurtidas.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Selecione uma música para descurtir.");
            return;
        }

        int idMusica = (int) modeloTabela.getValueAt(selectedRow, 0);

        String url = "jdbc:postgresql://localhost:5432/SpotiFEI";
        String usuarioDB = "postgres";
        String senha = "hN@060106";
        String sql = "DELETE FROM curtidas WHERE usuario = ? AND id_musica = ?";

        try (Connection conn = DriverManager.getConnection(url, usuarioDB, senha);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, usuario);
            pstmt.setInt(2, idMusica);

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                JOptionPane.showMessageDialog(this, "Música descurtida com sucesso.");
                carregarCurtidas();
            } else {
                JOptionPane.showMessageDialog(this, "Não foi possível descurtir a música.");
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao descurtir música:\n" + e.getMessage());
        }
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
