/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sevices;

import Helper.JdbcHelper;
import Models.NHAN_VIEN;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;
import java.util.Random;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author DELL
 */
public class NHAN_VIEN_Service implements IServices.INhanVien_Service{

    private Object[] NHAN_VIEN;

    @Override
    public NHAN_VIEN readFromResultSet(ResultSet rs) throws SQLException {
Models.NHAN_VIEN model = new Models.NHAN_VIEN();
        model.setMANV(rs.getString("MANV"));
        model.setHOTEN(rs.getString("HOTEN"));
        model.setGIOITINH(rs.getInt("GIOITINH"));
        model.setSDT(rs.getString("SDT"));
        model.setEMAIL(rs.getString("EMAIL"));
        model.setMATKHAU(rs.getString("MATKHAU"));
        model.setNGAYSINH(rs.getDate("NGAYSINH"));
        model.setDIACHI(rs.getString("DIACHI"));
        model.setTRANGTHAI(rs.getInt("TRANGTHAI"));
        return model;    }

    @Override
    public List<NHAN_VIEN> select(String sql, Object... args) {
 List<Models.NHAN_VIEN> list = new ArrayList<>();
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
    public List<NHAN_VIEN> selectAll() {
 String sql = "select * from NHAN_VIEN";
        return select(sql);
    }

    @Override
    public List<NHAN_VIEN> selectByID(String ID) {
 String sql = "select * from NHAN_VIEN where MANV like ?";
        return select(sql, ID);   
    }

    @Override
    public NHAN_VIEN findById(String ID) {
String sql = "select * from NHAN_VIEN where MANV like ?";
        List<Models.NHAN_VIEN> list = select(sql, ID);
        return list.size() > 0 ? list.get(0) : null;
    }

    @Override
    public NHAN_VIEN findByName(String Names) {
String sql = "select * from NHAN_VIEN where HOTEN like ?";
        List<Models.NHAN_VIEN> list = select(sql, Names);
        return list.size() > 0 ? list.get(0) : null;
    }

    @Override
    public void insert(NHAN_VIEN NHAN_VIEN) {
String sql = "insert into NHAN_VIEN(MANV, HOTEN,SDT,EMAIL,MATKHAU"
                + ",NGAYSINH,DIACHI, TRANGTHAI) values(?, ?,?,?,?,?,?,?)";
        Helper.JdbcHelper.executeUpdate(sql,
                NHAN_VIEN.getMANV(),
                NHAN_VIEN.getHOTEN(),
                NHAN_VIEN.getSDT(),
                NHAN_VIEN.getEMAIL(),
                NHAN_VIEN.getMATKHAU(),
                NHAN_VIEN.getNGAYSINH(),
                NHAN_VIEN.getDIACHI(),
                NHAN_VIEN.getTRANGTHAI()
        )    ;
                }

    @Override
    public void update(NHAN_VIEN NHAN_VIEN) {
String sql = "update NHAN_VIEN set  HOTEN=?,SDT=?,EMAIL=?,MATKHAU=?,NGAYSINH=?,DIACHI=?, TRANGTHAI=? where MANV like ?";
        Helper.JdbcHelper.executeUpdate(sql,
                NHAN_VIEN.getHOTEN(),
                NHAN_VIEN.getSDT(),
                NHAN_VIEN.getEMAIL(),
                NHAN_VIEN.getMATKHAU(),
                NHAN_VIEN.getNGAYSINH(),
                NHAN_VIEN.getDIACHI(),
                NHAN_VIEN.getTRANGTHAI(),
                NHAN_VIEN.getMANV()
        )    ;
    }

    @Override
    public void delete(String ID) {
String sql = "delete from CHI_TIET_HOA_DON where MACTHD like ?";
        Helper.JdbcHelper.executeUpdate(sql, ID);
    }

    @Override
    public String randomCode() {
            String AlphaNumericString ="0123456789"
                                    + "abcdefghijklmnopqrstuvxyz";
         StringBuilder str=new StringBuilder(6);
          for (int i = 0; i < 6; i++) {
  
            // generate a random number between
            // 0 to AlphaNumericString variable length
            int index
                = (int)(AlphaNumericString.length()
                        * Math.random());
            str.append(AlphaNumericString
                          .charAt(index));
        }
      return str.toString();
       // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<NHAN_VIEN> selectEmailByID(String ma) {
            return select("select*from nhan_vien where manv=?",ma);
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int changePassWord(String ma, String pass) {
           try {
          String query="update nhan_vien set matkhau =? where manv=?";
          
          PreparedStatement pre=DriverManager.getConnection(JdbcHelper.dburl,JdbcHelper.username,JdbcHelper.password).prepareStatement(query);
          pre.setString(1, pass);
          pre.setString(2, ma);
          return pre.executeUpdate();
          // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
      } catch (SQLException ex) {
          Logger.getLogger(NHAN_VIEN_Service.class.getName()).log(Level.SEVERE, null, ex);
      }
     
     return 0;
      //  throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
