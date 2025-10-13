package br.com.fiap.dao;

import br.com.fiap.factory.ConnectionFactory;
import br.com.fiap.model.Medico;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MedicoDao implements AutoCloseable {

    private final Connection conn;

    public MedicoDao() throws SQLException, ClassNotFoundException {
        this.conn = ConnectionFactory.getConnection();
    }

    public boolean inserir(Medico medico) throws SQLException {
        String sql = """
            INSERT INTO T_JPS_MEDICO 
            (ID_MEDICO, NM_MEDICO, EM_MEDICO, CPF_MEDICO, IDD_MEDICO, TEL1_MEDICO,TEL2_MEDICO, CRM_MEDICO, ESP_MEDICO) 
            VALUES (SEQ_MEDICO.NEXTVAL, ?, ?, ?, ?, ?, ?, ?,?)
            """;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, medico.getNome());
            ps.setString(2, medico.getEmail());
            ps.setString(3, medico.getCpf());
            ps.setInt(4, medico.getIdade());
            ps.setString(5, medico.getTelefone1());
            ps.setString(6, medico.getTelefone2());
            ps.setString(7, medico.getCrm());
            ps.setString(8, medico.getEspecialidade());
            return ps.executeUpdate() > 0;
        }
    }

    public List<Medico> listarTodos() throws SQLException {
        List<Medico> medicos = new ArrayList<>();
        String sql = "SELECT * FROM T_JPS_MEDICO";

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                medicos.add(mapResultSetToMedico(rs));
            }
        }
        return medicos;
    }

    public Medico buscarPorCodigo(int codigo) throws SQLException {
        String sql = "SELECT * FROM T_JPS_MEDICO WHERE ID_MEDICO = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, codigo);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToMedico(rs);
                }
            }
        }
        return null;
    }

    public Medico buscarPorCrm(String crm) throws SQLException {
        String sql = "SELECT * FROM T_JPS_MEDICO WHERE CRM_MEDICO=?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, crm);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToMedico(rs);
                }
            }
        }
        return null;
    }

    public Medico buscarPorCpf(String cpf) throws SQLException {
        String sql = "SELECT * FROM T_JPS_MEDICO WHERE CPF_MEDICO=?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, cpf);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToMedico(rs);
                }
            }
        }
        return null;
    }

    public Medico buscarPorEmail(String email) throws SQLException {
        String sql = "SELECT * FROM T_JPS_MEDICO WHERE EM_MEDICO=?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToMedico(rs);
                }
            }
        }
        return null;
    }

    public Medico buscarPorTelefone(String telefone1, String telefone2) throws SQLException {
        String sql = """
        SELECT * FROM T_JPS_MEDICO 
        WHERE (TEL1_MEDICO = ? OR TEL2_MEDICO = ?) 
          OR (TEL1_MEDICO = ? OR TEL2_MEDICO = ?)
        """;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, telefone1);
            ps.setString(2, telefone1);
            ps.setString(3, telefone2);
            ps.setString(4, telefone2);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToMedico(rs);
                }
            }
        }
        return null;
    }

    public boolean atualizar(Medico medico) throws SQLException {
        String sql = """
            UPDATE T_JPS_MEDICO 
            SET NM_MEDICO=?, EM_MEDICO=?, CPF_MEDICO=?, IDD_MEDICO=?, TEL1_MEDICO=?, TEL2_MEDICO=?, CRM_MEDICO=?, ESP_MEDICO=? 
            WHERE ID_MEDICO=?
            """;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, medico.getNome());
            ps.setString(2, medico.getEmail());
            ps.setString(3, medico.getCpf());
            ps.setInt(4, medico.getIdade());
            ps.setString(5, medico.getTelefone1());
            ps.setString(6, medico.getTelefone2());
            ps.setString(7, medico.getCrm());
            ps.setString(8, medico.getEspecialidade());
            ps.setInt(9, medico.getCodigo());

            return ps.executeUpdate() > 0;
        }
    }

    public boolean deletar(int codigo) throws SQLException {
        String sql = "DELETE FROM T_JPS_MEDICO WHERE ID_MEDICO=?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, codigo);
            return ps.executeUpdate() > 0;
        }
    }

    private Medico mapResultSetToMedico(ResultSet rs) throws SQLException {

        return new Medico(
                rs.getInt("ID_MEDICO"),
                rs.getString("NM_MEDICO"),
                rs.getString("EM_MEDICO"),
                rs.getString("CPF_MEDICO"),
                rs.getInt("IDD_MEDICO"),
                rs.getString("TEL1_MEDICO"),
                rs.getString("TEL2_MEDICO"),
                rs.getString("CRM_MEDICO"),
                rs.getString("ESP_MEDICO")
        );
    }

    @Override
    public void close() throws SQLException {
        if (conn != null && !conn.isClosed()) {
            conn.close();
        }
    }
}
