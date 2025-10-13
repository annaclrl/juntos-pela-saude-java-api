package br.com.fiap.dao;

import br.com.fiap.factory.ConnectionFactory;
import br.com.fiap.model.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ConsultaDao implements AutoCloseable  {

    private final Connection conn;

    public ConsultaDao() throws SQLException, ClassNotFoundException {
        this.conn = ConnectionFactory.getConnection();
    }

    public boolean inserir(Consulta consulta) throws SQLException {
        String sql = """
            INSERT INTO T_JPS_CONSULTA 
            (ID_CONSULTA, ID_PACIENTE, ID_MEDICO, ID_FUNCIONARIO, DT_HR_CONSULTA, ST_CONSULTA)
            VALUES (SEQ_CONSULTA.NEXTVAL, ?, ?, ?, ?,?)
            """;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, consulta.getPaciente().getCodigo());
            ps.setInt(2, consulta.getMedico().getCodigo());
            ps.setInt(3, consulta.getFuncionario().getCodigo());
            ps.setTimestamp(4, Timestamp.valueOf(consulta.getDataHora()));
            ps.setString(5, consulta.getStatus().getValorBanco());

            return ps.executeUpdate() > 0;
        }
    }

    public List<Consulta> listarTodos() throws SQLException {
        List<Consulta> consultas = new ArrayList<>();
        String sql = "SELECT * FROM T_JPS_CONSULTA";

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                consultas.add(mapResultSetToConsulta(rs));
            }
        }
        return consultas;
    }

    public Consulta buscarPorCodigo(int codigo) throws SQLException {
        String sql = "SELECT * FROM T_JPS_CONSULTA WHERE ID_CONSULTA = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, codigo);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToConsulta(rs);
                }
            }
        }
        return null;
    }

    public List<Consulta> listarPorMedico(int medicoCodigo) throws SQLException {
        List<Consulta> consultas = new ArrayList<>();
        String sql = "SELECT * FROM T_JPS_CONSULTA WHERE ID_MEDICO=?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, medicoCodigo);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    consultas.add(mapResultSetToConsulta(rs));
                }
            }
        }
        return consultas;
    }

    public List<Consulta> listarPorPaciente(int pacienteCodigo) throws SQLException {
        List<Consulta> consultas = new ArrayList<>();
        String sql = "SELECT * FROM T_JPS_CONSULTA WHERE ID_PACIENTE=?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, pacienteCodigo);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    consultas.add(mapResultSetToConsulta(rs));
                }
            }
        }
        return consultas;
    }

    public boolean atualizar(Consulta consulta) throws SQLException {
        String sql = """
            UPDATE T_JPS_CONSULTA 
            SET ID_PACIENTE=?, ID_MEDICO=?, ID_FUNCIONARIO=?, DT_HR_CONSULTA=?, ST_CONSULTA=?
            WHERE ID_CONSULTA=?
            """;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, consulta.getPaciente().getCodigo());
            ps.setInt(2, consulta.getMedico().getCodigo());
            ps.setInt(3,consulta.getFuncionario().getCodigo());
            ps.setTimestamp(4, Timestamp.valueOf(consulta.getDataHora()));
            ps.setString(5, consulta.getStatus().getValorBanco());
            ps.setInt(6, consulta.getCodigo());

            return ps.executeUpdate() > 0;
        }
    }

    public boolean deletar(int codigo) throws SQLException {
        String sql = "DELETE FROM T_JPS_CONSULTA WHERE ID_CONSULTA=?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, codigo);
            return ps.executeUpdate() > 0;
        }
    }

    private Consulta mapResultSetToConsulta(ResultSet rs) throws SQLException {
        Paciente paciente = new Paciente();
        paciente.setCodigo(rs.getInt("ID_PACIENTE"));

        Medico medico = new Medico();
        medico.setCodigo(rs.getInt("ID_MEDICO"));

        Funcionario funcionario = new Funcionario();
        funcionario.setCodigo(rs.getInt("ID_FUNCIONARIO"));

        String statusStr = rs.getString("ST_CONSULTA");
        StatusConsulta status = null;
        try {
            status = StatusConsulta.fromDbValue(statusStr);
        } catch (IllegalArgumentException e) {
            System.out.println("Status inválido no banco: " + statusStr);
        }

        return new Consulta(
                rs.getInt("ID_CONSULTA"),
                paciente,
                medico,
                funcionario,
                rs.getTimestamp("DT_HR_CONSULTA").toLocalDateTime(),
                status
        );
    }

    @Override
    public void close() throws SQLException {
        if (conn != null && !conn.isClosed()) {
            conn.close();
        }
    }
}

