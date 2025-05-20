package View;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.*;
import java.awt.*;
import java.sql.*;

public class MainView extends JFrame {
    private JTable tabela;
    private DefaultTableModel modeloTabela;
    private JTextField campoPesquisa;
    private String usuarioLogado = "joao"; 

    public MainView() {
        setTitle("SpotiFEI - Biblioteca de Músicas");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        Color backgroundColor = new Color(18, 18, 18);
        Color greenColor = new Color(30, 215, 96);
        Color textColor = Color.WHITE;
        Font fonteBase = new Font("SansSerif", Font.PLAIN, 16);

        JPanel painelPrincipal = new JPanel();
        painelPrincipal.setLayout(new BoxLayout(painelPrincipal, BoxLayout.Y_AXIS));
        painelPrincipal.setBackground(backgroundColor);
        painelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titulo = new JLabel("🎵 SpotiFEI");
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        titulo.setFont(new Font("SansSerif", Font.BOLD, 28));
        titulo.setForeground(greenColor);
        painelPrincipal.add(titulo);
        painelPrincipal.add(Box.createRigidArea(new Dimension(0, 15)));

        JPanel painelPesquisa = new JPanel(new FlowLayout(FlowLayout.CENTER));
        painelPesquisa.setBackground(backgroundColor);
        campoPesquisa = new JTextField(20);
        campoPesquisa.setFont(fonteBase);
        campoPesquisa.setForeground(textColor);
        campoPesquisa.setCaretColor(textColor);
        campoPesquisa.setBackground(Color.DARK_GRAY);
        campoPesquisa.setBorder(BorderFactory.createLineBorder(greenColor));

        JButton btnBuscar = new JButton("Buscar");
        styleButton(btnBuscar, greenColor, backgroundColor, fonteBase);
        btnBuscar.addActionListener(e -> buscarMusicas());

        JLabel lblBuscar = new JLabel("Buscar título:");
        lblBuscar.setForeground(textColor);
        lblBuscar.setFont(fonteBase);

        JButton btnAbrirPlaylist = new JButton("Abrir Playlist");
        styleButton(btnAbrirPlaylist, greenColor, backgroundColor, fonteBase);
        btnAbrirPlaylist.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnAbrirPlaylist.addActionListener(e -> new PlaylistView());
        painelPrincipal.add(btnAbrirPlaylist);
        painelPrincipal.add(Box.createRigidArea(new Dimension(0, 10)));

        painelPesquisa.add(lblBuscar);
        painelPesquisa.add(campoPesquisa);
        painelPesquisa.add(btnBuscar);
        painelPrincipal.add(painelPesquisa);
        painelPrincipal.add(Box.createRigidArea(new Dimension(0, 10)));

        JButton btnCarregar = new JButton("Carregar Todas");
        styleButton(btnCarregar, greenColor, backgroundColor, fonteBase);
        btnCarregar.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnCarregar.addActionListener(e -> carregarMusicas());
        painelPrincipal.add(btnCarregar);
        painelPrincipal.add(Box.createRigidArea(new Dimension(0, 15)));

        JButton btnVerCurtidas = new JButton("Ver Músicas Curtidas");
        styleButton(btnVerCurtidas, greenColor, backgroundColor, fonteBase);
        btnVerCurtidas.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnVerCurtidas.addActionListener(e -> new CurtidasView(usuarioLogado));
        painelPrincipal.add(btnVerCurtidas);
        painelPrincipal.add(Box.createRigidArea(new Dimension(0, 10)));

        String[] colunas = {"ID", "Título", "Autor", "Gênero", "Curtir"};
        modeloTabela = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 4;
            }
        };

        tabela = new JTable(modeloTabela) {
            @Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
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
        tabela.getColumn("Curtir").setCellRenderer(new ButtonRenderer());
        tabela.getColumn("Curtir").setCellEditor(new ButtonEditor(new JCheckBox()));

        JScrollPane scrollPane = new JScrollPane(tabela);
        scrollPane.setPreferredSize(new Dimension(850, 300));
        scrollPane.getViewport().setBackground(backgroundColor);
        scrollPane.setBorder(BorderFactory.createLineBorder(greenColor));
        painelPrincipal.add(scrollPane);

        add(painelPrincipal);
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
                        rs.getString("genero"),
                        "Curtir"
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
                        rs.getString("genero"),
                        "Curtir"
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

    private void curtirMusica(int idMusica, String titulo, String autor) {
        if (usuarioLogado == null) {
            JOptionPane.showMessageDialog(this, "Você precisa estar logado para curtir músicas.");
            return;
        }

        String url = "jdbc:postgresql://localhost:5432/SpotiFEI";
        String usuario = "postgres";
        String senha = "hN@060106";
        String sql = "INSERT INTO curtidas (usuario, id_musica, titulo, autor) VALUES (?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(url, usuario, senha);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, usuarioLogado);
            pstmt.setInt(2, idMusica);
            pstmt.setString(3, titulo);
            pstmt.setString(4, autor);
            pstmt.executeUpdate();

            JOptionPane.showMessageDialog(this, "Música curtida com sucesso!");

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao curtir música:\n" + e.getMessage());
        }
    }

    class ButtonRenderer extends JButton implements TableCellRenderer {
        public ButtonRenderer() {
            setOpaque(true);
            setText("Curtir");
            setBackground(new Color(18, 18, 18));
            setForeground(new Color(30, 215, 96));
            setBorder(BorderFactory.createLineBorder(new Color(30, 215, 96)));
        }

        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            return this;
        }
    }

    class ButtonEditor extends DefaultCellEditor {
        private JButton button;
        private boolean isPushed;

        public ButtonEditor(JCheckBox checkBox) {
            super(checkBox);
            button = new JButton("Curtir");
            button.setForeground(new Color(30, 215, 96));
            button.setBackground(new Color(18, 18, 18));
            button.setBorder(BorderFactory.createLineBorder(new Color(30, 215, 96)));
            button.setFocusPainted(false);
            button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            button.addActionListener(e -> fireEditingStopped());
        }

        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            isPushed = true;
            return button;
        }

        public Object getCellEditorValue() {
            if (isPushed) {
                int row = tabela.getSelectedRow();
                int idMusica = (int) tabela.getValueAt(row, 0);
                String titulo = (String) tabela.getValueAt(row, 1);
                String autor = (String) tabela.getValueAt(row, 2);
                curtirMusica(idMusica, titulo, autor);
            }
            isPushed = false;
            return "Curtir";
        }

        public boolean stopCellEditing() {
            isPushed = false;
            return super.stopCellEditing();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainView::new);
    }


   
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
