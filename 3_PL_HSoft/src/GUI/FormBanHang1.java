/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Helper.DialogHelper;
import IServices.IQLyBanHang_Service;
import IServices.IQLySanPhamService;
import Models.CHI_TIET_HOA_DON;
import Models.CHI_TIET_SAN_PHAM;
import Models.GIOI_TINH;
import Models.GIO_HANG;
import Models.HANG;
import Models.HOA_DON;
import Models.KHACH_HANG;
import Models.KICH_THUOC;
import Models.LOAI_KINH;
import Models.MAU_SAC;
import Models.NHAN_VIEN;
import Models.SAN_PHAM;
import Models.SAN_PHAM_CT;
import Sevices.ChiTietHoaDon_Service;
import Sevices.QLyBanHang_Service;
import Sevices.QLySanPham_Service;
import Sevices.SanPham_Service;
import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import com.github.sarxos.webcam.WebcamResolution;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
//import com.itextpdf.text.BaseColor;
//import com.itextpdf.text.Chunk;
//import com.itextpdf.text.Document;
//import com.itextpdf.text.DocumentException;
//import com.itextpdf.text.Font;
//import com.itextpdf.text.FontFactory;
//import com.itextpdf.text.Paragraph;
//import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.sql.ResultSet;
import java.util.Vector;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import javax.swing.DefaultCellEditor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;

/**
 *
 * @author LENOVO
 */
public class FormBanHang1 extends javax.swing.JDialog implements Runnable, ThreadFactory {

    IQLyBanHang_Service _IQLyBanHang_Service = (IQLyBanHang_Service) new QLyBanHang_Service();
    IQLySanPhamService ql = (IQLySanPhamService) new QLySanPham_Service();
    int _rowSP = -1, index, soluong;
    double tongtien;
    List<SAN_PHAM_CT> list = new ArrayList<>();
    DefaultTableModel model;
    JComboBox _jcbTrangThai = new JComboBox();
    JComboBox _jcbTrangThaiSP = new JComboBox();
     List<GIO_HANG> giohang;

    private WebcamPanel panel = null;
    private Webcam webcam = null;

    private static final long serialVersionUID = 6441489157408381878L;
    private Executor executor = Executors.newSingleThreadExecutor((ThreadFactory) this);

    /**
     * Creates new form BanHang
     */
    public FormBanHang1(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
      
        initComponents();
          
        setLocationRelativeTo(null);
        model = (DefaultTableModel) tbl_CTSP.getModel();
        list = ql.selectAll();
        init();

        initWebcam();
    }

//    private void loadTBGioHANG() {
//        DefaultTableModel model = (DefaultTableModel) tbl_gioHang.getModel();
//        model.setRowCount(0);
//        for (updateSoLuong x : list_remove) {
//            model.addRow(new Object[]{
//                x.getMa(), x.getTensp(), x.getSoluong(), x.getDongia()});
//        }
//    }

    private int getIndex(String ma) {
        for (int i = 0; i < list.size(); i++) {
            if (ma.equals(list.get(i).getMASP())) {
                return i;
            }
        }
        return -1;
    }

    @SuppressWarnings("empty-statement")
    public void remove() {
       
        int index1 = tbl_gioHang.getSelectedRow();
        String sl = JOptionPane.showInputDialog("Mời bạn nhập lại số lượng");
        if (!sl.matches("^[0-9]+$")) {
            JOptionPane.showMessageDialog(rootPane, "Số lượng phải là số và lơn hơn 0");
            return;
        }
        if (Integer.parseInt(sl) > Integer.parseInt(tbl_CTSP.getValueAt(index, 4) + "")) {
            JOptionPane.showMessageDialog(rootPane, "Số lượng bạn nhập vượt quá số lượng sản phẩm");
            return;
        }
        int index_list = getIndex(tbl_gioHang.getValueAt(index, 0) + "");
        giohang.get(index1).setSoluong(Integer.parseInt(sl));
        _IQLyBanHang_Service.removeSoLuong(Integer.parseInt(tbl_gioHang.getValueAt(index1, 2) + ""),_IQLyBanHang_Service.selectMACTSP(tbl_gioHang.getValueAt(index1, 0)+"")+"",Integer.parseInt(sl));
       list.get(index_list).setSOLUONG(list.get(index1).getSOLUONG() + Integer.parseInt(tbl_gioHang.getValueAt(index1, 2) + "")-Integer.parseInt(sl));
       
        fillTableGioHang();
       // loadTBGioHANG();
        model.setRowCount(0);
        tinhToan();
        fillTableChiTietSanPham();
        System.out.println(giohang.get(index1).getSoluong());

    }

    public void tinhToan() {
        double tongTien = 0;
        for (int i = 0; i < tbl_gioHang.getRowCount(); i++) {
            double Tien = Double.parseDouble(String.valueOf(tbl_gioHang.getValueAt(i, 2))) * Double.parseDouble(String.valueOf(tbl_gioHang.getValueAt(i, 3)));
            tongTien += Tien;
//             
        }
        double khachcantra = tongTien - (tongTien * Double.parseDouble(txt_chietKhau.getText()) / 100);
        txt_TongTien.setText(String.valueOf(tongTien));
        txt_canTra.setText(khachcantra + "");
    }

//    public void addVector() {
//        for (int i = 0; i < tbl_gioHang.getRowCount(); i++) {
//            list_remove.add(new updateSoLuong(String.valueOf(tbl_gioHang.getValueAt(i, 0)), String.valueOf(tbl_gioHang.getValueAt(i, 1)), (int) tbl_gioHang.getValueAt(i, 2), (double) tbl_gioHang.getValueAt(i, 3)));
//
//        }
//    }

    public void fillTableChiTietSanPham() {
        model = (DefaultTableModel) tbl_CTSP.getModel();
        model.setRowCount(0);
        for (SAN_PHAM_CT x : list) {
            model.addRow(new Object[]{
                x.getTENLOAIHANG(), x.getMASP(), x.getTENSP(), x.getTHONGTINSP(), x.getSOLUONG(), x.getDONGIA()});
        }
//        try {
//            for (CHI_TIET_SAN_PHAM chi_tiet_san_pham : list) {
//                LOAI_KINH loai_kinh = _IQLyBanHang_Service.findById_LoaiKinh(chi_tiet_san_pham.getMALOAI());
//                SAN_PHAM san_pham = _IQLyBanHang_Service.findById_SanPham(chi_tiet_san_pham.getMASP());
//                HANG hang = _IQLyBanHang_Service.findById_Hang(san_pham.getMAHANG());
//                MAU_SAC mau_sac = _IQLyBanHang_Service.findById_MauSac(chi_tiet_san_pham.getMAMAU());
//                KICH_THUOC kich_thuoc = _IQLyBanHang_Service.findById_KichThuoc(chi_tiet_san_pham.getMAKICHTHUOC());
//                GIOI_TINH gioi_tinh = _IQLyBanHang_Service.findById_GioiTinh(chi_tiet_san_pham.getMAGT());
//
//                if (chi_tiet_san_pham.getTRANGTHAI() == 0) {
//                    Object[] row = {
//                        _IQLyBanHang_Service.findById_LoaiHang(chi_tiet_san_pham.getMALOAIHANG()),
//                        chi_tiet_san_pham.getMASP(),
//                        _IQLyBanHang_Service.findById_SanPham(chi_tiet_san_pham.getMASP()),
//                        "Hãng: " + hang.getTENHANG()
//                        + ", Loại kính: " + loai_kinh.getLOAIKINH() + ", Màu sắc: " + mau_sac.getTENMAU()
//                        + ", Kích thước: " + kich_thuoc.getKICHTHUOC() + ", Giới tính: " + gioi_tinh.getGIOITINH(),
//                        chi_tiet_san_pham.getSOLUONG(),
//                        chi_tiet_san_pham.getDONGIA()
//                    };
//                    model.addRow(row);
//                }
//
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            Helper.DialogHelper.alert(this, "Lỗi truy vấn dữ liệu!");
//        }

    }
 public void fillTableGioHang(){
     DefaultTableModel model = (DefaultTableModel) tbl_gioHang.getModel();
     model.setRowCount(0);
   giohang = _IQLyBanHang_Service.SelectGroupBy(txt_maHD.getText());
     for (GIO_HANG x : giohang) {
         model.addRow(new Object[]{
         x.getMasp(),x.getTensp(),x.getSoluong(),x.getDongia()});
     }
 }
    public void fillTableGioHang(int soLuong) {
        DefaultTableModel model = (DefaultTableModel) tbl_gioHang.getModel();

        try {
//            Product models = (Product) cbb_Product.getSelectedItem();
//            List<CHI_TIET_SAN_PHAM> list = _IQLyBanHang_Service.selectAll_CTSP();
//            CHI_TIET_SAN_PHAM chi_tiet_san_pham = _IQLyBanHang_Service.findById_CTSP(String.valueOf(tbl_CTSP.getValueAt(_rowSP, 1)));

            Object[] row = {
                //                chi_tiet_san_pham.getMASP()
                tbl_CTSP.getValueAt(_rowSP, 1),
                tbl_CTSP.getValueAt(_rowSP, 2),
                soLuong,
                tbl_CTSP.getValueAt(_rowSP, 5)
            };
            model.addRow(row);

        } catch (Exception e) {
//            e.printStackTrace();
            Helper.DialogHelper.alert(this, "Lỗi truy vấn dữ liệu!");
        }

    }

    private void initWebcam() {
        Dimension size = WebcamResolution.QVGA.getSize();
        webcam = Webcam.getWebcams().get(0); //0 is default webcam
        webcam.setViewSize(size);

        panel = new WebcamPanel(webcam);
        panel.setPreferredSize(size);
        panel.setFPSDisplayed(true);

        panel_barcode.add(panel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 250, 190));

        executor.execute(this);
    }

    @Override
    public void run() {
        do {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Result result = null;
            BufferedImage image = null;

            if (webcam.isOpen()) {
                if ((image = webcam.getImage()) == null) {
                    continue;
                }
            }
            if (image == null) {
                return;
            }
            LuminanceSource source = new BufferedImageLuminanceSource(image);

            BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
            try {
                result = new MultiFormatReader().decode(bitmap);
            } catch (NotFoundException e) {
                //No result...
            }
            double tongTien = 0;
            
            if (result != null) {
                CHI_TIET_SAN_PHAM chi_tiet_san_pham = _IQLyBanHang_Service.findById_Barcode(result.getText());
                
                if (chi_tiet_san_pham != null && chi_tiet_san_pham.getTRANGTHAI() == 0 && chi_tiet_san_pham.getSOLUONG() > 0) {
                    SAN_PHAM san_pham = _IQLyBanHang_Service.findById_SanPham(chi_tiet_san_pham.getMASP());
                    int row=getIndex(chi_tiet_san_pham.getMASP());
//                    result_field.setText(chi_tiet_san_pham.getMASP() + " " + san_pham.getTENSP());
                    String soLuong = Helper.DialogHelper.prompt(null, "Tìm thấy sản phẩm: " + san_pham.getTENSP() + "\nMời bạn nhập số lượng:");
                    int soluongOf3Code=Integer.parseInt(soLuong);
//                    model = (DefaultTableModel) tbl_gioHang.getModel();
//                    model.addRow(new Object[]{chi_tiet_san_pham.getMASP(), san_pham.getTENSP(), soLuong, chi_tiet_san_pham.getDONGIA()});
//                    
//                    tongtien = 0;
//                    for (int i = 0; i < tbl_gioHang.getRowCount(); i++) {
//                        double Tien = Double.parseDouble(String.valueOf(tbl_gioHang.getValueAt(i, 2))) * Double.parseDouble(String.valueOf(tbl_gioHang.getValueAt(i, 3)));
//                        tongtien += Tien;
////                System.out.println(tongTien);
//                    }
//                    double khachcantra = tongtien - (tongtien * Double.parseDouble(txt_chietKhau.getText()) / 100);
//                    txt_TongTien.setText(String.valueOf(tongtien));
//                    txt_canTra.setText(khachcantra + "");
 AddChiTietHoaDon(txt_maHD.getText(),_IQLyBanHang_Service.selectMACTSP(chi_tiet_san_pham.getMASP()), soluongOf3Code,Float.parseFloat(chi_tiet_san_pham.getDONGIA()+""), Float.parseFloat( chi_tiet_san_pham.getDONGIA()+"")*soluongOf3Code,0);
                if (_IQLyBanHang_Service.updateSoLuong(soluongOf3Code,chi_tiet_san_pham.getMASP()) > 0) {
                    list.get(row).setSOLUONG(list.get(row).getSOLUONG() - soluongOf3Code);
                    model.setRowCount(0);
                    for (SAN_PHAM_CT x : list) {
                        model.addRow(new Object[]{
                            x.getTENLOAIHANG(), x.getMASP(), x.getTENSP(), x.getTHONGTINSP(), x.getSOLUONG(), x.getDONGIA()});
                    }
                }
                fillTableGioHang();
             //   this.fillTableGioHang(soluong);
//                list_remove = new Vector<>();
//                addVector();
//                list_temp.add(new updateSoLuong(list.get(index).getMASP(), soluong));

                //            System.out.println(tbl_gioHang.getRowCount());
                for (int i = 0; i < tbl_gioHang.getRowCount(); i++) {
                    double Tien = Double.parseDouble(String.valueOf(tbl_gioHang.getValueAt(i, 2))) * Double.parseDouble(String.valueOf(tbl_gioHang.getValueAt(i, 3)));
                    tongTien += Tien;
                    //
                }
                double khachcantra = tongTien - (tongTien * Double.parseDouble(txt_chietKhau.getText()) / 100);
                txt_TongTien.setText(String.valueOf(tongTien));
                txt_canTra.setText(khachcantra + "");
                }

            }

        } while (true);
    }

    @Override
    public Thread newThread(Runnable r) {
        Thread t = new Thread(r, "My Thread");
        t.setDaemon(true);
        return t;
    }

    public void init() {
        this.fillTableChiTietSanPham();
//        this.loadTableHoaDonByTrangThai();
        fillTableHoaDonByChuaHoanThanh();
        this.fillComboBoxNhanVien();
        this.fillComboBoxKhachHang();
//        cbb_trangThaiHD.removeAllItems();
//        cbb_trangThaiHD.addItem("Tất cả");
//        cbb_trangThaiHD.addItem("Đã thanh toán");
//        cbb_trangThaiHD.addItem("Chưa thanh toán");
        txt_maHD.setText(SoHoaDon());

        txt_ngayLap.setText(String.valueOf(Helper.DateHelper.toString(Helper.DateHelper.now())));
//        rd_dathanhtoan.setSelected(true);
        rd_dagiao.setSelected(true);
//        System.out.println(SoHoaDon());
        _jcbTrangThai.addItem("Đã thanh toán");
        _jcbTrangThai.addItem("Chưa thanh toán");
        _jcbTrangThaiSP.addItem("Đã giao");
        _jcbTrangThaiSP.addItem("Chưa giao");
//        tbl_HoaDon1.getColumnModel().getColumn(4).setCellEditor(new DefaultCellEditor(_jcbTrangThai));
//        tbl_HoaDon1.getColumnModel().getColumn(5).setCellEditor(new DefaultCellEditor(_jcbTrangThaiSP));

    }

    private String SoHoaDon() {
        String soHoaDon = "";
        String maHD = "HD" + soHoaDon;

        try {

            DateFormat dateFormat = new SimpleDateFormat("yyMMdd");

            Date d = new Date();

            soHoaDon = dateFormat.format(d);
//            System.out.println("soHoaDon: " + soHoaDon);
            int rowCount = _IQLyBanHang_Service.countMaHD_HoaDon(soHoaDon);
//            int rowCount = 0;
//            if (rs.next()) {
//                rowCount = rs.getInt(1);
//            }
            boolean dup = false;
            do {
                if (rowCount > 99) {
                    soHoaDon = soHoaDon + (rowCount + 1);
                } else if (rowCount > 9) {
                    soHoaDon = soHoaDon + "0" + (rowCount + 1);
                } else {
                    soHoaDon = soHoaDon + "00" + (rowCount + 1);
                }
//                System.out.println("soHoaDon: " + soHoaDon);
                List<HOA_DON> list = _IQLyBanHang_Service.GetBySoHoaDon(soHoaDon);
                if (list.size() > 0) {
                    dup = true;
                    rowCount++;
                    soHoaDon = dateFormat.format(d);
//                    break;
                } else {
                    dup = false;
                }
            } while (dup);

        } catch (Exception e) {
            System.out.println("Lỗi số hóa đơn");
        }
//        System.out.println(maHD);
        return "HD" + soHoaDon;
    }

    public void fillComboBoxNhanVien() {
        DefaultComboBoxModel model = (DefaultComboBoxModel) cbb_nvien.getModel();
        model.removeAllElements();
        try {
            List<NHAN_VIEN> list = _IQLyBanHang_Service.selectAll_NhanVien();
            for (NHAN_VIEN models : list) {
                if (models.getTRANGTHAI() == 0) {
                    model.addElement(models);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            DialogHelper.alert(this, "Lỗi truy vấn dữ liệu");
        }
    }

    public void fillComboBoxKhachHang() {
        DefaultComboBoxModel model = (DefaultComboBoxModel) cbb_KhachHang.getModel();
        model.removeAllElements();
        try {
            List<KHACH_HANG> list = _IQLyBanHang_Service.selectByKeyword(txt_searchKhachHang.getText());
            for (KHACH_HANG models : list) {
                if (models.getTRANGTHAI() == 0) {
                    model.addElement(models);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            DialogHelper.alert(this, "Lỗi truy vấn dữ liệu");
        }
    }

//    public void loadTableHoaDonByTrangThai() {
//        if (cbb_trangThaiHD.getSelectedIndex() == 0) {
//            this.fillTableHoaDon();
//        } else if (cbb_trangThaiHD.getSelectedIndex() == 1) {
//            this.fillTableHoaDonByHoanThanh();
//        } else {
//            this.fillTableHoaDonByChuaHoanThanh();
//        }
//    }
//    public void fillTableHoaDon() {
//        DefaultTableModel model = (DefaultTableModel) tbl_HoaDon.getModel();
//        model.setRowCount(0);
//        try {
//            List<HOA_DON> list = _IQLyBanHang_Service.selectAll_HoaDon();
//
//            for (HOA_DON hoa_don : list) {
//                KHACH_HANG khach_hang = _IQLyBanHang_Service.findById_KhachHang(hoa_don.getMAKH());
//                NHAN_VIEN nhan_vien = _IQLyBanHang_Service.findById_NhanVien(hoa_don.getMANV());
//                CHI_TIET_HOA_DON chi_tiet_hoa_don = _IQLyBanHang_Service.findById_CTHD(hoa_don.getMAHD());
////                System.out.println(chi_tiet_hoa_don.getTRANGTHAI());
//                Object[] row = {
//                    hoa_don.getMAHD(),
//                    nhan_vien.getHOTEN(),
//                    khach_hang.getTENKH(),
//                    khach_hang.getSDT(),
//                    hoa_don.getTRANGTHAI() == 0 ? "Đã thanh toán" : "Chưa thanh toán",
//                    chi_tiet_hoa_don.getTRANGTHAI() == 0 ? "Đã giao" : "Chưa giao"
//                };
//
//                model.addRow(row);
//            }
//
//        } catch (Exception e) {
//            Helper.DialogHelper.alert(this, "Lỗi truy vấn dữ liệu!");
//        }
//    }
//    public void fillTableHoaDonByHoanThanh() {
//        DefaultTableModel model = (DefaultTableModel) tbl_HoaDon.getModel();
//        model.setRowCount(0);
//        try {
//            List<HOA_DON> list = _IQLyBanHang_Service.selectAll_HoaDon();
//            for (HOA_DON hoa_don : list) {
//                KHACH_HANG khach_hang = _IQLyBanHang_Service.findById_KhachHang(hoa_don.getMAKH());
//                NHAN_VIEN nhan_vien = _IQLyBanHang_Service.findById_NhanVien(hoa_don.getMANV());
//                CHI_TIET_HOA_DON chi_tiet_hoa_don = _IQLyBanHang_Service.findById_CTHD(hoa_don.getMAHD());
//                if (hoa_don.getTRANGTHAI() == 0) {
//                    Object[] row = {
//                        hoa_don.getMAHD(),
//                        nhan_vien.getHOTEN(),
//                        khach_hang.getTENKH(),
//                        khach_hang.getSDT(),
//                        hoa_don.getTRANGTHAI() == 0 ? "Đã thanh toán" : "Chưa thanh toán",
//                        chi_tiet_hoa_don.getTRANGTHAI() == 0 ? "Đã giao" : "Chưa giao"
//                    };
//                    model.addRow(row);
//                }
//            }
//
//        } catch (Exception e) {
//            Helper.DialogHelper.alert(this, "Lỗi truy vấn dữ liệu!");
//        }
//    }
    public void fillTableHoaDonByChuaHoanThanh() {
        DefaultTableModel model = (DefaultTableModel) tbl_HoaDon1.getModel();
        model.setRowCount(0);
        try {
            List<HOA_DON> list = _IQLyBanHang_Service.selectAll_HoaDon();
            for (HOA_DON hoa_don : list) {
                KHACH_HANG khach_hang = _IQLyBanHang_Service.findById_KhachHang(hoa_don.getMAKH());
                NHAN_VIEN nhan_vien = _IQLyBanHang_Service.findById_NhanVien(hoa_don.getMANV());
                CHI_TIET_HOA_DON chi_tiet_hoa_don = _IQLyBanHang_Service.findById_CTHD(hoa_don.getMAHD());
                if (hoa_don.getTRANGTHAI() == 1) {
                    Object[] row = {
                        hoa_don.getMAHD(),
                        nhan_vien.getHOTEN(),
                        //                        khach_hang.getTENKH(),
                        //                        khach_hang.getSDT(),
                        hoa_don.getTRANGTHAI() == 0 ? "Đã thanh toán" : "Chưa thanh toán", //                        chi_tiet_hoa_don.getTRANGTHAI() == 0 ? "Đã giao" : "Chưa giao"
                    };
                    model.addRow(row);
                }
            }

        } catch (Exception e) {
            Helper.DialogHelper.alert(this, "Lỗi truy vấn dữ liệu!");
        }
    }

    public void AddCTHDtuTable(String maHD) {
        for (int i = 0; i < tbl_gioHang.getRowCount(); i++) {
            CHI_TIET_SAN_PHAM chi_tiet_san_pham = _IQLyBanHang_Service.findById_CTSP(String.valueOf(tbl_gioHang.getValueAt(i, 0)));
            int idCTSP = chi_tiet_san_pham.getMACTSP();
            int soLuong = Integer.parseInt(String.valueOf(tbl_gioHang.getValueAt(i, 2)));
            float donGia = Float.parseFloat(String.valueOf(tbl_gioHang.getValueAt(i, 3)));
            float thanhTien = soLuong * donGia;
            int trangThai = 0;
            if (rd_dagiao.isSelected()) {
                trangThai = 0;
            } else {
                trangThai = 1;
            }
            AddChiTietHoaDon(maHD, idCTSP, soLuong, donGia, thanhTien, trangThai);
        }
    }

    public void AddChiTietHoaDon(String maHD, int idCTSP, int soLuong, float donGia, float thanhTien, int trangThai) {
        CHI_TIET_HOA_DON chi_tiet_hoa_don = new CHI_TIET_HOA_DON();
        int count = _IQLyBanHang_Service.count_CTHD();
        count++;
        chi_tiet_hoa_don.setMACTHD(count);
        chi_tiet_hoa_don.setMAHD(maHD);
        chi_tiet_hoa_don.setMACTSP(idCTSP);
        chi_tiet_hoa_don.setSOLUONG(soLuong);
        chi_tiet_hoa_don.setDONGIA(donGia);
        chi_tiet_hoa_don.setTHANHTIEN(thanhTien);
        chi_tiet_hoa_don.setTRANGTHAI(trangThai);
        _IQLyBanHang_Service.insert_CTHD(chi_tiet_hoa_don);
        
    }
    

    public void NewHoaDon() {
        txt_maHD.setText(SoHoaDon());

        DefaultTableModel tbModel = (DefaultTableModel) tbl_gioHang.getModel();
        tbModel.setRowCount(0);
    }

//    public void inHoaDon() throws FileNotFoundException, DocumentException {
//
//        int sumHoadon = 1;
//        String name = "hoadon" + sumHoadon + ".pdf";
//        Document document = new Document();
//        PdfWriter.getInstance(document, new FileOutputStream(name));
//        document.open();
//        Font font = FontFactory.getFont(FontFactory.COURIER_BOLD, 30, BaseColor.RED);
//        Font font2 = FontFactory.getFont(FontFactory.COURIER_BOLD, 20, BaseColor.BLACK);
//        Chunk chunk = new Chunk("Hóa don thanh toán HSOFT", font);
//        Chunk chunk1 = new Chunk("Tong tien hang : " + txt_TongTien.getText() + "\n", font2);
//        Chunk chunk2 = new Chunk("Giam gia :" + txt_chietKhau.getText() + "\n", font2);
//        Chunk chunk3 = new Chunk("Thu khac :" + txt_thukhac.getText() + "\n", font2);
//        Chunk chunk4 = new Chunk("Tong tien da thanh toan : " + txt_thanhtoan.getText() + "\n", font2);
//        Chunk chunk5 = new Chunk("Trang thai :" + (rd_dathanhtoan.isSelected() ? "Da thanh toan" : "Chua thanh toan"), font2);
//        Chunk chunk6 = new Chunk("Trang thai sp :" + (rd_dagiao.isSelected() ? "Da giao" : "Chua giao"), font2);
//        Chunk chunk7 = new Chunk("Thu Ngan : La Van Tho", font);
//        document.add(new Paragraph(chunk));
//        document.add(new Paragraph(chunk1));
//        document.add(new Paragraph(chunk2));
//        document.add(new Paragraph(chunk3));
//        document.add(new Paragraph(chunk4));
//        document.add(new Paragraph(chunk5));
//        document.add(new Paragraph(chunk6));
//        document.add(new Paragraph(chunk7));
//        document.close();
//    }
//    public boolean updateSL() {
//        for (int i = 0; i < list_temp.size(); i++) {
//            if (_IQLyBanHang_Service.updateSoLuong(list_temp.get(i).getSoluong(), list_temp.get(i).getMa()) > 0) {
//                return true;
//            }
//        }
//        return false;
//    }

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
        jSplitPane1 = new javax.swing.JSplitPane();
        pnlSide = new javax.swing.JPanel();
        pnlTrangChu = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        lblTrangChu = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        jPanel6 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        lblDanhMuc = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JSeparator();
        jLabel1 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jPanel7 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        tabs_QLSP = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbl_gioHang = new javax.swing.JTable();
        jLabel15 = new javax.swing.JLabel();
        jTabbedPane4 = new javax.swing.JTabbedPane();
        jPanel10 = new javax.swing.JPanel();
        jLabel36 = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        jLabel41 = new javax.swing.JLabel();
        txt_TongTien = new javax.swing.JTextField();
        txt_chietKhau = new javax.swing.JTextField();
        txt_thukhac = new javax.swing.JTextField();
        txt_canTra = new javax.swing.JTextField();
        txt_thanhtoan = new javax.swing.JTextField();
        jTextField17 = new javax.swing.JTextField();
        jLabel56 = new javax.swing.JLabel();
        rd_dagiao = new javax.swing.JRadioButton();
        rd_chuaGiao = new javax.swing.JRadioButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel42 = new javax.swing.JLabel();
        txt_thanhtoan1 = new javax.swing.JTextField();
        jLabel24 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jRadioButton1 = new javax.swing.JRadioButton();
        jLabel18 = new javax.swing.JLabel();
        jRadioButton2 = new javax.swing.JRadioButton();
        txt_searchKhachHang = new javax.swing.JTextField();
        txt_ngayLap = new javax.swing.JTextField();
        txt_SdtKH = new javax.swing.JTextField();
        jLabel23 = new javax.swing.JLabel();
        cbb_nvien = new javax.swing.JComboBox<>();
        btn_addKH = new javax.swing.JButton();
        cbb_KhachHang = new javax.swing.JComboBox<>();
        jLabel14 = new javax.swing.JLabel();
        txt_dcKH = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        jButton2 = new javax.swing.JButton();
        txt_timKiem = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl_CTSP = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jLabel21 = new javax.swing.JLabel();
        txt_maHD = new javax.swing.JTextField();
        jToggleButton2 = new javax.swing.JToggleButton();
        panel_barcode = new javax.swing.JPanel();
        jToggleButton3 = new javax.swing.JToggleButton();
        jLabel17 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tbl_HoaDon1 = new javax.swing.JTable();
        jLabel22 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        pnlSide.setBackground(new java.awt.Color(255, 102, 102));

        pnlTrangChu.setBackground(new java.awt.Color(255, 102, 102));
        pnlTrangChu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                pnlTrangChuMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                pnlTrangChuMouseExited(evt);
            }
        });

        lblTrangChu.setBackground(new java.awt.Color(74, 31, 61));
        lblTrangChu.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        lblTrangChu.setForeground(new java.awt.Color(240, 240, 240));
        lblTrangChu.setText("   Hóa đơn");
        lblTrangChu.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblTrangChu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblTrangChuMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblTrangChuMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblTrangChuMouseExited(evt);
            }
        });

        jSeparator2.setForeground(new java.awt.Color(240, 240, 240));

        jPanel6.setBackground(new java.awt.Color(255, 102, 102));

        lblDanhMuc.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        lblDanhMuc.setForeground(new java.awt.Color(240, 240, 240));
        lblDanhMuc.setText("   Danh sách hóa đơn");
        lblDanhMuc.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblDanhMuc.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblDanhMucMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblDanhMucMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblDanhMucMouseExited(evt);
            }
        });

        jSeparator3.setForeground(new java.awt.Color(240, 240, 240));

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(lblDanhMuc, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblDanhMuc, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );

        javax.swing.GroupLayout pnlTrangChuLayout = new javax.swing.GroupLayout(pnlTrangChu);
        pnlTrangChu.setLayout(pnlTrangChuLayout);
        pnlTrangChuLayout.setHorizontalGroup(
            pnlTrangChuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlTrangChuLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlTrangChuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnlTrangChuLayout.createSequentialGroup()
                        .addComponent(lblTrangChu, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(pnlTrangChuLayout.createSequentialGroup()
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, 231, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        pnlTrangChuLayout.setVerticalGroup(
            pnlTrangChuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlTrangChuLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlTrangChuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblTrangChu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 4, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(240, 240, 240));
        jLabel1.setText("Horse SoftWare Team");

        jSeparator1.setForeground(new java.awt.Color(240, 240, 240));

        javax.swing.GroupLayout pnlSideLayout = new javax.swing.GroupLayout(pnlSide);
        pnlSide.setLayout(pnlSideLayout);
        pnlSideLayout.setHorizontalGroup(
            pnlSideLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlTrangChu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(pnlSideLayout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(pnlSideLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jSeparator1)
                    .addComponent(jLabel1))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlSideLayout.setVerticalGroup(
            pnlSideLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlSideLayout.createSequentialGroup()
                .addGap(70, 70, 70)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(63, 63, 63)
                .addComponent(pnlTrangChu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(467, Short.MAX_VALUE))
        );

        jSplitPane1.setLeftComponent(pnlSide);

        jPanel7.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel8.setBackground(new java.awt.Color(255, 102, 102));

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(240, 240, 240));
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("Quản lý Bán Hàng H-Soft");

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel9.setText("x");
        jLabel9.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel9.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel9MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel9MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel9MouseExited(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap(246, Short.MAX_VALUE)
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 560, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(388, 388, 388)
                .addComponent(jLabel9)
                .addGap(13, 13, 13))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 29, Short.MAX_VALUE))
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel7.add(jPanel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1220, 50));

        tabs_QLSP.setBackground(new java.awt.Color(255, 102, 102));
        tabs_QLSP.setTabLayoutPolicy(javax.swing.JTabbedPane.SCROLL_TAB_LAYOUT);
        tabs_QLSP.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 204, 204)));

        tbl_gioHang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã SP", "Tên SP", "Số Lượng", " Đơn giá", ""
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Boolean.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        tbl_gioHang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_gioHangMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tbl_gioHang);

        jLabel15.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/004.png"))); // NOI18N
        jLabel15.setText("Giỏ hàng");

        jTabbedPane4.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N

        jLabel36.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jLabel36.setText("Tổng tiền hàng");

        jLabel37.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jLabel37.setText("Giảm giá");

        jLabel38.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jLabel38.setText("Thu khác");

        jLabel39.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabel39.setText("Khách cần trả");

        jLabel40.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jLabel40.setText("Khách thanh toán");

        jLabel41.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jLabel41.setText("Tiền thừa trả khách");

        txt_TongTien.setEditable(false);
        txt_TongTien.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txt_TongTien.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txt_TongTien.setText("0");

        txt_chietKhau.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txt_chietKhau.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txt_chietKhau.setText("0");
        txt_chietKhau.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txt_chietKhauFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txt_chietKhauFocusLost(evt);
            }
        });
        txt_chietKhau.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_chietKhauActionPerformed(evt);
            }
        });

        txt_thukhac.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txt_thukhac.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txt_thukhac.setText("0");

        txt_canTra.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txt_canTra.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txt_canTra.setText("0");

        txt_thanhtoan.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txt_thanhtoan.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txt_thanhtoan.setText("0");

        jTextField17.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jTextField17.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTextField17.setText("0");

        jLabel56.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jLabel56.setText("Trạng thái SP");

        buttonGroup3.add(rd_dagiao);
        rd_dagiao.setText("Đã giao");

        buttonGroup3.add(rd_chuaGiao);
        rd_chuaGiao.setText("Chưa giao");

        jLabel2.setText("%");

        jLabel4.setText("đ");

        jLabel6.setText("đ");

        jLabel7.setText("đ");

        jLabel10.setText("đ");

        jLabel20.setText("đ");

        jLabel42.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jLabel42.setText("Thuế VAT");

        txt_thanhtoan1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txt_thanhtoan1.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txt_thanhtoan1.setText("10");

        jLabel24.setText("%");

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addComponent(jLabel56)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 57, Short.MAX_VALUE)
                        .addComponent(rd_dagiao)
                        .addGap(50, 50, 50)
                        .addComponent(rd_chuaGiao)
                        .addGap(47, 47, 47))
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel36)
                            .addComponent(jLabel37)
                            .addComponent(jLabel38)
                            .addComponent(jLabel39)
                            .addComponent(jLabel40)
                            .addComponent(jLabel41)
                            .addComponent(jLabel42))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txt_thanhtoan, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txt_canTra, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txt_thukhac, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txt_TongTien, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txt_chietKhau, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 184, Short.MAX_VALUE)
                            .addComponent(jTextField17)
                            .addComponent(txt_thanhtoan1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel4)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 9, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 9, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel24))
                        .addGap(30, 30, 30))))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel36)
                    .addComponent(txt_TongTien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addGap(10, 10, 10)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel37)
                    .addComponent(txt_chietKhau, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addGap(10, 10, 10)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel38)
                    .addComponent(txt_thukhac, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(7, 7, 7)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel42)
                    .addComponent(txt_thanhtoan1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel24))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel39)
                    .addComponent(txt_canTra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(9, 9, 9)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel40)
                    .addComponent(txt_thanhtoan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(9, 9, 9)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel41)
                    .addComponent(jTextField17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(rd_dagiao)
                        .addComponent(rd_chuaGiao))
                    .addComponent(jLabel56))
                .addContainerGap(27, Short.MAX_VALUE))
        );

        jTabbedPane4.addTab("Hóa đơn", jPanel10);

        jLabel11.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jLabel11.setText("Tìm kiếm KH:");

        jLabel12.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jLabel12.setText(" Khách hàng:");

        jLabel13.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jLabel13.setText("SĐT KH:");

        jLabel16.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jLabel16.setText("Hình thức thanh toán:");

        buttonGroup1.add(jRadioButton1);
        jRadioButton1.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jRadioButton1.setText("Chuyển khoản");

        jLabel18.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jLabel18.setText("Ngày lập:");

        buttonGroup1.add(jRadioButton2);
        jRadioButton2.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jRadioButton2.setText("Tiền mặt");

        txt_searchKhachHang.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        txt_searchKhachHang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_searchKhachHangActionPerformed(evt);
            }
        });

        txt_ngayLap.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N

        txt_SdtKH.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N

        jLabel23.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel23.setText("Nhân viên: ");

        cbb_nvien.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        btn_addKH.setText("+");
        btn_addKH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_addKHActionPerformed(evt);
            }
        });

        cbb_KhachHang.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbb_KhachHang.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbb_KhachHangItemStateChanged(evt);
            }
        });
        cbb_KhachHang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbb_KhachHangActionPerformed(evt);
            }
        });

        jLabel14.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jLabel14.setText("Địa chỉ KH:");

        txt_dcKH.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel16)
                                .addGap(18, 18, 18)
                                .addComponent(jRadioButton1)
                                .addGap(18, 18, 18)
                                .addComponent(jRadioButton2)))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(jLabel11)
                                        .addGap(21, 21, 21))
                                    .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(12, 12, 12)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cbb_KhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txt_searchKhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel18)
                                    .addComponent(jLabel23))
                                .addGap(45, 45, 45)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txt_ngayLap)
                                    .addComponent(cbb_nvien, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel13)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txt_dcKH, javax.swing.GroupLayout.DEFAULT_SIZE, 191, Short.MAX_VALUE)
                                    .addComponent(txt_SdtKH))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_addKH)
                        .addGap(22, 22, 22))))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel23)
                    .addComponent(cbb_nvien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(txt_ngayLap, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(txt_searchKhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_addKH))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(cbb_KhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(txt_SdtKH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(txt_dcKH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 8, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(jRadioButton1)
                    .addComponent(jRadioButton2))
                .addContainerGap())
        );

        jTabbedPane4.addTab("Đặt hàng", jPanel3);

        jPanel1.setBackground(new java.awt.Color(255, 102, 102));

        jButton2.setBackground(new java.awt.Color(204, 204, 204));
        jButton2.setFont(new java.awt.Font("Dialog", 0, 11)); // NOI18N
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/baseline_search_black_24dp.png"))); // NOI18N
        jButton2.setText("Tìm");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        txt_timKiem.setBackground(new java.awt.Color(240, 240, 240));

        tbl_CTSP.setBackground(new java.awt.Color(255, 204, 204));
        tbl_CTSP.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        tbl_CTSP.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Loại hàng", "Mã SP", "Tên SP", "Thông tin SP", "SL", "Giá"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbl_CTSP.setGridColor(new java.awt.Color(255, 102, 102));
        tbl_CTSP.setRowHeight(22);
        tbl_CTSP.setSelectionBackground(new java.awt.Color(255, 102, 102));
        tbl_CTSP.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_CTSPMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbl_CTSP);
        if (tbl_CTSP.getColumnModel().getColumnCount() > 0) {
            tbl_CTSP.getColumnModel().getColumn(0).setMinWidth(90);
            tbl_CTSP.getColumnModel().getColumn(0).setMaxWidth(100);
            tbl_CTSP.getColumnModel().getColumn(1).setMinWidth(90);
            tbl_CTSP.getColumnModel().getColumn(1).setMaxWidth(110);
            tbl_CTSP.getColumnModel().getColumn(2).setMinWidth(100);
            tbl_CTSP.getColumnModel().getColumn(2).setMaxWidth(130);
            tbl_CTSP.getColumnModel().getColumn(4).setMaxWidth(50);
            tbl_CTSP.getColumnModel().getColumn(5).setMaxWidth(80);
        }

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(txt_timKiem)
                        .addGap(18, 18, 18)
                        .addComponent(jButton2)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton2)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(txt_timKiem)
                        .addGap(6, 6, 6)))
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 169, Short.MAX_VALUE)
                .addContainerGap())
        );

        jButton1.setText("Xóa");

        jLabel21.setText("Mã hóa đơn: ");

        jToggleButton2.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jToggleButton2.setForeground(new java.awt.Color(0, 153, 51));
        jToggleButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/baseline_print_black_24dp.png"))); // NOI18N
        jToggleButton2.setText("In hóa đơn + Thanh toán");
        jToggleButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton2ActionPerformed(evt);
            }
        });

        panel_barcode.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jToggleButton3.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jToggleButton3.setForeground(new java.awt.Color(0, 153, 51));
        jToggleButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/baseline_print_black_24dp.png"))); // NOI18N
        jToggleButton3.setText("Tạo hóa đơn");
        jToggleButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton3ActionPerformed(evt);
            }
        });

        jLabel17.setText("Select All");

        jLabel19.setText("Select None");

        tbl_HoaDon1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Mã Hóa Đơn", "Nhân viên", "Trạng thái HĐ"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbl_HoaDon1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_HoaDon1MouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(tbl_HoaDon1);

        jLabel22.setFont(new java.awt.Font("Tahoma", 3, 12)); // NOI18N
        jLabel22.setText("Click 2 lần vào Sản phẩm muốn thêm vào giỏ hàng !");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 338, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(panel_barcode, javax.swing.GroupLayout.PREFERRED_SIZE, 271, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addComponent(jButton1)
                                .addGap(36, 36, 36)
                                .addComponent(jLabel17)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel19))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel2Layout.createSequentialGroup()
                                    .addComponent(jLabel15)
                                    .addGap(18, 18, 18)
                                    .addComponent(jLabel21)
                                    .addGap(18, 18, 18)
                                    .addComponent(txt_maHD, javax.swing.GroupLayout.PREFERRED_SIZE, 276, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 512, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTabbedPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 391, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 385, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addComponent(jToggleButton3)
                        .addGap(18, 18, 18)
                        .addComponent(jToggleButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 217, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(128, 128, 128))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addComponent(jTabbedPane4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addComponent(panel_barcode, javax.swing.GroupLayout.PREFERRED_SIZE, 246, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(42, 42, 42)))
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jToggleButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jToggleButton3)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel15)
                            .addComponent(jLabel21)
                            .addComponent(txt_maHD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 266, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel17)
                            .addComponent(jLabel19)
                            .addComponent(jButton1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel22)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 224, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        tabs_QLSP.addTab("Tạo hóa đơn", jPanel2);

        jPanel7.add(tabs_QLSP, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, 1210, 650));

        jSplitPane1.setRightComponent(jPanel7);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSplitPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSplitPane1)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void lblTrangChuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblTrangChuMouseClicked
        tabs_QLSP.setSelectedIndex(0);
    }//GEN-LAST:event_lblTrangChuMouseClicked

    private void lblTrangChuMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblTrangChuMouseEntered
        lblTrangChu.setForeground(Color.BLACK);
    }//GEN-LAST:event_lblTrangChuMouseEntered

    private void lblTrangChuMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblTrangChuMouseExited
        lblTrangChu.setForeground(Color.white);
    }//GEN-LAST:event_lblTrangChuMouseExited

    private void lblDanhMucMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblDanhMucMouseClicked
        tabs_QLSP.setSelectedIndex(1);
    }//GEN-LAST:event_lblDanhMucMouseClicked

    private void lblDanhMucMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblDanhMucMouseEntered
        lblDanhMuc.setForeground(Color.black);
    }//GEN-LAST:event_lblDanhMucMouseEntered

    private void lblDanhMucMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblDanhMucMouseExited
        lblDanhMuc.setForeground(Color.white);
    }//GEN-LAST:event_lblDanhMucMouseExited

    private void pnlTrangChuMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlTrangChuMouseEntered

    }//GEN-LAST:event_pnlTrangChuMouseEntered

    private void pnlTrangChuMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlTrangChuMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_pnlTrangChuMouseExited

    private void jLabel9MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel9MouseEntered
        jLabel9.setForeground(Color.red);
    }//GEN-LAST:event_jLabel9MouseEntered

    private void jLabel9MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel9MouseExited
        jLabel9.setForeground(Color.BLACK);
    }//GEN-LAST:event_jLabel9MouseExited

    private void jLabel9MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel9MouseClicked
        System.exit(0);
    }//GEN-LAST:event_jLabel9MouseClicked

    private void jToggleButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton3ActionPerformed
        try {
            this.NewHoaDon();
            HOA_DON hoa_don = new HOA_DON();
            KHACH_HANG khach_hang = _IQLyBanHang_Service.findByNames_KhachHang(String.valueOf(cbb_KhachHang.getSelectedItem()));
            NHAN_VIEN nhan_vien = _IQLyBanHang_Service.findByName_NhanVien(String.valueOf(cbb_nvien.getSelectedItem()));
            hoa_don.setMAHD(txt_maHD.getText());
            hoa_don.setNGAYLAP(Helper.DateHelper.toDate(txt_ngayLap.getText()));
            hoa_don.setMANV(nhan_vien.getMANV());
            hoa_don.setMAKH(khach_hang.getMAKH());
            hoa_don.setTRANGTHAI(1);

            _IQLyBanHang_Service.insert_HoaDon(hoa_don);
            fillTableHoaDonByChuaHoanThanh();
            Helper.DialogHelper.alert(this, "Tạo hóa đơn thành công !");
        } catch (Exception e) {
            Helper.DialogHelper.alert(this, "Tạo hóa đơn thất bại !");
        }
    }//GEN-LAST:event_jToggleButton3ActionPerformed

    private void jToggleButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton2ActionPerformed
//        HOA_DON hoa_don = _IQLyBanHang_Service.findById_HoaDon(txt_maHD.getText());
//        if (hoa_don == null) {
//            Helper.DialogHelper.alert(this, "Bạn chưa tạo đơn hàng !");
//            return;
//        }
//        if (tbl_gioHang.getRowCount() == 0) {
//            JOptionPane.showMessageDialog(rootPane, "Giỏ hàng của bạn đang trống ");
//            return;
//        }
        //        int chon = JOptionPane.showConfirmDialog(rootPane, "Bạn muốn in hóa đơn không ?", "Hỏi", JOptionPane.YES_NO_OPTION);
        //        if (chon == 0) {
        //            try {
        //                if (updateSL() == true) {
        //                    JOptionPane.showMessageDialog(rootPane, "In thành công hóa đơn");
        ////                    inHoaDon();// TODO add your handling code here:
        //
        //                }
        //
        //            } catch (FileNotFoundException | DocumentException ex) {
        //                Logger.getLogger(FormBanHang1.class.getName()).log(Level.SEVERE, null, ex);
        //            }
        //        }
        
//        try {
//
//            AddCTHDtuTable(hoa_don.getMAHD());
//            Helper.DialogHelper.alert(this, "Tạo đơn hàng thành công !");
//            this.fillTableHoaDonByChuaHoanThanh();
//            this.NewHoaDon();
//            this.fillTableChiTietSanPham();
//        } catch (Exception e) {
//            Helper.DialogHelper.alert(this, "Tạo đơn hàng thất bại !");
//        }
    }//GEN-LAST:event_jToggleButton2ActionPerformed

    private void tbl_CTSPMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_CTSPMouseClicked
           
        
        if (evt.getClickCount() == 2) {
            HOA_DON hd=_IQLyBanHang_Service.findById_HoaDon(txt_maHD.getText());
            if(hd==null){
                Helper.DialogHelper.alert(this, "Mã hóa đơn "+txt_maHD.getText()+" chưa được tạo !");
                    return;
            }
            this._rowSP = tbl_CTSP.getSelectedRow();
            double tongTien = 0;
            index = tbl_CTSP.getSelectedRow();
            String soLuong = Helper.DialogHelper.prompt(this, "Mời bạn nhập số lượng");
            if (soLuong != null) {
                soluong = Integer.parseInt(soLuong);
                if (soluong > Integer.parseInt(String.valueOf(tbl_CTSP.getValueAt(_rowSP, 4)))) {
                    Helper.DialogHelper.alert(this, "Số lượng không đủ !");
                    return;
                }
                AddChiTietHoaDon(txt_maHD.getText(),_IQLyBanHang_Service.selectMACTSP(tbl_CTSP.getValueAt(index,1)+""), soluong, Float.parseFloat(tbl_CTSP.getValueAt(index,5)+""), Float.parseFloat(tbl_CTSP.getValueAt(index,5)+"")*soluong,0);
                if (_IQLyBanHang_Service.updateSoLuong(Integer.parseInt(soLuong), _IQLyBanHang_Service.selectMACTSP((String) tbl_CTSP.getValueAt(index, 1))+"") > 0) {
                    list.get(index).setSOLUONG(list.get(index).getSOLUONG() - soluong);
                    model.setRowCount(0);
                    for (SAN_PHAM_CT x : list) {
                        model.addRow(new Object[]{
                            x.getTENLOAIHANG(), x.getMASP(), x.getTENSP(), x.getTHONGTINSP(), x.getSOLUONG(), x.getDONGIA()});
                    }
                }
                fillTableGioHang();
             //   this.fillTableGioHang(soluong);
//                list_remove = new Vector<>();
//                addVector();
              //  list_temp.add(new updateSoLuong(list.get(index).getMASP(), soluong));

                //            System.out.println(tbl_gioHang.getRowCount());
                for (int i = 0; i < tbl_gioHang.getRowCount(); i++) {
                    double Tien = Double.parseDouble(String.valueOf(tbl_gioHang.getValueAt(i, 2))) * Double.parseDouble(String.valueOf(tbl_gioHang.getValueAt(i, 3)));
                    tongTien += Tien;
                    //
                }
                double khachcantra = tongTien - (tongTien * Double.parseDouble(txt_chietKhau.getText()) / 100);
                txt_TongTien.setText(String.valueOf(tongTien));
                txt_canTra.setText(khachcantra + "");
            }
        }
    }//GEN-LAST:event_tbl_CTSPMouseClicked

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed

    }//GEN-LAST:event_jButton2ActionPerformed

    private void cbb_KhachHangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbb_KhachHangActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbb_KhachHangActionPerformed

    private void cbb_KhachHangItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbb_KhachHangItemStateChanged
        if (cbb_KhachHang.getItemCount() > 0) {
            KHACH_HANG khach_hang = _IQLyBanHang_Service.findByNames_KhachHang(String.valueOf(cbb_KhachHang.getSelectedItem()));
            if (khach_hang != null) {
                txt_SdtKH.setText(khach_hang.getSDT());
                txt_dcKH.setText(khach_hang.getDIACHI());
            }
        }
    }//GEN-LAST:event_cbb_KhachHangItemStateChanged

    private void btn_addKHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_addKHActionPerformed
        new KhachHang_JDialog(null, true).setVisible(true);
    }//GEN-LAST:event_btn_addKHActionPerformed

    private void txt_searchKhachHangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_searchKhachHangActionPerformed
        this.fillComboBoxKhachHang();
    }//GEN-LAST:event_txt_searchKhachHangActionPerformed

    private void txt_chietKhauActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_chietKhauActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_chietKhauActionPerformed

    private void txt_chietKhauFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_chietKhauFocusLost
        double tongTien = Double.parseDouble(txt_TongTien.getText());
        double chietKhau = tongTien * (Double.parseDouble(txt_chietKhau.getText()) / 100);
        txt_canTra.setText(String.valueOf(tongTien - chietKhau));
    }//GEN-LAST:event_txt_chietKhauFocusLost

    private void txt_chietKhauFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_chietKhauFocusGained
        double tongTien = Double.parseDouble(txt_TongTien.getText());
        double chietKhau = tongTien * (Double.parseDouble(txt_chietKhau.getText()) / 100);
        txt_canTra.setText(String.valueOf(tongTien - chietKhau));
    }//GEN-LAST:event_txt_chietKhauFocusGained

    private void tbl_gioHangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_gioHangMouseClicked
        remove();        // TODO add your handling code here:
    }//GEN-LAST:event_tbl_gioHangMouseClicked

    private void tbl_HoaDon1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_HoaDon1MouseClicked
    int index=tbl_HoaDon1.getSelectedRow();
    txt_maHD.setText(tbl_HoaDon1.getValueAt(index, 0)+"");
    fillTableGioHang();
    }//GEN-LAST:event_tbl_HoaDon1MouseClicked

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
            java.util.logging.Logger.getLogger(FormBanHang1.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FormBanHang1.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FormBanHang1.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FormBanHang1.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
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

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                FormBanHang1 dialog = new FormBanHang1(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btn_addKH;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.ButtonGroup buttonGroup3;
    private javax.swing.JComboBox<String> cbb_KhachHang;
    private javax.swing.JComboBox<String> cbb_nvien;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JTabbedPane jTabbedPane4;
    private javax.swing.JTextField jTextField17;
    private javax.swing.JToggleButton jToggleButton2;
    private javax.swing.JToggleButton jToggleButton3;
    private javax.swing.JLabel lblDanhMuc;
    private javax.swing.JLabel lblTrangChu;
    private javax.swing.JPanel panel_barcode;
    private javax.swing.JPanel pnlSide;
    private javax.swing.JPanel pnlTrangChu;
    private javax.swing.JRadioButton rd_chuaGiao;
    private javax.swing.JRadioButton rd_dagiao;
    private javax.swing.JTabbedPane tabs_QLSP;
    private javax.swing.JTable tbl_CTSP;
    private javax.swing.JTable tbl_HoaDon1;
    private javax.swing.JTable tbl_gioHang;
    private javax.swing.JTextField txt_SdtKH;
    private javax.swing.JTextField txt_TongTien;
    private javax.swing.JTextField txt_canTra;
    private javax.swing.JTextField txt_chietKhau;
    private javax.swing.JTextField txt_dcKH;
    private javax.swing.JTextField txt_maHD;
    private javax.swing.JTextField txt_ngayLap;
    private javax.swing.JTextField txt_searchKhachHang;
    private javax.swing.JTextField txt_thanhtoan;
    private javax.swing.JTextField txt_thanhtoan1;
    private javax.swing.JTextField txt_thukhac;
    private javax.swing.JTextField txt_timKiem;
    // End of variables declaration//GEN-END:variables
}

class updateSoLuong {

    private String ma, tensp;
    private int soluong;
    private double dongia;

    public updateSoLuong(String ma, String tensp, int soluong, double dongia) {
        this.ma = ma;
        this.tensp = tensp;
        this.soluong = soluong;
        this.dongia = dongia;
    }

    public String getTensp() {
        return tensp;
    }

    public void setTensp(String tensp) {
        this.tensp = tensp;
    }

    public double getDongia() {
        return dongia;
    }

    public void setDongia(double dongia) {
        this.dongia = dongia;
    }

    public updateSoLuong() {
    }

    public updateSoLuong(String ma, int soluong) {
        this.ma = ma;
        this.soluong = soluong;
    }

    public String getMa() {
        return ma;
    }

    public void setMa(String ma) {
        this.ma = ma;
    }

    public int getSoluong() {
        return soluong;
    }

    public void setSoluong(int soluong) {
        this.soluong = soluong;
    }

}
