package br.edu.ifsp.doo.petshop.controller;

import br.edu.ifsp.doo.petshop.main.Main;
import br.edu.ifsp.doo.petshop.model.entities.Veterinary;
import br.edu.ifsp.doo.petshop.model.usecases.UCManageSecretary;
import br.edu.ifsp.doo.petshop.model.usecases.UCManageVeterinary;
import br.edu.ifsp.doo.petshop.persistence.dao.DAOSecretary;
import br.edu.ifsp.doo.petshop.persistence.dao.DAOVeterinary;
import br.edu.ifsp.doo.petshop.view.loaders.WindowVeterinaryConsultation;
import br.edu.ifsp.doo.petshop.view.util.InputTextMask;
import br.edu.ifsp.doo.petshop.view.util.InputValidator;
import br.edu.ifsp.doo.petshop.view.util.TextFieldFormater;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.util.Arrays;
import java.util.List;

public class CtrlWindowVeterinary {
    @FXML TextField txtName;
    @FXML TextField txtCpf;
    @FXML TextField txtEmail;
    @FXML TextField txtPhone;
    @FXML TextField txtCell;

    @FXML CheckBox chkActive;

    @FXML PasswordField txtPassword;
    @FXML PasswordField txtConfirmPassword;

    @FXML TextArea txaAddress;

    @FXML Button btnAddNewConsultant;
    @FXML Button btnSaveVeterinary;
    @FXML Button btnCloseVeterinary;

    @FXML TableView tblSchedule;

    @FXML TableColumn clnDate;
    @FXML TableColumn clnStartTime;
    @FXML TableColumn clnEndTime;
    @FXML TableColumn clnClientName;
    @FXML TableColumn clnAnimalName;

    private Veterinary veterinary;
    private UCManageVeterinary ucManageVeterinary;
    private String errorMessage;

    public void addNewConsultantion(ActionEvent actionEvent) {
        WindowVeterinaryConsultation windowVeterinaryConsultation = new WindowVeterinaryConsultation();
        windowVeterinaryConsultation.startModal();
    }

    public void saveVeterinary(ActionEvent actionEvent) {
        identifyErrorsAndBuildMsg();
        if (allViewDataIsOk())
            saveOrUpdateVeterinary ();
        else
            showErrorMessage("Erro");
    }

    public void closeVeterinary(ActionEvent actionEvent) {
        closeStage();
    }

    private void closeStage() {
        Stage stage = (Stage) btnCloseVeterinary.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void initialize() {
        InputTextMask.maskCPF(txtCpf);
        InputTextMask.maskEmail(txtEmail);
        InputTextMask.maskPhoneOrCell(txtPhone);
        InputTextMask.maskPhoneOrCell(txtCell);
        //configureTextFields();
    }

    private void instanceEntityIfNull() {
        if (veterinary == null)
            veterinary = new Veterinary();
    }

    public CtrlWindowVeterinary() {
        ucManageVeterinary = new UCManageVeterinary(new DAOVeterinary());
    }

    public void sendViewVeterinary(ActionEvent actionEvent) {
        identifyErrorsAndBuildMsg();
        if (allViewDataIsOk())
            saveOrUpdateVeterinary ();
        else
            showErrorMessage("Erro");
    }

    private void saveOrUpdateVeterinary () {
        errorMessage = getEntityFromView();
        if (!allViewDataIsOk())
            showErrorMessage("Erro");
        else
            requestSaveOrUpdate();
    }

    private void requestSaveOrUpdate () {
        ucManageVeterinary.saveOrUpdate(veterinary);
        // TODO: Atualizar tabela de veterinários
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
            veterinary.setCpf(txtCpf.getText().trim());
            veterinary.setName(txtName.getText().trim());
            veterinary.setEmail(txtEmail.getText().trim());
            veterinary.setPhone(txtPhone.getText().trim());
            veterinary.setCell(txtCell.getText().trim());
            veterinary.setAddress(txaAddress.getText().trim());
            veterinary.setPassword(txtPassword.getText().trim());
            veterinary.setActive(chkActive.isSelected());
        } catch (Exception e) {
            return e.getMessage();
        }
        return null;
    }

    public void setEntityToView(Veterinary veterinary) {
        this.veterinary = veterinary;

        txtCpf.setText(veterinary.getCpf());
        txtName.setText(veterinary.getName());
        txtEmail.setText(veterinary.getEmail());
        txtPhone.setText(veterinary.getPhone());
        txtCell.setText(veterinary.getCell());
        txaAddress.setText(veterinary.getAddress());
        chkActive.setSelected(veterinary.getActive());
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
        if(veterinaryInfoIsIncomplete())
            appendErrorMessage("Todos os dados devem ser preenchidos.");
        if (confirmPasswordIsOk())
            appendErrorMessage("As senhas informadas não combinam.");
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
                txtPhone.getText(), txtCell.getText(), txaAddress.getText(),
                txtPassword.getText());
        return dataList;
    }

    // Métodos específicos para validação de dados
    private boolean veterinaryInfoIsIncomplete() {
        return someStringsAreNotFilled(getAllDataViewAsList()) || !allStringsAreFilled(getAllDataViewAsList());
    }

    private boolean confirmPasswordIsOk() {
        return !txtPassword.getText().equals(txtConfirmPassword.getText());
    }
}
