package main;

import dao.MusicaDAO;
import dao.Conexao;
import model.Musicas;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
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

            // Teste de inserção
            System.out.println("Inserindo novas músicas...");
            
            Musicas musica1 = new Musicas();
            musica1.setTitulo("Bohemian Rhapsody");
            musica1.setAutor("Queen");
            musica1.setGenero("Rock");
            musicaDAO.inserir(musica1);
            
            Musicas musica2 = new Musicas();
            musica2.setTitulo("Imagine");
            musica2.setAutor("John Lennon");
            musica2.setGenero("Pop");
            musicaDAO.inserir(musica2);
            
            System.out.println("Músicas inseridas com sucesso!");

            // Teste de consulta (parte modificada)
            System.out.println("\nConsultando músicas...");
            
            // Consulta por título específico - versão modificada
            String tituloBusca = "Imagine";
            ResultSet resultado = musicaDAO.consultar(tituloBusca);
            
            System.out.println("Resultados da consulta por título 'Imagine':");
            while (resultado.next()) {
                System.out.println("Título: " + resultado.getString("titulo"));
                System.out.println("Autor: " + resultado.getString("autor"));
                System.out.println("Gênero: " + resultado.getString("genero"));
                System.out.println("---------------------");
            }

            // Consulta geral
            System.out.println("\nTodas as músicas cadastradas:");
            resultado = conn.createStatement().executeQuery("SELECT * FROM musicas");
            while (resultado.next()) {
                System.out.println("Título: " + resultado.getString("titulo"));
                System.out.println("Autor: " + resultado.getString("autor"));
                System.out.println("Gênero: " + resultado.getString("genero"));
                System.out.println("---------------------");
            }

        } catch (SQLException e) {
            System.err.println("Erro ao acessar o banco de dados:");
            e.printStackTrace();
        }
    }
}