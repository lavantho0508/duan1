/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sevices;

import IServices.IKhachHang_Service;
import IServices.IQLyKhachHang_Service;
import Models.KHACH_HANG;
import java.util.List;

/**
 *
 * @author DEll
 */
public class QLyKhachHang_Service implements IQLyKhachHang_Service {

     IKhachHang_Service _KhachHang_Service = (IKhachHang_Service) new KhachHang_Service();

    @Override
    public List<KHACH_HANG> selectAll_Khach_Hang() {
        return _KhachHang_Service.selectAll();
    }

    @Override
    public List<KHACH_HANG> selectByID_Khach_Hang(String ID_KhachHang) {
        return _KhachHang_Service.selectByID(ID_KhachHang);
    }

    @Override
    public KHACH_HANG findById_Khach_Hang(String ID_KhachHang) {
        return _KhachHang_Service.findById(ID_KhachHang);
    }

    @Override
    public KHACH_HANG findByNames_Khach_Hang(String Names_KhachHang) {
        return _KhachHang_Service.findByNames(Names_KhachHang);
    }

    @Override
    public void insert_Khach_Hang(KHACH_HANG KhachHang) {
        _KhachHang_Service.insert(KhachHang);
    }

    @Override
    public void update_Khach_Hang(KHACH_HANG KhachHang) {
        _KhachHang_Service.update(KhachHang);

    }

    @Override
    public void delete_Khach_Hang(String ID_KhachHang) {
        _KhachHang_Service.delete(ID_KhachHang);

    }

}
