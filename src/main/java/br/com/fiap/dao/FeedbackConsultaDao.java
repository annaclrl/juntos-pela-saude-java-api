package br.com.fiap.dao;

import br.com.fiap.factory.ConnectionFactory;
import br.com.fiap.model.Consulta;
import br.com.fiap.model.FeedbackConsulta;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FeedbackConsultaDao implements AutoCloseable{
    private final Connection conn;

    public FeedbackConsultaDao() throws SQLException, ClassNotFoundException {
        this.conn = ConnectionFactory.getConnection();
    }

    public boolean inserir(FeedbackConsulta feedback) throws SQLException {
        String sql = """
            INSERT INTO T_JPS_FEEDBACK
            (ID_FEEDBACK, ID_CONSULTA, DS_FEEDBACK, NT_FEEDBACK)
            VALUES (SEQ_FEEDBACK.NEXTVAL, ?, ?, ?)
            """;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, feedback.getConsulta().getCodigo());
            ps.setString(2, feedback.getComentario());
            ps.setDouble(3, feedback.getNota());
            return ps.executeUpdate() > 0;
        }
    }

    public FeedbackConsulta buscarPorCodigo(int id) throws SQLException {
        String sql = "SELECT * FROM T_JPS_FEEDBACK WHERE ID_FEEDBACK = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToFeedback(rs);
                }
            }
        }
        return null;
    }

    public List<FeedbackConsulta> listarTodos() throws SQLException {
        List<FeedbackConsulta> lista = new ArrayList<>();
        String sql = "SELECT * FROM T_JPS_FEEDBACK";

        try (Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(mapResultSetToFeedback(rs));
            }
        }
        return lista;
    }

    public List<FeedbackConsulta> listarPorConsulta(int consultaId) throws SQLException {
        List<FeedbackConsulta> lista = new ArrayList<>();
        String sql = "SELECT * FROM T_JPS_FEEDBACK WHERE ID_CONSULTA = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, consultaId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapResultSetToFeedback(rs));
                }
            }
        }
        return lista;
    }

    public boolean atualizar(FeedbackConsulta feedback) throws SQLException {
        String sql = """
            UPDATE T_JPS_FEEDBACK
            SET DS_FEEDBACK = ?, NT_FEEDBACK = ?
            WHERE ID_FEEDBACK = ?
            """;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, feedback.getComentario());
            ps.setDouble(2, feedback.getNota());
            ps.setInt(3, feedback.getCodigo());
            return ps.executeUpdate() > 0;
        }
    }

    public boolean deletar(int id) throws SQLException {
        String sql = "DELETE FROM T_JPS_FEEDBACK WHERE ID_FEEDBACK = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    private FeedbackConsulta mapResultSetToFeedback(ResultSet rs) throws SQLException {
        Consulta consulta = new Consulta();
        consulta.setCodigo(rs.getInt("ID_CONSULTA"));

        FeedbackConsulta fb = new FeedbackConsulta();
        fb.setCodigo(rs.getInt("ID_FEEDBACK"));
        fb.setConsulta(consulta);
        fb.setComentario(rs.getString("DS_FEEDBACK"));
        fb.setNota(rs.getDouble("NT_FEEDBACK"));
        return fb;
    }

    @Override
    public void close() throws SQLException {
        if (conn != null && !conn.isClosed()) {
            conn.close();
        }
    }
}
