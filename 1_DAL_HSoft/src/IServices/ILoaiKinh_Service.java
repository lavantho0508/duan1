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
public interface ILoaiKinh_Service {
    
    public Models.LOAI_KINH readFromResultSet(ResultSet rs) throws SQLException;

    public List<Models.LOAI_KINH> select(String sql, Object... args);

    public List<Models.LOAI_KINH> selectAll();
    
    public List<Models.LOAI_KINH> selectByID(String ID);

    public Models.LOAI_KINH findById(String ID);
    
    public Models.LOAI_KINH findByNames(String Names);

    public void insert(Models.LOAI_KINH LOAI_KINH);

    public void update(Models.LOAI_KINH LOAI_KINH);

    public void delete(String LOAI_KINH);
}
