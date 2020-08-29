package br.edu.ifsp.doo.petshop.controller;

import br.edu.ifsp.doo.petshop.model.entities.Veterinary;
import br.edu.ifsp.doo.petshop.model.usecases.UCManageVeterinary;
import br.edu.ifsp.doo.petshop.persistence.dao.DAOVeterinary;
import br.edu.ifsp.doo.petshop.view.loaders.WindowConsultation;
import br.edu.ifsp.doo.petshop.view.util.InputTextMask;
import br.edu.ifsp.doo.petshop.view.util.InputValidator;
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

    private Veterinary veterinaryToSet;
    private Veterinary veterinaryToSaveOrUpdate;
    private UCManageVeterinary ucManageVeterinary;
    private String errorMessage;

    public CtrlWindowVeterinary() {
        ucManageVeterinary = new UCManageVeterinary(new DAOVeterinary());
    }

    @FXML
    private void initialize() {
        InputTextMask.maskCPF(txtCpf);
        InputTextMask.maskEmail(txtEmail);
        InputTextMask.maskPhoneOrCell(txtPhone);
        InputTextMask.maskPhoneOrCell(txtCell);
    }

    public void addNewConsultation(ActionEvent actionEvent) {
        WindowConsultation windowConsultation = new WindowConsultation();
        windowConsultation.startModal();
    }

    public void sendViewVeterinary(ActionEvent actionEvent) {
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

    private void saveOrUpdateVeterinary () {
        errorMessage = getEntityFromView();
        if (!allViewDataIsOk())
            showErrorMessage("Erro");
        else
            requestSaveOrUpdate();
    }

    private void requestSaveOrUpdate () {
        ucManageVeterinary.saveOrUpdate(veterinaryToSaveOrUpdate);
        closeStage();
    }

    private String getEntityFromView () {
        veterinaryToSaveOrUpdate = new Veterinary();
        try {
            veterinaryToSaveOrUpdate.setCpf(txtCpf.getText().trim());
            veterinaryToSaveOrUpdate.setName(txtName.getText().trim());
            veterinaryToSaveOrUpdate.setEmail(txtEmail.getText().trim());
            veterinaryToSaveOrUpdate.setPhone(txtPhone.getText().trim());
            veterinaryToSaveOrUpdate.setCell(txtCell.getText().trim());
            veterinaryToSaveOrUpdate.setAddress(txaAddress.getText().trim());
            veterinaryToSaveOrUpdate.setPassword(isUpdateRequest() ? null:txtPassword.getText().trim());
            veterinaryToSaveOrUpdate.setActive(chkActive.isSelected());
        } catch (Exception e) {
            return e.getMessage();
        }
        return null;
    }

    public void setEntityToView(Veterinary veterinary) {
        this.veterinaryToSet = veterinary;

        txtCpf.setText(veterinary.getMaskedCpf());
        txtName.setText(veterinary.getName());
        txtEmail.setText(veterinary.getEmail());
        txtPhone.setText(veterinary.getMaskedPhone());
        txtCell.setText(veterinary.getMaskedCell());
        txaAddress.setText(veterinary.getAddress());
        chkActive.setSelected(veterinary.getActive());

        setViewToEditMode();
    }

    private void setViewToEditMode() {
        btnAddNewConsultant.setDisable(false);
        tblSchedule.setDisable(false);
    }

    private boolean isUpdateRequest() {
        return veterinaryToSet != null;
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
        if(veterinaryInfoIsIncomplete() && !isUpdateRequest())
            appendErrorMessage("Todos os dados devem ser preenchidos.");
        if(veterinaryInfoRequiredIsIncomplete() && isUpdateRequest())
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

    private boolean veterinaryInfoRequiredIsIncomplete() {
        return someStringsAreNotFilled(getRequiredDataViewAsList()) || !allStringsAreFilled(getRequiredDataViewAsList());
    }

    private List<String> getRequiredDataViewAsList() {
        List<String> dataList = Arrays.asList(txtCpf.getText(), txtName.getText(), txtEmail.getText(),
                txtPhone.getText(), txtCell.getText(), txaAddress.getText());
        return dataList;
    }
}
