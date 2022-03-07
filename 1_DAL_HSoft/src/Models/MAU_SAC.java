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
public class MAU_SAC {

    private String MAMAU, TENMAU;
    private int TRANGTHAI;

    public MAU_SAC() {
    }

    public MAU_SAC(String MAMAU, String TENMAU, int TRANGTHAI) {
        this.MAMAU = MAMAU;
        this.TENMAU = TENMAU;
        this.TRANGTHAI = TRANGTHAI;
    }

    public String getMAMAU() {
        return MAMAU;
    }

    public void setMAMAU(String MAMAU) {
        this.MAMAU = MAMAU;
    }

    public String getTENMAU() {
        return TENMAU;
    }

    public void setTENMAU(String TENMAU) {
        this.TENMAU = TENMAU;
    }

    public int getTRANGTHAI() {
        return TRANGTHAI;
    }

    public void setTRANGTHAI(int TRANGTHAI) {
        this.TRANGTHAI = TRANGTHAI;
    }

    @Override
    public String toString() {
        return TENMAU;
    }

}
