/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Helper.DialogHelper;
import Helper.UtilityHelper;
import IServices.IQLyKhachHang_Service;
import Models.KHACH_HANG;
import Sevices.QLyKhachHang_Service;
import java.util.List;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author DEll
 */
public class QuanLyKhachHang_Dialog extends javax.swing.JDialog {

    /**
     * Creates new form QuanLyKhachHang_Dialog
     */
    DefaultTableModel model = new DefaultTableModel();
    IQLyKhachHang_Service _khachHang_Service = (IQLyKhachHang_Service) new QLyKhachHang_Service();
    int _rowkh = -1;

    public QuanLyKhachHang_Dialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        init();
        setID();
    }

    public void setID() {
        List<KHACH_HANG> lst = _khachHang_Service.selectAll_Khach_Hang();
        if (lst.size() == 0) {
            txt_ID_KhachHang.setText("KH" + 1);
        } else {
            String matam = lst.get(lst.size() - 1).getMAKH();
            int c = Integer.parseInt(matam.substring(2));
            c++;
            txt_ID_KhachHang.setText("KH" + c);
        }
    }

    public void init() {
        setLocationRelativeTo(null);
        fillTableKhachHang();
    }

    public void fillTableKhachHang() {
        model = (DefaultTableModel) Tbl_KhachHang.getModel();
        model.setRowCount(0);
        try {
            List<KHACH_HANG> lst = _khachHang_Service.selectAll_Khach_Hang();
            for (KHACH_HANG kh : lst) {
                Object[] row = {
                    kh.getMAKH(),
                    kh.getTENKH(),
                    kh.getSDT(),
                    kh.getDIACHI(),
                    kh.getTRANGTHAI() == 0 ? "Hoạt động" : "Không hoạt động"
                };
                model.addRow(row);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean checkIDProduct(String ID) {
        if (_khachHang_Service.findById_Khach_Hang(ID) == null) {
            return true;
        } else {
            Helper.DialogHelper.alert(this, "Mã " + ID + " đã tồn tại !");
            return false;
        }
    }

    public void insertKhach_Hang() {
        KHACH_HANG kh = getform();
        try {
            _khachHang_Service.insert_Khach_Hang(kh);
            Helper.DialogHelper.alert(this, "Thêm mới thành công");
            fillTableKhachHang();

        } catch (Exception e) {
            e.printStackTrace();
            Helper.DialogHelper.alert(this, "Thêm mới thất bại");
        }
    }

    public KHACH_HANG getform() {
        KHACH_HANG kh = new KHACH_HANG();
        kh.setMAKH(txt_ID_KhachHang.getText());
        kh.setTENKH(txt_Ten_KhachHang.getText());
        kh.setSDT(SDT_KhachHang.getText());
        kh.setDIACHI(txtDiaChi.getText());
        if (rdo_0.isSelected()) {
            kh.setTRANGTHAI(0);
        } else {
            kh.setTRANGTHAI(1);
        }
        return kh;
    }

    public void setFormKH() {
        try {
            String ID = String.valueOf(Tbl_KhachHang.getValueAt(this._rowkh, 0));
            KHACH_HANG model = _khachHang_Service.findById_Khach_Hang(ID);
            if (model != null) {
                txt_ID_KhachHang.setText(model.getMAKH());
                txt_Ten_KhachHang.setText(model.getTENKH());
                SDT_KhachHang.setText(model.getSDT());
                txtDiaChi.setText(model.getDIACHI());
                if (model.getTRANGTHAI() == 0) {
                    rdo_0.setSelected(true);
                } else {
                    rdo_0.setSelected(false);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            DialogHelper.alert(this, "Lỗi truy vấn dữ liệu!");
        }
    }

    public void updateKhachHang() {
        KHACH_HANG kh = getform();
        try {
            _khachHang_Service.update_Khach_Hang(kh);
            DialogHelper.alert(this, "Sửa thành công");
            fillTableKhachHang();
            _rowkh = -1;
        } catch (Exception e) {
            e.printStackTrace();
            DialogHelper.alert(this, "Sửa không thành công");
        }
    }

    public void deleteKh() {
        if (DialogHelper.confirm(this, "Bạn chắc chắn xóa Khách hàng này")) {
            try {
                _khachHang_Service.delete_Khach_Hang(txt_ID_KhachHang.getText());
                DialogHelper.alert(this, "Xóa thành công");
                fillTableKhachHang();
                clearForm();
                _rowkh = -1;
            } catch (Exception e) {
                e.printStackTrace();
                DialogHelper.alert(this, "Xóa không thành công");
            }
        }
    }

    public void clearForm() {
        KHACH_HANG kh = new KHACH_HANG();
        txt_ID_KhachHang.setText(kh.getMAKH());
        txt_Ten_KhachHang.setText(kh.getTENKH());
        SDT_KhachHang.setText(kh.getSDT());
        txtDiaChi.setText(kh.getDIACHI());
        if (kh.getTRANGTHAI() == 0) {
            rdo_0.setSelected(true);
        } else {
            rdo_0.setSelected(false);
        }
        setID();
        _rowkh = -1;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txt_ID_KhachHang = new javax.swing.JTextField();
        txt_Ten_KhachHang = new javax.swing.JTextField();
        SDT_KhachHang = new javax.swing.JTextField();
        rdo_0 = new javax.swing.JRadioButton();
        rdo_1 = new javax.swing.JRadioButton();
        btn_insertKHang = new javax.swing.JButton();
        btn_updateKHang = new javax.swing.JButton();
        btn_deleteKHang = new javax.swing.JButton();
        btn_newKHang = new javax.swing.JButton();
        txtDiaChi = new javax.swing.JTextArea();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        Tbl_KhachHang = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 102, 102));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setText("QUẢN LÝ KHÁCH HÀNG");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 300, -1));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel3.setText("Thêm khách hàng");

        jLabel2.setText("Mã Khách hàng:");

        jLabel4.setText("Tên khách hàng:");

        jLabel5.setText("SDT:");

        jLabel6.setText("Địa Chỉ:");

        jLabel7.setText("Trạng thái:");

        txt_ID_KhachHang.setName("Mã khách hàng"); // NOI18N

        txt_Ten_KhachHang.setName("Tên khách hàng"); // NOI18N

        rdo_0.setSelected(true);
        rdo_0.setText("Hoạt động");

        rdo_1.setText("Không hoạt động");

        btn_insertKHang.setBackground(new java.awt.Color(11, 181, 217));
        btn_insertKHang.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btn_insertKHang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/baseline_add_black_24dp.png"))); // NOI18N
        btn_insertKHang.setText("Thêm");
        btn_insertKHang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_insertKHangActionPerformed(evt);
            }
        });

        btn_updateKHang.setBackground(new java.awt.Color(11, 181, 217));
        btn_updateKHang.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btn_updateKHang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/baseline_sync_alt_black_24dp.png"))); // NOI18N
        btn_updateKHang.setText("Sửa");
        btn_updateKHang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_updateKHangActionPerformed(evt);
            }
        });

        btn_deleteKHang.setBackground(new java.awt.Color(11, 181, 217));
        btn_deleteKHang.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btn_deleteKHang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/baseline_delete_forever_black_24dp.png"))); // NOI18N
        btn_deleteKHang.setText("Xóa");
        btn_deleteKHang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_deleteKHangActionPerformed(evt);
            }
        });

        btn_newKHang.setBackground(new java.awt.Color(11, 181, 217));
        btn_newKHang.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btn_newKHang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/baseline_open_in_new_black_24dp.png"))); // NOI18N
        btn_newKHang.setText("Làm mới");
        btn_newKHang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_newKHangActionPerformed(evt);
            }
        });

        txtDiaChi.setColumns(20);
        txtDiaChi.setRows(5);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txt_ID_KhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel6)
                                    .addComponent(jLabel7))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(rdo_0, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(rdo_1))
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(btn_insertKHang, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(btn_deleteKHang, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(btn_updateKHang, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(btn_newKHang, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(txt_Ten_KhachHang, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 237, Short.MAX_VALUE)
                                        .addComponent(SDT_KhachHang, javax.swing.GroupLayout.Alignment.LEADING))
                                    .addComponent(txtDiaChi, javax.swing.GroupLayout.PREFERRED_SIZE, 235, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                .addContainerGap(27, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txt_ID_KhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txt_Ten_KhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(SDT_KhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(jLabel6)
                        .addGap(18, 92, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(txtDiaChi)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(rdo_1)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(rdo_0)
                        .addComponent(jLabel7)))
                .addGap(9, 9, 9)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_updateKHang)
                    .addComponent(btn_insertKHang))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_newKHang)
                    .addComponent(btn_deleteKHang))
                .addContainerGap())
        );

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, 370, 360));

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Danh sách khách hàng"));
        jPanel3.setLayout(new java.awt.GridLayout(1, 0));

        Tbl_KhachHang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Ma KH", "Ten KH", "SDT", "Dia Chi", "Trang Thai"
            }
        ));
        Tbl_KhachHang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Tbl_KhachHangMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(Tbl_KhachHang);

        jPanel3.add(jScrollPane1);

        jPanel1.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(392, 70, 570, 360));

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Danh sách hóa đơn"));
        jPanel4.setLayout(new java.awt.GridLayout(1, 0));

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(jTable2);

        jPanel4.add(jScrollPane2);

        jPanel1.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 440, 950, 220));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 969, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 668, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_insertKHangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_insertKHangActionPerformed
        if (UtilityHelper.checkNullText(txt_ID_KhachHang) && UtilityHelper.checkNullText(txt_Ten_KhachHang)) {
            if (UtilityHelper.checkIdHang(txt_ID_KhachHang) && UtilityHelper.checkTenHang(txt_Ten_KhachHang)) {
                if (checkIDProduct(txt_ID_KhachHang.getText()) && UtilityHelper.checkSDT(SDT_KhachHang)) {
                    insertKhach_Hang();
                }
            }
        }
    }//GEN-LAST:event_btn_insertKHangActionPerformed

    private void btn_updateKHangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_updateKHangActionPerformed

        if (_rowkh < 0) {
            Helper.DialogHelper.alert(this, "Mời bạn chọn Hãng cần sửa");
            return;
        }
        if (UtilityHelper.checkNullText(txt_ID_KhachHang) && UtilityHelper.checkNullText(txt_Ten_KhachHang)) {
            if (UtilityHelper.checkIdHang(txt_ID_KhachHang) && UtilityHelper.checkTenHang(txt_Ten_KhachHang)) {
                if (!txt_ID_KhachHang.getText().equalsIgnoreCase(String.valueOf(Tbl_KhachHang.getValueAt(_rowkh, 0)))) {
                    Helper.DialogHelper.alert(this, "Bạn không được thay đổi Mã Hãng");
                    return;
                } else {
                    updateKhachHang();
                }
            }
        }
    }//GEN-LAST:event_btn_updateKHangActionPerformed

    private void btn_deleteKHangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_deleteKHangActionPerformed

        if (_rowkh < 0) {
            DialogHelper.alert(this, "Mời bạn chọn Hãng cần xóa");
            return;
        }
        deleteKh();
    }//GEN-LAST:event_btn_deleteKHangActionPerformed

    private void btn_newKHangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_newKHangActionPerformed
        if (Helper.DialogHelper.confirm(this, "Bạn thực sự muốn làm mới?")) {
            clearForm();
        }
    }//GEN-LAST:event_btn_newKHangActionPerformed

    private void Tbl_KhachHangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Tbl_KhachHangMouseClicked
        // TODO add your handling code here:
        this._rowkh = Tbl_KhachHang.getSelectedRow();
        setFormKH();
    }//GEN-LAST:event_Tbl_KhachHangMouseClicked

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
            java.util.logging.Logger.getLogger(QuanLyKhachHang_Dialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(QuanLyKhachHang_Dialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(QuanLyKhachHang_Dialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(QuanLyKhachHang_Dialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                QuanLyKhachHang_Dialog dialog = new QuanLyKhachHang_Dialog(new javax.swing.JFrame(), true);
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
    private javax.swing.JTextField SDT_KhachHang;
    private javax.swing.JTable Tbl_KhachHang;
    private javax.swing.JButton btn_deleteKHang;
    private javax.swing.JButton btn_insertKHang;
    private javax.swing.JButton btn_newKHang;
    private javax.swing.JButton btn_updateKHang;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable2;
    private javax.swing.JRadioButton rdo_0;
    private javax.swing.JRadioButton rdo_1;
    private javax.swing.JTextArea txtDiaChi;
    private javax.swing.JTextField txt_ID_KhachHang;
    private javax.swing.JTextField txt_Ten_KhachHang;
    // End of variables declaration//GEN-END:variables
}
