package main;

import dao.MusicaDAO;
import dao.Conexao;
import dao.UsuariosDAO;
import model.Musicas;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import model.Usuarios;

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        
        Conexao conexao = new Conexao();
        
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("Driver do PostgreSQL não encontrado!");
            e.printStackTrace();
            return;
        }   
        
        try (Connection conn = conexao.getConnection()) {
            MusicaDAO musicaDAO = new MusicaDAO(conn);
            UsuariosDAO usuarioDAO = new UsuariosDAO(conn);
            
            System.out.println("Digite o nome de usuario: ");;;;;
            String usuario = input.next();
            System.out.println("Digite a sua senha: ");
            String senha = input.next();
            
            Usuarios usuario1 = new Usuarios();
            usuario1.setUsuarios(usuario);
            usuario1.setSenha(senha);
            
            
            ResultSet busca = usuarioDAO.consultar(usuario);

            if (busca.next()) { 
                System.out.println("Usuário Existente!");
            } else {
                usuarioDAO.inserir(usuario1);
                System.out.println("Usuario Cadastrado com sucesso!");
            }

            System.out.println(musicaDAO.buscarIdPorTitulo("One Time"));
               
        } catch (SQLException e) {
            System.err.println("Erro ao acessar o banco de dados:");
            e.printStackTrace();
        }
    }
}