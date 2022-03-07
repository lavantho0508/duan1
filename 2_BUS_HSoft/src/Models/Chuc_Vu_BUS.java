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
public class Chuc_Vu_BUS extends CHUC_VU{

    public Chuc_Vu_BUS() {
    }

    public Chuc_Vu_BUS(String MACV, String TENCV, int TRANGTHAI) {
        super(MACV, TENCV, TRANGTHAI);
    }

    @Override
    public void setTRANGTHAI(int TRANGTHAI) {
        super.setTRANGTHAI(TRANGTHAI); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int getTRANGTHAI() {
        return super.getTRANGTHAI(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setTENCV(String TENCV) {
        super.setTENCV(TENCV); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getTENCV() {
        return super.getTENCV(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setMACV(String MACV) {
        super.setMACV(MACV); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getMACV() {
        return super.getMACV(); //To change body of generated methods, choose Tools | Templates.
    }
    
    
}
