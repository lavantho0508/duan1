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
public interface IKichThuoc_Service {

    public Models.KICH_THUOC readFromResultSet(ResultSet rs) throws SQLException;

    public List<Models.KICH_THUOC> select(String sql, Object... args);

    public List<Models.KICH_THUOC> selectAll();

    public List<Models.KICH_THUOC> selectByID(String KICH_THUOC);

    public Models.KICH_THUOC findById(String ID);

    public Models.KICH_THUOC findByNames(String KichThuoc);

    public void insert(Models.KICH_THUOC KICH_THUOC);

    public void update(Models.KICH_THUOC KICH_THUOC);

    public void delete(String ID);
}
