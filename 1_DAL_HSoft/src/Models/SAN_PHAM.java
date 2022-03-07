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
public class SAN_PHAM {

    private String MASP, MAHANG, TENSP;
    private int TRANGTHAI;

    public SAN_PHAM(String MASP, String MAHANG, String TENSP, int TRANGTHAI) {
        this.MASP = MASP;
        this.MAHANG = MAHANG;
        this.TENSP = TENSP;
        this.TRANGTHAI = TRANGTHAI;
    }

    public SAN_PHAM() {
    }

    public String getMASP() {
        return MASP;
    }

    public void setMASP(String MASP) {
        this.MASP = MASP;
    }

    public String getMAHANG() {
        return MAHANG;
    }

    public void setMAHANG(String MAHANG) {
        this.MAHANG = MAHANG;
    }

    public String getTENSP() {
        return TENSP;
    }

    public void setTENSP(String TENSP) {
        this.TENSP = TENSP;
    }

    public int getTRANGTHAI() {
        return TRANGTHAI;
    }

    public void setTRANGTHAI(int TRANGTHAI) {
        this.TRANGTHAI = TRANGTHAI;
    }

    @Override
    public String toString() {
        return TENSP;
    }

}
