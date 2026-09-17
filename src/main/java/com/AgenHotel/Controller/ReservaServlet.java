package com.AgenHotel.Controller;
import com.AgenHotel.Model.*;import com.AgenHotel.Service.*;import jakarta.servlet.*;import jakarta.servlet.annotation.WebServlet;import jakarta.servlet.http.*;import java.io.IOException;import java.sql.SQLException;import java.time.LocalDate;
@WebServlet("/reservas") public class ReservaServlet extends HttpServlet{
    private static final long serialVersionUID=1L;private final ReservaService service=new ReservaService();private final HospedeService hospedes=new HospedeService();private final QuartoService quartos=new QuartoService();
    protected void doGet(HttpServletRequest req,HttpServletResponse resp)throws ServletException,IOException{
        String acao=req.getParameter("acao");
        try{
            if("novo".equals(acao)){
                Reserva reserva = new Reserva();
                String quartoId = req.getParameter("quartoId");
                if (quartoId != null && !quartoId.isBlank()) {
                    Quarto quarto = new Quarto();
                    quarto.setId(Integer.parseInt(quartoId));
                    reserva.setQuarto(quarto);
                }
                abrirForm(req,resp,reserva);return;
            }
            if(acao!=null&&req.getParameter("id")!=null){int id=Integer.parseInt(req.getParameter("id"));
                if("checkin".equals(acao))service.checkin(id);else if("checkout".equals(acao))service.checkout(id);else if("cancelar".equals(acao))service.cancelar(id);
                resp.sendRedirect(req.getContextPath()+"/reservas");return;}
            listar(req,resp);
        } catch (IllegalArgumentException e) {
            req.setAttribute("erro", e.getMessage());
            try {
                listar(req, resp);
            } catch (SQLException sqlException) {
                throw new ServletException("Nao foi possivel listar as reservas.", sqlException);
            }
        } catch (SQLException e) {
            throw new ServletException("Nao foi possivel concluir a operacao.", e);
        }
    }
    protected void doPost(HttpServletRequest req,HttpServletResponse resp)throws ServletException,IOException{
        req.setCharacterEncoding("UTF-8");Reserva r=new Reserva();Hospede h=new Hospede();Quarto q=new Quarto();
        try{h.setId(Integer.parseInt(req.getParameter("hospedeId")));q.setId(Integer.parseInt(req.getParameter("quartoId")));r.setHospede(h);r.setQuarto(q);r.setDataCheckin(LocalDate.parse(req.getParameter("dataCheckin")));r.setDataCheckout(LocalDate.parse(req.getParameter("dataCheckout")));service.reservar(r);resp.sendRedirect(req.getContextPath()+"/reservas");}
        catch(IllegalArgumentException|SQLException e){req.setAttribute("erro",e.getMessage());abrirForm(req,resp,r);}
    }
    private void listar(HttpServletRequest req,HttpServletResponse resp)throws SQLException,ServletException,IOException{
        String busca=req.getParameter("busca");String status=req.getParameter("status");int quartoId=0;
        try{if(req.getParameter("quartoId")!=null&&!req.getParameter("quartoId").isBlank())quartoId=Integer.parseInt(req.getParameter("quartoId"));}catch(NumberFormatException ignored){}
        req.setAttribute("busca",busca);req.setAttribute("statusSelecionado",status);req.setAttribute("quartoSelecionado",quartoId);
        req.setAttribute("quartos",quartos.listarTodos());req.setAttribute("reservas",service.listar(busca,status,quartoId));
        req.getRequestDispatcher("/WEB-INF/view/reservas/lista.jsp").forward(req,resp);
    }
    private void abrirForm(HttpServletRequest req,HttpServletResponse resp,Reserva r)throws ServletException,IOException{try{req.setAttribute("reserva",r);req.setAttribute("hoje",LocalDate.now());req.setAttribute("hospedes",hospedes.listar());req.setAttribute("quartos",quartos.listarTodos());req.getRequestDispatcher("/WEB-INF/view/reservas/form.jsp").forward(req,resp);}catch(SQLException e){throw new ServletException(e);}}
}
