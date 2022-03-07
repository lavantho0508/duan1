/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IServices;

import Models.CHUC_VU;
import Models.HANG;
import Models.NHAN_VIEN;
import Models.NHAN_VIEN_BUS;
import Models.NV_HAS_CV;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author DELL
 */
public interface IQLyNHAN_VIENService {

    public List<NHAN_VIEN> selectAll_Nhan_Vien();

    public List<NHAN_VIEN> selectByID_Nhan_Vien(String ID_NhanVien);

    public NHAN_VIEN findById_NhanVien(String ID_NhanVien);

    public NHAN_VIEN findByNames_NhanVien(String Names_NhanVien);

    public void insert_Nhan_vien(NHAN_VIEN NhanVien);

    public void update_Nhan_Vien(NHAN_VIEN NhanVien);

    public void delete_Nhan_Vien(String ID_Nhan_vien);

    public String randomCode();

    public List<NHAN_VIEN> selectEmailByID(String ma);

    public int changePassWord(String ma, String pass);

    public List<CHUC_VU> selectAll_CHUC_VU();

    public List<CHUC_VU> selectByID_CHUC_VU(String ID_CHUC_VU);

    public CHUC_VU findById_CHUC_VU(String ID_CHUC_VU);

    public CHUC_VU findByNames_CHUC_VU(String Names);

    public void insert_CHUC_VU(CHUC_VU CHUCVU);

    public void update_CHUC_VU(CHUC_VU CHUCVU);

    public void delete_CHUC_VU(String ID_CHUC_VU);

    public List<NV_HAS_CV> selectAll_NV_HAS_CV();

    public List<NV_HAS_CV> selectByID_NV_HAS_CV(String ID_CHUC_VU);

    public NV_HAS_CV findById_NV_HAS_CV(String ID_CHUC_VU);

    public void insert_NV_HAS_CV(NV_HAS_CV CHUCVU);

    public void update_NV_HAS_CV(NV_HAS_CV CHUCVU);

    public void delete_NV_HAS_CV(String NV_HAS_CV);
}
