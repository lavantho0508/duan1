/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

/**
 *
 * @author BUI_QUANG_HIEU
 */
public class GIOI_TINH {

    private String MAGT, GIOITINH;
    private int TrangThai;

    public GIOI_TINH(String MAGT, String GIOITINH, int TrangThai) {
        this.MAGT = MAGT;
        this.GIOITINH = GIOITINH;
        this.TrangThai = TrangThai;
    }

    public GIOI_TINH() {
    }

    public String getMAGT() {
        return MAGT;
    }

    public void setMAGT(String MAGT) {
        this.MAGT = MAGT;
    }

    public String getGIOITINH() {
        return GIOITINH;
    }

    public void setGIOITINH(String GIOITINH) {
        this.GIOITINH = GIOITINH;
    }

    public int getTrangThai() {
        return TrangThai;
    }

    public void setTrangThai(int TrangThai) {
        this.TrangThai = TrangThai;
    }

    @Override
    public String toString() {
        return GIOITINH;
    }

}
