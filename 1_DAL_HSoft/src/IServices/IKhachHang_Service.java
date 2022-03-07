/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IServices;

import Models.KHACH_HANG;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author BUI_QUANG_HIEU
 */
public interface IKhachHang_Service {

    public KHACH_HANG readFromResultSet(ResultSet rs) throws SQLException;

    public List<KHACH_HANG> select(String sql, Object... args);

    public List<KHACH_HANG> selectAll();

    public List<KHACH_HANG> selectByID(String ID);

    public List<KHACH_HANG> selectByKeyword(String keyWord);

    public KHACH_HANG findById(String ID);

    public KHACH_HANG findByNames(String ID);

    public void insert(KHACH_HANG khach_hang);

    public void update(KHACH_HANG khach_hang);

    public void delete(String ID);

}
