package br.com.fiap.dao;

import br.com.fiap.exception.EntidadeNaoEncontradaException;
import br.com.fiap.model.Funcionario;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class FuncionarioDao {

    @Inject
    private DataSource dataSource;

    public void inserir(Funcionario funcionario) throws SQLException {
        String sql = """
                INSERT INTO T_JPS_FUNCIONARIO
                (ID_FUNCIONARIO, NM_FUNCIONARIO, EM_FUNCIONARIO, CPF_FUNCIONARIO, IDD_FUNCIONARIO, TEL1_FUNCIONARIO, TEL2_FUNCIONARIO, PSWD_FUNCIONARIO)
                VALUES (SEQ_FUNCIONARIO.NEXTVAL, ?, ?, ?, ?, ?, ?, ?)
                """;


        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, new String[]{"ID_FUNCIONARIO"})) {

            ps.setString(1, funcionario.getNome());
            ps.setString(2, funcionario.getEmail());
            ps.setString(3, funcionario.getCpf());
            ps.setInt(4, funcionario.getIdade());
            ps.setString(5, funcionario.getTelefone1());
            ps.setString(6, funcionario.getTelefone2());
            ps.setString(7, funcionario.getSenha());

            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    funcionario.setCodigo(rs.getInt(1));
                }
            }
        }
    }

    public List<Funcionario> listarTodos() throws SQLException {
        List<Funcionario> funcionarios = new ArrayList<>();
        String sql = "SELECT * FROM T_JPS_FUNCIONARIO";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                funcionarios.add(parseFuncionario(rs));
            }
        }
        return funcionarios;
    }

    public Funcionario buscarPorCodigo(int codigo) throws SQLException, EntidadeNaoEncontradaException {
        String sql = "SELECT * FROM T_JPS_FUNCIONARIO WHERE ID_FUNCIONARIO = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, codigo);
            ResultSet rs = ps.executeQuery();

            if (!rs.next()) {
                throw new EntidadeNaoEncontradaException("Funcionário não encontrado!");
            }

            return parseFuncionario(rs);
        }
    }

    public Funcionario buscarPorCpf(String cpf) throws SQLException, EntidadeNaoEncontradaException {
        String sql = "SELECT * FROM T_JPS_FUNCIONARIO WHERE CPF_FUNCIONARIO = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, cpf);
            ResultSet rs = ps.executeQuery();

            if (!rs.next()) {
                throw new EntidadeNaoEncontradaException("CPF não encontrado!");
            }

            return parseFuncionario(rs);
        }
    }

    public Funcionario buscarPorEmail(String email) throws SQLException, EntidadeNaoEncontradaException {
        String sql = "SELECT * FROM T_JPS_FUNCIONARIO WHERE EM_FUNCIONARIO = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();

            if (!rs.next()) {
                throw new EntidadeNaoEncontradaException("E-mail não encontrado!");
            }

            return parseFuncionario(rs);
        }
    }

    public Funcionario buscarPorTelefone(String telefone1, String telefone2) throws SQLException, EntidadeNaoEncontradaException {
        String sql = """
                SELECT * FROM T_JPS_FUNCIONARIO 
                WHERE TEL1_FUNCIONARIO IN (?, ?) OR TEL2_FUNCIONARIO IN (?, ?)
                """;

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, telefone1);
            ps.setString(2, telefone1);
            ps.setString(3, telefone2);
            ps.setString(4, telefone2);
            ResultSet rs = ps.executeQuery();

            if (!rs.next()) {
                throw new EntidadeNaoEncontradaException("Telefone não encontrado!");
            }

            return parseFuncionario(rs);
        }
    }

    public boolean atualizar(Funcionario funcionario) throws SQLException, EntidadeNaoEncontradaException {
        String sql = """
                UPDATE T_JPS_FUNCIONARIO
                SET NM_FUNCIONARIO = ?, EM_FUNCIONARIO = ?, CPF_FUNCIONARIO = ?, IDD_FUNCIONARIO = ?, 
                    TEL1_FUNCIONARIO = ?, TEL2_FUNCIONARIO = ?, PSWD_FUNCIONARIO = ?
                WHERE ID_FUNCIONARIO = ?
                """;

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, funcionario.getNome());
            ps.setString(2, funcionario.getEmail());
            ps.setString(3, funcionario.getCpf());
            ps.setInt(4, funcionario.getIdade());
            ps.setString(5, funcionario.getTelefone1());
            ps.setString(6, funcionario.getTelefone2());
            ps.setString(7, funcionario.getSenha());
            ps.setInt(8, funcionario.getCodigo());

            if (ps.executeUpdate() == 0) {
                throw new EntidadeNaoEncontradaException("Funcionário não encontrado para atualizar!");
            }

            return true;
        }
    }

    public void deletar(int codigo) throws SQLException, EntidadeNaoEncontradaException {
        String sql = "DELETE FROM T_JPS_FUNCIONARIO WHERE ID_FUNCIONARIO = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, codigo);
            if (ps.executeUpdate() == 0) {
                throw new EntidadeNaoEncontradaException("Funcionário não encontrado para remover!");
            }
        }
    }

    private Funcionario parseFuncionario(ResultSet rs) throws SQLException {
        return new Funcionario(
                rs.getInt("ID_FUNCIONARIO"),
                rs.getString("NM_FUNCIONARIO"),
                rs.getString("EM_FUNCIONARIO"),
                rs.getString("CPF_FUNCIONARIO"),
                rs.getInt("IDD_FUNCIONARIO"),
                rs.getString("TEL1_FUNCIONARIO"),
                rs.getString("TEL2_FUNCIONARIO"),
                rs.getString("PSWD_FUNCIONARIO")
        );
    }
}
