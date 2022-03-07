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
public class LOAI_KINH {

    private String MALOAI, LOAIKINH;
    private int TRANGTHAI;

    public LOAI_KINH() {
    }

    public LOAI_KINH(String MALOAI, String LOAIKINH, int TRANGTHAI) {
        this.MALOAI = MALOAI;
        this.LOAIKINH = LOAIKINH;
        this.TRANGTHAI = TRANGTHAI;
    }

    public String getMALOAI() {
        return MALOAI;
    }

    public void setMALOAI(String MALOAI) {
        this.MALOAI = MALOAI;
    }

    public String getLOAIKINH() {
        return LOAIKINH;
    }

    public void setLOAIKINH(String LOAIKINH) {
        this.LOAIKINH = LOAIKINH;
    }

    public int getTRANGTHAI() {
        return TRANGTHAI;
    }

    public void setTRANGTHAI(int TRANGTHAI) {
        this.TRANGTHAI = TRANGTHAI;
    }

    @Override
    public String toString() {
        return LOAIKINH;
    }

}
