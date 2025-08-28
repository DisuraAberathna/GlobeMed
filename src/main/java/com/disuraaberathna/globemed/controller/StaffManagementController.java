package com.disuraaberathna.globemed.controller;

import com.disuraaberathna.globemed.enums.Status;
import com.disuraaberathna.globemed.enums.UserRoles;
import com.disuraaberathna.globemed.enums.UserTitles;
import com.disuraaberathna.globemed.model.dao.UserDAO;
import com.disuraaberathna.globemed.model.entity.User;
import com.disuraaberathna.globemed.model.service.composite.StaffGroup;
import com.disuraaberathna.globemed.util.LoggerUtil;
import com.disuraaberathna.globemed.view.StaffManagementView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Base64;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

public class StaffManagementController {
    private final StaffManagementView view;
    private final UserDAO userDAO;
    private final StaffGroup staffGroup;
    private List<User> allStaff;
    private User selectedUser;
    private final static Logger logger = LoggerUtil.getLogger();
    private final JPanel viewPanel;
    private String details;

    public StaffManagementController(StaffManagementView view, UserDAO userDAO, JPanel viewPanel) {
        this.view = view;
        this.userDAO = userDAO;
        this.viewPanel = viewPanel;
        this.staffGroup = new StaffGroup("All Staff");

        loadStaffTable();
        addEventListeners();
        buildStaffHierarchy();
    }

    private void loadStaffTable() {
        allStaff = userDAO.getAllUsers();
        updateStaffTable(allStaff);
    }

    private void updateStaffTable(List<User> staff) {
        DefaultTableModel model = (DefaultTableModel) view.getStaffTable().getModel();
        model.setRowCount(0);

        for (User user : staff) {
            Vector<String> row = new Vector<>();
            row.add(String.valueOf(user.getId()));
            row.add(user.getRole().toString());
            row.add(user.getTitle() != null ? user.getTitle().toString() : "");
            row.add(user.getFirstName());
            row.add(user.getLastName());
            row.add(user.getUsername());
            row.add(user.getStatus().toString());
            model.addRow(row);
        }
    }

    private void selectStaffFromTable(int row) {
        if (row >= 0 && row < allStaff.size()) {
            selectedUser = allStaff.get(row);
            populateFormFromUser(selectedUser);
            view.updateStatus("Selected staff for modification", new Color(230, 126, 34));
        }
    }

    private void populateFormFromUser(User user) {
        view.getRoleComboBox().setSelectedItem(user.getRole().toString());
        view.getTitleComboBox().setSelectedItem(user.getTitle() != null ? user.getTitle().toString() : "DR");
        view.getFnameField().setText(user.getFirstName());
        view.getLnameField().setText(user.getLastName());
        view.getUsernameField().setText(user.getUsername());
        view.getStatusComboBox().setSelectedItem(user.getStatus().toString());
    }

    private void buildStaffHierarchy() {
        for (User user : allStaff) {
            staffGroup.addMember(user);
        }

        details = "Staff Hierarchy : \n" + staffGroup.showDetails();
        System.out.println(details);
    }

    private boolean validateStaffDataFields() {
        if (view.getFnameField().getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(viewPanel.getParent(), "First name is required.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (view.getLnameField().getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(viewPanel.getParent(), "Last name is required.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (view.getUsernameField().getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(viewPanel.getParent(), "Username is required.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (view.getPasswordField().getPassword().length == 0) {
            JOptionPane.showMessageDialog(viewPanel.getParent(), "Password is required.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return true;
    }

    private void addEventListeners() {
        view.getAddBtn().addActionListener(e -> {
            if (!validateStaffDataFields()) {
                return;
            }

            try {
                User newUser = new User();
                newUser.setRole(UserRoles.valueOf(String.valueOf(view.getRoleComboBox().getSelectedItem())));
                newUser.setTitle(UserTitles.valueOf(String.valueOf(view.getTitleComboBox().getSelectedItem())));
                newUser.setFirstName(view.getFnameField().getText().trim());
                newUser.setLastName(view.getLnameField().getText().trim());
                newUser.setUsername(view.getUsernameField().getText().trim());

                String password = new String(view.getPasswordField().getPassword());
                newUser.setPassword(Base64.getEncoder().encodeToString(password.getBytes()));

                newUser.setStatus(Status.valueOf((String) view.getStatusComboBox().getSelectedItem()));

                userDAO.saveUser(newUser);

                staffGroup.addMember(newUser);

                view.updateStatus("Staff added successfully!", new Color(46, 204, 113));
                JOptionPane.showMessageDialog(viewPanel.getParent(), "Staff added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);

                loadStaffTable();
                view.clearForm();
                selectedUser = null;
            } catch (Exception ex) {
                view.updateStatus("Error adding staff: " + ex.getMessage(), new Color(231, 76, 60));
                JOptionPane.showMessageDialog(viewPanel.getParent(), "Error adding staff: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                logger.log(Level.SEVERE, "Error adding staff: " + ex.getMessage(), ex);
            }
        });

        view.getUpdateBtn().addActionListener(e -> {
            if (selectedUser == null) {
                JOptionPane.showMessageDialog(viewPanel.getParent(), "Please select a staff member to update.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!validateStaffDataFields()) {
                return;
            }

            try {
                selectedUser.setRole(UserRoles.valueOf(String.valueOf(view.getRoleComboBox().getSelectedItem())));
                selectedUser.setTitle(UserTitles.valueOf(String.valueOf(view.getTitleComboBox().getSelectedItem())));
                selectedUser.setFirstName(view.getFnameField().getText().trim());
                selectedUser.setLastName(view.getLnameField().getText().trim());
                selectedUser.setUsername(view.getUsernameField().getText().trim());

                String newPassword = new String(view.getPasswordField().getPassword());
                if (!newPassword.isEmpty()) {
                    selectedUser.setPassword(Base64.getEncoder().encodeToString(newPassword.getBytes()));
                }

                selectedUser.setStatus(Status.valueOf((String) view.getStatusComboBox().getSelectedItem()));

                userDAO.updateUser(selectedUser);

                view.updateStatus("Staff updated successfully!", new Color(46, 204, 113));
                JOptionPane.showMessageDialog(viewPanel.getParent(), "Staff updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);

                loadStaffTable();
                view.clearForm();
                selectedUser = null;
            } catch (Exception ex) {
                view.updateStatus("Error updating staff: " + ex.getMessage(), new Color(231, 76, 60));
                JOptionPane.showMessageDialog(viewPanel.getParent(), "Error updating staff: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                logger.log(Level.SEVERE, "Error updating staff: " + ex.getMessage(), ex);
            }
        });

        view.getDeleteBtn().addActionListener(e -> {
            if (selectedUser == null) {
                JOptionPane.showMessageDialog(viewPanel.getParent(), "Please select a staff member to delete.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int confirm = JOptionPane.showConfirmDialog(viewPanel.getParent(), "Are you sure you want to delete this staff member?", "Confirm Deletion", JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                try {
                    userDAO.deleteUser(selectedUser.getId());

                    staffGroup.removeMember(selectedUser);

                    view.updateStatus("Staff deleted successfully!", new Color(46, 204, 113));
                    JOptionPane.showMessageDialog(viewPanel.getParent(), "Staff deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);

                    loadStaffTable();
                    view.clearForm();
                    selectedUser = null;
                } catch (Exception ex) {
                    view.updateStatus("Error deleting staff: " + ex.getMessage(), new Color(231, 76, 60));
                    JOptionPane.showMessageDialog(viewPanel.getParent(), "Error deleting staff: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    logger.log(Level.SEVERE, "Error deleting staff: " + ex.getMessage(), ex);
                }
            }
        });

        view.getSearchField().addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyReleased(java.awt.event.KeyEvent e) {
                if (e.getKeyCode() != KeyEvent.VK_ENTER) {
                    return;
                }

                String searchTerm = view.getSearchField().getText().trim().toLowerCase();

                if (searchTerm.isEmpty()) {
                    loadStaffTable();
                    view.updateStatus("Showing all staff", new Color(52, 152, 219));
                    return;
                }

                List<User> filteredStaff = allStaff.stream()
                        .filter(user -> user.getFirstName().toLowerCase().contains(searchTerm) ||
                                user.getLastName().toLowerCase().contains(searchTerm) ||
                                user.getUsername().toLowerCase().contains(searchTerm) ||
                                user.getRole().toString().toLowerCase().contains(searchTerm) ||
                                user.getStatus().toString().toLowerCase().contains(searchTerm))
                        .toList();

                updateStaffTable(filteredStaff);
                view.updateStatus("Found " + filteredStaff.size() + " staff member(s)", new Color(52, 152, 219));
            }
        });

        view.getStaffTable().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selectedRow = view.getStaffTable().getSelectedRow();

                if (selectedRow >= 0) {
                    selectStaffFromTable(selectedRow);
                }
            }
        });

        view.getClearBtn().addActionListener(e -> {
            view.getSearchField().setText("");
            loadStaffTable();
        });
    }

    public void showView() {
        viewPanel.removeAll();
        viewPanel.add(view);
        SwingUtilities.updateComponentTreeUI(viewPanel);
    }
}
