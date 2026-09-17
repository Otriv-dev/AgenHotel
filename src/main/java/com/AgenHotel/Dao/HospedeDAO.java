package com.AgenHotel.Dao;

import com.AgenHotel.Config.JdbConnection;
import com.AgenHotel.Model.Hospede;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class HospedeDAO {

    private final JdbConnection banco = JdbConnection.getInstance();

    public List<Hospede> listar() throws SQLException {
        String sql = "SELECT id, nome, email, telefone FROM hospedes ORDER BY nome";
        List<Hospede> hospedes = new ArrayList<>();

        try (Connection conn = banco.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                hospedes.add(montarHospede(rs));
            }
        }
        return hospedes;
    }

    public Hospede buscarPorId(int id) throws SQLException {
        String sql = "SELECT id, nome, email, telefone FROM hospedes WHERE id = ?";

        try (Connection conn = banco.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return montarHospede(rs);
                }
            }
        }
        return null;
    }

    public void inserir(Hospede hospede) throws SQLException {
        String sql = "INSERT INTO hospedes (nome, email, telefone) VALUES (?, ?, ?)";

        try (Connection conn = banco.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            preencherDados(ps, hospede);
            ps.executeUpdate();
        }
    }

    public void atualizar(Hospede hospede) throws SQLException {
        String sql = "UPDATE hospedes SET nome = ?, email = ?, telefone = ? WHERE id = ?";

        try (Connection conn = banco.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            preencherDados(ps, hospede);
            ps.setInt(4, hospede.getId());
            ps.executeUpdate();
        }
    }

    public void excluir(int id) throws SQLException {
        String sql = "DELETE FROM hospedes WHERE id = ?";

        try (Connection conn = banco.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    private void preencherDados(PreparedStatement ps, Hospede hospede) throws SQLException {
        ps.setString(1, hospede.getNome());
        ps.setString(2, hospede.getEmail());
        ps.setString(3, hospede.getTelefone());
    }

    private Hospede montarHospede(ResultSet rs) throws SQLException {
        return new Hospede(
                rs.getInt("id"),
                rs.getString("nome"),
                rs.getString("email"),
                rs.getString("telefone")
        );
    }
}
