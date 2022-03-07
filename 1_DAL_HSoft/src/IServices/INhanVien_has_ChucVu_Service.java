/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IServices;

import Models.CHUC_VU;
import Models.NV_HAS_CV;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author LENOVO
 */
public interface INhanVien_has_ChucVu_Service {
    
    public NV_HAS_CV readFromResultSet(ResultSet rs) throws SQLException;

    public List<NV_HAS_CV> select(String sql, Object... args);

    public List<NV_HAS_CV> selectAll();

    public List<NV_HAS_CV> selectByID(String ID);

    public NV_HAS_CV findById(String ID);

    public void insert(NV_HAS_CV NV_HAS_CV);

    public void update(NV_HAS_CV NV_HAS_CV);

    public void delete(String ID);
}
