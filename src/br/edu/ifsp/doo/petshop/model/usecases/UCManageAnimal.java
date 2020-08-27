package br.edu.ifsp.doo.petshop.model.usecases;

import br.edu.ifsp.doo.petshop.model.entities.Animal;
import br.edu.ifsp.doo.petshop.model.entities.Client;
import br.edu.ifsp.doo.petshop.model.entities.Veterinary;
import br.edu.ifsp.doo.petshop.persistence.dao.DAOAnimal;
import br.edu.ifsp.doo.petshop.persistence.dao.DAOVeterinary;
import br.edu.ifsp.doo.petshop.persistence.utils.DAO;

import java.util.List;

public class UCManageAnimal {
    DAOAnimal daoAnimal;
    UCManageClient ucManageClientPersistence;
    UCManageVeterinary ucManageVeterinaryPersistence;

    public UCManageAnimal(DAOAnimal daoAnimal) {
        this.daoAnimal = daoAnimal;
    }

    public UCManageAnimal(DAOAnimal daoAnimal, DAO<Client, String> daoClient, DAOVeterinary daoVeterinary) {
        this.daoAnimal = daoAnimal;

        this.ucManageClientPersistence = new UCManageClient(daoClient);
        this.ucManageVeterinaryPersistence = new UCManageVeterinary(daoVeterinary);
    }

    public void saveOrUpdate(Animal animal) {
        daoAnimal.saveOrUpdate(animal);
    }

    public Integer saveOrUpdateWithReturnId(Animal animal) {
        return daoAnimal.saveOrUpdateWithReturnId(animal);
    }

    public List<Animal> selectAllWithOwnerAndVeterinary() {
        List<Animal> animalList = selectAll();
        animalList.forEach(k -> {
            daoAnimal.selectAndBindOwner(k);
            daoAnimal.selectAndBindVeterinary(k);
        });

        return animalList;
    }

    public List<Animal> selectAllWithOwnerAndVeterinary(Client client) {
        List<Animal> animalList = daoAnimal.selectBy("cpf_owner", client.getCpf());
        animalList.forEach(k -> {
            k.setOwner(client);
            daoAnimal.selectAndBindVeterinary(k);
        });

        return animalList;
    }

    public List<Animal> selectAll() {
        return daoAnimal.selectAll();
    }

    public void updateDiseaseList(Animal animal) {
        List<String> diseasesToUpdate = animal.getDiseasesAsString();
        List<String> diseasesInDatabase = selectAnimalDiseases(animal);

        deleteRemovedDiseases(animal, diseasesToUpdate, diseasesInDatabase);
        insertNewDiseases(animal, diseasesToUpdate, diseasesInDatabase);
    }

    public List<String> selectAnimalDiseases(Animal animal){
        List<String> diseases = daoAnimal.selectAnimalDiseases(animal);
        return diseases;
    }

    private void deleteRemovedDiseases(Animal animal, List<String> diseasesToUpdate, List<String> diseasesInDatabase) {
        diseasesInDatabase.forEach(c-> {
            if(!diseasesToUpdate.contains(c))
                daoAnimal.removeAnimalDisease(animal, c);
        });
    }

    private void insertNewDiseases(Animal animal, List<String> diseasesToUpdate, List<String> diseasesInDatabase) {
        diseasesToUpdate.forEach(c-> {
            if(!diseasesInDatabase.contains(c))
                daoAnimal.insertAnimalDisease(animal, c);
        });
    }

    public List<Client> getClientsList() {
        return ucManageClientPersistence.selectAll();
    }

    public List<Veterinary> getVeterinariesList() {
        return ucManageVeterinaryPersistence.selectAll();
    }
}
