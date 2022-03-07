/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IServices;

import Models.CHI_TIET_SAN_PHAM;
import Models.GIOI_TINH;
import Models.GIO_HANG;
import Models.HANG;
import Models.KHACH_HANG;
import Models.KICH_THUOC;
import Models.LOAI_HANG;
import Models.LOAI_KINH;
import Models.MAU_SAC;
import Models.SAN_PHAM;
import java.sql.ResultSet;
import java.util.List;

/**
 *
 * @author BUI_QUANG_HIEU
 */
public interface IQLyBanHang_Service {

    public LOAI_HANG findById_LoaiHang(String ID_LoaiHang);

    public LOAI_KINH findById_LoaiKinh(String ID_LoaiKinh);

    public HANG findById_Hang(String ID_Hang);

    public MAU_SAC findById_MauSac(String ID_MauSac);

    public KICH_THUOC findById_KichThuoc(String ID_KichThuoc);

    public GIOI_TINH findById_GioiTinh(String ID_GioiTinh);

    public SAN_PHAM findById_SanPham(String ID_SanPham);

    public List<CHI_TIET_SAN_PHAM> selectAll_CTSP();

    public List<CHI_TIET_SAN_PHAM> selectByID_CTSP(String ID_CTSP);

    public CHI_TIET_SAN_PHAM findById_CTSP(String ID_CTSP);

    public CHI_TIET_SAN_PHAM findById_Barcode(String Barcode);

    public int updateSoLuong(int SoLuong, String ma);

    public List<Models.HOA_DON> select_HoaDon(String sql, Object... args);

    public List<Models.HOA_DON> selectAll_HoaDon();

    public List<Models.HOA_DON> selectByID_HoaDon(String ID);

    public Models.HOA_DON findById_HoaDon(String ID);

    public Models.HOA_DON findByName_HoaDon(String Names);

    public void insert_HoaDon(Models.HOA_DON hoa_don);

    public void update_HoaDon(Models.HOA_DON hoa_don);

    public void delete_HoaDon(String ID);

    public int countMaHD_HoaDon(String SoHoaDon);

    public List<Models.HOA_DON> GetBySoHoaDon(String SoHoaDon);

    public List<Models.CHI_TIET_HOA_DON> select_CTHD(String sql, Object... args);

    public List<Models.CHI_TIET_HOA_DON> selectAll_CTHD();

    public List<Models.CHI_TIET_HOA_DON> selectByID_CTHD(String CHI_TIET_HOA_DON);

    public Models.CHI_TIET_HOA_DON findById_CTHD(String CHI_TIET_HOA_DON);

    public void insert_CTHD(Models.CHI_TIET_HOA_DON CHI_TIET_HOA_DON);

    public void update_CTHD(Models.CHI_TIET_HOA_DON CHI_TIET_HOA_DON);

    public void delete_CTHD(String CHI_TIET_HOA_DON);

    public int count_CTHD();

    public List<Models.NHAN_VIEN> selectAll_NhanVien();

    public List<Models.NHAN_VIEN> selectByID_NhanVien(String ID);

    public Models.NHAN_VIEN findById_NhanVien(String ID);

    public Models.NHAN_VIEN findByName_NhanVien(String Names);

    public void insert_NhanVien(Models.NHAN_VIEN NHAN_VIEN);

    public void update_NhanVien(Models.NHAN_VIEN NHAN_VIEN);

    public void delete_NhanVien(String ID);

    public List<Models.KHACH_HANG> select_KhachHang(String sql, Object... args);

    public List<Models.KHACH_HANG> selectAll_KhachHang();

    public List<Models.KHACH_HANG> selectByID_KhachHang(String ID);

    public Models.KHACH_HANG findById_KhachHang(String ID);

    public void insert_KhachHang(Models.KHACH_HANG khach_hang);

    public void update_KhachHang(Models.KHACH_HANG khach_hang);

    public void delete_KhachHang(String ID);

    public List<KHACH_HANG> selectByKeyword(String keyWord);

    public KHACH_HANG findByNames_KhachHang(String ID);
    public int removeSoLuong(int SoLuong,String ma,int soLuongNhapLai);
    public int selectMACTSP( String ma);
    public  List<GIO_HANG>SelectGroupBy(String mahd);

}
