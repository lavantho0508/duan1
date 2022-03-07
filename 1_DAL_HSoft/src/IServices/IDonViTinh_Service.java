/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IServices;

import Models.DON_VI_TINH;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author LENOVO
 */
public interface IDonViTinh_Service {

    public DON_VI_TINH readFromResultSet(ResultSet rs) throws SQLException;

    public List<DON_VI_TINH> select(String sql, Object... args);

    public List<DON_VI_TINH> selectAll();

    public List<DON_VI_TINH> selectByID(String ID);

    public DON_VI_TINH findById(String ID);

    public DON_VI_TINH findByNames(String Names);

    public void insert(DON_VI_TINH DON_VI_TINH);

    public void update(DON_VI_TINH DON_VI_TINH);

    public void delete(String ID);
}
