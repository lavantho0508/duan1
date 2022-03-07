/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sevices;

import Helper.JdbcHelper;
import IServices.IKieuDang_Service;
import Models.KIEU_DANG;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author BUI_QUANG_HIEU
 */
public class KieuDang_Service implements IKieuDang_Service {

    @Override
    public KIEU_DANG readFromResultSet(ResultSet rs) throws SQLException {
        KIEU_DANG model = new KIEU_DANG();
        model.setMAKIEUDANG(rs.getString("MAKIEUDANG"));
        model.setKIEUDANG(rs.getString("KIEUDANG"));
        model.setTrangThai(rs.getInt("TRANGTHAI"));
        return model;
    }

    @Override
    public List<KIEU_DANG> select(String sql, Object... args) {
        List<KIEU_DANG> list = new ArrayList<>();
        try {
            ResultSet rs = null;
            try {
                rs = JdbcHelper.executeQuery(sql, args);
                while (rs.next()) {
                    list.add(readFromResultSet(rs));
                }
            } finally {
                rs.getStatement().getConnection().close();      //đóng kết nối từ resultSet
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new RuntimeException();
        }
        return list;
    }

    @Override
    public List<KIEU_DANG> selectAll() {
        String sql = "select * from KIEU_DANG";
        return select(sql);
    }

    @Override
    public List<KIEU_DANG> selectByID(String KIEU_DANG) {
        String sql = "select * from KIEU_DANG where MAKIEUDANG like ?";
        return select(sql, KIEU_DANG);
    }

    @Override
    public KIEU_DANG findById(String ID) {
        String sql = "select * from KIEU_DANG where MAKIEUDANG like ?";
        List<KIEU_DANG> list = select(sql, ID);
        return list.size() > 0 ? list.get(0) : null;
    }

    @Override
    public KIEU_DANG findByNames(String Names) {
        String sql = "select * from KIEU_DANG where KIEUDANG like ?";
        List<KIEU_DANG> list = select(sql, Names);
        return list.size() > 0 ? list.get(0) : null;
    }

    @Override
    public void insert(KIEU_DANG KIEU_DANG) {
        String sql = "insert into KIEU_DANG(MAKIEUDANG, KIEUDANG,TRANGTHAI) values(?, ? ,?)";
        Helper.JdbcHelper.executeUpdate(sql,
                KIEU_DANG.getMAKIEUDANG(),
                KIEU_DANG.getKIEUDANG(),
                KIEU_DANG.getTrangThai()
        );
    }

    @Override
    public void update(KIEU_DANG KIEU_DANG) {
        String sql = "update KIEU_DANG set KIEUDANG=?,TRANGTHAI=? where MAKIEUDANG like ? ";
        Helper.JdbcHelper.executeUpdate(sql,
                KIEU_DANG.getKIEUDANG(),
                KIEU_DANG.getTrangThai(),
                KIEU_DANG.getMAKIEUDANG()
        );
    }

    @Override
    public void delete(String KIEU_DANG) {
        String sql = "delete from KIEU_DANG where MAKIEUDANG like ? ";
        Helper.JdbcHelper.executeUpdate(sql, KIEU_DANG);
    }

}
