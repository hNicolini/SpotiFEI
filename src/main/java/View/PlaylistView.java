package View;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class PlaylistView extends JFrame {
    private JTable tabela;
    private DefaultTableModel modeloTabela;
    private final Color backgroundColor = new Color(18, 18, 18);
    private final Color greenColor = new Color(30, 215, 96);
    private final Color textColor = Color.WHITE;
    private final Font fonteBase = new Font("SansSerif", Font.PLAIN, 16);
    private String usuarioLogado = null;
    
    public PlaylistView() {
        setTitle("SpotiFEI - Adicionar à Playlist");
        setSize(800, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel painelPrincipal = new JPanel();
        painelPrincipal.setLayout(new BoxLayout(painelPrincipal, BoxLayout.Y_AXIS));
        painelPrincipal.setBackground(backgroundColor);
        painelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titulo = new JLabel("➕ Adicionar Músicas à Playlist");
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        titulo.setFont(new Font("SansSerif", Font.BOLD, 24));
        titulo.setForeground(greenColor);
        painelPrincipal.add(titulo);
        painelPrincipal.add(Box.createRigidArea(new Dimension(0, 20)));

        modeloTabela = new DefaultTableModel(new Object[]{"ID", "Título", "Autor", "Gênero", "Ação"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 4;
            }
        };

        tabela = new JTable(modeloTabela) {
            @Override
            public Component prepareRenderer(javax.swing.table.TableCellRenderer renderer, int row, int column) {
                Component comp = super.prepareRenderer(renderer, row, column);
                comp.setBackground(row % 2 == 0 ? new Color(24, 24, 24) : new Color(30, 30, 30));
                comp.setForeground(Color.WHITE);
                comp.setFont(fonteBase);
                ((JComponent) comp).setBorder(new LineBorder(greenColor, 1, true));
                return comp;
            }
        };

        tabela.setRowHeight(40);
        tabela.setGridColor(greenColor);
        tabela.setShowGrid(false);
        tabela.setIntercellSpacing(new Dimension(0, 10));
        tabela.setBackground(backgroundColor);
        tabela.setForeground(textColor);
        tabela.setFont(fonteBase);
        tabela.setSelectionBackground(new Color(50, 50, 50));
        tabela.setSelectionForeground(greenColor);
        tabela.getTableHeader().setReorderingAllowed(false);
        tabela.getTableHeader().setBackground(greenColor.darker());
        tabela.getTableHeader().setForeground(Color.BLACK);
        tabela.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 16));

        tabela.getColumn("Ação").setCellRenderer(new ButtonRenderer());
        tabela.getColumn("Ação").setCellEditor(new ButtonEditor(new JCheckBox()));

        JScrollPane scrollPane = new JScrollPane(tabela);
        scrollPane.setPreferredSize(new Dimension(750, 300));
        scrollPane.getViewport().setBackground(backgroundColor);
        scrollPane.setBorder(BorderFactory.createLineBorder(greenColor));
        painelPrincipal.add(scrollPane);

        add(painelPrincipal);
        carregarMusicas();

        painelPrincipal.add(Box.createRigidArea(new Dimension(0, 20)));

        JButton verPlaylistBtn = new JButton("🎵 Ver Minha Playlist");
        verPlaylistBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        verPlaylistBtn.setForeground(greenColor);
        verPlaylistBtn.setBackground(backgroundColor);
        verPlaylistBtn.setFont(new Font("SansSerif", Font.BOLD, 16));
        verPlaylistBtn.setBorder(BorderFactory.createLineBorder(greenColor));
        verPlaylistBtn.setFocusPainted(false);
        verPlaylistBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        verPlaylistBtn.addActionListener(e -> {
            JPanel panel = new JPanel(new GridLayout(2, 2));
            JTextField userField = new JTextField();
            JPasswordField passField = new JPasswordField();

            panel.add(new JLabel("Usuário:"));
            panel.add(userField);
            panel.add(new JLabel("Senha:"));
            panel.add(passField);

            int result = JOptionPane.showConfirmDialog(this, panel,
                    "Digite suas credenciais", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

            if (result == JOptionPane.OK_OPTION) {
                String usuario = userField.getText().trim();
                String senhaUsuario = new String(passField.getPassword()).trim();

                if (usuario.isEmpty() || senhaUsuario.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Usuário e senha são obrigatórios.");
                    return;
                }

                if (validarUsuario(usuario, senhaUsuario)) {
                    usuarioLogado = usuario;
                    new MinhaPlaylistView(usuarioLogado);
                } else {
                    JOptionPane.showMessageDialog(this, "Usuário ou senha inválidos.");
                }
            }
        });

        painelPrincipal.add(verPlaylistBtn);
        setVisible(true);
    }

    private void carregarMusicas() {
        modeloTabela.setRowCount(0);
        String url = "jdbc:postgresql://localhost:5432/SpotiFEI";
        String usuario = "postgres";
        String senha = "hN@060106";
        String sql = "SELECT id, titulo, autor, genero FROM musicas";

        try (Connection conn = DriverManager.getConnection(url, usuario, senha);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                modeloTabela.addRow(new Object[]{
                        rs.getInt("id"),
                        rs.getString("titulo"),
                        rs.getString("autor"),
                        rs.getString("genero"),
                        "Adicionar"
                });
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar músicas:\n" + e.getMessage());
        }
    }

    private boolean validarUsuario(String usuario, String senhaUsuario) {
        String url = "jdbc:postgresql://localhost:5432/SpotiFEI";
        String user = "postgres";
        String senha = "hN@060106";
        String sql = "SELECT COUNT(*) FROM usuarios WHERE usuario = ? AND senha = ?";

        try (Connection conn = DriverManager.getConnection(url, user, senha);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, usuario);
            pstmt.setString(2, senhaUsuario);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro na validação de usuário:\n" + e.getMessage());
        }
        return false;
    }

    private void adicionarMusicaNaPlaylist(int musicaId) {
    if (usuarioLogado == null) {
        JPanel panel = new JPanel(new GridLayout(2, 2));
        JTextField userField = new JTextField();
        JPasswordField passField = new JPasswordField();

        panel.add(new JLabel("Usuário:"));
        panel.add(userField);
        panel.add(new JLabel("Senha:"));
        panel.add(passField);

        int result = JOptionPane.showConfirmDialog(this, panel,
                "Digite suas credenciais", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            String usuario = userField.getText().trim();
            String senhaUsuario = new String(passField.getPassword()).trim();

            if (usuario.isEmpty() || senhaUsuario.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Usuário e senha são obrigatórios.");
                return;
            }

            if (validarUsuario(usuario, senhaUsuario)) {
                usuarioLogado = usuario;
            } else {
                JOptionPane.showMessageDialog(this, "Usuário ou senha inválidos.");
                return;
            }
        } else {
            return;
        }
    }

    String url = "jdbc:postgresql://localhost:5432/SpotiFEI";
    String user = "postgres";
    String senha = "hN@060106";
    String sql = "INSERT INTO playlist (id_musica, usuario, titulo, autor, data_adicionado) " +
            "SELECT ?, ?, titulo, autor, CURRENT_TIMESTAMP FROM musicas WHERE id = ?";

    try (Connection conn = DriverManager.getConnection(url, user, senha);
         PreparedStatement pstmt = conn.prepareStatement(sql)) {

        pstmt.setInt(1, musicaId);
        pstmt.setString(2, usuarioLogado);
        pstmt.setInt(3, musicaId);
        pstmt.executeUpdate();

        JOptionPane.showMessageDialog(this, "Música adicionada à sua playlist!");

    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this, "Erro ao adicionar à playlist:\n" + e.getMessage());
    }
}


    class ButtonRenderer extends JButton implements javax.swing.table.TableCellRenderer {
        public ButtonRenderer() {
            setOpaque(true);
            setForeground(greenColor);
            setBackground(backgroundColor);
            setBorder(BorderFactory.createLineBorder(greenColor));
            setFont(fonteBase);
        }

        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,int row, int column) {
            setText("Adicionar");
            return this;
        }
    }

    class ButtonEditor extends DefaultCellEditor {
        private JButton button;
        private boolean isPushed;

        public ButtonEditor(JCheckBox checkBox) {
            super(checkBox);
            button = new JButton("Adicionar");
            button.setForeground(greenColor);
            button.setBackground(backgroundColor);
            button.setFont(fonteBase);
            button.setBorder(BorderFactory.createLineBorder(greenColor));
            button.setFocusPainted(false);
            button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

            button.addActionListener(e -> fireEditingStopped());
        }

        public Component getTableCellEditorComponent(JTable table, Object value,
                                                     boolean isSelected, int row, int column) {
            isPushed = true;
            return button;
        }

        public Object getCellEditorValue() {
            if (isPushed) {
                int row = tabela.getSelectedRow();
                int idMusica = (int) tabela.getValueAt(row, 0);
                adicionarMusicaNaPlaylist(idMusica);
            }
            isPushed = false;
            return "Adicionar";
        }

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
