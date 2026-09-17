package com.AgenHotel.Service;

import com.AgenHotel.Dao.HospedeDAO;
import com.AgenHotel.Model.Hospede;

import java.sql.SQLException;
import java.util.List;

public class HospedeService {

    private final HospedeDAO hospedeDAO = new HospedeDAO();

    public List<Hospede> listar() throws SQLException {
        return hospedeDAO.listar();
    }

    public List<Hospede> listar(String busca) throws SQLException {
        return hospedeDAO.listar(busca);
    }

    public Hospede buscarPorId(int id) throws SQLException {
        return hospedeDAO.buscarPorId(id);
    }

    public void salvar(Hospede hospede) throws SQLException {
        validar(hospede);

        if (hospede.getId() == 0) {
            hospedeDAO.inserir(hospede);
        } else {
            hospedeDAO.atualizar(hospede);
        }
    }

    public void excluir(int id) throws SQLException {
        hospedeDAO.excluir(id);
    }

    private void validar(Hospede hospede) {
        if (hospede.getNome() == null || hospede.getNome().isBlank()) {
            throw new IllegalArgumentException("Informe o nome do hospede.");
        }
        if (hospede.getEmail() == null || !hospede.getEmail().contains("@")) {
            throw new IllegalArgumentException("Informe um e-mail valido.");
        }
    }
}
