package org.tom.ecocart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tom.ecocart.Entities.Product;

import java.util.Optional;

public interface IProductRepository extends JpaRepository<Product, String> {

    Optional<Product> findProductByName(String name);

}
