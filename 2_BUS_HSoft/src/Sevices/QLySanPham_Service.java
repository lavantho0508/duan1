/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sevices;

import IServices.IChiTietHoaDon_Service;
import IServices.IChiTietTietSanPham_Service;
import IServices.IDonViTinh_Service;
import IServices.IGioiTinh_Service;
import IServices.IHang_Service;
import IServices.IKichThuoc_Service;
import IServices.IKieuDang_Service;
import IServices.ILoaiHoang_Service;
import IServices.ILoaiKinh_Service;
import IServices.IMauSac_Service;
import IServices.IQLySanPhamService;
import IServices.ISanPham_Service;
import Models.CHI_TIET_HOA_DON;
import Models.CHI_TIET_SAN_PHAM;
import Models.DON_VI_TINH;
import Models.GIOI_TINH;
import Models.HANG;
import Models.KICH_THUOC;
import Models.KIEU_DANG;
import Models.LOAI_HANG;
import Models.LOAI_KINH;
import Models.MAU_SAC;
import Models.MauSac_Service;
import Models.SAN_PHAM;
import Models.SAN_PHAM_CT;
import java.util.List;

/**
 *
 * @author BUI_QUANG_HIEU
 */
public class QLySanPham_Service implements IQLySanPhamService {

    IHang_Service _IHang_Service = (IHang_Service) new Hang_Service();
    IGioiTinh_Service _IGioiTinh_Service = (IGioiTinh_Service) new GioiTinh_Service();
    IDonViTinh_Service _IDonViTinh_Service = (IDonViTinh_Service) new DonViTinh_Service();
    ILoaiHoang_Service _ILoaiHoang_Service = (ILoaiHoang_Service) new LoaiHang_Service();
    IKieuDang_Service _IKieuDang_Service = (IKieuDang_Service) new KieuDang_Service();
    ILoaiKinh_Service _ILoaiKinh_Service = (ILoaiKinh_Service) new LoaiKinh_Service();
    IKichThuoc_Service _IKichThuoc_Service = (IKichThuoc_Service) new KichThuoc_Service();
    IMauSac_Service _IMauSac_Service = (IMauSac_Service) new MauSac_Service();
    IServices.SAN_PHAM sp = (IServices.SAN_PHAM) new CHI_TIET_SP();
    ISanPham_Service _ISanPham_Service = (ISanPham_Service) new SanPham_Service();
    IChiTietTietSanPham_Service _IChiTietTietSanPham_Service = (IChiTietTietSanPham_Service) new ChiTietSanPham_Service();
    IChiTietHoaDon_Service _IChiTietHoaDon_Service = (IChiTietHoaDon_Service) new ChiTietHoaDon_Service();

    @Override
    public List<HANG> selectAll_Hang() {
        return _IHang_Service.selectAll();
    }

    @Override
    public List<HANG> selectByID_Hang(String ID_Hang) {
        return _IHang_Service.selectByID(ID_Hang);
    }

    @Override
    public HANG findById_Hang(String ID_Hang) {
        return _IHang_Service.findById(ID_Hang);
    }

    @Override
    public void insert_Hang(HANG hang) {
        _IHang_Service.insert(hang);
    }

    @Override
    public void update_Hang(HANG hang) {
        _IHang_Service.update(hang);
    }

    @Override
    public void delete_Hang(String ID_Hang) {
        _IHang_Service.delete(ID_Hang);
    }

    @Override
    public HANG findByNames_Hang(String Names_Hang) {
        return _IHang_Service.findByNames(Names_Hang);
    }

    @Override
    public List<GIOI_TINH> selectAll_GioiTinh() {
        return _IGioiTinh_Service.selectAll();
    }

    @Override
    public List<GIOI_TINH> selectByID_GioiTinh(String ID_GioiTinh) {
        return _IGioiTinh_Service.selectByID(ID_GioiTinh);
    }

    @Override
    public GIOI_TINH findById_GioiTinh(String ID_GioiTinh) {
        return _IGioiTinh_Service.findById(ID_GioiTinh);
    }

    @Override
    public GIOI_TINH findByNames_GioiTinh(String Names_GioiTinh) {
        return _IGioiTinh_Service.findByNames(Names_GioiTinh);
    }

    @Override
    public void insert_GioiTinh(GIOI_TINH gioi_tinh) {
        _IGioiTinh_Service.insert(gioi_tinh);
    }

    @Override
    public void update_GioiTinh(GIOI_TINH gioi_tinh) {
        _IGioiTinh_Service.update(gioi_tinh);
    }

    @Override
    public void delete_GioiTinh(String ID_GioiTinh) {
        _IGioiTinh_Service.delete(ID_GioiTinh);
    }

    @Override
    public List<DON_VI_TINH> selectAll_DonViTinh() {
        return _IDonViTinh_Service.selectAll();
    }

    @Override
    public List<DON_VI_TINH> selectByID_DonViTinh(String ID_DVT) {
        return _IDonViTinh_Service.selectByID(ID_DVT);
    }

    @Override
    public DON_VI_TINH findById_DonViTinh(String ID_DVT) {
        return _IDonViTinh_Service.findById(ID_DVT);
    }

    @Override
    public DON_VI_TINH findByNames_DonViTinh(String Names_DVT) {
        return _IDonViTinh_Service.findByNames(Names_DVT);
    }

    @Override
    public void insert_DonViTinh(DON_VI_TINH don_vi_tinh) {
        _IDonViTinh_Service.insert(don_vi_tinh);
    }

    @Override
    public void update_DonViTinh(DON_VI_TINH don_vi_tinh) {
        _IDonViTinh_Service.update(don_vi_tinh);
    }

    @Override
    public void delete_DonViTinh(String ID_DVT) {
        _IDonViTinh_Service.delete(ID_DVT);
    }

    @Override
    public List<LOAI_HANG> selectAll_LoaiHang() {
        return _ILoaiHoang_Service.selectAll();
    }

    @Override
    public List<LOAI_HANG> selectByID_LoaiHang(String ID_LoaiHang) {
        return _ILoaiHoang_Service.selectByID(ID_LoaiHang);
    }

    @Override
    public LOAI_HANG findById_LoaiHang(String ID_LoaiHang) {
        return _ILoaiHoang_Service.findById(ID_LoaiHang);
    }

    @Override
    public LOAI_HANG findByNames_LoaiHang(String Names_LoaiHang) {
        return _ILoaiHoang_Service.findByNames(Names_LoaiHang);
    }

    @Override
    public void insert_LoaiHang(LOAI_HANG loai_hang) {
        _ILoaiHoang_Service.insert(loai_hang);
    }

    @Override
    public void update_LoaiHang(LOAI_HANG don_vi_tinh) {
        _ILoaiHoang_Service.update(don_vi_tinh);
    }

    @Override
    public void delete_LoaiHang(String ID_LoaiHang) {
        _ILoaiHoang_Service.delete(ID_LoaiHang);
    }

    @Override
    public List<KIEU_DANG> selectAll_KieuDang() {
        return _IKieuDang_Service.selectAll();
    }

    @Override
    public List<KIEU_DANG> selectByID_KieuDang(String ID_KieuDang) {
        return _IKieuDang_Service.selectByID(ID_KieuDang);
    }

    @Override
    public KIEU_DANG findById_KieuDang(String ID_KieuDang) {
        return _IKieuDang_Service.findById(ID_KieuDang);
    }

    @Override
    public KIEU_DANG findByNames_KieuDang(String Names_KieuDang) {
        return _IKieuDang_Service.findByNames(Names_KieuDang);
    }

    @Override
    public void insert_KieuDang(KIEU_DANG kieu_dang) {
        _IKieuDang_Service.insert(kieu_dang);
    }

    @Override
    public void update_KieuDang(KIEU_DANG kieu_dang) {
        _IKieuDang_Service.update(kieu_dang);
    }

    @Override
    public void delete_KieuDang(String ID_KieuDang) {
        _IKieuDang_Service.delete(ID_KieuDang);
    }

    @Override
    public List<LOAI_KINH> selectAll_LoaiKinh() {
        return _ILoaiKinh_Service.selectAll();
    }

    @Override
    public List<LOAI_KINH> selectByID_LoaiKinh(String ID_LoaiKinh) {
        return _ILoaiKinh_Service.selectByID(ID_LoaiKinh);
    }

    @Override
    public LOAI_KINH findById_LoaiKinh(String ID_LoaiKinh) {
        return _ILoaiKinh_Service.findById(ID_LoaiKinh);
    }

    @Override
    public LOAI_KINH findByNames_LoaiKinh(String Names_LoaiKinh) {
        return _ILoaiKinh_Service.findByNames(Names_LoaiKinh);
    }

    @Override
    public void insert_LoaiKinh(LOAI_KINH loai_kinh) {
        _ILoaiKinh_Service.insert(loai_kinh);
    }

    @Override
    public void update_LoaiKinh(LOAI_KINH loai_kinh) {
        _ILoaiKinh_Service.update(loai_kinh);
    }

    @Override
    public void delete_LoaiKinh(String ID_LoaiKinh) {
        _ILoaiKinh_Service.delete(ID_LoaiKinh);
    }

    @Override
    public List<KICH_THUOC> selectAll_KichThuoc() {
        return _IKichThuoc_Service.selectAll();
    }

    @Override
    public List<KICH_THUOC> selectByID_KichThuoc(String ID_KichThuoc) {
        return _IKichThuoc_Service.selectByID(ID_KichThuoc);
    }

    @Override
    public KICH_THUOC findById_KichThuoc(String ID_KichThuoc) {
        return _IKichThuoc_Service.findById(ID_KichThuoc);
    }

    @Override
    public KICH_THUOC findByNames_KichThuoc(String Names_KichThuoc) {
        return _IKichThuoc_Service.findByNames(Names_KichThuoc);
    }

    @Override
    public void insert_KichThuoc(KICH_THUOC kich_thuoc) {
        _IKichThuoc_Service.insert(kich_thuoc);
    }

    @Override
    public void update_KichThuoc(KICH_THUOC kich_thuoc) {
        _IKichThuoc_Service.update(kich_thuoc);
    }

    @Override
    public void delete_KichThuoc(String ID_KichThuoc) {
        _IKichThuoc_Service.delete(ID_KichThuoc);
    }

    @Override
    public List<MAU_SAC> selectAll_MauSac() {
        return _IMauSac_Service.selectAll();
    }

    @Override
    public List<MAU_SAC> selectByID_MauSac(String ID_MauSac) {
        return _IMauSac_Service.selectByID(ID_MauSac);
    }

    @Override
    public MAU_SAC findById_MauSac(String ID_MauSac) {
        return _IMauSac_Service.findById(ID_MauSac);
    }

    @Override
    public MAU_SAC findByNames_MauSac(String Names_MauSac) {
        return _IMauSac_Service.findByNames(Names_MauSac);
    }

    @Override
    public void insert_MauSac(MAU_SAC mau_sac) {
        _IMauSac_Service.insert(mau_sac);
    }

    @Override
    public void update_MauSac(MAU_SAC mau_sac) {
        _IMauSac_Service.update(mau_sac);
    }

    @Override
    public void delete_MauSac(String ID_MauSac) {
        _IMauSac_Service.delete(ID_MauSac);
    }

    @Override
    public List<SAN_PHAM> selectAll_SanPham() {
        return _ISanPham_Service.selectAll();
    }

    @Override
    public List<SAN_PHAM> selectByID_SanPham(String ID_SanPham) {
        return _ISanPham_Service.selectByID(ID_SanPham);
    }

    @Override
    public SAN_PHAM findById_SanPham(String ID_SanPham) {
        return _ISanPham_Service.findById(ID_SanPham);
    }

    @Override
    public SAN_PHAM findByNames_SanPham(String Names_SanPham) {
        return _ISanPham_Service.findByName(Names_SanPham);
    }

    @Override
    public void insert_SanPham(SAN_PHAM san_pham) {
        _ISanPham_Service.insert(san_pham);
    }

    @Override
    public void update_SanPham(SAN_PHAM san_pham) {
        _ISanPham_Service.update(san_pham);
    }

    @Override
    public void delete_SanPham(String ID_SanPham) {
        _ISanPham_Service.delete(ID_SanPham);
    }

    @Override
    public List<CHI_TIET_SAN_PHAM> selectAll_CTSP() {
        return _IChiTietTietSanPham_Service.selectAll();
    }

    @Override
    public List<CHI_TIET_SAN_PHAM> selectByID_CTSP(String ID_CTSP) {
        return _IChiTietTietSanPham_Service.selectByID(ID_CTSP);
    }

    @Override
    public CHI_TIET_SAN_PHAM findById_CTSP(String ID_CTSP) {
        return _IChiTietTietSanPham_Service.findById(ID_CTSP);
    }

    @Override
    public void insert_CTSP(CHI_TIET_SAN_PHAM chi_tiet_san_pham) {
        _IChiTietTietSanPham_Service.insert(chi_tiet_san_pham);
    }

    @Override
    public void update_CTSP(CHI_TIET_SAN_PHAM chi_tiet_san_pham) {
        _IChiTietTietSanPham_Service.update(chi_tiet_san_pham);
    }

    @Override
    public void delete_CTSP(String ID_CTSP) {
        _IChiTietTietSanPham_Service.delete(ID_CTSP);
    }

    @Override
    public List<SAN_PHAM_CT> selectAll() {
        return sp.selectAll();
        //  throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int countByIDHang(String IDHang) {
        return _IHang_Service.countByIDHang(IDHang);
    }

    @Override
    public void updateHangNull(String maHang) {
        _ISanPham_Service.updateHangNull(maHang);
    }

    @Override
    public int countByIDGioiTinh(String ID) {
        return _IGioiTinh_Service.countIDGioiTinh(ID);
    }

    @Override
    public int countByIDDVT(String ID) {
        return _IChiTietTietSanPham_Service.countByIDDVT(ID);
    }

    @Override
    public int countByIDLoaiHang(String ID) {
        return _IChiTietTietSanPham_Service.countByIDLoaiHang(ID);
    }

    @Override
    public int countByIDKieuDang(String ID) {
        return _IChiTietTietSanPham_Service.countByIDKieuDang(ID);
    }

    @Override
    public int countByIDLoaiKinh(String ID) {
        return _IChiTietTietSanPham_Service.countByIDLoaiKinh(ID);
    }

    @Override
    public int countByIDKichThuoc(String ID) {
        return _IChiTietTietSanPham_Service.countByIDKichThuoc(ID);
    }

    @Override
    public int countByIDMauSac(String ID) {
        return _IChiTietTietSanPham_Service.countByIDMauSac(ID);
    }

    public int selectMACTSP(String ma) {
        return _IChiTietTietSanPham_Service.selectMACTSP(ma);
    }

    @Override
    public CHI_TIET_HOA_DON findByMaCTSP(String MaCTSP) {
        return _IChiTietHoaDon_Service.findByMaCTSP(MaCTSP);
    }

    @Override
    public CHI_TIET_SAN_PHAM findAllOfAMCTSP(String MaSP, String maKT, String maKD, String maMau, String maLoai, String MaGT, String maLH, String maDVT) {
        return _IChiTietTietSanPham_Service.findAllOfAMCTSP(MaSP, maKT, maKD, maMau, maLoai, MaGT, maLH, maDVT);
    }

    @Override
    public void updateTrangThai_CTSP(int MaCTSP, int TrangThai) {
        _IChiTietTietSanPham_Service.updateTrangThai(MaCTSP, TrangThai);
    }

    @Override
    public CHI_TIET_SAN_PHAM findByIdCTS_CTSP(String CHI_TIET_SAN_PHAM) {
        return _IChiTietTietSanPham_Service.findByIdCTSP(CHI_TIET_SAN_PHAM);
    }

}
