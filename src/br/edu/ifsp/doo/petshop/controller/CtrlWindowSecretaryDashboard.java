package br.edu.ifsp.doo.petshop.controller;

import br.edu.ifsp.doo.petshop.main.Main;
import br.edu.ifsp.doo.petshop.model.entities.Consultation;
import br.edu.ifsp.doo.petshop.model.entities.Secretary;
import br.edu.ifsp.doo.petshop.model.entities.Veterinary;
import br.edu.ifsp.doo.petshop.model.usecases.UCManageSecretary;
import br.edu.ifsp.doo.petshop.persistence.dao.DAOSecretary;
import br.edu.ifsp.doo.petshop.persistence.dao.DAOVeterinary;
import br.edu.ifsp.doo.petshop.persistence.dao.DAOVeterinaryRecord;
import br.edu.ifsp.doo.petshop.view.loaders.*;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;
import javafx.util.StringConverter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

public class CtrlWindowSecretaryDashboard {
    @FXML DatePicker txtDate;

    @FXML Button btnToday;
    @FXML Button btnManageVets;
    @FXML Button btnManageClients;
    @FXML Button btnManageAnimals;
    @FXML Button btnManageProducts;
    @FXML Button btnBilling;

    @FXML ComboBox cbxVeterinary;
    private StringConverter<Veterinary> stringVeterinaryConverter;

    @FXML Label lblUserName;
    @FXML Label lblCurrentDate;
    @FXML Label lblCurrentTime;

    @FXML TableView<Consultation> tblSchedule;

    @FXML TableColumn<Consultation, String> clnStartTime;
    @FXML TableColumn<Consultation, String> clnEndTime;
    @FXML TableColumn<Consultation, String> clnClient;
    @FXML TableColumn<Consultation, String> clnAnimal;
    @FXML TableColumn<Consultation, String> clnStatus;

    private Secretary secretary;
    private UCManageSecretary ucManageSecretary;

    private ObservableList<Veterinary> veterinaries;

    private ObservableList<Consultation> tableData;
    private List<Consultation> allConsultations;

    public CtrlWindowSecretaryDashboard() {
        secretary = (Secretary)Main.getInstance().getLoggedUser();
        ucManageSecretary = new UCManageSecretary(new DAOSecretary(), new DAOVeterinary(), new DAOVeterinaryRecord());
    }

    @FXML
    private void initialize() {
        lblUserName.setText(secretary.getName());
        goToToday();

        initializeClock();
        initializeDate();

        bindTableViewToItemsList();
        bindColumnsToValueSources();

        loadVeterinariesInComboBox();

        loadDataAndShow();
    }

    private void initializeClock() {
        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            LocalTime currentTime = LocalTime.now();
            lblCurrentTime.setText(currentTime.getHour() + ":" + currentTime.getMinute() + ":" +
                    (currentTime.getSecond() > 10 ? currentTime.getSecond():"0" + currentTime.getSecond()));
        }),
                new KeyFrame(Duration.seconds(1))
        );
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();
    }

    private void initializeDate() {
        LocalDate localDate = LocalDate.now();
        String[] localDateParts = localDate.toString().split("-");
        lblCurrentDate.setText(localDateParts[2] + "/" + localDateParts[1] + "/" + localDateParts[0]);
    }

    public void showClientsList(ActionEvent actionEvent) {
        WindowClientManager windowClientManager = new WindowClientManager();
        windowClientManager.startModal();
    }

    public void showVetsList(ActionEvent actionEvent) {
        WindowVeterinaryManager windowVeterinaryManager = new WindowVeterinaryManager();
        windowVeterinaryManager.startModal();
    }

    public void showProductsList(ActionEvent actionEvent) {
        WindowProductManager windowProductManager = new WindowProductManager();
        windowProductManager.startModal();
    }

    public void showAnimalsList(ActionEvent actionEvent) {
        WindowAnimalManager windowAnimalManager = new WindowAnimalManager();
        windowAnimalManager.startModal();
    }

    public void showBilling(ActionEvent actionEvent) {
        WindowBilling windowBilling = new WindowBilling();
        windowBilling.startModal();
    }

    public void showUserData(MouseEvent mouseEvent) {
        WindowSecretary windowSecretary = new WindowSecretary();
        windowSecretary.startModal(secretary, secretary.getName());
    }

    public void changeDate(ActionEvent actionEvent) {
        loadDataAndShow();
    }

    public void goToToday(ActionEvent actionEvent) {
        goToToday();
        loadDataAndShow();
    }

    public void goToToday() {
        LocalDate currentDate = LocalDate.now();
        txtDate.setValue(currentDate);
    }

    public void goToVeterinary(ActionEvent actionEvent) {
        loadDataAndShow();
    }

    private Veterinary getVeterinaryFromView() {
        return (Veterinary) cbxVeterinary.getValue();
    }

    private LocalDate getDateFromView() {
        return txtDate.getValue();
    }

    private void loadVeterinariesInComboBox() {
        veterinaries = FXCollections.observableArrayList(ucManageSecretary.getVeterinariesList());
        veterinaries.add(0, null);
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

    public void manageConsultations(MouseEvent mouseEvent) {
        Consultation selectedConsultation = tblSchedule.getSelectionModel().getSelectedItem();
        if (mouseEvent.getClickCount() == 2) {
            if (selectedConsultation != null) {
                WindowConsultation windowConsultation = new WindowConsultation();
                windowConsultation.startModal(selectedConsultation);
            } else {
                WindowConsultation windowConsultation = new WindowConsultation();
                windowConsultation.startModal();
            }

            loadDataAndShow();
        }
    }

    public void bindTableViewToItemsList() {
        tableData = FXCollections.observableArrayList();
        tblSchedule.setItems(tableData);
    }

    public void bindColumnsToValueSources() {
        clnStartTime.setCellValueFactory((param) -> new SimpleStringProperty(param.getValue().getTimeLapse().getStartTime().toString().substring(11, 16)));
        clnEndTime.setCellValueFactory((param) -> new SimpleStringProperty(param.getValue().getTimeLapse().getEndTime().toString().substring(11, 16)));
        clnAnimal.setCellValueFactory((param) -> new SimpleStringProperty(param.getValue().getAnimal().getName()));
        clnStatus.setCellValueFactory((param) -> new SimpleStringProperty(param.getValue().isPaid() ? "Pago":"Não Pago"));
    }

    private void loadDataAndShow() {
        loadTableDataFromDatabase();
    }

    private void loadTableDataFromDatabase() {
        allConsultations = ucManageSecretary.selectConsultationsList(getVeterinaryFromView(), getDateFromView());
        tableData.setAll(allConsultations);
    }
}
