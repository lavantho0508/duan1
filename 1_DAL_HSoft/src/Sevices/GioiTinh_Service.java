/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sevices;

import Helper.JdbcHelper;
import IServices.IGioiTinh_Service;
import Models.GIOI_TINH;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author BUI_QUANG_HIEU
 */
public class GioiTinh_Service implements IGioiTinh_Service{

    @Override
    public GIOI_TINH readFromResultSet(ResultSet rs) throws SQLException {
        GIOI_TINH model = new GIOI_TINH();
        model.setMAGT(rs.getString("MAGT"));
        model.setGIOITINH(rs.getString("GIOITINH"));
        model.setTrangThai(rs.getInt("TRANGTHAI"));
        return model;
    }

    @Override
    public List<GIOI_TINH> select(String sql, Object... args) {
        List<GIOI_TINH> list = new ArrayList<>();
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
    public List<GIOI_TINH> selectAll() {
        String sql = "select * from GIOI_TINH";
        return select(sql);
    }

    @Override
    public List<GIOI_TINH> selectByID(String ID) {
        String sql = "select * from GIOI_TINH where MAGT like ?";
        return select(sql, ID);
    }

    @Override
    public GIOI_TINH findById(String ID) {
        String sql = "select * from GIOI_TINH where MAGT like ?";
        List<GIOI_TINH> list = select(sql, ID);
        return list.size() > 0 ? list.get(0) : null;
    }

    @Override
    public GIOI_TINH findByNames(String Names) {
        String sql = "select * from GIOI_TINH where GIOITINH like ?";
        List<GIOI_TINH> list = select(sql, Names);
        return list.size() > 0 ? list.get(0) : null;
    }

    @Override
    public void insert(GIOI_TINH GIOI_TINH) {
        String sql = "insert into GIOI_TINH(MAGT, GIOITINH,TRANGTHAI) values(?, ? ,?)";
        Helper.JdbcHelper.executeUpdate(sql,
                GIOI_TINH.getMAGT(),
                GIOI_TINH.getGIOITINH(),
                GIOI_TINH.getTrangThai()
        );
    }

    @Override
    public void update(GIOI_TINH GIOI_TINH) {
        String sql = "update GIOI_TINH set GIOITINH=?,TRANGTHAI=? where MAGT like ? ";
        Helper.JdbcHelper.executeUpdate(sql,
                GIOI_TINH.getGIOITINH(),
                GIOI_TINH.getTrangThai(),
                GIOI_TINH.getMAGT()
        );
    }

    @Override
    public void delete(String GIOI_TINH) {
        String sql = "delete from GIOI_TINH where MAGT like ? ";
        Helper.JdbcHelper.executeUpdate(sql, GIOI_TINH);
    }

    @Override
    public int countIDGioiTinh(String ID) {
        String sql = "select count(*) from CHI_TIET_SAN_PHAM where MAGT like ?";
        int count = 0;
        try {
            ResultSet rs = null;
            try {
                rs = Helper.JdbcHelper.executeQuery(sql, ID);
                while (rs.next()) {
                    count = rs.getInt(1);
                }
            } finally {
                rs.getStatement().getConnection().close();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new RuntimeException();
        }
        return count;
    }
    
}
