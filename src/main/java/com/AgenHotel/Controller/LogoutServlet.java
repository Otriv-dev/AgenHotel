package com.AgenHotel.Controller;

import com.AgenHotel.Model.Usuario;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Usuario usuario = (Usuario) request.getSession().getAttribute("usuarioLogado");
        if (usuario != null) {
            System.out.println("[AgenHotel] Logout realizado: " + usuario.getEmail());
        }
        request.getSession().invalidate();
        response.sendRedirect(request.getContextPath() + "/login");
    }
}
