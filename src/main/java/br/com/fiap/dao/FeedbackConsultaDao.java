package br.com.fiap.dao;

import br.com.fiap.exeption.EntidadeNaoEncontradaException;
import br.com.fiap.model.Consulta;
import br.com.fiap.model.FeedbackConsulta;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class FeedbackConsultaDao {

    @Inject
    private DataSource dataSource;

    public void inserir(FeedbackConsulta feedback) throws SQLException {
        String sql = """
                INSERT INTO T_JPS_FEEDBACK
                (ID_FEEDBACK, ID_CONSULTA, DS_FEEDBACK, NT_FEEDBACK)
                VALUES (SEQ_FEEDBACK.NEXTVAL, ?, ?, ?)
                """;

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, new String[]{"ID_FEEDBACK"})) {

            ps.setInt(1, feedback.getConsulta().getCodigo());
            ps.setString(2, feedback.getComentario());
            ps.setDouble(3, feedback.getNota());

            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    feedback.setCodigo(rs.getInt(1));
                }
            }
        }
    }

    public List<FeedbackConsulta> listarTodos() throws SQLException {
        List<FeedbackConsulta> lista = new ArrayList<>();
        String sql = "SELECT * FROM T_JPS_FEEDBACK";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(parseFeedback(rs));
            }
        }
        return lista;
    }

    public List<FeedbackConsulta> listarPorConsulta(int consultaId) throws SQLException {
        List<FeedbackConsulta> lista = new ArrayList<>();
        String sql = "SELECT * FROM T_JPS_FEEDBACK WHERE ID_CONSULTA = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, consultaId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                lista.add(parseFeedback(rs));
            }
        }
        return lista;
    }

    public FeedbackConsulta buscarPorCodigo(int codigo) throws SQLException, EntidadeNaoEncontradaException {
        String sql = "SELECT * FROM T_JPS_FEEDBACK WHERE ID_FEEDBACK = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, codigo);
            ResultSet rs = ps.executeQuery();

            if (!rs.next()) {
                throw new EntidadeNaoEncontradaException("Feedback não encontrado!");
            }
            return parseFeedback(rs);
        }
    }

    public void atualizar(FeedbackConsulta feedback) throws SQLException, EntidadeNaoEncontradaException {
        String sql = "UPDATE T_JPS_FEEDBACK SET DS_FEEDBACK = ?, NT_FEEDBACK = ? WHERE ID_FEEDBACK = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, feedback.getComentario());
            ps.setDouble(2, feedback.getNota());
            ps.setInt(3, feedback.getCodigo());

            if (ps.executeUpdate() == 0) {
                throw new EntidadeNaoEncontradaException("Feedback não encontrado para atualizar!");
            }
        }
    }

    public void deletar(int codigo) throws SQLException, EntidadeNaoEncontradaException {
        String sql = "DELETE FROM T_JPS_FEEDBACK WHERE ID_FEEDBACK = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, codigo);
            if (ps.executeUpdate() == 0) {
                throw new EntidadeNaoEncontradaException("Feedback não encontrado para remover!");
            }
        }
    }

    private FeedbackConsulta parseFeedback(ResultSet rs) throws SQLException {
        Consulta consulta = new Consulta();
        consulta.setCodigo(rs.getInt("ID_CONSULTA"));

        FeedbackConsulta fb = new FeedbackConsulta();
        fb.setCodigo(rs.getInt("ID_FEEDBACK"));
        fb.setConsulta(consulta);
        fb.setComentario(rs.getString("DS_FEEDBACK"));
        fb.setNota(rs.getDouble("NT_FEEDBACK"));

        return fb;
    }
}
