package app.core.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import app.core.entities.Company;
import app.core.excpetions.CouponSystemException;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Integer> {

	Optional<Company> findByEmailAndPassword(String email, String password);

	Optional<Company> findByName(String name);
	Optional<Company> findByEmail(String email) ;


}
