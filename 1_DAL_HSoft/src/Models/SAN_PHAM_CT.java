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
public class SAN_PHAM_CT {

    private String TENLOAIHANG,MASP,TENSP,THONGTINSP;
    private int SOLUONG;
            private double DONGIA;

    public SAN_PHAM_CT() {
    }

    public SAN_PHAM_CT(String TENLOAIHANG, String MASP, String TENSP, String THONGTINSP, int SOLUONG, double DONGIA) {
        this.TENLOAIHANG = TENLOAIHANG;
        this.MASP = MASP;
        this.TENSP = TENSP;
        this.THONGTINSP = THONGTINSP;
        this.SOLUONG = SOLUONG;
        this.DONGIA = DONGIA;
    }

    public String getTENLOAIHANG() {
        return TENLOAIHANG;
    }

    public void setTENLOAIHANG(String TENLOAIHANG) {
        this.TENLOAIHANG = TENLOAIHANG;
    }

    public String getMASP() {
        return MASP;
    }

    public void setMASP(String MASP) {
        this.MASP = MASP;
    }

    public String getTENSP() {
        return TENSP;
    }

    public void setTENSP(String TENSP) {
        this.TENSP = TENSP;
    }

    public String getTHONGTINSP() {
        return THONGTINSP;
    }

    public void setTHONGTINSP(String THONGTINSP) {
        this.THONGTINSP = THONGTINSP;
    }

    public int getSOLUONG() {
        return SOLUONG;
    }

    public void setSOLUONG(int SOLUONG) {
        this.SOLUONG = SOLUONG;
    }

    public double getDONGIA() {
        return DONGIA;
    }

    public void setDONGIA(double DONGIA) {
        this.DONGIA = DONGIA;
    }

    
    
}
