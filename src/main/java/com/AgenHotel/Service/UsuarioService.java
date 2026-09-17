package com.AgenHotel.Service;

import com.AgenHotel.Dao.UsuarioDAO;
import com.AgenHotel.Model.Usuario;

import java.sql.SQLException;

public class UsuarioService {

    private final UsuarioDAO usuarioDAO = new UsuarioDAO();

    public Usuario autenticar(String email, String senha) throws SQLException {
        if (email == null || email.isBlank() || senha == null || senha.isBlank()) {
            return null;
        }
        return usuarioDAO.autenticar(email.trim(), senha);
    }

    public void cadastrar(Usuario usuario) throws SQLException {
        if (usuario.getNome() == null || usuario.getNome().isBlank()) {
            throw new IllegalArgumentException("Informe seu nome completo.");
        }
        if (usuario.getEmail() == null || !usuario.getEmail().contains("@")) {
            throw new IllegalArgumentException("Informe um e-mail válido.");
        }
        if (usuario.getSenha() == null || usuario.getSenha().length() < 6) {
            throw new IllegalArgumentException("A senha deve possuir pelo menos 6 caracteres.");
        }
        usuarioDAO.inserir(usuario);
    }
}
