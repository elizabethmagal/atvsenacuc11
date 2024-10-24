package com.leilao.projeto;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

import java.sql.ResultSet;

import java.sql.Statement;


/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Adm
 */
public class conectaDAO {

    // Definindo as variáveis da conexão como estáticas na classe
    private static final String URL = "jdbc:mysql://localhost:3306/uc11?useTimezone=true&serverTimezone=America/Sao_Paulo";
    private static final String USER = "root";
    private static final String PASSWORD = "root";

    // Método para obter a conexão com o banco de dados
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    // Método principal para testar a conexão
    public static void main(String[] args) {
        try (Connection connection = conectaDAO.getConnection()) {
            if (connection != null) {
                System.out.println("Conexão estabelecida com sucesso!");
            } else {
                System.out.println("Falha ao conectar ao banco de dados!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erro ao conectar ao banco de dados: " + e.getMessage());
        }
    }

    // Método para testar a conexão executando uma consulta simples
    public static void testConnection() {
        try (Connection connection = getConnection()) {
            String sql = "SELECT 1";
            try (Statement stmt = connection.createStatement();
                 ResultSet rs = stmt.executeQuery(sql)) {
                if (rs.next()) {
                    System.out.println("Conexão ao banco de dados verificada com sucesso.");
                } else {
                    System.out.println("Erro ao verificar a conexão com o banco de dados.");
                }
            }
        } catch (SQLException ex) {
            System.out.println("Erro ao testar a conexão: " + ex.getMessage());
        }
    }
}