package br.edu.ifsp.doo.petshop.controller;

import br.edu.ifsp.doo.petshop.main.Main;
import br.edu.ifsp.doo.petshop.model.entities.User;
import br.edu.ifsp.doo.petshop.model.usecases.UCManageUser;
import br.edu.ifsp.doo.petshop.view.util.InputTextMask;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

public class CtrlWindowLogin {
    @FXML TextField txtLogin;
    @FXML TextField txtPassword;

    @FXML Button btnSendLogin;

    private String errorMessage;

    private UCManageUser ucManageUser;
    private User user;

    public CtrlWindowLogin() {
        ucManageUser = new UCManageUser();
    }

    @FXML
    private void initialize () {
        InputTextMask.maskCPF(txtLogin);
    }

    @FXML
    public void close(){
        Stage stage = (Stage)btnSendLogin.getScene().getWindow();
        stage.close();
    }

    public void sendLogin(ActionEvent actionEvent) throws UnsupportedEncodingException, NoSuchAlgorithmException, SQLException {
        identifyErrorsAndBuildMsg();
        if(allViewDataIsOk()) {
            user = ucManageUser.performLogin(txtLogin.getText(), txtPassword.getText());
            if (user != null){
                String[] userData = {txtLogin.getText(), txtPassword.getText(), Boolean.toString(user.isSuperUser())};
                //sendUser();
                Main.getInstance().userLogging(user);
            } else {
                errorMessage = null;
                appendErrorMessage("Login inválido!");
                showErrorMessage("Erro");
            }
        } else
            showErrorMessage("Erro");
    }

    public User sendUser() {
        return this.user;
    }

    // Métodos gerais para validação
    private void showErrorMessage(String title) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(errorMessage);
        alert.setContentText(null);
        alert.showAndWait();
        errorMessage = null;
    }

    private void identifyErrorsAndBuildMsg() {
        if(loginInfoIsIncomplete())
            appendErrorMessage("Todos os dados devem ser preenchidos!");
    }

    private void appendErrorMessage(String message) {
        if (errorMessage == null)
            errorMessage = " Erro(s) na solicitação: \n";
        errorMessage += "\n - ".concat(message);
    }

    private boolean allViewDataIsOk() {
        return errorMessage == null;
    }

    // Métodos específicos para validação de dados
    private boolean loginInfoIsIncomplete() {
        return txtLogin.getText().isEmpty() || txtPassword.getText().isEmpty();
    }
}
