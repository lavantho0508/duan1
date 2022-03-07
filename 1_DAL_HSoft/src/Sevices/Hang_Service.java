/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sevices;

import Helper.JdbcHelper;
import IServices.IHang_Service;
import Models.DON_VI_TINH;
import Models.HANG;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author BUI_QUANG_HIEU
 */
public class Hang_Service implements IHang_Service {

    @Override
    public HANG readFromResultSet(ResultSet rs) throws SQLException {
        HANG model = new HANG();
        model.setMAHANG(rs.getString("MAHANG"));
        model.setTENHANG(rs.getString("TENHANG"));
        model.setTRANGTHAI(rs.getInt("TRANGTHAI"));
        return model;
    }

    @Override
    public List<HANG> select(String sql, Object... args) {
        List<HANG> list = new ArrayList<>();
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
    public List<HANG> selectAll() {
        String sql = "select * from HANG";
        return select(sql);
    }

    @Override
    public List<HANG> selectByID(String HANG) {
        String sql = "select * from HANG where MAHANG like ?";
        return select(sql, HANG);
    }

    @Override
    public HANG findById(String HANG) {
        String sql = "select * from HANG where MAHANG like ?";
        List<HANG> list = select(sql, HANG);
        return list.size() > 0 ? list.get(0) : null;
    }

    @Override
    public void insert(HANG HANG) {
        String sql = "insert into HANG(MAHANG, TENHANG,TRANGTHAI) values(?, ? ,?)";
        Helper.JdbcHelper.executeUpdate(sql,
                HANG.getMAHANG(),
                HANG.getTENHANG(),
                HANG.getTRANGTHAI()
        );
    }

    @Override
    public void update(HANG HANG) {
        String sql = "update HANG set TENHANG=?,TRANGTHAI=? where MAHANG like ? ";
        Helper.JdbcHelper.executeUpdate(sql,
                HANG.getTENHANG(),
                HANG.getTRANGTHAI(),
                HANG.getMAHANG()
        );
    }

    @Override
    public void delete(String HANG) {
        String sql = "delete from HANG where MAHANG like ? ";
        Helper.JdbcHelper.executeUpdate(sql, HANG);
    }

    @Override
    public HANG findByNames(String Names) {
        String sql = "select * from HANG where TENHANG like ?";
        List<HANG> list = select(sql, Names);
        return list.size() > 0 ? list.get(0) : null;
    }

    @Override
    public int countByIDHang(String IDHang) {
        String sql = "select count(*) from SAN_PHAM where MAHANG like ?";
        int count = 0;
        try {
            ResultSet rs = null;
            try {
                rs = Helper.JdbcHelper.executeQuery(sql, IDHang);
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
