package com.AgenHotel.Dao;

import com.AgenHotel.Config.JdbConnection;
import com.AgenHotel.Model.Quarto;
import java.sql.*;
import java.util.*;

public class QuartoDAO {
    private final JdbConnection banco=JdbConnection.getInstance();
    public List<Quarto> listar(String busca) throws SQLException {
        String sql="SELECT q.*, CASE WHEN EXISTS (SELECT 1 FROM reservas r WHERE r.quarto_id=q.id AND r.status='CHECK_IN') THEN 'OCUPADO' " +
                "WHEN EXISTS (SELECT 1 FROM reservas r WHERE r.quarto_id=q.id AND r.status='RESERVADA' AND CURDATE()<r.data_checkout) THEN 'RESERVADO' ELSE 'LIVRE' END situacao " +
                "FROM quartos q WHERE (?='' OR CAST(q.numero AS CHAR) LIKE ? OR q.tipo LIKE ?) ORDER BY q.numero";
        List<Quarto> lista=new ArrayList<>(); String termo=busca==null?"":busca.trim();
        try(Connection c=banco.getConnection(); PreparedStatement p=c.prepareStatement(sql)){
            p.setString(1,termo); p.setString(2,"%"+termo+"%"); p.setString(3,"%"+termo+"%");
            try(ResultSet r=p.executeQuery()){while(r.next())lista.add(montar(r));}
        } return lista;
    }
    public List<Quarto> listarTodos() throws SQLException { return listar(""); }
    public Quarto buscar(int id) throws SQLException {
        try(Connection c=banco.getConnection(); PreparedStatement p=c.prepareStatement("SELECT q.*, 'LIVRE' situacao FROM quartos q WHERE id=?")){
            p.setInt(1,id); try(ResultSet r=p.executeQuery()){return r.next()?montar(r):null;}
        }
    }
    private Quarto montar(ResultSet r)throws SQLException{return new Quarto(r.getInt("id"),r.getInt("numero"),r.getString("tipo"),r.getInt("capacidade"),r.getBigDecimal("preco_diaria"),r.getString("situacao"));}
}
