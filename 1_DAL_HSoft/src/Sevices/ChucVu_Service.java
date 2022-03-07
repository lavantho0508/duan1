/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sevices;

import Helper.JdbcHelper;
import Models.CHUC_VU;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author LENOVO
 */
public class ChucVu_Service implements IServices.IChucVu_Service {

    @Override
    public CHUC_VU readFromResultSet(ResultSet rs) throws SQLException {
        Models.CHUC_VU model = new Models.CHUC_VU();
        model.setMACV(rs.getString("MACV"));
        model.setTENCV(rs.getString("TENCV"));
        model.setTRANGTHAI(rs.getInt("TRANGTHAI"));
        return model;
    }

    @Override
    public List<CHUC_VU> select(String sql, Object... args) {
        List<Models.CHUC_VU> list = new ArrayList<>();
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
    public List<CHUC_VU> selectAll() {
        String sql = "select * from CHUC_VU";
        return select(sql);
    }

    @Override
    public List<CHUC_VU> selectByID(String ID) {
        String sql = "select * from CHUC_VU where MACV like ?";
        return select(sql, ID);
    }

    @Override
    public CHUC_VU findById(String ID) {
        String sql = "select * from CHUC_VU where MACV like ?";
        List<Models.CHUC_VU> list = select(sql, ID);
        return list.size() > 0 ? list.get(0) : null;
    }

    @Override
    public void insert(CHUC_VU CHUC_VU) {
        String sql = "insert into CHUC_VU(MACV,TENCV, TRANGTHAI) values(? ,?,?)";
        Helper.JdbcHelper.executeUpdate(sql,
                CHUC_VU.getMACV(),
                CHUC_VU.getTENCV(),
                CHUC_VU.getTRANGTHAI()
        );
    }

    @Override
    public void update(CHUC_VU CHUC_VU) {
        String sql = "Update CHUC_VU set TENCV=?, TRANGTHAI=? where macv like ?";
        Helper.JdbcHelper.executeUpdate(sql,
                CHUC_VU.getTENCV(),
                CHUC_VU.getTRANGTHAI(),
                CHUC_VU.getMACV()
        );
    }

    @Override
    public void delete(String ID) {
        String sql = "delete from CHUC_VU where MACV like ? ";
        Helper.JdbcHelper.executeUpdate(sql, ID);
    }

    @Override
    public CHUC_VU findByNames(String Names) {
        String sql = "select * from CHUC_VU where TENCV like ?";
        List<Models.CHUC_VU> list = select(sql, Names);
        return list.size() > 0 ? list.get(0) : null;
    }
}
