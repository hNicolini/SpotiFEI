package View;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class MinhaPlaylistView extends JFrame {
    private JTable tabela;
    private DefaultTableModel modeloTabela;
    private final Color backgroundColor = new Color(18, 18, 18);
    private final Color greenColor = new Color(30, 215, 96);
    private final Color textColor = Color.WHITE;
    private final Font fonteBase = new Font("SansSerif", Font.PLAIN, 16);

    private String usuario;

    public MinhaPlaylistView(String usuario) {
        this.usuario = usuario;

        setTitle("SpotiFEI - Minha Playlist de " + usuario);
        setSize(750, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel painelPrincipal = new JPanel();
        painelPrincipal.setLayout(new BorderLayout());
        painelPrincipal.setBackground(backgroundColor);
        painelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titulo = new JLabel("🎧 Playlist de " + usuario);
        titulo.setFont(new Font("SansSerif", Font.BOLD, 24));
        titulo.setForeground(greenColor);
        titulo.setHorizontalAlignment(SwingConstants.CENTER);
        painelPrincipal.add(titulo, BorderLayout.NORTH);

        modeloTabela = new DefaultTableModel(new Object[]{"ID Música", "Título", "Autor", "Data Adicionado", "Ação"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 4;
            }
        };

        tabela = new JTable(modeloTabela);
        tabela.setRowHeight(35);
        tabela.setFont(fonteBase);
        tabela.setBackground(backgroundColor);
        tabela.setForeground(textColor);
        tabela.getTableHeader().setBackground(greenColor.darker());
        tabela.getTableHeader().setForeground(Color.BLACK);
        tabela.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 16));
        tabela.getColumn("Ação").setCellRenderer(new ButtonRenderer());
        tabela.getColumn("Ação").setCellEditor(new ButtonEditor(new JCheckBox()));

        JScrollPane scrollPane = new JScrollPane(tabela);
        scrollPane.setBackground(backgroundColor);
        painelPrincipal.add(scrollPane, BorderLayout.CENTER);

        add(painelPrincipal);

        carregarPlaylist();

        setVisible(true);
    }

    private void carregarPlaylist() {
        modeloTabela.setRowCount(0);
        String url = "jdbc:postgresql://localhost:5432/SpotiFEI";
        String usuarioDB = "postgres";
        String senhaDB = "hN@060106";
        String sql = "SELECT id_musica, titulo, autor, data_adicionado FROM playlist WHERE usuario = ? ORDER BY data_adicionado DESC";

        try (Connection conn = DriverManager.getConnection(url, usuarioDB, senhaDB);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, usuario);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                modeloTabela.addRow(new Object[]{
                        rs.getInt("id_musica"),
                        rs.getString("titulo"),
                        rs.getString("autor"),
                        rs.getTimestamp("data_adicionado"),
                        "Remover"
                });
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar playlist:\n" + e.getMessage());
        }
    }

    private void removerMusicaDaPlaylist(int idMusica) {
        int confirm = JOptionPane.showConfirmDialog(this,
                "Tem certeza que deseja remover esta música da sua playlist?",
                "Confirmar remoção",
                JOptionPane.YES_NO_OPTION);

        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        String url = "jdbc:postgresql://localhost:5432/SpotiFEI";
        String usuarioDB = "postgres";
        String senhaDB = "hN@060106";
        String sql = "DELETE FROM playlist WHERE usuario = ? AND id_musica = ?";

        try (Connection conn = DriverManager.getConnection(url, usuarioDB, senhaDB);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, usuario);
            pstmt.setInt(2, idMusica);

            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(this, "Música removida da playlist!");
                carregarPlaylist();
            } else {
                JOptionPane.showMessageDialog(this, "Música não encontrada na playlist.");
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao remover música:\n" + e.getMessage());
        }
    }


    class ButtonRenderer extends JButton implements TableCellRenderer {
        public ButtonRenderer() {
            setOpaque(true);
            setForeground(greenColor);
            setBackground(backgroundColor);
            setBorder(BorderFactory.createLineBorder(greenColor));
            setFont(fonteBase);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
        boolean isSelected, boolean hasFocus,
        int row, int column) {
            setText((value == null) ? "Remover" : value.toString());
            return this;
        }
    }

    class ButtonEditor extends DefaultCellEditor {
        private JButton button;
        private boolean isPushed;
        private int linhaSelecionada;

        public ButtonEditor(JCheckBox checkBox) {
            super(checkBox);
            button = new JButton();
            button.setOpaque(true);
            button.setForeground(greenColor);
            button.setBackground(backgroundColor);
            button.setBorder(BorderFactory.createLineBorder(greenColor));
            button.setFont(fonteBase);
            button.setFocusPainted(false);
            button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

            button.addActionListener(e -> fireEditingStopped());
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value,
                                                     boolean isSelected, int row, int column) {
            isPushed = true;
            linhaSelecionada = row;
            button.setText((value == null) ? "Remover" : value.toString());
            return button;
        }

        @Override
        public Object getCellEditorValue() {
            if (isPushed) {
                int idMusica = (int) modeloTabela.getValueAt(linhaSelecionada, 0);
                removerMusicaDaPlaylist(idMusica);
            }
            isPushed = false;
            return "Remover";
        }

        @Override
        public boolean stopCellEditing() {
            isPushed = false;
            return super.stopCellEditing();
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
