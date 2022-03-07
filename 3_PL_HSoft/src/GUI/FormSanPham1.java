/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Helper.DialogHelper;
import Helper.UtilityHelper;
import IServices.IQLySanPhamService;
import Models.CHI_TIET_SAN_PHAM;
import Models.DON_VI_TINH;
import Models.GIOI_TINH;
import Models.HANG;
import Models.KICH_THUOC;
import Models.KIEU_DANG;
import Models.LOAI_HANG;
import Models.LOAI_KINH;
import Models.MAU_SAC;
import Models.SAN_PHAM;
import Sevices.QLySanPham_Service;
import com.barcodelib.barcode.Linear;
import java.awt.Color;
import java.awt.FileDialog;
import java.awt.Frame;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.imageio.ImageIO;
import javax.swing.ButtonGroup;
import javax.swing.DefaultCellEditor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author LENOVO
 */
public class FormSanPham1 extends javax.swing.JDialog {

    IQLySanPhamService _IQLySanPhamService = (IQLySanPhamService) new QLySanPham_Service();
    JTextField _field = new JTextField();

    int _rowHang = -1;
    int _rowGioiTinh = -1;
    int _rowDVT = -1;
    int _rowLoaiHang = -1;
    int _rowKieuDang = -1;
    int _rowLoaiKinh = -1;
    int _rowKichThuoc = -1;
    int _rowMauSac = -1;
    int _rowSanPham = -1;

    File _file;
    FileDialog _fd = new FileDialog(new Frame(), "Chọn ảnh cho sản phẩm", FileDialog.LOAD);
    String _images = null;
    private javax.swing.JTextArea jTextArea1;

    public FormSanPham1(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        init();
    }

    public void init() {
        setLocationRelativeTo(null);
        ButtonGroup bgHang = new ButtonGroup();
        ButtonGroup bgGioiTinh = new ButtonGroup();
        ButtonGroup bgDVT = new ButtonGroup();
        ButtonGroup bgLoaiHang = new ButtonGroup();
        ButtonGroup bgKieuDang = new ButtonGroup();
        ButtonGroup bgLoaiKinh = new ButtonGroup();
        ButtonGroup bgKichThuoc = new ButtonGroup();
        ButtonGroup bgMauSac = new ButtonGroup();
        ButtonGroup bgSanPham = new ButtonGroup();

        bgHang.add(rdb_0_Hang);
        bgHang.add(rdb_1_Hang);

        bgGioiTinh.add(rdb_0_GioiTinh);
        bgGioiTinh.add(rdb_1_GioiTinh);

        bgLoaiHang.add(rdb_0_LoaiHang);
        bgLoaiHang.add(rdb_1_LoaiHang);

        bgDVT.add(rdb_0_DVT);
        bgDVT.add(rdb_1_DVT);

        bgKieuDang.add(rdb_0_KieuDang);
        bgKieuDang.add(rdb_1_KieuDang);

        bgLoaiKinh.add(rdb_0_LoaiKinh);
        bgLoaiKinh.add(rdb_1_LoaiKinh);

        bgKichThuoc.add(rdb_0_KichThuoc);
        bgKichThuoc.add(rdb_1_KichThuoc);

        bgMauSac.add(rdb_0_MauSac);
        bgMauSac.add(rdb_1_MauSac);

        bgSanPham.add(rdb_0_SanPham);
        bgSanPham.add(rdb_1_SanPham);

        rdb_0_Hang.setSelected(true);
        rdb_0_GioiTinh.setSelected(true);
        rdb_0_DVT.setSelected(true);
        rdb_0_LoaiHang.setSelected(true);
        rdb_0_KieuDang.setSelected(true);
        rdb_0_LoaiKinh.setSelected(true);
        rdb_0_KichThuoc.setSelected(true);
        rdb_0_MauSac.setSelected(true);
        rdb_0_SanPham.setSelected(true);

//        ChuyenManHinh control = new ChuyenManHinh(pnlbang);
//        control.setView(pnlTrangChu, lblTrangChu);
        this.fillTableHang();
        this.fillTableGioiTinh();
        this.fillTableDonViTinh();
        this.fillTableLoaiHang();
        this.fillTableKieuDang();
        this.fillTableLoaiKinh();
        this.fillTableKichThuoc();
        this.fillTableMauSac();
        this.fillTableChiTietSanPham();
//        
        this.fillComboBoxLoaiHang();
        this.fillComboBoxHang();
        this.fillComboBoxGioiTinh();
        this.fillComboBoxDVT();
        this.fillComboBoxKichThuoc();
        this.fillComboBoxMauSac();
        this.fillComboBoxLoaiKinh();
        this.fillComboBoxKieuDang();

        this.selectCbbLoaiHang();
//        this.selectCbbProduct2();
//        this.fillTableGioiTinh();
//        this.fillComboBoxOption();
//        this.selectCbbOptions();
//        this.actionOptions();
//tbl_CTSP.addRowSelectionInterval(0, 0);
        tbl_CTSP.getColumnModel().getColumn(0).setCellEditor(new DefaultCellEditor(cbb_DVT));

    }

    public void fillComboBoxLoaiHang() {
        DefaultComboBoxModel model = (DefaultComboBoxModel) cbb_LoaiHang.getModel();
        DefaultComboBoxModel model2 = (DefaultComboBoxModel) cbb_LoaiHang2.getModel();
        model.removeAllElements();
        model2.removeAllElements();
        try {
            List<LOAI_HANG> list = _IQLySanPhamService.selectAll_LoaiHang();
            cbb_LoaiHang.addItem("Tất cả");
            for (LOAI_HANG models : list) {
                if (models.getTRANGTHAI() == 0) {
                    model.addElement(models);
                    model2.addElement(models);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            DialogHelper.alert(this, "Lỗi truy vấn dữ liệu");
        }
    }

    public void fillComboBoxHang() {
        DefaultComboBoxModel model = (DefaultComboBoxModel) cbb_Hang.getModel();
        model.removeAllElements();
        try {
            List<HANG> list = _IQLySanPhamService.selectAll_Hang();
            for (HANG models : list) {
                if (models.getTRANGTHAI() == 0) {
                    model.addElement(models);
                }
            }
//             tbl_CTSP.getColumnModel().getColumn(3).setCellEditor(new DefaultCellEditor(cbb_DVT));
        } catch (Exception e) {
            e.printStackTrace();
            DialogHelper.alert(this, "Lỗi truy vấn dữ liệu");
        }
    }

    public void fillComboBoxGioiTinh() {
        DefaultComboBoxModel model = (DefaultComboBoxModel) cbb_gioiTinh.getModel();
        model.removeAllElements();
        try {
            List<GIOI_TINH> list = _IQLySanPhamService.selectAll_GioiTinh();
            for (GIOI_TINH models : list) {
                if (models.getTrangThai() == 0) {
                    model.addElement(models);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            DialogHelper.alert(this, "Lỗi truy vấn dữ liệu");
        }
    }

    public void fillComboBoxDVT() {
//        String [] a = {"a","b","c"};
        JComboBox jcb = new JComboBox();
//        jTable1.getColumnModel().getColumn(0).setCellEditor(new DefaultCellEditor(jcb));
        DefaultComboBoxModel model = (DefaultComboBoxModel) cbb_DVT.getModel();
        model.removeAllElements();
        try {
            List<DON_VI_TINH> list = _IQLySanPhamService.selectAll_DonViTinh();
            for (DON_VI_TINH models : list) {
                if (models.getTRANGTHAI() == 0) {
                    model.addElement(models);
//                   jcb.addItem(list);
                }
            }
            tbl_CTSP.getColumnModel().getColumn(5).setCellEditor(new DefaultCellEditor(cbb_DVT));
        } catch (Exception e) {
            e.printStackTrace();
            DialogHelper.alert(this, "Lỗi truy vấn dữ liệu");
        }
    }

    public void fillComboBoxKichThuoc() {
        DefaultComboBoxModel model = (DefaultComboBoxModel) cbb_KichThuoc.getModel();
        model.removeAllElements();
        try {
            List<KICH_THUOC> list = _IQLySanPhamService.selectAll_KichThuoc();
            for (KICH_THUOC models : list) {
                if (models.getTRANGTHAI() == 0) {
                    model.addElement(models);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            DialogHelper.alert(this, "Lỗi truy vấn dữ liệu");
        }
    }

    public void fillComboBoxMauSac() {
        DefaultComboBoxModel model = (DefaultComboBoxModel) cbb_MauSac.getModel();
        model.removeAllElements();
        try {
            List<MAU_SAC> list = _IQLySanPhamService.selectAll_MauSac();
            for (MAU_SAC models : list) {
                if (models.getTRANGTHAI() == 0) {
                    model.addElement(models);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            DialogHelper.alert(this, "Lỗi truy vấn dữ liệu");
        }
    }

    public void fillComboBoxLoaiKinh() {
        DefaultComboBoxModel model = (DefaultComboBoxModel) cbb_LoaiKinh.getModel();
        model.removeAllElements();
        try {
            List<LOAI_KINH> list = _IQLySanPhamService.selectAll_LoaiKinh();
            for (LOAI_KINH models : list) {
                if (models.getTRANGTHAI() == 0) {
                    model.addElement(models);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            DialogHelper.alert(this, "Lỗi truy vấn dữ liệu");
        }
    }

    public void fillComboBoxKieuDang() {
        DefaultComboBoxModel model = (DefaultComboBoxModel) cbb_KieuDang.getModel();
        model.removeAllElements();
        try {
            List<KIEU_DANG> list = _IQLySanPhamService.selectAll_KieuDang();
            for (KIEU_DANG models : list) {
                if (models.getTrangThai() == 0) {
                    model.addElement(models);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            DialogHelper.alert(this, "Lỗi truy vấn dữ liệu");
        }
    }

    public void fillComboBoxIDVariant_ProductVarint() {
//        DefaultComboBoxModel model = (DefaultComboBoxModel) cbb_IDVariant.getModel();
//        model.removeAllElements();
//        try {
//            Product product = (Product) cbb_Product.getSelectedItem();
//            List<Product_variant> list = _IQLySanPhamService.selectByIDProduct_Product_variant(product.getID_Product());
//            for (Product_variant models : list) {
//                if (models.getTRANGTHAI() == 0) {
//                    model.addElement(models);
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            DialogHelper.alert(this, "Lỗi truy vấn dữ liệu");
//        }
//    }
//
//    public void fillComboBoxAllProductVarint() {
//        DefaultComboBoxModel model = (DefaultComboBoxModel) cbb_IDVariant.getModel();
//        model.removeAllElements();
//        try {
////            Product product = (Product) cbb_Product.getSelectedItem();
//            List<Product_variant> list = _IQLySanPhamService.selectAllProduct_variant();
//            for (Product_variant models : list) {
//                if (models.getTRANGTHAI() == 0) {
//                    model.addElement(models);
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            DialogHelper.alert(this, "Lỗi truy vấn dữ liệu");
//        }
    }
//

    public void selectCbbLoaiHang() {
        if (cbb_LoaiHang.getSelectedIndex() == 0) {
            this.fillTableChiTietSanPham();
//            this.fillComboBoxAllProductVarint();
        } else {
            LOAI_HANG models = (LOAI_HANG) cbb_LoaiHang.getSelectedItem();
            if (models == null) {
                return;
            }
            fillTableChiTietSanPhamByID();
//            fillComboBoxIDVariant_ProductVarint();
        }
    }

    public void selectCbbProduct2() {
//        Product models = (Product) cbb_Product1.getSelectedItem();
//        if (models == null) {
//            return;
//        }
//        fillTableProductVarintByID();

    }

    public void selectCbbOptions() {
//        Options models = (Options) cbb_option.getSelectedItem();
//        if (models == null) {
//            return;
//        }
//        fillTableOptionValuesByID();
    }

    public void fillTableVariantValues() {
//        DefaultTableModel model = (DefaultTableModel) tbl_Variant_values2.getModel();
//        model.setRowCount(0);
//        try {
////            Product models = (Product) cbb_Product.getSelectedItem();
//            List<Variant_Values2> list = _IQLySanPhamService.selectAllVariant_Values2();
//            for (Variant_Values2 values2 : list) {
//                Object[] row = {
//                    values2.getNames(),
//                    values2.getID_Variant(),
//                    values2.getSKU_ID(),
//                    values2.getThuocTinh(),
//                    values2.getQuantity(),
//                    values2.getPrice(),
//                    values2.getTrangThai() == 0 ? "Còn hàng" : "Hết hàng"
//                };
//                model.addRow(row);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            Helper.DialogHelper.alert(this, "Lỗi truy vấn dữ liệu!");
//        }

    }

    public void fillTableVariantValuesByID() {
//        DefaultTableModel model = (DefaultTableModel) tbl_Variant_values2.getModel();
//        model.setRowCount(0);
//        try {
//            Product models = (Product) cbb_Product.getSelectedItem();
//            List<Variant_Values2> list = _IQLySanPhamService.selectByIDVariant_Values2(models.getID_Product());
//            for (Variant_Values2 values2 : list) {
//                Object[] row = {
//                    values2.getNames(),
//                    values2.getID_Variant(),
//                    values2.getSKU_ID(),
//                    values2.getThuocTinh(),
//                    values2.getQuantity(),
//                    values2.getPrice(),
//                    values2.getTrangThai() == 0 ? "Còn hàng" : "Hết hàng"
//                };
//                model.addRow(row);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            Helper.DialogHelper.alert(this, "Lỗi truy vấn dữ liệu!");
//        }

    }

    public void fillTableHang() {
        DefaultTableModel model = (DefaultTableModel) tbl_Hang.getModel();
        model.setRowCount(0);
        try {
//            Product models = (Product) cbb_Product.getSelectedItem();
            List<HANG> list = _IQLySanPhamService.selectAll_Hang();
            for (HANG hang : list) {
                Object[] row = {
                    hang.getMAHANG(),
                    hang.getTENHANG(),
                    hang.getTRANGTHAI() == 0 ? "Đang kinh doanh" : "Ngừng kinh doanh"
                };
                model.addRow(row);
            }
           
        } catch (Exception e) {
            e.printStackTrace();
            Helper.DialogHelper.alert(this, "Lỗi truy vấn dữ liệu!");
        }

    }

    public void fillTableGioiTinh() {
        DefaultTableModel model = (DefaultTableModel) tbl_GioiTinh.getModel();
        model.setRowCount(0);
        try {
//            Product models = (Product) cbb_Product.getSelectedItem();
            List<GIOI_TINH> list = _IQLySanPhamService.selectAll_GioiTinh();
            for (GIOI_TINH gioi_tinh : list) {
                Object[] row = {
                    gioi_tinh.getMAGT(),
                    gioi_tinh.getGIOITINH(),
                    gioi_tinh.getTrangThai() == 0 ? "Hoạt động" : "Không hoạt động"
                };
                model.addRow(row);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Helper.DialogHelper.alert(this, "Lỗi truy vấn dữ liệu!");
        }

    }

    public void fillTableDonViTinh() {
        DefaultTableModel model = (DefaultTableModel) tbl_DonViTinh.getModel();
        model.setRowCount(0);
        try {
//            Product models = (Product) cbb_Product.getSelectedItem();
            List<DON_VI_TINH> list = _IQLySanPhamService.selectAll_DonViTinh();
            for (DON_VI_TINH don_vi_tinh : list) {
                Object[] row = {
                    don_vi_tinh.getMADV(),
                    don_vi_tinh.getTEN(),
                    don_vi_tinh.getTRANGTHAI() == 0 ? "Hoạt động" : "Không hoạt động"
                };
                model.addRow(row);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Helper.DialogHelper.alert(this, "Lỗi truy vấn dữ liệu!");
        }

    }

    public void fillTableLoaiHang() {
        DefaultTableModel model = (DefaultTableModel) tbl_LoaiHang.getModel();
        model.setRowCount(0);
        try {
//            Product models = (Product) cbb_Product.getSelectedItem();
            List<LOAI_HANG> list = _IQLySanPhamService.selectAll_LoaiHang();
            for (LOAI_HANG loai_hang : list) {
                Object[] row = {
                    loai_hang.getMALOAIHANG(),
                    loai_hang.getTENLOAIHANG(),
                    loai_hang.getTRANGTHAI() == 0 ? "Đang kinh doanh" : "Ngừng kinh doanh"
                };
                model.addRow(row);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Helper.DialogHelper.alert(this, "Lỗi truy vấn dữ liệu!");
        }

    }

    public void fillTableKieuDang() {
        DefaultTableModel model = (DefaultTableModel) tbl_KieuDang.getModel();
        model.setRowCount(0);
        try {
//            Product models = (Product) cbb_Product.getSelectedItem();
            List<KIEU_DANG> list = _IQLySanPhamService.selectAll_KieuDang();
            for (KIEU_DANG kieu_dang : list) {
                Object[] row = {
                    kieu_dang.getMAKIEUDANG(),
                    kieu_dang.getKIEUDANG(),
                    kieu_dang.getTrangThai() == 0 ? "Hoạt động" : "Không hoạt động"
                };
                model.addRow(row);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Helper.DialogHelper.alert(this, "Lỗi truy vấn dữ liệu!");
        }

    }

    public void fillTableLoaiKinh() {
        DefaultTableModel model = (DefaultTableModel) tbl_LoaiKinh.getModel();
        model.setRowCount(0);
        try {
//            Product models = (Product) cbb_Product.getSelectedItem();
            List<LOAI_KINH> list = _IQLySanPhamService.selectAll_LoaiKinh();
            for (LOAI_KINH loai_kinh : list) {
                Object[] row = {
                    loai_kinh.getMALOAI(),
                    loai_kinh.getLOAIKINH(),
                    loai_kinh.getTRANGTHAI() == 0 ? "Hoạt động" : "Không hoạt động"
                };
                model.addRow(row);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Helper.DialogHelper.alert(this, "Lỗi truy vấn dữ liệu!");
        }

    }

    public void fillTableKichThuoc() {
        DefaultTableModel model = (DefaultTableModel) tbl_KichThuoc.getModel();
        model.setRowCount(0);
        try {
//            Product models = (Product) cbb_Product.getSelectedItem();
            List<KICH_THUOC> list = _IQLySanPhamService.selectAll_KichThuoc();
            for (KICH_THUOC kich_thuoc : list) {
                Object[] row = {
                    kich_thuoc.getMAKICHTHUOC(),
                    kich_thuoc.getKICHTHUOC(),
                    kich_thuoc.getTRANGTHAI() == 0 ? "Hoạt động" : "Không hoạt động"
                };
                model.addRow(row);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Helper.DialogHelper.alert(this, "Lỗi truy vấn dữ liệu!");
        }

    }

    public void fillTableMauSac() {
        DefaultTableModel model = (DefaultTableModel) tbl_MauSac.getModel();
        model.setRowCount(0);
        try {
//            Product models = (Product) cbb_Product.getSelectedItem();
            List<MAU_SAC> list = _IQLySanPhamService.selectAll_MauSac();
            for (MAU_SAC mau_sac : list) {
                Object[] row = {
                    mau_sac.getMAMAU(),
                    mau_sac.getTENMAU(),
                    mau_sac.getTRANGTHAI() == 0 ? "Hoạt động" : "Không hoạt động"
                };
                model.addRow(row);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Helper.DialogHelper.alert(this, "Lỗi truy vấn dữ liệu!");
        }

    }

    public void fillTableChiTietSanPham() {
        DefaultTableModel model = (DefaultTableModel) tbl_CTSP.getModel();
        model.setRowCount(0);

        try {

//            Product models = (Product) cbb_Product.getSelectedItem();
            List<CHI_TIET_SAN_PHAM> list = _IQLySanPhamService.selectAll_CTSP();
            for (CHI_TIET_SAN_PHAM chi_tiet_san_pham : list) {
                LOAI_KINH loai_kinh = _IQLySanPhamService.findById_LoaiKinh(chi_tiet_san_pham.getMALOAI());
                MAU_SAC mau_sac = _IQLySanPhamService.findById_MauSac(chi_tiet_san_pham.getMAMAU());
                KICH_THUOC kich_thuoc = _IQLySanPhamService.findById_KichThuoc(chi_tiet_san_pham.getMAKICHTHUOC());
                GIOI_TINH gioi_tinh = _IQLySanPhamService.findById_GioiTinh(chi_tiet_san_pham.getMAGT());
                SAN_PHAM san_pham = _IQLySanPhamService.findById_SanPham(chi_tiet_san_pham.getMASP());
                HANG hang = _IQLySanPhamService.findById_Hang(san_pham.getMAHANG());
                Object[] row = {
                    _IQLySanPhamService.findById_LoaiHang(chi_tiet_san_pham.getMALOAIHANG()),
                    chi_tiet_san_pham.getMASP(),
                    _IQLySanPhamService.findById_SanPham(chi_tiet_san_pham.getMASP()),
                    "Hãng: " + hang.getTENHANG()
                    + ", Loại kính: " + loai_kinh.getLOAIKINH() + ", Màu sắc: " + mau_sac.getTENMAU()
                    + ", Kích thước: " + kich_thuoc.getKICHTHUOC() + ", Giới tính: " + gioi_tinh.getGIOITINH(),
                    chi_tiet_san_pham.getSOLUONG(),
                    _IQLySanPhamService.findById_DonViTinh(chi_tiet_san_pham.getMADV()),
                    chi_tiet_san_pham.getDONGIA(),
                    chi_tiet_san_pham.getTRANGTHAI() == 0 ? "Còn hàng" : "Hết hàng"
                };
                model.addRow(row);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Helper.DialogHelper.alert(this, "Lỗi truy vấn dữ liệu!");
        }

    }

    public void fillTableChiTietSanPhamByID() {
        DefaultTableModel model = (DefaultTableModel) tbl_CTSP.getModel();
        model.setRowCount(0);
        try {
            LOAI_HANG models = (LOAI_HANG) cbb_LoaiHang.getSelectedItem();
            List<CHI_TIET_SAN_PHAM> list = _IQLySanPhamService.selectByID_CTSP(models.getMALOAIHANG());
            for (CHI_TIET_SAN_PHAM chi_tiet_san_pham : list) {
                LOAI_KINH loai_kinh = _IQLySanPhamService.findById_LoaiKinh(chi_tiet_san_pham.getMALOAI());
                MAU_SAC mau_sac = _IQLySanPhamService.findById_MauSac(chi_tiet_san_pham.getMAMAU());
                KICH_THUOC kich_thuoc = _IQLySanPhamService.findById_KichThuoc(chi_tiet_san_pham.getMAKICHTHUOC());
                GIOI_TINH gioi_tinh = _IQLySanPhamService.findById_GioiTinh(chi_tiet_san_pham.getMAGT());
                SAN_PHAM san_pham = _IQLySanPhamService.findById_SanPham(chi_tiet_san_pham.getMASP());
                HANG hang = _IQLySanPhamService.findById_Hang(san_pham.getMAHANG());
                Object[] row = {
                    _IQLySanPhamService.findById_LoaiHang(chi_tiet_san_pham.getMALOAIHANG()),
                    chi_tiet_san_pham.getMASP(),
                    _IQLySanPhamService.findById_SanPham(chi_tiet_san_pham.getMASP()),
                    "Hãng: " + hang.getTENHANG()
                    + ", Loại kính: " + loai_kinh.getLOAIKINH() + ", Màu sắc: " + mau_sac.getTENMAU()
                    + ", Kích thước: " + kich_thuoc.getKICHTHUOC() + ", Giới tính: " + gioi_tinh.getGIOITINH(),
                    chi_tiet_san_pham.getSOLUONG(),
                    _IQLySanPhamService.findById_DonViTinh(chi_tiet_san_pham.getMADV()),
                    chi_tiet_san_pham.getDONGIA(),
                    chi_tiet_san_pham.getTRANGTHAI() == 0 ? "Còn hàng" : "Hết hàng"
                };
                model.addRow(row);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Helper.DialogHelper.alert(this, "Lỗi truy vấn dữ liệu!");
        }

    }

    public void fillTableProductVarintByID() {
//        DefaultTableModel model = (DefaultTableModel) tbl_ProductVariant.getModel();
//        model.setRowCount(0);
//        try {
//            Product models = (Product) cbb_Product1.getSelectedItem();
//            List<Product_variant> list = _IQLySanPhamService.selectByIDProduct_Product_variant(models.getID_Product());
//            for (Product_variant product_variant : list) {
//                Object[] row = {
//                    product_variant.getID_variant(),
//                    product_variant.getSKU_ID(),
//                    product_variant.getTRANGTHAI() == 0 ? "Còn hàng" : "Hết hàng"
//                };
//                model.addRow(row);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            Helper.DialogHelper.alert(this, "Lỗi truy vấn dữ liệu!");
//        }

    }

    public void setFormHang(HANG hang) {
        txt_IDHang.setText(hang.getMAHANG());
        txt_TenHang.setText(hang.getTENHANG());
        if (hang.getTRANGTHAI() == 0) {
            rdb_0_Hang.setSelected(true);
        } else {
            rdb_1_Hang.setSelected(true);
        }
    }

    public void setFormGioiTinh(GIOI_TINH gioi_tinh) {
        txt_IDGioiTinh.setText(gioi_tinh.getMAGT());
        txt_GioiTinh.setText(gioi_tinh.getGIOITINH());
        if (gioi_tinh.getTrangThai() == 0) {
            rdb_0_GioiTinh.setSelected(true);
        } else {
            rdb_1_GioiTinh.setSelected(true);
        }
    }

    public void setFormDonViTinh(DON_VI_TINH don_vi_tinh) {
        txt_IDDVT.setText(don_vi_tinh.getMADV());
        txt_DVT.setText(don_vi_tinh.getTEN());
        if (don_vi_tinh.getTRANGTHAI() == 0) {
            rdb_0_DVT.setSelected(true);
        } else {
            rdb_1_DVT.setSelected(true);
        }
    }

    public void setFormLoaiHang(LOAI_HANG loai_hang) {
        txt_IDLoaiHang.setText(loai_hang.getMALOAIHANG());
        txt_TenLoaiHang.setText(loai_hang.getTENLOAIHANG());
        if (loai_hang.getTRANGTHAI() == 0) {
            rdb_0_LoaiHang.setSelected(true);
        } else {
            rdb_1_LoaiHang.setSelected(true);
        }
    }

    public void setFormKieuDang(KIEU_DANG kieu_dang) {
        txt_IDKieuDang.setText(kieu_dang.getMAKIEUDANG());
        txt_NamesKieuDang.setText(kieu_dang.getKIEUDANG());
        if (kieu_dang.getTrangThai() == 0) {
            rdb_0_KieuDang.setSelected(true);
        } else {
            rdb_1_KieuDang.setSelected(true);
        }
    }

    public void setFormLoaiKinh(LOAI_KINH loai_kinh) {
        txt_IDLoaiKinh.setText(loai_kinh.getMALOAI());
        txt_NamesLoaiKinh.setText(loai_kinh.getLOAIKINH());
        if (loai_kinh.getTRANGTHAI() == 0) {
            rdb_0_LoaiKinh.setSelected(true);
        } else {
            rdb_1_LoaiKinh.setSelected(true);
        }
    }

    public void setFormKichThuoc(KICH_THUOC kich_thuoc) {
        txt_IDKichThuoc.setText(kich_thuoc.getMAKICHTHUOC());
        txt_KichThuoc.setText(kich_thuoc.getKICHTHUOC());
        if (kich_thuoc.getTRANGTHAI() == 0) {
            rdb_0_KichThuoc.setSelected(true);
        } else {
            rdb_1_KichThuoc.setSelected(true);
        }
    }

    public void setFormMauSac(MAU_SAC mau_sac) {
        txt_IDMauSac.setText(mau_sac.getMAMAU());
        txt_MauSac.setText(mau_sac.getTENMAU());
        if (mau_sac.getTRANGTHAI() == 0) {
            rdb_0_MauSac.setSelected(true);
        } else {
            rdb_1_MauSac.setSelected(true);
        }
    }

    public void setFormSanPham(SAN_PHAM san_pham) {
        HANG hang = _IQLySanPhamService.findById_Hang(san_pham.getMAHANG());
//        System.out.println(hang.getTENHANG());
        txt_IDSanPham.setText(san_pham.getMASP());
        txt_TenSP.setText(san_pham.getTENSP());
//        cbb_Hang.setSelectedItem(hang);
//        System.out.println(cbb_Hang.getItemCount());
//        System.out.println(cbb_Hang.getSelectedItem());
        for (int i = 0; i < cbb_Hang.getItemCount(); i++) {
//            String CbbHang = String.valueOf(cbb_Hang.getItemAt(i));
            if (hang.getTENHANG().equalsIgnoreCase(String.valueOf(cbb_Hang.getItemAt(i)))) {
                cbb_Hang.setSelectedIndex(i);
            }
        }

        if (san_pham.getTRANGTHAI() == 0) {
            rdb_0_SanPham.setSelected(true);
        } else {
            rdb_1_SanPham.setSelected(true);
        }
    }

    public void setFormChiTietSanPham(CHI_TIET_SAN_PHAM chi_tiet_san_pham) {
        txt_quantity.setText(String.valueOf(chi_tiet_san_pham.getSOLUONG()));
        txt_price.setText(String.valueOf(chi_tiet_san_pham.getDONGIA()));
        txt_barcode.setText(String.valueOf(chi_tiet_san_pham.getBARCODE()));
        _images = chi_tiet_san_pham.getIMAGES();
        LOAI_HANG loai_hang = _IQLySanPhamService.findById_LoaiHang(chi_tiet_san_pham.getMALOAIHANG());
        GIOI_TINH gioi_tinh = _IQLySanPhamService.findById_GioiTinh(chi_tiet_san_pham.getMAGT());
        DON_VI_TINH don_vi_tinh = _IQLySanPhamService.findById_DonViTinh(chi_tiet_san_pham.getMADV());
        KICH_THUOC kich_thuoc = _IQLySanPhamService.findById_KichThuoc(chi_tiet_san_pham.getMAKICHTHUOC());
        MAU_SAC mau_sac = _IQLySanPhamService.findById_MauSac(chi_tiet_san_pham.getMAMAU());
        LOAI_KINH loai_kinh = _IQLySanPhamService.findById_LoaiKinh(chi_tiet_san_pham.getMALOAI());
        KIEU_DANG kieu_dang = _IQLySanPhamService.findById_KieuDang(chi_tiet_san_pham.getMAKIEUDANG());

        String url = "images\\" + chi_tiet_san_pham.getIMAGES();
        ImageIcon imageIcon = new ImageIcon(url);
        Image img = imageIcon.getImage();
        lbl_images.setIcon(new ImageIcon(img.getScaledInstance(180, 220, 0)));
//        System.out.println(hang.getTENHANG());
//        txt_IDSanPham.setText(san_pham.getMASP());
//        txt_TenSP.setText(san_pham.getTENSP());
//        cbb_Hang.setSelectedItem(hang);
//        System.out.println(cbb_Hang.getItemCount());
//        System.out.println(cbb_Hang.getSelectedItem());
        for (int i = 0; i < cbb_LoaiHang2.getItemCount(); i++) {
//            String CbbHang = String.valueOf(cbb_Hang.getItemAt(i));
            if (loai_hang.getTENLOAIHANG().equalsIgnoreCase(String.valueOf(cbb_LoaiHang2.getItemAt(i)))) {
                cbb_LoaiHang2.setSelectedIndex(i);
            }
        }

        for (int i = 0; i < cbb_gioiTinh.getItemCount(); i++) {
            if (gioi_tinh.getGIOITINH().equalsIgnoreCase(String.valueOf(cbb_gioiTinh.getItemAt(i)))) {
                cbb_gioiTinh.setSelectedIndex(i);
            }
        }

        for (int i = 0; i < cbb_DVT.getItemCount(); i++) {
            if (don_vi_tinh.getTEN().equalsIgnoreCase(String.valueOf(cbb_DVT.getItemAt(i)))) {
                cbb_DVT.setSelectedIndex(i);
            }
        }

        for (int i = 0; i < cbb_KichThuoc.getItemCount(); i++) {
            if (kich_thuoc.getKICHTHUOC().equalsIgnoreCase(String.valueOf(cbb_KichThuoc.getItemAt(i)))) {
                cbb_KichThuoc.setSelectedIndex(i);
            }
        }

        for (int i = 0; i < cbb_MauSac.getItemCount(); i++) {
            if (mau_sac.getTENMAU().equalsIgnoreCase(String.valueOf(cbb_MauSac.getItemAt(i)))) {
                cbb_MauSac.setSelectedIndex(i);
            }
        }

        for (int i = 0; i < cbb_LoaiKinh.getItemCount(); i++) {
            if (loai_kinh.getLOAIKINH().equalsIgnoreCase(String.valueOf(cbb_LoaiKinh.getItemAt(i)))) {
                cbb_LoaiKinh.setSelectedIndex(i);
            }
        }

        for (int i = 0; i < cbb_KieuDang.getItemCount(); i++) {
            if (kieu_dang.getKIEUDANG().equalsIgnoreCase(String.valueOf(cbb_KieuDang.getItemAt(i)))) {
                cbb_KieuDang.setSelectedIndex(i);
            }
        }

        if (chi_tiet_san_pham.getTRANGTHAI() == 0) {
            rdb_0_SanPham.setSelected(true);
        } else {
            rdb_1_SanPham.setSelected(true);
        }
    }
    //
    //    public void setFormOptions(Options options) {
    //        txt_IDOptions.setText(options.getID_Options());
    //        txt_NamesOptions.setText(options.getNames());
    //        if (options.getTRANGTHAI() == 0) {
    //            rdb_0_Options.setSelected(true);
    //        } else {
    //            rdb_1_Options.setSelected(true);
    //        }
    //    }
    //
    //    public void setFormOptionValues(Options_values options_values) {
    //        txt_IDOptionValues.setText(options_values.getID_values());
    //        txt_NamesOptionValues.setText(options_values.getNames());
    //        if (options_values.getTRANGTHAI() == 0) {
    //            rdb_0_OptionValues.setSelected(true);
    //        } else {
    //            rdb_1_OptionValues.setSelected(true);
    //        }
    //    }
    //
    //    public void setFormProductVariant(Product_variant product_variant) {
    //        txt_IDVariant_ProductVariant.setText(product_variant.getID_variant());
    //        txt_SKUID_ProductVariant.setText(product_variant.getSKU_ID());
    //        if (product_variant.getTRANGTHAI() == 0) {
    //            rdb_0_ProductVariant.setSelected(true);
    //        } else {
    //            rdb_1_ProductVariant.setSelected(true);
    //        }
    //    }

    public HANG getFormHang() {
        HANG hang = new HANG();
        hang.setMAHANG(txt_IDHang.getText());
        hang.setTENHANG(txt_TenHang.getText());
        if (rdb_0_Hang.isSelected()) {
            hang.setTRANGTHAI(0);
        } else {
            hang.setTRANGTHAI(1);
        }
        return hang;
    }

    public GIOI_TINH getFormGioiTinh() {
        GIOI_TINH gioi_tinh = new GIOI_TINH();
        gioi_tinh.setMAGT(txt_IDGioiTinh.getText());
        gioi_tinh.setGIOITINH(txt_GioiTinh.getText());
        if (rdb_0_GioiTinh.isSelected()) {
            gioi_tinh.setTrangThai(0);
        } else {
            gioi_tinh.setTrangThai(1);
        }
        return gioi_tinh;
    }

    public DON_VI_TINH getFormDonViTinh() {
        DON_VI_TINH don_vi_tinh = new DON_VI_TINH();
        don_vi_tinh.setMADV(txt_IDDVT.getText());
        don_vi_tinh.setTEN(txt_DVT.getText());
        if (rdb_0_DVT.isSelected()) {
            don_vi_tinh.setTRANGTHAI(0);
        } else {
            don_vi_tinh.setTRANGTHAI(1);
        }
        return don_vi_tinh;
    }

    public LOAI_HANG getFormLoaiHang() {
        LOAI_HANG loai_hang = new LOAI_HANG();
        loai_hang.setMALOAIHANG(txt_IDLoaiHang.getText());
        loai_hang.setTENLOAIHANG(txt_TenLoaiHang.getText());
        if (rdb_0_LoaiHang.isSelected()) {
            loai_hang.setTRANGTHAI(0);
        } else {
            loai_hang.setTRANGTHAI(1);
        }
        return loai_hang;
    }

    public KIEU_DANG getFormKieuDang() {
        KIEU_DANG kieu_dang = new KIEU_DANG();
        kieu_dang.setMAKIEUDANG(txt_IDKieuDang.getText());
        kieu_dang.setKIEUDANG(txt_NamesKieuDang.getText());
        if (rdb_0_KieuDang.isSelected()) {
            kieu_dang.setTrangThai(0);
        } else {
            kieu_dang.setTrangThai(1);
        }
        return kieu_dang;
    }

    public LOAI_KINH getFormLoaiKinh() {
        LOAI_KINH loai_kinh = new LOAI_KINH();
        loai_kinh.setMALOAI(txt_IDLoaiKinh.getText());
        loai_kinh.setLOAIKINH(txt_NamesLoaiKinh.getText());
        if (rdb_0_LoaiKinh.isSelected()) {
            loai_kinh.setTRANGTHAI(0);
        } else {
            loai_kinh.setTRANGTHAI(1);
        }
        return loai_kinh;
    }

    public KICH_THUOC getFormKichThuoc() {
        KICH_THUOC kich_thuoc = new KICH_THUOC();
        kich_thuoc.setMAKICHTHUOC(txt_IDKichThuoc.getText());
        kich_thuoc.setKICHTHUOC(txt_KichThuoc.getText());
        if (rdb_0_KichThuoc.isSelected()) {
            kich_thuoc.setTRANGTHAI(0);
        } else {
            kich_thuoc.setTRANGTHAI(1);
        }
        return kich_thuoc;
    }

    public MAU_SAC getFormMauSac() {
        MAU_SAC mau_sac = new MAU_SAC();
        mau_sac.setMAMAU(txt_IDMauSac.getText());
        mau_sac.setTENMAU(txt_MauSac.getText());
        if (rdb_0_MauSac.isSelected()) {
            mau_sac.setTRANGTHAI(0);
        } else {
            mau_sac.setTRANGTHAI(1);
        }
        return mau_sac;
    }

    public SAN_PHAM getFormSanPham() {
        HANG hang = _IQLySanPhamService.findByNames_Hang(String.valueOf(cbb_Hang.getSelectedItem()));
        SAN_PHAM san_pham = new SAN_PHAM();
        san_pham.setMASP(txt_IDSanPham.getText());
        san_pham.setTENSP(txt_TenSP.getText());
        san_pham.setMAHANG(hang.getMAHANG());
        if (rdb_0_SanPham.isSelected()) {
            san_pham.setTRANGTHAI(0);
        } else {
            san_pham.setTRANGTHAI(1);
        }
        return san_pham;
    }

    public CHI_TIET_SAN_PHAM getFormChiTietSanPham() {

        CHI_TIET_SAN_PHAM ctsp = new CHI_TIET_SAN_PHAM();
        KIEU_DANG kieu_dang = _IQLySanPhamService.findByNames_KieuDang(String.valueOf(cbb_KieuDang.getSelectedItem()));
        DON_VI_TINH don_vi_tinh = _IQLySanPhamService.findByNames_DonViTinh(String.valueOf(cbb_DVT.getSelectedItem()));
        KICH_THUOC kich_thuoc = _IQLySanPhamService.findByNames_KichThuoc(String.valueOf(cbb_KichThuoc.getSelectedItem()));
        MAU_SAC mau_sac = _IQLySanPhamService.findByNames_MauSac(String.valueOf(cbb_MauSac.getSelectedItem()));
        LOAI_KINH loai_kinh = _IQLySanPhamService.findByNames_LoaiKinh(String.valueOf(cbb_LoaiKinh.getSelectedItem()));
        GIOI_TINH gioi_tinh = _IQLySanPhamService.findByNames_GioiTinh(String.valueOf(cbb_gioiTinh.getSelectedItem()));
        LOAI_HANG loai_hang = _IQLySanPhamService.findByNames_LoaiHang(String.valueOf(cbb_LoaiHang2.getSelectedItem()));

        ctsp.setMAKIEUDANG(kieu_dang.getMAKIEUDANG());
        ctsp.setMADV(don_vi_tinh.getMADV());
        ctsp.setMAKICHTHUOC(kich_thuoc.getMAKICHTHUOC());
        ctsp.setMAMAU(mau_sac.getMAMAU());
        ctsp.setMALOAI(loai_kinh.getMALOAI());
        ctsp.setMAGT(gioi_tinh.getMAGT());
        ctsp.setMASP(txt_IDSanPham.getText());
        ctsp.setMALOAIHANG(loai_hang.getMALOAIHANG());
        ctsp.setBARCODE(txt_barcode.getText());
        ctsp.setSOLUONG(Integer.parseInt(txt_quantity.getText()));
        ctsp.setDONGIA(Double.parseDouble(txt_price.getText()));
        ctsp.setIMAGES(_images);
        if (rdb_0_SanPham.isSelected()) {
            ctsp.setTRANGTHAI(0);
        } else {
            ctsp.setTRANGTHAI(1);
        }
        return ctsp;
    }

//    public Options getFormOptions() {
//        Options options = new Options();
//        options.setID_Options(txt_IDOptions.getText());
//        options.setNames(txt_NamesOptions.getText());
//        if (rdb_0_Options.isSelected()) {
//            options.setTRANGTHAI(0);
//        } else {
//            options.setTRANGTHAI(1);
//        }
//        return options;
//    }
//
//    public Options_values getFormOptionValues() {
//        Options options = (Options) cbb_option.getSelectedItem();
//        Options_values options_values = new Options_values();
//        options_values.setID_Options(options.getID_Options());
//        options_values.setID_values(txt_IDOptionValues.getText());
//        options_values.setNames(txt_NamesOptionValues.getText());
//        if (rdb_0_OptionValues.isSelected()) {
//            options_values.setTRANGTHAI(0);
//        } else {
//            options_values.setTRANGTHAI(1);
//        }
//        return options_values;
//    }
//
//    public Product_variant getFormProductVariant() {
//        Product product = (Product) cbb_Product1.getSelectedItem();
//        Product_variant product_variant = new Product_variant();
//        product_variant.setID_Product(product.getID_Product());
//        product_variant.setID_variant(txt_IDVariant_ProductVariant.getText());
//        product_variant.setSKU_ID(txt_SKUID_ProductVariant.getText());
//        if (rdb_0_ProductVariant.isSelected()) {
//            product_variant.setTRANGTHAI(0);
//        } else {
//            product_variant.setTRANGTHAI(1);
//        }
//        return product_variant;
//    }
//
//    public Product_options getFormProductOptions() {
//        Product product = (Product) cbb_Product.getSelectedItem();
//        Product_options product_options = new Product_options();
//        List<Object> list = new ArrayList<>();
//        for (int i = 0; i < _ListcbbTT.size(); i++) {
//            Options out = _IQLySanPhamService.findByNamesOptions(_ListcbbTT.get(i));
//            System.out.println(out.getID_Options());
//            product_options.setID_Product(product.getID_Product());
//            product_options.setID_Options(out.getID_Options());
//            if (rdb_0_variantValues.isSelected()) {
//                product_options.setTRANGTHAI(0);
//            } else {
//                product_options.setTRANGTHAI(1);
//            }
//            list.add(product_options);
//        }
//
//        return product_options;
//    }
//
//    public Variant_values getFormVariantValues() {
//        Product product = (Product) cbb_Product.getSelectedItem();
//        Product_variant product_variant = (Product_variant) cbb_IDVariant.getSelectedItem();
//        Variant_values variant_values = new Variant_values();
//
//        for (int i = 0; i < _ListcbbTT.size(); i++) {
//            Options options = _IQLySanPhamService.findByNamesOptions(_ListcbbTT.get(i));
//            Options_values options_values = _IQLySanPhamService.findByNamesOptions_values(_ListcbbGTTT.get(i));
//            variant_values.setID_Product(product.getID_Product());
//            variant_values.setID_variant(product_variant.getID_variant());
//            variant_values.setID_Options(options.getID_Options());
//            variant_values.setID_values(options_values.getID_values());
//            variant_values.setQuantity(Integer.parseInt(txt_Quarity.getText()));
//            variant_values.setPrice(Double.parseDouble(txt_Price.getText()));
//            variant_values.setBarcode(txt_barcode.getText());
//            variant_values.setImages(_images);
//            if (rdb_0_variantValues.isSelected()) {
//                variant_values.setTRANGTHAI(0);
//            } else {
//                variant_values.setTRANGTHAI(1);
//            }
//        }
//
//        return variant_values;
//    }
//
    public void editHang() {
        try {
            String IDHang = String.valueOf(tbl_Hang.getValueAt(this._rowHang, 0));
            HANG model = _IQLySanPhamService.findById_Hang(IDHang);
            if (model != null) {
                this.setFormHang(model);
//                this.setStatus(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
            DialogHelper.alert(this, "Lỗi truy vấn dữ liệu!");
        }
    }

    public void editGioiTinh() {
        try {
            String IDGioiTinh = String.valueOf(tbl_GioiTinh.getValueAt(this._rowGioiTinh, 0));
            GIOI_TINH model = _IQLySanPhamService.findById_GioiTinh(IDGioiTinh);
            if (model != null) {
                this.setFormGioiTinh(model);
//                this.setStatus(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
            DialogHelper.alert(this, "Lỗi truy vấn dữ liệu!");
        }
    }

    public void editDonViTinh() {
        try {
            String IDDVT = String.valueOf(tbl_DonViTinh.getValueAt(this._rowDVT, 0));
            DON_VI_TINH model = _IQLySanPhamService.findById_DonViTinh(IDDVT);
            if (model != null) {
                this.setFormDonViTinh(model);
//                this.setStatus(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
            DialogHelper.alert(this, "Lỗi truy vấn dữ liệu!");
        }
    }

    public void editLoaiHang() {
        try {
            String IDLoaiHang = String.valueOf(tbl_LoaiHang.getValueAt(this._rowLoaiHang, 0));
            LOAI_HANG model = _IQLySanPhamService.findById_LoaiHang(IDLoaiHang);
            if (model != null) {
                this.setFormLoaiHang(model);
//                this.setStatus(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
            DialogHelper.alert(this, "Lỗi truy vấn dữ liệu!");
        }
    }

    public void editKieuDang() {
        try {
            String IDKieuDang = String.valueOf(tbl_KieuDang.getValueAt(this._rowKieuDang, 0));
            KIEU_DANG model = _IQLySanPhamService.findById_KieuDang(IDKieuDang);
            if (model != null) {
                this.setFormKieuDang(model);
//                this.setStatus(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
            DialogHelper.alert(this, "Lỗi truy vấn dữ liệu!");
        }
    }

    public void editLoaiKinh() {
        try {
            String IDLoaiKinh = String.valueOf(tbl_LoaiKinh.getValueAt(this._rowLoaiKinh, 0));
            LOAI_KINH model = _IQLySanPhamService.findById_LoaiKinh(IDLoaiKinh);
            if (model != null) {
                this.setFormLoaiKinh(model);
//                this.setStatus(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
            DialogHelper.alert(this, "Lỗi truy vấn dữ liệu!");
        }
    }

    public void editKichThuoc() {
        try {
            String IDKichThuoc = String.valueOf(tbl_KichThuoc.getValueAt(this._rowKichThuoc, 0));
            KICH_THUOC model = _IQLySanPhamService.findById_KichThuoc(IDKichThuoc);
            if (model != null) {
                this.setFormKichThuoc(model);
//                this.setStatus(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
            DialogHelper.alert(this, "Lỗi truy vấn dữ liệu!");
        }
    }

    public void editMauSac() {
        try {
            String IDMauSac = String.valueOf(tbl_MauSac.getValueAt(this._rowMauSac, 0));
            MAU_SAC model = _IQLySanPhamService.findById_MauSac(IDMauSac);
            if (model != null) {
                this.setFormMauSac(model);
//                this.setStatus(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
            DialogHelper.alert(this, "Lỗi truy vấn dữ liệu!");
        }
    }

    public void editSanPham() {
        try {
            this._rowSanPham = tbl_CTSP.getSelectedRow();
            String IDSanPham = String.valueOf(tbl_CTSP.getValueAt(this._rowSanPham, 1));
            SAN_PHAM model = _IQLySanPhamService.findById_SanPham(IDSanPham);
            if (model != null) {
                this.setFormSanPham(model);
//                this.setStatus(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
            DialogHelper.alert(this, "Lỗi truy vấn dữ liệu!");
        }
    }

    public void editChiTietSanPham() {
        try {
            this._rowSanPham = tbl_CTSP.getSelectedRow();
            String IDSanPham = String.valueOf(tbl_CTSP.getValueAt(this._rowSanPham, 1));
            CHI_TIET_SAN_PHAM model = _IQLySanPhamService.findById_CTSP(IDSanPham);
            if (model != null) {
                this.setFormChiTietSanPham(model);
//                this.setStatus(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
            DialogHelper.alert(this, "Lỗi truy vấn dữ liệu!");
        }
    }
//
//    public void editOptions() {
//        try {
//            String idOptions = String.valueOf(tbl_option.getValueAt(this._rowOptions, 0));
//            Options model = _IQLySanPhamService.findByIdOptions(idOptions);
//            if (model != null) {
//                this.setFormOptions(model);
////                this.setStatus(false);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            DialogHelper.alert(this, "Lỗi truy vấn dữ liệu!");
//        }
//    }
//
//    public void editOptionValues() {
//        try {
//            String idOptionValues = String.valueOf(tbl_OptionValues.getValueAt(this._rowOptionValues, 0));
//            Options_values model = _IQLySanPhamService.findByIdOptions_Options_values(idOptionValues);
//            if (model != null) {
//                this.setFormOptionValues(model);
////                this.setStatus(false);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            DialogHelper.alert(this, "Lỗi truy vấn dữ liệu!");
//        }
//    }
//
//    public void editProductVariant() {
//        try {
//            String idVariant = String.valueOf(tbl_ProductVariant.getValueAt(this._rowProductVariant, 0));
//            Product_variant model = _IQLySanPhamService.findByIdProduct_variant(idVariant);
//            if (model != null) {
//                this.setFormProductVariant(model);
////                this.setStatus(false);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            DialogHelper.alert(this, "Lỗi truy vấn dữ liệu!");
//        }
//    }

    public void insertHang() {
        HANG hang = getFormHang();
        try {
            _IQLySanPhamService.insert_Hang(hang);
            this.fillTableHang();
            this.fillComboBoxHang();
//            this.viewSoTrang();
            this.clearFormHang();
//            tabs.setSelectedIndex(1);
            Helper.DialogHelper.alert(this, "Thêm mới thành công!");
            _rowHang = -1;
        } catch (Exception e) {
            e.printStackTrace();
            Helper.DialogHelper.alert(this, "Thêm mới thất bại!");
        }
    }

    public void insertGioiTinh() {
        GIOI_TINH gioi_tinh = getFormGioiTinh();
        try {
            _IQLySanPhamService.insert_GioiTinh(gioi_tinh);
            this.fillTableGioiTinh();
            this.fillComboBoxGioiTinh();
//            this.viewSoTrang();
            this.clearFormGioiTinh();
//            tabs.setSelectedIndex(1);
            Helper.DialogHelper.alert(this, "Thêm mới thành công!");
            _rowGioiTinh = -1;
        } catch (Exception e) {
            e.printStackTrace();
            Helper.DialogHelper.alert(this, "Thêm mới thất bại!");
        }
    }

    public void insertDonViTinh() {
        DON_VI_TINH don_vi_tinh = getFormDonViTinh();
        try {
            _IQLySanPhamService.insert_DonViTinh(don_vi_tinh);
            this.fillTableDonViTinh();
            this.fillComboBoxDVT();
//            this.viewSoTrang();
            this.clearFormDonViTinh();
//            tabs.setSelectedIndex(1);
            Helper.DialogHelper.alert(this, "Thêm mới thành công!");
            _rowDVT = -1;
        } catch (Exception e) {
            e.printStackTrace();
            Helper.DialogHelper.alert(this, "Thêm mới thất bại!");
        }
    }

    public void insertLoaiHang() {
        LOAI_HANG loai_hang = getFormLoaiHang();
        try {
            _IQLySanPhamService.insert_LoaiHang(loai_hang);
            this.fillTableLoaiHang();
            this.fillComboBoxLoaiHang();
//            this.viewSoTrang();
            this.clearFormLoaiHang();
//            tabs.setSelectedIndex(1);
            Helper.DialogHelper.alert(this, "Thêm mới thành công!");
            _rowLoaiHang = -1;
        } catch (Exception e) {
            e.printStackTrace();
            Helper.DialogHelper.alert(this, "Thêm mới thất bại!");
        }
    }

    public void insertKieuDang() {
        KIEU_DANG kieu_dang = getFormKieuDang();
        try {
            _IQLySanPhamService.insert_KieuDang(kieu_dang);
            this.fillTableKieuDang();
            this.fillComboBoxKieuDang();
//            this.viewSoTrang();
            this.clearFormKieuDang();
//            tabs.setSelectedIndex(1);
            Helper.DialogHelper.alert(this, "Thêm mới thành công!");
            _rowKieuDang = -1;
        } catch (Exception e) {
            e.printStackTrace();
            Helper.DialogHelper.alert(this, "Thêm mới thất bại!");
        }
    }

    public void insertLoaiKinh() {
        LOAI_KINH loai_kinh = getFormLoaiKinh();
        try {
            _IQLySanPhamService.insert_LoaiKinh(loai_kinh);
            this.fillTableLoaiKinh();
            this.fillComboBoxLoaiKinh();
//            this.viewSoTrang();
            this.clearFormLoaiKinh();
//            tabs.setSelectedIndex(1);
            Helper.DialogHelper.alert(this, "Thêm mới thành công!");
            _rowLoaiKinh = -1;
        } catch (Exception e) {
            e.printStackTrace();
            Helper.DialogHelper.alert(this, "Thêm mới thất bại!");
        }
    }

    public void insertKichThuoc() {
        KICH_THUOC kich_thuoc = getFormKichThuoc();
        try {
            _IQLySanPhamService.insert_KichThuoc(kich_thuoc);
            this.fillTableKichThuoc();
            this.fillComboBoxKichThuoc();
//            this.viewSoTrang();
            this.clearFormKichThuoc();
//            tabs.setSelectedIndex(1);
            Helper.DialogHelper.alert(this, "Thêm mới thành công!");
            _rowKichThuoc = -1;
        } catch (Exception e) {
            e.printStackTrace();
            Helper.DialogHelper.alert(this, "Thêm mới thất bại!");
        }
    }

    public void insertMauSac() {
        MAU_SAC mau_sac = getFormMauSac();
        try {
            _IQLySanPhamService.insert_MauSac(mau_sac);
            this.fillTableMauSac();
            this.fillComboBoxMauSac();
//            this.viewSoTrang();
            this.clearFormMauSac();
//            tabs.setSelectedIndex(1);
            Helper.DialogHelper.alert(this, "Thêm mới thành công!");
            _rowKichThuoc = -1;
        } catch (Exception e) {
            e.printStackTrace();
            Helper.DialogHelper.alert(this, "Thêm mới thất bại!");
        }
    }

    public void insertSanPham() {
        SAN_PHAM san_pham = getFormSanPham();
//        try {
        _IQLySanPhamService.insert_SanPham(san_pham);
//            this.fillTableMauSac();
//            this.fillComboBoxMauSac();
//            this.viewSoTrang();
//            this.clearFormMauSac();
//            tabs.setSelectedIndex(1);
//            Helper.DialogHelper.alert(this, "Thêm mới thành công!");
        _rowSanPham = -1;
//        } catch (Exception e) {
//            e.printStackTrace();
//            Helper.DialogHelper.alert(this, "Thêm mới thất bại!");
//        }
    }

    public void insertChiTietSanPham() {
        CHI_TIET_SAN_PHAM chi_tiet_san_pham = getFormChiTietSanPham();
//        try {
        _IQLySanPhamService.insert_CTSP(chi_tiet_san_pham);
//            this.fillTableMauSac();
//            this.fillComboBoxMauSac();
//            this.viewSoTrang();
//            this.clearFormMauSac();
//            tabs.setSelectedIndex(1);
//            Helper.DialogHelper.alert(this, "Thêm mới thành công!");
        _rowSanPham = -1;
//        } catch (Exception e) {
//            e.printStackTrace();
//            Helper.DialogHelper.alert(this, "Thêm mới thất bại!");
//        }
    }

//    public void insertOptions() {
//        Options options = getFormOptions();
//        try {
//            _IQLySanPhamService.insertOptions(options);
//            this.fillTableOption();
//            this.fillComboBoxOption();
////            this.viewSoTrang();
//            this.clearFormOptions();
////            tabs.setSelectedIndex(1);
//            Helper.DialogHelper.alert(this, "Thêm mới thành công!");
//        } catch (Exception e) {
//            e.printStackTrace();
//            Helper.DialogHelper.alert(this, "Thêm mới thất bại!");
//        }
//    }
//
//    public void insertOptionValues() {
//        Options_values options_values = getFormOptionValues();
//        try {
//            _IQLySanPhamService.insertOptions_values(options_values);
//            this.fillTableOptionValuesByID();
////            this.fillComboBoxOption();
////            this.viewSoTrang();
//            this.clearFormOptionValues();
////            tabs.setSelectedIndex(1);
//            Helper.DialogHelper.alert(this, "Thêm mới thành công!");
//        } catch (Exception e) {
//            e.printStackTrace();
//            Helper.DialogHelper.alert(this, "Thêm mới thất bại!");
//        }
//    }
//
//    public void insertProductVariant() {
//        Product_variant product_variant = getFormProductVariant();
//        try {
//            _IQLySanPhamService.insertProduct_variant(product_variant);
//            this.fillTableProductVarintByID();
//            this.fillComboBoxAllProductVarint();
////            this.fillTableVariantValuesByID();
////            this.fillComboBoxIDVariant_ProductVarint();
////            this.viewSoTrang();
//            this.clearFormProductVariant();
//
//            Helper.DialogHelper.alert(this, "Thêm mới thành công!");
//        } catch (Exception e) {
//            e.printStackTrace();
//            Helper.DialogHelper.alert(this, "Thêm mới thất bại!");
//        }
//    }
//
//    public void insertProductOptions() {
////        Product_options product_options = getFormProductOptions();
//        Product product = (Product) cbb_Product.getSelectedItem();
//        Product_options product_options = new Product_options();
//        try {
//            for (int i = 0; i < _ListcbbTT.size(); i++) {
//                Options out = _IQLySanPhamService.findByNamesOptions(_ListcbbTT.get(i));
//                product_options.setID_Product(product.getID_Product());
//                product_options.setID_Options(out.getID_Options());
//                if (rdb_0_variantValues.isSelected()) {
//                    product_options.setTRANGTHAI(0);
//                } else {
//                    product_options.setTRANGTHAI(1);
//                }
//
////            for (int i = 0; i < _cbbTT.size(); i++) {
//                _IQLySanPhamService.insertProduct_Options(product_options);
////            }
////            this.fillTableProductVarintByID();
////            this.fillTableVariantValuesByID();
////            this.fillComboBoxIDVariant_ProductVarint();
//////            this.viewSoTrang();
////            this.clearFormProductVariant();
////            tabs.setSelectedIndex(1);
//
//            }
//            Helper.DialogHelper.alert(this, "Thêm mới thành công!");
//        } catch (Exception e) {
//            e.printStackTrace();
//            Helper.DialogHelper.alert(this, "Thêm mới thất bại!");
//        }
//    }
//
//    public void insertVarianValues() {
////        Variant_values variant_values = getFormVariantValues();
//        try {
//            Product product = (Product) cbb_Product.getSelectedItem();
//            Product_variant product_variant = (Product_variant) cbb_IDVariant.getSelectedItem();
//            Variant_values variant_values = new Variant_values();
//
//            for (int i = 0; i < _ListcbbTT.size(); i++) {
//                Options options = _IQLySanPhamService.findByNamesOptions(_ListcbbTT.get(i));
//                Options_values options_values = _IQLySanPhamService.findByNamesOptions_values(_ListcbbGTTT.get(i));
//                variant_values.setID_Product(product.getID_Product());
//                variant_values.setID_variant(product_variant.getID_variant());
//                variant_values.setID_Options(options.getID_Options());
//                variant_values.setID_values(options_values.getID_values());
//                variant_values.setQuantity(Integer.parseInt(txt_Quarity.getText()));
//                variant_values.setPrice(Double.parseDouble(txt_Price.getText()));
//                variant_values.setBarcode(txt_barcode.getText());
//                variant_values.setImages(_images);
//                if (rdb_0_variantValues.isSelected()) {
//                    variant_values.setTRANGTHAI(0);
//                } else {
//                    variant_values.setTRANGTHAI(1);
//                }
//                _IQLySanPhamService.insertVariantValues(variant_values);
//            }
//            for (int i = 0; i < _cbbTT.size(); i++) {
//            }
//            this.fillTableProductVarintByID();
//            this.fillTableVariantValuesByID();
//            this.fillComboBoxIDVariant_ProductVarint();
////            this.viewSoTrang();
//            this.clearFormProductVariant();
//            tabs.setSelectedIndex(1);
//            Helper.DialogHelper.alert(this, "Thêm mới thành công!");
//        } catch (Exception e) {
//            e.printStackTrace();
//            Helper.DialogHelper.alert(this, "Thêm mới thất bại!");
//        }
//    }
//
    public void updateHang() {

        HANG hang = getFormHang();
        if (Helper.DialogHelper.confirm(this, "Bạn thực sự muốn sửa Hãng này?")) {
            try {
                _IQLySanPhamService.update_Hang(hang);
//            this.fillTable(_trang);
//            this.viewSoTrang();
//            tabs.setSelectedIndex(1);
                this.fillTableHang();
                this.fillComboBoxHang();
                Helper.DialogHelper.alert(this, "Cập nhật thành công!");
                _rowHang = -1;
            } catch (Exception e) {
                e.printStackTrace();
                Helper.DialogHelper.alert(this, "Cập nhật thất bại!");
            }
        }
    }

    public void updateGioiTinh() {

        GIOI_TINH gioi_tinh = getFormGioiTinh();
        if (Helper.DialogHelper.confirm(this, "Bạn thực sự muốn sửa Giới tính này?")) {
            try {
                _IQLySanPhamService.update_GioiTinh(gioi_tinh);
//            this.fillTable(_trang);
//            this.viewSoTrang();
//            tabs.setSelectedIndex(1);
                this.fillTableGioiTinh();
                this.fillComboBoxGioiTinh();
                Helper.DialogHelper.alert(this, "Cập nhật thành công!");
                _rowGioiTinh = -1;
            } catch (Exception e) {
                e.printStackTrace();
                Helper.DialogHelper.alert(this, "Cập nhật thất bại!");
            }
        }
    }

    public void updateDonViTinh() {

        DON_VI_TINH don_vi_tinh = getFormDonViTinh();
        if (Helper.DialogHelper.confirm(this, "Bạn thực sự muốn sửa Đơn vị tính này?")) {
            try {
                _IQLySanPhamService.update_DonViTinh(don_vi_tinh);
//            this.fillTable(_trang);
//            this.viewSoTrang();
//            tabs.setSelectedIndex(1);
                this.fillTableDonViTinh();
                this.fillComboBoxDVT();
                Helper.DialogHelper.alert(this, "Cập nhật thành công!");
                _rowDVT = -1;
            } catch (Exception e) {
                e.printStackTrace();
                Helper.DialogHelper.alert(this, "Cập nhật thất bại!");
            }
        }
    }

    public void updateLoaiHang() {

        LOAI_HANG loai_hang = getFormLoaiHang();
        if (Helper.DialogHelper.confirm(this, "Bạn thực sự muốn sửa Loại hàng này?")) {
            try {
                _IQLySanPhamService.update_LoaiHang(loai_hang);
//            this.fillTable(_trang);
//            this.viewSoTrang();
//            tabs.setSelectedIndex(1);
                this.fillTableLoaiHang();
                this.fillComboBoxLoaiHang();
                Helper.DialogHelper.alert(this, "Cập nhật thành công!");
                _rowLoaiHang = -1;
            } catch (Exception e) {
                e.printStackTrace();
                Helper.DialogHelper.alert(this, "Cập nhật thất bại!");
            }
        }
    }

    public void updateKieuDang() {

        KIEU_DANG kieu_dang = getFormKieuDang();
        if (Helper.DialogHelper.confirm(this, "Bạn thực sự muốn sửa Kiểu dáng này?")) {
            try {
                _IQLySanPhamService.update_KieuDang(kieu_dang);
//            this.fillTable(_trang);
//            this.viewSoTrang();
//            tabs.setSelectedIndex(1);
                this.fillTableKieuDang();
                this.fillComboBoxKieuDang();
                Helper.DialogHelper.alert(this, "Cập nhật thành công!");
                _rowKieuDang = -1;
            } catch (Exception e) {
                e.printStackTrace();
                Helper.DialogHelper.alert(this, "Cập nhật thất bại!");
            }
        }
    }

    public void updateLoaiKinh() {

        LOAI_KINH loai_kinh = getFormLoaiKinh();
        if (Helper.DialogHelper.confirm(this, "Bạn thực sự muốn sửa Loại kính này?")) {
            try {
                _IQLySanPhamService.update_LoaiKinh(loai_kinh);
//            this.fillTable(_trang);
//            this.viewSoTrang();
//            tabs.setSelectedIndex(1);
                this.fillTableLoaiKinh();
                this.fillComboBoxLoaiKinh();
                Helper.DialogHelper.alert(this, "Cập nhật thành công!");
                _rowLoaiKinh = -1;
            } catch (Exception e) {
                e.printStackTrace();
                Helper.DialogHelper.alert(this, "Cập nhật thất bại!");
            }
        }
    }

    public void updateKichThuoc() {

        KICH_THUOC kich_thuoc = getFormKichThuoc();
        if (Helper.DialogHelper.confirm(this, "Bạn thực sự muốn sửa Kích thước này?")) {
            try {
                _IQLySanPhamService.update_KichThuoc(kich_thuoc);
//            this.fillTable(_trang);
//            this.viewSoTrang();
//            tabs.setSelectedIndex(1);
                this.fillTableKichThuoc();
                this.fillComboBoxKichThuoc();
                Helper.DialogHelper.alert(this, "Cập nhật thành công!");
                _rowKichThuoc = -1;
            } catch (Exception e) {
                e.printStackTrace();
                Helper.DialogHelper.alert(this, "Cập nhật thất bại!");
            }
        }
    }

    public void updateMauSac() {

        MAU_SAC mau_sac = getFormMauSac();
        if (Helper.DialogHelper.confirm(this, "Bạn thực sự muốn sửa Màu sắc này?")) {
            try {
                _IQLySanPhamService.update_MauSac(mau_sac);
//            this.fillTable(_trang);
//            this.viewSoTrang();
//            tabs.setSelectedIndex(1);
                this.fillTableMauSac();
                this.fillComboBoxMauSac();
                Helper.DialogHelper.alert(this, "Cập nhật thành công!");
                _rowKichThuoc = -1;
            } catch (Exception e) {
                e.printStackTrace();
                Helper.DialogHelper.alert(this, "Cập nhật thất bại!");
            }
        }
    }

    public void updateSanPham() {

        SAN_PHAM san_pham = getFormSanPham();
//        if (Helper.DialogHelper.confirm(this, "Bạn thực sự muốn sửa Màu sắc này?")) {
//            try {
        _IQLySanPhamService.update_SanPham(san_pham);
//            this.fillTable(_trang);
//            this.viewSoTrang();
//            tabs.setSelectedIndex(1);
//                this.fillTableMauSac();
//                this.fillComboBoxMauSac();
//                Helper.DialogHelper.alert(this, "Cập nhật thành công!");
        _rowKichThuoc = -1;
//            } catch (Exception e) {
//                e.printStackTrace();
//                Helper.DialogHelper.alert(this, "Cập nhật thất bại!");
//            }
//        }
    }

    public void updateChiTietSanPham() {

        CHI_TIET_SAN_PHAM chi_tiet_san_pham = getFormChiTietSanPham();
//        if (Helper.DialogHelper.confirm(this, "Bạn thực sự muốn sửa Màu sắc này?")) {
//            try {
        _IQLySanPhamService.update_CTSP(chi_tiet_san_pham);
//            this.fillTable(_trang);
//            this.viewSoTrang();
//            tabs.setSelectedIndex(1);
//                this.fillTableMauSac();
//                this.fillComboBoxMauSac();
//                Helper.DialogHelper.alert(this, "Cập nhật thành công!");
        _rowKichThuoc = -1;
//            } catch (Exception e) {
//                e.printStackTrace();
//                Helper.DialogHelper.alert(this, "Cập nhật thất bại!");
//            }
//        }
    }
//
//    public void updateOptions() {
//
//        Options options = getFormOptions();
//        if (Helper.DialogHelper.confirm(this, "Bạn thực sự muốn sửa thuộc tính này?")) {
//            try {
//                _IQLySanPhamService.updateOptions(options);
////            this.fillTable(_trang);
////            this.viewSoTrang();
////            tabs.setSelectedIndex(1);
//                this.fillTableOption();
//                this.fillComboBoxOption();
//                Helper.DialogHelper.alert(this, "Cập nhật thành công!");
//            } catch (Exception e) {
//                e.printStackTrace();
//                Helper.DialogHelper.alert(this, "Cập nhật thất bại!");
//            }
//        }
//    }
//
//    public void updateOptionValues() {
//
//        Options_values options_values = getFormOptionValues();
//        if (Helper.DialogHelper.confirm(this, "Bạn thực sự muốn sửa giá trị này?")) {
//            try {
//                _IQLySanPhamService.updateOptions_values(options_values);
////            this.fillTable(_trang);
////            this.viewSoTrang();
////            tabs.setSelectedIndex(1);
//                this.fillTableOptionValuesByID();
////                this.fillComboBoxOption();
//                Helper.DialogHelper.alert(this, "Cập nhật thành công!");
//            } catch (Exception e) {
//                e.printStackTrace();
//                Helper.DialogHelper.alert(this, "Cập nhật thất bại!");
//            }
//        }
//    }
//
//    public void updateProductVariant() {
//
//        Product_variant product_variant = getFormProductVariant();
//        if (Helper.DialogHelper.confirm(this, "Bạn thực sự muốn sửa giá trị này?")) {
//            try {
//                _IQLySanPhamService.updateProduct_variant(product_variant);
////            this.fillTable(_trang);
////            this.viewSoTrang();
////            tabs.setSelectedIndex(1);
//                this.fillTableProductVarintByID();
//                this.fillComboBoxAllProductVarint();
////                this.fillTableVariantValuesByID();
////                this.fillComboBoxIDVariant_ProductVarint();
//                Helper.DialogHelper.alert(this, "Cập nhật thành công!");
//            } catch (Exception e) {
//                e.printStackTrace();
//                Helper.DialogHelper.alert(this, "Cập nhật thất bại!");
//            }
//        }
//    }
//

    public void deleteHang() {
        if (Helper.DialogHelper.confirm(this, "Bạn thực sự muốn xóa Hãng này?")) {
            try {
                _IQLySanPhamService.delete_Hang(txt_IDHang.getText());   //xóa nhân viên theo maNV
//                this.fillTable(_trang);//điền tt mới vào bảng
//                this.viewSoTrang();
                this.clearFormHang();//xóa trắng form và chỉnh lại status
                this.fillTableHang();
                this.fillComboBoxHang();
                Helper.DialogHelper.alert(this, "Xóa thành công!");
                _rowHang = -1;
//                tabs.setSelectedIndex(1);
            } catch (Exception e) {
                e.printStackTrace();
                Helper.DialogHelper.alert(this, "Xóa thất bại!");
            }
        }
    }

    public void deleteGioiTinh() {
        if (Helper.DialogHelper.confirm(this, "Bạn thực sự muốn xóa Giới tính này?")) {
            try {
                _IQLySanPhamService.delete_GioiTinh(txt_IDGioiTinh.getText());   //xóa nhân viên theo maNV
//                this.fillTable(_trang);//điền tt mới vào bảng
//                this.viewSoTrang();
                this.clearFormGioiTinh();//xóa trắng form và chỉnh lại status
                this.fillTableGioiTinh();
                this.fillComboBoxGioiTinh();
                Helper.DialogHelper.alert(this, "Xóa thành công!");
//                tabs.setSelectedIndex(1);
                _rowGioiTinh = -1;
            } catch (Exception e) {
                e.printStackTrace();
                Helper.DialogHelper.alert(this, "Xóa thất bại!");
            }
        }
    }

    public void deleteDonViTinh() {
        if (Helper.DialogHelper.confirm(this, "Bạn thực sự muốn xóa Đơn vị tính này?")) {
            try {
                _IQLySanPhamService.delete_DonViTinh(txt_IDDVT.getText());   //xóa nhân viên theo maNV
//                this.fillTable(_trang);//điền tt mới vào bảng
//                this.viewSoTrang();
                this.clearFormDonViTinh();//xóa trắng form và chỉnh lại status
                this.fillTableDonViTinh();
                this.fillComboBoxDVT();
                Helper.DialogHelper.alert(this, "Xóa thành công!");
//                tabs.setSelectedIndex(1);
                _rowDVT = -1;
            } catch (Exception e) {
                e.printStackTrace();
                Helper.DialogHelper.alert(this, "Xóa thất bại!");
            }
        }
    }

    public void deleteLoaiHang() {
        if (Helper.DialogHelper.confirm(this, "Bạn thực sự muốn xóa Loại hàng này?")) {
            try {
                _IQLySanPhamService.delete_LoaiHang(txt_IDLoaiHang.getText());   //xóa nhân viên theo maNV
//                this.fillTable(_trang);//điền tt mới vào bảng
//                this.viewSoTrang();
                this.clearFormLoaiHang();//xóa trắng form và chỉnh lại status
                this.fillTableLoaiHang();
                this.fillComboBoxLoaiHang();
                Helper.DialogHelper.alert(this, "Xóa thành công!");
//                tabs.setSelectedIndex(1);
                _rowLoaiHang = -1;
            } catch (Exception e) {
                e.printStackTrace();
                Helper.DialogHelper.alert(this, "Xóa thất bại!");
            }
        }
    }

    public void deleteKieuDang() {
        if (Helper.DialogHelper.confirm(this, "Bạn thực sự muốn xóa Kiểu dáng này?")) {
            try {
                _IQLySanPhamService.delete_KieuDang(txt_IDKieuDang.getText());   //xóa nhân viên theo maNV
//                this.fillTable(_trang);//điền tt mới vào bảng
//                this.viewSoTrang();
                this.clearFormKieuDang();//xóa trắng form và chỉnh lại status
                this.fillTableKieuDang();
                this.fillComboBoxKieuDang();
                Helper.DialogHelper.alert(this, "Xóa thành công!");
//                tabs.setSelectedIndex(1);
                _rowKieuDang = -1;
            } catch (Exception e) {
                e.printStackTrace();
                Helper.DialogHelper.alert(this, "Xóa thất bại!");
            }
        }
    }

    public void deleteLoaiKinh() {
        if (Helper.DialogHelper.confirm(this, "Bạn thực sự muốn xóa Loại kính này?")) {
            try {
                _IQLySanPhamService.delete_LoaiKinh(txt_IDLoaiKinh.getText());   //xóa nhân viên theo maNV
//                this.fillTable(_trang);//điền tt mới vào bảng
//                this.viewSoTrang();
                this.clearFormLoaiKinh();//xóa trắng form và chỉnh lại status
                this.fillTableLoaiKinh();
                this.fillComboBoxLoaiKinh();
                Helper.DialogHelper.alert(this, "Xóa thành công!");
//                tabs.setSelectedIndex(1);
                _rowLoaiKinh = -1;
            } catch (Exception e) {
                e.printStackTrace();
                Helper.DialogHelper.alert(this, "Xóa thất bại!");
            }
        }
    }

    public void deleteKichThuoc() {
        if (Helper.DialogHelper.confirm(this, "Bạn thực sự muốn xóa Kích thước này?")) {
            try {
                _IQLySanPhamService.delete_KichThuoc(txt_IDKichThuoc.getText());   //xóa nhân viên theo maNV
//                this.fillTable(_trang);//điền tt mới vào bảng
//                this.viewSoTrang();
                this.clearFormKichThuoc();//xóa trắng form và chỉnh lại status
                this.fillTableKichThuoc();
                this.fillComboBoxKichThuoc();
                Helper.DialogHelper.alert(this, "Xóa thành công!");
//                tabs.setSelectedIndex(1);
                _rowKichThuoc = -1;
            } catch (Exception e) {
                e.printStackTrace();
                Helper.DialogHelper.alert(this, "Xóa thất bại!");
            }
        }
    }

    public void deleteMauSac() {
        if (Helper.DialogHelper.confirm(this, "Bạn thực sự muốn xóa Màu sắc này?")) {
            try {
                _IQLySanPhamService.delete_MauSac(txt_KichThuoc.getText());   //xóa nhân viên theo maNV
//                this.fillTable(_trang);//điền tt mới vào bảng
//                this.viewSoTrang();
                this.clearFormMauSac();//xóa trắng form và chỉnh lại status
                this.fillTableMauSac();
                this.fillComboBoxMauSac();
                Helper.DialogHelper.alert(this, "Xóa thành công!");
//                tabs.setSelectedIndex(1);
                _rowKichThuoc = -1;
            } catch (Exception e) {
                e.printStackTrace();
                Helper.DialogHelper.alert(this, "Xóa thất bại!");
            }
        }
    }

    public void deleteSanPham() {
//        if (Helper.DialogHelper.confirm(this, "Bạn thực sự muốn xóa Màu sắc này?")) {
//            try {
        _IQLySanPhamService.delete_SanPham(txt_IDSanPham.getText());   //xóa nhân viên theo maNV
//                this.fillTable(_trang);//điền tt mới vào bảng
//                this.viewSoTrang();
//                this.clearFormMauSac();//xóa trắng form và chỉnh lại status
//                this.fillTableMauSac();
//                this.fillComboBoxMauSac();
//                Helper.DialogHelper.alert(this, "Xóa thành công!");
////                tabs.setSelectedIndex(1);
        _rowSanPham = -1;
//            } catch (Exception e) {
//                e.printStackTrace();
//                Helper.DialogHelper.alert(this, "Xóa thất bại!");
//            }
//        }
    }

    public void deleteChiTietSanPham() {
//        if (Helper.DialogHelper.confirm(this, "Bạn thực sự muốn xóa Màu sắc này?")) {
//            try {
        _IQLySanPhamService.delete_CTSP(txt_IDSanPham.getText());   //xóa nhân viên theo maNV
//                this.fillTable(_trang);//điền tt mới vào bảng
//                this.viewSoTrang();
//                this.clearFormMauSac();//xóa trắng form và chỉnh lại status
//                this.fillTableMauSac();
//                this.fillComboBoxMauSac();
//                Helper.DialogHelper.alert(this, "Xóa thành công!");
////                tabs.setSelectedIndex(1);
        _rowSanPham = -1;
//            } catch (Exception e) {
//                e.printStackTrace();
//                Helper.DialogHelper.alert(this, "Xóa thất bại!");
//            }
//        }
    }
//
//    public void deleteOptions() {
//        if (Helper.DialogHelper.confirm(this, "Bạn thực sự muốn xóa thuộc tính này?")) {
//            try {
//                _IQLySanPhamService.deleteOptions(txt_IDOptions.getText());   //xóa nhân viên theo maNV
////                this.fillTable(_trang);//điền tt mới vào bảng
////                this.viewSoTrang();
//                this.clearFormOptions();//xóa trắng form và chỉnh lại status
//                this.fillTableOption();
//                this.fillComboBoxOption();
//                Helper.DialogHelper.alert(this, "Xóa thành công!");
////                tabs.setSelectedIndex(1);
//            } catch (Exception e) {
//                e.printStackTrace();
//                Helper.DialogHelper.alert(this, "Xóa thất bại!");
//            }
//        }
//    }
//
//    public void deleteOptionValues() {
//        Options options = (Options) cbb_option.getSelectedItem();
//        if (Helper.DialogHelper.confirm(this, "Bạn thực sự muốn xóa giá trị này?")) {
//            try {
//                _IQLySanPhamService.deleteOptions_values(options.getID_Options(), txt_IDOptionValues.getText());   //xóa nhân viên theo maNV
////                this.fillTable(_trang);//điền tt mới vào bảng
////                this.viewSoTrang();
//                this.clearFormOptionValues();//xóa trắng form và chỉnh lại status
//                this.fillTableOptionValuesByID();
////                this.fillComboBoxOption();
//                Helper.DialogHelper.alert(this, "Xóa thành công!");
////                tabs.setSelectedIndex(1);
//            } catch (Exception e) {
//                e.printStackTrace();
//                Helper.DialogHelper.alert(this, "Xóa thất bại!");
//            }
//        }
//    }
//
//    public void deleteProductVariant() {
//        Product product = (Product) cbb_Product1.getSelectedItem();
//        if (Helper.DialogHelper.confirm(this, "Bạn thực sự muốn xóa giá trị này?")) {
//            try {
//                _IQLySanPhamService.deleteProduct_variant(product.getID_Product(), txt_IDVariant_ProductVariant.getText());
////                this.fillTable(_trang);//điền tt mới vào bảng
////                this.viewSoTrang();
//                this.clearFormProductVariant();//xóa trắng form và chỉnh lại status
//                this.fillTableProductVarintByID();
//                this.fillComboBoxAllProductVarint();
////                this.fillTableVariantValuesByID();
////                this.fillComboBoxIDVariant_ProductVarint();
//                Helper.DialogHelper.alert(this, "Xóa thành công!");
//
//            } catch (Exception e) {
//                e.printStackTrace();
//                Helper.DialogHelper.alert(this, "Xóa thất bại!");
//            }
//        }
//    }
//
//    public void deleteVariantValues() {
//        Product product = (Product) cbb_Product1.getSelectedItem();
//        if (Helper.DialogHelper.confirm(this, "Bạn thực sự muốn xóa giá trị này?")) {
//            try {
//                _IQLySanPhamService.deleteProduct_variant(product.getID_Product(), txt_IDVariant_ProductVariant.getText());
////                this.fillTable(_trang);//điền tt mới vào bảng
////                this.viewSoTrang();
//                this.clearFormProductVariant();//xóa trắng form và chỉnh lại status
//                this.fillTableProductVarintByID();
//                this.fillComboBoxAllProductVarint();
////                this.fillTableVariantValuesByID();
////                this.fillComboBoxIDVariant_ProductVarint();
//                Helper.DialogHelper.alert(this, "Xóa thành công!");
//
//            } catch (Exception e) {
//                e.printStackTrace();
//                Helper.DialogHelper.alert(this, "Xóa thất bại!");
//            }
//        }
//    }
//

    public void clearFormHang() {
        HANG hang = new HANG();
        this.setFormHang(hang);
        _rowHang = -1;
//        this.updateStatus(true);
    }

    public void clearFormGioiTinh() {
        GIOI_TINH gioi_tinh = new GIOI_TINH();
        this.setFormGioiTinh(gioi_tinh);
        _rowGioiTinh = -1;
//        this.updateStatus(true);
    }

    public void clearFormDonViTinh() {
        DON_VI_TINH don_vi_tinh = new DON_VI_TINH();
        this.setFormDonViTinh(don_vi_tinh);
        _rowDVT = -1;
//        this.updateStatus(true);
    }

    public void clearFormLoaiHang() {
        LOAI_HANG loai_hang = new LOAI_HANG();
        this.setFormLoaiHang(loai_hang);
        _rowLoaiHang = -1;
//        this.updateStatus(true);
    }

    public void clearFormKieuDang() {
        KIEU_DANG kieu_dang = new KIEU_DANG();
        this.setFormKieuDang(kieu_dang);
        _rowKieuDang = -1;
//        this.updateStatus(true);
    }

    public void clearFormLoaiKinh() {
        LOAI_KINH loai_kinh = new LOAI_KINH();
        this.setFormLoaiKinh(loai_kinh);
        _rowLoaiKinh = -1;
//        this.updateStatus(true);
    }

    public void clearFormKichThuoc() {
        KICH_THUOC kich_thuoc = new KICH_THUOC();
        this.setFormKichThuoc(kich_thuoc);
        _rowKichThuoc = -1;
//        this.updateStatus(true);
    }

    public void clearFormMauSac() {
        MAU_SAC mau_sac = new MAU_SAC();
        this.setFormMauSac(mau_sac);
        _rowKichThuoc = -1;
//        this.updateStatus(true);
    }
//
//    public void clearFormOptions() {
//        Options options = new Options();
//        this.setFormOptions(options);
//    }
//
//    public void clearFormOptionValues() {
//        Options_values options_values = new Options_values();
//        this.setFormOptionValues(options_values);
//    }
//
//    public void clearFormProductVariant() {
//        Product_variant product_variant = new Product_variant();
//        this.setFormProductVariant(product_variant);
//    }
//

    public boolean checkIDProduct(String ID) {
        if (_IQLySanPhamService.findById_Hang(ID) == null) {
            return true;
        } else {
            Helper.DialogHelper.alert(this, "Mã " + ID + " đã tồn tại !");
            return false;
        }
    }

    public boolean checkIDGioiTinh(String ID) {
        if (_IQLySanPhamService.findById_GioiTinh(ID) == null) {
            return true;
        } else {
            Helper.DialogHelper.alert(this, "Mã " + ID + " đã tồn tại !");
            return false;
        }
    }

    public boolean checkIDDVT(String ID) {
        if (_IQLySanPhamService.findById_DonViTinh(ID) == null) {
            return true;
        } else {
            Helper.DialogHelper.alert(this, "Mã " + ID + " đã tồn tại !");
            return false;
        }
    }

    public boolean checkIDLoaiHang(String ID) {
        if (_IQLySanPhamService.findById_LoaiHang(ID) == null) {
            return true;
        } else {
            Helper.DialogHelper.alert(this, "Mã " + ID + " đã tồn tại !");
            return false;
        }
    }

    public boolean checkIDKieuDang(String ID) {
        if (_IQLySanPhamService.findById_KieuDang(ID) == null) {
            return true;
        } else {
            Helper.DialogHelper.alert(this, "Mã " + ID + " đã tồn tại !");
            return false;
        }
    }

    public boolean checkIDLoaiKinh(String ID) {
        if (_IQLySanPhamService.findById_LoaiKinh(ID) == null) {
            return true;
        } else {
            Helper.DialogHelper.alert(this, "Mã " + ID + " đã tồn tại !");
            return false;
        }
    }

    public boolean checkIDKichThuoc(String ID) {
        if (_IQLySanPhamService.findById_KichThuoc(ID) == null) {
            return true;
        } else {
            Helper.DialogHelper.alert(this, "Mã " + ID + " đã tồn tại !");
            return false;
        }
    }

    public boolean checkIDMauSac(String ID) {
        if (_IQLySanPhamService.findById_MauSac(ID) == null) {
            return true;
        } else {
            Helper.DialogHelper.alert(this, "Mã " + ID + " đã tồn tại !");
            return false;
        }
    }
//
//    public boolean checkIDOptions(String ID) {
//        if (_IQLySanPhamService.findByIdOptions(ID) == null) {
//            return true;
//        } else {
//            Helper.DialogHelper.alert(this, "Mã " + ID + " đã tồn tại !");
//            return false;
//        }
//    }
//
//    public boolean checkIDOptionValues(String ID) {
//        if (_IQLySanPhamService.findByIdOptions_Options_values(ID) == null) {
//            return true;
//        } else {
//            Helper.DialogHelper.alert(this, "Mã " + ID + " đã tồn tại !");
//            return false;
//        }
//    }
//
//    public boolean checkIDProductVariant(String ID) {
//        if (_IQLySanPhamService.findByIdProduct_variant(ID) == null) {
//            return true;
//        } else {
//            Helper.DialogHelper.alert(this, "Mã " + ID + " đã tồn tại !");
//            return false;
//        }
//    }
//

    public boolean checkNamesProduct(String Names) {
        if (_IQLySanPhamService.findByNames_Hang(Names) == null) {
            return true;
        } else {
            Helper.DialogHelper.alert(this, "Hãng " + Names + " đã tồn tại !");
            return false;
        }
    }

    public boolean checkNamesGioiTinh(String GioiTinh) {
        if (_IQLySanPhamService.findByNames_GioiTinh(GioiTinh) == null) {
            return true;
        } else {
            Helper.DialogHelper.alert(this, "Giới tính " + GioiTinh + " đã tồn tại !");
            return false;
        }
    }

    public boolean checkNamesDVT(String DVT) {
        if (_IQLySanPhamService.findByNames_DonViTinh(DVT) == null) {
            return true;
        } else {
            Helper.DialogHelper.alert(this, "Đơn vị tính " + DVT + " đã tồn tại !");
            return false;
        }
    }

    public boolean checkNamesLoaiHang(String LoaiHang) {
        if (_IQLySanPhamService.findByNames_LoaiHang(LoaiHang) == null) {
            return true;
        } else {
            Helper.DialogHelper.alert(this, "Loại hàng " + LoaiHang + " đã tồn tại !");
            return false;
        }
    }

    public boolean checkNamesKieuDang(String KieuDang) {
        if (_IQLySanPhamService.findByNames_KieuDang(KieuDang) == null) {
            return true;
        } else {
            Helper.DialogHelper.alert(this, "Kiểu dáng " + KieuDang + " đã tồn tại !");
            return false;
        }
    }

    public boolean checkNamesLoaiKinh(String LoaiKinh) {
        if (_IQLySanPhamService.findByNames_LoaiKinh(LoaiKinh) == null) {
            return true;
        } else {
            Helper.DialogHelper.alert(this, "Loại kính " + LoaiKinh + " đã tồn tại !");
            return false;
        }
    }

    public boolean checkNamesKichThuoc(String KichThuoc) {
        if (_IQLySanPhamService.findByNames_KichThuoc(KichThuoc) == null) {
            return true;
        } else {
            Helper.DialogHelper.alert(this, "Kích thước " + KichThuoc + " đã tồn tại !");
            return false;
        }
    }

    public boolean checkNamesMauSac(String MauSac) {
        if (_IQLySanPhamService.findByNames_MauSac(MauSac) == null) {
            return true;
        } else {
            Helper.DialogHelper.alert(this, "Màu sắc " + MauSac + " đã tồn tại !");
            return false;
        }
    }
//
//    public boolean checkNamesOptions(String Names) {
//        if (_IQLySanPhamService.findByNamesOptions(Names) == null) {
//            return true;
//        } else {
//            Helper.DialogHelper.alert(this, "Tên " + Names + " đã tồn tại !");
//            return false;
//        }
//    }
//
//    public boolean checkNamesOptionValues(String Names) {
//        if (_IQLySanPhamService.findByNamesOptions_values(Names) == null) {
//            return true;
//        } else {
//            Helper.DialogHelper.alert(this, "Tên " + Names + " đã tồn tại !");
//            return false;
//        }
//    }
//
//    public boolean checkNamesProductVariant(String Names) {
//        if (_IQLySanPhamService.findByNamesProduct_variant(Names) == null) {
//            return true;
//        } else {
//            Helper.DialogHelper.alert(this, "Tên " + Names + " đã tồn tại !");
//            return false;
//        }
//    }
//
//    public void loadCBBOptionValues(JComboBox jcb) {
//        DefaultComboBoxModel cbbOptionValues = (DefaultComboBoxModel) jcb.getModel();
//        cbbOptionValues.removeAllElements();
//        try {
//            Options optionss = (Options) _cbx.getSelectedItem();
//            List<Options_values> list = _IQLySanPhamService.selectByIDOptions_values(optionss.getID_Options());
//            for (Options_values options_values : list) {
//                if (options_values.getTRANGTHAI() == 0) {
//                    cbbOptionValues.addElement(options_values);
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            DialogHelper.alert(this, "Lỗi truy vấn dữ liệu");
//        }
//    }
//
//    public void actionOptions() {
//        _thuocTinhs++;
//        _cbx = new JComboBox();
//        _cbx2 = new JComboBox();
//        _cbx.setSize(100, 30);
//        _cbx.setLocation(5, 0 + ((_thuocTinhs - 1) * 50));
//        _cbx2.setSize(150, 30);
//        _cbx2.setLocation(110, 0 + ((_thuocTinhs - 1) * 50));
//        Panel_ThuocTinhs.add(_cbx);
//        Panel_ThuocTinhs.add(_cbx2);
//        DefaultComboBoxModel cbbOptions = (DefaultComboBoxModel) _cbx.getModel();
//        cbbOptions.removeAllElements();
//        try {
//            List<Options> list = _IQLySanPhamService.selectAllOptions();
//            for (Options options : list) {
//                if (options.getTRANGTHAI() == 0) {
//                    cbbOptions.addElement(options);
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            DialogHelper.alert(this, "Lỗi truy vấn dữ liệu");
//        }
//        loadCBBOptionValues(_cbx2);
//
//        _cbx.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                loadCBBOptionValues(_cbx2);
//            }
//        });
//
//    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        pnlSide = new javax.swing.JPanel();
        pnlTrangChu = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        lblTrangChu = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        jPanel6 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        lblDanhMuc = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JSeparator();
        jPanel11 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        lblThuocTinh = new javax.swing.JLabel();
        jPanel13 = new javax.swing.JPanel();
        jLabel26 = new javax.swing.JLabel();
        lblLoaiSP = new javax.swing.JLabel();
        jSeparator5 = new javax.swing.JSeparator();
        jLabel1 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator4 = new javax.swing.JSeparator();
        tabs_QLSP = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        tab_CTSP = new javax.swing.JTabbedPane();
        jPanel8 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl_CTSP = new javax.swing.JTable();
        jTextField1 = new javax.swing.JTextField();
        jPanel9 = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        btn_insertVariantValues = new javax.swing.JButton();
        btn_updateVariantValues = new javax.swing.JButton();
        btn_deleteVariantValues = new javax.swing.JButton();
        btn_newVariantValues = new javax.swing.JButton();
        jLabel21 = new javax.swing.JLabel();
        btn_autoBarCode = new javax.swing.JButton();
        txt_barcode = new javax.swing.JTextField();
        rdb_0_SanPham = new javax.swing.JRadioButton();
        rdb_1_SanPham = new javax.swing.JRadioButton();
        jPanel17 = new javax.swing.JPanel();
        lbl_images = new javax.swing.JLabel();
        txt_TenSP = new javax.swing.JTextField();
        jLabel41 = new javax.swing.JLabel();
        cbb_Hang = new javax.swing.JComboBox<>();
        jLabel42 = new javax.swing.JLabel();
        cbb_LoaiHang2 = new javax.swing.JComboBox<>();
        jLabel43 = new javax.swing.JLabel();
        cbb_gioiTinh = new javax.swing.JComboBox<>();
        jLabel44 = new javax.swing.JLabel();
        cbb_DVT = new javax.swing.JComboBox<>();
        jLabel45 = new javax.swing.JLabel();
        txt_IDSanPham = new javax.swing.JTextField();
        cbb_KichThuoc = new javax.swing.JComboBox<>();
        jLabel46 = new javax.swing.JLabel();
        cbb_MauSac = new javax.swing.JComboBox<>();
        jLabel47 = new javax.swing.JLabel();
        cbb_LoaiKinh = new javax.swing.JComboBox<>();
        jLabel48 = new javax.swing.JLabel();
        cbb_KieuDang = new javax.swing.JComboBox<>();
        txt_quantity = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        txt_price = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        cbb_LoaiHang = new javax.swing.JComboBox<>();
        jPanel4 = new javax.swing.JPanel();
        jPanel16 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbl_Hang = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        txt_IDHang = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txt_TenHang = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        rdb_0_Hang = new javax.swing.JRadioButton();
        rdb_1_Hang = new javax.swing.JRadioButton();
        btn_insertHang = new javax.swing.JButton();
        btn_updateHang = new javax.swing.JButton();
        btn_deleteHang = new javax.swing.JButton();
        btn_newHang = new javax.swing.JButton();
        jPanel18 = new javax.swing.JPanel();
        jScrollPane8 = new javax.swing.JScrollPane();
        tbl_LoaiHang = new javax.swing.JTable();
        jLabel38 = new javax.swing.JLabel();
        txt_IDLoaiHang = new javax.swing.JTextField();
        jLabel39 = new javax.swing.JLabel();
        txt_TenLoaiHang = new javax.swing.JTextField();
        jLabel40 = new javax.swing.JLabel();
        rdb_0_LoaiHang = new javax.swing.JRadioButton();
        rdb_1_LoaiHang = new javax.swing.JRadioButton();
        btn_newLoaiHang = new javax.swing.JButton();
        btn_deleteLoaiHang = new javax.swing.JButton();
        btn_updateLoaiHang = new javax.swing.JButton();
        btn_insertLoaiHang = new javax.swing.JButton();
        jPanel10 = new javax.swing.JPanel();
        jPanel14 = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        tbl_GioiTinh = new javax.swing.JTable();
        jLabel17 = new javax.swing.JLabel();
        txt_IDGioiTinh = new javax.swing.JTextField();
        jLabel27 = new javax.swing.JLabel();
        txt_GioiTinh = new javax.swing.JTextField();
        jLabel34 = new javax.swing.JLabel();
        rdb_0_GioiTinh = new javax.swing.JRadioButton();
        rdb_1_GioiTinh = new javax.swing.JRadioButton();
        btn_insertGioiTinh = new javax.swing.JButton();
        btn_updateGioiTinh = new javax.swing.JButton();
        btn_deleteGioiTinh = new javax.swing.JButton();
        btn_newGioiTinh = new javax.swing.JButton();
        jPanel15 = new javax.swing.JPanel();
        jScrollPane7 = new javax.swing.JScrollPane();
        tbl_DonViTinh = new javax.swing.JTable();
        jLabel35 = new javax.swing.JLabel();
        txt_IDDVT = new javax.swing.JTextField();
        jLabel36 = new javax.swing.JLabel();
        txt_DVT = new javax.swing.JTextField();
        rdb_0_DVT = new javax.swing.JRadioButton();
        rdb_1_DVT = new javax.swing.JRadioButton();
        btn_newDVT = new javax.swing.JButton();
        btn_deleteDVT = new javax.swing.JButton();
        btn_updateDVT = new javax.swing.JButton();
        btn_insertDVT = new javax.swing.JButton();
        jLabel37 = new javax.swing.JLabel();
        jPanel12 = new javax.swing.JPanel();
        jPanel19 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tbl_KichThuoc = new javax.swing.JTable();
        btn_insertKichThuoc = new javax.swing.JButton();
        btn_updateKichThuoc = new javax.swing.JButton();
        btn_deleteKichThuoc = new javax.swing.JButton();
        btn_newKichThuoc = new javax.swing.JButton();
        jLabel25 = new javax.swing.JLabel();
        rdb_0_KichThuoc = new javax.swing.JRadioButton();
        rdb_1_KichThuoc = new javax.swing.JRadioButton();
        jLabel23 = new javax.swing.JLabel();
        txt_IDKichThuoc = new javax.swing.JTextField();
        jLabel24 = new javax.swing.JLabel();
        txt_KichThuoc = new javax.swing.JTextField();
        jPanel20 = new javax.swing.JPanel();
        jScrollPane9 = new javax.swing.JScrollPane();
        tbl_MauSac = new javax.swing.JTable();
        txt_IDMauSac = new javax.swing.JTextField();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        txt_MauSac = new javax.swing.JTextField();
        jLabel30 = new javax.swing.JLabel();
        rdb_0_MauSac = new javax.swing.JRadioButton();
        rdb_1_MauSac = new javax.swing.JRadioButton();
        btn_newMauSac = new javax.swing.JButton();
        btn_deleteMauSac = new javax.swing.JButton();
        btn_updateMauSac = new javax.swing.JButton();
        btn_insertMauSac = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tbl_LoaiKinh = new javax.swing.JTable();
        btn_insertLoaiKinh = new javax.swing.JButton();
        btn_updateLoaiKinh = new javax.swing.JButton();
        btn_deleteLoaiKinh = new javax.swing.JButton();
        btn_newLoaiKinh = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        txt_IDLoaiKinh = new javax.swing.JTextField();
        txt_NamesLoaiKinh = new javax.swing.JTextField();
        rdb_0_LoaiKinh = new javax.swing.JRadioButton();
        rdb_1_LoaiKinh = new javax.swing.JRadioButton();
        jPanel7 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tbl_KieuDang = new javax.swing.JTable();
        jLabel12 = new javax.swing.JLabel();
        txt_IDKieuDang = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        txt_NamesKieuDang = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        rdb_0_KieuDang = new javax.swing.JRadioButton();
        rdb_1_KieuDang = new javax.swing.JRadioButton();
        btn_insertKieuDang = new javax.swing.JButton();
        btn_updateKieuDang = new javax.swing.JButton();
        btn_deleteKieuDang = new javax.swing.JButton();
        btn_newKieuDang = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        pnlSide.setBackground(new java.awt.Color(74, 31, 61));

        pnlTrangChu.setBackground(new java.awt.Color(74, 31, 61));
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
        lblTrangChu.setForeground(new java.awt.Color(255, 255, 255));
        lblTrangChu.setText("   Danh sách sản phẩm");
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

        jPanel6.setBackground(new java.awt.Color(74, 31, 61));

        lblDanhMuc.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        lblDanhMuc.setForeground(new java.awt.Color(255, 255, 255));
        lblDanhMuc.setText("Hãng & Loại hàng");
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
                .addComponent(jLabel5)
                .addGap(18, 18, 18)
                .addComponent(lblDanhMuc, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(26, Short.MAX_VALUE))
            .addComponent(jSeparator3)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblDanhMuc, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout pnlTrangChuLayout = new javax.swing.GroupLayout(pnlTrangChu);
        pnlTrangChu.setLayout(pnlTrangChuLayout);
        pnlTrangChuLayout.setHorizontalGroup(
            pnlTrangChuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlTrangChuLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlTrangChuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jSeparator2)
                    .addGroup(pnlTrangChuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(pnlTrangChuLayout.createSequentialGroup()
                            .addComponent(jLabel3)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(lblTrangChu, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(29, Short.MAX_VALUE))
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
                .addGap(18, 18, 18)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel11.setBackground(new java.awt.Color(74, 31, 61));

        lblThuocTinh.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        lblThuocTinh.setForeground(new java.awt.Color(255, 255, 255));
        lblThuocTinh.setText("Kích thước & Màu sắc");
        lblThuocTinh.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblThuocTinh.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblThuocTinhMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblThuocTinhMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblThuocTinhMouseExited(evt);
            }
        });

        jPanel13.setBackground(new java.awt.Color(74, 31, 61));

        lblLoaiSP.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        lblLoaiSP.setForeground(new java.awt.Color(255, 255, 255));
        lblLoaiSP.setText("Giới tính & Đơn vị tính");
        lblLoaiSP.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblLoaiSP.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblLoaiSPMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblLoaiSPMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblLoaiSPMouseExited(evt);
            }
        });

        jSeparator5.setForeground(new java.awt.Color(240, 240, 240));

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addComponent(jLabel26)
                .addGap(18, 18, 18)
                .addComponent(lblLoaiSP, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(26, Short.MAX_VALUE))
            .addComponent(jSeparator5)
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblLoaiSP, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel26, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addComponent(jLabel15)
                        .addGap(18, 18, 18)
                        .addComponent(lblThuocTinh, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(29, Short.MAX_VALUE))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 28, Short.MAX_VALUE)
                        .addComponent(lblThuocTinh, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(5, 5, 5))
        );

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(240, 240, 240));
        jLabel1.setText("Horse Software Team");

        jSeparator1.setForeground(new java.awt.Color(240, 240, 240));

        jSeparator4.setForeground(new java.awt.Color(240, 240, 240));

        javax.swing.GroupLayout pnlSideLayout = new javax.swing.GroupLayout(pnlSide);
        pnlSide.setLayout(pnlSideLayout);
        pnlSideLayout.setHorizontalGroup(
            pnlSideLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlTrangChu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(pnlSideLayout.createSequentialGroup()
                .addGroup(pnlSideLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlSideLayout.createSequentialGroup()
                        .addGap(36, 36, 36)
                        .addGroup(pnlSideLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jSeparator1)
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(pnlSideLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 231, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlSideLayout.setVerticalGroup(
            pnlSideLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlSideLayout.createSequentialGroup()
                .addGap(73, 73, 73)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(65, 65, 65)
                .addComponent(pnlTrangChu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(255, 102, 102));
        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 204, 204)));

        tbl_CTSP.setBackground(new java.awt.Color(255, 204, 204));
        tbl_CTSP.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        tbl_CTSP.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Loại hàng", "Mã SP", "Tên SP", "Thông tin SP", "SL", "ĐVT", "Giá", "Trạng thái"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, true, false, false
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
            tbl_CTSP.getColumnModel().getColumn(1).setMinWidth(60);
            tbl_CTSP.getColumnModel().getColumn(1).setMaxWidth(70);
            tbl_CTSP.getColumnModel().getColumn(2).setMinWidth(120);
            tbl_CTSP.getColumnModel().getColumn(2).setMaxWidth(150);
            tbl_CTSP.getColumnModel().getColumn(3).setMinWidth(300);
            tbl_CTSP.getColumnModel().getColumn(4).setMaxWidth(40);
            tbl_CTSP.getColumnModel().getColumn(5).setMaxWidth(70);
            tbl_CTSP.getColumnModel().getColumn(6).setMaxWidth(60);
            tbl_CTSP.getColumnModel().getColumn(7).setMinWidth(80);
            tbl_CTSP.getColumnModel().getColumn(7).setMaxWidth(120);
        }

        jTextField1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jTextField1.setText("Tìm kiếm");

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1082, Short.MAX_VALUE)
                    .addComponent(jTextField1))
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 410, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(50, Short.MAX_VALUE))
        );

        tab_CTSP.addTab("Danh sách", jPanel8);

        jLabel16.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        jLabel16.setText("Tên SP:");

        jLabel18.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        jLabel18.setText("Trạng thái: ");

        jLabel20.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        jLabel20.setText("Kích thước:");

        btn_insertVariantValues.setBackground(new java.awt.Color(11, 181, 217));
        btn_insertVariantValues.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btn_insertVariantValues.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/baseline_add_black_24dp.png"))); // NOI18N
        btn_insertVariantValues.setText("Thêm");
        btn_insertVariantValues.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_insertVariantValuesActionPerformed(evt);
            }
        });

        btn_updateVariantValues.setBackground(new java.awt.Color(11, 181, 217));
        btn_updateVariantValues.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btn_updateVariantValues.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/baseline_sync_alt_black_24dp.png"))); // NOI18N
        btn_updateVariantValues.setText("Sửa");
        btn_updateVariantValues.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_updateVariantValuesActionPerformed(evt);
            }
        });

        btn_deleteVariantValues.setBackground(new java.awt.Color(11, 181, 217));
        btn_deleteVariantValues.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btn_deleteVariantValues.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/baseline_delete_forever_black_24dp.png"))); // NOI18N
        btn_deleteVariantValues.setText("Xóa");
        btn_deleteVariantValues.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_deleteVariantValuesActionPerformed(evt);
            }
        });

        btn_newVariantValues.setBackground(new java.awt.Color(11, 181, 217));
        btn_newVariantValues.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btn_newVariantValues.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/baseline_open_in_new_black_24dp.png"))); // NOI18N
        btn_newVariantValues.setText("Làm mới");
        btn_newVariantValues.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_newVariantValuesActionPerformed(evt);
            }
        });

        jLabel21.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        jLabel21.setText("Barcode:");

        btn_autoBarCode.setBackground(new java.awt.Color(11, 181, 217));
        btn_autoBarCode.setText("Zen Barcode tự động");
        btn_autoBarCode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_autoBarCodeActionPerformed(evt);
            }
        });

        txt_barcode.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N

        rdb_0_SanPham.setText("Còn hàng");

        rdb_1_SanPham.setText("Hết hàng");

        jPanel17.setBackground(new java.awt.Color(204, 204, 204));
        jPanel17.setBorder(javax.swing.BorderFactory.createTitledBorder("Hình ảnh"));

        lbl_images.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbl_imagesMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 181, Short.MAX_VALUE)
            .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel17Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(lbl_images, javax.swing.GroupLayout.DEFAULT_SIZE, 161, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 255, Short.MAX_VALUE)
            .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel17Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(lbl_images, javax.swing.GroupLayout.DEFAULT_SIZE, 233, Short.MAX_VALUE)
                    .addContainerGap()))
        );

        jLabel41.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        jLabel41.setText("Hãng:");

        cbb_Hang.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel42.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        jLabel42.setText("Loại hàng:");

        cbb_LoaiHang2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel43.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        jLabel43.setText("Giới tính:");

        cbb_gioiTinh.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel44.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        jLabel44.setText("Đơn vị tính:");

        cbb_DVT.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel45.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        jLabel45.setText("Mã SP:");

        cbb_KichThuoc.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel46.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        jLabel46.setText("Màu sắc:");

        cbb_MauSac.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel47.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        jLabel47.setText("Loại kính:");

        cbb_LoaiKinh.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel48.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        jLabel48.setText("Kiểu dáng:");

        cbb_KieuDang.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        txt_quantity.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N

        jLabel22.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        jLabel22.setText("Số lượng:");

        jLabel31.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        jLabel31.setText("Đơn giá:");

        txt_price.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGap(110, 110, 110)
                        .addComponent(btn_insertVariantValues)
                        .addGap(50, 50, 50)
                        .addComponent(btn_updateVariantValues)
                        .addGap(51, 51, 51)
                        .addComponent(btn_deleteVariantValues)
                        .addGap(40, 40, 40)
                        .addComponent(btn_newVariantValues)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel9Layout.createSequentialGroup()
                                .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txt_TenSP, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel9Layout.createSequentialGroup()
                                .addComponent(jLabel45, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txt_IDSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel9Layout.createSequentialGroup()
                                    .addComponent(jLabel44, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addComponent(cbb_DVT, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel9Layout.createSequentialGroup()
                                    .addComponent(jLabel43, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addComponent(cbb_gioiTinh, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel9Layout.createSequentialGroup()
                                    .addComponent(jLabel42, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addComponent(cbb_LoaiHang2, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel9Layout.createSequentialGroup()
                                    .addComponent(jLabel41, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addComponent(cbb_Hang, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel9Layout.createSequentialGroup()
                                .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(34, 34, 34)
                                .addComponent(rdb_0_SanPham)
                                .addGap(18, 18, 18)
                                .addComponent(rdb_1_SanPham)))
                        .addGap(87, 87, 87)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel9Layout.createSequentialGroup()
                                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel9Layout.createSequentialGroup()
                                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel48, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel22)
                                            .addComponent(jLabel31)
                                            .addComponent(jLabel21))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jPanel9Layout.createSequentialGroup()
                                                .addComponent(btn_autoBarCode)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 67, Short.MAX_VALUE))
                                            .addComponent(cbb_KieuDang, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(txt_quantity)
                                            .addComponent(txt_price)
                                            .addComponent(txt_barcode)))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                                        .addComponent(jLabel47, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(cbb_LoaiKinh, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                                        .addComponent(jLabel46, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(cbb_MauSac, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                .addGap(121, 121, 121)
                                .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(97, 97, 97))
                            .addGroup(jPanel9Layout.createSequentialGroup()
                                .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cbb_KichThuoc, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel9Layout.createSequentialGroup()
                                .addGap(43, 43, 43)
                                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel45)
                                    .addComponent(txt_IDSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 54, Short.MAX_VALUE)
                                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel16)
                                    .addComponent(txt_TenSP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(23, 23, 23)
                                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel41)
                                    .addComponent(cbb_Hang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(27, 27, 27)
                                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel42)
                                    .addComponent(cbb_LoaiHang2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(28, 28, 28)
                                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel43)
                                    .addComponent(cbb_gioiTinh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(16, 16, 16)
                                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel44)
                                    .addComponent(cbb_DVT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(49, 49, 49))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel18)
                            .addComponent(rdb_0_SanPham)
                            .addComponent(rdb_1_SanPham)))
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGap(43, 43, 43)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbb_KichThuoc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel46, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbb_MauSac, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel47, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbb_LoaiKinh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(27, 27, 27)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel48, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbb_KieuDang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(26, 26, 26)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_quantity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(14, 14, 14)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel31, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_price, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(49, 49, 49)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_barcode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btn_autoBarCode)
                .addGap(38, 38, 38)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_insertVariantValues)
                    .addComponent(btn_updateVariantValues)
                    .addComponent(btn_deleteVariantValues)
                    .addComponent(btn_newVariantValues))
                .addGap(44, 44, 44))
        );

        tab_CTSP.addTab("Cập nhật", jPanel9);

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(51, 51, 51));
        jLabel7.setText("Loại hàng");

        cbb_LoaiHang.setBackground(new java.awt.Color(255, 102, 102));
        cbb_LoaiHang.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        cbb_LoaiHang.setForeground(new java.awt.Color(51, 51, 51));
        cbb_LoaiHang.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbb_LoaiHang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbb_LoaiHangActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tab_CTSP)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addGap(18, 18, 18)
                        .addComponent(cbb_LoaiHang, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(cbb_LoaiHang, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(tab_CTSP)
                .addContainerGap())
        );

        tabs_QLSP.addTab("Danh sách sản phẩm", jPanel2);

        jPanel4.setBackground(new java.awt.Color(255, 102, 102));

        jPanel16.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Hãng", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12))); // NOI18N

        tbl_Hang.setBackground(new java.awt.Color(255, 204, 204));
        tbl_Hang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã Hãng", "Tên Hãng", "Trạng thái"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbl_Hang.setGridColor(new java.awt.Color(240, 240, 240));
        tbl_Hang.setRowHeight(22);
        tbl_Hang.setSelectionBackground(new java.awt.Color(255, 102, 102));
        tbl_Hang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_HangMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tbl_Hang);

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel2.setText("Mã hãng: ");
        jLabel2.setName(""); // NOI18N

        txt_IDHang.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        txt_IDHang.setName("Mã hãng"); // NOI18N

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel4.setText("Tên hãng: ");

        txt_TenHang.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        txt_TenHang.setName("Tên hãng"); // NOI18N

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel6.setText("Trạng thái:");

        rdb_0_Hang.setBackground(new java.awt.Color(255, 204, 204));
        rdb_0_Hang.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        rdb_0_Hang.setText("Đang kinh doanh");

        rdb_1_Hang.setBackground(new java.awt.Color(255, 204, 204));
        rdb_1_Hang.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        rdb_1_Hang.setText("Ngừng kinh doanh");

        btn_insertHang.setBackground(new java.awt.Color(11, 181, 217));
        btn_insertHang.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btn_insertHang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/baseline_add_black_24dp.png"))); // NOI18N
        btn_insertHang.setText("Thêm");
        btn_insertHang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_insertHangActionPerformed(evt);
            }
        });

        btn_updateHang.setBackground(new java.awt.Color(11, 181, 217));
        btn_updateHang.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btn_updateHang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/baseline_sync_alt_black_24dp.png"))); // NOI18N
        btn_updateHang.setText("Sửa");
        btn_updateHang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_updateHangActionPerformed(evt);
            }
        });

        btn_deleteHang.setBackground(new java.awt.Color(11, 181, 217));
        btn_deleteHang.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btn_deleteHang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/baseline_delete_forever_black_24dp.png"))); // NOI18N
        btn_deleteHang.setText("Xóa");
        btn_deleteHang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_deleteHangActionPerformed(evt);
            }
        });

        btn_newHang.setBackground(new java.awt.Color(11, 181, 217));
        btn_newHang.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btn_newHang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/baseline_open_in_new_black_24dp.png"))); // NOI18N
        btn_newHang.setText("Làm mới");
        btn_newHang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_newHangActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addContainerGap(55, Short.MAX_VALUE)
                .addComponent(btn_insertHang)
                .addGap(18, 18, 18)
                .addComponent(btn_updateHang)
                .addGap(18, 18, 18)
                .addComponent(btn_deleteHang)
                .addGap(26, 26, 26)
                .addComponent(btn_newHang)
                .addGap(29, 29, 29))
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel16Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel16Layout.createSequentialGroup()
                        .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel16Layout.createSequentialGroup()
                                .addComponent(rdb_0_Hang)
                                .addGap(51, 51, 51)
                                .addComponent(rdb_1_Hang))
                            .addComponent(txt_TenHang, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 380, Short.MAX_VALUE)
                            .addComponent(txt_IDHang, javax.swing.GroupLayout.Alignment.TRAILING)))
                    .addComponent(jScrollPane2))
                .addContainerGap())
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 264, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(73, 73, 73)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txt_IDHang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txt_TenHang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(rdb_0_Hang)
                    .addComponent(rdb_1_Hang))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_insertHang)
                    .addComponent(btn_updateHang)
                    .addComponent(btn_deleteHang)
                    .addComponent(btn_newHang))
                .addGap(28, 28, 28))
        );

        jPanel18.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Loại hàng", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12))); // NOI18N
        jPanel18.setToolTipText("");

        tbl_LoaiHang.setBackground(new java.awt.Color(255, 204, 204));
        tbl_LoaiHang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã loại hàng", "Tên loại hàng", "Trạng thái"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbl_LoaiHang.setGridColor(new java.awt.Color(240, 240, 240));
        tbl_LoaiHang.setRowHeight(22);
        tbl_LoaiHang.setSelectionBackground(new java.awt.Color(255, 102, 102));
        tbl_LoaiHang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_LoaiHangMouseClicked(evt);
            }
        });
        jScrollPane8.setViewportView(tbl_LoaiHang);

        jLabel38.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel38.setText("Mã loại hàng: ");
        jLabel38.setName(""); // NOI18N

        txt_IDLoaiHang.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        txt_IDLoaiHang.setName("Mã loại hàng"); // NOI18N

        jLabel39.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel39.setText("Tên loại hàng:");

        txt_TenLoaiHang.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        txt_TenLoaiHang.setName("Tên loại hàng"); // NOI18N

        jLabel40.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel40.setText("Trạng thái:");

        rdb_0_LoaiHang.setBackground(new java.awt.Color(255, 204, 204));
        rdb_0_LoaiHang.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        rdb_0_LoaiHang.setText("Đang kinh doanh");

        rdb_1_LoaiHang.setBackground(new java.awt.Color(255, 204, 204));
        rdb_1_LoaiHang.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        rdb_1_LoaiHang.setText("Ngừng kinh doanh");

        btn_newLoaiHang.setBackground(new java.awt.Color(11, 181, 217));
        btn_newLoaiHang.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btn_newLoaiHang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/baseline_open_in_new_black_24dp.png"))); // NOI18N
        btn_newLoaiHang.setText("Làm mới");
        btn_newLoaiHang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_newLoaiHangActionPerformed(evt);
            }
        });

        btn_deleteLoaiHang.setBackground(new java.awt.Color(11, 181, 217));
        btn_deleteLoaiHang.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btn_deleteLoaiHang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/baseline_delete_forever_black_24dp.png"))); // NOI18N
        btn_deleteLoaiHang.setText("Xóa");
        btn_deleteLoaiHang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_deleteLoaiHangActionPerformed(evt);
            }
        });

        btn_updateLoaiHang.setBackground(new java.awt.Color(11, 181, 217));
        btn_updateLoaiHang.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btn_updateLoaiHang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/baseline_sync_alt_black_24dp.png"))); // NOI18N
        btn_updateLoaiHang.setText("Sửa");
        btn_updateLoaiHang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_updateLoaiHangActionPerformed(evt);
            }
        });

        btn_insertLoaiHang.setBackground(new java.awt.Color(11, 181, 217));
        btn_insertLoaiHang.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btn_insertLoaiHang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/baseline_add_black_24dp.png"))); // NOI18N
        btn_insertLoaiHang.setText("Thêm");
        btn_insertLoaiHang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_insertLoaiHangActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel18Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel18Layout.createSequentialGroup()
                        .addGap(0, 64, Short.MAX_VALUE)
                        .addComponent(btn_insertLoaiHang)
                        .addGap(27, 27, 27)
                        .addComponent(btn_updateLoaiHang)
                        .addGap(33, 33, 33)
                        .addComponent(btn_deleteLoaiHang)
                        .addGap(27, 27, 27)
                        .addComponent(btn_newLoaiHang)
                        .addGap(22, 22, 22))
                    .addGroup(jPanel18Layout.createSequentialGroup()
                        .addComponent(jLabel40)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel18Layout.createSequentialGroup()
                        .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel38)
                            .addComponent(jLabel39))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel18Layout.createSequentialGroup()
                                .addComponent(rdb_0_LoaiHang)
                                .addGap(51, 51, 51)
                                .addComponent(rdb_1_LoaiHang))
                            .addComponent(txt_TenLoaiHang, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txt_IDLoaiHang, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 380, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jScrollPane8))
                .addContainerGap())
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 73, Short.MAX_VALUE)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel38)
                    .addComponent(txt_IDLoaiHang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel39)
                    .addComponent(txt_TenLoaiHang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel40)
                    .addComponent(rdb_0_LoaiHang)
                    .addComponent(rdb_1_LoaiHang))
                .addGap(80, 80, 80)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_insertLoaiHang)
                    .addComponent(btn_updateLoaiHang)
                    .addComponent(btn_deleteLoaiHang)
                    .addComponent(btn_newLoaiHang))
                .addGap(28, 28, 28))
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(jPanel18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        tabs_QLSP.addTab("Hãng & Loại hàng", jPanel4);

        jPanel14.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Giới tính", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12))); // NOI18N

        tbl_GioiTinh.setBackground(new java.awt.Color(255, 204, 204));
        tbl_GioiTinh.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã Giới tính", "Giới tính", "Trạng thái"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbl_GioiTinh.setGridColor(new java.awt.Color(240, 240, 240));
        tbl_GioiTinh.setRowHeight(22);
        tbl_GioiTinh.setSelectionBackground(new java.awt.Color(255, 102, 102));
        tbl_GioiTinh.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_GioiTinhMouseClicked(evt);
            }
        });
        jScrollPane6.setViewportView(tbl_GioiTinh);

        jLabel17.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel17.setText("Mã giới tính:");
        jLabel17.setName(""); // NOI18N

        txt_IDGioiTinh.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        txt_IDGioiTinh.setName("Mã giới tính"); // NOI18N

        jLabel27.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel27.setText("Giới tính:");

        txt_GioiTinh.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        txt_GioiTinh.setName("Giới tính"); // NOI18N

        jLabel34.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel34.setText("Trạng thái:");

        rdb_0_GioiTinh.setBackground(new java.awt.Color(255, 204, 204));
        rdb_0_GioiTinh.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        rdb_0_GioiTinh.setText("Hoạt động");

        rdb_1_GioiTinh.setBackground(new java.awt.Color(255, 204, 204));
        rdb_1_GioiTinh.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        rdb_1_GioiTinh.setText("Không hoạt động");

        btn_insertGioiTinh.setBackground(new java.awt.Color(11, 181, 217));
        btn_insertGioiTinh.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btn_insertGioiTinh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/baseline_add_black_24dp.png"))); // NOI18N
        btn_insertGioiTinh.setText("Thêm");
        btn_insertGioiTinh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_insertGioiTinhActionPerformed(evt);
            }
        });

        btn_updateGioiTinh.setBackground(new java.awt.Color(11, 181, 217));
        btn_updateGioiTinh.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btn_updateGioiTinh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/baseline_sync_alt_black_24dp.png"))); // NOI18N
        btn_updateGioiTinh.setText("Sửa");
        btn_updateGioiTinh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_updateGioiTinhActionPerformed(evt);
            }
        });

        btn_deleteGioiTinh.setBackground(new java.awt.Color(11, 181, 217));
        btn_deleteGioiTinh.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btn_deleteGioiTinh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/baseline_delete_forever_black_24dp.png"))); // NOI18N
        btn_deleteGioiTinh.setText("Xóa");
        btn_deleteGioiTinh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_deleteGioiTinhActionPerformed(evt);
            }
        });

        btn_newGioiTinh.setBackground(new java.awt.Color(11, 181, 217));
        btn_newGioiTinh.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btn_newGioiTinh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/baseline_open_in_new_black_24dp.png"))); // NOI18N
        btn_newGioiTinh.setText("Làm mới");
        btn_newGioiTinh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_newGioiTinhActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane6)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel14Layout.createSequentialGroup()
                        .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel17)
                            .addComponent(jLabel27)
                            .addComponent(jLabel34))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txt_IDGioiTinh)
                            .addComponent(txt_GioiTinh)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel14Layout.createSequentialGroup()
                                .addComponent(rdb_0_GioiTinh)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 74, Short.MAX_VALUE)
                                .addComponent(rdb_1_GioiTinh)
                                .addGap(58, 58, 58))))
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addGap(36, 36, 36)
                        .addComponent(btn_insertGioiTinh)
                        .addGap(18, 18, 18)
                        .addComponent(btn_updateGioiTinh)
                        .addGap(18, 18, 18)
                        .addComponent(btn_deleteGioiTinh)
                        .addGap(18, 18, 18)
                        .addComponent(btn_newGioiTinh)
                        .addGap(0, 50, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 254, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_IDGioiTinh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel17))
                .addGap(18, 18, 18)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txt_GioiTinh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel27))
                .addGap(22, 22, 22)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel34)
                    .addComponent(rdb_0_GioiTinh)
                    .addComponent(rdb_1_GioiTinh))
                .addGap(35, 35, 35)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_insertGioiTinh)
                    .addComponent(btn_updateGioiTinh)
                    .addComponent(btn_deleteGioiTinh)
                    .addComponent(btn_newGioiTinh))
                .addGap(41, 41, 41))
        );

        jPanel15.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Đơn vị tính", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12))); // NOI18N

        tbl_DonViTinh.setBackground(new java.awt.Color(255, 204, 204));
        tbl_DonViTinh.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã đơn vị", "Đơn vị tính", "Trạng thái"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbl_DonViTinh.setGridColor(new java.awt.Color(240, 240, 240));
        tbl_DonViTinh.setRowHeight(22);
        tbl_DonViTinh.setSelectionBackground(new java.awt.Color(255, 102, 102));
        tbl_DonViTinh.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_DonViTinhMouseClicked(evt);
            }
        });
        jScrollPane7.setViewportView(tbl_DonViTinh);

        jLabel35.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel35.setText("Mã đơn vị");
        jLabel35.setName(""); // NOI18N

        txt_IDDVT.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        txt_IDDVT.setName("Mã đơn vị tính"); // NOI18N

        jLabel36.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel36.setText("Đơn vị tính");

        txt_DVT.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        txt_DVT.setName("Đơn vị tính"); // NOI18N

        rdb_0_DVT.setBackground(new java.awt.Color(255, 204, 204));
        rdb_0_DVT.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        rdb_0_DVT.setText("Hoạt động");

        rdb_1_DVT.setBackground(new java.awt.Color(255, 204, 204));
        rdb_1_DVT.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        rdb_1_DVT.setText("Không hoạt động");

        btn_newDVT.setBackground(new java.awt.Color(11, 181, 217));
        btn_newDVT.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btn_newDVT.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/baseline_open_in_new_black_24dp.png"))); // NOI18N
        btn_newDVT.setText("Làm mới");
        btn_newDVT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_newDVTActionPerformed(evt);
            }
        });

        btn_deleteDVT.setBackground(new java.awt.Color(11, 181, 217));
        btn_deleteDVT.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btn_deleteDVT.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/baseline_delete_forever_black_24dp.png"))); // NOI18N
        btn_deleteDVT.setText("Xóa");
        btn_deleteDVT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_deleteDVTActionPerformed(evt);
            }
        });

        btn_updateDVT.setBackground(new java.awt.Color(11, 181, 217));
        btn_updateDVT.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btn_updateDVT.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/baseline_sync_alt_black_24dp.png"))); // NOI18N
        btn_updateDVT.setText("Sửa");
        btn_updateDVT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_updateDVTActionPerformed(evt);
            }
        });

        btn_insertDVT.setBackground(new java.awt.Color(11, 181, 217));
        btn_insertDVT.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btn_insertDVT.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/baseline_add_black_24dp.png"))); // NOI18N
        btn_insertDVT.setText("Thêm");
        btn_insertDVT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_insertDVTActionPerformed(evt);
            }
        });

        jLabel37.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel37.setText("Trạng thái:");

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel15Layout.createSequentialGroup()
                        .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel35)
                            .addComponent(jLabel36)
                            .addComponent(jLabel37))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txt_IDDVT)
                            .addComponent(txt_DVT)
                            .addGroup(jPanel15Layout.createSequentialGroup()
                                .addComponent(rdb_0_DVT)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 74, Short.MAX_VALUE)
                                .addComponent(rdb_1_DVT)
                                .addGap(58, 58, 58))))
                    .addComponent(jScrollPane7))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel15Layout.createSequentialGroup()
                .addContainerGap(75, Short.MAX_VALUE)
                .addComponent(btn_insertDVT)
                .addGap(18, 18, 18)
                .addComponent(btn_updateDVT)
                .addGap(18, 18, 18)
                .addComponent(btn_deleteDVT)
                .addGap(18, 18, 18)
                .addComponent(btn_newDVT)
                .addGap(50, 50, 50))
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 90, Short.MAX_VALUE)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_IDDVT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel35))
                .addGap(30, 30, 30)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txt_DVT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel36))
                .addGap(22, 22, 22)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel37)
                    .addComponent(rdb_0_DVT)
                    .addComponent(rdb_1_DVT))
                .addGap(34, 34, 34)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_insertDVT)
                    .addComponent(btn_updateDVT)
                    .addComponent(btn_deleteDVT)
                    .addComponent(btn_newDVT))
                .addGap(41, 41, 41))
        );

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        tabs_QLSP.addTab("Giới Tính & Đơn vị tính", jPanel10);

        jPanel12.setBackground(new java.awt.Color(255, 102, 102));

        jPanel19.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Kích thước", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12))); // NOI18N

        tbl_KichThuoc.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã kích thước", "Kích thước", "Trạng thái"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbl_KichThuoc.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_KichThuocMouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(tbl_KichThuoc);

        btn_insertKichThuoc.setBackground(new java.awt.Color(11, 181, 217));
        btn_insertKichThuoc.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btn_insertKichThuoc.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/baseline_add_black_24dp.png"))); // NOI18N
        btn_insertKichThuoc.setText("Thêm");
        btn_insertKichThuoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_insertKichThuocActionPerformed(evt);
            }
        });

        btn_updateKichThuoc.setBackground(new java.awt.Color(11, 181, 217));
        btn_updateKichThuoc.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btn_updateKichThuoc.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/baseline_sync_alt_black_24dp.png"))); // NOI18N
        btn_updateKichThuoc.setText("Sửa");
        btn_updateKichThuoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_updateKichThuocActionPerformed(evt);
            }
        });

        btn_deleteKichThuoc.setBackground(new java.awt.Color(11, 181, 217));
        btn_deleteKichThuoc.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btn_deleteKichThuoc.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/baseline_delete_forever_black_24dp.png"))); // NOI18N
        btn_deleteKichThuoc.setText("Xóa");
        btn_deleteKichThuoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_deleteKichThuocActionPerformed(evt);
            }
        });

        btn_newKichThuoc.setBackground(new java.awt.Color(11, 181, 217));
        btn_newKichThuoc.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btn_newKichThuoc.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/baseline_open_in_new_black_24dp.png"))); // NOI18N
        btn_newKichThuoc.setText("Làm mới");
        btn_newKichThuoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_newKichThuocActionPerformed(evt);
            }
        });

        jLabel25.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        jLabel25.setText("Trạng thái: ");

        rdb_0_KichThuoc.setText("Hoạt động");

        rdb_1_KichThuoc.setText("Không hoạt động");

        jLabel23.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        jLabel23.setText("Mã kích thước:");
        jLabel23.setName("Mã kích thước"); // NOI18N

        txt_IDKichThuoc.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        txt_IDKichThuoc.setName("Loại sản phẩm"); // NOI18N

        jLabel24.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        jLabel24.setText("Kích thước: ");
        jLabel24.setName("Kích thước"); // NOI18N

        txt_KichThuoc.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        txt_KichThuoc.setName("Tên sản phẩm"); // NOI18N

        javax.swing.GroupLayout jPanel19Layout = new javax.swing.GroupLayout(jPanel19);
        jPanel19.setLayout(jPanel19Layout);
        jPanel19Layout.setHorizontalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane5)
                    .addGroup(jPanel19Layout.createSequentialGroup()
                        .addComponent(jLabel23, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(306, 306, 306))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel19Layout.createSequentialGroup()
                        .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel25))
                        .addGap(237, 408, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(btn_insertKichThuoc)
                .addGap(18, 18, 18)
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txt_IDKichThuoc, javax.swing.GroupLayout.PREFERRED_SIZE, 288, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel19Layout.createSequentialGroup()
                        .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btn_updateKichThuoc)
                            .addComponent(rdb_0_KichThuoc))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(rdb_1_KichThuoc)
                            .addGroup(jPanel19Layout.createSequentialGroup()
                                .addComponent(btn_deleteKichThuoc)
                                .addGap(18, 18, 18)
                                .addComponent(btn_newKichThuoc))))
                    .addComponent(txt_KichThuoc, javax.swing.GroupLayout.PREFERRED_SIZE, 312, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel19Layout.setVerticalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 287, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 91, Short.MAX_VALUE)
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel23)
                    .addComponent(txt_IDKichThuoc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel19Layout.createSequentialGroup()
                        .addGap(45, 45, 45)
                        .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(rdb_0_KichThuoc)
                            .addComponent(rdb_1_KichThuoc)))
                    .addGroup(jPanel19Layout.createSequentialGroup()
                        .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel24)
                            .addComponent(txt_KichThuoc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(20, 20, 20)
                        .addComponent(jLabel25)))
                .addGap(18, 18, 18)
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_insertKichThuoc)
                    .addComponent(btn_updateKichThuoc)
                    .addComponent(btn_deleteKichThuoc)
                    .addComponent(btn_newKichThuoc))
                .addGap(22, 22, 22))
        );

        jPanel20.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Màu sắc", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12))); // NOI18N

        tbl_MauSac.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã màu", "Màu sắc", "Trạng thái"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbl_MauSac.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_MauSacMouseClicked(evt);
            }
        });
        jScrollPane9.setViewportView(tbl_MauSac);

        txt_IDMauSac.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        txt_IDMauSac.setName("Loại sản phẩm"); // NOI18N

        jLabel28.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        jLabel28.setText("Mã màu:");
        jLabel28.setName("Mã kích thước"); // NOI18N

        jLabel29.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        jLabel29.setText("Màu sắc:");
        jLabel29.setName("Kích thước"); // NOI18N

        txt_MauSac.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        txt_MauSac.setName("Tên sản phẩm"); // NOI18N

        jLabel30.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        jLabel30.setText("Trạng thái: ");

        rdb_0_MauSac.setText("Hoạt động");

        rdb_1_MauSac.setText("Không hoạt động");

        btn_newMauSac.setBackground(new java.awt.Color(11, 181, 217));
        btn_newMauSac.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btn_newMauSac.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/baseline_open_in_new_black_24dp.png"))); // NOI18N
        btn_newMauSac.setText("Làm mới");
        btn_newMauSac.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_newMauSacActionPerformed(evt);
            }
        });

        btn_deleteMauSac.setBackground(new java.awt.Color(11, 181, 217));
        btn_deleteMauSac.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btn_deleteMauSac.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/baseline_delete_forever_black_24dp.png"))); // NOI18N
        btn_deleteMauSac.setText("Xóa");
        btn_deleteMauSac.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_deleteMauSacActionPerformed(evt);
            }
        });

        btn_updateMauSac.setBackground(new java.awt.Color(11, 181, 217));
        btn_updateMauSac.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btn_updateMauSac.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/baseline_sync_alt_black_24dp.png"))); // NOI18N
        btn_updateMauSac.setText("Sửa");
        btn_updateMauSac.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_updateMauSacActionPerformed(evt);
            }
        });

        btn_insertMauSac.setBackground(new java.awt.Color(11, 181, 217));
        btn_insertMauSac.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btn_insertMauSac.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/baseline_add_black_24dp.png"))); // NOI18N
        btn_insertMauSac.setText("Thêm");
        btn_insertMauSac.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_insertMauSacActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel20Layout = new javax.swing.GroupLayout(jPanel20);
        jPanel20.setLayout(jPanel20Layout);
        jPanel20Layout.setHorizontalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane9)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel20Layout.createSequentialGroup()
                        .addGap(40, 40, 40)
                        .addComponent(btn_insertMauSac)
                        .addGap(18, 18, 18)
                        .addComponent(btn_updateMauSac)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(rdb_1_MauSac)
                            .addGroup(jPanel20Layout.createSequentialGroup()
                                .addComponent(btn_deleteMauSac)
                                .addGap(18, 18, 18)
                                .addComponent(btn_newMauSac))))
                    .addGroup(jPanel20Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel30))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(rdb_0_MauSac)
                            .addComponent(txt_MauSac, javax.swing.GroupLayout.DEFAULT_SIZE, 312, Short.MAX_VALUE)
                            .addComponent(txt_IDMauSac))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel28, javax.swing.GroupLayout.DEFAULT_SIZE, 113, Short.MAX_VALUE)
                .addGap(410, 410, 410))
        );
        jPanel20Layout.setVerticalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel28)
                    .addComponent(txt_IDMauSac, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel29)
                    .addComponent(txt_MauSac, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(23, 23, 23)
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel30)
                    .addComponent(rdb_0_MauSac)
                    .addComponent(rdb_1_MauSac))
                .addGap(18, 18, 18)
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_insertMauSac)
                    .addComponent(btn_updateMauSac)
                    .addComponent(btn_deleteMauSac)
                    .addComponent(btn_newMauSac))
                .addGap(22, 22, 22))
        );

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 31, Short.MAX_VALUE)
                .addComponent(jPanel20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        tabs_QLSP.addTab("Kích thước & Màu sắc", jPanel12);

        jPanel3.setBackground(new java.awt.Color(255, 102, 102));

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Loại kính", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 13))); // NOI18N

        tbl_LoaiKinh.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã Loại Kính", "Tên Loại Kính", "Trạng thái"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbl_LoaiKinh.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_LoaiKinhMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tbl_LoaiKinh);

        btn_insertLoaiKinh.setBackground(new java.awt.Color(11, 181, 217));
        btn_insertLoaiKinh.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btn_insertLoaiKinh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/baseline_add_black_24dp.png"))); // NOI18N
        btn_insertLoaiKinh.setText("Thêm");
        btn_insertLoaiKinh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_insertLoaiKinhActionPerformed(evt);
            }
        });

        btn_updateLoaiKinh.setBackground(new java.awt.Color(11, 181, 217));
        btn_updateLoaiKinh.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btn_updateLoaiKinh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/baseline_sync_alt_black_24dp.png"))); // NOI18N
        btn_updateLoaiKinh.setText("Sửa");
        btn_updateLoaiKinh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_updateLoaiKinhActionPerformed(evt);
            }
        });

        btn_deleteLoaiKinh.setBackground(new java.awt.Color(11, 181, 217));
        btn_deleteLoaiKinh.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btn_deleteLoaiKinh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/baseline_delete_forever_black_24dp.png"))); // NOI18N
        btn_deleteLoaiKinh.setText("Xóa");
        btn_deleteLoaiKinh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_deleteLoaiKinhActionPerformed(evt);
            }
        });

        btn_newLoaiKinh.setBackground(new java.awt.Color(11, 181, 217));
        btn_newLoaiKinh.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btn_newLoaiKinh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/baseline_open_in_new_black_24dp.png"))); // NOI18N
        btn_newLoaiKinh.setText("Làm mới");
        btn_newLoaiKinh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_newLoaiKinhActionPerformed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel9.setText("Mã Loại kính:");
        jLabel9.setName("Mã Loại kính"); // NOI18N

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel10.setText("Tên Loại kính:");
        jLabel10.setName("Tên Loại kính"); // NOI18N

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel11.setText("Trạng thái:");

        txt_IDLoaiKinh.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txt_IDLoaiKinh.setName("Mã thuộc tính"); // NOI18N

        txt_NamesLoaiKinh.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txt_NamesLoaiKinh.setName("Tên thuộc tính"); // NOI18N

        rdb_0_LoaiKinh.setText("Hoạt động");

        rdb_1_LoaiKinh.setText("Không hoạt động");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 501, Short.MAX_VALUE)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(jPanel5Layout.createSequentialGroup()
                                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(txt_NamesLoaiKinh, javax.swing.GroupLayout.PREFERRED_SIZE, 299, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel5Layout.createSequentialGroup()
                                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addComponent(txt_IDLoaiKinh, javax.swing.GroupLayout.PREFERRED_SIZE, 301, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel5Layout.createSequentialGroup()
                                        .addComponent(btn_insertLoaiKinh)
                                        .addGap(18, 18, 18)
                                        .addComponent(btn_updateLoaiKinh))
                                    .addGroup(jPanel5Layout.createSequentialGroup()
                                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(rdb_0_LoaiKinh)))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel5Layout.createSequentialGroup()
                                        .addComponent(btn_deleteLoaiKinh)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(btn_newLoaiKinh))
                                    .addComponent(rdb_1_LoaiKinh))))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 294, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(txt_IDLoaiKinh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(txt_NamesLoaiKinh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(rdb_0_LoaiKinh)
                    .addComponent(rdb_1_LoaiKinh))
                .addGap(21, 21, 21)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_insertLoaiKinh)
                    .addComponent(btn_updateLoaiKinh)
                    .addComponent(btn_deleteLoaiKinh)
                    .addComponent(btn_newLoaiKinh))
                .addGap(24, 24, 24))
        );

        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Kiểu dáng", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 13))); // NOI18N

        tbl_KieuDang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã Kiểu dáng", "Tên Kiểu dáng", "Trạng thái"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbl_KieuDang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_KieuDangMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(tbl_KieuDang);

        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel12.setText("Mã Kiểu dáng:");
        jLabel12.setName("Mã Kiểu dáng"); // NOI18N

        txt_IDKieuDang.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txt_IDKieuDang.setName("Mã giá trị"); // NOI18N

        jLabel13.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel13.setText("Tên Kiểu dáng:");
        jLabel13.setName("Tên Kiểu dáng"); // NOI18N

        txt_NamesKieuDang.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txt_NamesKieuDang.setName("Tên giá trị"); // NOI18N

        jLabel14.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel14.setText("Trạng thái:");

        rdb_0_KieuDang.setText("Hoạt động");

        rdb_1_KieuDang.setText("Không hoạt động");

        btn_insertKieuDang.setBackground(new java.awt.Color(11, 181, 217));
        btn_insertKieuDang.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btn_insertKieuDang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/baseline_add_black_24dp.png"))); // NOI18N
        btn_insertKieuDang.setText("Thêm");
        btn_insertKieuDang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_insertKieuDangActionPerformed(evt);
            }
        });

        btn_updateKieuDang.setBackground(new java.awt.Color(11, 181, 217));
        btn_updateKieuDang.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btn_updateKieuDang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/baseline_sync_alt_black_24dp.png"))); // NOI18N
        btn_updateKieuDang.setText("Sửa");
        btn_updateKieuDang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_updateKieuDangActionPerformed(evt);
            }
        });

        btn_deleteKieuDang.setBackground(new java.awt.Color(11, 181, 217));
        btn_deleteKieuDang.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btn_deleteKieuDang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/baseline_delete_forever_black_24dp.png"))); // NOI18N
        btn_deleteKieuDang.setText("Xóa");
        btn_deleteKieuDang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_deleteKieuDangActionPerformed(evt);
            }
        });

        btn_newKieuDang.setBackground(new java.awt.Color(11, 181, 217));
        btn_newKieuDang.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btn_newKieuDang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/baseline_open_in_new_black_24dp.png"))); // NOI18N
        btn_newKieuDang.setText("Làm mới");
        btn_newKieuDang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_newKieuDangActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 517, Short.MAX_VALUE)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(rdb_0_KieuDang)
                                .addGap(64, 64, 64)
                                .addComponent(rdb_1_KieuDang))
                            .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(jPanel7Layout.createSequentialGroup()
                                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(txt_NamesKieuDang, javax.swing.GroupLayout.PREFERRED_SIZE, 299, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel7Layout.createSequentialGroup()
                                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addComponent(txt_IDKieuDang, javax.swing.GroupLayout.PREFERRED_SIZE, 301, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(btn_insertKieuDang)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btn_updateKieuDang)
                .addGap(18, 18, 18)
                .addComponent(btn_deleteKieuDang)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btn_newKieuDang, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 294, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 81, Short.MAX_VALUE)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(txt_IDKieuDang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(txt_NamesKieuDang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(rdb_0_KieuDang)
                    .addComponent(rdb_1_KieuDang))
                .addGap(18, 18, 18)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_insertKieuDang)
                    .addComponent(btn_updateKieuDang)
                    .addComponent(btn_deleteKieuDang)
                    .addComponent(btn_newKieuDang))
                .addGap(28, 28, 28))
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        tabs_QLSP.addTab("Loại Kính & Kiểu dáng", jPanel3);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(pnlSide, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(tabs_QLSP, javax.swing.GroupLayout.PREFERRED_SIZE, 1134, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(304, 304, 304))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlSide, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(tabs_QLSP)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 1410, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void lblDanhMucMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblDanhMucMouseClicked
        tabs_QLSP.setSelectedIndex(1);
    }//GEN-LAST:event_lblDanhMucMouseClicked

    private void lblTrangChuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblTrangChuMouseClicked
        tabs_QLSP.setSelectedIndex(0);
    }//GEN-LAST:event_lblTrangChuMouseClicked

    private void lblTrangChuMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblTrangChuMouseEntered
        lblTrangChu.setForeground(Color.black);
    }//GEN-LAST:event_lblTrangChuMouseEntered

    private void lblTrangChuMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblTrangChuMouseExited
        lblTrangChu.setForeground(Color.white);
    }//GEN-LAST:event_lblTrangChuMouseExited

    private void pnlTrangChuMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlTrangChuMouseEntered

    }//GEN-LAST:event_pnlTrangChuMouseEntered

    private void pnlTrangChuMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlTrangChuMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_pnlTrangChuMouseExited

    private void lblDanhMucMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblDanhMucMouseEntered
        lblDanhMuc.setForeground(Color.black);
    }//GEN-LAST:event_lblDanhMucMouseEntered

    private void lblDanhMucMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblDanhMucMouseExited
        lblDanhMuc.setForeground(Color.white);
    }//GEN-LAST:event_lblDanhMucMouseExited

    private void lblThuocTinhMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblThuocTinhMouseEntered
        lblThuocTinh.setForeground(Color.black);
    }//GEN-LAST:event_lblThuocTinhMouseEntered

    private void lblThuocTinhMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblThuocTinhMouseExited
        lblThuocTinh.setForeground(Color.white);
    }//GEN-LAST:event_lblThuocTinhMouseExited

    private void lblThuocTinhMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblThuocTinhMouseClicked
        tabs_QLSP.setSelectedIndex(3);
    }//GEN-LAST:event_lblThuocTinhMouseClicked

    private void lblLoaiSPMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblLoaiSPMouseClicked
        tabs_QLSP.setSelectedIndex(2);
    }//GEN-LAST:event_lblLoaiSPMouseClicked

    private void lblLoaiSPMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblLoaiSPMouseEntered
        lblLoaiSP.setForeground(Color.black);
    }//GEN-LAST:event_lblLoaiSPMouseEntered

    private void lblLoaiSPMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblLoaiSPMouseExited
        lblLoaiSP.setForeground(Color.white);
    }//GEN-LAST:event_lblLoaiSPMouseExited

    private void btn_newKieuDangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_newKieuDangActionPerformed
        if (Helper.DialogHelper.confirm(this, "Bạn thực sự muốn làm mới?")) {
            clearFormKieuDang();
        }
    }//GEN-LAST:event_btn_newKieuDangActionPerformed

    private void btn_deleteKieuDangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_deleteKieuDangActionPerformed
        if (_rowKieuDang < 0) {
            DialogHelper.alert(this, "Mời bạn chọn Kiểu dáng cần xóa");
            return;
        }
        deleteKieuDang();
    }//GEN-LAST:event_btn_deleteKieuDangActionPerformed

    private void btn_updateKieuDangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_updateKieuDangActionPerformed
        if (_rowKieuDang < 0) {
            DialogHelper.alert(this, "Mời bạn chọn Kiểu dáng cần sửa");
            return;
        }
        if (UtilityHelper.checkNullText(txt_IDKieuDang) && UtilityHelper.checkNullText(txt_NamesKieuDang)) {
            if (UtilityHelper.checkIDKieuDang(txt_IDKieuDang) && UtilityHelper.checkNamesKieuDang(txt_NamesKieuDang)) {
                if (!txt_IDKieuDang.getText().equalsIgnoreCase(String.valueOf(tbl_KieuDang.getValueAt(_rowKieuDang, 0)))) {
                    DialogHelper.alert(this, "Bạn không được thay đổi Mã Kiểu dáng");
                    return;
                } else {
                    updateKieuDang();
                }
            }
        }
    }//GEN-LAST:event_btn_updateKieuDangActionPerformed

    private void btn_insertKieuDangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_insertKieuDangActionPerformed
        if (UtilityHelper.checkNullText(txt_IDKieuDang) && UtilityHelper.checkNullText(txt_NamesKieuDang)) {
            if (UtilityHelper.checkIDKieuDang(txt_IDKieuDang) && UtilityHelper.checkNamesKieuDang(txt_NamesKieuDang)) {
                if (checkIDKieuDang(txt_IDKieuDang.getText()) && checkNamesKieuDang(txt_NamesKieuDang.getText())) {
                    insertKieuDang();
                }
            }
        }
    }//GEN-LAST:event_btn_insertKieuDangActionPerformed

    private void tbl_KieuDangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_KieuDangMouseClicked
        this._rowKieuDang = tbl_KieuDang.rowAtPoint(evt.getPoint());
        editKieuDang();
    }//GEN-LAST:event_tbl_KieuDangMouseClicked

    private void btn_newLoaiKinhActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_newLoaiKinhActionPerformed
        if (Helper.DialogHelper.confirm(this, "Bạn thực sự muốn làm mới?")) {
            clearFormLoaiKinh();
        }
    }//GEN-LAST:event_btn_newLoaiKinhActionPerformed

    private void btn_deleteLoaiKinhActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_deleteLoaiKinhActionPerformed
        if (_rowLoaiKinh < 0) {
            DialogHelper.alert(this, "Mời bạn chọn Loại kính cần xóa");
            return;
        }
        deleteLoaiKinh();
    }//GEN-LAST:event_btn_deleteLoaiKinhActionPerformed

    private void btn_updateLoaiKinhActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_updateLoaiKinhActionPerformed
        if (_rowLoaiKinh < 0) {
            DialogHelper.alert(this, "Mời bạn chọn Loại kính cần sửa");
            return;
        }
        if (UtilityHelper.checkNullText(txt_IDLoaiKinh) && UtilityHelper.checkNullText(txt_NamesLoaiKinh)) {
            if (UtilityHelper.checkIDLoaiKinh(txt_IDLoaiKinh) && UtilityHelper.checkNamesLoaiKinh(txt_NamesLoaiKinh)) {
                if (!txt_IDLoaiKinh.getText().equalsIgnoreCase(String.valueOf(tbl_LoaiKinh.getValueAt(_rowLoaiKinh, 0)))) {
                    DialogHelper.alert(this, "Bạn không được thay đổi Mã Loại kính");
                    return;
                } else {
                    updateLoaiKinh();
                }
            }
        }
    }//GEN-LAST:event_btn_updateLoaiKinhActionPerformed

    private void btn_insertLoaiKinhActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_insertLoaiKinhActionPerformed
        if (UtilityHelper.checkNullText(txt_IDLoaiKinh) && UtilityHelper.checkNullText(txt_NamesLoaiKinh)) {
            if (UtilityHelper.checkIDLoaiKinh(txt_IDLoaiKinh) && UtilityHelper.checkNamesLoaiKinh(txt_NamesLoaiKinh)) {
                if (checkIDLoaiKinh(txt_IDLoaiKinh.getText()) && checkNamesLoaiKinh(txt_NamesLoaiKinh.getText())) {
                    insertLoaiKinh();
                }
            }
        }
    }//GEN-LAST:event_btn_insertLoaiKinhActionPerformed

    private void tbl_LoaiKinhMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_LoaiKinhMouseClicked
        this._rowLoaiKinh = tbl_LoaiKinh.rowAtPoint(evt.getPoint());
        editLoaiKinh();
    }//GEN-LAST:event_tbl_LoaiKinhMouseClicked

    private void btn_newKichThuocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_newKichThuocActionPerformed
        if (Helper.DialogHelper.confirm(this, "Bạn thực sự muốn làm mới?")) {
            clearFormKichThuoc();
        }
    }//GEN-LAST:event_btn_newKichThuocActionPerformed

    private void btn_deleteKichThuocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_deleteKichThuocActionPerformed
        if (_rowKichThuoc < 0) {
            DialogHelper.alert(this, "Mời bạn chọn Kích thước cần xóa");
            return;
        }
        deleteKichThuoc();
    }//GEN-LAST:event_btn_deleteKichThuocActionPerformed

    private void btn_updateKichThuocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_updateKichThuocActionPerformed
        if (_rowKichThuoc < 0) {
            DialogHelper.alert(this, "Mời bạn chọn Loại SP cần sửa");
            return;
        }
        if (UtilityHelper.checkNullText(txt_IDKichThuoc) && UtilityHelper.checkNullText(txt_KichThuoc)) {
            if (UtilityHelper.checkNamesKichThuoc(txt_KichThuoc)) {
                if (!txt_IDKichThuoc.getText().equalsIgnoreCase(String.valueOf(tbl_KichThuoc.getValueAt(_rowKichThuoc, 0)))) {
                    DialogHelper.alert(this, "Bạn không được thay đổi Mã Kích thước");
                    return;
                } else {
                    updateKichThuoc();
                }
            }
        }
    }//GEN-LAST:event_btn_updateKichThuocActionPerformed

    private void btn_insertKichThuocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_insertKichThuocActionPerformed
        if (UtilityHelper.checkNullText(txt_IDKichThuoc) && UtilityHelper.checkNullText(txt_KichThuoc)) {
            if (UtilityHelper.checkIDKichThuoc(txt_IDKichThuoc) && UtilityHelper.checkNamesKichThuoc(txt_KichThuoc)) {
                if (checkIDKichThuoc(txt_IDKichThuoc.getText()) && checkNamesKichThuoc(txt_KichThuoc.getText())) {
                    insertKichThuoc();
                }
            }
        }
    }//GEN-LAST:event_btn_insertKichThuocActionPerformed

    private void tbl_KichThuocMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_KichThuocMouseClicked
        this._rowKichThuoc = tbl_KichThuoc.rowAtPoint(evt.getPoint());
        this.editKichThuoc();
    }//GEN-LAST:event_tbl_KichThuocMouseClicked

    private void btn_insertDVTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_insertDVTActionPerformed
        if (UtilityHelper.checkNullText(txt_IDDVT) && UtilityHelper.checkNullText(txt_DVT)) {
            if (UtilityHelper.checkIdDVT(txt_IDDVT) && UtilityHelper.checkNamesDVT(txt_DVT)) {
                if (checkIDDVT(txt_IDDVT.getText()) && checkNamesDVT(txt_DVT.getText())) {
                    insertDonViTinh();
                }
            }
        }
    }//GEN-LAST:event_btn_insertDVTActionPerformed

    private void btn_updateDVTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_updateDVTActionPerformed
        if (_rowDVT < 0) {
            DialogHelper.alert(this, "Mời bạn chọn Đơn vị tính cần sửa");
            return;
        }
        if (UtilityHelper.checkNullText(txt_IDDVT) && UtilityHelper.checkNullText(txt_DVT)) {
            if (UtilityHelper.checkIdGioiTinh(txt_IDDVT) && UtilityHelper.checkNamesGioiTinh(txt_DVT)) {
                if (!txt_IDDVT.getText().equalsIgnoreCase(String.valueOf(tbl_DonViTinh.getValueAt(_rowDVT, 0)))) {
                    DialogHelper.alert(this, "Bạn không được thay đổi Mã Đơn vị tính");
                    return;
                } else {
                    updateDonViTinh();
                }
            }
        }
    }//GEN-LAST:event_btn_updateDVTActionPerformed

    private void btn_deleteDVTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_deleteDVTActionPerformed
        if (_rowDVT < 0) {
            DialogHelper.alert(this, "Mời bạn chọn Đơn vị tính cần xóa");
            return;
        }
        deleteDonViTinh();
    }//GEN-LAST:event_btn_deleteDVTActionPerformed

    private void btn_newDVTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_newDVTActionPerformed
        if (Helper.DialogHelper.confirm(this, "Bạn thực sự muốn làm mới?")) {
            clearFormDonViTinh();
        }
    }//GEN-LAST:event_btn_newDVTActionPerformed

    private void tbl_DonViTinhMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_DonViTinhMouseClicked
        this._rowDVT = tbl_DonViTinh.rowAtPoint(evt.getPoint());
        this.editDonViTinh();
    }//GEN-LAST:event_tbl_DonViTinhMouseClicked

    private void btn_newGioiTinhActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_newGioiTinhActionPerformed
        if (Helper.DialogHelper.confirm(this, "Bạn thực sự muốn làm mới?")) {
            clearFormGioiTinh();
        }
    }//GEN-LAST:event_btn_newGioiTinhActionPerformed

    private void btn_deleteGioiTinhActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_deleteGioiTinhActionPerformed
        if (_rowGioiTinh < 0) {
            DialogHelper.alert(this, "Mời bạn chọn Giới tính cần xóa");
            return;
        }
        deleteGioiTinh();
    }//GEN-LAST:event_btn_deleteGioiTinhActionPerformed

    private void btn_updateGioiTinhActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_updateGioiTinhActionPerformed
        if (_rowGioiTinh < 0) {
            DialogHelper.alert(this, "Mời bạn chọn Giới tính cần sửa");
            return;
        }
        if (UtilityHelper.checkNullText(txt_IDGioiTinh) && UtilityHelper.checkNullText(txt_GioiTinh)) {
            if (UtilityHelper.checkIdGioiTinh(txt_IDGioiTinh) && UtilityHelper.checkNamesGioiTinh(txt_GioiTinh)) {
                if (!txt_IDGioiTinh.getText().equalsIgnoreCase(String.valueOf(tbl_GioiTinh.getValueAt(_rowGioiTinh, 0)))) {
                    DialogHelper.alert(this, "Bạn không được thay đổi Mã Giới tính");
                    return;
                } else {
                    updateGioiTinh();
                }
            }
        }
    }//GEN-LAST:event_btn_updateGioiTinhActionPerformed

    private void btn_insertGioiTinhActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_insertGioiTinhActionPerformed
        if (UtilityHelper.checkNullText(txt_IDGioiTinh) && UtilityHelper.checkNullText(txt_GioiTinh)) {
            if (UtilityHelper.checkIdGioiTinh(txt_IDGioiTinh) && UtilityHelper.checkNamesGioiTinh(txt_GioiTinh)) {
                if (checkIDGioiTinh(txt_IDGioiTinh.getText()) && checkNamesGioiTinh(txt_GioiTinh.getText())) {
                    insertGioiTinh();
                }
            }
        }
    }//GEN-LAST:event_btn_insertGioiTinhActionPerformed

    private void tbl_GioiTinhMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_GioiTinhMouseClicked
        this._rowGioiTinh = tbl_GioiTinh.rowAtPoint(evt.getPoint());
        this.editGioiTinh();
    }//GEN-LAST:event_tbl_GioiTinhMouseClicked

    private void btn_insertLoaiHangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_insertLoaiHangActionPerformed
        if (UtilityHelper.checkNullText(txt_IDLoaiHang) && UtilityHelper.checkNullText(txt_TenLoaiHang)) {
            if (UtilityHelper.checkIdLoaiHang(txt_IDLoaiHang) && UtilityHelper.checkNamesLoaiHang(txt_TenLoaiHang)) {
                if (checkIDLoaiHang(txt_IDLoaiHang.getText()) && checkNamesLoaiHang(txt_TenLoaiHang.getText())) {
                    insertLoaiHang();
                }
            }
        }
    }//GEN-LAST:event_btn_insertLoaiHangActionPerformed

    private void btn_updateLoaiHangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_updateLoaiHangActionPerformed
        if (_rowLoaiHang < 0) {
            DialogHelper.alert(this, "Mời bạn chọn Loại hàng cần sửa");
            return;
        }
        if (UtilityHelper.checkNullText(txt_IDLoaiHang) && UtilityHelper.checkNullText(txt_TenLoaiHang)) {
            if (UtilityHelper.checkIdLoaiHang(txt_IDLoaiHang) && UtilityHelper.checkNamesLoaiHang(txt_TenLoaiHang)) {
                if (!txt_IDLoaiHang.getText().equalsIgnoreCase(String.valueOf(tbl_LoaiHang.getValueAt(_rowLoaiHang, 0)))) {
                    DialogHelper.alert(this, "Bạn không được thay đổi Mã Loại hàng");
                    return;
                } else {
                    updateLoaiHang();
                }
            }
        }
    }//GEN-LAST:event_btn_updateLoaiHangActionPerformed

    private void btn_deleteLoaiHangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_deleteLoaiHangActionPerformed
        if (_rowLoaiHang < 0) {
            DialogHelper.alert(this, "Mời bạn chọn Loại hàng cần xóa");
            return;
        }
        deleteLoaiHang();
    }//GEN-LAST:event_btn_deleteLoaiHangActionPerformed

    private void btn_newLoaiHangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_newLoaiHangActionPerformed
        if (Helper.DialogHelper.confirm(this, "Bạn thực sự muốn làm mới?")) {
            clearFormLoaiHang();
        }
    }//GEN-LAST:event_btn_newLoaiHangActionPerformed

    private void tbl_LoaiHangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_LoaiHangMouseClicked
        this._rowLoaiHang = tbl_LoaiHang.rowAtPoint(evt.getPoint());
        this.editLoaiHang();
    }//GEN-LAST:event_tbl_LoaiHangMouseClicked

    private void btn_newHangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_newHangActionPerformed
        if (Helper.DialogHelper.confirm(this, "Bạn thực sự muốn làm mới?")) {
            clearFormHang();
        }
    }//GEN-LAST:event_btn_newHangActionPerformed

    private void btn_deleteHangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_deleteHangActionPerformed

        if (_rowHang < 0) {
            DialogHelper.alert(this, "Mời bạn chọn Hãng cần xóa");
            return;
        }
        deleteHang();
    }//GEN-LAST:event_btn_deleteHangActionPerformed

    private void btn_updateHangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_updateHangActionPerformed

        if (_rowHang < 0) {
            DialogHelper.alert(this, "Mời bạn chọn Hãng cần sửa");
            return;
        }
        if (UtilityHelper.checkNullText(txt_IDHang) && UtilityHelper.checkNullText(txt_TenHang)) {
            if (UtilityHelper.checkIdHang(txt_IDHang) && UtilityHelper.checkTenHang(txt_TenHang)) {
                if (!txt_IDHang.getText().equalsIgnoreCase(String.valueOf(tbl_Hang.getValueAt(_rowHang, 0)))) {
                    DialogHelper.alert(this, "Bạn không được thay đổi Mã Hãng");
                    return;
                } else {
                    updateHang();
                }
            }
        }
    }//GEN-LAST:event_btn_updateHangActionPerformed

    private void btn_insertHangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_insertHangActionPerformed
        if (UtilityHelper.checkNullText(txt_IDHang) && UtilityHelper.checkNullText(txt_TenHang)) {
            if (UtilityHelper.checkIdHang(txt_IDHang) && UtilityHelper.checkTenHang(txt_TenHang)) {
                if (checkIDProduct(txt_IDHang.getText()) && checkNamesProduct(txt_TenHang.getText())) {
                    insertHang();
                }
            }
        }
    }//GEN-LAST:event_btn_insertHangActionPerformed

    private void tbl_HangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_HangMouseClicked
        this._rowHang = tbl_Hang.rowAtPoint(evt.getPoint());
        this.editHang();
    }//GEN-LAST:event_tbl_HangMouseClicked

    private void cbb_LoaiHangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbb_LoaiHangActionPerformed
        this.selectCbbLoaiHang();
        //        fillComboBoxIDVariant_ProductVarint();
    }//GEN-LAST:event_cbb_LoaiHangActionPerformed

    private void lbl_imagesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_imagesMouseClicked
        try {
            _fd.setVisible(true); //mở hộp thoại chọn file
            _file = new File(_fd.getDirectory() + _fd.getFile());
            if (!String.valueOf(_file).equals("nullnull")) {
                Helper.Image.save(_file);
                Image image = ImageIO.read(_file);
                _images = _file.getName();
                lbl_images.setIcon(new ImageIcon(image.getScaledInstance(180, 220, 0)));
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_lbl_imagesMouseClicked

    private void btn_autoBarCodeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_autoBarCodeActionPerformed
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        String random = uuid.substring(0, 5);
        txt_barcode.setText(random);
//        try {
//
//            Linear barcode = new Linear();
//            barcode.setType(Linear.CODE128B);
//            barcode.setData(txt_Quarity.getText());
//            barcode.setI(11.0f);
//            String fname = txt_Quarity.getText();
//            barcode.renderBarcode("barcode\\" + fname + ".png");
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }//GEN-LAST:event_btn_autoBarCodeActionPerformed

    private void btn_newVariantValuesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_newVariantValuesActionPerformed
        if (Helper.DialogHelper.confirm(this, "Bạn thực sự muốn xóa Sản phẩm này?")) {
            txt_IDSanPham.setText("");
            txt_TenSP.setText("");
            txt_quantity.setText("");
            txt_price.setText("");
            txt_barcode.setText("");
            _images = "";
            lbl_images.setIcon(null);
        }
    }//GEN-LAST:event_btn_newVariantValuesActionPerformed

    private void btn_deleteVariantValuesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_deleteVariantValuesActionPerformed
        if (Helper.DialogHelper.confirm(this, "Bạn thực sự muốn xóa Sản phẩm này?")) {
            try {
                deleteChiTietSanPham();
                deleteSanPham();
                Helper.DialogHelper.alert(this, "Xóa thành công!");
                this.fillTableChiTietSanPham();
                _rowKichThuoc = -1;
            } catch (Exception e) {
//                e.printStackTrace();
                Helper.DialogHelper.alert(this, "Xóa thất bại!");
            }
        }
    }//GEN-LAST:event_btn_deleteVariantValuesActionPerformed

    private void btn_updateVariantValuesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_updateVariantValuesActionPerformed
        if (Helper.DialogHelper.confirm(this, "Bạn thực sự muốn sửa Sản phẩm này?")) {
            try {
                updateSanPham();
                updateChiTietSanPham();
                Helper.DialogHelper.alert(this, "Cập nhật thành công!");
                this.fillTableChiTietSanPham();
                _rowKichThuoc = -1;
            } catch (Exception e) {
//                e.printStackTrace();
                Helper.DialogHelper.alert(this, "Cập nhật thất bại!");
            }
        }
    }//GEN-LAST:event_btn_updateVariantValuesActionPerformed

    private void btn_insertVariantValuesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_insertVariantValuesActionPerformed
        try {
            insertSanPham();
            insertChiTietSanPham();
            try {

                Linear barcode = new Linear();
                barcode.setType(Linear.CODE128B);
                barcode.setData(txt_barcode.getText());
                barcode.setI(11.0f);
                String fname = txt_barcode.getText();
                barcode.renderBarcode("barcode\\" + fname + ".png");

            } catch (Exception e) {
                e.printStackTrace();
            }
            Helper.DialogHelper.alert(this, "Thêm mới thành công!");
            this.fillTableChiTietSanPham();
//            _rowKichThuoc = -1;
        } catch (Exception e) {
//            e.printStackTrace();
            Helper.DialogHelper.alert(this, "Thêm mới thất bại!");
        }

        //        insertVarianValues();
        ////        Product product = (Product) cbb_Product.getSelectedItem();
        ////        System.out.println(product.getID_Product());
        //        try {
        //
        //            Linear barcode = new Linear();
        //            barcode.setType(Linear.CODE128B);
        //            barcode.setData(txt_barcode.getText());
        //            barcode.setI(11.0f);
        //            String fname = txt_barcode.getText();
        //            barcode.renderBarcode("barcode\\" + fname + ".png");
        //
        //        } catch (Exception e) {
        //            e.printStackTrace();
        //        }
    }//GEN-LAST:event_btn_insertVariantValuesActionPerformed

    private void tbl_CTSPMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_CTSPMouseClicked
        if (evt.getButton() == java.awt.event.MouseEvent.BUTTON3) {
            System.out.println("hiếu");

        }
//        for (int i = 0; i < cbb_Hang.getItemCount(); i++) {
//            String a = String.valueOf(cbb_Hang.getItemAt(i));
//            System.out.println(a);
////            if (hang.equals(cbb_Hang.getItemAt(i) + "")) {
////                cbb_Hang.setSelectedIndex(i);
//        }
//        int index = tbl_CTSP.getSelectedRow();
        //        Variant_values variant_values = _IQLySanPhamService.findByIdVariant_VariantValues(String.valueOf(tbl_Variant_values2.getValueAt(index, 1)));
        //        List<Variant_values> listVariant_valueses = _IQLySanPhamService.selectByIDVariant_VariantValues(String.valueOf(tbl_Variant_values2.getValueAt(index, 1)));
        //        Product_variant product_variant = _IQLySanPhamService.findByIdProduct_variant(String.valueOf(tbl_Variant_values2.getValueAt(index, 1)));
        //        List<String> lstOptions = new ArrayList<>();
        //        lstOptions.add(variant_values.getID_values());
        //        for (int i = 0; i < listVariant_valueses.size(); i++) {
        //            System.out.println(listVariant_valueses.get(index));
        //        }
        //        System.out.println(variant_values);
        //        System.out.println(listVariant_valueses.size());
        //        int countOptions = _IQLySanPhamService.countOptions_VariantValues(String.valueOf(tbl_Variant_values2.getValueAt(index, 1)));
        if (evt.getClickCount() == 2) {
            //            if (index >= 0) {
            //                cbb_IDVariant.setSelectedItem(product_variant.getSKU_ID());
            //                txt_Quarity.setText(String.valueOf(tbl_Variant_values2.getValueAt(index, 4)));
            //                txt_Price.setText(String.valueOf(tbl_Variant_values2.getValueAt(index, 5)));
            //                txt_barcode.setText(variant_values.getBarcode());
            //                String url = "images\\" + variant_values.getImages();
            //                ImageIcon imageIcon = new ImageIcon(url);
            //                Image img = imageIcon.getImage();
            //                lbl_images.setIcon(new ImageIcon(img.getScaledInstance(140, 200, 0)));
            //                //                Panel_ThuocTinhs = new JPanel();
            //                //                Panel_ThuocTinhs.removeAll();
            ////                SrollPane_Options = new JScrollPane();
            //                Panel_ThuocTinhs.removeAll();
            //                _thuocTinhs = 0;
            //                Panel_ThuocTinhs.repaint();
            //                for (int i = 0; i < countOptions; i++) {
            //                    actionOptions();
            //                    Options o = _IQLySanPhamService.findByIdOptions(listVariant_valueses.get(i).getID_Options());
            //                    Options_values o1 = _IQLySanPhamService.findByIdValues_Options_values(listVariant_valueses.get(i).getID_values());
            ////                    System.out.println(o.getNames());
            ////                    System.out.println(o1.getNames());
            ////                    _cbx.setSelectedItem(o.getNames());
            //
            //                    for (int j = 1; j <= _cbx.getItemCount(); j++) {
            ////                        System.out.println(_cbx.getItemAt(j));
            //                        if (o.getNames().equalsIgnoreCase(_cbx.getItemAt(j) + "")) {
            //                            _cbx.setSelectedIndex(j);
            //                        }
            //                    }
            //                    for (int j = 0; j < _cbx2.getItemCount(); j++) {
            //                        if (o1.getNames().equalsIgnoreCase(_cbx2.getItemAt(j) + "")) {
            //                            _cbx2.setSelectedIndex(j);
            //                        }
            //                    }
            ////                    System.out.println(_cbx.getItemCount());
            //                }
            //                tab_VariantValues.setSelectedIndex(1);
            //            }
            //        }
            ////        if (tab_VariantValues.getSelectedIndex() == 0) {
            ////            Panel_ThuocTinhs = new JPanel();
            this.editSanPham();
            this.editChiTietSanPham();
            tab_CTSP.setSelectedIndex(1);
        }
    }//GEN-LAST:event_tbl_CTSPMouseClicked

    private void tbl_MauSacMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_MauSacMouseClicked
        this._rowMauSac = tbl_MauSac.rowAtPoint(evt.getPoint());
        this.editMauSac();
    }//GEN-LAST:event_tbl_MauSacMouseClicked

    private void btn_newMauSacActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_newMauSacActionPerformed
        if (Helper.DialogHelper.confirm(this, "Bạn thực sự muốn làm mới?")) {
            clearFormMauSac();
        }
    }//GEN-LAST:event_btn_newMauSacActionPerformed

    private void btn_deleteMauSacActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_deleteMauSacActionPerformed
        if (_rowMauSac < 0) {
            DialogHelper.alert(this, "Mời bạn chọn Màu sắc cần xóa");
            return;
        }
        deleteMauSac();
    }//GEN-LAST:event_btn_deleteMauSacActionPerformed

    private void btn_updateMauSacActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_updateMauSacActionPerformed
        if (_rowMauSac < 0) {
            DialogHelper.alert(this, "Mời bạn chọn Màu sắc cần sửa");
            return;
        }
        if (UtilityHelper.checkNullText(txt_IDMauSac) && UtilityHelper.checkNullText(txt_MauSac)) {
            if (UtilityHelper.checkNamesMauSac(txt_MauSac)) {
                if (!txt_IDMauSac.getText().equalsIgnoreCase(String.valueOf(tbl_MauSac.getValueAt(_rowMauSac, 0)))) {
                    DialogHelper.alert(this, "Bạn không được thay đổi Mã Màu sắc");
                    return;
                } else {
                    updateMauSac();
                }
            }
        }
    }//GEN-LAST:event_btn_updateMauSacActionPerformed

    private void btn_insertMauSacActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_insertMauSacActionPerformed
        if (UtilityHelper.checkNullText(txt_IDMauSac) && UtilityHelper.checkNullText(txt_MauSac)) {
            if (UtilityHelper.checkIDMauSac(txt_IDMauSac) && UtilityHelper.checkNamesMauSac(txt_MauSac)) {
                if (checkIDMauSac(txt_IDMauSac.getText()) && checkNamesMauSac(txt_MauSac.getText())) {
                    insertMauSac();
                }
            }
        }
    }//GEN-LAST:event_btn_insertMauSacActionPerformed

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
            java.util.logging.Logger.getLogger(FormSanPham1.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FormSanPham1.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FormSanPham1.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FormSanPham1.class
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
                FormSanPham1 dialog = new FormSanPham1(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btn_autoBarCode;
    private javax.swing.JButton btn_deleteDVT;
    private javax.swing.JButton btn_deleteGioiTinh;
    private javax.swing.JButton btn_deleteHang;
    private javax.swing.JButton btn_deleteKichThuoc;
    private javax.swing.JButton btn_deleteKieuDang;
    private javax.swing.JButton btn_deleteLoaiHang;
    private javax.swing.JButton btn_deleteLoaiKinh;
    private javax.swing.JButton btn_deleteMauSac;
    private javax.swing.JButton btn_deleteVariantValues;
    private javax.swing.JButton btn_insertDVT;
    private javax.swing.JButton btn_insertGioiTinh;
    private javax.swing.JButton btn_insertHang;
    private javax.swing.JButton btn_insertKichThuoc;
    private javax.swing.JButton btn_insertKieuDang;
    private javax.swing.JButton btn_insertLoaiHang;
    private javax.swing.JButton btn_insertLoaiKinh;
    private javax.swing.JButton btn_insertMauSac;
    private javax.swing.JButton btn_insertVariantValues;
    private javax.swing.JButton btn_newDVT;
    private javax.swing.JButton btn_newGioiTinh;
    private javax.swing.JButton btn_newHang;
    private javax.swing.JButton btn_newKichThuoc;
    private javax.swing.JButton btn_newKieuDang;
    private javax.swing.JButton btn_newLoaiHang;
    private javax.swing.JButton btn_newLoaiKinh;
    private javax.swing.JButton btn_newMauSac;
    private javax.swing.JButton btn_newVariantValues;
    private javax.swing.JButton btn_updateDVT;
    private javax.swing.JButton btn_updateGioiTinh;
    private javax.swing.JButton btn_updateHang;
    private javax.swing.JButton btn_updateKichThuoc;
    private javax.swing.JButton btn_updateKieuDang;
    private javax.swing.JButton btn_updateLoaiHang;
    private javax.swing.JButton btn_updateLoaiKinh;
    private javax.swing.JButton btn_updateMauSac;
    private javax.swing.JButton btn_updateVariantValues;
    private javax.swing.JComboBox<String> cbb_DVT;
    private javax.swing.JComboBox<String> cbb_Hang;
    private javax.swing.JComboBox<String> cbb_KichThuoc;
    private javax.swing.JComboBox<String> cbb_KieuDang;
    private javax.swing.JComboBox<String> cbb_LoaiHang;
    private javax.swing.JComboBox<String> cbb_LoaiHang2;
    private javax.swing.JComboBox<String> cbb_LoaiKinh;
    private javax.swing.JComboBox<String> cbb_MauSac;
    private javax.swing.JComboBox<String> cbb_gioiTinh;
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
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JLabel lblDanhMuc;
    private javax.swing.JLabel lblLoaiSP;
    private javax.swing.JLabel lblThuocTinh;
    private javax.swing.JLabel lblTrangChu;
    private javax.swing.JLabel lbl_images;
    private javax.swing.JPanel pnlSide;
    private javax.swing.JPanel pnlTrangChu;
    private javax.swing.JRadioButton rdb_0_DVT;
    private javax.swing.JRadioButton rdb_0_GioiTinh;
    private javax.swing.JRadioButton rdb_0_Hang;
    private javax.swing.JRadioButton rdb_0_KichThuoc;
    private javax.swing.JRadioButton rdb_0_KieuDang;
    private javax.swing.JRadioButton rdb_0_LoaiHang;
    private javax.swing.JRadioButton rdb_0_LoaiKinh;
    private javax.swing.JRadioButton rdb_0_MauSac;
    private javax.swing.JRadioButton rdb_0_SanPham;
    private javax.swing.JRadioButton rdb_1_DVT;
    private javax.swing.JRadioButton rdb_1_GioiTinh;
    private javax.swing.JRadioButton rdb_1_Hang;
    private javax.swing.JRadioButton rdb_1_KichThuoc;
    private javax.swing.JRadioButton rdb_1_KieuDang;
    private javax.swing.JRadioButton rdb_1_LoaiHang;
    private javax.swing.JRadioButton rdb_1_LoaiKinh;
    private javax.swing.JRadioButton rdb_1_MauSac;
    private javax.swing.JRadioButton rdb_1_SanPham;
    private javax.swing.JTabbedPane tab_CTSP;
    private javax.swing.JTabbedPane tabs_QLSP;
    private javax.swing.JTable tbl_CTSP;
    private javax.swing.JTable tbl_DonViTinh;
    private javax.swing.JTable tbl_GioiTinh;
    private javax.swing.JTable tbl_Hang;
    private javax.swing.JTable tbl_KichThuoc;
    private javax.swing.JTable tbl_KieuDang;
    private javax.swing.JTable tbl_LoaiHang;
    private javax.swing.JTable tbl_LoaiKinh;
    private javax.swing.JTable tbl_MauSac;
    private javax.swing.JTextField txt_DVT;
    private javax.swing.JTextField txt_GioiTinh;
    private javax.swing.JTextField txt_IDDVT;
    private javax.swing.JTextField txt_IDGioiTinh;
    private javax.swing.JTextField txt_IDHang;
    private javax.swing.JTextField txt_IDKichThuoc;
    private javax.swing.JTextField txt_IDKieuDang;
    private javax.swing.JTextField txt_IDLoaiHang;
    private javax.swing.JTextField txt_IDLoaiKinh;
    private javax.swing.JTextField txt_IDMauSac;
    private javax.swing.JTextField txt_IDSanPham;
    private javax.swing.JTextField txt_KichThuoc;
    private javax.swing.JTextField txt_MauSac;
    private javax.swing.JTextField txt_NamesKieuDang;
    private javax.swing.JTextField txt_NamesLoaiKinh;
    private javax.swing.JTextField txt_TenHang;
    private javax.swing.JTextField txt_TenLoaiHang;
    private javax.swing.JTextField txt_TenSP;
    private javax.swing.JTextField txt_barcode;
    private javax.swing.JTextField txt_price;
    private javax.swing.JTextField txt_quantity;
    // End of variables declaration//GEN-END:variables
}
