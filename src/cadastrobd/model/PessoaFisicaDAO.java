/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cadastrobd.model;

/**
 *
 * @author Smith
 */
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import cadastrobd.model.util.ConectorBD;
import cadastrobd.model.util.SequenceManager;

public class PessoaFisicaDAO {
    public PessoaFisica getPessoa(int id) {
        try {
            Connection conexao = ConectorBD.getConnection();

            if (conexao == null) {
                return null;
            }

            String sql = "SELECT * FROM Pessoa p INNER JOIN PessoaFisica pf ON p.idPessoa = pf.idPessoa WHERE p.idPessoa = ?";

            PreparedStatement prepared = ConectorBD.getPrepared(conexao, sql);
            prepared.setInt(1, id);

            ResultSet resultSet = ConectorBD.getSelect(prepared);

            if (resultSet != null && resultSet.next()) {
                PessoaFisica pessoaFisica = criaPessoaFisica(resultSet);

                ConectorBD.close(resultSet);
                ConectorBD.close(prepared);
                ConectorBD.close(conexao);
                return pessoaFisica;
            }

            ConectorBD.close(prepared);
            ConectorBD.close(conexao);
            return null;
        } catch (SQLException e) {
            System.out.println("Erro ao obter a pessoa física pelo id: " + e.getMessage());
            return null;
        }
    }

    public List<PessoaFisica> getPessoas() {
        try {
            Connection conexao = ConectorBD.getConnection();

            if (conexao == null) {
                return null;
            }

            String sql = "SELECT * FROM Pessoa p INNER JOIN PessoaFisica pf ON p.idPessoa = pf.idPessoa";

            PreparedStatement prepared = conexao.prepareStatement(sql);

            ResultSet resultSet = ConectorBD.getSelect(prepared);

            List<PessoaFisica> pessoas = new ArrayList<>();

            while (resultSet != null && resultSet.next()) {
                PessoaFisica pessoaFisica = criaPessoaFisica(resultSet);
                pessoas.add(pessoaFisica);
            }

            ConectorBD.close(resultSet);
            ConectorBD.close(prepared);
            ConectorBD.close(conexao);

            return pessoas;
        } catch (SQLException e) {
            System.out.println("Erro ao obter todas as pessoas físicas: " + e.getMessage());
            return null;
        }
    }

    public boolean incluir(PessoaFisica pessoaFisica) {
        try {
            Integer nextId = SequenceManager.getValue("CodigoPessoa");

            if (nextId == -1) {
                return false;
            }

            pessoaFisica.setId(nextId);
            Connection conexao = ConectorBD.getConnection();

            if (conexao == null) {
                return false;
            }

            String sql = "INSERT INTO Pessoa (idPessoa, nome, telefone, email, rua, cidade, estado) VALUES (?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement prepared = ConectorBD.getPrepared(conexao, sql);
            prepared.setInt(1, pessoaFisica.getId());
            prepared.setString(2, pessoaFisica.getNome());
            prepared.setString(3, pessoaFisica.getTelefone());
            prepared.setString(4, pessoaFisica.getEmail());
            prepared.setString(5, pessoaFisica.getRua());
            prepared.setString(6, pessoaFisica.getCidade());
            prepared.setString(7, pessoaFisica.getEstado());

            if (prepared.executeUpdate() <= 0) {
                ConectorBD.close(prepared);
                ConectorBD.close(conexao);
                return false;
            }

            sql = "INSERT INTO PessoaFisica (idPessoa, cpf) VALUES (?, ?)";

            prepared = ConectorBD.getPrepared(conexao, sql);
            prepared.setInt(1, nextId);
            prepared.setString(2, pessoaFisica.getCpf());

            if (prepared.executeUpdate() <= 0) {
                ConectorBD.close(prepared);
                ConectorBD.close(conexao);
                return false;
            }

            ConectorBD.close(prepared);
            ConectorBD.close(conexao);
            return true;
        } catch (SQLException e) {
            System.out.println("Erro ao incluir a pessoa física: " + e.getMessage());
            return false;
        }
    }

    public boolean alterar(PessoaFisica pessoaFisica) {
        try {
            Connection conexao = ConectorBD.getConnection();

            if (conexao == null) {
                return false;
            }

            String sql = "UPDATE Pessoa SET nome = ?, telefone = ?, email = ?, rua = ?, cidade = ?, estado = ? WHERE idPessoa = ?";

            // Obtém um objeto PreparedStatement com o SQL criado e os dados da pessoa física fornecida como parâmetro
            PreparedStatement prepared = ConectorBD.getPrepared(conexao, sql);
            prepared.setString(1, pessoaFisica.getNome());
            prepared.setString(2, pessoaFisica.getTelefone());
            prepared.setString(3, pessoaFisica.getEmail());
            prepared.setString(4, pessoaFisica.getRua());
            prepared.setString(5, pessoaFisica.getCidade());
            prepared.setString(6, pessoaFisica.getEstado());
            prepared.setInt(7, pessoaFisica.getId());

            if (prepared.executeUpdate() <= 0) {
                ConectorBD.close(prepared);
                ConectorBD.close(conexao);
                return false;
            }

            sql = "UPDATE PessoaFisica SET cpf = ? WHERE idPessoa = ?";

            prepared = ConectorBD.getPrepared(conexao, sql);
            prepared.setString(1, pessoaFisica.getCpf());
            prepared.setInt(2, pessoaFisica.getId());

            if (prepared.executeUpdate() <= 0) {
                ConectorBD.close(prepared);
                ConectorBD.close(conexao);
                return false;
            }

            ConectorBD.close(prepared);
            ConectorBD.close(conexao);
            return true;
        } catch (SQLException e) {
            System.out.println("Erro ao alterar a pessoa física: " + e.getMessage());
            return false;
        }
    }

    public boolean excluir(int id) {
        try {
            Connection conexao = ConectorBD.getConnection();

            if (conexao == null) {
                return false;
            }

            String sql = "DELETE FROM PessoaFisica WHERE idPessoa = ?";

            PreparedStatement prepared = ConectorBD.getPrepared(conexao, sql);
            prepared.setInt(1, id);

            if (prepared.executeUpdate() <= 0) {
                ConectorBD.close(prepared);
                ConectorBD.close(conexao);
                return false;
            }

            sql = "DELETE FROM Pessoa WHERE idPessoa = ?";

            prepared = ConectorBD.getPrepared(conexao, sql);
            prepared.setInt(1, id); 

            if (prepared.executeUpdate() <= 0) {
                ConectorBD.close(prepared);
                ConectorBD.close(conexao);
                return false;
            }

            ConectorBD.close(prepared);
            ConectorBD.close(conexao);
            return true;
        } catch (SQLException e) {
            System.out.println("Erro ao excluir a pessoa física: " + e.getMessage());
            return false;
        }
    }

    private static PessoaFisica criaPessoaFisica(ResultSet resultSet) throws SQLException {
        PessoaFisica pessoaFisica = new PessoaFisica();
        pessoaFisica.setId(resultSet.getInt("idPessoa"));
        pessoaFisica.setNome(resultSet.getString("nome"));
        pessoaFisica.setTelefone(resultSet.getString("telefone"));
        pessoaFisica.setEmail(resultSet.getString("email"));
        pessoaFisica.setRua(resultSet.getString("rua"));
        pessoaFisica.setCidade(resultSet.getString("cidade"));
        pessoaFisica.setEstado(resultSet.getString("estado"));
        pessoaFisica.setCpf(resultSet.getString("cpf"));
        return pessoaFisica;
    }
}