package br.edu.ifsp.doo.petshop.controller;

import br.edu.ifsp.doo.petshop.main.Main;
import br.edu.ifsp.doo.petshop.model.entities.Consultation;
import br.edu.ifsp.doo.petshop.model.entities.Veterinary;
import br.edu.ifsp.doo.petshop.model.usecases.UCManageVeterinary;
import br.edu.ifsp.doo.petshop.persistence.dao.DAOVeterinary;
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

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class CtrlWindowVeterinaryDashboard {
    @FXML DatePicker txtDate;

    @FXML Button btnToday;
    @FXML Button btnManageVeterinaryRecords;
    @FXML Button btnManageVeterinaryConsultations;
    @FXML Button btnManageAnimals;
    @FXML Button btnBilling;

    @FXML Label lblCurrentTime;
    @FXML Label lblCurrentDate;
    @FXML Label lblUserName;

    @FXML TableView<Consultation> tblSchedule;

    @FXML TableColumn<Consultation, String> clnStartTime;
    @FXML TableColumn<Consultation, String> clnEndTime;
    @FXML TableColumn<Consultation, String> clnClient;
    @FXML TableColumn<Consultation, String> clnAnimal;
    @FXML TableColumn<Consultation, String> clnStatus;

    private Veterinary veterinary;

    private ObservableList<Consultation> tableData;
    private List<Consultation> allConsultations;

    private UCManageVeterinary ucManageVeterinary;

    public CtrlWindowVeterinaryDashboard() {
        veterinary = (Veterinary) Main.getInstance().getLoggedUser();

        ucManageVeterinary = new UCManageVeterinary(new DAOVeterinary());
    }

    @FXML
    private void initialize() {
        txtDate.setValue(LocalDate.now());
        lblUserName.setText(veterinary.getName());

        bindTableViewToItemsList();
        bindColumnsToValueSources();

        initializeClock();
        initializeDate();
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

    public void showUserData(MouseEvent mouseEvent) {
        WindowVeterinary windowVeterinary = new WindowVeterinary();
        windowVeterinary.startModal(veterinary, veterinary.getName());
    }

    public void changeDate(ActionEvent actionEvent) {
        loadDataAndShow();
    }

    public void goToToday(ActionEvent actionEvent) {
        txtDate.setValue(LocalDate.now());

        loadDataAndShow();
    }

    public void manageVeterinaryConsultations(ActionEvent actionEvent) {
        WindowVeterinaryRecords windowVeterinaryRecords = new WindowVeterinaryRecords();
        windowVeterinaryRecords.startModal("Título do Prontuário");
    }

    public void manageVeterinaryRecords(ActionEvent actionEvent) {
        WindowVeterinaryRecords windowVeterinaryRecords = new WindowVeterinaryRecords();
        windowVeterinaryRecords.startModal("Prontuários");
    }

    public void showBilling(ActionEvent actionEvent) {
        WindowBilling windowBilling = new WindowBilling();
        windowBilling.startModal();
    }

    public void manageAnimals(ActionEvent actionEvent) {
        WindowAnimalManager windowAnimalManager = new WindowAnimalManager();
        windowAnimalManager.startModal();
    }

    private LocalDate getDateFromView() {
        return txtDate.getValue();
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
        allConsultations = ucManageVeterinary.selectConsultationsList(veterinary, getDateFromView());
        tableData.setAll(allConsultations);
    }
}
