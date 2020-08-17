package br.edu.ifsp.doo.petshop.controller;

import br.edu.ifsp.doo.petshop.model.entities.Veterinary;
import br.edu.ifsp.doo.petshop.model.usecases.UCManageVeterinary;
import br.edu.ifsp.doo.petshop.persistence.dao.DAOVeterinary;
import br.edu.ifsp.doo.petshop.view.loaders.WindowVeterinary;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import java.util.List;
import java.util.stream.Collectors;

public class CtrlWindowVeterinaryManager {
    @FXML TextField txtSearch;

    @FXML CheckBox chkInactive;

    @FXML Button btnAddVeterinary;

    @FXML TableView<Veterinary> tblVeterinary;

    @FXML TableColumn<Veterinary, String> clnCpf;
    @FXML TableColumn<Veterinary, String> clnName;
    @FXML TableColumn<Veterinary, String> clnEmail;
    @FXML TableColumn<Veterinary, String> clnPhone;
    @FXML TableColumn<Veterinary, String> clnCell;

    private List<Veterinary> allVeterinaries;
    private List<Veterinary> filteredVeterinaries;
    private ObservableList<Veterinary> tableData;
    private String filter;
    private boolean showingInactive;

    private UCManageVeterinary ucManageVeterinary;

    public CtrlWindowVeterinaryManager() {
        ucManageVeterinary = new UCManageVeterinary(new DAOVeterinary());
    }

    @FXML
    private void initialize() {
        bindTableViewToItemsList();
        bindColumnsToValueSources();
        loadDataAndShow();
    }

    public void bindTableViewToItemsList() {
        tableData = FXCollections.observableArrayList();
        tblVeterinary.setItems(tableData);
    }

    public void bindColumnsToValueSources() {
        clnCpf.setCellValueFactory(new PropertyValueFactory<>("cpf"));
        clnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        clnEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        clnPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        clnCell.setCellValueFactory(new PropertyValueFactory<>("cell"));
    }

    private void loadDataAndShow() {
        loadTableDataFromDatabase();
        showFilteredData();
    }

    public void loadTableDataFromDatabase() {
        allVeterinaries = ucManageVeterinary.selectAll();
    }

    public void showFilteredData() {
        getFiltersFromView();
        updateTableViewFromFilters();
    }

    public void getFiltersFromView() {
        filter = txtSearch.getText();
        showingInactive = chkInactive.isSelected();
    }

    public void updateTableViewFromFilters() {
        filterData();
        loadTableWithFilteredData();
    }

    private void filterData() {
        filterDataFromCheckboxes();
        filterDataFromSubstring();
    }

    private void filterDataFromCheckboxes() {
        filteredVeterinaries = allVeterinaries.
                parallelStream().
                filter(c -> c.matchesSearchByInactive(showingInactive)).
                collect(Collectors.toList());
    }

    private void filterDataFromSubstring() {
        if(substringIsNotEmpty())
            filteredVeterinaries = filteredVeterinaries.
                    parallelStream().
                    filter(c -> c.matchesSearchString(filter)).
                    collect(Collectors.toList());
    }

    private boolean substringIsNotEmpty() {
        return !filter.equals("");
    }

    private void loadTableWithFilteredData() {
        tableData.setAll(filteredVeterinaries);
    }

    public void filterTableData(KeyEvent keyEvent) {
        showFilteredData();
    }

    public void filterByActive(ActionEvent actionEvent) {
        showFilteredData();
    }

    public void addNewVeterinary(ActionEvent actionEvent) {
        WindowVeterinary windowVeterinary = new WindowVeterinary();
        windowVeterinary.startModal();
    }

    public void editClient(MouseEvent mouseEvent) {
        Veterinary selectedVeterinary = tblVeterinary.getSelectionModel().getSelectedItem();
        if (mouseEvent.getClickCount() == 2 && selectedVeterinary != null) {
            WindowVeterinary windowVeterinary = new WindowVeterinary();
            windowVeterinary.startModal(selectedVeterinary, selectedVeterinary.getName());

            loadDataAndShow();
        }
    }
}
