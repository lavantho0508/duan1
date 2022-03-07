/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IServices;

import Models.NHAN_VIEN;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author LENOVO
 */
public interface INhanVien_Service {

    public Models.NHAN_VIEN readFromResultSet(ResultSet rs) throws SQLException;

    public List<Models.NHAN_VIEN> select(String sql, Object... args);

    public List<Models.NHAN_VIEN> selectAll();

    public String randomCode();

    public List<NHAN_VIEN> selectEmailByID(String ma);

    public int changePassWord(String ma, String pass);

    public List<Models.NHAN_VIEN> selectByID(String ID);

    public Models.NHAN_VIEN findById(String ID);

    public Models.NHAN_VIEN findByName(String Names);

    public void insert(Models.NHAN_VIEN NHAN_VIEN);

    public void update(Models.NHAN_VIEN NHAN_VIEN);

    public void delete(String ID);
}
