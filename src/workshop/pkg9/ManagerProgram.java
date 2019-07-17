package workshop.pkg9;

import com.sun.xml.internal.ws.util.StringUtils;
import dbo.*;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.Rectangle;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

public class ManagerProgram extends javax.swing.JFrame {

    int itemCurPage, itemTotalPage, supCurPage, supTotalPage;
    ItemDBAccess dbAccess = null;
    Suppliers suppliers;
    Suppliers supResult;
    Suppliers supShow;
    Items items;
    Items itemResult;
    Items itemShow;
    ItemFullModel itemModel;
    SupplierFullModel supplierModel;
    boolean addNewItem = false;
    boolean addNewSupplier = false;
    boolean itemChanged = false;
    boolean supChanged = false;
    final int rowShow = 5;
    boolean isSearching = false;

    public ManagerProgram() {
        initComponents();
        dbAccess = new ItemDBAccess();
        suppliers = new Suppliers();
        supShow = new Suppliers();
        supResult = new Suppliers();
        suppliers.loadFromDB(dbAccess);
        supplierModel = new SupplierFullModel(suppliers);
        items = new Items();
        itemShow = new Items();
        itemResult = new Items();
        int getAllItems = 3;
        items.loadFromDB(dbAccess, suppliers, getAllItems);
        itemModel = new ItemFullModel(items);
        setupModel();
        txtItemCur.setText("1");
        txtSupCur.setText("1");
        txtItemTotal.setText("" + itemTotalPage);
        txtSupTotal.setText("" + supTotalPage);
        this.cbbItemSupplier.setModel(new DefaultComboBoxModel(suppliers));
        paging(0);
        paging(1);
        txtItemTotal.setEditable(false);
        txtSupTotal.setEditable(false);
        checkValidPage();
        btnNewActionPerformed(null);
    }

    private void paging(int tab) {

        if (tab == 0) {
            if (!isSearching) {
                supShow.removeAllElements();
                supTotalPage = (int) Math.ceil((double) suppliers.size() / 5);
                txtSupTotal.setText("" + supTotalPage);
                supCurPage = Integer.parseInt(txtSupCur.getText());
                if (supCurPage < supTotalPage) {
                    for (int i = (supCurPage * rowShow) - rowShow; i < supCurPage * rowShow; i++) {
                        supShow.add(suppliers.get(i));
                    }
                } else {
                    for (int i = (supCurPage * rowShow) - rowShow; i < suppliers.size(); i++) {
                        supShow.add(suppliers.get(i));
                    }
                }
                supplierModel = new SupplierFullModel(supShow);
                setupModel();
                tblSupplier.updateUI();
            } else {
                supShow.removeAllElements();
                supTotalPage = (int) Math.ceil((double) supResult.size() / 5);
                txtSupTotal.setText("" + supTotalPage);
                supCurPage = Integer.parseInt(txtSupCur.getText());
                if (supCurPage < supTotalPage) {
                    for (int i = (supCurPage * rowShow) - rowShow; i < supCurPage * rowShow; i++) {
                        supShow.add(supResult.get(i));
                    }
                } else {
                    for (int i = (supCurPage * rowShow) - rowShow; i < supResult.size(); i++) {
                        supShow.add(supResult.get(i));
                    }
                }
                supplierModel = new SupplierFullModel(supShow);
                setupModel();
                tblSupplier.updateUI();
            }
        }

        if (tab == 1) {
            if (!isSearching) {
                itemShow.removeAllElements();
                itemTotalPage = (int) Math.ceil((double) items.size() / 5);
                txtItemTotal.setText("" + itemTotalPage);
                itemCurPage = Integer.parseInt(txtItemCur.getText());
                if (itemCurPage < itemTotalPage) {
                    for (int i = (itemCurPage * rowShow) - rowShow; i < itemCurPage * rowShow; i++) {
                        itemShow.add(items.get(i));
                    }
                } else {
                    for (int i = (itemCurPage * rowShow) - rowShow; i < items.size(); i++) {
                        itemShow.add(items.get(i));
                    }
                }
                itemModel = new ItemFullModel(itemShow);
                setupModel();
                tblItem.updateUI();
            } else {
                itemShow.removeAllElements();
                itemTotalPage = (int) Math.ceil((double) itemResult.size() / 5);
                txtItemTotal.setText("" + itemTotalPage);
                itemCurPage = Integer.parseInt(txtItemCur.getText());

                if (itemCurPage < itemTotalPage) {
                    for (int i = (itemCurPage * rowShow) - rowShow; i < itemCurPage * rowShow; i++) {
                        itemShow.add(itemResult.get(i));
                    }
                } else {
                    for (int i = (itemCurPage * rowShow) - rowShow; i < itemResult.size(); i++) {
                        itemShow.add(itemResult.get(i));
                    }
                }
                itemModel = new ItemFullModel(itemShow);
                setupModel();
                tblItem.updateUI();
            }
        }
        checkValidPage();
    }

    private void checkValidPage() {
        supCurPage = Integer.parseInt(txtSupCur.getText());
        supTotalPage = Integer.parseInt(txtSupTotal.getText());
        itemCurPage = Integer.parseInt(txtItemCur.getText());
        itemTotalPage = Integer.parseInt(txtItemTotal.getText());
        if (supCurPage == 1 && supTotalPage == 1) {
            btnSupNext.setEnabled(false);
            btnSupPrev.setEnabled(false);
        } else if (supCurPage < supTotalPage) {
            if (supCurPage == 1) {
                btnSupNext.setEnabled(true);
                btnSupPrev.setEnabled(false);
            }
            if (supCurPage != 1) {
                btnSupNext.setEnabled(true);
                btnSupPrev.setEnabled(true);
            }
        } else {
            btnSupNext.setEnabled(false);
            btnSupPrev.setEnabled(true);
        }

        if (itemCurPage == 1 && itemTotalPage == 1) {
            btnItemNext.setEnabled(false);
            btnItemPrev.setEnabled(false);
        } else if (itemCurPage < itemTotalPage) {
            if (itemCurPage == 1) {
                btnItemNext.setEnabled(true);
                btnItemPrev.setEnabled(false);
            }
            if (itemCurPage != 1) {
                btnItemNext.setEnabled(true);
                btnItemPrev.setEnabled(true);
            }
        } else {
            btnItemNext.setEnabled(false);
            btnItemPrev.setEnabled(true);
        }
    }

    private void setupModel() {
        tblItem.setModel(itemModel);
        tblSupplier.setModel(supplierModel);
    }

    private boolean checkItem(String itemCode) {
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getItemCode().equals(itemCode)) {
                return true;
            }
        }
        return false;
    }

    private boolean checkSupplier(String supCode) {
        for (int i = 0; i < suppliers.size(); i++) {
            if (suppliers.get(i).getSupCode().equals(supCode)) {
                return true;
            }
        }
        return false;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        tabPanel = new javax.swing.JTabbedPane();
        suppliersTab = new javax.swing.JPanel();
        supplierListPanel = new javax.swing.JPanel();
        supScrollPane = new javax.swing.JScrollPane();
        tblSupplier = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        btnSupPrev = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        txtSupCur = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtSupTotal = new javax.swing.JTextField();
        btnSupNext = new javax.swing.JButton();
        supplierDetailPanel = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        txtSupCode = new javax.swing.JTextField();
        txtSupName = new javax.swing.JTextField();
        txtSupAddress = new javax.swing.JTextField();
        cbColloborating = new javax.swing.JCheckBox();
        btnSupAdd = new javax.swing.JButton();
        itemsTab = new javax.swing.JPanel();
        itemListPanel = new javax.swing.JPanel();
        tablePanel = new javax.swing.JPanel();
        itemScrollPane = new javax.swing.JScrollPane();
        tblItem = new javax.swing.JTable();
        buttonpanel = new javax.swing.JPanel();
        btnItemPrev = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        txtItemCur = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtItemTotal = new javax.swing.JTextField();
        btnItemNext = new javax.swing.JButton();
        itemDetailPanel = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        txtItemCode = new javax.swing.JTextField();
        txtItemName = new javax.swing.JTextField();
        cbbItemSupplier = new javax.swing.JComboBox<>();
        txtItemUnit = new javax.swing.JTextField();
        txtItemPrice = new javax.swing.JTextField();
        cbSupplying = new javax.swing.JCheckBox();
        btnItemAdd = new javax.swing.JButton();
        functionPanel = new javax.swing.JPanel();
        btnNew = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        btnRemove = new javax.swing.JButton();
        btnExit = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        txtSearch = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(750, 450));
        setPreferredSize(new java.awt.Dimension(750, 450));

        tabPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabPanelMouseClicked(evt);
            }
        });

        suppliersTab.setLayout(new java.awt.BorderLayout());

        supplierListPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Supplier List"));
        supplierListPanel.setLayout(new java.awt.BorderLayout());

        supScrollPane.setPreferredSize(new java.awt.Dimension(480, 130));

        tblSupplier.setModel(new javax.swing.table.DefaultTableModel(
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
        tblSupplier.setPreferredSize(null);
        tblSupplier.setRowHeight(20);
        tblSupplier.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblSupplierMouseClicked(evt);
            }
        });
        supScrollPane.setViewportView(tblSupplier);

        supplierListPanel.add(supScrollPane, java.awt.BorderLayout.NORTH);

        jPanel1.setPreferredSize(new java.awt.Dimension(229, 180));

        btnSupPrev.setFont(new java.awt.Font("SimSun-ExtB", 1, 12)); // NOI18N
        btnSupPrev.setText("<");
        btnSupPrev.setPreferredSize(new java.awt.Dimension(39, 30));
        btnSupPrev.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSupPrevActionPerformed(evt);
            }
        });
        jPanel1.add(btnSupPrev);

        jLabel1.setFont(new java.awt.Font("Monospaced", 0, 14)); // NOI18N
        jLabel1.setText("Page:");
        jPanel1.add(jLabel1);

        txtSupCur.setPreferredSize(new java.awt.Dimension(40, 30));
        txtSupCur.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSupCurActionPerformed(evt);
            }
        });
        jPanel1.add(txtSupCur);

        jLabel2.setFont(new java.awt.Font("Monospaced", 0, 14)); // NOI18N
        jLabel2.setText("in total");
        jPanel1.add(jLabel2);

        txtSupTotal.setPreferredSize(new java.awt.Dimension(40, 30));
        jPanel1.add(txtSupTotal);

        btnSupNext.setFont(new java.awt.Font("SimSun-ExtB", 1, 12)); // NOI18N
        btnSupNext.setText(">");
        btnSupNext.setPreferredSize(new java.awt.Dimension(39, 30));
        btnSupNext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSupNextActionPerformed(evt);
            }
        });
        jPanel1.add(btnSupNext);

        supplierListPanel.add(jPanel1, java.awt.BorderLayout.CENTER);

        suppliersTab.add(supplierListPanel, java.awt.BorderLayout.CENTER);

        supplierDetailPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Supplier Details"));
        supplierDetailPanel.setPreferredSize(new java.awt.Dimension(300, 373));
        supplierDetailPanel.setLayout(new java.awt.GridBagLayout());

        jLabel11.setText("Supplier Code");
        jLabel11.setPreferredSize(new java.awt.Dimension(80, 30));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 10, 0);
        supplierDetailPanel.add(jLabel11, gridBagConstraints);

        jLabel12.setText("Supplier Name");
        jLabel12.setPreferredSize(new java.awt.Dimension(80, 30));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 10, 0);
        supplierDetailPanel.add(jLabel12, gridBagConstraints);

        jLabel13.setText("Address");
        jLabel13.setPreferredSize(new java.awt.Dimension(80, 30));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 10, 0);
        supplierDetailPanel.add(jLabel13, gridBagConstraints);

        jLabel14.setText("Colloborating");
        jLabel14.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jLabel14.setPreferredSize(new java.awt.Dimension(80, 30));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 10, 0);
        supplierDetailPanel.add(jLabel14, gridBagConstraints);

        txtSupCode.setMinimumSize(new java.awt.Dimension(150, 30));
        txtSupCode.setPreferredSize(new java.awt.Dimension(150, 30));
        txtSupCode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSupCodeActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 10, 0);
        supplierDetailPanel.add(txtSupCode, gridBagConstraints);

        txtSupName.setMinimumSize(new java.awt.Dimension(150, 30));
        txtSupName.setPreferredSize(new java.awt.Dimension(150, 30));
        txtSupName.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                txtSupNameMousePressed(evt);
            }
        });
        txtSupName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSupNameActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 10, 0);
        supplierDetailPanel.add(txtSupName, gridBagConstraints);

        txtSupAddress.setMinimumSize(new java.awt.Dimension(150, 30));
        txtSupAddress.setPreferredSize(new java.awt.Dimension(150, 30));
        txtSupAddress.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSupAddressActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 10, 0);
        supplierDetailPanel.add(txtSupAddress, gridBagConstraints);

        cbColloborating.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        cbColloborating.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        cbColloborating.setMinimumSize(new java.awt.Dimension(150, 30));
        cbColloborating.setPreferredSize(new java.awt.Dimension(150, 30));
        cbColloborating.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbColloboratingActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 10, 0);
        supplierDetailPanel.add(cbColloborating, gridBagConstraints);

        btnSupAdd.setText("Add");
        btnSupAdd.setMaximumSize(new java.awt.Dimension(51, 40));
        btnSupAdd.setMinimumSize(new java.awt.Dimension(51, 40));
        btnSupAdd.setPreferredSize(new java.awt.Dimension(51, 40));
        btnSupAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSupAddActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 10, 0);
        supplierDetailPanel.add(btnSupAdd, gridBagConstraints);

        suppliersTab.add(supplierDetailPanel, java.awt.BorderLayout.LINE_END);

        tabPanel.addTab("Suppliers", suppliersTab);

        itemsTab.setLayout(new java.awt.BorderLayout());

        itemListPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Item List"));
        itemListPanel.setLayout(new java.awt.BorderLayout());

        tablePanel.setLayout(new java.awt.BorderLayout());

        itemScrollPane.setPreferredSize(new java.awt.Dimension(480, 130));

        tblItem.setModel(new javax.swing.table.DefaultTableModel(
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
        tblItem.setPreferredSize(null);
        tblItem.setRowHeight(20);
        tblItem.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblItemMouseClicked(evt);
            }
        });
        itemScrollPane.setViewportView(tblItem);

        tablePanel.add(itemScrollPane, java.awt.BorderLayout.CENTER);

        itemListPanel.add(tablePanel, java.awt.BorderLayout.PAGE_START);

        buttonpanel.setPreferredSize(null);

        btnItemPrev.setFont(new java.awt.Font("SimSun-ExtB", 1, 12)); // NOI18N
        btnItemPrev.setText("<");
        btnItemPrev.setPreferredSize(new java.awt.Dimension(39, 30));
        btnItemPrev.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnItemPrevActionPerformed(evt);
            }
        });
        buttonpanel.add(btnItemPrev);

        jLabel3.setFont(new java.awt.Font("Monospaced", 0, 14)); // NOI18N
        jLabel3.setText("Page:");
        buttonpanel.add(jLabel3);

        txtItemCur.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtItemCur.setPreferredSize(new java.awt.Dimension(40, 30));
        txtItemCur.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtItemCurActionPerformed(evt);
            }
        });
        buttonpanel.add(txtItemCur);

        jLabel4.setFont(new java.awt.Font("Monospaced", 0, 14)); // NOI18N
        jLabel4.setText("in total");
        buttonpanel.add(jLabel4);

        txtItemTotal.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtItemTotal.setPreferredSize(new java.awt.Dimension(40, 30));
        txtItemTotal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtItemTotalActionPerformed(evt);
            }
        });
        buttonpanel.add(txtItemTotal);

        btnItemNext.setFont(new java.awt.Font("SimSun-ExtB", 1, 12)); // NOI18N
        btnItemNext.setText(">");
        btnItemNext.setPreferredSize(new java.awt.Dimension(39, 30));
        btnItemNext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnItemNextActionPerformed(evt);
            }
        });
        buttonpanel.add(btnItemNext);

        itemListPanel.add(buttonpanel, java.awt.BorderLayout.CENTER);

        itemsTab.add(itemListPanel, java.awt.BorderLayout.CENTER);

        itemDetailPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Item Details"));
        itemDetailPanel.setPreferredSize(new java.awt.Dimension(300, 373));
        itemDetailPanel.setLayout(new java.awt.GridBagLayout());

        jLabel5.setFont(new java.awt.Font("Monospaced", 1, 14)); // NOI18N
        jLabel5.setText("Item Code");
        jLabel5.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jLabel5.setPreferredSize(new java.awt.Dimension(80, 30));
        jLabel5.setVerticalTextPosition(javax.swing.SwingConstants.TOP);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 10, 0);
        itemDetailPanel.add(jLabel5, gridBagConstraints);

        jLabel6.setFont(new java.awt.Font("Monospaced", 1, 14)); // NOI18N
        jLabel6.setText("Item Name");
        jLabel6.setPreferredSize(new java.awt.Dimension(80, 30));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 10, 0);
        itemDetailPanel.add(jLabel6, gridBagConstraints);

        jLabel7.setText("Supplier");
        jLabel7.setPreferredSize(new java.awt.Dimension(80, 30));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 10, 0);
        itemDetailPanel.add(jLabel7, gridBagConstraints);

        jLabel8.setText("Unit");
        jLabel8.setPreferredSize(new java.awt.Dimension(80, 30));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 10, 0);
        itemDetailPanel.add(jLabel8, gridBagConstraints);

        jLabel9.setText("Price");
        jLabel9.setPreferredSize(new java.awt.Dimension(80, 30));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 10, 0);
        itemDetailPanel.add(jLabel9, gridBagConstraints);

        jLabel10.setText("Supplying");
        jLabel10.setPreferredSize(new java.awt.Dimension(80, 30));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 10, 0);
        itemDetailPanel.add(jLabel10, gridBagConstraints);

        txtItemCode.setMinimumSize(new java.awt.Dimension(150, 30));
        txtItemCode.setPreferredSize(new java.awt.Dimension(150, 30));
        itemDetailPanel.add(txtItemCode, new java.awt.GridBagConstraints());

        txtItemName.setMinimumSize(new java.awt.Dimension(150, 30));
        txtItemName.setPreferredSize(new java.awt.Dimension(150, 30));
        txtItemName.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                txtItemNameMousePressed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        itemDetailPanel.add(txtItemName, gridBagConstraints);

        cbbItemSupplier.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbbItemSupplier.setMinimumSize(new java.awt.Dimension(150, 30));
        cbbItemSupplier.setPreferredSize(new java.awt.Dimension(150, 30));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        itemDetailPanel.add(cbbItemSupplier, gridBagConstraints);

        txtItemUnit.setMinimumSize(new java.awt.Dimension(150, 30));
        txtItemUnit.setPreferredSize(new java.awt.Dimension(150, 30));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        itemDetailPanel.add(txtItemUnit, gridBagConstraints);

        txtItemPrice.setMinimumSize(new java.awt.Dimension(150, 30));
        txtItemPrice.setPreferredSize(new java.awt.Dimension(150, 30));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        itemDetailPanel.add(txtItemPrice, gridBagConstraints);

        cbSupplying.setMinimumSize(new java.awt.Dimension(150, 30));
        cbSupplying.setPreferredSize(new java.awt.Dimension(150, 30));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        itemDetailPanel.add(cbSupplying, gridBagConstraints);

        btnItemAdd.setText("Add");
        btnItemAdd.setPreferredSize(new java.awt.Dimension(51, 40));
        btnItemAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnItemAddActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 5, 0);
        itemDetailPanel.add(btnItemAdd, gridBagConstraints);

        itemsTab.add(itemDetailPanel, java.awt.BorderLayout.LINE_END);

        tabPanel.addTab("Items", itemsTab);

        getContentPane().add(tabPanel, java.awt.BorderLayout.CENTER);

        functionPanel.setPreferredSize(null);

        btnNew.setText("New");
        btnNew.setPreferredSize(new java.awt.Dimension(80, 40));
        btnNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNewActionPerformed(evt);
            }
        });
        functionPanel.add(btnNew);

        btnUpdate.setText("Update");
        btnUpdate.setPreferredSize(new java.awt.Dimension(80, 40));
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });
        functionPanel.add(btnUpdate);

        btnRemove.setText("Remove");
        btnRemove.setPreferredSize(new java.awt.Dimension(80, 40));
        btnRemove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemoveActionPerformed(evt);
            }
        });
        functionPanel.add(btnRemove);

        btnExit.setText("Exit");
        btnExit.setPreferredSize(new java.awt.Dimension(80, 40));
        btnExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExitActionPerformed(evt);
            }
        });
        functionPanel.add(btnExit);

        getContentPane().add(functionPanel, java.awt.BorderLayout.PAGE_END);

        jPanel2.setPreferredSize(new java.awt.Dimension(647, 30));
        jPanel2.setLayout(new java.awt.BorderLayout(0, 5));

        txtSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtSearchKeyReleased(evt);
            }
        });
        jPanel2.add(txtSearch, java.awt.BorderLayout.CENTER);

        jLabel15.setFont(new java.awt.Font("Monospaced", 1, 14)); // NOI18N
        jLabel15.setText("Search:");
        jLabel15.setMinimumSize(new java.awt.Dimension(60, 20));
        jLabel15.setPreferredSize(new java.awt.Dimension(60, 30));
        jPanel2.add(jLabel15, java.awt.BorderLayout.LINE_START);

        getContentPane().add(jPanel2, java.awt.BorderLayout.PAGE_START);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSupPrevActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSupPrevActionPerformed
        // TODO add your handling code here:
        supCurPage = Integer.parseInt(txtSupCur.getText());
        supTotalPage = Integer.parseInt(txtSupTotal.getText());
        if (supCurPage > 1) {
            supCurPage--;
            txtSupCur.setText("" + supCurPage);
            checkValidPage();
            paging(0);
        }

    }//GEN-LAST:event_btnSupPrevActionPerformed

    private void btnSupNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSupNextActionPerformed
        // TODO add your handling code here:
        supCurPage = Integer.parseInt(txtSupCur.getText());
        supTotalPage = Integer.parseInt(txtSupTotal.getText());
        if (supCurPage < supTotalPage) {
            supCurPage++;
            txtSupCur.setText("" + supCurPage);
            checkValidPage();
            paging(0);
        }


    }//GEN-LAST:event_btnSupNextActionPerformed

    private void txtSupCurActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSupCurActionPerformed
        // TODO add your handling code here:
        supCurPage = Integer.parseInt(txtSupCur.getText());
        supTotalPage = Integer.parseInt(txtSupTotal.getText());
        if (supCurPage < 1) {
            txtSupCur.setText("1");
        } else if (supCurPage > supTotalPage) {
            txtSupCur.setText("" + supTotalPage);
        }
        checkValidPage();
        paging(0);


    }//GEN-LAST:event_txtSupCurActionPerformed

    private void btnItemPrevActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnItemPrevActionPerformed
        // TODO add your handling code here:
        itemCurPage = Integer.parseInt(txtItemCur.getText());
        itemTotalPage = Integer.parseInt(txtItemTotal.getText());
        if (itemCurPage > 1) {
            itemCurPage--;
            txtItemCur.setText("" + itemCurPage);
            checkValidPage();
            paging(1);
        }
    }//GEN-LAST:event_btnItemPrevActionPerformed

    private void txtItemCurActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtItemCurActionPerformed
        // TODO add your handling code here:
        itemCurPage = Integer.parseInt(txtItemCur.getText());
        itemTotalPage = Integer.parseInt(txtItemTotal.getText());
        if (itemCurPage < 1) {
            txtItemCur.setText("1");
        } else if (itemCurPage > itemTotalPage) {
            txtItemCur.setText("" + itemTotalPage);
        }
        checkValidPage();
        paging(1);

    }//GEN-LAST:event_txtItemCurActionPerformed

    private void btnItemNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnItemNextActionPerformed
        // TODO add your handling code here:
        itemCurPage = Integer.parseInt(txtItemCur.getText());
        itemTotalPage = Integer.parseInt(txtItemTotal.getText());
        if (itemCurPage < itemTotalPage) {
            itemCurPage++;
            txtItemCur.setText("" + itemCurPage);
            checkValidPage();
            paging(1);
        }
    }//GEN-LAST:event_btnItemNextActionPerformed

    private void txtItemTotalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtItemTotalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtItemTotalActionPerformed

    private void txtSupAddressActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSupAddressActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSupAddressActionPerformed

    private void tblItemMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblItemMouseClicked
        addNewItem = false;
        itemCurPage = Integer.parseInt(txtItemCur.getText());
        int pos = tblItem.getSelectedRow();
        Item item = itemModel.getItems().get(pos);
        txtItemCode.setText(item.getItemCode());
        txtItemCode.setEditable(false);
        txtItemName.setText(item.getItemName());
        int index = suppliers.find(item.getSupplier().getSupCode());
        cbbItemSupplier.setSelectedIndex(index);
        txtItemUnit.setText(item.getUnit());
        txtItemPrice.setText("" + item.getPrice());
        cbSupplying.setSelected(item.isSupplying());
        btnUpdate.setEnabled(true);
        btnRemove.setEnabled(true);
        btnItemAdd.setEnabled(false);
    }//GEN-LAST:event_tblItemMouseClicked

    private void tblSupplierMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblSupplierMouseClicked
        addNewSupplier = false;
        int pos = tblSupplier.getSelectedRow();
        Supplier sup = supplierModel.getSuppliers().get(pos);
        txtSupCode.setText(sup.getSupCode());
        txtSupCode.setEditable(false);
        txtSupName.setText(sup.getSupName());
        txtSupAddress.setText(sup.getAddress());
        cbColloborating.setSelected(sup.isColloborating());
        btnRemove.setEnabled(true);
        btnUpdate.setEnabled(true);
        btnSupAdd.setEnabled(false);
    }//GEN-LAST:event_tblSupplierMouseClicked

    private void btnNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewActionPerformed
        if (tabPanel.getSelectedIndex() == 1) {
            txtItemCode.setText("");
            txtItemCode.setEditable(true);
            txtItemCode.requestFocus();
            txtItemName.setText("");
            cbbItemSupplier.setSelectedIndex(0);
            txtItemUnit.setText("");
            txtItemPrice.setText("");
            cbSupplying.setSelected(true);
            btnItemAdd.setEnabled(true);
        }
        if (tabPanel.getSelectedIndex() == 0) {
            txtSupCode.setText("");
            txtSupCode.setEditable(true);
            txtSupCode.requestFocus();
            txtSupName.setText("");
            txtSupAddress.setText("");
            cbColloborating.setSelected(true);
            btnSupAdd.setEnabled(true);
        }
        btnUpdate.setEnabled(false);
        btnRemove.setEnabled(false);
    }//GEN-LAST:event_btnNewActionPerformed

    private void txtSupNameMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtSupNameMousePressed


    }//GEN-LAST:event_txtSupNameMousePressed

    private void txtItemNameMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtItemNameMousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtItemNameMousePressed

    private void btnRemoveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoveActionPerformed
        if (tabPanel.getSelectedIndex() == 0) {
            supCurPage = Integer.parseInt(txtSupCur.getText());
            int pos = tblSupplier.getSelectedRow() * ((supCurPage * rowShow) - rowShow);
            String supCode = txtSupCode.getText();
            String sql = "Delete from suppliers where supcode='" + supCode + "'";
            int choice = JOptionPane.showConfirmDialog(rootPane, sql);
            if (choice == JOptionPane.YES_OPTION) {
                String msg = "The supplier " + supCode + " has been deleted from database!";
                try {
                    int n = dbAccess.executeUpdate(sql);
                    if (n > 0) {
                        JOptionPane.showMessageDialog(rootPane, msg);
                        suppliers.remove(pos - 1);
                        tblSupplier.updateUI();
                        paging(0);
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(rootPane, e);
                }
            }
        }
        if (tabPanel.getSelectedIndex() == 1) {
            itemCurPage = Integer.parseInt(txtItemCur.getText());
            int pos = tblItem.getSelectedRow() + ((itemCurPage * rowShow) - rowShow);
            String itemCode = txtItemCode.getText();
            String sql = "Delete from items where itemcode='" + itemCode + "'";
            int choice = JOptionPane.showConfirmDialog(rootPane, sql);
            if (choice == JOptionPane.YES_OPTION) {
                String msg = "The item " + itemCode + " has been deleted from database!";
                try {
                    int n = dbAccess.executeUpdate(sql);
                    if (n > 0) {
                        JOptionPane.showMessageDialog(rootPane, msg);
                        items.remove(pos);
                        tblItem.updateUI();
                        paging(1);

                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(rootPane, e);
                }
            }
        }
        btnRemove.setEnabled(false);

    }//GEN-LAST:event_btnRemoveActionPerformed

    private void btnItemAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnItemAddActionPerformed
        // TODO add your handling code here:
        String itemCode = txtItemCode.getText().toUpperCase().trim();
        if (!itemCode.matches("(E)\\d{4}")) {
            JOptionPane.showMessageDialog(rootPane, "This " + itemCode + " is invalid!(Exxxx)");
            txtItemCode.requestFocus();
            return;
        }
        if (checkItem(itemCode)) {
            JOptionPane.showMessageDialog(rootPane, "This " + itemCode + " is existed!");
            txtItemCode.requestFocus();
            return;
        }
        String itemName = StringUtils.capitalize(txtItemName.getText()).trim();
        if (itemName.isEmpty()) {
            JOptionPane.showMessageDialog(rootPane, "Please fill in Item Name");
            txtItemName.requestFocus();
            return;
        }
        Supplier sup = (Supplier) cbbItemSupplier.getSelectedItem();
        String supCode = sup.getSupCode();
        String unit = txtItemUnit.getText();
        int price = Integer.parseInt(txtItemPrice.getText());
        boolean supplying = cbSupplying.isSelected();
        Item item = new Item(itemCode, itemName, sup, unit, price, supplying);
        setupModel();
        //SQL statement
        String sql = "insert into items values('" + itemCode + "','" + itemName + "','" + supCode + "','"
                + unit + "','" + price + "','" + (supplying ? 1 : 0) + "')";
        JOptionPane.showMessageDialog(rootPane, sql);
        try {
            int n = dbAccess.executeUpdate(sql);
            if (n > 0) {
                JOptionPane.showMessageDialog(rootPane, "A new item has been added!");
                items.add(item);
                paging(1);
            }
        } catch (Exception e) {
        }
        btnNewActionPerformed(null);
    }//GEN-LAST:event_btnItemAddActionPerformed

    private void btnSupAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSupAddActionPerformed
        // TODO add your handling code here:
        String supCode = txtSupCode.getText().trim().toUpperCase();
        if (supCode.isEmpty()) {
            JOptionPane.showMessageDialog(rootPane, "Please fill in Supplier Code");
            txtSupCode.requestFocus();
            return;
        }
        if (checkSupplier(supCode)) {
            JOptionPane.showMessageDialog(rootPane, "This " + supCode + " is existed!");
            txtSupCode.requestFocus();
            return;
        }
        String supName = StringUtils.capitalize(txtSupName.getText().trim());
        if (supName.isEmpty()) {
            JOptionPane.showMessageDialog(rootPane, "Please fill in Supplier Name");
            txtSupName.requestFocus();
            return;
        }
        String address = StringUtils.capitalize(txtSupAddress.getText()).trim();
        boolean colloborating = cbColloborating.isSelected();
        Supplier newSup = new Supplier(supCode, supName, address, colloborating);
        setupModel();
        //SQL statement
        String sql = "insert into suppliers values('" + supCode + "','" + supName + "','"
                + address + "','" + (colloborating ? 1 : 0) + "')";
        JOptionPane.showMessageDialog(rootPane, sql);
        try {
            int n = dbAccess.executeUpdate(sql);
            if (n > 0) {
                JOptionPane.showMessageDialog(rootPane, "A new supplier has been added!");
                suppliers.add(newSup);
                paging(0);
            }
        } catch (Exception e) {
        }
        btnNewActionPerformed(null);
    }//GEN-LAST:event_btnSupAddActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        // TODO add your handling code here:
        if (tabPanel.getSelectedIndex() == 0) {
            String supName = txtSupName.getText();
            String address = txtSupAddress.getText();
            boolean colloborating = cbColloborating.isSelected();
            supCurPage = Integer.parseInt(txtSupCur.getText());
            int pos = tblSupplier.getSelectedRow() * ((supCurPage * rowShow) - rowShow);
            String supCode = txtSupCode.getText();
            Supplier updatedSup = new Supplier(supCode, supName, address, colloborating);
            String sql = "update suppliers set " + "supplierName='" + supName + "'," + "address='" + address + "',"
                    + ",colloborating='" + (colloborating ? 1 : 0)
                    + "where supCode = '" + supCode + "'";
            int choice = JOptionPane.showConfirmDialog(rootPane, sql);
            if (choice == JOptionPane.YES_OPTION) {
                String msg = "The supplier " + supCode + " has been updated from database!";
                try {
                    int n = dbAccess.executeUpdate(sql);
                    if (n > 0) {
                        JOptionPane.showMessageDialog(rootPane, msg);
                        suppliers.set(pos, updatedSup);
                        paging(0);
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(rootPane, e);
                }
            }
        }
        if (tabPanel.getSelectedIndex() == 1) {
            String itemCode = txtItemCode.getText();
            String itemName = txtItemName.getText();
            Supplier sup = (Supplier) cbbItemSupplier.getSelectedItem();
            String supCode = sup.getSupCode();
            String unit = txtItemUnit.getText();
            int price = Integer.parseInt(txtItemPrice.getText());
            boolean supplying = cbSupplying.isSelected();
            Item item = new Item(itemCode, itemName, sup, unit, price, supplying);
            itemCurPage = Integer.parseInt(txtItemCur.getText());
            int pos = tblItem.getSelectedRow() + ((itemCurPage * rowShow) - rowShow);
            System.out.println(tblItem.getSelectedRow());
            String sql = "update items set " + "itemName='" + itemName + "'," + "supCode='" + supCode
                    + "',unit='" + unit + "'," + "price=" + price + ",supplying=" + (supplying ? 1 : 0)
                    + "where itemCode ='" + itemCode + "'";
            int choice = JOptionPane.showConfirmDialog(rootPane, sql);
            if (choice == JOptionPane.YES_OPTION) {
                String msg = "The item " + itemCode + " has been updated from database!";
                try {
                    int n = dbAccess.executeUpdate(sql);
                    if (n > 0) {
                        JOptionPane.showMessageDialog(rootPane, msg);
                        items.set(pos, item);
                        paging(1);

                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(rootPane, e);
                }
            }
        }
        btnUpdate.setEnabled(false);

    }//GEN-LAST:event_btnUpdateActionPerformed

    private void txtSupCodeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSupCodeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSupCodeActionPerformed

    private void cbColloboratingActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbColloboratingActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbColloboratingActionPerformed

    private void txtSupNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSupNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSupNameActionPerformed

    private void txtSearchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchKeyReleased
        // TODO add your handling code here:
        if (tabPanel.getSelectedIndex() == 0) {
            if (txtSearch.getText().isEmpty()) {
                isSearching = false;
                txtSupCur.setText("1");
                paging(0);
            } else {
                isSearching = true;
                supResult.removeAllElements();
                String name = txtSearch.getText().trim().toUpperCase();
                for (int i = 0; i < suppliers.size(); i++) {
                    if (suppliers.get(i).getSupName().toUpperCase().contains(name)) {
                        supResult.add(suppliers.get(i));
                    }
                }
                if (!supResult.isEmpty()) {
                    paging(0);
                } else {
                    supplierModel = new SupplierFullModel(supResult);
                    setupModel();
                    tblSupplier.updateUI();
                    btnSupNext.setEnabled(false);
                    btnSupPrev.setEnabled(false);
                    txtSupCur.setText("0");
                    txtSupTotal.setText("0");

                }

            }
        }
        if (tabPanel.getSelectedIndex() == 1) {
            if (txtSearch.getText().isEmpty()) {
                isSearching = false;
                txtItemCur.setText("1");
                paging(1);
            } else {
                isSearching = true;
                itemResult.removeAllElements();
                String name = txtSearch.getText().trim().toUpperCase();
                for (int i = 0; i < items.size(); i++) {
                    if (items.get(i).getItemName().toUpperCase().contains(name)) {
                        itemResult.add(items.get(i));
                        System.out.println("found");
                    }
                }
                if (!itemResult.isEmpty()) {
                    paging(1);
                } else {
                    itemModel = new ItemFullModel(itemResult);
                    setupModel();
                    tblItem.updateUI();
                    btnItemNext.setEnabled(false);
                    btnItemPrev.setEnabled(false);
                    txtItemCur.setText("0");
                    txtItemTotal.setText("0");

                }

            }
        }
    }//GEN-LAST:event_txtSearchKeyReleased

    private void tabPanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabPanelMouseClicked
        // TODO add your handling code here:
        txtSearch.setText("");
        txtSearchKeyReleased(null);
    }//GEN-LAST:event_tabPanelMouseClicked

    private void btnExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExitActionPerformed
        // TODO add your handling code here:
        System.exit(0);
    }//GEN-LAST:event_btnExitActionPerformed

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
            java.util.logging.Logger.getLogger(ManagerProgram.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ManagerProgram.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ManagerProgram.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ManagerProgram.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ManagerProgram().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnExit;
    private javax.swing.JButton btnItemAdd;
    private javax.swing.JButton btnItemNext;
    private javax.swing.JButton btnItemPrev;
    private javax.swing.JButton btnNew;
    private javax.swing.JButton btnRemove;
    private javax.swing.JButton btnSupAdd;
    private javax.swing.JButton btnSupNext;
    private javax.swing.JButton btnSupPrev;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JPanel buttonpanel;
    private javax.swing.JCheckBox cbColloborating;
    private javax.swing.JCheckBox cbSupplying;
    private javax.swing.JComboBox<String> cbbItemSupplier;
    private javax.swing.JPanel functionPanel;
    private javax.swing.JPanel itemDetailPanel;
    private javax.swing.JPanel itemListPanel;
    private javax.swing.JScrollPane itemScrollPane;
    private javax.swing.JPanel itemsTab;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
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
    private javax.swing.JScrollPane supScrollPane;
    private javax.swing.JPanel supplierDetailPanel;
    private javax.swing.JPanel supplierListPanel;
    private javax.swing.JPanel suppliersTab;
    private javax.swing.JTabbedPane tabPanel;
    private javax.swing.JPanel tablePanel;
    private javax.swing.JTable tblItem;
    private javax.swing.JTable tblSupplier;
    private javax.swing.JTextField txtItemCode;
    private javax.swing.JTextField txtItemCur;
    private javax.swing.JTextField txtItemName;
    private javax.swing.JTextField txtItemPrice;
    private javax.swing.JTextField txtItemTotal;
    private javax.swing.JTextField txtItemUnit;
    private javax.swing.JTextField txtSearch;
    private javax.swing.JTextField txtSupAddress;
    private javax.swing.JTextField txtSupCode;
    private javax.swing.JTextField txtSupCur;
    private javax.swing.JTextField txtSupName;
    private javax.swing.JTextField txtSupTotal;
    // End of variables declaration//GEN-END:variables
}
