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
public class DON_VI_TINH {

    private String MADV, TEN;
    private int TRANGTHAI;

    public DON_VI_TINH() {
    }

    public DON_VI_TINH(String MADV, String TEN, int TRANGTHAI) {
        this.MADV = MADV;
        this.TEN = TEN;
        this.TRANGTHAI = TRANGTHAI;
    }

    public String getMADV() {
        return MADV;
    }

    public void setMADV(String MADV) {
        this.MADV = MADV;
    }

    public String getTEN() {
        return TEN;
    }

    public void setTEN(String TEN) {
        this.TEN = TEN;
    }

    public int getTRANGTHAI() {
        return TRANGTHAI;
    }

    public void setTRANGTHAI(int TRANGTHAI) {
        this.TRANGTHAI = TRANGTHAI;
    }

    @Override
    public String toString() {
        return TEN;
    }

}
