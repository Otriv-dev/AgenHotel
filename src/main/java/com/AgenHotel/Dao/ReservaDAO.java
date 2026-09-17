package com.AgenHotel.Dao;

import com.AgenHotel.Config.JdbConnection;
import com.AgenHotel.Model.*;
import java.sql.*;
import java.util.*;

public class ReservaDAO {
    private final JdbConnection banco=JdbConnection.getInstance();
    public List<Reserva> listar(String busca)throws SQLException{return listar(busca,"",0);}
    public List<Reserva> listar(String busca,String status,int quartoId)throws SQLException{
        String sql="SELECT r.*,h.nome hospede_nome,h.email hospede_email,q.numero,q.tipo,q.capacidade,q.preco_diaria FROM reservas r JOIN hospedes h ON h.id=r.hospede_id JOIN quartos q ON q.id=r.quarto_id " +
                "WHERE (?='' OR h.nome LIKE ? OR h.email LIKE ? OR CAST(q.numero AS CHAR) LIKE ? OR r.status LIKE ?) " +
                "AND (?='' OR r.status=?) AND (?=0 OR q.id=?) ORDER BY r.data_checkin DESC";
        List<Reserva> lista=new ArrayList<>(); String t=busca==null?"":busca.trim();String s=status==null?"":status.trim();
        try(Connection c=banco.getConnection();PreparedStatement p=c.prepareStatement(sql)){
            p.setString(1,t);for(int i=2;i<=5;i++)p.setString(i,"%"+t+"%");
            p.setString(6,s);p.setString(7,s);p.setInt(8,quartoId);p.setInt(9,quartoId);
            try(ResultSet rs=p.executeQuery()){while(rs.next())lista.add(montar(rs));}
        }return lista;
    }
    public boolean existeConflito(int quartoId, java.time.LocalDateTime entrada, java.time.LocalDateTime saida)throws SQLException{
        String sql="SELECT COUNT(*) FROM reservas WHERE quarto_id=? AND status IN ('RESERVADA','CHECK_IN') " +
                "AND ? < TIMESTAMP(data_checkout,hora_checkout) AND ? > TIMESTAMP(data_checkin,hora_checkin)";
        try(Connection c=banco.getConnection();PreparedStatement p=c.prepareStatement(sql)){
            p.setInt(1,quartoId);p.setTimestamp(2,java.sql.Timestamp.valueOf(entrada));p.setTimestamp(3,java.sql.Timestamp.valueOf(saida));
            try(ResultSet r=p.executeQuery()){r.next();return r.getInt(1)>0;}
        }
    }
    public void inserir(Reserva r)throws SQLException{
        try(Connection c=banco.getConnection();PreparedStatement p=c.prepareStatement("INSERT INTO reservas(hospede_id,quarto_id,data_checkin,hora_checkin,data_checkout,hora_checkout,status) VALUES(?,?,?,?,?,?, 'RESERVADA')")){
            p.setInt(1,r.getHospede().getId());p.setInt(2,r.getQuarto().getId());p.setDate(3,java.sql.Date.valueOf(r.getDataCheckin()));p.setTime(4,java.sql.Time.valueOf(r.getHoraCheckin()));p.setDate(5,java.sql.Date.valueOf(r.getDataCheckout()));p.setTime(6,java.sql.Time.valueOf(r.getHoraCheckout()));p.executeUpdate();
        }
    }
    public void alterarStatus(int id,String status)throws SQLException{
        try(Connection c=banco.getConnection();PreparedStatement p=c.prepareStatement("UPDATE reservas SET status=? WHERE id=?")){p.setString(1,status);p.setInt(2,id);p.executeUpdate();}
    }
    public String buscarStatus(int id)throws SQLException{
        try(Connection c=banco.getConnection();PreparedStatement p=c.prepareStatement("SELECT status FROM reservas WHERE id=?")){p.setInt(1,id);try(ResultSet r=p.executeQuery()){return r.next()?r.getString(1):null;}}
    }
    private Reserva montar(ResultSet r)throws SQLException{
        Reserva x=new Reserva();x.setId(r.getInt("id"));
        x.setHospede(new Hospede(r.getInt("hospede_id"),r.getString("hospede_nome"),r.getString("hospede_email"),null));
        x.setQuarto(new Quarto(r.getInt("quarto_id"),r.getInt("numero"),r.getString("tipo"),r.getInt("capacidade"),r.getBigDecimal("preco_diaria"),null));
        x.setDataCheckin(r.getDate("data_checkin").toLocalDate());x.setHoraCheckin(r.getTime("hora_checkin").toLocalTime());
        x.setDataCheckout(r.getDate("data_checkout").toLocalDate());x.setHoraCheckout(r.getTime("hora_checkout").toLocalTime());x.setStatus(r.getString("status"));return x;
    }
}
