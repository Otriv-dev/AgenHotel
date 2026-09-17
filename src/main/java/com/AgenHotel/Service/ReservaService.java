package com.AgenHotel.Service;
import com.AgenHotel.Dao.ReservaDAO;import com.AgenHotel.Model.Reserva;
import java.sql.SQLException;import java.util.List;
public class ReservaService {
    private final ReservaDAO dao=new ReservaDAO();
    public List<Reserva> listar(String busca)throws SQLException{return dao.listar(busca);}
    public List<Reserva> listar(String busca,String status,int quartoId)throws SQLException{return dao.listar(busca,status,quartoId);}
    public void reservar(Reserva r)throws SQLException{
        if(r.getHospede()==null||r.getHospede().getId()==0)throw new IllegalArgumentException("Selecione o hospede.");
        if(r.getQuarto()==null||r.getQuarto().getId()==0)throw new IllegalArgumentException("Selecione o quarto.");
        if(r.getDataCheckin()==null||r.getDataCheckout()==null)throw new IllegalArgumentException("Informe as datas.");
        if(r.getHoraCheckin()==null||r.getHoraCheckout()==null)throw new IllegalArgumentException("Informe os horarios.");
        if(r.getDataCheckin().isBefore(java.time.LocalDate.now()))throw new IllegalArgumentException("A data de entrada nao pode estar no passado.");
        if(!r.getDataCheckout().isAfter(r.getDataCheckin()))throw new IllegalArgumentException("A data de checkout deve ser posterior ao check-in.");
        java.time.LocalDateTime entrada=r.getDataCheckin().atTime(r.getHoraCheckin());
        java.time.LocalDateTime saida=r.getDataCheckout().atTime(r.getHoraCheckout());
        if(!saida.isAfter(entrada))throw new IllegalArgumentException("O checkout deve ser posterior ao check-in.");
        if(dao.existeConflito(r.getQuarto().getId(),entrada,saida))throw new IllegalArgumentException("O quarto ja possui reserva nesse periodo.");
        dao.inserir(r);
    }
    public void checkin(int id)throws SQLException{
        if(!"RESERVADA".equals(dao.buscarStatus(id)))throw new IllegalArgumentException("Somente reservas confirmadas permitem check-in.");
        dao.alterarStatus(id,"CHECK_IN");
    }
    public void checkout(int id)throws SQLException{
        if(!"CHECK_IN".equals(dao.buscarStatus(id)))throw new IllegalArgumentException("O check-in precisa ser realizado primeiro.");
        dao.alterarStatus(id,"CHECK_OUT");
    }
    public void cancelar(int id)throws SQLException{
        if("CHECK_OUT".equals(dao.buscarStatus(id)))throw new IllegalArgumentException("Uma hospedagem finalizada nao pode ser cancelada.");
        dao.alterarStatus(id,"CANCELADA");
    }
}
