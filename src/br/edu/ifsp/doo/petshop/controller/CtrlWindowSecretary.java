package br.edu.ifsp.doo.petshop.controller;

import br.edu.ifsp.doo.petshop.model.entities.Secretary;
import br.edu.ifsp.doo.petshop.model.usecases.UCManageSecretary;
import br.edu.ifsp.doo.petshop.persistence.dao.DAOSecretary;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.util.Arrays;
import java.util.List;

import br.edu.ifsp.doo.petshop.view.util.TextFieldFormater;
import br.edu.ifsp.doo.petshop.view.util.InputValidator;
import br.edu.ifsp.doo.petshop.view.util.InputTextMask;

public class CtrlWindowSecretary {
    @FXML TextField txtName;
    @FXML TextField txtCpf;
    @FXML TextField txtEmail;
    @FXML TextField txtPhone;
    @FXML TextField txtCell;

    @FXML PasswordField txtPassword;
    @FXML PasswordField txtConfirmPassword;

    @FXML TextArea txaAddress;

    @FXML Button btnSaveSecretary;
    @FXML Button btnCloseSecretary;

    String errorMessage;

    private Secretary secretary;
    private UCManageSecretary ucManageSecretary;

    public CtrlWindowSecretary() {
        secretary = new Secretary();
        ucManageSecretary = new UCManageSecretary(new DAOSecretary());
    }

    @FXML
    private void initialize() {

        InputTextMask.maskCPF(txtCpf);
        InputTextMask.maskEmail(txtEmail);
        InputTextMask.maskPhoneOrCell(txtPhone);
        InputTextMask.maskPhoneOrCell(txtCell);
    }

    public void sendViewSecretary(ActionEvent actionEvent) {
        identifyErrorsAndBuildMsg();
        if (allViewDataIsOk())
            saveOrUpdateSecretary ();
        else
            showErrorMessage("Erro");
    }

    private void saveOrUpdateSecretary () {
        errorMessage = getEntityFromView();
        if (!allViewDataIsOk())
            showErrorMessage("Erro");
        else
            requestSaveOrUpdate();
    }

    private void requestSaveOrUpdate() {
        ucManageSecretary.saveOrUpdate(secretary);
        closeStage();
    }

    public void closeViewSecretary(ActionEvent actionEvent) {
        closeStage();
    }

    private void closeStage() {
        Stage stage = (Stage) btnCloseSecretary.getScene().getWindow();
        stage.close();
    }

    private String getEntityFromView () {
        try {
            secretary.setCpf(txtCpf.getText().trim());
            secretary.setName(txtName.getText().trim());
            secretary.setEmail(txtEmail.getText().trim());
            secretary.setPhone(txtPhone.getText().trim());
            secretary.setCell(txtCell.getText().trim());
            secretary.setAddress(txaAddress.getText().trim());
            secretary.setPassword(txtPassword.getText().trim());
        } catch (Exception e) {
            return e.getMessage();
        }
        return null;
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
        if(secretaryInfoIsIncomplete())
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

    private List<String> getAllDataFormAsString() {
        List<String> dataList = Arrays.asList(txtCpf.getText(), txtName.getText(), txtEmail.getText(),
                txtPhone.getText(), txtCell.getText(), txaAddress.getText(),
                txtPassword.getText());
        return dataList;
    }

    // Métodos específicos para validação de dados
    private boolean secretaryInfoIsIncomplete() {
        return someStringsAreNotFilled(getAllDataFormAsString()) || !allStringsAreFilled(getAllDataFormAsString());
    }

    private boolean confirmPasswordIsOk() {
        return !txtPassword.getText().equals(txtConfirmPassword.getText());
    }
}
