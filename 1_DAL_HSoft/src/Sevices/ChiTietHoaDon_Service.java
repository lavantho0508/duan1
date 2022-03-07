/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sevices;

import Helper.JdbcHelper;
import Models.CHI_TIET_HOA_DON;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author LENOVO
 */
public class ChiTietHoaDon_Service implements IServices.IChiTietHoaDon_Service {

    @Override
    public Models.CHI_TIET_HOA_DON readFromResultSet(ResultSet rs) throws SQLException {
        Models.CHI_TIET_HOA_DON model = new Models.CHI_TIET_HOA_DON();
        model.setMACTHD(rs.getInt("MACTHD"));
        model.setMAHD(rs.getString("MAHD"));
        model.setMACTSP(rs.getInt("MACTSP"));
        model.setSOLUONG(rs.getInt("SOLUONG"));
        model.setDONGIA(rs.getDouble("DONGIA"));
        model.setTHANHTIEN(rs.getDouble("THANHTIEN"));

        model.setTRANGTHAI(rs.getInt("TRANGTHAI"));
        return model;
    }

    @Override
    public List<Models.CHI_TIET_HOA_DON> select(String sql, Object... args) {
        List<Models.CHI_TIET_HOA_DON> list = new ArrayList<>();
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
    public List<Models.CHI_TIET_HOA_DON> selectAll() {
        String sql = "select * from CHI_TIET_HOA_DON";
        return select(sql);
    }

    @Override
    public List<Models.CHI_TIET_HOA_DON> selectByID(String CHI_TIET_HOA_DON) {
        String sql = "select * from CHI_TIET_HOA_DON where MACTHD like ?";
        return select(sql, CHI_TIET_HOA_DON);
    }

    @Override
    public Models.CHI_TIET_HOA_DON findById(String CHITIETHOADON) {
        String sql = "select * from CHI_TIET_HOA_DON where MAHD like ?";
        List<Models.CHI_TIET_HOA_DON> list = select(sql, CHITIETHOADON);
        return list.size() > 0 ? list.get(0) : null;
    }

    @Override
    public void insert(Models.CHI_TIET_HOA_DON CHI_TIET_HOA_DON) {
        String sql = "SET IDENTITY_INSERT [dbo].CHI_TIET_HOA_DON ON insert into CHI_TIET_HOA_DON(MACTHD,MAHD,MACTSP,SOLUONG,DONGIA"
                + ",THANHTIEN, TRANGTHAI) values(?,?,?,?,?,?,?)";

        Helper.JdbcHelper.executeUpdate(sql,
                CHI_TIET_HOA_DON.getMACTHD(),
                CHI_TIET_HOA_DON.getMAHD(),
                CHI_TIET_HOA_DON.getMACTSP(),
                CHI_TIET_HOA_DON.getSOLUONG(),
                CHI_TIET_HOA_DON.getDONGIA(),
                CHI_TIET_HOA_DON.getTHANHTIEN(),
                CHI_TIET_HOA_DON.getTRANGTHAI());
//        Helper.JdbcHelper.executeUpdate(sql,"5", "211119018", "9", "2", "50","100", "0");
    }

    @Override
    public void update(Models.CHI_TIET_HOA_DON CHI_TIET_HOA_DON) {
        String sql = "update CHI_TIET_HOA_DON set SOLUONG = ?,DONGIA=?,THANHTIEN=?,"
                + "MAHD=?,MACTSP=? TRANGTHAI = ?"
                + " where MACTHD like ? ";
        Helper.JdbcHelper.executeUpdate(sql,
                CHI_TIET_HOA_DON.getSOLUONG(),
                CHI_TIET_HOA_DON.getDONGIA(), CHI_TIET_HOA_DON.getTHANHTIEN(),
                CHI_TIET_HOA_DON.getMAHD(), CHI_TIET_HOA_DON.getMACTSP(), CHI_TIET_HOA_DON.getTRANGTHAI(),
                CHI_TIET_HOA_DON.getMACTHD()
        );
    }

    @Override
    public void delete(String CHI_TIET_HOA_DON) {
        String sql = "delete from CHI_TIET_HOA_DON where MACTHD ";
        Helper.JdbcHelper.executeUpdate(sql, CHI_TIET_HOA_DON);
    }

    @Override
    public int countCTHD() {
        String sql = "select MAX(MACTHD)from CHI_TIET_HOA_DON";
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
    public CHI_TIET_HOA_DON findByMaCTSP(String MaCTSP) {
        String sql = "select * from CHI_TIET_HOA_DON where MACTSP like ?";
        List<Models.CHI_TIET_HOA_DON> list = select(sql, MaCTSP);
        return list.size() > 0 ? list.get(0) : null;
    }

}
