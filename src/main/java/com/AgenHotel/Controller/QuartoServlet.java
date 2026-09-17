package com.AgenHotel.Controller;

import com.AgenHotel.Model.Quarto;
import com.AgenHotel.Service.QuartoService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/quartos")
public class QuartoServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final QuartoService service = new QuartoService();

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            List<Quarto> todos = service.listarTodos();
            String tipo = req.getParameter("tipo") == null ? "" : req.getParameter("tipo");
            String situacao = req.getParameter("situacao") == null ? "" : req.getParameter("situacao");
            List<Quarto> quartos = service.listar(req.getParameter("busca")).stream()
                    .filter(q -> tipo.isBlank() || tipo.equals(q.getTipo()))
                    .filter(q -> situacao.isBlank() || situacao.equals(q.getSituacao()))
                    .toList();
            long livres = todos.stream().filter(q -> "LIVRE".equals(q.getSituacao())).count();
            long reservados = todos.stream().filter(q -> "RESERVADO".equals(q.getSituacao())).count();
            long ocupados = todos.stream().filter(q -> "OCUPADO".equals(q.getSituacao())).count();

            req.setAttribute("busca", req.getParameter("busca"));
            req.setAttribute("quartos", quartos);
            req.setAttribute("tipoSelecionado", tipo);
            req.setAttribute("situacaoSelecionada", situacao);
            req.setAttribute("livres", livres);
            req.setAttribute("reservados", reservados);
            req.setAttribute("ocupados", ocupados);
            req.getRequestDispatcher("/WEB-INF/view/quartos/lista.jsp").forward(req, resp);
        } catch (SQLException e) {
            throw new ServletException("Nao foi possivel listar os quartos.", e);
        }
    }
}
