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
public interface IKieuDang_Service {
        public Models.KIEU_DANG readFromResultSet(ResultSet rs) throws SQLException;

    public List<Models.KIEU_DANG> select(String sql, Object... args);

    public List<Models.KIEU_DANG> selectAll();
    
    public List<Models.KIEU_DANG> selectByID(String KIEU_DANG);

    public Models.KIEU_DANG findById(String ID);
    
    public Models.KIEU_DANG findByNames(String Names);

    public void insert(Models.KIEU_DANG KIEU_DANG);

    public void update(Models.KIEU_DANG KIEU_DANG);

    public void delete(String KIEU_DANG);
}
