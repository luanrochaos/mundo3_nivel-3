/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package cadastrobd;

import cadastrobd.model.PessoaFisica;
import cadastrobd.model.PessoaFisicaDAO;
import cadastrobd.model.PessoaJuridica;
import cadastrobd.model.PessoaJuridicaDAO;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author Smith
 */
public class CadastroBD {
    private static Scanner sc = new Scanner(System.in);
    private static PessoaFisicaDAO pfDAO = new PessoaFisicaDAO();
    private static PessoaJuridicaDAO pjDAO = new PessoaJuridicaDAO();


    public static void main(String[] args) {
        int opcao = -1;
        while (opcao != 0) {
            System.out.println("===== CADASTRO BD =====");
            System.out.println("Selecione uma opcao:");
            System.out.println("1 - Incluir Pessoa");
            System.out.println("2 - Alterar Pessoa");
            System.out.println("3 - Excluir Pessoa");
            System.out.println("4 - Exibir pelo id");
            System.out.println("5 - Exibir todos");
            System.out.println("0 - Finalizar a execucao");

            opcao = Integer.parseInt(sc.nextLine());

            System.out.println("===========================================");
            switch (opcao) {
                case 1:
                    inserirPessoa();
                    break;
                case 2:
                    alterarPessoa();
                    break;
                case 3:
                    excluirPessoa();
                    break;
                case 4:
                    exibirPessoaPeloId();
                    break;
                case 5:
                    exibirTodasAsPessoas();
                    break;
            }

            System.out.println();
        }
    }

    private static String lerTipoDePessoa() {
        System.out.println("Escolha um opcao:\n\tPara Pessoa Fisica digite F\n\tPara Pessoa Juridica digite J");
        String tipo = sc.nextLine();

        System.out.println("===========================================\n");

        if (tipo.equalsIgnoreCase("F") || tipo.equalsIgnoreCase("J")) {
            return tipo;
        } else {
            System.out.println("Opcao invalida, tente novamente.");
            return lerTipoDePessoa();
        }
    }

    private static PessoaFisica definirDadosPessoaFisica(PessoaFisica pessoaFisica) {
        try {
            System.out.println("Digite o nome: ");
            pessoaFisica.setNome(sc.nextLine());
            System.out.println("Digite o cpf: ");
            pessoaFisica.setCpf(sc.nextLine());
            System.out.println("Digite o telefone: ");
            pessoaFisica.setTelefone(sc.nextLine());
            System.out.println("Digite o email: ");
            pessoaFisica.setEmail(sc.nextLine());
            System.out.println("Digite a rua: ");
            pessoaFisica.setRua(sc.nextLine());
            System.out.println("Digite a cidade: ");
            pessoaFisica.setCidade(sc.nextLine());
            System.out.println("Digite o estado: ");
            pessoaFisica.setEstado(sc.nextLine());
            return pessoaFisica;
        } catch (Exception e) {
            System.out.println("Nao foi possivel cadastrar os dados da Pessoa física:");
            e.printStackTrace();
            System.out.println("Tente novamente.");
            return null;
        }
    }

    private static PessoaJuridica definirDadosPessoaJuridica(PessoaJuridica pessoaJuridica) {
        try {
            System.out.println("Digite o nome: ");
            pessoaJuridica.setNome(sc.nextLine());
            System.out.println("Digite o CNPJ: ");
            pessoaJuridica.setCnpj(sc.nextLine());
            System.out.println("Digite o telefone: ");
            pessoaJuridica.setTelefone(sc.nextLine());
            System.out.println("Digite o email: ");
            pessoaJuridica.setEmail(sc.nextLine());
            System.out.println("Digite a rua: ");
            pessoaJuridica.setRua(sc.nextLine());
            System.out.println("Digite a cidade: ");
            pessoaJuridica.setCidade(sc.nextLine());
            System.out.println("Digite o estado: ");
            pessoaJuridica.setEstado(sc.nextLine());
            return pessoaJuridica;
        } catch (Exception e) {
            System.out.println("Nao foi possivel cadastrar os dados da Pessoa Jurídica:");
            e.printStackTrace();
            System.out.println("Tente novamente.");
            return null;
        }
    }

    private static void inserirPessoa() {
        String tipo = lerTipoDePessoa();

        if (tipo.equalsIgnoreCase("F")) {
            PessoaFisica pessoaFisica = definirDadosPessoaFisica(new PessoaFisica());
            if (pessoaFisica == null) {
                System.out.println("Erro ao cadastrar Pessoa Física.");
                return;
            }
            if (pfDAO.incluir(pessoaFisica) == false) {
                System.out.println("Erro ao cadastrar Pessoa Física.");
                return;
            }
            System.out.println("Pessoa Física cadastrada com sucesso.");
            return;
        }
        if (tipo.equalsIgnoreCase("J")) {
            PessoaJuridica pessoaJuridica = definirDadosPessoaJuridica(new PessoaJuridica());
            if (pessoaJuridica == null) {
                System.out.println("Falha ao cadastrar Pessoa Jurídica.");
                return;
            }
            if (pjDAO.incluir(pessoaJuridica) == false) {
                System.out.println("Falha ao cadastrar Pessoa Jurídica.");
                return;
            }
            System.out.println("Pessoa Jurídica cadastrar com sucesso.");
        }
    }

    private static void alterarPessoa() {
        String tipo = lerTipoDePessoa();

        if (tipo.equalsIgnoreCase("F")) {
            System.out.println("Digite o id da Pessoa Física que deseja alterar: ");
            int idPessoaFisica = Integer.parseInt(sc.nextLine());
            PessoaFisica pessoaFisica = pfDAO.getPessoa(idPessoaFisica);

            if (pessoaFisica == null) {
                System.out.println("Pessoa Física não encontrada.");
                return;
            }
            pessoaFisica.exibir();
            pessoaFisica = definirDadosPessoaFisica(pessoaFisica);

            if (pessoaFisica == null) {
                System.out.println("Falha ao alterar Pessoa Física.");
                return;
            }

            if (pfDAO.alterar(pessoaFisica) == false) {
                System.out.println("Falha ao alterar Pessoa Física.");
                return;
            }
            System.out.println("Pessoa Física alterada com sucesso.");
            return;
        }

        if (tipo.equalsIgnoreCase("J")) {
            System.out.println("Digite o id da Pessoa Jurídica que deseja alterar: ");
            int idPessoaJuridica = Integer.parseInt(sc.nextLine());
            PessoaJuridica pessoaJuridica = pjDAO.getPessoa(idPessoaJuridica);

            if (pessoaJuridica == null) {
                System.out.println("Pessoa Jurídica não encontrada.");
                return;
            }

            pessoaJuridica.exibir();
            pessoaJuridica = definirDadosPessoaJuridica(pessoaJuridica);

            if (pessoaJuridica == null) {
                System.out.println("Falha ao alterar Pessoa Jurídica.");
                return;
            }

            if (pjDAO.alterar(pessoaJuridica) == false) {
                System.out.println("Falha ao alterar Pessoa Jurídica.");
                return;
            }

            System.out.println("Pessoa Jurídica alterada com sucesso.");
        }
    }

    private static void excluirPessoa() {
        String tipo = lerTipoDePessoa();

        if (tipo.equalsIgnoreCase("F")) {
            System.out.println("Digite o id da Pessoa Física que deseja excluir: ");
            int idPessoaFisica = Integer.parseInt(sc.nextLine());
            PessoaFisica pessoaFisica = pfDAO.getPessoa(idPessoaFisica);

            if (pessoaFisica == null) {
                System.out.println("Pessoa Física não encontrada.");
                return;
            }

            if (pfDAO.excluir(idPessoaFisica) == false) {
                System.out.println("Falha ao excluir Pessoa Física.");
                return;
            }

            System.out.println("Pessoa Física excluída com sucesso.");
            return;
        }

        if (tipo.equalsIgnoreCase("J")) {
            System.out.println("Digite o id da Pessoa Jurídica que deseja excluir: ");
            int idPessoaJuridica = Integer.parseInt(sc.nextLine());
            PessoaJuridica pessoaJuridica = pjDAO.getPessoa(idPessoaJuridica);

            if (pessoaJuridica == null) {
                System.out.println("Pessoa Jurídica não encontrada.");
                return;
            }

            if (pjDAO.excluir(idPessoaJuridica) == false) {
                System.out.println("Falha ao excluir Pessoa Jurídica.");
                return;
            }

            System.out.println("Pessoa Jurídica excluída com sucesso.");
        }
    }

    private static void exibirPessoaPeloId() {
        String tipo = lerTipoDePessoa();

        if (tipo.equalsIgnoreCase("F")) {
            System.out.println("Digite o id da Pessoa Física que deseja exibir: ");
            int idPessoaFisica = Integer.parseInt(sc.nextLine());
            PessoaFisica pessoaFisica = pfDAO.getPessoa(idPessoaFisica);

            if (pessoaFisica == null) {
                System.out.println("Pessoa Física não encontrada.");
                return;
            }
            pessoaFisica.exibir();
            return;
        }

        if (tipo.equalsIgnoreCase("J")) {
            System.out.println("Digite o id da Pessoa Jurídica que deseja exibir: ");
            int idPessoaJuridica = Integer.parseInt(sc.nextLine());
            PessoaJuridica pessoaJuridica = pjDAO.getPessoa(idPessoaJuridica);

            if (pessoaJuridica == null) {
                System.out.println("Pessoa Jurídica não encontrada.");
                return;
            }
            pessoaJuridica.exibir();
        }
    }

    private static void exibirTodasAsPessoas() {
        String tipo = lerTipoDePessoa();

        if (tipo.equalsIgnoreCase("F")) {
            List<PessoaFisica> pessoasFisicas = pfDAO.getPessoas();
            if (pessoasFisicas == null) {
                System.out.println("Não existem Pessoas Físicas cadastradas.");
                return;
            }

            System.out.println("Exibindo todos os registros de Pessoas Físicas:\n");
            for (PessoaFisica pessoaFisica : pessoasFisicas) {
                System.out.println("-------------------------------------------");
                pessoaFisica.exibir();
            }
            return;
        }

        if (tipo.equalsIgnoreCase("J")) {
            List<PessoaJuridica> pessoasJuridicas = pjDAO.getPessoas();
            if (pessoasJuridicas == null) {
                System.out.println("Não existem Pessoas Jurídicas cadastradas.");
                return;
            }

            System.out.println("Exibindo todos os registros de Pessoas Jurídicas.");
            for (PessoaJuridica pessoaJuridica : pessoasJuridicas) {
                System.out.println("-------------------------------------------");
                pessoaJuridica.exibir();
            }
        }
    }
}