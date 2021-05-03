package app.core.repositories;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import app.core.entities.Coupon;
import app.core.entities.Coupon.Category;
import app.core.excpetions.CouponSystemException;

@Repository
public interface CouponRepository extends JpaRepository<Coupon, Integer> {

	List<Coupon> findAllByCompanyIdAndPriceLessThanEqual(int companyId, double price) ;

	List<Coupon> findAllByCompanyIdAndPriceLessThanEqual(int companyId, double price, Sort sort) ;

	List<Coupon> findAllByCompanyIdAndCategory(int companyId, Category category) ;

	List<Coupon> findAllByCompanyIdAndCategory(int companyId, Category category, Sort sort) ;

	Optional<Coupon> findOneByCompanyIdAndTitle(int companyId, String couponTitle) ;

	Optional<Coupon> findOneByIdAndCompanyId(int companyId, int couponId) ;

	List<Coupon> findAllByCompanyId(int companyId) ;

	List<Coupon> findByCustomersId(int customerID) ;

	List<Coupon> findByCustomersIdAndCategory(int customerID, Category category) ;

	List<Coupon> findByCustomersIdAndPriceLessThanEqual(int customerID, double price) ;

	void deleteAllByEndDateBefore(LocalDate date) ;

}
