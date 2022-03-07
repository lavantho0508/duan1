/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sevices;

import Helper.JdbcHelper;
import Models.CHI_TIET_SAN_PHAM;
import java.sql.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author LENOVO
 */
public class ChiTietSanPham_Service implements IServices.IChiTietTietSanPham_Service {

    @Override
    public Models.CHI_TIET_SAN_PHAM readFromResultSet(ResultSet rs) throws SQLException {
        Models.CHI_TIET_SAN_PHAM model = new Models.CHI_TIET_SAN_PHAM();
        model.setMACTSP(rs.getInt("MACTSP"));
        model.setMAKIEUDANG(rs.getString("MAKIEUDANG"));
        model.setMADV(rs.getString("MADV"));
        model.setMAKICHTHUOC(rs.getString("MAKICHTHUOC"));
        model.setMAMAU(rs.getString("MAMAU"));
        model.setMALOAI(rs.getString("MALOAI"));
        model.setMAGT(rs.getString("MAGT"));
        model.setMASP(rs.getString("MASP"));
        model.setMALOAIHANG(rs.getString("MALOAIHANG"));
        model.setBARCODE(rs.getString("BARCODE"));
        model.setSOLUONG(rs.getInt("SOLUONG"));
        model.setDONGIA(rs.getDouble("DONGIA"));
        model.setIMAGES(rs.getString("IMAGES"));
        model.setTRANGTHAI(rs.getInt("TRANGTHAI"));
        return model;
    }

    @Override
    public List<Models.CHI_TIET_SAN_PHAM> select(String sql, Object... args) {
        List<Models.CHI_TIET_SAN_PHAM> list = new ArrayList<>();
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
    public List<Models.CHI_TIET_SAN_PHAM> selectAll() {
        String sql = "select * from CHI_TIET_SAN_PHAM";
        return select(sql);
    }

    @Override
    public List<Models.CHI_TIET_SAN_PHAM> selectByID(String CHI_TIET_SAN_PHAM) {
        String sql = "select * from CHI_TIET_SAN_PHAM where MALOAIHANG like ?";
        return select(sql, CHI_TIET_SAN_PHAM);
    }

    @Override
    public Models.CHI_TIET_SAN_PHAM findById(String CHI_TIET_SAN_PHAM) {
        String sql = "select * from CHI_TIET_SAN_PHAM where MASP like ?";
        List<Models.CHI_TIET_SAN_PHAM> list = select(sql, CHI_TIET_SAN_PHAM);
        return list.size() > 0 ? list.get(0) : null;
    }

    @Override
    public void insert(Models.CHI_TIET_SAN_PHAM CHI_TIET_SAN_PHAM) {
        String sql = "insert into CHI_TIET_SAN_PHAM(MAKIEUDANG,MADV,MAKICHTHUOC,MAMAU,MALOAI"
                + ",MAGT,MASP,MALOAIHANG,BARCODE,SOLUONG,DONGIA,IMAGES, TRANGTHAI) values(? ,?,?,?,?,?,?,?,?,?,?,?,?)";
        Helper.JdbcHelper.executeUpdate(sql,
                CHI_TIET_SAN_PHAM.getMAKIEUDANG(),
                CHI_TIET_SAN_PHAM.getMADV(),
                CHI_TIET_SAN_PHAM.getMAKICHTHUOC(),
                CHI_TIET_SAN_PHAM.getMAMAU(),
                CHI_TIET_SAN_PHAM.getMALOAI(),
                CHI_TIET_SAN_PHAM.getMAGT(),
                CHI_TIET_SAN_PHAM.getMASP(),
                CHI_TIET_SAN_PHAM.getMALOAIHANG(),
                CHI_TIET_SAN_PHAM.getBARCODE(),
                CHI_TIET_SAN_PHAM.getSOLUONG(),
                CHI_TIET_SAN_PHAM.getDONGIA(),
                CHI_TIET_SAN_PHAM.getIMAGES(),
                CHI_TIET_SAN_PHAM.getTRANGTHAI()
        );
    }

    @Override
    public void update(Models.CHI_TIET_SAN_PHAM CHI_TIET_SAN_PHAM) {
        String sql = "update CHI_TIET_SAN_PHAM set MAKIEUDANG=?,MADV=?,MAKICHTHUOC=?,MAMAU=?,MALOAI=?"
                + ",MAGT=?,MALOAIHANG=?,BARCODE=?,SOLUONG=?,DONGIA=?,IMAGES=?, TRANGTHAI=? where MACTSP like ? ";
        Helper.JdbcHelper.executeUpdate(sql,
                CHI_TIET_SAN_PHAM.getMAKIEUDANG(), CHI_TIET_SAN_PHAM.getMADV(), CHI_TIET_SAN_PHAM.getMAKICHTHUOC(),
                CHI_TIET_SAN_PHAM.getMAMAU(), CHI_TIET_SAN_PHAM.getMALOAI(), CHI_TIET_SAN_PHAM.getMAGT(),
                CHI_TIET_SAN_PHAM.getMALOAIHANG(), CHI_TIET_SAN_PHAM.getBARCODE(),
                CHI_TIET_SAN_PHAM.getSOLUONG(), CHI_TIET_SAN_PHAM.getDONGIA(), CHI_TIET_SAN_PHAM.getIMAGES(),
                CHI_TIET_SAN_PHAM.getTRANGTHAI(), CHI_TIET_SAN_PHAM.getMACTSP()
        );
    }

    @Override
    public void delete(String CHI_TIET_SAN_PHAM) {
        String sql = "delete from CHI_TIET_SAN_PHAM where MACTSP like ? ";
        Helper.JdbcHelper.executeUpdate(sql, CHI_TIET_SAN_PHAM);
    }

    @Override
    public CHI_TIET_SAN_PHAM findByBarcode(String CHI_TIET_SAN_PHAM) {
        String sql = "select * from CHI_TIET_SAN_PHAM where BARCODE like ?";
        List<Models.CHI_TIET_SAN_PHAM> list = select(sql, CHI_TIET_SAN_PHAM);
        return list.size() > 0 ? list.get(0) : null;
    }

    @Override
    public int updateSoluong(int SoLuong, String ma) {
        try {
            PreparedStatement pre = DriverManager.getConnection(JdbcHelper.dburl, JdbcHelper.username, JdbcHelper.password).prepareStatement("update CHI_TIET_SAN_PHAM set SOLUONG=(select SOLUONG where MACTSP=?)-?");
            pre.setString(1, ma);
            pre.setInt(2, SoLuong);
            return pre.executeUpdate();
        } catch (Exception e) {
        }
        return 0;
    }

    @Override
    public int removeSoLuong(int SoLuong, String masp,int soLuongNhapLai) {
        try {
            PreparedStatement pre = DriverManager.getConnection(JdbcHelper.dburl, JdbcHelper.username, JdbcHelper.password).prepareStatement("update CHI_TIET_SAN_PHAM set SOLUONG=(select SOLUONG where MACTSp=?)+?-? ");
            pre.setString(1, masp);
            pre.setInt(2, SoLuong);
            pre.setInt(3, soLuongNhapLai);
            return pre.executeUpdate();
            // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        } catch (SQLException ex) {
            Logger.getLogger(ChiTietSanPham_Service.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
        //  throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int countByIDDVT(String ID) {
        String sql = "select count(*) from CHI_TIET_SAN_PHAM where MADV like ?";
        int count = 0;
        try {
            ResultSet rs = null;
            try {
                rs = Helper.JdbcHelper.executeQuery(sql, ID);
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
    public int countByIDLoaiHang(String ID) {
        String sql = "select count(*) from CHI_TIET_SAN_PHAM where MALOAIHANG like ?";
        int count = 0;
        try {
            ResultSet rs = null;
            try {
                rs = Helper.JdbcHelper.executeQuery(sql, ID);
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
    public int countByIDKieuDang(String ID) {
        String sql = "select count(*) from CHI_TIET_SAN_PHAM where MAKIEUDANG like ?";
        int count = 0;
        try {
            ResultSet rs = null;
            try {
                rs = Helper.JdbcHelper.executeQuery(sql, ID);
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
    public int countByIDLoaiKinh(String ID) {
        String sql = "select count(*) from CHI_TIET_SAN_PHAM where MALOAI like ?";
        int count = 0;
        try {
            ResultSet rs = null;
            try {
                rs = Helper.JdbcHelper.executeQuery(sql, ID);
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
    public int countByIDKichThuoc(String ID) {
        String sql = "select count(*) from CHI_TIET_SAN_PHAM where MAKICHTHUOC like ?";
        int count = 0;
        try {
            ResultSet rs = null;
            try {
                rs = Helper.JdbcHelper.executeQuery(sql, ID);
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
    public int countByIDMauSac(String ID) {
        String sql = "select count(*) from CHI_TIET_SAN_PHAM where MAMAU like ?";
        int count = 0;
        try {
            ResultSet rs = null;
            try {
                rs = Helper.JdbcHelper.executeQuery(sql, ID);
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
    public int selectMACTSP(String ma) {
        PreparedStatement pre;
        try {
            pre = DriverManager.getConnection(JdbcHelper.dburl, JdbcHelper.username, JdbcHelper.password).prepareStatement("select mactsp from chi_tiet_san_pham where masp=?");
            pre.setString(1, ma);
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                return rs.getInt("mactsp");

            }
        } catch (SQLException ex) {
            Logger.getLogger(ChiTietSanPham_Service.class.getName()).log(Level.SEVERE, null, ex);
        }

        return 0;
    }

    @Override
    public CHI_TIET_SAN_PHAM findAllOfAMCTSP(String MaSP, String maKT, String maKD, String maMau, String maLoai, String MaGT, String maLH, String maDVT) {
        String sql = "select * from CHI_TIET_SAN_PHAM where MAKIEUDANG = ? and MADV = ? and MAKICHTHUOC = ? and MAMAU = ? and MALOAI = ? and MAGT = ? and MALOAIHANG = ? and MASP = ?";
        List<Models.CHI_TIET_SAN_PHAM> list = select(sql, maKD, maDVT, maKT, maMau, maLoai, MaGT, maLH, MaSP);
        return list.size() > 0 ? list.get(0) : null;
    }

    @Override
    public void updateTrangThai(int MaCTSP, int TrangThai) {
        String sql = "update CHI_TIET_SAN_PHAM set TRANGTHAI = ? where MACTSP = ?";
        Helper.JdbcHelper.executeUpdate(sql,
                TrangThai, MaCTSP
        );
    }

    @Override
    public CHI_TIET_SAN_PHAM findByIdCTSP(String CHI_TIET_SAN_PHAM) {
        String sql = "select * from CHI_TIET_SAN_PHAM where MACTSP like ?";
        List<Models.CHI_TIET_SAN_PHAM> list = select(sql, CHI_TIET_SAN_PHAM);
        return list.size() > 0 ? list.get(0) : null;
    }


}
