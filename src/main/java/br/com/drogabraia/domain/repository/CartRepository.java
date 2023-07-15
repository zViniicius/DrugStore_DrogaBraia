package br.com.drogabraia.domain.repository;

import br.com.drogabraia.domain.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CartRepository extends JpaRepository<Cart, Long> {


}
