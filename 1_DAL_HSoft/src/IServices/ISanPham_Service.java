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
public interface ISanPham_Service {

    public Models.SAN_PHAM readFromResultSet(ResultSet rs) throws SQLException;

    public List<Models.SAN_PHAM> select(String sql, Object... args);

    public List<Models.SAN_PHAM> selectAll();

    public List<Models.SAN_PHAM> selectByID(String ID);

    public Models.SAN_PHAM findById(String ID);

    public Models.SAN_PHAM findByName(String Names);

    public void insert(Models.SAN_PHAM SAN_PHAM);

    public void update(Models.SAN_PHAM SAN_PHAM);

    public void updateHangNull(String maHang);

    public void delete(String ID);
}
