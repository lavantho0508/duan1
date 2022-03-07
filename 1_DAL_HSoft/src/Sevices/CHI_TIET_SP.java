/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sevices;

import Helper.JdbcHelper;
import IServices.SAN_PHAM;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import Models.*;

/**
 *
 * @author DELL
 */
public class CHI_TIET_SP implements SAN_PHAM{
    public List<Models.SAN_PHAM_CT>list=new ArrayList<>();
String query="select tenloaihang,ctsp.MASP,tensp,(N'Hãng :'+tenloaihang+N'Loại Kính :'+''+loaikinh+N'Màu sắc :'+''+TENMAU+N'Kích thước :'+''+KICHTHUOC+N'Giới tính :'+''+GIOITINH) as thongtinsp,SOLUONG,dongia,ctsp.TRANGTHAI\n" +
"from CHI_TIET_SAN_PHAM ctsp join LOAI_HANG lh on ctsp.MALOAIHANG=lh.MALOAIHANG\n" +
"                            join SAN_PHAM sp on ctsp.MASP=sp.MASP\n" +
"                            join KIEU_DANG kd on ctsp.MAKIEUDANG=Kd.MAKIEUDANG\n" +
"							join LOAI_KINH lk on ctsp.MALOAI=lk.MALOAI\n" +
"							join don_vi_tinh dvt on ctsp.MADV=dvt.MADV\n" +
"							join GIOI_TINH gt on ctsp.MAGT=gt.MAGT\n" +
"							join KICH_THUOC kt on ctsp.MAKICHTHUOC=kt.MAKICHTHUOC\n" +
"							join MAU_SAC ms on ctsp.MAMAU=ms.MAMAU where ctsp.TRANGTHAI=0";
 
    @Override
    public Models.SAN_PHAM_CT readFromResultSet(ResultSet rs) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Models.SAN_PHAM_CT> select(String sql, Object... args) {
    
        
        try {
            ResultSet rs = null;
            try {
                rs = JdbcHelper.executeQuery(sql, args);
                while (rs.next()) {
                    Models.SAN_PHAM_CT sp =new Models.SAN_PHAM_CT(rs.getString(1),rs.getString(2),rs.getString(3), rs.getString(4), rs.getInt(5), rs.getDouble(6));
                    list.add(sp);
                }
            } finally {
                rs.getStatement().getConnection().close();      //đóng kết nối từ resultSet
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new RuntimeException();
        }
        return list;
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Models.SAN_PHAM_CT> selectAll() {
      return select(query);
    }

    @Override
    public List<Models.SAN_PHAM_CT> selectByID(String SAN_PHAM) {
        String sql="select TENLOAIHANG,ctsp.MASP,tensp,(kieudang+', '+loaikinh+' ,'+ten+kichthuoc+', '+gioitinh+' ,'+tenmau) as thongtinsp,soluong,DONGIA\n" +
"from CHI_TIET_SAN_PHAM ctsp join LOAI_HANG lh on ctsp.MALOAIHANG=lh.MALOAIHANG\n" +
"                            join SAN_PHAM sp on ctsp.MASP=sp.MASP\n" +
"                            join KIEU_DANG kd on ctsp.MAKIEUDANG=Kd.MAKIEUDANG\n" +
"							join LOAI_KINH lk on ctsp.MALOAI=lk.MALOAI\n" +
"							join don_vi_tinh dvt on ctsp.MADV=dvt.MADV\n" +
"							join GIOI_TINH gt on ctsp.MAGT=gt.MAGT\n" +
"							join KICH_THUOC kt on ctsp.MAKICHTHUOC=kt.MAKICHTHUOC\n" +
"							join MAU_SAC ms on ctsp.MAMAU=ms.MAMAU where ctsp.TRANGTHAI=0 and ctsp.masp=?";
    return select(sql);
    }

    @Override
    public Models.SAN_PHAM_CT findById(String SAN_PHAM) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void insert(Models.SAN_PHAM_CT SAN_PHAM) {
       String insert ="insert into san_pham values(?,?,?,?)";
        try {
            JdbcHelper.preparedStatement(insert,SAN_PHAM.getMASP(),SAN_PHAM.getTENSP(),SAN_PHAM.getTHONGTINSP(),0);
        } catch (SQLException ex) {
            Logger.getLogger(CHI_TIET_SP.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void update(Models.SAN_PHAM_CT SAN_PHAM) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(String SAN_PHAM) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
