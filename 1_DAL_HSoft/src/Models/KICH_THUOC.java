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
public class KICH_THUOC {
     private String MAKICHTHUOC, KICHTHUOC;
    private int TRANGTHAI;

    public KICH_THUOC() {
    }

    public KICH_THUOC(String MAKICHTHUOC, String KICHTHUOC, int TRANGTHAI) {
        this.MAKICHTHUOC = MAKICHTHUOC;
        this.KICHTHUOC = KICHTHUOC;
        this.TRANGTHAI = TRANGTHAI;
    }

    public String getMAKICHTHUOC() {
        return MAKICHTHUOC;
    }

    public void setMAKICHTHUOC(String MAKICHTHUOC) {
        this.MAKICHTHUOC = MAKICHTHUOC;
    }

    public String getKICHTHUOC() {
        return KICHTHUOC;
    }

    public void setKICHTHUOC(String KICHTHUOC) {
        this.KICHTHUOC = KICHTHUOC;
    }

    public int getTRANGTHAI() {
        return TRANGTHAI;
    }

    public void setTRANGTHAI(int TRANGTHAI) {
        this.TRANGTHAI = TRANGTHAI;
    }

    @Override
    public String toString() {
        return KICHTHUOC;
    }
    
    
    
}
