package com.disuraaberathna.globemed.view;

import javax.swing.*;
import java.awt.*;

public class MedicalReportView extends JPanel {

    public MedicalReportView() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {
        jPanel1 = new JPanel();
        jPanel3 = new JPanel();
        jPanel4 = new JPanel();
        patientComboBox = new JComboBox<>();
        doctorComboBox = new JComboBox<>();
        diagnosisField = new JTextField();
        treatmentField = new JTextField();
        priceField = new JTextField();
        createBtn = new JButton();
        clearBtn = new JButton();
        jLabel3 = new JLabel();
        statusLabel = new JLabel();
        jPanel5 = new JPanel();
        searchField = new JTextField();
        clearSearchBtn = new JButton();
        jScrollPane1 = new JScrollPane();
        reportsTable = new JTable();
        jLabel2 = new JLabel();

        setLayout(new BorderLayout());

        jPanel1.setLayout(new BorderLayout());
        jPanel3.setLayout(new BorderLayout());

        jPanel4.setBackground(new Color(229, 229, 229));

        patientComboBox.setBackground(new Color(229, 229, 229));
        patientComboBox.setForeground(new Color(0, 0, 0));
        patientComboBox.setModel(new DefaultComboBoxModel<>(new String[] { "Select Patient" }));
        patientComboBox.setBorder(BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new Color(0, 0, 0), 1, true), "Patient", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new Font("Comic Sans MS", 0, 14), new Color(0, 0, 0)));

        doctorComboBox.setBackground(new Color(229, 229, 229));
        doctorComboBox.setForeground(new Color(0, 0, 0));
        doctorComboBox.setModel(new DefaultComboBoxModel<>(new String[] { "Select Doctor" }));
        doctorComboBox.setBorder(BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new Color(0, 0, 0), 1, true), "Doctor", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new Font("Comic Sans MS", 0, 14), new Color(0, 0, 0)));

        diagnosisField.setBackground(new Color(229, 229, 229));
        diagnosisField.setBorder(BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new Color(0, 0, 0), 1, true), "Diagnosis", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new Font("Comic Sans MS", 0, 14), new Color(0, 0, 0)));

        treatmentField.setBackground(new Color(229, 229, 229));
        treatmentField.setBorder(BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new Color(0, 0, 0), 1, true), "Treatment", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new Font("Comic Sans MS", 0, 14), new Color(0, 0, 0)));

        priceField.setBackground(new Color(229, 229, 229));
        priceField.setBorder(BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new Color(0, 0, 0), 1, true), "Price (LKR)", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new Font("Comic Sans MS", 0, 14), new Color(0, 0, 0)));

        createBtn.setBackground(new Color(51, 102, 255));
        createBtn.setFont(new Font("Comic Sans MS", 0, 14));
        createBtn.setForeground(new Color(255, 255, 255));
        createBtn.setText("Create Report");

        clearBtn.setBackground(new Color(255, 204, 102));
        clearBtn.setFont(new Font("Comic Sans MS", 0, 14));
        clearBtn.setForeground(new Color(0, 0, 0));
        clearBtn.setText("Clear");

        jLabel3.setFont(new Font("Comic Sans MS", 0, 18));
        jLabel3.setForeground(new Color(0, 0, 0));
        jLabel3.setText("Create Medical Report");

        statusLabel.setFont(new Font("Comic Sans MS", 3, 12));
        statusLabel.setForeground(new Color(51, 102, 255));
        statusLabel.setText("Ready to create report");

        GroupLayout jPanel4Layout = new GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel4Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(statusLabel)
                        .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                            .addComponent(patientComboBox, GroupLayout.Alignment.LEADING, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(doctorComboBox, GroupLayout.Alignment.LEADING, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(diagnosisField, GroupLayout.Alignment.LEADING)
                            .addComponent(treatmentField, GroupLayout.Alignment.LEADING)
                            .addComponent(priceField, GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(createBtn, GroupLayout.PREFERRED_SIZE, 147, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(clearBtn, GroupLayout.PREFERRED_SIZE, 120, GroupLayout.PREFERRED_SIZE))
                            .addGroup(GroupLayout.Alignment.LEADING, jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addGap(15, 15, 15))))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jLabel3)
                .addGap(18, 18, 18)
                .addComponent(patientComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(doctorComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(diagnosisField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(treatmentField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(priceField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(statusLabel)
                .addGap(30, 30, 30)
                .addGroup(jPanel4Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(createBtn)
                    .addComponent(clearBtn))
                .addGap(100, 100, 100))
        );

        jPanel3.add(jPanel4, BorderLayout.LINE_START);

        jPanel5.setBackground(new Color(255, 255, 255));

        searchField.setBackground(new Color(255, 255, 255));
        searchField.setForeground(new Color(0, 0, 0));
        searchField.setBorder(BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new Color(0, 0, 0), 1, true), "Search", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new Font("Comic Sans MS", 0, 14), new Color(0, 0, 0)));

        clearSearchBtn.setBackground(new Color(0, 0, 0));
        clearSearchBtn.setFont(new Font("Comic Sans MS", 0, 14));
        clearSearchBtn.setForeground(new Color(255, 255, 255));
        clearSearchBtn.setText("Clear");

        reportsTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
            },
            new String [] {
                "ID", "Patient", "Doctor", "Diagnosis", "Treatment", "Price", "Date"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        reportsTable.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(reportsTable);

        jLabel2.setFont(new Font("Comic Sans MS", 1, 14));
        jLabel2.setForeground(new Color(0, 0, 0));
        jLabel2.setText("Medical Reports");

        GroupLayout jPanel5Layout = new GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(jPanel5Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane1, GroupLayout.DEFAULT_SIZE, 852, Short.MAX_VALUE)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(searchField, GroupLayout.PREFERRED_SIZE, 250, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(clearSearchBtn)))
                        .addGap(20, 20, 20))))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(searchField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(clearSearchBtn))
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel2)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, GroupLayout.DEFAULT_SIZE, 496, Short.MAX_VALUE)
                .addGap(12, 12, 12))
        );

        jPanel3.add(jPanel5, BorderLayout.CENTER);
        jPanel1.add(jPanel3, BorderLayout.CENTER);
        add(jPanel1, BorderLayout.CENTER);
    }

    public JComboBox<String> getPatientComboBox() {
        return patientComboBox;
    }

    public JComboBox<String> getDoctorComboBox() {
        return doctorComboBox;
    }

    public JTextField getDiagnosisField() {
        return diagnosisField;
    }

    public JTextField getTreatmentField() {
        return treatmentField;
    }

    public JTextField getPriceField() {
        return priceField;
    }

    public JButton getCreateBtn() {
        return createBtn;
    }

    public JButton getClearBtn() {
        return clearBtn;
    }

    public JButton getClearSearchBtn() {
        return clearSearchBtn;
    }

    public JTextField getSearchField() {
        return searchField;
    }

    public JTable getReportsTable() {
        return reportsTable;
    }

    public JLabel getStatusLabel() {
        return statusLabel;
    }

    public void clearForm() {
        patientComboBox.setSelectedIndex(0);
        doctorComboBox.setSelectedIndex(0);
        diagnosisField.setText("");
        treatmentField.setText("");
        priceField.setText("");
        statusLabel.setText("Ready to create report");
        statusLabel.setForeground(new Color(51, 102, 255));
    }

    public void updateStatus(String message, Color color) {
        statusLabel.setText(message);
        statusLabel.setForeground(color);
    }

    private JButton clearBtn;
    private JButton clearSearchBtn;
    private JButton createBtn;
    private JTextField diagnosisField;
    private JComboBox<String> doctorComboBox;
    private JLabel jLabel2;
    private JLabel jLabel3;
    private JPanel jPanel1;
    private JPanel jPanel3;
    private JPanel jPanel4;
    private JPanel jPanel5;
    private JScrollPane jScrollPane1;
    private JComboBox<String> patientComboBox;
    private JTextField priceField;
    private JTable reportsTable;
    private JTextField searchField;
    private JLabel statusLabel;
    private JTextField treatmentField;
}
