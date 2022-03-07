/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sevices;

import Helper.JdbcHelper;
import IServices.IDonViTinh_Service;
import Models.CHI_TIET_SAN_PHAM;
import Models.DON_VI_TINH;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author BUI_QUANG_HIEU
 */
public class DonViTinh_Service implements IDonViTinh_Service {

    @Override
    public DON_VI_TINH readFromResultSet(ResultSet rs) throws SQLException {
        DON_VI_TINH model = new DON_VI_TINH();
        model.setMADV(rs.getString("MADV"));
        model.setTEN(rs.getString("TEN"));
        model.setTRANGTHAI(rs.getInt("TRANGTHAI"));
        return model;
    }

    @Override
    public List<DON_VI_TINH> select(String sql, Object... args) {
        List<DON_VI_TINH> list = new ArrayList<>();
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
    public List<DON_VI_TINH> selectAll() {
        String sql = "select * from DON_VI_TINH";
        return select(sql);
    }

    @Override
    public List<DON_VI_TINH> selectByID(String DON_VI_TINH) {
        String sql = "select * from DON_VI_TINH where MADV like ?";
        return select(sql, DON_VI_TINH);
    }

    @Override
    public DON_VI_TINH findById(String DON_VI_TINH) {
        String sql = "select * from DON_VI_TINH where MADV like ?";
        List<DON_VI_TINH> list = select(sql, DON_VI_TINH);
        return list.size() > 0 ? list.get(0) : null;
    }

    @Override
    public void insert(DON_VI_TINH DON_VI_TINH) {
        String sql = "insert into DON_VI_TINH(MADV, TEN,TRANGTHAI) values(?, ? ,?)";
        Helper.JdbcHelper.executeUpdate(sql,
                DON_VI_TINH.getMADV(),
                DON_VI_TINH.getTEN(),
                DON_VI_TINH.getTRANGTHAI()
        );
    }

    @Override
    public void update(DON_VI_TINH DON_VI_TINH) {
        String sql = "update DON_VI_TINH set TEN=?,TRANGTHAI=? where MADV like ? ";
        Helper.JdbcHelper.executeUpdate(sql,
                DON_VI_TINH.getTEN(),
                DON_VI_TINH.getTRANGTHAI(),
                DON_VI_TINH.getMADV()
        );
    }

    @Override
    public void delete(String DON_VI_TINH) {
        String sql = "delete from DON_VI_TINH where MADV like ? ";
        Helper.JdbcHelper.executeUpdate(sql, DON_VI_TINH);
    }

    @Override
    public DON_VI_TINH findByNames(String Names) {
        String sql = "select * from DON_VI_TINH where TEN like ?";
        List<DON_VI_TINH> list = select(sql, Names);
        return list.size() > 0 ? list.get(0) : null;
    }

}
