/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package cadastrobd;

import cadastrobd.model.PessoaFisica;
import cadastrobd.model.PessoaFisicaDAO;

/**
 *
 * @author Smith
 */
public class CadastroBDTeste {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
    PessoaFisica pessoaFisica = new PessoaFisica(1, "Jeferson", "Rua A", "Petropolis", "RJ", "1234561263", "jeferson@gmail.com", "11111111541");
    PessoaFisicaDAO pessoaFisicaDAO = new PessoaFisicaDAO();
    pessoaFisicaDAO.incluir(pessoaFisica);
    
    pessoaFisica.setNome("Jeferson Smith");
    pessoaFisicaDAO.alterar(pessoaFisica);
    
    
    pessoaFisicaDAO.getPessoas().forEach((pessoa) -> {
        pessoa.exibir();
    });
    
    

    }
}
