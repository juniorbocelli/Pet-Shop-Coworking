package br.edu.ifsp.doo.petshop.persistence.dao;

import br.edu.ifsp.doo.petshop.model.entities.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DAOUser {
    public User returnUserIfExist(String login, String password) throws  SQLException {
        DAOSecretary daoSecretary = new DAOSecretary();
        ResultSet secretaryRS = new DAOSecretary().hasFromLogin(login, password);
        if (secretaryRS != null)
            return daoSecretary.getEntityFromResultSet(secretaryRS);

        DAOVeterinary daoVeterinary = new DAOVeterinary();
        ResultSet veterinaryRS = new DAOVeterinary().hasFromLogin(login, password);
        if (veterinaryRS != null)
            return daoVeterinary.getEntityFromResultSet(veterinaryRS);

        return null;
    }

    public boolean existSecretary() {
        return false;
    }
}
