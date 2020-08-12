package br.edu.ifsp.doo.petshop.main;

import br.edu.ifsp.doo.petshop.persistence.dao.DAOSecretary;
import br.edu.ifsp.doo.petshop.persistence.utils.DatabaseBuilder;
import br.edu.ifsp.doo.petshop.view.loaders.WindowLogin;
import br.edu.ifsp.doo.petshop.view.loaders.WindowSecretary;

public class Main {
    public static void main(String[] args) {
        // Constrói banco caso não exista
        DatabaseBuilder dbBuilder = new DatabaseBuilder();
        dbBuilder.buildDatabaseIfMissing();

        // Cadastro inicial de secretária ou tela de login
        DAOSecretary daoSecretary = new DAOSecretary();
        if (daoSecretary.existSecretary()) 
            WindowLogin.main(args);
        WindowSecretary.main(args);
    }
}
