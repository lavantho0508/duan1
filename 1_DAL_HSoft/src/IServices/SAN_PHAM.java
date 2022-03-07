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
public interface SAN_PHAM {
    public Models.SAN_PHAM_CT readFromResultSet(ResultSet rs) throws SQLException;

    public List<Models.SAN_PHAM_CT> select(String sql, Object... args);

    public List<Models.SAN_PHAM_CT> selectAll();
    
    public List<Models.SAN_PHAM_CT> selectByID(String SAN_PHAM);

    public Models.SAN_PHAM_CT findById(String SAN_PHAM);

    public void insert(Models.SAN_PHAM_CT SAN_PHAM);

    public void update(Models.SAN_PHAM_CT SAN_PHAM);

    public void delete(String SAN_PHAM);
}
