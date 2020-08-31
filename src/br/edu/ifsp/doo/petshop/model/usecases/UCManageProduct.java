package br.edu.ifsp.doo.petshop.model.usecases;

import br.edu.ifsp.doo.petshop.model.entities.Product;
import br.edu.ifsp.doo.petshop.persistence.dao.DAOProduct;

import java.util.List;

public class UCManageProduct {
    DAOProduct daoProduct;

    public UCManageProduct(DAOProduct daoProduct) {
        this.daoProduct = daoProduct;
    }

    public void saveOrUpdate(Product product) {
        daoProduct.saveOrUpdate(product);
    }

    public List<Product> selectAll() {
        return daoProduct.selectAll();
    }

    public List<Product> selectAllActives() {
        return daoProduct.selectBy("active", 1);
    }
}
