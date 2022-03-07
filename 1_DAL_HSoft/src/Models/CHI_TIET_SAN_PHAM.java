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
public class CHI_TIET_SAN_PHAM {

    private int MACTSP;
    private String MAKIEUDANG, MADV, MAKICHTHUOC, MAMAU, MALOAI, MAGT, MASP, MALOAIHANG, BARCODE, IMAGES;
    private int SOLUONG;
    private double DONGIA;
    private int TRANGTHAI;

    public CHI_TIET_SAN_PHAM() {
    }

    public CHI_TIET_SAN_PHAM(int MACTSP, String MAKIEUDANG, String MADV, String MAKICHTHUOC, String MAMAU, String MALOAI, String MAGT, String MASP, String MALOAIHANG, String BARCODE, String IMAGES, int SOLUONG, double DONGIA, int TRANGTHAI) {
        this.MACTSP = MACTSP;
        this.MAKIEUDANG = MAKIEUDANG;
        this.MADV = MADV;
        this.MAKICHTHUOC = MAKICHTHUOC;
        this.MAMAU = MAMAU;
        this.MALOAI = MALOAI;
        this.MAGT = MAGT;
        this.MASP = MASP;
        this.MALOAIHANG = MALOAIHANG;
        this.BARCODE = BARCODE;
        this.IMAGES = IMAGES;
        this.SOLUONG = SOLUONG;
        this.DONGIA = DONGIA;
        this.TRANGTHAI = TRANGTHAI;
    }

    public String getMAKIEUDANG() {
        return MAKIEUDANG;
    }

    public void setMAKIEUDANG(String MAKIEUDANG) {
        this.MAKIEUDANG = MAKIEUDANG;
    }

    public int getMACTSP() {
        return MACTSP;
    }

    public void setMACTSP(int MACTSP) {
        this.MACTSP = MACTSP;
    }

    public String getMADV() {
        return MADV;
    }

    public void setMADV(String MADV) {
        this.MADV = MADV;
    }

    public String getMAKICHTHUOC() {
        return MAKICHTHUOC;
    }

    public void setMAKICHTHUOC(String MAKICHTHUOC) {
        this.MAKICHTHUOC = MAKICHTHUOC;
    }

    public String getMAMAU() {
        return MAMAU;
    }

    public void setMAMAU(String MAMAU) {
        this.MAMAU = MAMAU;
    }

    public String getMALOAI() {
        return MALOAI;
    }

    public void setMALOAI(String MALOAI) {
        this.MALOAI = MALOAI;
    }

    public String getMAGT() {
        return MAGT;
    }

    public void setMAGT(String MAGT) {
        this.MAGT = MAGT;
    }

    public String getMASP() {
        return MASP;
    }

    public void setMASP(String MASP) {
        this.MASP = MASP;
    }

    public String getMALOAIHANG() {
        return MALOAIHANG;
    }

    public void setMALOAIHANG(String MALOAIHANG) {
        this.MALOAIHANG = MALOAIHANG;
    }

    public String getBARCODE() {
        return BARCODE;
    }

    public void setBARCODE(String BARCODE) {
        this.BARCODE = BARCODE;
    }

    public String getIMAGES() {
        return IMAGES;
    }

    public void setIMAGES(String IMAGES) {
        this.IMAGES = IMAGES;
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

    public int getTRANGTHAI() {
        return TRANGTHAI;
    }

    public void setTRANGTHAI(int TRANGTHAI) {
        this.TRANGTHAI = TRANGTHAI;
    }

}
