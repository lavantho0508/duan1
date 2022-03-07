/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sevices;

import Helper.JdbcHelper;
import IServices.ILoaiKinh_Service;
import Models.DON_VI_TINH;
import Models.LOAI_KINH;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author BUI_QUANG_HIEU
 */
public class LoaiKinh_Service implements ILoaiKinh_Service {

    @Override
    public LOAI_KINH readFromResultSet(ResultSet rs) throws SQLException {
        LOAI_KINH model = new LOAI_KINH();
        model.setMALOAI(rs.getString("MALOAI"));
        model.setLOAIKINH(rs.getString("LOAIKINH"));
        model.setTRANGTHAI(rs.getInt("TRANGTHAI"));
        return model;
    }

    @Override
    public List<LOAI_KINH> select(String sql, Object... args) {
        List<LOAI_KINH> list = new ArrayList<>();
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
    public List<LOAI_KINH> selectAll() {
        String sql = "select * from LOAI_KINH";
        return select(sql);
    }

    @Override
    public List<LOAI_KINH> selectByID(String ID) {
        String sql = "select * from LOAI_KINH where MALOAI like ?";
        return select(sql, ID);
    }

    @Override
    public LOAI_KINH findById(String ID) {
        String sql = "select * from LOAI_KINH where MALOAI like ?";
        List<LOAI_KINH> list = select(sql, ID);
        return list.size() > 0 ? list.get(0) : null;
    }

    @Override
    public LOAI_KINH findByNames(String Names) {
        String sql = "select * from LOAI_KINH where LOAIKINH like ?";
        List<LOAI_KINH> list = select(sql, Names);
        return list.size() > 0 ? list.get(0) : null;
    }

    @Override
    public void insert(LOAI_KINH LOAI_KINH) {
        String sql = "insert into LOAI_KINH(MALOAI, LOAIKINH,TRANGTHAI) values(?, ? ,?)";
        Helper.JdbcHelper.executeUpdate(sql,
                LOAI_KINH.getMALOAI(),
                LOAI_KINH.getLOAIKINH(),
                LOAI_KINH.getTRANGTHAI()
        );
    }

    @Override
    public void update(LOAI_KINH LOAI_KINH) {
        String sql = "update LOAI_KINH set LOAI_KINH=?,TRANGTHAI=? where MALOAI like ? ";
        Helper.JdbcHelper.executeUpdate(sql,
                LOAI_KINH.getLOAIKINH(),
                LOAI_KINH.getTRANGTHAI(),
                LOAI_KINH.getMALOAI()
        );
    }

    @Override
    public void delete(String LOAI_KINH) {
        String sql = "delete from LOAI_KINH where MALOAI like ? ";
        Helper.JdbcHelper.executeUpdate(sql, LOAI_KINH);
    }

    

}
