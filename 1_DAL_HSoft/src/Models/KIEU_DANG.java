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
public class KIEU_DANG {

    private String MAKIEUDANG, KIEUDANG;
    private int trangThai;

    public KIEU_DANG() {
    }

    public KIEU_DANG(String MAKIEUDANG, String KIEUDANG, int trangThai) {
        this.MAKIEUDANG = MAKIEUDANG;
        this.KIEUDANG = KIEUDANG;
        this.trangThai = trangThai;
    }

    public String getMAKIEUDANG() {
        return MAKIEUDANG;
    }

    public void setMAKIEUDANG(String MAKIEUDANG) {
        this.MAKIEUDANG = MAKIEUDANG;
    }

    public String getKIEUDANG() {
        return KIEUDANG;
    }

    public void setKIEUDANG(String KIEUDANG) {
        this.KIEUDANG = KIEUDANG;
    }

    public int getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(int trangThai) {
        this.trangThai = trangThai;
    }

    @Override
    public String toString() {
        return KIEUDANG;
    }

}
