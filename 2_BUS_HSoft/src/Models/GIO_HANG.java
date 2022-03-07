/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

/**
 *
 * @author DELL
 */
public class GIO_HANG {
    

    private String mahd, tensp,masp;
    private int soluong;
    private double dongia;

    public GIO_HANG() {
    }

    public GIO_HANG(String mahd, String tensp, String masp, int soluong, double dongia) {
        this.mahd = mahd;
        this.tensp = tensp;
        this.masp = masp;
        this.soluong = soluong;
        this.dongia = dongia;
    }

    public String getMahd() {
        return mahd;
    }

    public void setMahd(String mahd) {
        this.mahd = mahd;
    }

    public String getTensp() {
        return tensp;
    }

    public void setTensp(String tensp) {
        this.tensp = tensp;
    }

    public String getMasp() {
        return masp;
    }

    public void setMasp(String masp) {
        this.masp = masp;
    }

    public int getSoluong() {
        return soluong;
    }

    public void setSoluong(int soluong) {
        this.soluong = soluong;
    }

    public double getDongia() {
        return dongia;
    }

    public void setDongia(double dongia) {
        this.dongia = dongia;
    }

   

}


