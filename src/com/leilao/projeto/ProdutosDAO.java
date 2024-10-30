package com.leilao.projeto;



/**
 *
 * @author Adm
 */

import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;


public class ProdutosDAO {
    
    Connection conn;
    PreparedStatement prep;
    ResultSet resultset;
    ArrayList<ProdutosDTO> listagem = new ArrayList<>();

    // Método para cadastrar um produto no banco de dados
    public void cadastrarProduto(ProdutosDTO produto) {
        String sql = "INSERT INTO produtos (nome, valor) VALUES (?, ?)";
        
        try {
            conn = conectaDAO.getConnection();  // Estabelecendo a conexão com o banco
            prep = conn.prepareStatement(sql);  // Preparando a consulta SQL
            
            // Substituindo os placeholders (?) pelos valores do objeto produto
            prep.setString(1, produto.getNome());
            prep.setDouble(2, produto.getValor());
            
            // Executando a inserção
            prep.executeUpdate();
            JOptionPane.showMessageDialog(null, "Produto cadastrado com sucesso!");
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao cadastrar produto: " + e.getMessage());
        } finally {
            // Fechar a conexão após o uso
            try {
                if (prep != null) prep.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Erro ao fechar a conexão: " + e.getMessage());
            }
        }
    }

    // Método para listar os produtos cadastrados
    public ArrayList<ProdutosDTO> listarProdutos() {
        String sql = "SELECT * FROM produtos";
        
        try {
            conn = conectaDAO.getConnection();
            prep = conn.prepareStatement(sql);
            resultset = prep.executeQuery();
            
            // Limpando a lista de produtos antes de adicionar os novos
            listagem.clear();
            
            // Percorrendo os resultados da consulta
            while (resultset.next()) {
                ProdutosDTO produto = new ProdutosDTO();
                produto.setId(resultset.getInt("id"));
                produto.setNome(resultset.getString("nome"));
                produto.setValor(resultset.getDouble("valor"));
                
                listagem.add(produto);  // Adicionando o produto à lista
            }
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao listar produtos: " + e.getMessage());
        } finally {
            try {
                if (resultset != null) resultset.close();
                if (prep != null) prep.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Erro ao fechar a conexão: " + e.getMessage());
            }
        }
        
        return listagem;
    }

    // Método para buscar um produto pelo nome
    public ProdutosDTO buscarPorNome(String nome) {
        ProdutosDTO produto = null;
        try {
            conn = conectaDAO.getConnection();
            String sql = "SELECT * FROM produtos WHERE nome = ?";
            prep = conn.prepareStatement(sql);
            prep.setString(1, nome);
            resultset = prep.executeQuery();
            
            if (resultset.next()) {
                produto = new ProdutosDTO();
                produto.setId(resultset.getInt("id"));
                produto.setNome(resultset.getString("nome"));
                produto.setValor(resultset.getDouble("valor"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultset != null) resultset.close();
                if (prep != null) prep.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return produto;
    }

    // Método para atualizar um produto
    public void atualizarProduto(ProdutosDTO produto) {
        String sql = "UPDATE produtos SET nome = ?, valor = ? WHERE id = ?";

        try {
            conn = conectaDAO.getConnection();
            prep = conn.prepareStatement(sql);
            prep.setString(1, produto.getNome());
            prep.setDouble(2, produto.getValor());
            prep.setInt(3, produto.getId());

            prep.executeUpdate();
            JOptionPane.showMessageDialog(null, "Produto atualizado com sucesso!");

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao atualizar o produto: " + e.getMessage());
        } finally {
            try {
                if (prep != null) prep.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Erro ao fechar a conexão: " + e.getMessage());
            }
        }
    }
}