/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import java.util.Date;

/**
 *
 * @author LENOVO
 */
public class CHI_TIET_HOA_DON {
	//Dungna
    private int MACTHD;
    private String MAHD;
    private int MACTSP;
    private int SOLUONG;
    private double DONGIA, THANHTIEN;
    private int TRANGTHAI;

    public CHI_TIET_HOA_DON() {
    }

    public CHI_TIET_HOA_DON(int MACTHD, String MAHD, int MACTSP, int SOLUONG, double DONGIA, double THANHTIEN, int TRANGTHAI) {
        this.MACTHD = MACTHD;
        this.MAHD = MAHD;
        this.MACTSP = MACTSP;
        this.SOLUONG = SOLUONG;
        this.DONGIA = DONGIA;
        this.THANHTIEN = THANHTIEN;
        this.TRANGTHAI = TRANGTHAI;
    }

    public int getMACTHD() {
        return MACTHD;
    }

    public void setMACTHD(int MACTHD) {
        this.MACTHD = MACTHD;
    }

    public String getMAHD() {
        return MAHD;
    }

    public void setMAHD(String MAHD) {
        this.MAHD = MAHD;
    }

    public int getMACTSP() {
        return MACTSP;
    }

    public void setMACTSP(int MACTSP) {
        this.MACTSP = MACTSP;
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

    public double getTHANHTIEN() {
        return THANHTIEN;
    }

    public void setTHANHTIEN(double THANHTIEN) {
        this.THANHTIEN = THANHTIEN;
    }

    public int getTRANGTHAI() {
        return TRANGTHAI;
    }

    public void setTRANGTHAI(int TRANGTHAI) {
        this.TRANGTHAI = TRANGTHAI;
    }

}
