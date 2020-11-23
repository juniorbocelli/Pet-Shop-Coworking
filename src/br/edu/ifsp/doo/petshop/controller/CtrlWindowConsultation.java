package br.edu.ifsp.doo.petshop.controller;

import br.edu.ifsp.doo.petshop.main.Main;
import br.edu.ifsp.doo.petshop.model.entities.*;
import br.edu.ifsp.doo.petshop.model.usecases.UCManageConsultation;
import br.edu.ifsp.doo.petshop.persistence.dao.*;
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
import javafx.util.StringConverter;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class CtrlWindowConsultation {
    @FXML ComboBox<Client> cbxClient;
    private StringConverter<Client> stringClientConverter;

    @FXML ComboBox<Animal> cbxAnimal;
    private StringConverter<Animal> stringAnimalConverter;

    @FXML ComboBox<Veterinary> cbxVeterinary;
    private StringConverter<Veterinary> stringVeterinaryConverter;

    @FXML ComboBox<Product> cbxProducts;
    private StringConverter<Product> stringProductConverter;

    @FXML DatePicker txtDate;

    @FXML TextField txtInitialTime;
    @FXML TextField txtEndTime;
    @FXML TextField txtPrice;

    @FXML Button btnFinalizeConsultation;
    @FXML Button btnAddProduct;
    @FXML Button btnPaidConsultation;
    @FXML Button btnSaveVeterinaryConsultant;
    @FXML Button btnCloseVeterinaryConsultant;

    @FXML TextArea txaAnnotations;

    @FXML TableView<Product> tblProducts;

    @FXML TableColumn<Product, String> clnName;
    @FXML TableColumn<Product, String> clnPrice;

    private String errorMessage;

    private Consultation consultationToSet;
    private Consultation consultationToSaveOrUpdate;

    private ObservableList<Client> clients;
    private ObservableList<Animal> animals;
    private ObservableList<Veterinary> veterinaries;
    private ObservableList<Product> products;

    private ObservableList<Product> tableData;
    private List<Product> allProducts;

    private boolean itsPaid;
    private boolean isPaymentRequest = false;

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
        loadProductsInComboBox();

        initializeEventFocusOutInEndTime();

        bindTableViewToItemsList();
        bindColumnsToValueSources();
        loadDataAndShow();

        if (isLoggedUserSecretary())
            setViewToSecretaryMode();
        else
            setViewToVeterinaryMode();
    }

    public void bindTableViewToItemsList() {
        tableData = FXCollections.observableArrayList();
        tblProducts.setItems(tableData);
    }

    public void bindColumnsToValueSources() {
        clnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        clnPrice.setCellValueFactory((param) -> new SimpleStringProperty(param.getValue().getMaskedPrice()));
    }

    private void loadDataAndShow() {
        if (allProducts == null)
            allProducts = new ArrayList<>();
        tableData.setAll(allProducts);
    }

    private void initializeEventFocusOutInEndTime() {
        txtInitialTime.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal) {
                setEndTime();
            }
        });
    }

    public void changeClient(ActionEvent actionEvent) {
        loadAnimalsInComboBox();
    }

    public void changeAnimal(ActionEvent actionEvent) {
        cbxVeterinary.setValue(getAnimalFromView().getPreferredVeterinarian());
    }

    public void changeVeterinary(ActionEvent actionEvent) {
    }

    public void paidConsultation(ActionEvent actionEvent) {
        itsPaid = true;
        identifyErrorsAndBuildMsg();
        if (allViewDataIsOk())
            saveOrUpdateConsultation();
        else
            showErrorMessage("Erro");
    }

    public void addProduct(ActionEvent actionEvent) {
        Product product = getProductFromView();
        if (product != null)
            allProducts.add(product);
        loadDataAndShow();
    }

    public void finalizeConsultation(ActionEvent actionEvent) {
        isPaymentRequest = true;
        txtEndTime.setText(LocalTime.now().toString().substring(0, 5));
    }

    public void  setEndTime() {
        setEndTimeToView();
    }

    public void sendViewConsultation(ActionEvent actionEvent) {
        identifyErrorsAndBuildMsg();
        if (allViewDataIsOk())
            saveOrUpdateConsultation();
        else
            showErrorMessage("Erro");
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

    private void loadProductsInComboBox() {
        products = FXCollections.observableArrayList(ucManageConsultation.getProductsList());
        cbxProducts.setItems(products);

        stringProductConverter = new StringConverter<Product>() {

            @Override
            public String toString(Product object) {
                return object.getName();
            }

            @Override
            public Product fromString(String id) {
                return products.stream()
                        .filter(item -> item.getId() == Integer.parseInt(id))
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
        if (isUpdateRequest())
            ucManageConsultation.saveOrUpdate(consultationToSaveOrUpdate);
        else
            consultationToSaveOrUpdate.setId(ucManageConsultation.saveOrUpdateWithReturnId(consultationToSaveOrUpdate));
        ucManageConsultation.updateProductsList(consultationToSaveOrUpdate);
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

            consultationToSaveOrUpdate.setProducts(allProducts);

            if(isUpdateRequest())
                consultationToSaveOrUpdate.setId(consultationToSet.getId());
            else consultationToSaveOrUpdate.setId(-1);
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
            System.out.println("Data inválida!");
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
        txtEndTime.setText(endTime.toString().substring(0, 5));
    }

    public void setEntityToView(Consultation consultation) {
        this.consultationToSet = consultation;

        cbxClient.getSelectionModel().select(stringClientConverter.fromString(consultation.getAnimal().getOwner().getCpf()));
        loadAnimalsInComboBox();
        cbxAnimal.getSelectionModel().select(stringAnimalConverter.fromString(consultation.getAnimal().getId() + ""));
        cbxVeterinary.getSelectionModel().select(stringVeterinaryConverter.fromString(consultation.getVeterinary().getCpf()));
        txtDate.setValue(consultation.getTimeLapse().getStartTime().toLocalDate());
        txtInitialTime.setText(consultation.getTimeLapse().getStartTime().toLocalTime().toString());
        txtEndTime.setText(consultation.getTimeLapse().getEndTime().toLocalTime().toString());
        txtPrice.setText(consultation.getMaskedPrice());
        txaAnnotations.setText(consultation.getAnnotations());

        itsPaid = consultation.isPaid();
        if (itsPaid)
            setViewToPaidMode();

        consultationToSet.setProducts(ucManageConsultation.selectConsultationProducts(consultation));

        allProducts = consultationToSet.getProducts();
        loadDataAndShow();
        setViewToEditMode();
    }

    public void setAnimalToView(Animal animal) {

        cbxClient.getSelectionModel().select(stringClientConverter.fromString(animal.getOwner().getCpf()));
        loadAnimalsInComboBox();
        cbxAnimal.getSelectionModel().select(stringAnimalConverter.fromString(animal.getId() + ""));
        cbxVeterinary.getSelectionModel().select(stringVeterinaryConverter.fromString(animal.getPreferredVeterinarian().getCpf()));
    }

    private void setViewToEditMode() {

    }

    private void setViewToPaidMode() {
        txtPrice.setDisable(true);
        btnPaidConsultation.setDisable(true);
    }

    public User getLoggedUser() {
        return Main.getInstance().getLoggedUser();
    }

    public boolean isLoggedUserSecretary() {
        return getLoggedUser() instanceof Secretary;
    }

    private void setViewToSecretaryMode() {
        btnAddProduct.setDisable(true);
        txaAnnotations.setDisable(true);
        cbxProducts.setDisable(true);
        btnFinalizeConsultation.setDisable(true);
        txtPrice.setDisable(true);
    }

    private void setViewToVeterinaryMode() {
        cbxClient.setDisable(true);
        cbxAnimal.setDisable(true);
        cbxVeterinary.setDisable(true);
        txtDate.setDisable(true);
        txtInitialTime.setDisable(true);
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

    private boolean showConfirmationMessage(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK)
            return true;
        return false;
    }

    private boolean isPaymentRequest() {
        return isPaymentRequest;
    }

    private void identifyErrorsAndBuildMsg() {
        if(consultationInfoIsIncomplete() && !isPaymentRequest())
            appendErrorMessage("A data e o horário devem ser preenchidos.");
        if(consultationInfoIsIncomplete() && isPaymentRequest())
            appendErrorMessage("Data, horário e preço devem ser preenchidos.");
        if (!txtPrice.getText().isEmpty() && !InputValidator.isMoney(txtPrice.getText()))
            appendErrorMessage("O formato do preço é inválido.");
        if (getClientFromView() == null)
            appendErrorMessage("Escolha um cliente.");
        if (getAnimalFromView() == null)
            appendErrorMessage("Escolha um animal.");
        if (getVeterinaryFromView() == null)
            appendErrorMessage("Escolha um veterinário.");
        if (!checkInitialDate())
            appendErrorMessage("A data selecionada é inferior a data de hoje.");
        if (!checkTime())
            appendErrorMessage("O horário de funcionamento do escritório é 8:00 às 18:00.");
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
        return (Animal)cbxAnimal.getValue();
    }

    private Veterinary getVeterinaryFromView() {
        return (Veterinary) cbxVeterinary.getValue();
    }

    private Product getProductFromView() {
        return (Product) cbxProducts.getValue();
    }

    private List<String> getAllDataViewAsList() {
        List<String> dataList = Arrays.asList(txtInitialTime.getText(), txtDate.getValue()!=null?txtDate.getValue().toString():"");
        return dataList;
    }

    private boolean checkTime() {
        if (getStartTimeFromView() != null)
            return getStartTimeFromView().isBefore(LocalTime.of(17, 31)) && getStartTimeFromView().isAfter(LocalTime.of(7, 59));
        return false;
    }

    private boolean checkInitialDate() {
        if (txtDate.getValue() != null)
            return LocalDate.from(txtDate.getValue()).isAfter(LocalDate.now());
        return false;
    }

    // Métodos específicos para validação de dados
    private boolean consultationInfoIsIncomplete() {
        return someStringsAreNotFilled(getAllDataViewAsList()) || !allStringsAreFilled(getAllDataViewAsList());
    }

    public void removeProduct(MouseEvent mouseEvent) {
        Product selectedProduct = tblProducts.getSelectionModel().getSelectedItem();
        if (mouseEvent.getClickCount() == 2 && selectedProduct != null) {
            int selectedProductIndex = tblProducts.getSelectionModel().getSelectedIndex();
            Boolean confirmExclusion = showConfirmationMessage("Excluir Produto", "Deseja excluir o produto?");

            if (confirmExclusion)
                allProducts.remove(selectedProductIndex);
            loadDataAndShow();
        }
    }
}
