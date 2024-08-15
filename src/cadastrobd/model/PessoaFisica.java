/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cadastrobd.model;

/**
 *
 * @author Smith
 */
public class PessoaFisica extends Pessoa{
    private String cpf;

    public PessoaFisica() {
    }

public PessoaFisica(Integer id, String nome, String rua, String cidade, String estado, String telefone, String email, String cpf) {
    super(id, nome, rua, cidade, estado, telefone, email);
    this.cpf = cpf;
}

    
    @Override
    public void exibir() {
        super.exibir();
        System.out.println("CPF: " + this.cpf);
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
    
}
