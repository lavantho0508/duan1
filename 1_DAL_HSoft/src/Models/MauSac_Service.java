/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import Helper.JdbcHelper;
import IServices.IMauSac_Service;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author BUI_QUANG_HIEU
 */
public class MauSac_Service implements IMauSac_Service {

    @Override
    public MAU_SAC readFromResultSet(ResultSet rs) throws SQLException {
        MAU_SAC model = new MAU_SAC();
        model.setMAMAU(rs.getString("MAMAU"));
        model.setTENMAU(rs.getString("TENMAU"));
        model.setTRANGTHAI(rs.getInt("TRANGTHAI"));
        return model;
    }

    @Override
    public List<MAU_SAC> select(String sql, Object... args) {
        List<MAU_SAC> list = new ArrayList<>();
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
    public List<MAU_SAC> selectAll() {
        String sql = "select * from MAU_SAC";
        return select(sql);
    }

    @Override
    public List<MAU_SAC> selectByID(String ID) {
        String sql = "select * from MAU_SAC where MAMAU like ?";
        return select(sql, ID);
    }

    @Override
    public MAU_SAC findById(String ID) {
        String sql = "select * from MAU_SAC where MAMAU like ?";
        List<MAU_SAC> list = select(sql, ID);
        return list.size() > 0 ? list.get(0) : null;
    }

    @Override
    public MAU_SAC findByNames(String Names) {
        String sql = "select * from MAU_SAC where TENMAU like ?";
        List<MAU_SAC> list = select(sql, Names);
        return list.size() > 0 ? list.get(0) : null;
    }

    @Override
    public void insert(MAU_SAC MAU_SAC) {
        String sql = "insert into MAU_SAC(MAMAU, TENMAU, TRANGTHAI) values(?, ? ,?)";
        Helper.JdbcHelper.executeUpdate(sql,
                MAU_SAC.getMAMAU(),
                MAU_SAC.getTENMAU(),
                MAU_SAC.getTRANGTHAI()
        );
    }

    @Override
    public void update(MAU_SAC MAU_SAC) {
        String sql = "update MAU_SAC set TENMAU=?,TRANGTHAI=? where MAMAU like ? ";
        Helper.JdbcHelper.executeUpdate(sql,
                MAU_SAC.getTENMAU(),
                MAU_SAC.getTRANGTHAI(),
                MAU_SAC.getMAMAU()
        );
    }

    @Override
    public void delete(String ID) {
        String sql = "delete from MAU_SAC where MAMAU like ? ";
        Helper.JdbcHelper.executeUpdate(sql, ID);
    }

}
