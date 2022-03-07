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
public interface IMauSac_Service {

    public Models.MAU_SAC readFromResultSet(ResultSet rs) throws SQLException;

    public List<Models.MAU_SAC> select(String sql, Object... args);

    public List<Models.MAU_SAC> selectAll();

    public List<Models.MAU_SAC> selectByID(String ID);

    public Models.MAU_SAC findById(String ID);

    public Models.MAU_SAC findByNames(String Names);

    public void insert(Models.MAU_SAC MAU_SAC);

    public void update(Models.MAU_SAC MAU_SAC);

    public void delete(String ID);
}
