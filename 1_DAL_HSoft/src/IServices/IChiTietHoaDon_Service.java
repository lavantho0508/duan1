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
public interface IChiTietHoaDon_Service {

    public Models.CHI_TIET_HOA_DON readFromResultSet(ResultSet rs) throws SQLException;

    public List<Models.CHI_TIET_HOA_DON> select(String sql, Object... args);

    public List<Models.CHI_TIET_HOA_DON> selectAll();

    public List<Models.CHI_TIET_HOA_DON> selectByID(String CHI_TIET_HOA_DON);

    public Models.CHI_TIET_HOA_DON findById(String CHI_TIET_HOA_DON);

    public Models.CHI_TIET_HOA_DON findByMaCTSP(String MaCTSP);

    public void insert(Models.CHI_TIET_HOA_DON CHI_TIET_HOA_DON);

    public void update(Models.CHI_TIET_HOA_DON CHI_TIET_HOA_DON);

    public void delete(String CHI_TIET_HOA_DON);

    public int countCTHD();

}
