/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IServices;
import Models.KHACH_HANG;
import java.util.List;
/**
 *
 * @author DEll
 */
public interface IQLyKhachHang_Service {
    public List<KHACH_HANG> selectAll_Khach_Hang();

    public List<KHACH_HANG> selectByID_Khach_Hang(String ID_KhachHang);

    public KHACH_HANG findById_Khach_Hang(String ID_KhachHang);

    public KHACH_HANG findByNames_Khach_Hang(String Names_KhachHang);

    public void insert_Khach_Hang(KHACH_HANG KhachHang);

    public void update_Khach_Hang(KHACH_HANG KhachHang);

    public void delete_Khach_Hang(String ID_KhachHang);
}
