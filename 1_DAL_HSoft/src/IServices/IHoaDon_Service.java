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
 * @author BUI_QUANG_HIEU
 */
public interface IHoaDon_Service {

    public Models.HOA_DON readFromResultSet(ResultSet rs) throws SQLException;

    public List<Models.HOA_DON> select(String sql, Object... args);

    public List<Models.HOA_DON> selectAll();

    public List<Models.HOA_DON> selectByID(String ID);

    public Models.HOA_DON findById(String ID);

    public Models.HOA_DON findByName(String Names);

    public void insert(Models.HOA_DON hoa_don);

    public void update(Models.HOA_DON hoa_don);

    public void delete(String ID);

    public int countMaHD(String SoHoaDon);

    public List<Models.HOA_DON> GetBySoHoaDon(String SoHoaDon);
}
