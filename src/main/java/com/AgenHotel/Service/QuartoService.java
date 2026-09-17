package com.AgenHotel.Service;
import com.AgenHotel.Dao.QuartoDAO;
import com.AgenHotel.Model.Quarto;
import java.sql.SQLException;import java.util.List;
public class QuartoService {
    private final QuartoDAO dao=new QuartoDAO();
    public List<Quarto> listar(String busca)throws SQLException{return dao.listar(busca);}
    public List<Quarto> listarTodos()throws SQLException{return dao.listarTodos();}
    public Quarto buscar(int id)throws SQLException{return dao.buscar(id);}
}
