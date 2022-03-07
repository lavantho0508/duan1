/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sevices;

import IServices.IChucVu_Service;
import IServices.INhanVien_Service;
import IServices.INhanVien_has_ChucVu_Service;
import IServices.IQLyNHAN_VIENService;
import Models.CHUC_VU;
import Models.HANG;
import Models.NHAN_VIEN;
import Models.NV_HAS_CV;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author LENOVO
 */
public class QLNHAN_VIEN_Service implements IQLyNHAN_VIENService {

     INhanVien_Service nv = (INhanVien_Service) new NHAN_VIEN_Service();
     IChucVu_Service cv = (IChucVu_Service) new ChucVu_Service();
     INhanVien_has_ChucVu_Service nv_has_cv = (INhanVien_has_ChucVu_Service) new NhanVien_Has_ChucVu_Service();

    @Override
    public List<NHAN_VIEN> selectAll_Nhan_Vien() {
        return nv.selectAll();
    }

    @Override
    public List<NHAN_VIEN> selectByID_Nhan_Vien(String ID_NhanVien) {
        return nv.selectByID(ID_NhanVien);
    }

    @Override
    public NHAN_VIEN findById_NhanVien(String ID_NhanVien) {
        return nv.findById(ID_NhanVien);
    }

    @Override
    public NHAN_VIEN findByNames_NhanVien(String Names_NhanVien) {
        return nv.findByName(Names_NhanVien);
    }

    @Override
    public void insert_Nhan_vien(NHAN_VIEN NhanVien) {
        nv.insert(NhanVien);
    }

    @Override
    public void update_Nhan_Vien(NHAN_VIEN NhanVien) {
        nv.update(NhanVien);
    }

    @Override
    public void delete_Nhan_Vien(String ID_Nhan_vien) {
        nv.delete(ID_Nhan_vien);
    }

    @Override
    public String randomCode() {
        return nv.randomCode();
        // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<NHAN_VIEN> selectEmailByID(String ma) {
        return nv.selectEmailByID(ma);
        // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int changePassWord(String ma, String pass) {
        return nv.changePassWord(ma, pass);
        // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<CHUC_VU> selectAll_CHUC_VU() {
        return cv.selectAll();
    }

    @Override
    public List<CHUC_VU> selectByID_CHUC_VU(String ID_CHUC_VU) {
        return cv.selectByID(ID_CHUC_VU);
    }

    @Override
    public CHUC_VU findById_CHUC_VU(String ID_CHUC_VU) {
        return cv.findById(ID_CHUC_VU);
    }

    @Override
    public void insert_CHUC_VU(CHUC_VU CHUCVU) {
        cv.insert(CHUCVU);
    }

    @Override
    public void update_CHUC_VU(CHUC_VU CHUCVU) {
        cv.update(CHUCVU);
    }

    @Override
    public void delete_CHUC_VU(String ID_CHUC_VU) {
        cv.delete(ID_CHUC_VU);
    }

    @Override
    public List<NV_HAS_CV> selectAll_NV_HAS_CV() {
        return nv_has_cv.selectAll();
    }

    @Override
    public List<NV_HAS_CV> selectByID_NV_HAS_CV(String ID_CHUC_VU) {
        return nv_has_cv.selectByID(ID_CHUC_VU);
    }

    @Override
    public NV_HAS_CV findById_NV_HAS_CV(String ID_CHUC_VU) {
        return nv_has_cv.findById(ID_CHUC_VU);
    }

    @Override
    public void insert_NV_HAS_CV(NV_HAS_CV CHUCVU) {
        nv_has_cv.insert(CHUCVU);
    }

    @Override
    public void update_NV_HAS_CV(NV_HAS_CV CHUCVU) {
        nv_has_cv.update(CHUCVU);
    }

    @Override
    public void delete_NV_HAS_CV(String NV_HAS_CV) {
        nv_has_cv.delete(NV_HAS_CV);
    }

    @Override
    public CHUC_VU findByNames_CHUC_VU(String Names) {
        return cv.findByNames(Names);
    }
}
