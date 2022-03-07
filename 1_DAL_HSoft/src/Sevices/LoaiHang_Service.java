/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sevices;

import Helper.JdbcHelper;
import IServices.ILoaiHoang_Service;
import Models.LOAI_HANG;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author BUI_QUANG_HIEU
 */
public class LoaiHang_Service implements ILoaiHoang_Service {

    @Override
    public LOAI_HANG readFromResultSet(ResultSet rs) throws SQLException {
        LOAI_HANG model = new LOAI_HANG();
        model.setMALOAIHANG(rs.getString("MALOAIHANG"));
        model.setTENLOAIHANG(rs.getString("TENLOAIHANG"));
        model.setTRANGTHAI(rs.getInt("TRANGTHAI"));
        return model;
    }

    @Override
    public List<LOAI_HANG> select(String sql, Object... args) {
        List<LOAI_HANG> list = new ArrayList<>();
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
    public List<LOAI_HANG> selectAll() {
        String sql = "select * from LOAI_HANG";
        return select(sql);
    }

    @Override
    public List<LOAI_HANG> selectByID(String LOAI_HANG) {
        String sql = "select * from LOAI_HANG where MALOAIHANG like ?";
        return select(sql, LOAI_HANG);
    }

    @Override
    public LOAI_HANG findById(String LOAI_HANG) {
        String sql = "select * from LOAI_HANG where MALOAIHANG like ?";
        List<LOAI_HANG> list = select(sql, LOAI_HANG);
        return list.size() > 0 ? list.get(0) : null;
    }

    @Override
    public LOAI_HANG findByNames(String Names) {
        String sql = "select * from LOAI_HANG where TENLOAIHANG like ?";
        List<LOAI_HANG> list = select(sql, Names);
        return list.size() > 0 ? list.get(0) : null;
    }

    @Override
    public void insert(LOAI_HANG LOAI_HANG) {
        String sql = "insert into LOAI_HANG(MALOAIHANG, TENLOAIHANG, TRANGTHAI) values(?, ? ,?)";
        Helper.JdbcHelper.executeUpdate(sql,
                LOAI_HANG.getMALOAIHANG(),
                LOAI_HANG.getTENLOAIHANG(),
                LOAI_HANG.getTRANGTHAI()
        );
    }

    @Override
    public void update(LOAI_HANG LOAI_HANG) {
        String sql = "update LOAI_HANG set TENLOAIHANG=?,TRANGTHAI=? where MALOAIHANG like ? ";
        Helper.JdbcHelper.executeUpdate(sql,
                LOAI_HANG.getTENLOAIHANG(),
                LOAI_HANG.getTRANGTHAI(),
                LOAI_HANG.getMALOAIHANG()
        );
    }

    @Override
    public void delete(String LOAI_HANG) {
        String sql = "delete from LOAI_HANG where MALOAIHANG like ? ";
        Helper.JdbcHelper.executeUpdate(sql, LOAI_HANG);
    }

}
