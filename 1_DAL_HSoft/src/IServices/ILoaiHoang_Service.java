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
public interface ILoaiHoang_Service {
    
    public Models.LOAI_HANG readFromResultSet(ResultSet rs) throws SQLException;

    public List<Models.LOAI_HANG> select(String sql, Object... args);

    public List<Models.LOAI_HANG> selectAll();
    
    public List<Models.LOAI_HANG> selectByID(String LOAI_HANG);

    public Models.LOAI_HANG findById(String LOAI_HANG);
    
    public Models.LOAI_HANG findByNames(String Names);

    public void insert(Models.LOAI_HANG LOAI_HANG);

    public void update(Models.LOAI_HANG LOAI_HANG);

    public void delete(String LOAI_HANG);
}
