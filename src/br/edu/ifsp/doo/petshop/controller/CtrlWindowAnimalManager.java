package br.edu.ifsp.doo.petshop.controller;

import br.edu.ifsp.doo.petshop.model.entities.Animal;
import br.edu.ifsp.doo.petshop.model.entities.Client;
import br.edu.ifsp.doo.petshop.model.usecases.UCManageAnimal;
import br.edu.ifsp.doo.petshop.persistence.dao.DAOAnimal;
import br.edu.ifsp.doo.petshop.persistence.dao.DAOClient;
import br.edu.ifsp.doo.petshop.view.loaders.WindowAnimal;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.util.StringConverter;

import java.util.List;
import java.util.stream.Collectors;

public class CtrlWindowAnimalManager {
    @FXML TextField txtSearch;

    @FXML CheckBox chkInactive;

    @FXML ComboBox cbxOwner;
    private StringConverter<Client> stringClientConverter;

    @FXML Button btnAddVeterinary;

    @FXML TableView<Animal> tblAnimal;

    @FXML TableColumn<Animal, String> clnName;
    @FXML TableColumn<Animal, String> clnOwner;
    @FXML TableColumn<Animal, String> clnType;
    @FXML TableColumn<Animal, String> clnGender;
    @FXML TableColumn<Animal, String> clnAge;

    private ObservableList<Client> clients;

    private UCManageAnimal ucManageAnimal;

    private List<Animal> allAnimals;
    private List<Animal> filteredAnimals;
    private ObservableList<Animal> tableData;
    private String filter;
    private boolean showingInactive;

    @FXML
    private void initialize() {
        loadClientsInComboBox();
        bindTableViewToItemsList();
        bindColumnsToValueSources();
        loadDataAndShow();
    }

    public CtrlWindowAnimalManager() {
        ucManageAnimal = new UCManageAnimal(new DAOAnimal(), new DAOClient(), null);
    }

    public void addNewAnimal(ActionEvent actionEvent) {
        WindowAnimal windowAnimal = new WindowAnimal();
        windowAnimal.startModal();

        loadDataAndShow();
    }

    public void filterByOwner(ActionEvent actionEvent) {
        showFilteredData();
    }

    public void bindTableViewToItemsList() {
        tableData = FXCollections.observableArrayList();
        tblAnimal.setItems(tableData);
    }

    public void bindColumnsToValueSources() {
        clnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        clnOwner.setCellValueFactory((param) -> new SimpleStringProperty(param.getValue().getOwner().getName()));
        clnType.setCellValueFactory((param) -> new SimpleStringProperty(param.getValue().getAnimalType().toString()));
        clnGender.setCellValueFactory((param) -> new SimpleStringProperty(param.getValue().getGender().toString()));
        clnAge.setCellValueFactory((param) -> new SimpleStringProperty( Integer.toString(param.getValue().getAge())));
    }

    private void loadDataAndShow() {
        loadTableDataFromDatabase();
        showFilteredData();
    }

    public void loadTableDataFromDatabase() {
        allAnimals = ucManageAnimal.selectAllWithOwnerAndVeterinary();
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
        filterDataFromOwner();
        filterDataFromSubstring();
    }

    private void filterDataFromCheckboxes() {
        filteredAnimals = allAnimals.
                parallelStream().
                filter(c -> c.matchesSearchByInactive(showingInactive)).
                collect(Collectors.toList());
    }

    private void filterDataFromOwner() {
        if (clientIsNotNull())
            filteredAnimals = filteredAnimals.
                parallelStream().
                filter(c -> c.matchesSearchByClient(getClientFromView().getCpf())).
                collect(Collectors.toList());
    }

    private Client getClientFromView() {
        return (Client)cbxOwner.getValue();
    }

    private void filterDataFromSubstring() {
        if(substringIsNotEmpty())
            filteredAnimals = filteredAnimals.
                    parallelStream().
                    filter(c -> c.matchesSearchString(filter)).
                    collect(Collectors.toList());
    }

    private boolean substringIsNotEmpty() {
        return !filter.equals("");
    }

    private void loadTableWithFilteredData() {
        tableData.setAll(filteredAnimals);
    }

    public void filterTableData(KeyEvent keyEvent) {
        showFilteredData();
    }

    public void filterByActive(ActionEvent actionEvent) {
        showFilteredData();
    }

    public void editAnimal(MouseEvent mouseEvent) {
        Animal selectedAnimal = tblAnimal.getSelectionModel().getSelectedItem();
        if (mouseEvent.getClickCount() == 2 && selectedAnimal != null) {
            WindowAnimal windowAnimal = new WindowAnimal();
            windowAnimal.startModal(selectedAnimal, selectedAnimal.getName());

            loadDataAndShow();
        }
    }

    private void loadClientsInComboBox() {
        clients = FXCollections.observableArrayList(ucManageAnimal.getClientsList());
        cbxOwner.setItems(clients);
        cbxOwner.getItems().add(0, new Client());
        stringClientConverter = new StringConverter<Client>() {

            @Override
            public String toString(Client object) {
                return object.getName();
            }

            @Override
            public Client fromString(String cpf) {
                return clients.stream()
                        .filter(item -> item.getCpf().equals(cpf))
                        .collect(Collectors.toList()).get(0);
            }
        };

        cbxOwner.setConverter(stringClientConverter);

    }

    private boolean clientIsNotNull() {
        return !(getClientFromView() == null);
    }
}
