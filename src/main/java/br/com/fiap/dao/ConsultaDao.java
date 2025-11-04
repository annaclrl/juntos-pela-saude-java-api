package br.com.fiap.dao;


import br.com.fiap.exception.EntidadeNaoEncontradaException;
import br.com.fiap.exception.RegraNegocioExeption;
import br.com.fiap.model.*;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;


import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class ConsultaDao {

    @Inject
    private DataSource dataSource;

    public void inserir(Consulta consulta) throws SQLException, RegraNegocioExeption {
        String sql = """
                INSERT INTO T_JPS_CONSULTA 
                (ID_CONSULTA, ID_PACIENTE, ID_MEDICO, ID_FUNCIONARIO, DT_HR_CONSULTA, ST_CONSULTA)
                VALUES (SEQ_CONSULTA.NEXTVAL, ?, ?, ?, ?,?)
                """;

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, new String[]{"ID_CONSULTA"})) {
            ps.setInt(1, consulta.getPaciente().getCodigo());
            ps.setInt(2, consulta.getMedico().getCodigo());
            ps.setInt(3, consulta.getFuncionario().getCodigo());
            ps.setTimestamp(4, Timestamp.valueOf(consulta.getDataHora()));
            ps.setString(5, consulta.getStatus().getValorBanco());

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                consulta.setCodigo(rs.getInt(1));
            }
        }catch (SQLException e) {
            // FK pai não encontrado
            if (e.getErrorCode() == 2291) { // ORA-02291
                throw new RegraNegocioExeption("Paciente, Médico ou Funcionário não encontrado no banco.");
            }
            throw e; // outros erros continuam lançando SQLException
        }
    }

    public List<Consulta> listarTodos() throws SQLException {
        List<Consulta> consultas = new ArrayList<>();
        String sql = "SELECT * FROM T_JPS_CONSULTA";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                consultas.add(parseConsulta(rs));
            }
        }
        return consultas;
    }

    public Consulta buscarPorCodigo(int codigo) throws SQLException, EntidadeNaoEncontradaException {
        String sql = "SELECT * FROM T_JPS_CONSULTA WHERE ID_CONSULTA = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

                ps.setInt(1, codigo);
                ResultSet rs = ps.executeQuery();

                if (!rs.next()) {
                    throw new EntidadeNaoEncontradaException("Consulta não encontrada!");
                }

                return parseConsulta(rs);
        }
    }

    public void atualizar(Consulta consulta) throws SQLException, EntidadeNaoEncontradaException, RegraNegocioExeption {
        String sql = "UPDATE T_JPS_CONSULTA SET " +
                "ID_PACIENTE = ?, ID_MEDICO = ?, ID_FUNCIONARIO = ?, DT_HR_CONSULTA = ?, ST_CONSULTA = ? " +
                "WHERE ID_CONSULTA = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, consulta.getPaciente().getCodigo());
            ps.setInt(2, consulta.getMedico().getCodigo());
            ps.setInt(3, consulta.getFuncionario().getCodigo());
            ps.setTimestamp(4, Timestamp.valueOf(consulta.getDataHora()));
            ps.setString(5, consulta.getStatus().getValorBanco());
            ps.setInt(6, consulta.getCodigo());

            if (ps.executeUpdate() == 0) {
                throw new EntidadeNaoEncontradaException("Consulta não encontrada para atualizar!");
            }

        }
    }

    public void deletar(int codigo) throws SQLException, EntidadeNaoEncontradaException {
        String sql = "DELETE FROM T_JPS_CONSULTA WHERE ID_CONSULTA = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, codigo);
            if (ps.executeUpdate() == 0) {
                throw new EntidadeNaoEncontradaException("Consulta não encontrada para remover!");
            }
        }
    }

    private List<Consulta> buscarPorCampo(String campo, int valor) throws SQLException {
        List<Consulta> consultas = new ArrayList<>();
        String sql = "SELECT * FROM T_JPS_CONSULTA WHERE " + campo + " = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, valor);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    consultas.add(parseConsulta(rs));
                }
            }
        }
        return consultas;
    }

    private void deletarPorCampo(String campo, int valor) throws SQLException {
        String sql = "DELETE FROM T_JPS_CONSULTA WHERE " + campo + " = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, valor);
            ps.executeUpdate();
        }
    }


    public List<Consulta> buscarPorPaciente(int idPaciente) throws SQLException {
        return buscarPorCampo("ID_PACIENTE", idPaciente);
    }

    public List<Consulta> buscarPorMedico(int idMedico) throws SQLException {
        return buscarPorCampo("ID_MEDICO", idMedico);
    }

    public List<Consulta> buscarPorFuncionario(int idFuncionario) throws SQLException {
        return buscarPorCampo("ID_FUNCIONARIO", idFuncionario);
    }

    public void deletarPorPaciente(int idPaciente) throws SQLException {
        deletarPorCampo("ID_PACIENTE", idPaciente);
    }

    public void deletarPorMedico(int idMedico) throws SQLException {
        deletarPorCampo("ID_MEDICO", idMedico);
    }

    public void deletarPorFuncionario(int idFuncionario) throws SQLException {
        deletarPorCampo("ID_FUNCIONARIO", idFuncionario);
    }

    private Consulta parseConsulta(ResultSet rs) throws SQLException {
        Paciente paciente = new Paciente();
        paciente.setCodigo(rs.getInt("ID_PACIENTE"));

        Medico medico = new Medico();
        medico.setCodigo(rs.getInt("ID_MEDICO"));

        Funcionario funcionario = new Funcionario();
        funcionario.setCodigo(rs.getInt("ID_FUNCIONARIO"));

        StatusConsulta status = StatusConsulta.fromDbValue(rs.getString("ST_CONSULTA"));
        LocalDateTime dataHora = rs.getTimestamp("DT_HR_CONSULTA").toLocalDateTime();

        Consulta consulta = new Consulta();
        consulta.setCodigo(rs.getInt("ID_CONSULTA"));
        consulta.setPaciente(paciente);
        consulta.setMedico(medico);
        consulta.setFuncionario(funcionario);
        consulta.setStatus(status);
        consulta.setDataHora(dataHora);

        return consulta;
    }
}

