/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IServices;

import Models.CHUC_VU;
import Models.DON_VI_TINH;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author LENOVO
 */
public interface IChucVu_Service {

    public CHUC_VU readFromResultSet(ResultSet rs) throws SQLException;

    public List<CHUC_VU> select(String sql, Object... args);

    public List<CHUC_VU> selectAll();

    public List<CHUC_VU> selectByID(String ID);

    public CHUC_VU findById(String ID);

    public CHUC_VU findByNames(String Names);

    public void insert(CHUC_VU CHUC_VU);

    public void update(CHUC_VU CHUC_VU);

    public void delete(String ID);
}
