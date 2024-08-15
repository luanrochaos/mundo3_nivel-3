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

public class PessoaJuridicaDAO {
    public PessoaJuridica getPessoa(int id) {
        try {
            Connection conexao = ConectorBD.getConnection();

            if (conexao == null) {
                return null;
            }

            String sql = "SELECT * FROM Pessoa p INNER JOIN PessoaJuridica pj ON p.idPessoa = pj.idPessoa WHERE p.idPessoa = ?";

            PreparedStatement prepared = ConectorBD.getPrepared(conexao, sql);
            prepared.setInt(1, id);

            ResultSet resultSet = ConectorBD.getSelect(prepared);

            if (resultSet != null && resultSet.next()) {
                PessoaJuridica pessoaJuridica = criaPessoaJuridica(resultSet);

                ConectorBD.close(resultSet);
                ConectorBD.close(prepared);
                ConectorBD.close(conexao);
                return pessoaJuridica;
            }

            ConectorBD.close(prepared);
            ConectorBD.close(conexao);
            return null;
        } catch (SQLException e) {
            System.out.println("Erro ao obter a pessoa jurídica pelo id: " + e.getMessage());
            return null;
        }
    }

    public List<PessoaJuridica> getPessoas() {
        try {
            Connection conexao = ConectorBD.getConnection();

            if (conexao == null) {
                return null;
            }

            String sql = "SELECT * FROM Pessoa p INNER JOIN PessoaJuridica pf ON p.idPessoa = pf.idPessoa";

            PreparedStatement prepared = conexao.prepareStatement(sql);

            ResultSet resultSet = ConectorBD.getSelect(prepared);

            List<PessoaJuridica> pessoas = new ArrayList<>();

            while (resultSet != null && resultSet.next()) {
                PessoaJuridica pessoaJuridica = criaPessoaJuridica(resultSet);
                pessoas.add(pessoaJuridica);
            }

            ConectorBD.close(resultSet);
            ConectorBD.close(prepared);
            ConectorBD.close(conexao);

            return pessoas;
        } catch (SQLException e) {
            System.out.println("Erro ao obter todas as pessoas jurídicas: " + e.getMessage());
            return null;
        }
    }

    public boolean incluir(PessoaJuridica pessoaJuridica) {
        try {
            Integer nextId = SequenceManager.getValue("PessoaSequence");

            if (nextId == -1) {
                return false;
            }

            pessoaJuridica.setId(nextId);
            Connection conexao = ConectorBD.getConnection();

            if (conexao == null) {
                return false;
            }

            String sql = "INSERT INTO Pessoa (idPessoa, nome, telefone, email, rua, cidade, estado) VALUES (?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement prepared = ConectorBD.getPrepared(conexao, sql);
            prepared.setInt(1, pessoaJuridica.getId());
            prepared.setString(2, pessoaJuridica.getNome());
            prepared.setString(3, pessoaJuridica.getTelefone());
            prepared.setString(4, pessoaJuridica.getEmail());
            prepared.setString(5, pessoaJuridica.getRua());
            prepared.setString(6, pessoaJuridica.getCidade());
            prepared.setString(7, pessoaJuridica.getEstado());

            if (prepared.executeUpdate() <= 0) {
                ConectorBD.close(prepared);
                ConectorBD.close(conexao);
                return false;
            }

            sql = "INSERT INTO PessoaJuridica (idPessoa, cnpj) VALUES (?, ?)";

            prepared = ConectorBD.getPrepared(conexao, sql);
            prepared.setInt(1, nextId);
            prepared.setString(2, pessoaJuridica.getCnpj());

            if (prepared.executeUpdate() <= 0) {
                ConectorBD.close(prepared);
                ConectorBD.close(conexao);
                return false;
            }

            ConectorBD.close(prepared);
            ConectorBD.close(conexao);
            return true;
        } catch (SQLException e) {
            System.out.println("Erro ao incluir a pessoa jurídica: " + e.getMessage());
            return false;
        }
    }

    public boolean alterar(PessoaJuridica pessoaJuridica) {
        try {
            Connection conexao = ConectorBD.getConnection();

            if (conexao == null) {
                return false;
            }

            String sql = "UPDATE Pessoa SET nome = ?, telefone = ?, email = ?, rua = ?, cidade = ?, estado = ? WHERE idPessoa = ?";

            PreparedStatement prepared = ConectorBD.getPrepared(conexao, sql);
            prepared.setString(1, pessoaJuridica.getNome());
            prepared.setString(2, pessoaJuridica.getTelefone());
            prepared.setString(3, pessoaJuridica.getEmail());
            prepared.setString(4, pessoaJuridica.getRua());
            prepared.setString(5, pessoaJuridica.getCidade());
            prepared.setString(6, pessoaJuridica.getEstado());
            prepared.setInt(7, pessoaJuridica.getId());

            if (prepared.executeUpdate() <= 0) {
                ConectorBD.close(prepared);
                ConectorBD.close(conexao);
                return false;
            }

            sql = "UPDATE PessoaJuridica SET cnpj = ? WHERE idPessoa = ?";

            prepared = ConectorBD.getPrepared(conexao, sql);
            prepared.setString(1, pessoaJuridica.getCnpj());
            prepared.setInt(2, pessoaJuridica.getId());

            if (prepared.executeUpdate() <= 0) {
                ConectorBD.close(prepared);
                ConectorBD.close(conexao);
                return false;
            }

            ConectorBD.close(prepared);
            ConectorBD.close(conexao);
            return true;
        } catch (SQLException e) {
            System.out.println("Erro ao alterar a pessoa jurídica: " + e.getMessage());
            return false;
        }
    }

    public boolean excluir(int id) {
        try {
            Connection conexao = ConectorBD.getConnection();

            if (conexao == null) {
                return false;
            }

            String sql = "DELETE FROM PessoaJuridica WHERE idPessoa = ?";

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
            System.out.println("Erro ao excluir a pessoa jurídica: " + e.getMessage());
            return false;
        }
    }

    private static PessoaJuridica criaPessoaJuridica(ResultSet resultSet) throws SQLException {
        PessoaJuridica pessoaJuridica = new PessoaJuridica();
        pessoaJuridica.setId(resultSet.getInt("idPessoa"));
        pessoaJuridica.setNome(resultSet.getString("nome"));
        pessoaJuridica.setTelefone(resultSet.getString("telefone"));
        pessoaJuridica.setEmail(resultSet.getString("email"));
        pessoaJuridica.setRua(resultSet.getString("rua"));
        pessoaJuridica.setCidade(resultSet.getString("cidade"));
        pessoaJuridica.setEstado(resultSet.getString("estado"));
        pessoaJuridica.setCnpj(resultSet.getString("cnpj"));
        return pessoaJuridica;
    }
}