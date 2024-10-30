
package com.leilao.projeto;

import com.leilao.telas.cadastroVIEW;
import java.util.ArrayList;


/** @author bmaga*/

public class Main {

    public static void main(String[] args) {
        ProdutosDAO dao = new ProdutosDAO();

        // 1. Cadastrar um Produto
        System.out.println("Cadastrando Produto...");
        ProdutosDTO novoProduto = new ProdutosDTO();
        novoProduto.setNome("Produto Teste");
        novoProduto.setValor(50.00);
        dao.cadastrarProduto(novoProduto);

        // 2. Listar Produtos Cadastrados
        System.out.println("Listando Produtos...");
        ArrayList<ProdutosDTO> produtos = dao.listarProdutos();
        for (ProdutosDTO produto : produtos) {
            System.out.println("ID: " + produto.getId() + " | Nome: " + produto.getNome() + " | Valor: " + produto.getValor());
        }

        // 3. Buscar Produto pelo Nome
        System.out.println("Buscando Produto pelo Nome...");
        String nomeBuscado = "Produto Teste";
        ProdutosDTO produtoEncontrado = dao.buscarPorNome(nomeBuscado);
        if (produtoEncontrado != null) {
            System.out.println("Produto encontrado: Nome = " + produtoEncontrado.getNome() + ", Valor = " + produtoEncontrado.getValor());
        } else {
            System.out.println("Produto não encontrado.");
        }

        // 4. Atualizar Produto
        if (produtoEncontrado != null) {
            System.out.println("Atualizando Produto...");
            produtoEncontrado.setNome("Produto Atualizado");
            produtoEncontrado.setValor(75.00);
            dao.atualizarProduto(produtoEncontrado);

            // Verificando se a atualização foi realizada
            ProdutosDTO produtoAtualizado = dao.buscarPorNome("Produto Atualizado");
            if (produtoAtualizado != null) {
                System.out.println("Produto atualizado com sucesso: Nome = " + produtoAtualizado.getNome() + ", Valor = " + produtoAtualizado.getValor());
            }
        }
    }
}

