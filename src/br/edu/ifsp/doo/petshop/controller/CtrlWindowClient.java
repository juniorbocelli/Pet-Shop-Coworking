package br.edu.ifsp.doo.petshop.controller;

import br.edu.ifsp.doo.petshop.model.entities.Client;
import br.edu.ifsp.doo.petshop.model.usecases.UCManageClient;
import br.edu.ifsp.doo.petshop.persistence.dao.DAOClient;
import br.edu.ifsp.doo.petshop.view.loaders.WindowAnimal;
import br.edu.ifsp.doo.petshop.view.util.InputTextMask;
import br.edu.ifsp.doo.petshop.view.util.InputValidator;
import br.edu.ifsp.doo.petshop.view.util.TextFieldFormater;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.util.Arrays;
import java.util.List;

public class CtrlWindowClient {
    @FXML Label lblEmail;
    @FXML Label lblPhone;
    @FXML Label lblAddress;

    @FXML TextField txtName;
    @FXML TextField txtCpf;
    @FXML TextField txtEmail;
    @FXML TextField txtPhone;
    @FXML TextField txtCell;

    @FXML CheckBox chkTemporaryRegistration;

    @FXML TextArea txaAddress;

    @FXML Button btnAddNewConsultant;
    @FXML Button btnSaveClient;
    @FXML Button btnCloseClient;

    @FXML TableView tblAnimal;

    @FXML TableColumn clnName;
    @FXML TableColumn clnType;
    @FXML TableColumn clsGender;
    @FXML TableColumn clnAge;
    @FXML TableColumn clnActive;

    private Client client;
    private UCManageClient ucManageClient;
    private String errorMessage;

    public CtrlWindowClient() {
        ucManageClient = new UCManageClient(new DAOClient());
    }

    @FXML
    private void initialize() {
        InputTextMask.maskCPF(txtCpf);
        InputTextMask.maskEmail(txtEmail);
        InputTextMask.maskPhoneOrCell(txtPhone);
        InputTextMask.maskPhoneOrCell(txtCell);
    }

    public void addNewAnimal(ActionEvent actionEvent) {
        WindowAnimal windowAnimal = new WindowAnimal();
        windowAnimal.startModal();
    }

    public void sendViewClient(ActionEvent actionEvent) {
        identifyErrorsAndBuildMsg();
        if (allViewDataIsOk())
            saveOrUpdateVeterinary ();
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

    private void instanceEntityIfNull() {
        if (client == null)
            client = new Client();
    }

    private void saveOrUpdateVeterinary () {
        errorMessage = getEntityFromView();
        if (!allViewDataIsOk())
            showErrorMessage("Erro");
        else
            requestSaveOrUpdate();
    }

    private void requestSaveOrUpdate () {
        ucManageClient.saveOrUpdate(client);
        closeStage();
    }

    private void configureTextFields() {
        TextFieldFormater.formatAsRG(txtCpf);
        TextFieldFormater.formatAsPhoneNumber(txtPhone);
        //TextFieldFormater.formatAsPhoneNumber(txtCell);
    }

    private String getEntityFromView () {
        instanceEntityIfNull();
        try {
            client.setCpf(txtCpf.getText().trim());
            client.setName(txtName.getText().trim());
            client.setEmail(txtEmail.getText().trim());
            client.setPhone(txtPhone.getText().trim());
            client.setCell(txtCell.getText().trim());
            client.setAddress(txaAddress.getText().trim());
            client.setTemporaryRegistration(chkTemporaryRegistration.isSelected());
        } catch (Exception e) {
            return e.getMessage();
        }
        return null;
    }

    public void setEntityToView(Client client) {
        this.client = client;

        txtCpf.setText(client.getCpf());
        txtName.setText(client.getName());
        txtEmail.setText(client.getEmail());
        txtPhone.setText(client.getPhone());
        txtCell.setText(client.getCell());
        txaAddress.setText(client.getAddress());
        chkTemporaryRegistration.setSelected(client.isTemporaryRegistration());
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
}
