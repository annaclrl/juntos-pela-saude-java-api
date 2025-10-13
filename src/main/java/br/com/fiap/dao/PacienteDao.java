package br.com.fiap.dao;

import br.com.fiap.factory.ConnectionFactory;
import br.com.fiap.model.Paciente;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PacienteDao  implements  AutoCloseable{

    private final Connection conn;

    public PacienteDao() throws SQLException, ClassNotFoundException {
        this.conn = ConnectionFactory.getConnection();
    }

    public boolean inserir(Paciente paciente) throws SQLException {
        String sql = """
            INSERT INTO T_JPS_PACIENTE 
            (ID_PACIENTE, NM_PACIENTE, EM_PACIENTE, CPF_PACIENTE, IDD_PACIENTE, TEL1_PACIENTE, TEL2_PACIENTE) 
            VALUES (SEQ_PACIENTE.NEXTVAL, ?, ?, ?, ?, ?,?)
            """;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, paciente.getNome());
            ps.setString(2, paciente.getEmail());
            ps.setString(3, paciente.getCpf());
            ps.setInt(4,paciente.getIdade());
            ps.setString(5, paciente.getTelefone1());
            ps.setString(6, paciente.getTelefone2());
            return ps.executeUpdate() > 0;
        }
    }

    public List<Paciente> listarTodos() throws SQLException {
        List<Paciente> pacientes = new ArrayList<>();
        String sql = "SELECT * FROM T_JPS_PACIENTE";

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                pacientes.add(mapResultSetToPaciente(rs));
            }
        }
        return pacientes;
    }

    public Paciente buscarPorCodigo(int codigo) throws SQLException {
        String sql = "SELECT * FROM T_JPS_PACIENTE WHERE ID_PACIENTE = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, codigo);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToPaciente(rs);
                }
            }
        }
        return null;
    }

    public Paciente buscarPorCpf(String cpf) throws SQLException {
        String sql = "SELECT * FROM T_JPS_PACIENTE WHERE CPF_PACIENTE=?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, cpf);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToPaciente(rs);
                }
            }
        }
        return null;
    }

    public Paciente buscarPorEmail(String email) throws SQLException {
        String sql = "SELECT * FROM T_JPS_PACIENTE WHERE EM_PACIENTE=?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToPaciente(rs);
                }
            }
        }
        return null;
    }

    public Paciente buscarPorTelefone(String telefone1, String telefone2) throws SQLException {
        String sql = """
        SELECT * FROM T_JPS_PACIENTE 
        WHERE (TEL1_PACIENTE = ? OR TEL2_PACIENTE = ?) 
          OR (TEL1_PACIENTE = ? OR TEL2_PACIENTE = ?)
        """;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, telefone1);
            ps.setString(2, telefone1);
            ps.setString(3, telefone2);
            ps.setString(4, telefone2);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToPaciente(rs);
                }
            }
        }
        return null;
    }

    public boolean atualizar(Paciente paciente) throws SQLException {
        String sql = """
            UPDATE T_JPS_PACIENTE 
            SET NM_PACIENTE=?, EM_PACIENTE=?, CPF_PACIENTE=?, IDD_PACIENTE=?, TEL1_PACIENTE=?,  TEL2_PACIENTE=?
            WHERE ID_PACIENTE=?
            """;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, paciente.getNome());
            ps.setString(2, paciente.getEmail());
            ps.setString(3, paciente.getCpf());
            ps.setInt(4, paciente.getIdade());
            ps.setString(5, paciente.getTelefone1());
            ps.setString(6, paciente.getTelefone2());
            ps.setInt(7, paciente.getCodigo());

            return ps.executeUpdate() > 0;
        }
    }

    public boolean deletar(int codigo) throws SQLException {
        String sql = "DELETE FROM T_JPS_PACIENTE WHERE ID_PACIENTE=?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, codigo);
            return ps.executeUpdate() > 0;
        }
    }

    private Paciente mapResultSetToPaciente(ResultSet rs) throws SQLException {

        return new Paciente(
                rs.getInt("ID_PACIENTE"),
                rs.getString("NM_PACIENTE"),
                rs.getString("EM_PACIENTE"),
                rs.getString("CPF_PACIENTE"),
                rs.getInt("IDD_PACIENTE"),
                rs.getString("TEL1_PACIENTE"),
                rs.getString("TEL2_PACIENTE")
        );
    }

    @Override
    public void close() throws SQLException {
        if (conn != null && !conn.isClosed()) {
            conn.close();
        }
    }
}
