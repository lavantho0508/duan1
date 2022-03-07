/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sevices;

import Helper.JdbcHelper;
import IServices.ISanPham_Service;
import Models.SAN_PHAM;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author BUI_QUANG_HIEU
 */
public class SanPham_Service implements ISanPham_Service {

    @Override
    public SAN_PHAM readFromResultSet(ResultSet rs) throws SQLException {
        SAN_PHAM model = new SAN_PHAM();
        model.setMASP(rs.getString("MASP"));
        model.setMAHANG(rs.getString("MAHANG"));
        model.setTENSP(rs.getString("TENSP"));
        model.setTRANGTHAI(rs.getInt("TRANGTHAI"));
        return model;
    }

    @Override
    public List<SAN_PHAM> select(String sql, Object... args) {
        List<SAN_PHAM> list = new ArrayList<>();
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
    public List<SAN_PHAM> selectAll() {
        String sql = "select * from SAN_PHAM";
        return select(sql);
    }

    @Override
    public List<SAN_PHAM> selectByID(String ID) {
        String sql = "select * from SAN_PHAM where MASP like ?";
        return select(sql, ID);
    }

    @Override
    public SAN_PHAM findById(String ID) {
        String sql = "select * from SAN_PHAM where MASP like ?";
        List<SAN_PHAM> list = select(sql, ID);
        return list.size() > 0 ? list.get(0) : null;
    }

    @Override
    public SAN_PHAM findByName(String Names) {
        String sql = "select * from SAN_PHAM where TENSP like ?";
        List<SAN_PHAM> list = select(sql, Names);
        return list.size() > 0 ? list.get(0) : null;
    }

    @Override
    public void insert(SAN_PHAM SAN_PHAM) {
        String sql = "insert into SAN_PHAM(MASP, MAHANG,TENSP, TRANGTHAI) values(?, ? ,?, ?)";
        Helper.JdbcHelper.executeUpdate(sql,
                SAN_PHAM.getMASP(),
                SAN_PHAM.getMAHANG(),
                SAN_PHAM.getTENSP(),
                SAN_PHAM.getTRANGTHAI()
        );
    }

    @Override
    public void update(SAN_PHAM SAN_PHAM) {
        String sql = "update SAN_PHAM set MAHANG=?, TENSP=?, TRANGTHAI=? where MASP like ? ";
        Helper.JdbcHelper.executeUpdate(sql,
                SAN_PHAM.getMAHANG(),
                SAN_PHAM.getTENSP(),
                SAN_PHAM.getTRANGTHAI(),
                SAN_PHAM.getMASP()
        );
    }

    @Override
    public void delete(String ID) {
        String sql = "delete from SAN_PHAM where MASP like ? ";
        Helper.JdbcHelper.executeUpdate(sql, ID);
    }

    @Override
    public void updateHangNull(String maHang) {
        String sql = "update SAN_PHAM set MAHANG = '' where MAHANG = ?";
        Helper.JdbcHelper.executeUpdate(sql,
                maHang
        );
    }

}
