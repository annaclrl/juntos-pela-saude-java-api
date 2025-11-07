package br.com.fiap.dao;


import br.com.fiap.exception.EntidadeNaoEncontradaException;
import br.com.fiap.model.Paciente;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class PacienteDao{

    @Inject
    private DataSource dataSource;

    public void inserir(Paciente paciente) throws SQLException {
        String sql = """
                INSERT INTO T_JPS_PACIENTE 
                (ID_PACIENTE, NM_PACIENTE, EM_PACIENTE, CPF_PACIENTE, IDD_PACIENTE, TEL1_PACIENTE, TEL2_PACIENTE, PSWD_PACIENTE) 
                VALUES (SEQ_PACIENTE.NEXTVAL, ?, ?, ?, ?, ?,?, ?)
                """;

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, new String[]{"ID_PACIENTE"})) {

            ps.setString(1, paciente.getNome());
            ps.setString(2, paciente.getEmail());
            ps.setString(3, paciente.getCpf());
            ps.setInt(4, paciente.getIdade());
            ps.setString(5, paciente.getTelefone1());
            ps.setString(6, paciente.getTelefone2());
            ps.setString(7,paciente.getSenha());

            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    paciente.setCodigo(rs.getInt(1));
                }
            }
        }
    }

    public List<Paciente> listarTodos() throws SQLException {
        List<Paciente> pacientes = new ArrayList<>();
        String sql = "SELECT * FROM T_JPS_PACIENTE";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                pacientes.add(parsePaciente(rs));
            }
        }
        return pacientes;
    }

    public Paciente buscarPorCodigo(int codigo) throws SQLException, EntidadeNaoEncontradaException {
        String sql = "SELECT * FROM T_JPS_PACIENTE WHERE ID_PACIENTE = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, codigo);
            ResultSet rs = ps.executeQuery();

            if (!rs.next()) {
                throw new EntidadeNaoEncontradaException("Paciente não encontrado!");
            }
            return parsePaciente(rs);
        }
    }

    public Paciente buscarPorCpf(String cpf) throws SQLException, EntidadeNaoEncontradaException {
        String sql = "SELECT * FROM T_JPS_PACIENTE WHERE CPF_PACIENTE=?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, cpf);
            ResultSet rs = ps.executeQuery();

            if (!rs.next()) {
                throw new EntidadeNaoEncontradaException("CPF não encontrado!");
            }

            return parsePaciente(rs);
        }
    }

    public Paciente buscarPorEmail(String email) throws SQLException, EntidadeNaoEncontradaException {
        String sql = "SELECT * FROM T_JPS_PACIENTE WHERE EM_PACIENTE=?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();

            if (!rs.next()) {
                throw new EntidadeNaoEncontradaException("E-mail não encontrado!");
            }

            return parsePaciente(rs);
        }
    }

    public Paciente buscarPorTelefone(String telefone1, String telefone2) throws SQLException, EntidadeNaoEncontradaException {
        String sql = """
                SELECT * FROM T_JPS_PACIENTE 
                WHERE TEL1_PACIENTE IN (?, ?) OR TEL2_PACIENTE IN (?, ?)
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
            return parsePaciente(rs);
        }
    }

    public boolean atualizar(Paciente paciente) throws SQLException, EntidadeNaoEncontradaException {
        String sql = """
                UPDATE T_JPS_PACIENTE 
                SET NM_PACIENTE=?, EM_PACIENTE=?, CPF_PACIENTE=?, IDD_PACIENTE=?, TEL1_PACIENTE=?,  TEL2_PACIENTE=?, PSWD_SENHA=?
                WHERE ID_PACIENTE=?
                """;

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, paciente.getNome());
            ps.setString(2, paciente.getEmail());
            ps.setString(3, paciente.getCpf());
            ps.setInt(4, paciente.getIdade());
            ps.setString(5, paciente.getTelefone1());
            ps.setString(6, paciente.getTelefone2());
            ps.setString(7, paciente.getSenha());
            ps.setInt(8, paciente.getCodigo());

            if (ps.executeUpdate() == 0) {
                throw new EntidadeNaoEncontradaException("Paciente não encontrado para atualizar!");
            }

            return true;
        }
    }

    public void deletar(int codigo) throws SQLException, EntidadeNaoEncontradaException {
        String sql = "DELETE FROM T_JPS_PACIENTE WHERE ID_PACIENTE=?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, codigo);

            if (ps.executeUpdate() == 0) {
                throw new EntidadeNaoEncontradaException("Paciente não encontrado para remover!");
            }
        }
    }

    private Paciente parsePaciente(ResultSet rs) throws SQLException {

        return new Paciente(
                rs.getInt("ID_PACIENTE"),
                rs.getString("NM_PACIENTE"),
                rs.getString("EM_PACIENTE"),
                rs.getString("CPF_PACIENTE"),
                rs.getInt("IDD_PACIENTE"),
                rs.getString("TEL1_PACIENTE"),
                rs.getString("TEL2_PACIENTE"),
                rs.getString("PSWD_PACIENTE")
        );
    }
}
