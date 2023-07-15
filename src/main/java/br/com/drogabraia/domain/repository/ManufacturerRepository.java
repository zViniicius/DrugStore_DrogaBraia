package br.com.drogabraia.domain.repository;

import br.com.drogabraia.domain.model.Manufacturer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ManufacturerRepository extends JpaRepository<Manufacturer, Long> {

    Optional<Manufacturer> findByRazaoSocial(String name);

    Optional<Manufacturer> findByCnpj(String cnpj);

    boolean existsByCnpj(String cnpj);

}
