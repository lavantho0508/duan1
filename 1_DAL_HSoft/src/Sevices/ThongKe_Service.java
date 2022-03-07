/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sevices;

import Helper.JdbcHelper;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author LENOVO
 */
public class ThongKe_Service implements IServices.IThongKe{

    @Override
    public List<Object[]> getListOfArray(String sql, String[] cols, Object... args) {
   try {
            List<Object[]> list= new ArrayList<>();
            ResultSet rs = JdbcHelper.executeQuery(sql, args);
            while (rs.next()) {                
                Object[] vals= new Object[cols.length];
                for (int i = 0; i < cols.length; i++) {
                    vals[i] = rs.getObject(cols[i]);
                }
                list.add(vals);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }      
    }

    @Override
    public List<Object[]> getDoanhThu() {
        String sql = "{CALL SP_THONGKE}";
        String[] cols= {"MAKH","tenkh","MACTHD","TENSP","SOLUONG","DONGIA","NGAYLAP"};
        return this.getListOfArray(sql, cols);
    }
    
}
