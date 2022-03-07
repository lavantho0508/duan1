/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sevices;

import Helper.JdbcHelper;
import IServices.IKhachHang_Service;
import Models.KHACH_HANG;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author BUI_QUANG_HIEU
 */
public class KhachHang_Service implements IKhachHang_Service {

    @Override
    public KHACH_HANG readFromResultSet(ResultSet rs) throws SQLException {
        Models.KHACH_HANG model = new Models.KHACH_HANG();
        model.setMAKH(rs.getString("MAKH"));
        model.setTENKH(rs.getString("TENKH"));
        model.setSDT(rs.getString("SDT"));
        model.setDIACHI(rs.getString("DIACHI"));
        model.setTRANGTHAI(rs.getInt("TRANGTHAI"));
        return model;
    }

    @Override
    public List<KHACH_HANG> select(String sql, Object... args) {
        List<Models.KHACH_HANG> list = new ArrayList<>();
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
    public List<KHACH_HANG> selectAll() {
        String sql = "select * from KHACH_HANG";
        return select(sql);
    }

    @Override
    public List<KHACH_HANG> selectByID(String ID) {
        String sql = "select * from KHACH_HANG where MAKH like ?";
        return select(sql, ID);
    }

    @Override
    public KHACH_HANG findById(String ID) {
        String sql = "select * from KHACH_HANG where MAKH like ?";
        List<Models.KHACH_HANG> list = select(sql, ID);
        return list.size() > 0 ? list.get(0) : null;
    }

    @Override
    public void insert(KHACH_HANG khach_hang) {
        String sql = "insert into KHACH_HANG(MAKH,TENKH,SDT,DIACHI, TRANGTHAI) values(? ,?,?,?,?)";
        Helper.JdbcHelper.executeUpdate(sql,
                khach_hang.getMAKH(),
                khach_hang.getTENKH(),
                khach_hang.getSDT(),
                khach_hang.getDIACHI(),
                khach_hang.getTRANGTHAI()
        );
    }

    @Override
    public void update(KHACH_HANG khach_hang) {
        String sql = "Update KHACH_HANG set TENKH=?,SDT=?, DIACHI=?, TRANGTHAI=? where MAKH like ?";
        Helper.JdbcHelper.executeUpdate(sql,
                khach_hang.getTENKH(),
                khach_hang.getSDT(),
                khach_hang.getDIACHI(),
                khach_hang.getTRANGTHAI(),
                khach_hang.getMAKH()
        );
    }

    @Override
    public void delete(String ID) {
        String sql = "delete from KHACH_HANG where MAKH like ? ";
        Helper.JdbcHelper.executeUpdate(sql, ID);
    }

    @Override
    public List<KHACH_HANG> selectByKeyword(String keyWord) {
        String sql = "Select * from KHACH_HANG where SDT like '%" + keyWord + "%'  or "
                + " TENKH like N'%" + keyWord + "%'";
        return select(sql);
    }

    @Override
    public KHACH_HANG findByNames(String ID) {
        String sql = "select * from KHACH_HANG where TENKH like ?";
        List<Models.KHACH_HANG> list = select(sql, ID);
        return list.size() > 0 ? list.get(0) : null;
    }

}
