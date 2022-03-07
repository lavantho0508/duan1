/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Helper.DateHelper;
import Helper.DialogHelper;
import Helper.UtilityHelper;
import IServices.IQLyNHAN_VIENService;
import Models.CHUC_VU;
import Models.NHAN_VIEN;
import Models.NV_HAS_CV;
import Sevices.NHAN_VIEN_Service;
import Sevices.QLNHAN_VIEN_Service;
import static java.awt.Color.pink;
import static java.awt.Color.white;
import java.text.Normalizer;
import java.util.List;
import java.util.regex.Pattern;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author LENOVO
 */
public class FormQuanLyNhanVien1 extends javax.swing.JDialog {

    DefaultTableModel model;
    IQLyNHAN_VIENService _IQLyNHAN_VIENService = new QLNHAN_VIEN_Service();
    int row = -1;
    int row1 = -1;

    /**
     * Creates new form NewJDialog
     */
    public FormQuanLyNhanVien1(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        setLocationRelativeTo(null);
        init();
    }

    void timKiemNhanVien() {

        for (int i = 0; i < model.getRowCount(); i++) {
            if (TxtTimKiemChucVu.getText().equals(tblChucVu.getValueAt(i, 0).toString().trim())) {
                Object[] row = {
                    tblChucVu.getValueAt(i, 0).toString(), tblChucVu.getValueAt(i, 1).toString()
                };
                model.addRow(row);
            }
        }
    }

    void init() {
        this.fillTableNhanVien();
        this.fillTableNhanVienOff();
        this.fillTableChucVu();
        this.fillCbbChucVu();
        rdoNam.setSelected(true);
        rdoDangDiLam.setSelected(true);
        rdoHoatDong.setSelected(true);
        txtNgaySinh.setDateFormatString("dd/MM/yyyy");
    }

    public void fillCbbChucVu() {
        DefaultComboBoxModel model = (DefaultComboBoxModel) cbb_ChucVu.getModel();
        model.removeAllElements();
        try {
            List<CHUC_VU> list = _IQLyNHAN_VIENService.selectAll_CHUC_VU();
            for (CHUC_VU models : list) {
                if (models.getTRANGTHAI() == 0) {
                    model.addElement(models);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            DialogHelper.alert(this, "Lỗi truy vấn dữ liệu");
        }
    }

    public void fillTableChucVu() {
        model = (DefaultTableModel) tblChucVu.getModel();
        model.setRowCount(0);
        try {
            List<CHUC_VU> list = _IQLyNHAN_VIENService.selectAll_CHUC_VU();
            for (CHUC_VU x : list) {
                Object[] row = {
                    x.getMACV(),
                    x.getTENCV(),
                    x.getTRANGTHAI() == 0 ? "Hoạt động" : "Không hoạt động"
                };
                model.addRow(row);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public CHUC_VU getFormChucVu() {
        CHUC_VU cv = new CHUC_VU();
        cv.setMACV(txtMaChucVu.getText());
        cv.setTENCV(txtTenChucVu.getText());
        cv.setTRANGTHAI(rdoHoatDong.isSelected() ? 0 : 1);
        return cv;
    }

    void setFormChucVu(CHUC_VU cv) {
        txtMaChucVu.setText(cv.getMACV());
        txtTenChucVu.setText(cv.getTENCV());
        if (cv.getTRANGTHAI() == 0) {
            rdoHoatDong.setSelected(true);
        } else {
            rdoKhongHoatDong.setSelected(true);
        }
    }

    public void insertChucVu() {
        CHUC_VU cv = getFormChucVu();
        try {
            _IQLyNHAN_VIENService.insert_CHUC_VU(cv);
            fillTableChucVu();
            DialogHelper.alert(this, "Thêm chức vụ thành công nè <3");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void updateChucVu() {
        CHUC_VU cv = getFormChucVu();
        try {
            _IQLyNHAN_VIENService.update_CHUC_VU(cv);
            fillTableChucVu();
            DialogHelper.alert(this, "Cập nhật chức vụ thành công nè <3");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteChucVu() {
        try {
            _IQLyNHAN_VIENService.delete_CHUC_VU(txtMaChucVu.getText());
            this.fillTableChucVu();
            DialogHelper.alert(this, "xóa thành công nè <3");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void clearChucVu() {
        txtMaChucVu.setText("");
        txtTenChucVu.setText("");
    }

    public void editChucVu() {
        try {
            String chucvu = String.valueOf(tblChucVu.getValueAt(row1, 0));
            CHUC_VU model = _IQLyNHAN_VIENService.findById_CHUC_VU(chucvu);
            if (model != null) {
                this.setFormChucVu(model);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void fillTableNhanVien() {
        model = (DefaultTableModel) tblNhanVien.getModel();
        model.setRowCount(0);
        try {
            List<NHAN_VIEN> list = _IQLyNHAN_VIENService.selectAll_Nhan_Vien();
            List<CHUC_VU> list2 = _IQLyNHAN_VIENService.selectAll_CHUC_VU();

            for (NHAN_VIEN x : list) {

//                
                NV_HAS_CV nv_has_cv = _IQLyNHAN_VIENService.findById_NV_HAS_CV(x.getMANV());
                CHUC_VU cv = _IQLyNHAN_VIENService.findById_CHUC_VU(nv_has_cv.getMACV());
                if (x.getTRANGTHAI() == 0) {

                    Object[] row = {
                        x.getMANV(), x.getHOTEN(), x.getGIOITINH() == 0 ? "Nam" : "Nữ",
                        cv.getTENCV(),
                        x.getSDT(), x.getEMAIL(), x.getMATKHAU(), x.getNGAYSINH(), x.getDIACHI(),
                        x.getTRANGTHAI() == 0 ? "Đang đi làm" : "Đã nghỉ làm", //                        cv.getTENCV()
                    };
                    model.addRow(row);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void fillTableNhanVienOff() {
        model = (DefaultTableModel) tbl_nvNghiLam.getModel();
        model.setRowCount(0);
        try {
            List<NHAN_VIEN> list = _IQLyNHAN_VIENService.selectAll_Nhan_Vien();
//            List<CHUC_VU> list2 = _IQLyNHAN_VIENService.selectAll_CHUC_VU();

            for (NHAN_VIEN x : list) {
//                
                NV_HAS_CV nv_has_cv = _IQLyNHAN_VIENService.findById_NV_HAS_CV(x.getMANV());
                CHUC_VU cv = _IQLyNHAN_VIENService.findById_CHUC_VU(nv_has_cv.getMACV());
                if (x.getTRANGTHAI() == 1) {
                    Object[] row = {
                        x.getMANV(), x.getHOTEN(),
                        x.getTRANGTHAI() == 0 ? "Đang đi làm" : "Đã nghỉ làm", //                        cv.getTENCV()
                    };
                    model.addRow(row);
                }

//                CHUC_VU cv = _cvService.findById_CHUC_VU(x.getMANV());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public NHAN_VIEN getFormNhanVien() {
        NHAN_VIEN nv = new NHAN_VIEN();
        nv.setMANV(txtManv.getText());
        nv.setHOTEN(chuanHoaDanhTuRieng(txtHoTen.getText()));
        nv.setGIOITINH(rdoNam.isSelected() ? 0 : 1);
        nv.setSDT(txtSdt.getText());
        nv.setEMAIL(txtEmail.getText());
        nv.setMATKHAU(txtMatKhau.getText());
        nv.setNGAYSINH(txtNgaySinh.getDate());
        nv.setDIACHI(txtDiaChi.getText());
        nv.setTRANGTHAI(rdoDangDiLam.isSelected() ? 0 : 1);
        return nv;
    }

    public void insertNhanVien() {
        NHAN_VIEN nv = getFormNhanVien();
        CHUC_VU chuc_vu = _IQLyNHAN_VIENService.findByNames_CHUC_VU(String.valueOf(cbb_ChucVu.getSelectedItem()));
        try {
            _IQLyNHAN_VIENService.insert_Nhan_vien(nv);
            _IQLyNHAN_VIENService.insert_NV_HAS_CV(new NV_HAS_CV(nv.getMANV(), chuc_vu.getMACV(), 0));
            fillTableNhanVien();
            this.fillTableNhanVienOff();
            DialogHelper.alert(this, "Thêm nhân viên thành công nè <3");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void updateNhanVien() {
        NHAN_VIEN nv = getFormNhanVien();
        CHUC_VU chuc_vu = _IQLyNHAN_VIENService.findByNames_CHUC_VU(String.valueOf(cbb_ChucVu.getSelectedItem()));
        try {
            _IQLyNHAN_VIENService.update_Nhan_Vien(nv);
            _IQLyNHAN_VIENService.update_NV_HAS_CV(new NV_HAS_CV(nv.getMANV(), chuc_vu.getMACV(), 0));
            this.fillTableNhanVien();
            this.fillTableNhanVienOff();
            DialogHelper.alert(this, "Cập nhật thành công nè <3");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void clearNhanVien() {
        txtDiaChi.setText("");
        txtEmail.setText("");
        txtHoTen.setText("");
        txtManv.setText("");
        txtMatKhau.setText("");
        txtNgaySinh.setDate(null);
        txtSdt.setText("");
    }

    public void deleteNhanVien() {
        try {
            _IQLyNHAN_VIENService.delete_NV_HAS_CV(txtManv.getText());
            _IQLyNHAN_VIENService.delete_Nhan_Vien(txtManv.getText());
            this.fillTableNhanVien();
            this.fillTableNhanVienOff();
            DialogHelper.alert(this, "xóa thành công nè <3");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void setFormNhanVien(NHAN_VIEN nv) {
        NV_HAS_CV nv_has_cv = _IQLyNHAN_VIENService.findById_NV_HAS_CV(nv.getMANV());
        CHUC_VU chuc_vu = _IQLyNHAN_VIENService.findById_CHUC_VU(nv_has_cv.getMACV());

        txtManv.setText(nv.getMANV());
        txtHoTen.setText(nv.getHOTEN());
        if (nv.getGIOITINH() == 0) {
            rdoNam.setSelected(true);
        } else {
            rdoNu.setSelected(true);
        }
        txtSdt.setText(nv.getSDT());
        txtEmail.setText(nv.getEMAIL());
        txtMatKhau.setText(nv.getMATKHAU());
        txtNgaySinh.setDate(nv.getNGAYSINH());
        txtDiaChi.setText(nv.getDIACHI());
        if (nv.getTRANGTHAI() == 0) {
            rdoDangDiLam.setSelected(true);
        } else {
            rdoDaNghiLam.setSelected(true);
        }
        for (int i = 0; i < cbb_ChucVu.getItemCount(); i++) {
//       
            if (chuc_vu.getTENCV().equalsIgnoreCase(String.valueOf(cbb_ChucVu.getItemAt(i)))) {
                cbb_ChucVu.setSelectedIndex(i);
            }
        }
    }

    public void editNhanVien() {
        try {
            String nhanvien = String.valueOf(tblNhanVien.getValueAt(row, 0));
            NHAN_VIEN model = _IQLyNHAN_VIENService.findById_NhanVien(nhanvien);
            if (model != null) {
                this.setFormNhanVien(model);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void editNhanVienOff() {
        try {
            String nhanvien = String.valueOf(tbl_nvNghiLam.getValueAt(row, 0));
            NHAN_VIEN model = _IQLyNHAN_VIENService.findById_NhanVien(nhanvien);
            if (model != null) {
                this.setFormNhanVien(model);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean checkTrungMa(JTextField txt) {
        txt.setBackground(white);
        if (_IQLyNHAN_VIENService.findById_NhanVien(txt.getText()) == null) {
            return true;
        } else {
            txt.setBackground(pink);
            DialogHelper.alert(this, txt.getName() + " đã bị tồn tại.");
            return false;
        }
    }

    public boolean checkTrungMaChucVu(JTextField txt) {
        txt.setBackground(white);
        if (_IQLyNHAN_VIENService.findById_CHUC_VU(txt.getText()) == null) {
            return true;
        } else {
            txt.setBackground(pink);
            DialogHelper.alert(this, txt.getName() + " đã bị tồn tại.");
            return false;
        }
    }

    public static String autoMaNV(String s) {
        s.trim();
        s = s.toLowerCase();
        String temp1[] = s.split("\\s+");
        s = temp1[temp1.length - 1];
//        System.out.println(temp.length);
//        System.out.println(chuoi);
//        chuoi = ""; // ? ^-^
        for (int i = 0; i < temp1.length - 1; i++) {
            s += String.valueOf(temp1[i].charAt(0));
//            chuoi +=temp[i].charAt(0);
            if (i < temp1.length - 1) {
                s += "";
            }
        }
        String temp = Normalizer.normalize(s, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        temp = pattern.matcher(temp).replaceAll("");
        return temp.replaceAll("đ", "d");
    }

    public String chuanHoaDanhTuRieng(String chuoi) {
        chuoi.trim();
        chuoi = chuoi.toLowerCase();
        String temp[] = chuoi.split("\\s+");
        chuoi = ""; // ? ^-^
        for (int i = 0; i < temp.length; i++) {
            chuoi += String.valueOf(temp[i].charAt(0)).toUpperCase() + temp[i].substring(1);
            if (i < temp.length - 1) {
                chuoi += " ";
            }
        }
        return chuoi;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        buttonGroup3 = new javax.swing.ButtonGroup();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtManv = new javax.swing.JTextField();
        txtHoTen = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        rdoNam = new javax.swing.JRadioButton();
        rdoNu = new javax.swing.JRadioButton();
        jLabel5 = new javax.swing.JLabel();
        txtSdt = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtEmail = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtMatKhau = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtDiaChi = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        rdoDangDiLam = new javax.swing.JRadioButton();
        rdoDaNghiLam = new javax.swing.JRadioButton();
        cbb_ChucVu = new javax.swing.JComboBox<>();
        jButton10 = new javax.swing.JButton();
        jLabel15 = new javax.swing.JLabel();
        txtNgaySinh = new com.toedter.calendar.JDateChooser();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jDesktopPane1 = new javax.swing.JDesktopPane();
        txtTimKiem = new javax.swing.JTextField();
        jToggleButton1 = new javax.swing.JToggleButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblNhanVien = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tbl_nvNghiLam = new javax.swing.JTable();
        jLabel16 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        txtTenChucVu = new javax.swing.JTextField();
        txtMaChucVu = new javax.swing.JTextField();
        rdoHoatDong = new javax.swing.JRadioButton();
        rdoKhongHoatDong = new javax.swing.JRadioButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblChucVu = new javax.swing.JTable();
        jLabel14 = new javax.swing.JLabel();
        TxtTimKiemChucVu = new javax.swing.JTextField();
        jButton9 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jTabbedPane1.setBackground(new java.awt.Color(255, 102, 102));
        jTabbedPane1.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N

        jPanel1.setBackground(new java.awt.Color(255, 102, 102));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setText("Quản lý nhân viên");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel2.setText("Mã nhân viên:");

        txtManv.setEditable(false);
        txtManv.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        txtManv.setToolTipText("");
        txtManv.setName("Mã nhân viên"); // NOI18N
        txtManv.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtManvFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtManvFocusLost(evt);
            }
        });

        txtHoTen.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        txtHoTen.setName("Họ tên"); // NOI18N
        txtHoTen.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtHoTenFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtHoTenFocusLost(evt);
            }
        });
        txtHoTen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtHoTenActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel3.setText("Họ tên:");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel4.setText("Giới tính:");

        rdoNam.setBackground(new java.awt.Color(255, 102, 102));
        buttonGroup1.add(rdoNam);
        rdoNam.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        rdoNam.setText("Nam");

        rdoNu.setBackground(new java.awt.Color(255, 102, 102));
        buttonGroup1.add(rdoNu);
        rdoNu.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        rdoNu.setText("Nữ");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel5.setText("Số điện thoại:");

        txtSdt.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        txtSdt.setName("Số điện thoại"); // NOI18N

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel6.setText("Email:");

        txtEmail.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        txtEmail.setName("email"); // NOI18N

        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel7.setText("Mật khẩu:");

        txtMatKhau.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        txtMatKhau.setName("mật khẩu"); // NOI18N

        jLabel8.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel8.setText("Ngày sinh:");

        jLabel9.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel9.setText("Địa chỉ:");

        txtDiaChi.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        txtDiaChi.setName("địa chỉ"); // NOI18N

        jLabel10.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel10.setText("Trạng thái:");

        jButton2.setBackground(new java.awt.Color(102, 255, 102));
        jButton2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/baseline_save_black_24dp.png"))); // NOI18N
        jButton2.setText("Lưu");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setBackground(new java.awt.Color(51, 51, 255));
        jButton3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/baseline_sync_alt_black_24dp.png"))); // NOI18N
        jButton3.setText("Cập nhật");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setBackground(new java.awt.Color(255, 51, 51));
        jButton4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/baseline_delete_forever_black_24dp.png"))); // NOI18N
        jButton4.setText("Xóa");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/baseline_add_black_24dp.png"))); // NOI18N
        jButton1.setText("Tạo mới");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        rdoDangDiLam.setBackground(new java.awt.Color(255, 102, 102));
        buttonGroup2.add(rdoDangDiLam);
        rdoDangDiLam.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        rdoDangDiLam.setText("Đang đi làm");

        rdoDaNghiLam.setBackground(new java.awt.Color(255, 102, 102));
        buttonGroup2.add(rdoDaNghiLam);
        rdoDaNghiLam.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        rdoDaNghiLam.setText("Đã nghỉ làm");

        cbb_ChucVu.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        cbb_ChucVu.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jButton10.setText("+");

        jLabel15.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel15.setText("Chức vụ");

        txtTimKiem.setForeground(new java.awt.Color(153, 153, 153));
        txtTimKiem.setText("tìm kiếm");
        txtTimKiem.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtTimKiemFocusGained(evt);
            }
        });

        jToggleButton1.setText("Tìm kiếm");
        jToggleButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton1ActionPerformed(evt);
            }
        });

        tblNhanVien.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        tblNhanVien.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "MÃ NV", "Họ tên", "Giới tính", "Chức vụ", "Số điện thoại", "Email ", "Mật khẩu", "Ngày sinh", "Địa chỉ", "Trạng thái"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblNhanVien.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblNhanVienMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblNhanVien);

        jDesktopPane1.setLayer(txtTimKiem, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane1.setLayer(jToggleButton1, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane1.setLayer(jScrollPane1, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout jDesktopPane1Layout = new javax.swing.GroupLayout(jDesktopPane1);
        jDesktopPane1.setLayout(jDesktopPane1Layout);
        jDesktopPane1Layout.setHorizontalGroup(
            jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDesktopPane1Layout.createSequentialGroup()
                .addGap(56, 56, 56)
                .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 258, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jToggleButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jDesktopPane1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 846, Short.MAX_VALUE)
                .addContainerGap())
        );
        jDesktopPane1Layout.setVerticalGroup(
            jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDesktopPane1Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jToggleButton1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 350, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane2.addTab("tab1", jDesktopPane1);

        jPanel3.setBackground(new java.awt.Color(171, 171, 171));

        tbl_nvNghiLam.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        tbl_nvNghiLam.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Mã nhân viên", "Tên nhân viên", "Trạng thái"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbl_nvNghiLam.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_nvNghiLamMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tbl_nvNghiLam);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 827, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(29, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 372, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(55, 55, 55))
        );

        jTabbedPane2.addTab("tab2", jPanel3);

        jLabel16.setFont(new java.awt.Font("Segoe UI", 2, 12)); // NOI18N
        jLabel16.setText("Bạn nhập Họ tên Nhân Viên, Mã NV tự động set !");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jButton2)
                        .addGap(18, 18, 18)
                        .addComponent(jButton3)
                        .addGap(18, 18, 18)
                        .addComponent(jButton4)
                        .addGap(18, 18, 18)
                        .addComponent(jButton1))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txtHoTen, javax.swing.GroupLayout.PREFERRED_SIZE, 259, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txtMatKhau, javax.swing.GroupLayout.PREFERRED_SIZE, 259, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txtNgaySinh, javax.swing.GroupLayout.PREFERRED_SIZE, 257, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel9)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txtDiaChi, javax.swing.GroupLayout.PREFERRED_SIZE, 259, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 259, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addGap(18, 18, 18)
                                .addComponent(txtManv))
                            .addComponent(jLabel16, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 288, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(rdoNam)
                                        .addGap(18, 18, 18)
                                        .addComponent(rdoNu))
                                    .addComponent(txtSdt, javax.swing.GroupLayout.PREFERRED_SIZE, 259, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addGap(3, 3, 3)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel10)
                                    .addComponent(jLabel15))
                                .addGap(29, 29, 29)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(cbb_ChucVu, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(rdoDangDiLam)
                                        .addGap(18, 18, 18)
                                        .addComponent(rdoDaNghiLam)))
                                .addGap(18, 18, 18)
                                .addComponent(jButton10)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTabbedPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 871, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(67, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel16)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(txtManv, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(26, 26, 26)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(txtHoTen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(rdoNam)
                            .addComponent(rdoNu))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtSdt, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(txtMatKhau, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtNgaySinh, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(txtDiaChi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(27, 27, 27)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cbb_ChucVu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton10)
                            .addComponent(jLabel15))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10)
                            .addComponent(rdoDangDiLam)
                            .addComponent(rdoDaNghiLam)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addComponent(jTabbedPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
                .addGap(27, 27, 27)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton2)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButton3)
                        .addComponent(jButton4)
                        .addComponent(jButton1)))
                .addGap(30, 30, 30))
        );

        jTabbedPane1.addTab("Quản lý nhân viên", jPanel1);

        jPanel2.setBackground(new java.awt.Color(255, 102, 102));

        jLabel11.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel11.setText("Mã chức vụ");

        jLabel12.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel12.setText("Tên chức vụ:");

        jLabel13.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel13.setText("Trạng thái:");

        txtTenChucVu.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        txtTenChucVu.setName("tên chức vụ"); // NOI18N

        txtMaChucVu.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        txtMaChucVu.setName("mã chức vụ"); // NOI18N

        rdoHoatDong.setBackground(new java.awt.Color(255, 102, 102));
        buttonGroup3.add(rdoHoatDong);
        rdoHoatDong.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        rdoHoatDong.setText("Hoạt động");

        rdoKhongHoatDong.setBackground(new java.awt.Color(255, 102, 102));
        buttonGroup3.add(rdoKhongHoatDong);
        rdoKhongHoatDong.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        rdoKhongHoatDong.setText("Không hoạt động");

        jButton5.setBackground(new java.awt.Color(102, 255, 102));
        jButton5.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/baseline_save_black_24dp.png"))); // NOI18N
        jButton5.setText("Lưu");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton6.setBackground(new java.awt.Color(51, 51, 255));
        jButton6.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/baseline_sync_alt_black_24dp.png"))); // NOI18N
        jButton6.setText("Cập nhật");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jButton7.setBackground(new java.awt.Color(255, 51, 51));
        jButton7.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/baseline_delete_forever_black_24dp.png"))); // NOI18N
        jButton7.setText("Xóa");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        jButton8.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/baseline_add_black_24dp.png"))); // NOI18N
        jButton8.setText("Tạo mới");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        tblChucVu.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        tblChucVu.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã chức vụ", "Tên chức vụ", "Trạng thái"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblChucVu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblChucVuMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblChucVu);

        jLabel14.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel14.setText("Quản lý nhân viên");

        TxtTimKiemChucVu.setForeground(new java.awt.Color(153, 153, 153));
        TxtTimKiemChucVu.setText("Tìm kiếm");
        TxtTimKiemChucVu.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                TxtTimKiemChucVuFocusGained(evt);
            }
        });

        jButton9.setText("Tìm kiếm");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(38, 38, 38)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel14)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel13)
                                .addGap(18, 18, 18)
                                .addComponent(rdoHoatDong, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(rdoKhongHoatDong))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel12)
                                    .addComponent(jLabel11))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtMaChucVu)
                                    .addComponent(txtTenChucVu, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(79, 79, 79)
                        .addComponent(jButton5)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton8)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jButton6)
                                .addGap(18, 18, 18)
                                .addComponent(jButton7)))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 107, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 596, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(TxtTimKiemChucVu, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton9)
                        .addGap(54, 54, 54)))
                .addGap(188, 188, 188))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel11)
                            .addComponent(txtMaChucVu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel12)
                            .addComponent(txtTenChucVu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel13)
                            .addComponent(rdoHoatDong)
                            .addComponent(rdoKhongHoatDong))
                        .addGap(30, 30, 30)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton5)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jButton6)
                                .addComponent(jButton7)))
                        .addGap(18, 18, 18)
                        .addComponent(jButton8))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(41, 41, 41)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(TxtTimKiemChucVu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton9))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(63, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Quản lý chức vụ", jPanel2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1309, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        if (UtilityHelper.checkNullText(txtManv) && UtilityHelper.checkIdNhanVien(txtManv) && checkTrungMa(txtManv)
                && UtilityHelper.checkNullText(txtHoTen)
                && UtilityHelper.checkTenNhanVien(txtHoTen)
                && UtilityHelper.checkNullText(txtSdt) && UtilityHelper.checkSDT(txtSdt) && UtilityHelper.checkNullText(txtEmail)
                && UtilityHelper.checkEmail(txtEmail)
                && UtilityHelper.checkNullText(txtMatKhau) && UtilityHelper.checkNullText(txtMatKhau)
                //                && UtilityHelper.checkNullText(jPanel1) && UtilityHelper.checkDate(jPanel1)
                && UtilityHelper.checkNullText(txtDiaChi)) {
            insertNhanVien();
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        if (UtilityHelper.checkNullText(txtManv)
                && UtilityHelper.checkNullText(txtHoTen)
                && UtilityHelper.checkTenNhanVien(txtHoTen)
                && UtilityHelper.checkNullText(txtSdt) && UtilityHelper.checkSDT(txtSdt) && UtilityHelper.checkNullText(txtEmail)
                && UtilityHelper.checkEmail(txtEmail)
                && UtilityHelper.checkNullText(txtMatKhau) && UtilityHelper.checkNullText(txtMatKhau)
                //                && UtilityHelper.checkNullText(jPanel1) && UtilityHelper.checkDate(jPanel1)
                && UtilityHelper.checkNullText(txtDiaChi)) {
            if (DialogHelper.confirm(this, "Bạn có thật sự muốn sửa không hả???")) {
                updateNhanVien();
            }
        }

    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed

        if (DialogHelper.confirm(this, "bạn muốn xóa thật sự không hả???")) {
            deleteNhanVien();
        }

    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        clearNhanVien();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        if (UtilityHelper.checkNullText(txtMaChucVu) && UtilityHelper.checkIdChucVu(txtMaChucVu)
                && checkTrungMaChucVu(txtMaChucVu)
                && UtilityHelper.checkNullText(txtTenChucVu) && UtilityHelper.checkTenChucVu(txtTenChucVu)) {
            this.insertChucVu();
        }
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        if (UtilityHelper.checkNullText(txtMaChucVu) && UtilityHelper.checkIdChucVu(txtMaChucVu)
                && UtilityHelper.checkNullText(txtTenChucVu) && UtilityHelper.checkTenChucVu(txtTenChucVu)) {
            if (DialogHelper.confirm(this, "Bạn có thật sự muốn sửa không hả???")) {
                updateChucVu();
            }
        }    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        if (UtilityHelper.checkNullText(txtMaChucVu) && UtilityHelper.checkIdChucVu(txtMaChucVu)
                && UtilityHelper.checkNullText(txtTenChucVu) && UtilityHelper.checkTenChucVu(txtTenChucVu)) {
            if (DialogHelper.confirm(this, "bạn muốn xóa thật sự không hả???")) {
                deleteChucVu();
            }
        }    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        clearChucVu();
    }//GEN-LAST:event_jButton8ActionPerformed

    private void TxtTimKiemChucVuFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_TxtTimKiemChucVuFocusGained
        TxtTimKiemChucVu.setText("");

    }//GEN-LAST:event_TxtTimKiemChucVuFocusGained

    private void tblChucVuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblChucVuMouseClicked
        int row1 = tblChucVu.getRowCount();
        if (evt.getClickCount() == 2) {
            this.row1 = tblChucVu.getSelectedRow();
            if (this.row1 >= 0) {
                this.editChucVu();
            }
        }
    }//GEN-LAST:event_tblChucVuMouseClicked

    private void tblNhanVienMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblNhanVienMouseClicked
        int row = tblNhanVien.getRowCount();
        if (evt.getClickCount() == 2) {
            this.row = tblNhanVien.getSelectedRow();
            if (this.row >= 0) {
                this.editNhanVien();
            }
        }
    }//GEN-LAST:event_tblNhanVienMouseClicked

    private void jToggleButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton1ActionPerformed

    }//GEN-LAST:event_jToggleButton1ActionPerformed

    private void txtTimKiemFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtTimKiemFocusGained
        txtTimKiem.setText("");
    }//GEN-LAST:event_txtTimKiemFocusGained

    private void tbl_nvNghiLamMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_nvNghiLamMouseClicked
        int row = tbl_nvNghiLam.getRowCount();
        if (evt.getClickCount() == 2) {
            this.row = tbl_nvNghiLam.getSelectedRow();
            if (this.row >= 0) {
                this.editNhanVienOff();
            }
        }
    }//GEN-LAST:event_tbl_nvNghiLamMouseClicked

    private void txtHoTenFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtHoTenFocusLost
        txtManv.setText(autoMaNV(txtHoTen.getText()));
        if (!txtManv.getText().isEmpty()) {
            txtEmail.setText(txtManv.getText() + "@fpt.edu.vn");
        }

    }//GEN-LAST:event_txtHoTenFocusLost

    private void txtHoTenFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtHoTenFocusGained
        txtManv.setText(autoMaNV(txtHoTen.getText()));
        if (!txtManv.getText().isEmpty()) {
            txtEmail.setText(txtManv.getText() + "@fpt.edu.vn");
        }
    }//GEN-LAST:event_txtHoTenFocusGained

    private void txtHoTenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtHoTenActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtHoTenActionPerformed

    private void txtManvFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtManvFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_txtManvFocusGained

    private void txtManvFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtManvFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txtManvFocusLost

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FormQuanLyNhanVien1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FormQuanLyNhanVien1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FormQuanLyNhanVien1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FormQuanLyNhanVien1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                FormQuanLyNhanVien1 dialog = new FormQuanLyNhanVien1(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField TxtTimKiemChucVu;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.ButtonGroup buttonGroup3;
    private javax.swing.JComboBox<String> cbb_ChucVu;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JDesktopPane jDesktopPane1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JToggleButton jToggleButton1;
    private javax.swing.JRadioButton rdoDaNghiLam;
    private javax.swing.JRadioButton rdoDangDiLam;
    private javax.swing.JRadioButton rdoHoatDong;
    private javax.swing.JRadioButton rdoKhongHoatDong;
    private javax.swing.JRadioButton rdoNam;
    private javax.swing.JRadioButton rdoNu;
    private javax.swing.JTable tblChucVu;
    private javax.swing.JTable tblNhanVien;
    private javax.swing.JTable tbl_nvNghiLam;
    private javax.swing.JTextField txtDiaChi;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtHoTen;
    private javax.swing.JTextField txtMaChucVu;
    private javax.swing.JTextField txtManv;
    private javax.swing.JTextField txtMatKhau;
    private com.toedter.calendar.JDateChooser txtNgaySinh;
    private javax.swing.JTextField txtSdt;
    private javax.swing.JTextField txtTenChucVu;
    private javax.swing.JTextField txtTimKiem;
    // End of variables declaration//GEN-END:variables
}
