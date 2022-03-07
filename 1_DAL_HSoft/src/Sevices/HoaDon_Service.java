/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sevices;

import Helper.JdbcHelper;
import IServices.IHoaDon_Service;
import Models.HOA_DON;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author BUI_QUANG_HIEU
 */
public class HoaDon_Service implements IHoaDon_Service {

    @Override
    public HOA_DON readFromResultSet(ResultSet rs) throws SQLException {

        Models.HOA_DON model = new Models.HOA_DON();
        model.setMAHD(rs.getString("MAHD"));
        model.setMANV(rs.getString("MANV"));
        model.setMAKH(rs.getString("MAKH"));
        model.setNGAYLAP(rs.getDate("NGAYLAP"));
        model.setTRANGTHAI(rs.getInt("TRANGTHAI"));

        return model;
    }

    @Override
    public List<HOA_DON> select(String sql, Object... args) {
        List<Models.HOA_DON> list = new ArrayList<>();
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
    public List<HOA_DON> selectAll() {
        String sql = "select * from HOA_DON";
        return select(sql);
    }

    @Override
    public List<HOA_DON> selectByID(String ID) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public HOA_DON findById(String ID) {
        String sql = "select * from HOA_DON where MAHD like ?";
        List<Models.HOA_DON> list = select(sql, ID);
        return list.size() > 0 ? list.get(0) : null;
    }

    @Override
    public HOA_DON findByName(String Names) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void insert(HOA_DON hoa_don) {
        String sql = "insert into HOA_DON values(?,?,?,?,?)";
        Helper.JdbcHelper.executeUpdate(sql,
                hoa_don.getMAHD(),
                hoa_don.getMANV(),
                hoa_don.getMAKH(),
                hoa_don.getNGAYLAP(),
                hoa_don.getTRANGTHAI());
    }

    @Override
    public void update(HOA_DON hoa_don) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(String ID) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int countMaHD(String SoHoaDon) {
        String sql = "select Count(*) from HOA_DON where MAHD like N'%" + SoHoaDon + "%'";
        int count = 0;
        try {
            ResultSet rs = null;
            try {
                rs = Helper.JdbcHelper.executeQuery(sql);
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

    @Override
    public List<Models.HOA_DON> GetBySoHoaDon(String SoHoaDon) {
        String sql = "select * from HOA_DON where MAHD = N'" + SoHoaDon + "'";
        return select(sql);
    }

}
