package br.edu.ifsp.doo.petshop.controller;

import br.edu.ifsp.doo.petshop.model.entities.*;
import br.edu.ifsp.doo.petshop.model.usecases.UCManageConsultation;
import br.edu.ifsp.doo.petshop.persistence.dao.*;
import br.edu.ifsp.doo.petshop.view.loaders.WindowProductManager;
import br.edu.ifsp.doo.petshop.view.util.InputValidator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class CtrlWindowConsultation {
    @FXML ComboBox<Client> cbxClient;
    @FXML ComboBox<Animal> cbxAnimal;
    @FXML ComboBox<Veterinary> cbxVeterinary;

    @FXML DatePicker txtDate;

    @FXML TextField txtInitialTime;
    @FXML TextField txtEndTime;
    @FXML TextField txtPrice;

    @FXML Button btnModifyStartTime;
    @FXML Button btnAddProduct;
    @FXML Button btnFinalizeConsultant;
    @FXML Button btnSaveVeterinaryConsultant;
    @FXML Button btnCloseVeterinaryConsultant;

    @FXML TextArea txaAnnotations;

    @FXML TableView tblProducts;

    @FXML TableColumn clnName;
    @FXML TableColumn clnPrice;

    private String errorMessage;

    private Consultation consultationToSet;
    private Consultation consultationToSaveOrUpdate;

    private List<Client> clients;
    private List<Animal> animals;
    private List<Veterinary> veterinaries;

    private UCManageConsultation ucManageConsultation;

    public CtrlWindowConsultation() {
        ucManageConsultation = new UCManageConsultation(new DAOConsultation(), new DAOVeterinary(), new DAOClient(), new DAOAnimal(), new DAOProduct());
    }

    @FXML
    private void initialize() {

    }

    public void changeClient(ActionEvent actionEvent) {
    }

    public void changeAnimal(ActionEvent actionEvent) {
    }

    public void changeVeterinary(ActionEvent actionEvent) {
    }

    public void modifyStartTime(ActionEvent actionEvent) {
    }

    public void addProduct(ActionEvent actionEvent) {
        // TODO: Criar nova tela para selecionar ou excluir produtos
        WindowProductManager windowProductManager = new WindowProductManager();
        windowProductManager.startModal();
    }

    public void finalizeConsultant(ActionEvent actionEvent) {
    }

    public void saveVeterinaryConsultant(ActionEvent actionEvent) {
    }

    public void closeVeterinaryConsultant(ActionEvent actionEvent) {
        closeStage();
    }

    private void closeStage() {
        Stage stage = (Stage) btnCloseVeterinaryConsultant.getScene().getWindow();
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
        ucManageConsultation.saveOrUpdate(consultationToSaveOrUpdate);
        closeStage();
    }

    private String getEntityFromView () {
        consultationToSaveOrUpdate = new Consultation();
        try {
            consultationToSaveOrUpdate.setVeterinary(cbxVeterinary.getValue());
            consultationToSaveOrUpdate.setAnimal(cbxAnimal.getValue());
            TimeLapse timeLapse = new TimeLapse();
            timeLapse.setStartTime();
            consultationToSaveOrUpdate.setTimeLapse(txtEmail.getText().trim());
            consultationToSaveOrUpdate.setPhone(txtPhone.getText().trim());
            consultationToSaveOrUpdate.setCell(txtCell.getText().trim());
            consultationToSaveOrUpdate.setAddress(txaAddress.getText().trim());
            consultationToSaveOrUpdate.setPassword(isUpdateRequest() ? null:txtPassword.getText().trim());
            consultationToSaveOrUpdate.setActive(chkActive.isSelected());
        } catch (Exception e) {
            return e.getMessage();
        }
        return null;
    }

    public TimeLapse setTimeLapseFromView() {
        TimeLapse timeLapse = new TimeLapse();

        return timeLapse;
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
