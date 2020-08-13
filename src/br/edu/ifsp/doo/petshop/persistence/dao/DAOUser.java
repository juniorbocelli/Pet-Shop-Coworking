package br.edu.ifsp.doo.petshop.persistence.dao;

import br.edu.ifsp.doo.petshop.model.entities.Secretary;
import br.edu.ifsp.doo.petshop.model.entities.User;
import br.edu.ifsp.doo.petshop.model.entities.Veterinary;

import java.sql.SQLException;

public class DAOUser {

    public User returnUserIfExist(String login, String password) throws  SQLException {
        DAOSecretary daoSecretary = new DAOSecretary();
        Secretary secretary = daoSecretary.getFromLogin(login, password);
            if (secretary != null)
                return secretary;

        DAOVeterinary daoVeterinary = new DAOVeterinary();
        Veterinary veterinary = daoVeterinary.getFromLogin(login, password);

        return veterinary;
    }
}
