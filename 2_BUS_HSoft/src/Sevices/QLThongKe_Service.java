/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sevices;

import IServices.IQLyThongKe_Service;
import IServices.IThongKe;
import java.util.List;

/**
 *
 * @author LENOVO
 */
public class QLThongKe_Service implements IQLyThongKe_Service{
    static IThongKe tk = (IThongKe) new ThongKe_Service();
    @Override
    public List<Object[]> getListOfArray(String sql, String[] cols, Object... args) {
        return tk.getListOfArray(sql, cols, args);
    }

    @Override
    public List<Object[]> getDoanhThu() {
        return tk.getDoanhThu();
    }
    
}
