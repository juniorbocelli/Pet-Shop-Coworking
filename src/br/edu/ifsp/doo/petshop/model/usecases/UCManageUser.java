package br.edu.ifsp.doo.petshop.model.usecases;

import br.edu.ifsp.doo.petshop.model.entities.User;
import br.edu.ifsp.doo.petshop.persistence.dao.DAOUser;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

public class UCManageUser {
    public User performLogin (String login, String password) throws UnsupportedEncodingException, NoSuchAlgorithmException, SQLException {
        DAOUser daoUser = new DAOUser();
        return daoUser.returnUserIfExist(login, encryptPassword(password));
    }

    private String encryptPassword(String password) throws NoSuchAlgorithmException,
            UnsupportedEncodingException {

        if (password.isEmpty()) return null;

        MessageDigest algorithm = MessageDigest.getInstance("SHA-256");
        byte messageDigest[] = algorithm.digest(password.getBytes("UTF-8"));

        StringBuilder hexString = new StringBuilder();
        for (byte b : messageDigest) {
            hexString.append(String.format("%02X", 0xFF & b));
        }
        return hexString.toString();
    }
}
