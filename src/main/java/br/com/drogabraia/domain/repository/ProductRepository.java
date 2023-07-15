package br.com.drogabraia.domain.repository;

import br.com.drogabraia.domain.model.Manufacturer;
import br.com.drogabraia.domain.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {


    List<Product> findAllByFabricante(Manufacturer manufacturer);
}
