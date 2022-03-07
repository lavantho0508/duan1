/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IServices;

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
import Models.SAN_PHAM;
import java.util.List;

/**
 *
 * @author BUI_QUANG_HIEU
 */
public interface IQLySanPhamService {

    public List<HANG> selectAll_Hang();

    public List<HANG> selectByID_Hang(String ID_Hang);

    public HANG findById_Hang(String ID_Hang);

    public HANG findByNames_Hang(String Names_Hang);

    public void insert_Hang(HANG hang);

    public void update_Hang(HANG hang);

    public void delete_Hang(String ID_Hang);

    public int countByIDHang(String IDHang);

    public List<GIOI_TINH> selectAll_GioiTinh();

    public List<GIOI_TINH> selectByID_GioiTinh(String ID_GioiTinh);

    public GIOI_TINH findById_GioiTinh(String ID_GioiTinh);

    public GIOI_TINH findByNames_GioiTinh(String Names_GioiTinh);

    public void insert_GioiTinh(GIOI_TINH gioi_tinh);

    public void update_GioiTinh(GIOI_TINH gioi_tinh);

    public void delete_GioiTinh(String ID_GioiTinh);

    public List<DON_VI_TINH> selectAll_DonViTinh();

    public List<DON_VI_TINH> selectByID_DonViTinh(String ID_DVT);

    public DON_VI_TINH findById_DonViTinh(String ID_DVT);

    public DON_VI_TINH findByNames_DonViTinh(String Names_DVT);

    public void insert_DonViTinh(DON_VI_TINH don_vi_tinh);

    public void update_DonViTinh(DON_VI_TINH don_vi_tinh);

    public void delete_DonViTinh(String ID_DVT);

    public List<LOAI_HANG> selectAll_LoaiHang();

    public List<LOAI_HANG> selectByID_LoaiHang(String ID_LoaiHang);

    public LOAI_HANG findById_LoaiHang(String ID_LoaiHang);

    public LOAI_HANG findByNames_LoaiHang(String Names_LoaiHang);

    public void insert_LoaiHang(LOAI_HANG loai_hang);

    public void update_LoaiHang(LOAI_HANG don_vi_tinh);

    public void delete_LoaiHang(String ID_LoaiHang);

    public List<KIEU_DANG> selectAll_KieuDang();

    public List<KIEU_DANG> selectByID_KieuDang(String ID_KieuDang);

    public KIEU_DANG findById_KieuDang(String ID_KieuDang);

    public KIEU_DANG findByNames_KieuDang(String Names_KieuDang);

    public void insert_KieuDang(KIEU_DANG kieu_dang);

    public void update_KieuDang(KIEU_DANG kieu_dang);

    public void delete_KieuDang(String ID_KieuDang);

    public List<LOAI_KINH> selectAll_LoaiKinh();

    public List<LOAI_KINH> selectByID_LoaiKinh(String ID_LoaiKinh);

    public LOAI_KINH findById_LoaiKinh(String ID_LoaiKinh);

    public LOAI_KINH findByNames_LoaiKinh(String Names_LoaiKinh);

    public void insert_LoaiKinh(LOAI_KINH loai_kinh);

    public void update_LoaiKinh(LOAI_KINH loai_kinh);

    public void delete_LoaiKinh(String ID_LoaiKinh);

    public List<KICH_THUOC> selectAll_KichThuoc();

    public List<KICH_THUOC> selectByID_KichThuoc(String ID_KichThuoc);

    public KICH_THUOC findById_KichThuoc(String ID_KichThuoc);

    public KICH_THUOC findByNames_KichThuoc(String Names_KichThuoc);

    public void insert_KichThuoc(KICH_THUOC kich_thuoc);

    public void update_KichThuoc(KICH_THUOC kich_thuoc);

    public void delete_KichThuoc(String ID_KichThuoc);

    public List<MAU_SAC> selectAll_MauSac();

    public List<MAU_SAC> selectByID_MauSac(String ID_MauSac);

    public MAU_SAC findById_MauSac(String ID_MauSac);

    public MAU_SAC findByNames_MauSac(String Names_MauSac);

    public void insert_MauSac(MAU_SAC mau_sac);

    public void update_MauSac(MAU_SAC mau_sac);

    public void delete_MauSac(String ID_MauSac);

    public List<SAN_PHAM> selectAll_SanPham();

    public List<SAN_PHAM> selectByID_SanPham(String ID_SanPham);

    public SAN_PHAM findById_SanPham(String ID_SanPham);

    public SAN_PHAM findByNames_SanPham(String Names_SanPham);

    public void insert_SanPham(SAN_PHAM san_pham);

    public void update_SanPham(SAN_PHAM san_pham);

    public void delete_SanPham(String ID_SanPham);

    public List<CHI_TIET_SAN_PHAM> selectAll_CTSP();

    public List<CHI_TIET_SAN_PHAM> selectByID_CTSP(String ID_CTSP);

    public CHI_TIET_SAN_PHAM findById_CTSP(String ID_CTSP);

    public Models.CHI_TIET_SAN_PHAM findByIdCTS_CTSP(String CHI_TIET_SAN_PHAM);

    public void insert_CTSP(CHI_TIET_SAN_PHAM chi_tiet_san_pham);

    public void update_CTSP(CHI_TIET_SAN_PHAM chi_tiet_san_pham);

    public void delete_CTSP(String ID_CTSP);

    public List<Models.SAN_PHAM_CT> selectAll();

    public void updateHangNull(String maHang);

    public int countByIDGioiTinh(String ID);

    public int countByIDDVT(String ID);

    public int countByIDLoaiHang(String ID);

    public int countByIDKieuDang(String ID);

    public int countByIDLoaiKinh(String ID);

    public int countByIDKichThuoc(String ID);

    public int countByIDMauSac(String ID);

    public CHI_TIET_HOA_DON findByMaCTSP(String MaCTSP);

    public CHI_TIET_SAN_PHAM findAllOfAMCTSP(String MaSP, String maKT, String maKD, String maMau, String maLoai, String MaGT, String maLH, String maDVT);

    public void updateTrangThai_CTSP(int MaCTSP, int TrangThai);
}
