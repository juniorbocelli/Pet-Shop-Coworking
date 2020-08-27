package br.edu.ifsp.doo.petshop.controller;

import br.edu.ifsp.doo.petshop.model.entities.Animal;
import br.edu.ifsp.doo.petshop.model.entities.Client;
import br.edu.ifsp.doo.petshop.model.usecases.UCManageClient;
import br.edu.ifsp.doo.petshop.persistence.dao.DAOAnimal;
import br.edu.ifsp.doo.petshop.persistence.dao.DAOClient;
import br.edu.ifsp.doo.petshop.view.loaders.WindowAnimal;
import br.edu.ifsp.doo.petshop.view.util.InputTextMask;
import br.edu.ifsp.doo.petshop.view.util.InputValidator;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.util.Arrays;
import java.util.List;

public class CtrlWindowClient {
    @FXML Label lblEmail;
    @FXML Label lblPhone;
    @FXML Label lblAddress;
    @FXML Label lblTableTitle;

    @FXML TextField txtName;
    @FXML TextField txtCpf;
    @FXML TextField txtEmail;
    @FXML TextField txtPhone;
    @FXML TextField txtCell;

    @FXML CheckBox chkTemporaryRegistration;

    @FXML TextArea txaAddress;

    @FXML Button btnAddNewAnimal;
    @FXML Button btnSaveClient;
    @FXML Button btnCloseClient;

    @FXML TableView<Animal> tblAnimal;

    @FXML TableColumn<Animal, String> clnName;
    @FXML TableColumn<Animal, String> clnType;
    @FXML TableColumn<Animal, String> clnGender;
    @FXML TableColumn<Animal, String> clnAge;
    @FXML TableColumn<Animal, String> clnActive;

    private ObservableList<Animal> tableData;
    private List<Animal> allAnimals;

    private Client clientToSaveOrUpdate;
    private Client clientToSet;
    private UCManageClient ucManageClient;
    private String errorMessage;

    public CtrlWindowClient() {
        ucManageClient = new UCManageClient(new DAOClient(), new DAOAnimal());
    }

    @FXML
    private void initialize() {
        InputTextMask.maskCPF(txtCpf);
        InputTextMask.maskEmail(txtEmail);
        InputTextMask.maskPhoneOrCell(txtPhone);
        InputTextMask.maskPhoneOrCell(txtCell);
    }

    public void bindTableViewToItemsList() {
        tableData = FXCollections.observableArrayList();
        tblAnimal.setItems(tableData);
    }

    public void bindColumnsToValueSources() {
        clnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        clnType.setCellValueFactory((param) -> new SimpleStringProperty(param.getValue().getAnimalType().toString()));
        clnGender.setCellValueFactory((param) -> new SimpleStringProperty(param.getValue().getGender().toString()));
        clnAge.setCellValueFactory((param) -> new SimpleStringProperty(Integer.toString(param.getValue().getAge())));
        clnActive.setCellValueFactory((param) -> new SimpleStringProperty(param.getValue().isActive()?"Ativo":"Inativo"));
    }

    private void loadDataAndShow() {
        loadTableDataFromDatabase();
    }

    private void loadTableDataFromDatabase() {
        allAnimals = ucManageClient.selectAllWithOwnerAndVeterinary(clientToSet);
        tableData.setAll(allAnimals);
    }

    public void addNewAnimal(ActionEvent actionEvent) {
        WindowAnimal windowAnimal = new WindowAnimal();
        windowAnimal.startModal(clientToSet);
    }

    public void sendViewClient(ActionEvent actionEvent) {
        identifyErrorsAndBuildMsg();
        if (allViewDataIsOk())
            saveOrUpdateClient();
        else
            showErrorMessage("Erro");
    }

    public void closeClient(ActionEvent actionEvent) {
        closeStage();
    }

    private void closeStage() {
        Stage stage = (Stage) btnCloseClient.getScene().getWindow();
        stage.close();
    }

    public void checkTemporaryRegistration(ActionEvent actionEvent) {
        refreshRequiredFields();
    }

    private void saveOrUpdateClient() {
        errorMessage = getEntityFromView();
        if (!allViewDataIsOk())
            showErrorMessage("Erro");
        else
            requestSaveOrUpdate();
    }

    private void requestSaveOrUpdate () {
        ucManageClient.saveOrUpdate(clientToSaveOrUpdate);
        closeStage();
    }

    private String getEntityFromView () {
        clientToSaveOrUpdate = new Client();
        try {
            clientToSaveOrUpdate.setCpf(txtCpf.getText().trim());
            clientToSaveOrUpdate.setName(txtName.getText().trim());
            clientToSaveOrUpdate.setEmail(txtEmail.getText().trim());
            clientToSaveOrUpdate.setPhone(txtPhone.getText().trim());
            clientToSaveOrUpdate.setCell(txtCell.getText().trim());
            clientToSaveOrUpdate.setAddress(txaAddress.getText().trim());
            clientToSaveOrUpdate.setTemporaryRegistration(chkTemporaryRegistration.isSelected());
        } catch (Exception e) {
            return e.getMessage();
        }
        return null;
    }

    public void setEntityToView(Client client) {
        this.clientToSet = client;

        txtCpf.setText(client.getMaskedCpf());
        txtName.setText(client.getName());
        txtEmail.setText(client.getEmail());
        txtPhone.setText(client.getMaskedPhone());
        txtCell.setText(client.getMaskedCell());
        txaAddress.setText(client.getAddress());
        chkTemporaryRegistration.setSelected(client.isTemporaryRegistration());

        setViewToEditMode();
    }

    private void setViewToEditMode() {
        setAnimalTableData();

        btnAddNewAnimal.setDisable(false);
        lblTableTitle.setDisable(false);
        tblAnimal.setDisable(false);
    }

    private void setAnimalTableData() {
        bindTableViewToItemsList();
        bindColumnsToValueSources();
        loadDataAndShow();
    }

    /**
     * Validação e exibição de mensagens
     */

    private void showErrorMessage(String title) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(errorMessage);
        alert.setContentText(null);
        alert.showAndWait();
        errorMessage = null;
    }

    private void identifyErrorsAndBuildMsg() {
        if(clientInfoIsIncomplete() && !chkTemporaryRegistration.isSelected())
            appendErrorMessage("Todos os dados devem ser preenchidos.");
        if(clientInfoRequiredIsIncomplete() && chkTemporaryRegistration.isSelected())
            appendErrorMessage("Todos os dados devem ser preenchidos.");
        if (!txtCpf.getText().isEmpty() && !InputValidator.isCPF(txtCpf.getText()))
            appendErrorMessage("O formato do CPF é inválido.");
        if (!txtPhone.getText().isEmpty() && !InputValidator.isPhone(txtPhone.getText()))
            appendErrorMessage("O formato do Telefone é inválido.");
        if (!txtCell.getText().isEmpty() && !InputValidator.isCell(txtCell.getText()))
            appendErrorMessage("O formato do Celular é inválido.");
        if (!txtEmail.getText().isEmpty() && !InputValidator.isEmail(txtEmail.getText()))
            appendErrorMessage("O E-mail informado é inválido.");
    }

    private void appendErrorMessage(String message) {
        if (errorMessage == null)
            errorMessage = " Erro(s) na solicitação: \n";
        errorMessage += "\n - ".concat(message);
    }

    private boolean allStringsAreFilled(List<String> list){
        int totalElements = list.size();
        int filledElements = (int) list.parallelStream().filter(x -> !x.equals("")).count();
        return filledElements == totalElements;
    }

    private boolean someStringsAreNotFilled(List<String> list){
        boolean anyStringIsFilled = (int) list.parallelStream().filter(x -> !x.equals("")).count() > 0;
        return  anyStringIsFilled && !allStringsAreFilled(list);
    }

    private boolean allViewDataIsOk() {
        return errorMessage == null;
    }

    private List<String> getAllDataViewAsList() {
        List<String> dataList = Arrays.asList(txtCpf.getText(), txtName.getText(), txtEmail.getText(),
                txtPhone.getText(), txtCell.getText(), txaAddress.getText());
        return dataList;
    }

    private List<String> getRequiredDataViewAsList() {
        List<String> dataList = Arrays.asList(txtCpf.getText(), txtName.getText(), txtCell.getText());
        return dataList;
    }

    // Métodos específicos para validação de dados
    private boolean clientInfoIsIncomplete() {
        return someStringsAreNotFilled(getAllDataViewAsList()) || !allStringsAreFilled(getAllDataViewAsList());
    }

    private boolean clientInfoRequiredIsIncomplete() {
        return someStringsAreNotFilled(getRequiredDataViewAsList()) || !allStringsAreFilled(getRequiredDataViewAsList());
    }

    private void disableUnrequiredFields () {
        lblEmail.setDisable(true);
        txtEmail.setDisable(true);
        lblPhone.setDisable(true);
        txtPhone.setDisable(true);
        lblAddress.setDisable(true);
        txaAddress.setDisable(true);
    }

    private void enableUnrequiredFields () {
        lblEmail.setDisable(false);
        txtEmail.setDisable(false);
        lblPhone.setDisable(false);
        txtPhone.setDisable(false);
        lblAddress.setDisable(false);
        txaAddress.setDisable(false);
    }

    public void refreshRequiredFields() {
        if (chkTemporaryRegistration.isSelected())
            disableUnrequiredFields();
        else
            enableUnrequiredFields();
    }

    public void editAnimal(MouseEvent mouseEvent) {
        Animal selectedAnimal = tblAnimal.getSelectionModel().getSelectedItem();
        if (mouseEvent.getClickCount() == 2 && selectedAnimal != null) {
            WindowAnimal windowAnimal = new WindowAnimal();
            windowAnimal.startModal(selectedAnimal, clientToSet);

            loadDataAndShow();
        }
    }
}
