/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sevices;

import Helper.JdbcHelper;
import IServices.IChiTietHoaDon_Service;
import IServices.IChiTietTietSanPham_Service;
import IServices.IGioiTinh_Service;
import IServices.IHang_Service;
import IServices.IHoaDon_Service;
import IServices.IKhachHang_Service;
import IServices.IKichThuoc_Service;
import IServices.ILoaiHoang_Service;
import IServices.ILoaiKinh_Service;
import IServices.IMauSac_Service;
import IServices.INhanVien_Service;
import IServices.IQLyBanHang_Service;
import IServices.ISanPham_Service;
import Models.CHI_TIET_HOA_DON;
import Models.CHI_TIET_SAN_PHAM;
import Models.GIOI_TINH;
import Models.GIO_HANG;
import Models.HANG;
import Models.HOA_DON;
import Models.KHACH_HANG;
import Models.KICH_THUOC;
import Models.LOAI_HANG;
import Models.LOAI_KINH;
import Models.MAU_SAC;
import Models.MauSac_Service;
import Models.NHAN_VIEN;
import Models.SAN_PHAM;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author BUI_QUANG_HIEU
 */
public class QLyBanHang_Service implements IQLyBanHang_Service {

     ILoaiHoang_Service _ILoaiHoang_Service = (ILoaiHoang_Service) new LoaiHang_Service();
     ILoaiKinh_Service _ILoaiKinh_Service = (ILoaiKinh_Service) new LoaiKinh_Service();
     IChiTietTietSanPham_Service _IChiTietTietSanPham_Service = (IChiTietTietSanPham_Service) new ChiTietSanPham_Service();
     ISanPham_Service _ISanPham_Service = (ISanPham_Service) new SanPham_Service();
     IHang_Service _IHang_Service = (IHang_Service) new Hang_Service();
     IMauSac_Service _IMauSac_Service = (IMauSac_Service) new MauSac_Service();
     IKichThuoc_Service _IKichThuoc_Service = (IKichThuoc_Service) new KichThuoc_Service();
     IGioiTinh_Service _IGioiTinh_Service = (IGioiTinh_Service) new GioiTinh_Service();
     IHoaDon_Service _IHoaDon_Service = (IHoaDon_Service) new HoaDon_Service();
     IChiTietHoaDon_Service _IChiTietHoaDon_Service = (IChiTietHoaDon_Service) new ChiTietHoaDon_Service();
     INhanVien_Service _INhanVien_Service = (INhanVien_Service) new NHAN_VIEN_Service();
     IKhachHang_Service _IKhachHang_Service = (IKhachHang_Service) new KhachHang_Service();

    @Override
    public List<CHI_TIET_SAN_PHAM> selectAll_CTSP() {
        return _IChiTietTietSanPham_Service.selectAll();
    }

    @Override
    public List<CHI_TIET_SAN_PHAM> selectByID_CTSP(String ID_CTSP) {
        return _IChiTietTietSanPham_Service.selectByID(ID_CTSP);
    }

    @Override
    public LOAI_KINH findById_LoaiKinh(String ID_LoaiKinh) {
        return _ILoaiKinh_Service.findById(ID_LoaiKinh);
    }

    @Override
    public LOAI_HANG findById_LoaiHang(String ID_LoaiHang) {
        return _ILoaiHoang_Service.findById(ID_LoaiHang);
    }

    @Override
    public SAN_PHAM findById_SanPham(String ID_SanPham) {
        return _ISanPham_Service.findById(ID_SanPham);
    }

    @Override
    public HANG findById_Hang(String ID_Hang) {
        return _IHang_Service.findById(ID_Hang);
    }

    @Override
    public MAU_SAC findById_MauSac(String ID_MauSac) {
        return _IMauSac_Service.findById(ID_MauSac);
    }

    @Override
    public KICH_THUOC findById_KichThuoc(String ID_KichThuoc) {
        return _IKichThuoc_Service.findById(ID_KichThuoc);
    }

    @Override
    public GIOI_TINH findById_GioiTinh(String ID_GioiTinh) {
        return _IGioiTinh_Service.findById(ID_GioiTinh);
    }

    @Override
    public CHI_TIET_SAN_PHAM findById_Barcode(String Barcode) {
        return _IChiTietTietSanPham_Service.findByBarcode(Barcode);
    }

    @Override
    public CHI_TIET_SAN_PHAM findById_CTSP(String ID_CTSP) {
        return _IChiTietTietSanPham_Service.findById(ID_CTSP);
    }

    @Override
    public int updateSoLuong(int SoLuong, String ma) {
        return _IChiTietTietSanPham_Service.updateSoluong(SoLuong, ma);
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<HOA_DON> select_HoaDon(String sql, Object... args) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<HOA_DON> selectAll_HoaDon() {
        return _IHoaDon_Service.selectAll();
    }

    @Override
    public List<HOA_DON> selectByID_HoaDon(String ID) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public HOA_DON findById_HoaDon(String ID) {
        return _IHoaDon_Service.findById(ID);
    }

    @Override
    public HOA_DON findByName_HoaDon(String Names) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void insert_HoaDon(HOA_DON hoa_don) {
        _IHoaDon_Service.insert(hoa_don);
    }

    @Override
    public void update_HoaDon(HOA_DON hoa_don) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete_HoaDon(String ID) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int countMaHD_HoaDon(String SoHoaDon) {
        return _IHoaDon_Service.countMaHD(SoHoaDon);
    }

    @Override
    public List<Models.HOA_DON> GetBySoHoaDon(String SoHoaDon) {
        return _IHoaDon_Service.GetBySoHoaDon(SoHoaDon);
    }

    @Override
    public List<CHI_TIET_HOA_DON> select_CTHD(String sql, Object... args) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<CHI_TIET_HOA_DON> selectAll_CTHD() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<CHI_TIET_HOA_DON> selectByID_CTHD(String CHI_TIET_HOA_DON) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public CHI_TIET_HOA_DON findById_CTHD(String CHI_TIET_HOA_DON) {
        return _IChiTietHoaDon_Service.findById(CHI_TIET_HOA_DON);
    }

    @Override
    public void insert_CTHD(CHI_TIET_HOA_DON CHI_TIET_HOA_DON) {
        _IChiTietHoaDon_Service.insert(CHI_TIET_HOA_DON);
    }

    @Override
    public void update_CTHD(CHI_TIET_HOA_DON CHI_TIET_HOA_DON) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete_CTHD(String CHI_TIET_HOA_DON) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int count_CTHD() {
        return _IChiTietHoaDon_Service.countCTHD();
    }

    @Override
    public List<NHAN_VIEN> selectAll_NhanVien() {
        return _INhanVien_Service.selectAll();
    }

    @Override
    public List<NHAN_VIEN> selectByID_NhanVien(String ID) {
        return _INhanVien_Service.selectByID(ID);
    }

    @Override
    public NHAN_VIEN findById_NhanVien(String ID) {
        return _INhanVien_Service.findById(ID);
    }

    @Override
    public NHAN_VIEN findByName_NhanVien(String Names) {
        return _INhanVien_Service.findByName(Names);
    }

    @Override
    public void insert_NhanVien(NHAN_VIEN NHAN_VIEN) {
        _INhanVien_Service.insert(NHAN_VIEN);
    }

    @Override
    public void update_NhanVien(NHAN_VIEN NHAN_VIEN) {
        _INhanVien_Service.update(NHAN_VIEN);
    }

    @Override
    public void delete_NhanVien(String ID) {
        _INhanVien_Service.delete(ID);
    }

    @Override
    public List<KHACH_HANG> select_KhachHang(String sql, Object... args) {
        return null;
    }

    @Override
    public List<KHACH_HANG> selectAll_KhachHang() {
        return _IKhachHang_Service.selectAll();
    }

    @Override
    public List<KHACH_HANG> selectByID_KhachHang(String ID) {
        return _IKhachHang_Service.selectByID(ID);
    }

    @Override
    public KHACH_HANG findById_KhachHang(String ID) {
        return _IKhachHang_Service.findById(ID);
    }

    @Override
    public void insert_KhachHang(KHACH_HANG khach_hang) {
        _IKhachHang_Service.insert(khach_hang);
    }

    @Override
    public void update_KhachHang(KHACH_HANG khach_hang) {
        _IKhachHang_Service.update(khach_hang);
    }

    @Override
    public void delete_KhachHang(String ID) {
        _IKhachHang_Service.delete(ID);
    }

    @Override
    public List<KHACH_HANG> selectByKeyword(String keyWord) {
        return _IKhachHang_Service.selectByKeyword(keyWord);
    }

    @Override
    public KHACH_HANG findByNames_KhachHang(String ID) {
        return _IKhachHang_Service.findByNames(ID);
    }

    @Override
    public int removeSoLuong(int SoLuong, String ma,int soLuongNhapLai) {
        return _IChiTietTietSanPham_Service.removeSoLuong(SoLuong, ma,soLuongNhapLai);
      //  throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
     @Override
     public int selectMACTSP( String ma){
          return _IChiTietTietSanPham_Service.selectMACTSP(ma);
      }

    @Override
    public List<GIO_HANG> SelectGroupBy(String mahd) {
        List<GIO_HANG>list=new ArrayList<>();
        String sql="select mahd,ctsp.masp,tensp,sum(cthd.soluong),cthd.dongia from CHI_TIET_HOA_DON cthd\n" +
"join CHI_TIET_SAN_PHAM ctsp on cthd.MACTSP=ctsp.MACTSP\n" +
"join SAN_PHAM sp on sp.MASP=ctsp.MAsp\n" +
"where mahd=?\n" +
"group by ctsp.masp,tensp,cthd.dongia,mahd  ";
         try {
             PreparedStatement pre=DriverManager.getConnection(JdbcHelper.dburl,JdbcHelper.username,JdbcHelper.password).prepareStatement(sql);
             pre.setString(1, mahd);
             ResultSet rs=pre.executeQuery();
             while (rs.next()) { 
                 list.add(new GIO_HANG(rs.getString("mahd"),rs.getString("tensp"),rs.getString("masp"),rs.getInt(4),rs.getDouble(5)));
                
             }
             //  throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
         } catch (SQLException ex) {
             Logger.getLogger(QLyBanHang_Service.class.getName()).log(Level.SEVERE, null, ex);
         }
         return list;
    }
    
}
