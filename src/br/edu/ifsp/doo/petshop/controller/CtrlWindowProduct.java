package br.edu.ifsp.doo.petshop.controller;

import br.edu.ifsp.doo.petshop.model.entities.Product;
import br.edu.ifsp.doo.petshop.model.entities.Veterinary;
import br.edu.ifsp.doo.petshop.model.usecases.UCManageProduct;
import br.edu.ifsp.doo.petshop.model.usecases.UCManageVeterinary;
import br.edu.ifsp.doo.petshop.persistence.dao.DAOProduct;
import br.edu.ifsp.doo.petshop.persistence.dao.DAOVeterinary;
import br.edu.ifsp.doo.petshop.view.loaders.WindowVeterinaryConsultation;
import br.edu.ifsp.doo.petshop.view.util.InputTextMask;
import br.edu.ifsp.doo.petshop.view.util.InputValidator;
import br.edu.ifsp.doo.petshop.view.util.TextFieldFormater;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.util.Arrays;
import java.util.List;

public class CtrlWindowProduct {
    @FXML TextField txtName;
    @FXML TextField txtPrice;

    @FXML CheckBox chkActive;

    @FXML Button btnSaveProduct;
    @FXML Button btnCloseProduct;

    private Product product;
    private UCManageProduct ucManageProduct;
    private String errorMessage;

    public CtrlWindowProduct() {
        ucManageProduct = new UCManageProduct(new DAOProduct());
    }

    @FXML
    private void initialize() {
        InputTextMask.maskReal(txtPrice);
        //configureTextFields();
    }

    public void sendViewProduct(ActionEvent actionEvent) {
        identifyErrorsAndBuildMsg();
        if (allViewDataIsOk())
            saveOrUpdateVeterinary ();
        else
            showErrorMessage("Erro");
    }

    public void closeProduct(ActionEvent actionEvent) {
        closeStage();
    }

    private void closeStage() {
        Stage stage = (Stage) btnCloseProduct.getScene().getWindow();
        stage.close();
    }

    private void instanceEntityIfNull() {
        if (product == null)
            product = new Product();
    }

    private void saveOrUpdateVeterinary () {
        errorMessage = getEntityFromView();
        if (!allViewDataIsOk())
            showErrorMessage("Erro");
        else
            requestSaveOrUpdate();
    }

    private void requestSaveOrUpdate () {
        ucManageProduct.saveOrUpdate(product);
        closeStage();
    }

    private void configureTextFields() {
        //TextFieldFormater.formatAsPhoneNumber(txtCell);
    }

    private String getEntityFromView () {
        instanceEntityIfNull();
        try {
            product.setName(txtName.getText().trim());
            product.setName(txtPrice.getText().trim());
            product.setActive(chkActive.isSelected());
        } catch (Exception e) {
            return e.getMessage();
        }
        return null;
    }

    public void setEntityToView(Product product) {
        this.product = product;

        txtName.setText(product.getName());
        txtPrice.setText(product.getPrice().toString());
        chkActive.setSelected(product.isActive());
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
        if(productInfoIsIncomplete())
            appendErrorMessage("Todos os dados devem ser preenchidos.");
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
        List<String> dataList = Arrays.asList(txtName.getText(), txtPrice.getText());
        return dataList;
    }

    // Métodos específicos para validação de dados
    private boolean productInfoIsIncomplete() {
        return someStringsAreNotFilled(getAllDataViewAsList()) || !allStringsAreFilled(getAllDataViewAsList());
    }
}
