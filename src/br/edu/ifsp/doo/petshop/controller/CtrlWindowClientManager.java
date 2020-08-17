package br.edu.ifsp.doo.petshop.controller;

import br.edu.ifsp.doo.petshop.model.entities.Client;
import br.edu.ifsp.doo.petshop.model.usecases.UCManageClient;
import br.edu.ifsp.doo.petshop.persistence.dao.DAOClient;
import br.edu.ifsp.doo.petshop.view.loaders.WindowClient;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;

import java.util.List;
import java.util.stream.Collectors;

public class CtrlWindowClientManager {
    @FXML TextField txtSearch;

    @FXML CheckBox chkTemporaryRegistration;

    @FXML Button btnAddNewClient;

    @FXML TableView<Client> tblClient;

    @FXML TableColumn<Client, String> clnCpf;
    @FXML TableColumn<Client, String> clnName;
    @FXML TableColumn<Client, String> clnEmail;
    @FXML TableColumn<Client, String> clnPhone;
    @FXML TableColumn<Client, String> clnCell;

    private List<Client> allClients;
    private List<Client> filteredClients;
    private ObservableList<Client> tableData;
    private String filter;
    private boolean showingTemporaryRegistration;

    private UCManageClient ucManageClient;

    public CtrlWindowClientManager() {
        ucManageClient = new UCManageClient(new DAOClient());
    }

    @FXML
    private void initialize() {
        bindTableViewToItemsList();
        bindColumnsToValueSources();
        loadDataAndShow();
    }

    public void bindTableViewToItemsList() {
        tableData = FXCollections.observableArrayList();
        tblClient.setItems(tableData);
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
        allClients = ucManageClient.selectAll();
    }

    public void showFilteredData() {
        getFiltersFromView();
        updateTableViewFromFilters();
    }

    public void getFiltersFromView() {
        filter = txtSearch.getText();
        showingTemporaryRegistration = chkTemporaryRegistration.isSelected();
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
        filteredClients = allClients.
                parallelStream().
                filter(c -> c.matchesTemporaryRegistration(showingTemporaryRegistration)).
                collect(Collectors.toList());
    }

    private void filterDataFromSubstring() {
        if(substringIsNotEmpty())
            filteredClients = filteredClients.
                    parallelStream().
                    filter(c -> c.matchesSearchString(filter)).
                    collect(Collectors.toList());
    }

    private boolean substringIsNotEmpty() {
        return !filter.equals("");
    }

    private void loadTableWithFilteredData() {
        tableData.setAll(filteredClients);
    }

    public void filterTableData(KeyEvent keyEvent) {
        showFilteredData();
    }

    public void addNewClient(ActionEvent actionEvent) {
        WindowClient windowClient = new WindowClient();
        windowClient.startModal();
    }
}
