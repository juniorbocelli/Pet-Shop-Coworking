package br.edu.ifsp.doo.petshop.controller;

import br.edu.ifsp.doo.petshop.main.Main;
import br.edu.ifsp.doo.petshop.model.entities.*;
import br.edu.ifsp.doo.petshop.model.usecases.UCManageAnimal;
import br.edu.ifsp.doo.petshop.persistence.dao.DAOAnimal;
import br.edu.ifsp.doo.petshop.persistence.dao.DAOClient;
import br.edu.ifsp.doo.petshop.persistence.dao.DAOVeterinary;
import br.edu.ifsp.doo.petshop.view.loaders.WindowVeterinaryRecords;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CtrlWindowAnimal {
    @FXML Label lblObservations;
    @FXML TextField txtName;

    @FXML Spinner<Integer> txtBirthYear;

    @FXML CheckBox chkActive;

    @FXML RadioButton rdoMale;
    @FXML RadioButton rdoFemale;
    @FXML ToggleGroup genderGroup = new ToggleGroup();

    @FXML RadioButton rdoDog;
    @FXML RadioButton rdoCat;
    @FXML ToggleGroup typeGroup = new ToggleGroup();

    @FXML ComboBox cbxVeterinary;
    private StringConverter<Veterinary> stringVeterinaryConverter;

    @FXML ComboBox cbxOwner;
    private StringConverter<Client> stringClientConverter;

    @FXML CheckBox chkCardiopath;
    @FXML CheckBox chkRenal;
    @FXML CheckBox chkPulmonary;
    @FXML CheckBox chkAllergic;

    @FXML Button btnVeterinaryRecord;
    @FXML Button btnSaveAnimal;
    @FXML Button btnCloseAnimal;

    @FXML TextArea txaGeneralAnnotations;

    private String errorMessage;

    private UCManageAnimal ucManageAnimal;

    private Animal animalToSet;
    private Animal animalToSaveOrUpdate;

    private Client owner;

    private ObservableList<Client> clients;
    private ObservableList<Veterinary> veterinaries;

    public CtrlWindowAnimal() {
        ucManageAnimal = new UCManageAnimal(new DAOAnimal(), new DAOClient(), new DAOVeterinary());
    }

    @FXML
    private void initialize() {
        groupTypeRadioButton();
        groupGenderRadioButton();

        loadClientsInComboBox();
        loadVeterinariesInComboBox();

        if (isRequestByVeterinary())
            setViewVeterinaryMode();
    }

    public void setOwnerToView(Client client) {
        owner = client;

        cbxOwner.getSelectionModel().select(client);
        cbxOwner.setDisable(true);
    }

    private void groupTypeRadioButton() {
        rdoDog.setToggleGroup(typeGroup);
        rdoCat.setToggleGroup(typeGroup);
    }

    private String getSelectedAnimalType() {
        RadioButton selectedRadioButton = (RadioButton) typeGroup.getSelectedToggle();
        if (selectedRadioButton == null) return null;
        return selectedRadioButton.getText();
    }

    private void groupGenderRadioButton() {
        rdoMale.setToggleGroup(genderGroup);
        rdoFemale.setToggleGroup(genderGroup);
    }

    private String getSelectedAnimalGender() {
        RadioButton selectedRadioButton = (RadioButton) genderGroup.getSelectedToggle();
        if (selectedRadioButton == null) return null;
        return selectedRadioButton.getText();
    }

    private void loadClientsInComboBox() {
        clients = FXCollections.observableArrayList(ucManageAnimal.getClientsList());
        cbxOwner.setItems(clients);
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

    private void loadVeterinariesInComboBox() {
        veterinaries = FXCollections.observableArrayList(ucManageAnimal.getVeterinariesList());
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

    public void openVeterinaryRecord(ActionEvent actionEvent) {
        WindowVeterinaryRecords windowVeterinaryRecords = new WindowVeterinaryRecords();
        windowVeterinaryRecords.startModal(animalToSet);
    }

    public void sendViewAnimal(ActionEvent actionEvent) {
        identifyErrorsAndBuildMsg();
        if (allViewDataIsOk())
            saveOrUpdateAnimal();
        else
            showErrorMessage("Erro");
    }

    private boolean isUpdateRequest() {
        return animalToSet != null;
    }

    private boolean isRequestByVeterinary() {
        return Main.getInstance().getLoggedUser() instanceof Veterinary;
    }

    private void setViewVeterinaryMode() {
        txaGeneralAnnotations.setDisable(false);
        lblObservations.setDisable(false);
    }

    public void closeAnimal(ActionEvent actionEvent) {
        closeStage();
    }

    private void closeStage() {
        Stage stage = (Stage) btnCloseAnimal.getScene().getWindow();
        stage.close();
    }

    private void saveOrUpdateAnimal () {
        errorMessage = getEntityFromView();
        if (!allViewDataIsOk())
            showErrorMessage("Erro");
        else
            requestSaveOrUpdate();
    }

    private void requestSaveOrUpdate () {
        if (isUpdateRequest())
            ucManageAnimal.saveOrUpdate(animalToSaveOrUpdate);
        else
            animalToSaveOrUpdate.setId(ucManageAnimal.saveOrUpdateWithReturnId(animalToSaveOrUpdate));
        ucManageAnimal.updateDiseaseList(animalToSaveOrUpdate);
        closeStage();
    }

    private String getEntityFromView () {
        animalToSaveOrUpdate = new Animal();
        try {
            animalToSaveOrUpdate.setOwner(getClientFromView());
            animalToSaveOrUpdate.setPreferredVeterinarian(getPreferredVeterinaryFromView());
            animalToSaveOrUpdate.setName(txtName.getText().trim());
            animalToSaveOrUpdate.setAnimalType(getSelectedAnimalType());
            animalToSaveOrUpdate.setGender(getSelectedAnimalGender());
            animalToSaveOrUpdate.setBirthYear(txtBirthYear.getValue());
            animalToSaveOrUpdate.setVeterinaryRecord(new VeterinaryRecord(txaGeneralAnnotations.getText()));
            animalToSaveOrUpdate.setActive(chkActive.isSelected());

            getDiseasesFromView(animalToSaveOrUpdate);

            if(isUpdateRequest())
                animalToSaveOrUpdate.setId(animalToSet.getId());
            else animalToSaveOrUpdate.setId(-1);
        } catch (Exception e) {
            return e.getMessage();
        }
        return null;
    }

    private void getDiseasesFromView(Animal animal) {
        animal.clearDisease();
        if (chkAllergic.isSelected())
            animal.addDisease(Animal.Diseases.ALÉRGICO);
        if (chkCardiopath.isSelected())
            animal.addDisease(Animal.Diseases.CARDIOPATA);
        if (chkPulmonary.isSelected())
            animal.addDisease(Animal.Diseases.PULMONAR);
        if (chkRenal.isSelected())
            animal.addDisease(Animal.Diseases.RENAL);
    }

    public void setEntityToView(Animal animal) {
        this.animalToSet = animal;

        cbxOwner.getSelectionModel().select(stringClientConverter.fromString(animal.getOwner().getCpf()));
        cbxVeterinary.getSelectionModel().select(stringVeterinaryConverter.fromString(animal.getPreferredVeterinarian().getCpf()));
        txtName.setText(animal.getName());
        if (animal.getAnimalType().toString() == "CACHORRO") rdoDog.setSelected(true);
        else rdoCat.setSelected(true);
        if (animal.getGender().toString() == "MACHO") rdoMale.setSelected(true);
        else rdoFemale.setSelected(true);
        txtBirthYear.getValueFactory().setValue(animal.getBirthYear());
        txaGeneralAnnotations.setText(animal.getVeterinaryRecord().getGeneralAnnotations());
        chkActive.setSelected(animal.isActive());

        loadDiseasesFromDatabase(animal);
        setDiseasesToView(animal);

        setViewToEditMode();
    }

    public void setDiseasesToView(Animal animal) {
        List<Animal.Diseases> diseasesList = animal.getDiseases();
        diseasesList.forEach((d)->{
            switch (d) {
                case RENAL:
                    chkRenal.setSelected(true);
                    break;

                case ALÉRGICO:
                    chkAllergic.setSelected(true);
                    break;

                case PULMONAR:
                    chkPulmonary.setSelected(true);
                    break;

                case CARDIOPATA:
                    chkCardiopath.setSelected(true);
                    break;
            }
        });
    }

    public void loadDiseasesFromDatabase(Animal animal) {
        animal.setDiseasesFromString(ucManageAnimal.selectAnimalDiseases(animal));
    }

    private void setViewToEditMode() {
        btnVeterinaryRecord.setDisable(false);
    }

    private Client getClientFromView () {
        return (Client)cbxOwner.getValue();
    }

    private Veterinary getPreferredVeterinaryFromView() {
        return (Veterinary)cbxVeterinary.getValue();
    }

    private void showErrorMessage(String title) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(errorMessage);
        alert.setContentText(null);
        alert.showAndWait();
        errorMessage = null;
    }

    private void identifyErrorsAndBuildMsg() {
        if(animalInfoIsIncomplete())
            appendErrorMessage("Todos os dados devem ser preenchidos.");
        if (getClientFromView() == null)
            appendErrorMessage("Escolha um Cliente para o Animal.");
        if (getPreferredVeterinaryFromView() == null)
            appendErrorMessage("Escolha um Veterinário Preferencial para o animal.");
        if (getSelectedAnimalType() == null)
            appendErrorMessage("Escolha o tipo do Animal.");
        if (getSelectedAnimalGender() == null)
            appendErrorMessage("Escolha o sexo do Animal.");
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
        List<String> dataList = Arrays.asList(txtName.getText(), txtBirthYear.getValue() == null ? "":txtBirthYear.getValue().toString());
        return dataList;
    }

    // Métodos específicos para validação de dados
    private boolean animalInfoIsIncomplete() {
        return someStringsAreNotFilled(getAllDataViewAsList()) || !allStringsAreFilled(getAllDataViewAsList());
    }
}
