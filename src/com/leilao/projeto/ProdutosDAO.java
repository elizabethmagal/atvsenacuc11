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
        String sql = "INSERT INTO produtos (nome, valor) VALUES (?, ?)";  // Ajuste de acordo com os campos da sua tabela
        
        try {
            conn = conectaDAO.getConnection();  // Estabelecendo a conexão com o banco
            prep = conn.prepareStatement(sql);  // Preparando a consulta SQL
            
            // Substituindo os placeholders (?) pelos valores do objeto produto
            prep.setString(1, produto.getNome());  
            prep.setDouble(2, produto.getValor());  // Supondo que o valor seja um tipo numérico
            
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
        String sql = "SELECT * FROM produtos";  // Ajuste de acordo com sua tabela de produtos
        
        try {
            conn = conectaDAO.getConnection();  // Estabelecendo a conexão com o banco
            prep = conn.prepareStatement(sql);  // Preparando a consulta SQL
            resultset = prep.executeQuery();  // Executando a consulta
            
            // Limpando a lista de produtos antes de adicionar os novos
            listagem.clear();
            
            // Percorrendo os resultados da consulta
            while (resultset.next()) {
                ProdutosDTO produto = new ProdutosDTO();
                produto.setId(resultset.getInt("id"));  // Supondo que sua tabela tenha um campo 'id'
                produto.setNome(resultset.getString("nome"));
                produto.setValor(resultset.getInt("valor"));
                
                listagem.add(produto);  // Adicionando o produto à lista
            }
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao listar produtos: " + e.getMessage());
        } finally {
            // Fechar a conexão após o uso
            try {
                if (resultset != null) resultset.close();
                if (prep != null) prep.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Erro ao fechar a conexão: " + e.getMessage());
            }
        }
        
        return listagem;  // Retorna a lista de produtos
    }
    public ProdutosDTO buscarPorNome(String nome) {
    ProdutosDTO produto = null;
    try {
        conn = conectaDAO.getConnection();  // Estabelecendo a conexão com o banco de dados
        String sql = "SELECT * FROM produtos WHERE nome = ?";
        prep = conn.prepareStatement(sql);  // Preparando a consulta SQL
        prep.setString(1, nome);  // Definindo o parâmetro nome na consulta
        
        resultset = prep.executeQuery();  // Executando a consulta
        
        if (resultset.next()) {
            produto = new ProdutosDTO();
            produto.setId(resultset.getInt("id"));  // Supondo que sua tabela tenha um campo 'id'
            produto.setNome(resultset.getString("nome"));
            produto.setValor(resultset.getInt("valor"));
            produto.setStatus(resultset.getString("status"));  // Se você tiver o campo 'status' na tabela
        }
    } catch (SQLException e) {
        e.printStackTrace();  // Exibe o erro no console
    } finally {
        // Fechar a conexão após o uso
        try {
            if (resultset != null) resultset.close();
            if (prep != null) prep.close();
            if (conn != null) conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    return produto;  // Retorna o produto encontrado ou null se não existir
}
    public void atualizarProduto(ProdutosDTO produto) {
    String sql = "UPDATE produtos SET nome = ?, valor = ?, status = ? WHERE id = ?";  // Ajuste de acordo com os campos da sua tabela

    try {
        conn = conectaDAO.getConnection();  // Estabelecendo a conexão com o banco
        prep = conn.prepareStatement(sql);  // Preparando a consulta SQL

        // Substituindo os placeholders (?) pelos valores do objeto produto
        prep.setString(1, produto.getNome());
        prep.setDouble(2, produto.getValor());  // Supondo que o valor seja um tipo numérico
        prep.setString(3, produto.getStatus()); // Atualizando o status, se houver
        prep.setInt(4, produto.getId());  // Atualizando o produto com base no seu ID

        // Executando a atualização
        prep.executeUpdate();
        
        JOptionPane.showMessageDialog(null, "Produto atualizado com sucesso!");

    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, "Erro ao atualizar o produto: " + e.getMessage());
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

}
    
    
        


