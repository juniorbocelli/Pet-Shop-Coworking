package br.edu.ifsp.doo.petshop.controller;

import br.edu.ifsp.doo.petshop.model.entities.*;
import br.edu.ifsp.doo.petshop.model.usecases.UCManageConsultation;
import br.edu.ifsp.doo.petshop.persistence.dao.*;
import br.edu.ifsp.doo.petshop.view.loaders.WindowProductManager;
import br.edu.ifsp.doo.petshop.view.util.InputTextMask;
import br.edu.ifsp.doo.petshop.view.util.InputValidator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CtrlWindowConsultation {
    @FXML ComboBox<Client> cbxClient;
    private StringConverter<Client> stringClientConverter;

    @FXML ComboBox<Animal> cbxAnimal;
    private StringConverter<Animal> stringAnimalConverter;

    @FXML ComboBox<Veterinary> cbxVeterinary;
    private StringConverter<Veterinary> stringVeterinaryConverter;

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

    private ObservableList<Client> clients;
    private ObservableList<Animal> animals;
    private ObservableList<Veterinary> veterinaries;

    private boolean itsPaid;
    private boolean isPaymentRequest;

    private UCManageConsultation ucManageConsultation;

    public CtrlWindowConsultation() {
        ucManageConsultation = new UCManageConsultation(new DAOConsultation(), new DAOVeterinary(), new DAOClient(), new DAOAnimal(), new DAOProduct());
    }

    @FXML
    private void initialize() {
        InputTextMask.maskMoney(txtPrice);
        InputTextMask.maskTime(txtInitialTime);

        loadClientsInComboBox();
        loadVeterinariesInComboBox();
    }

    public void changeClient(ActionEvent actionEvent) {
        loadAnimalsInComboBox();
    }

    public void changeAnimal(ActionEvent actionEvent) {
        cbxVeterinary.setValue(getAnimalFromView().getPreferredVeterinarian());
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

    private void loadClientsInComboBox() {
        clients = FXCollections.observableArrayList(ucManageConsultation.getClientsList());
        cbxClient.setItems(clients);

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

        cbxClient.setConverter(stringClientConverter);
    }

    private void loadAnimalsInComboBox() {
        animals = FXCollections.observableArrayList(cbxClient.getValue().listActiveAnimals());
        cbxAnimal.setItems(animals);

        stringAnimalConverter = new StringConverter<Animal>() {

            @Override
            public String toString(Animal object) {
                return object.getName();
            }

            @Override
            public Animal fromString(String id) {
                return animals.stream()
                        .filter(item -> item.getId() == Integer.parseInt(id))
                        .collect(Collectors.toList()).get(0);
            }
        };

        cbxAnimal.setConverter(stringAnimalConverter);
    }

    private void loadVeterinariesInComboBox() {
        veterinaries = FXCollections.observableArrayList(ucManageConsultation.getVeterinariesList());
        cbxVeterinary.setItems(veterinaries);

        stringVeterinaryConverter = new StringConverter<Veterinary>() {

            @Override
            public String toString(Veterinary object) {
                return object.getName();
            }

            @Override
            public Veterinary fromString(String cpf) {
                return veterinaries.stream()
                        .filter(item -> item.getCpf().equals(cpf))
                        .collect(Collectors.toList()).get(0);
            }
        };

        cbxVeterinary.setConverter(stringVeterinaryConverter);
    }

    private void saveOrUpdateConsultation () {
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
            consultationToSaveOrUpdate.setTimeLapse(setTimeLapseFromView());
            consultationToSaveOrUpdate.setPrice(txtPrice.getText().trim());
            consultationToSaveOrUpdate.setAnnotations(txaAnnotations.getText().trim());
            consultationToSaveOrUpdate.setPaid(itsPaid);
        } catch (Exception e) {
            return e.getMessage();
        }
        return null;
    }

    public TimeLapse setTimeLapseFromView() {
        TimeLapse timeLapse = new TimeLapse();
        try {
            LocalDate localDate = LocalDate.from(txtDate.getValue());
            String[] startTimeParts = txtInitialTime.getText().split(":");
            LocalDateTime startDateTime = localDate.atTime(Integer.parseInt(startTimeParts[0]), Integer.parseInt(startTimeParts[1]));

            timeLapse.setStartTime(startDateTime);
            timeLapse.setEndTime(startDateTime.plusMinutes(30));
        } catch (DateTimeException e) {
            System.out.println(e.getMessage());
            throw new DateTimeException("Data ou hora inválida!");
        }

        return timeLapse;
    }

    private LocalTime getStartTimeFromView () {
        LocalTime startTime = null;
        try {
            String[] startTimeParts = txtInitialTime.getText().split(":");
            startTime = LocalTime.of(Integer.parseInt(startTimeParts[0]), Integer.parseInt(startTimeParts[1]));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return startTime;
    }

    private LocalTime getEndTimeFromView () {
        LocalTime startTime = null;
        try {
            String[] startTimeParts = txtEndTime.getText().split(":");
            startTime = LocalTime.of(Integer.parseInt(startTimeParts[0]), Integer.parseInt(startTimeParts[1]));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return startTime;
    }

    private void setEndTimeToView () {
        LocalTime startTime = getStartTimeFromView();
        LocalTime endTime = startTime.plusMinutes(30);
        txtEndTime.setText(endTime.toString());
    }

    public void setEntityToView(Consultation consultation) {
        this.consultationToSet = consultation;

        cbxClient.getSelectionModel().select(stringClientConverter.fromString(consultation.getAnimal().getOwner().getCpf()));
        cbxAnimal.getSelectionModel().select(stringAnimalConverter.fromString(consultation.getAnimal().getId() + ""));
        cbxVeterinary.getSelectionModel().select(stringVeterinaryConverter.fromString(consultation.getAnimal().getPreferredVeterinarian().getCpf()));
        txtDate.setValue(consultation.getTimeLapse().getStartTime().toLocalDate());
        txtInitialTime.setText(consultation.getTimeLapse().getStartTime().toLocalTime().toString());
        txtEndTime.setText(consultation.getTimeLapse().getEndTime().toLocalTime().toString());
        txtPrice.setText(consultation.getMaskedPrice());
        txaAnnotations.setText(consultation.getAnnotations());

        itsPaid = consultation.isPaid();

        setViewToEditMode();
    }

    private void setViewToEditMode() {
        //btnAddNewConsultant.setDisable(false);
        //tblSchedule.setDisable(false);
    }

    private boolean isUpdateRequest() {
        return consultationToSet != null;
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

    private boolean isPaymentRequest() {
        return isPaymentRequest;
    }

    private void identifyErrorsAndBuildMsg() {
        if(consultationInfoIsIncomplete() && !isPaymentRequest())
            appendErrorMessage("A data e o horário devem ser preenchidos.");
        if(consultationInfoIsIncomplete() && isPaymentRequest())
            appendErrorMessage("Data, hhorário e preço devem ser preenchidos.");
        if (!txtPrice.getText().isEmpty() && !InputValidator.isMoney(txtPrice.getText()))
            appendErrorMessage("O formato do CPF é inválido.");
        if (getClientFromView() == null)
            appendErrorMessage("Escolha um cliente.");
        if (getAnimalFromView() == null)
            appendErrorMessage("Escolha um animal.");
        if (getVeterinaryFromView() == null)
            appendErrorMessage("Escolha um veterinário.");
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

    private Client getClientFromView() {
        return (Client)cbxClient.getValue();
    }

    private Animal getAnimalFromView() {
        return (Animal) cbxAnimal.getValue();
    }

    private Animal getVeterinaryFromView() {
        return (Animal) cbxAnimal.getValue();
    }

    private List<String> getAllDataViewAsList() {
        List<String> dataList = Arrays.asList(txtInitialTime.getText(), txtDate.getValue().toString());
        return dataList;
    }

    private boolean checkDate() {
        return false;
    }

    private boolean checkInitialDate() {
        return false;
    }

    // Métodos específicos para validação de dados
    private boolean consultationInfoIsIncomplete() {
        return someStringsAreNotFilled(getAllDataViewAsList()) || !allStringsAreFilled(getAllDataViewAsList());
    }
}
