package br.edu.ifsp.doo.petshop.controller;

import br.edu.ifsp.doo.petshop.model.entities.User;
import br.edu.ifsp.doo.petshop.model.usecases.UCManageUser;
import br.edu.ifsp.doo.petshop.view.loaders.WindowSecretaryDashboard;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

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

    @FXML
    private void initialize () {
        ucManageUser = new UCManageUser();
        user = new User();
    }

    public void sendLogin(ActionEvent actionEvent) throws UnsupportedEncodingException, NoSuchAlgorithmException, SQLException {
        identifyErrorsAndBuildMsg();
        if(allViewDataIsOk()) {
            user = ucManageUser.performLogin(txtLogin.getText(), txtPassword.getText());
            if (user != null)
                WindowSecretaryDashboard.main(user);
        } else
            showErrorMessage("Erro");
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
