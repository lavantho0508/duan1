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
public class HANG {

    private String MAHANG, TENHANG;
    private int TRANGTHAI;

    public String getMAHANG() {
        return MAHANG;
    }

    public void setMAHANG(String MAHANG) {
        this.MAHANG = MAHANG;
    }

    public String getTENHANG() {
        return TENHANG;
    }

    public void setTENHANG(String TENHANG) {
        this.TENHANG = TENHANG;
    }

    public int getTRANGTHAI() {
        return TRANGTHAI;
    }

    public void setTRANGTHAI(int TRANGTHAI) {
        this.TRANGTHAI = TRANGTHAI;
    }

    public HANG(String MAHANG, String TENHANG, int TRANGTHAI) {
        this.MAHANG = MAHANG;
        this.TENHANG = TENHANG;
        this.TRANGTHAI = TRANGTHAI;
    }

    public HANG() {
    }

    @Override
    public String toString() {
        return TENHANG;
    }

}
