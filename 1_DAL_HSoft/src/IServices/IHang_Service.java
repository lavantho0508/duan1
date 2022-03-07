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
public interface IHang_Service {
    public Models.HANG readFromResultSet(ResultSet rs) throws SQLException;

    public List<Models.HANG> select(String sql, Object... args);

    public List<Models.HANG> selectAll();
    
    public List<Models.HANG> selectByID(String HANG);

    public Models.HANG findById(String HANG);
    
    public Models.HANG findByNames(String Names);

    public void insert(Models.HANG HANG);

    public void update(Models.HANG HANG);

    public void delete(String HANG);
    
    public int countByIDHang(String IDHang);
}
