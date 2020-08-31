package br.edu.ifsp.doo.petshop.controller;

import br.edu.ifsp.doo.petshop.main.Main;
import br.edu.ifsp.doo.petshop.model.entities.Secretary;
import br.edu.ifsp.doo.petshop.model.entities.Veterinary;
import br.edu.ifsp.doo.petshop.model.usecases.UCManageSecretary;
import br.edu.ifsp.doo.petshop.persistence.dao.DAOSecretary;
import br.edu.ifsp.doo.petshop.persistence.dao.DAOVeterinary;
import br.edu.ifsp.doo.petshop.view.loaders.*;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;
import javafx.util.StringConverter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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

    @FXML TableView tblSchedule;

    @FXML TableColumn clnStartTime;
    @FXML TableColumn clnEndTime;
    @FXML TableColumn clnClient;
    @FXML TableColumn clnAnimal;
    @FXML TableColumn clnStatus;

    private Secretary secretary;
    private UCManageSecretary ucManageSecretary;

    private ObservableList<Veterinary> veterinaries;

    public CtrlWindowSecretaryDashboard() {
        secretary = (Secretary)Main.getInstance().getLoggedUser();
        ucManageSecretary = new UCManageSecretary(new DAOSecretary(), new DAOVeterinary());
    }

    @FXML
    private void initialize() {
        lblUserName.setText(secretary.getName());
        goToToday();

        initializeClock();
        initializeDate();

        loadVeterinariesInComboBox();
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
    }

    public void goToToday(ActionEvent actionEvent) {
        goToToday();
    }

    public void goToToday() {
        LocalDate currentDate = LocalDate.now();
        txtDate.setValue(currentDate);
    }

    public void goToVeterinary(ActionEvent actionEvent) {
    }

    private void loadVeterinariesInComboBox() {
        veterinaries = FXCollections.observableArrayList(ucManageSecretary.getVeterinariesList());
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
}
