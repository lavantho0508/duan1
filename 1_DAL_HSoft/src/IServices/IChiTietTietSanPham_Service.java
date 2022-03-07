/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IServices;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author LENOVO
 */
public interface IChiTietTietSanPham_Service {

    public Models.CHI_TIET_SAN_PHAM readFromResultSet(ResultSet rs) throws SQLException;

    public List<Models.CHI_TIET_SAN_PHAM> select(String sql, Object... args);

    public List<Models.CHI_TIET_SAN_PHAM> selectAll();

    public List<Models.CHI_TIET_SAN_PHAM> selectByID(String CHI_TIET_SAN_PHAM);

    public Models.CHI_TIET_SAN_PHAM findById(String CHI_TIET_SAN_PHAM);

    public Models.CHI_TIET_SAN_PHAM findByIdCTSP(String CHI_TIET_SAN_PHAM);

    public Models.CHI_TIET_SAN_PHAM findByBarcode(String CHI_TIET_SAN_PHAM);

    public Models.CHI_TIET_SAN_PHAM findAllOfAMCTSP(String MaSP, String maKT, String maKD, String maMau, String maLoai, String MaGT, String maLH, String maDVT);

    public void insert(Models.CHI_TIET_SAN_PHAM CHI_TIET_SAN_PHAM);

    public void update(Models.CHI_TIET_SAN_PHAM CHI_TIET_SAN_PHAM);

    public void updateTrangThai(int MaCTSP, int TrangThai);

    public void delete(String CHI_TIET_SAN_PHAM);

    public int countByIDDVT(String ID);

    public int countByIDLoaiHang(String ID);

    public int countByIDKieuDang(String ID);

    public int countByIDLoaiKinh(String ID);

    public int countByIDKichThuoc(String ID);

    public int countByIDMauSac(String ID);

    public int updateSoluong(int SoLuong, String ma);

    public int selectMACTSP(String ma);

    public int removeSoLuong(int SoLuong, String masp, int soLuongNhapLai);


}
