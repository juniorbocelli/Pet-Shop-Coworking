package br.edu.ifsp.doo.petshop.model.usecases;

import br.edu.ifsp.doo.petshop.model.entities.User;
import br.edu.ifsp.doo.petshop.persistence.dao.DAOUser;
import br.edu.ifsp.doo.petshop.model.utils.EncryptString;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

public class UCManageUser {
    public User performLogin (String login, String password) throws UnsupportedEncodingException, NoSuchAlgorithmException, SQLException {
        DAOUser daoUser = new DAOUser();
        return daoUser.returnUserIfExist(login, EncryptString.encryptPassword(password));
    }
}
