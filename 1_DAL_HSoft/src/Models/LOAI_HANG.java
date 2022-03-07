/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

/**
 *
 * @author LENOVO
 */
public class LOAI_HANG {
    private String MALOAIHANG, TENLOAIHANG;
    private int TRANGTHAI;

    public LOAI_HANG() {
    }

    public LOAI_HANG(String MALOAIHANG, String TENLOAIHANG, int TRANGTHAI) {
        this.MALOAIHANG = MALOAIHANG;
        this.TENLOAIHANG = TENLOAIHANG;
        this.TRANGTHAI = TRANGTHAI;
    }

    public String getMALOAIHANG() {
        return MALOAIHANG;
    }

    public void setMALOAIHANG(String MALOAIHANG) {
        this.MALOAIHANG = MALOAIHANG;
    }

    public String getTENLOAIHANG() {
        return TENLOAIHANG;
    }

    public void setTENLOAIHANG(String TENLOAIHANG) {
        this.TENLOAIHANG = TENLOAIHANG;
    }

    public int getTRANGTHAI() {
        return TRANGTHAI;
    }

    public void setTRANGTHAI(int TRANGTHAI) {
        this.TRANGTHAI = TRANGTHAI;
    }

    @Override
    public String toString() {
        return TENLOAIHANG;
    }
    
    
}
