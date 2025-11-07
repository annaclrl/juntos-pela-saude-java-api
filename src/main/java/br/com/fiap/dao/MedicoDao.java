package br.com.fiap.dao;

import br.com.fiap.exception.EntidadeNaoEncontradaException;
import br.com.fiap.model.Medico;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class MedicoDao {

    @Inject
    private DataSource dataSource;

    public void inserir(Medico medico) throws SQLException {
        String sql = """
            INSERT INTO T_JPS_MEDICO 
            (ID_MEDICO, NM_MEDICO, EM_MEDICO, CPF_MEDICO, IDD_MEDICO, TEL1_MEDICO, TEL2_MEDICO, CRM_MEDICO, ESP_MEDICO, PSWD_MEDICO)
            VALUES (SEQ_MEDICO.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, ?, ?)
            """;

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, new String[]{"ID_MEDICO"})) {

            ps.setString(1, medico.getNome());
            ps.setString(2, medico.getEmail());
            ps.setString(3, medico.getCpf());
            ps.setInt(4, medico.getIdade());
            ps.setString(5, medico.getTelefone1());
            ps.setString(6, medico.getTelefone2());
            ps.setString(7, medico.getCrm());
            ps.setString(8, medico.getEspecialidade());
            ps.setString(9, medico.getSenha());

            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    medico.setCodigo(rs.getInt(1));
                }
            }
        }
    }

    public List<Medico> listarTodos() throws SQLException {
        List<Medico> medicos = new ArrayList<>();
        String sql = "SELECT * FROM T_JPS_MEDICO";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                medicos.add(parseMedico(rs));
            }
        }
        return medicos;
    }

    public Medico buscarPorCodigo(int codigo) throws SQLException, EntidadeNaoEncontradaException {
        String sql = "SELECT * FROM T_JPS_MEDICO WHERE ID_MEDICO = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, codigo);
            ResultSet rs = ps.executeQuery();

            if (!rs.next()) {
                throw new EntidadeNaoEncontradaException("Médico não encontrado!");
            }

            return parseMedico(rs);
        }
    }

    public Medico buscarPorCrm(String crm) throws SQLException, EntidadeNaoEncontradaException {
        String sql = "SELECT * FROM T_JPS_MEDICO WHERE CRM_MEDICO = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, crm);
            ResultSet rs = ps.executeQuery();

            if (!rs.next()) {
                throw new EntidadeNaoEncontradaException("CRM não encontrado!");
            }

            return parseMedico(rs);
        }
    }

    public Medico buscarPorCpf(String cpf) throws SQLException, EntidadeNaoEncontradaException {
        String sql = "SELECT * FROM T_JPS_MEDICO WHERE CPF_MEDICO = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, cpf);
            ResultSet rs = ps.executeQuery();

            if (!rs.next()) {
                throw new EntidadeNaoEncontradaException("CPF não encontrado!");
            }

            return parseMedico(rs);
        }
    }

    public Medico buscarPorEmail(String email) throws SQLException, EntidadeNaoEncontradaException {
        String sql = "SELECT * FROM T_JPS_MEDICO WHERE EM_MEDICO = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();

            if (!rs.next()) {
                throw new EntidadeNaoEncontradaException("E-mail não encontrado!");
            }

            return parseMedico(rs);
        }
    }

    public Medico buscarPorTelefone(String telefone1, String telefone2) throws SQLException, EntidadeNaoEncontradaException {
        String sql = """
            SELECT * FROM T_JPS_MEDICO 
            WHERE TEL1_MEDICO = ? OR TEL2_MEDICO = ? OR TEL1_MEDICO = ? OR TEL2_MEDICO = ?
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

            return parseMedico(rs);
        }
    }

    public List<Medico> buscarPorEspecialidade(String especialidade) throws SQLException {
        List<Medico> lista = new ArrayList<>();
        String sql = "SELECT * FROM T_JPS_MEDICO WHERE UPPER(ESP_MEDICO) = UPPER(?)";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, especialidade);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(parseMedico(rs));
                }
            }
        }

        return lista;
    }



    public boolean atualizar(Medico medico) throws SQLException, EntidadeNaoEncontradaException {
        String sql = """
            UPDATE T_JPS_MEDICO 
            SET NM_MEDICO=?, EM_MEDICO=?, CPF_MEDICO=?, IDD_MEDICO=?, 
                TEL1_MEDICO=?, TEL2_MEDICO=?, CRM_MEDICO=?, ESP_MEDICO=?, PSWD_MEDICO=? 
            WHERE ID_MEDICO=?
            """;

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, medico.getNome());
            ps.setString(2, medico.getEmail());
            ps.setString(3, medico.getCpf());
            ps.setInt(4, medico.getIdade());
            ps.setString(5, medico.getTelefone1());
            ps.setString(6, medico.getTelefone2());
            ps.setString(7, medico.getCrm());
            ps.setString(8, medico.getEspecialidade());
            ps.setString(9, medico.getSenha());
            ps.setInt(10, medico.getCodigo());

            if (ps.executeUpdate() == 0) {
                throw new EntidadeNaoEncontradaException("Médico não encontrado para atualizar!");
            }

            return true;
        }
    }

    public void deletar(int codigo) throws SQLException, EntidadeNaoEncontradaException {
        String sql = "DELETE FROM T_JPS_MEDICO WHERE ID_MEDICO = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, codigo);

            if (ps.executeUpdate() == 0) {
                throw new EntidadeNaoEncontradaException("Médico não encontrado para remover!");
            }
        }
    }

    private Medico parseMedico(ResultSet rs) throws SQLException {
        return new Medico(
                rs.getInt("ID_MEDICO"),
                rs.getString("NM_MEDICO"),
                rs.getString("EM_MEDICO"),
                rs.getString("CPF_MEDICO"),
                rs.getInt("IDD_MEDICO"),
                rs.getString("TEL1_MEDICO"),
                rs.getString("TEL2_MEDICO"),
                rs.getString("CRM_MEDICO"),
                rs.getString("ESP_MEDICO"),
                rs.getString("PSWD_MEDICO")
        );
    }
}
