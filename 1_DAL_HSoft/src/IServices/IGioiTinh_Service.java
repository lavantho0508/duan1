/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IServices;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author LENOVO
 */
public interface IGioiTinh_Service {
    public Models.GIOI_TINH readFromResultSet(ResultSet rs) throws SQLException;

    public List<Models.GIOI_TINH> select(String sql, Object... args);

    public List<Models.GIOI_TINH> selectAll();
    
    public List<Models.GIOI_TINH> selectByID(String ID);

    public Models.GIOI_TINH findById(String ID);
    
    public Models.GIOI_TINH findByNames(String Names);

    public void insert(Models.GIOI_TINH GIOI_TINH);

    public void update(Models.GIOI_TINH GIOI_TINH);

    public void delete(String GIOI_TINH);
    
    public int countIDGioiTinh(String ID);
}
