package com.AgenHotel.Controller;

import com.AgenHotel.Model.Usuario;
import com.AgenHotel.Service.UsuarioService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private final UsuarioService usuarioService = new UsuarioService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (request.getSession().getAttribute("usuarioLogado") != null) {
            response.sendRedirect(request.getContextPath() + "/hospedes");
            return;
        }

        Object mensagem = request.getSession().getAttribute("mensagem");
        if (mensagem != null) {
            request.setAttribute("mensagem", mensagem);
            request.getSession().removeAttribute("mensagem");
        }
        request.getRequestDispatcher("/WEB-INF/view/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        try {
            Usuario usuario = usuarioService.autenticar(
                    request.getParameter("email"),
                    request.getParameter("senha")
            );

            if (usuario == null) {
                System.out.println("[AgenHotel] Login recusado para: " + request.getParameter("email"));
                request.setAttribute("erro", "E-mail ou senha incorretos.");
                request.setAttribute("email", request.getParameter("email"));
                request.getRequestDispatcher("/WEB-INF/view/login.jsp").forward(request, response);
                return;
            }

            usuario.setSenha(null);
            request.getSession().setAttribute("usuarioLogado", usuario);
            System.out.println("[AgenHotel] Login realizado: " + usuario.getEmail());
            response.sendRedirect(request.getContextPath() + "/hospedes");
        } catch (SQLException e) {
            System.out.println("[AgenHotel] Erro ao consultar usuario no login.");
            throw new ServletException("Não foi possível consultar o usuário.", e);
        }
    }
}
