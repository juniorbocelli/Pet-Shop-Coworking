package br.edu.ifsp.doo.petshop.controller;

import br.edu.ifsp.doo.petshop.model.entities.Product;
import br.edu.ifsp.doo.petshop.model.usecases.UCManageProduct;
import br.edu.ifsp.doo.petshop.persistence.dao.DAOProduct;
import br.edu.ifsp.doo.petshop.view.loaders.WindowProduct;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;

import java.util.List;
import java.util.stream.Collectors;

public class CtrlWindowProductManager {
    @FXML TextField txtSearch;

    @FXML CheckBox chkInactive;

    @FXML Button btnAddProduct;

    @FXML TableView<Product> tblProduct;

    @FXML TableColumn<Product, String> clnName;
    @FXML TableColumn<Product, Double> clnPrice;

    private List<Product> allProducts;
    private List<Product> filteredProducts;
    private ObservableList<Product> tableData;
    private String filter;
    private boolean showingInactive;

    private UCManageProduct ucManageProduct;

    public CtrlWindowProductManager() {
        ucManageProduct = new UCManageProduct(new DAOProduct());
    }

    @FXML
    private void initialize() {
        bindTableViewToItemsList();
        bindColumnsToValueSources();
        loadDataAndShow();
    }

    public void bindTableViewToItemsList() {
        tableData = FXCollections.observableArrayList();
        tblProduct.setItems(tableData);
    }

    public void bindColumnsToValueSources() {
        clnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        clnPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    private void loadDataAndShow() {
        loadTableDataFromDatabase();
        showFilteredData();
    }

    public void loadTableDataFromDatabase() {
        allProducts = ucManageProduct.selectAll();
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
        filteredProducts = allProducts.
                parallelStream().
                filter(c -> c.matchesSearchByInactive(showingInactive)).
                collect(Collectors.toList());
    }

    private void filterDataFromSubstring() {
        if(substringIsNotEmpty())
            filteredProducts = filteredProducts.
                    parallelStream().
                    filter(c -> c.matchesSearchString(filter)).
                    collect(Collectors.toList());
    }

    private boolean substringIsNotEmpty() {
        return !filter.equals("");
    }

    private void loadTableWithFilteredData() {
        tableData.setAll(filteredProducts);
    }

    public void filterTableData(KeyEvent keyEvent) {
        showFilteredData();
    }

    public void filterByActive(ActionEvent actionEvent) {
        showFilteredData();
    }

    public void search(ActionEvent actionEvent) {

    }

    public void addNewProduct(ActionEvent actionEvent) {
        WindowProduct windowProduct = new WindowProduct();
        windowProduct.startModal();

        loadDataAndShow();
    }
}
