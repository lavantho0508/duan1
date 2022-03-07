/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sevices;

import Helper.JdbcHelper;
import IServices.IKichThuoc_Service;
import Models.KICH_THUOC;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author BUI_QUANG_HIEU
 */
public class KichThuoc_Service implements IKichThuoc_Service {

    @Override
    public KICH_THUOC readFromResultSet(ResultSet rs) throws SQLException {
        KICH_THUOC model = new KICH_THUOC();
        model.setMAKICHTHUOC(rs.getString("MAKICHTHUOC"));
        model.setKICHTHUOC(rs.getString("KICHTHUOC"));
        model.setTRANGTHAI(rs.getInt("TRANGTHAI"));
        return model;
    }

    @Override
    public List<KICH_THUOC> select(String sql, Object... args) {
        List<KICH_THUOC> list = new ArrayList<>();
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
    public List<KICH_THUOC> selectAll() {
        String sql = "select * from KICH_THUOC";
        return select(sql);
    }

    @Override
    public List<KICH_THUOC> selectByID(String KICH_THUOC) {
        String sql = "select * from KICH_THUOC where MAKICHTHUOC like ?";
        return select(sql, KICH_THUOC);
    }

    @Override
    public KICH_THUOC findById(String ID) {
        String sql = "select * from KICH_THUOC where MAKICHTHUOC like ?";
        List<KICH_THUOC> list = select(sql, ID);
        return list.size() > 0 ? list.get(0) : null;
    }

    @Override
    public KICH_THUOC findByNames(String KichThuoc) {
        String sql = "select * from KICH_THUOC where KICHTHUOC like ?";
        List<KICH_THUOC> list = select(sql, KichThuoc);
        return list.size() > 0 ? list.get(0) : null;
    }

    @Override
    public void insert(KICH_THUOC KICH_THUOC) {
        String sql = "insert into KICH_THUOC(MAKICHTHUOC, KICHTHUOC, TRANGTHAI) values(?, ? ,?)";
        Helper.JdbcHelper.executeUpdate(sql,
                KICH_THUOC.getMAKICHTHUOC(),
                KICH_THUOC.getKICHTHUOC(),
                KICH_THUOC.getTRANGTHAI()
        );
    }

    @Override
    public void update(KICH_THUOC KICH_THUOC) {
        String sql = "update KICH_THUOC set KICHTHUOC=?,TRANGTHAI=? where MAKICHTHUOC like ? ";
        Helper.JdbcHelper.executeUpdate(sql,
                KICH_THUOC.getKICHTHUOC(),
                KICH_THUOC.getTRANGTHAI(),
                KICH_THUOC.getMAKICHTHUOC()
        );
    }

    @Override
    public void delete(String ID) {
        String sql = "delete from KICH_THUOC where MAKICHTHUOC like ? ";
        Helper.JdbcHelper.executeUpdate(sql, ID);
    }

}
