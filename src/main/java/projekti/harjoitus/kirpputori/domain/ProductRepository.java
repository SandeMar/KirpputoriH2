package projekti.harjoitus.kirpputori.domain;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<Product, Long> {
	List<Product> findByProductName(String productName);
	List<Product> findByProductNameStartingWith(String Housut);
}
