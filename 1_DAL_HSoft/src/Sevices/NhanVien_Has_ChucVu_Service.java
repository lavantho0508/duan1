/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sevices;

import Helper.JdbcHelper;
import IServices.INhanVien_has_ChucVu_Service;
import Models.NV_HAS_CV;
import Models.SAN_PHAM;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author LENOVO
 */
public class NhanVien_Has_ChucVu_Service implements IServices.INhanVien_has_ChucVu_Service {

    @Override
    public NV_HAS_CV readFromResultSet(ResultSet rs) throws SQLException {
        NV_HAS_CV model = new NV_HAS_CV();
        model.setMACV(rs.getString("MACV"));
        model.setMANV(rs.getString("MANV"));
        model.setTRANGTHAI(rs.getInt("TRANGTHAI"));
        return model;
    }

    @Override
    public List<NV_HAS_CV> select(String sql, Object... args) {
        List<NV_HAS_CV> list = new ArrayList<>();
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
    public List<NV_HAS_CV> selectAll() {
        String sql = "select * from NV_HAS_CV";
        return select(sql);
    }

    @Override
    public List<NV_HAS_CV> selectByID(String ID) {
        String sql = "select * from NV_HAS_CV where MANV like ?";
        return select(sql, ID);
    }

    @Override
    public NV_HAS_CV findById(String ID) {
        String sql = "select * from NV_HAS_CV where MANV like ?";
        List<Models.NV_HAS_CV> list = select(sql, ID);
        return list.size() > 0 ? list.get(0) : null;
    }

    @Override
    public void insert(NV_HAS_CV NV_HAS_CV) {
        String sql = "insert into NV_HAS_CV(MACV, MANV, TRANGTHAI) values(?, ? ,?)";
        Helper.JdbcHelper.executeUpdate(sql,
                NV_HAS_CV.getMACV(),
                NV_HAS_CV.getMANV(),
                NV_HAS_CV.getTRANGTHAI()
        );
    }

    @Override
    public void update(NV_HAS_CV NV_HAS_CV) {
        String sql = "update NV_HAS_CV set TRANGTHAI=? where manv like ? and MACV like ?";
        Helper.JdbcHelper.executeUpdate(sql,
                NV_HAS_CV.getTRANGTHAI(),
                NV_HAS_CV.getMANV(),
                NV_HAS_CV.getMACV()
        );
    }

    @Override
    public void delete(String ID) {
        String sql = "delete from NV_HAS_CV where manv like ?";
        Helper.JdbcHelper.executeUpdate(sql, ID);
    }

}
