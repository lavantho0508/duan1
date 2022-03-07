/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IServices;

import java.util.List;

/**
 *
 * @author LENOVO
 */
public interface IThongKe {

    public List<Object[]> getListOfArray(String sql, String[] cols, Object... args);

    public List<Object[]> getDoanhThu();
}
