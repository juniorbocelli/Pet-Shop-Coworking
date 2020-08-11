package br.edu.ifsp.doo.petshop.main;

import br.edu.ifsp.doo.petshop.persistence.utils.DatabaseBuilder;
import br.edu.ifsp.doo.petshop.view.loaders.WindowLogin;
import br.edu.ifsp.doo.petshop.view.loaders.WindowSecretaryDashboard;
import br.edu.ifsp.doo.petshop.view.loaders.WindowVeterinaryDashboard;

public class Main {
    public static void main(String[] args) {
        DatabaseBuilder dbBuilder = new DatabaseBuilder();
        dbBuilder.buildDatabaseIfMissing();
        // Testar Login
        //WindowLogin.main(args);

        // Testar Dashboard da Secretária
        //WindowSecretaryDashboard.main(args);

        // Testar Dashboard do Veterinário
        WindowVeterinaryDashboard.main(args);
    }
}
