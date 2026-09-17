package com.AgenHotel.Controller;

import com.AgenHotel.Model.Hospede;
import com.AgenHotel.Service.HospedeService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/hospedes")
public class HospedeServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private final HospedeService hospedeService = new HospedeService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String acao = request.getParameter("acao");

        try {
            if ("novo".equals(acao)) {
                abrirFormulario(request, response, new Hospede());
            } else if ("editar".equals(acao)) {
                int id = Integer.parseInt(request.getParameter("id"));
                Hospede hospede = hospedeService.buscarPorId(id);

                if (hospede == null) {
                    response.sendRedirect(request.getContextPath() + "/hospedes");
                    return;
                }
                abrirFormulario(request, response, hospede);
            } else if ("excluir".equals(acao)) {
                int id = Integer.parseInt(request.getParameter("id"));
                hospedeService.excluir(id);
                response.sendRedirect(request.getContextPath() + "/hospedes");
            } else {
                listar(request, response);
            }
        } catch (SQLException | NumberFormatException e) {
            throw new ServletException("Nao foi possivel concluir a operacao.", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        Hospede hospede = new Hospede();
        String id = request.getParameter("id");
        if (id != null && !id.isBlank()) {
            hospede.setId(Integer.parseInt(id));
        }
        hospede.setNome(request.getParameter("nome"));
        hospede.setEmail(request.getParameter("email"));
        hospede.setTelefone(request.getParameter("telefone"));

        try {
            hospedeService.salvar(hospede);
            response.sendRedirect(request.getContextPath() + "/hospedes");
        } catch (IllegalArgumentException e) {
            request.setAttribute("erro", e.getMessage());
            abrirFormulario(request, response, hospede);
        } catch (SQLException e) {
            request.setAttribute("erro", "Nao foi possivel salvar. Verifique se o e-mail ja esta cadastrado.");
            abrirFormulario(request, response, hospede);
        }
    }

    private void listar(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        request.setAttribute("hospedes", hospedeService.listar());
        request.getRequestDispatcher("/WEB-INF/view/hospedes/lista.jsp").forward(request, response);
    }

    private void abrirFormulario(HttpServletRequest request, HttpServletResponse response, Hospede hospede)
            throws ServletException, IOException {
        request.setAttribute("hospede", hospede);
        request.getRequestDispatcher("/WEB-INF/view/hospedes/form.jsp").forward(request, response);
    }
}
