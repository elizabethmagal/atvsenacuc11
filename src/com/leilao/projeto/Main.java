
package com.leilao.projeto;

import com.leilao.telas.cadastroVIEW;

/**
 *
 * @author bmaga
 */
public class Main {
   public static void main(String[] args) {
        // Cria uma instância da tela de cadastro
        cadastroVIEW telaDeCadastro = new cadastroVIEW();
        
        // Define a tela de cadastro como visível
        telaDeCadastro.setVisible(true);
        
        // Caso seja necessário, centraliza a tela no meio da tela do computador
        telaDeCadastro.setLocationRelativeTo(null);
    }
 
}
