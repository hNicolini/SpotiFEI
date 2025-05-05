package main;

import dao.MusicaDAO;
import dao.Conexao;
import dao.PlaylistDAO;
import dao.UsuariosDAO;
import model.Musicas;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import model.Playlist;
import model.Usuarios;

public class Main {
    public static void main(String[] args) throws SQLException {
        
        
        Conexao conexao = new Conexao();
        
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("Driver do PostgreSQL não encontrado!");
            e.printStackTrace();
            return;
        }   
        
        try (Connection conn = conexao.getConnection()) {
             Scanner input = new Scanner(System.in);
             
            MusicaDAO musicaDAO = new MusicaDAO(conn);
            UsuariosDAO usuarioDAO = new UsuariosDAO(conn);
            PlaylistDAO playlistDAO = new PlaylistDAO(conn);
            
            while(true){
            System.out.println("Oq deseja Fazer: \n"
                    + "1 - Login \n"
                    + "2 - Cadastro \n");
            int opcao = input.nextInt();
            
                if(opcao == 1){
                    
                    
                    System.out.println("Digite o nome de usuário: ");
                    String usuario = input.next();
                    System.out.println("Digite a sua senha: ");
                    String senha = input.next();

                    ResultSet busca = usuarioDAO.consultar_login(usuario, senha);

                    try {
                        if (busca.next()) { 
                            System.out.println("Usuário logado com sucesso!\n"
                                + "Bem-vindo, " + usuario + "!");
                        } else {
                            System.out.println("Usuário ou senha incorreto(s)");
                         System.exit(1);
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    
                   
            System.out.println("Oq deseja Fazer: \n"
                    + "1 - Adicionar musicas a Playlist \n"
                    + "2 - Ver Playlist \n");
            int opcao2 = input.nextInt();

                   if (opcao2 == 1){
                   
                    ResultSet resultado = musicaDAO.listarTodas();

                        while (resultado.next()) {
                            String titulo = resultado.getString("titulo");
                            String autor = resultado.getString("autor"); // ou "artista", conforme sua tabela
                            System.out.println("Título: " + titulo + " | Artista: " + autor);
                    }
                    input.nextLine(); 

                    Usuarios usuario1 = new Usuarios(usuario, senha); 

                    System.out.println("Qual o nome da música?");
                    String titulo = input.nextLine();

                    System.out.println("Qual o nome do artista?");
                    String artista = input.nextLine();

                    int idMusica = musicaDAO.buscarIdPorTitulo(titulo, artista);

                    if (idMusica == -1) {
                        System.out.println("Música não encontrada no banco de dados.");
                        return;
                    }

                    Musicas musica1 = new Musicas();
                    musica1.setTitulo(titulo);
                    musica1.setAutor(artista);
                    musica1.setId(idMusica);

                    playlistDAO.inserir(usuario1, musica1);

                    
                    System.out.println("Música adicionada à playlist de " + usuario1.getUsuario() + "!");
                }
                   else if(opcao2 == 2){
                   
                   
                   ResultSet resultado = playlistDAO.consultar(usuario);

                        while (resultado.next()) {
                            String titulo = resultado.getString("titulo");
                            String autor = resultado.getString("autor"); // ou "artista", conforme sua tabela
                            System.out.println("Título: " + titulo + " | Artista: " + autor);
                    }
                   }
                   }
                    
                
                else if (opcao == 2) {
                System.out.println("Digite o nome de usuário: ");
                String usuario = input.next();
                System.out.println("Digite a sua senha: ");
                String senha = input.next();

                Usuarios usuario1 = new Usuarios();
                usuario1.setUsuarios(usuario);
                usuario1.setSenha(senha);

                ResultSet busca = usuarioDAO.consultar(usuario);

                if (busca.next()) {
                    System.out.println("Usuário já existe!");
                } else {
                    usuarioDAO.inserir(usuario1);
                    System.out.println("Usuário cadastrado com sucesso!");
                }

                System.out.println("Voltando ao menu inicial...\n");
            }
        }   
            
            
     
            
               
        }catch (SQLException e) {
            System.err.println("Erro ao acessar o banco de dados:");
            e.printStackTrace();
        }
    }
}