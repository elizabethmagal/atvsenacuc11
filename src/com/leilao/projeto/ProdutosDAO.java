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
    
     // Método para vender um produto (atualizar o status para "Vendido"):
    public void venderProduto(int id) {
        String sql = "UPDATE produtos SET status = 'Vendido' WHERE id = ?";

        try {
            conn = conectaDAO.getConnection();
            prep = conn.prepareStatement(sql);
            prep.setInt(1, id);

            int rowsUpdated = prep.executeUpdate();
            if (rowsUpdated > 0) {
                JOptionPane.showMessageDialog(null, "Produto vendido com sucesso!");
            } else {
                JOptionPane.showMessageDialog(null, "Produto não encontrado.");
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao vender o produto: " + e.getMessage());
        } finally {
            try {
                if (prep != null) prep.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Erro ao fechar a conexão: " + e.getMessage());
            }
        }
    }

    // Método para listar os produtos vendidos
    public ArrayList<ProdutosDTO> listarProdutosVendidos() {
        String sql = "SELECT * FROM produtos WHERE status = 'Vendido'";

        try {
            conn = conectaDAO.getConnection();
            prep = conn.prepareStatement(sql);
            resultset = prep.executeQuery();

            listagem.clear();  // Limpa a lista antes de adicionar os produtos

            while (resultset.next()) {
                ProdutosDTO produto = new ProdutosDTO();
                produto.setId(resultset.getInt("id"));
                produto.setNome(resultset.getString("nome"));
                produto.setValor(resultset.getDouble("valor"));
                produto.setStatus(resultset.getString("status"));  // Assume que ProdutosDTO tem o campo "status"
                
                listagem.add(produto);  // Adiciona o produto vendido à lista
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao listar produtos vendidos: " + e.getMessage());
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
                produto.setStatus(resultset.getString("status"));  
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
    public void atualizarProduto(ProdutosDTO produto) throws SQLException {
    String sql = "UPDATE produtos SET status = ? WHERE id = ?";

    try (Connection conn = conectaDAO.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        // Define o status como "Vendido" e o ID do produto para ser atualizado
        stmt.setString(1, produto.getStatus());
        stmt.setInt(2, produto.getId());

        // Executa a atualização no banco de dados
        stmt.executeUpdate();
    } catch (SQLException e) {
        throw new SQLException("Erro ao atualizar produto: " + e.getMessage());
    }
    }
    
    public ProdutosDTO buscarProdutoPorId(int id) throws SQLException {
    String sql = "SELECT * FROM produtos WHERE id = ?";
    ProdutosDTO produto = null;

    try (Connection conn = conectaDAO.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            produto = new ProdutosDTO();
            produto.setId(rs.getInt("id"));
            produto.setNome(rs.getString("nome"));
            produto.setValor(rs.getDouble("valor"));
            produto.setStatus(rs.getString("status"));
        }
    } catch (SQLException e) {
        throw new SQLException("Erro ao buscar produto por ID: " + e.getMessage());
    }

    return produto;
}
    
}