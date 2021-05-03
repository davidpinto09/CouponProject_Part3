package app.core.services;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import app.core.entities.Company;
import app.core.entities.Coupon;
import app.core.entities.Coupon.Category;
import app.core.excpetions.CouponServiceException;
import app.core.excpetions.CouponSystemException;

@Service
@Transactional
@Scope("prototype")
public class CompanyService extends ClientService {


	private Integer companyServiceId;

	@Override
	public boolean login(String companyEmail, String companyPassword) {
		Optional<Company> optionalCompany = companyRepository.findByEmailAndPassword(companyEmail, companyPassword);
		if (optionalCompany.isPresent()) {
			this.companyServiceId = optionalCompany.get().getId();
			
			return true;

		}
		return false;
	}

	public Coupon addCoupon(Coupon coupon) throws CouponSystemException {
		Company actualCompany = companyRepository.findById(companyServiceId).get();
		if (actualCompany != null) {

			if (!couponRepository.findOneByCompanyIdAndTitle(actualCompany.getId(), coupon.getTitle()).isPresent()) {
				coupon.setCompany(actualCompany);
				if (actualCompany.getCoupons().add(coupon)) {
					return couponRepository.save(coupon);
				} else {
					return null;

				}

			} else {
				throw new CouponServiceException("addCoupon() - failed. The coupon with title: " + coupon.getTitle()
						+ " already exist in your list.");
			}
		}
		throw new CouponServiceException("addCoupon() - failed. No company found");

	}

	public Coupon updateCoupon(Coupon coupon) throws CouponSystemException {
//		Company actualCompany = companyRepository.getOne(this.companyServiceId);
		// if (actualCompany != null) {
		Optional<Coupon> optionalCoupon = couponRepository.findOneByIdAndCompanyId(coupon.getId(),
				this.companyServiceId);

		if (optionalCoupon.isPresent()) {

			Coupon couponToUpdate = couponRepository.getOne(coupon.getId());

			couponToUpdate.setQuantity(coupon.getQuantity());
			couponToUpdate.setTitle(coupon.getTitle());
			couponToUpdate.setDescription(coupon.getDescription());
			couponToUpdate.setPrice(coupon.getPrice());
			couponToUpdate.setCategory(coupon.getCategory());
			couponToUpdate.setImage(coupon.getImage());
			couponToUpdate.setEndDate(coupon.getEndDate());
			couponToUpdate.setStartDate(coupon.getStartDate());
			return couponToUpdate;

		} else {

			throw new CouponServiceException(
					"updateCoupon() - failed.The coupon with id: " + coupon.getId() + " does not exist");
		}
	}
	// }

	public Coupon deleteCoupon(int couponId) throws CouponServiceException {

		Coupon couponToDelete = couponRepository.getOne(couponId);

		if (couponToDelete != null) {
			if (couponToDelete.getCompany().getId() == this.companyServiceId) {

				couponRepository.delete(couponToDelete);
				return couponToDelete;
			} else {
				throw new CouponServiceException(
						"You have no permission to update this coupon, since the coupon with id: " + couponId
								+ " does not belong to this company");
			}
		} else {

			throw new CouponServiceException("deleteCoupon(couponId) - failed. The coupon with id: " + couponId
					+ " you are trying to delete does not exist");
		}

	}

	public Coupon getOneCoupon(int couponId) throws CouponSystemException {

		Optional<Coupon> optionalCoupon = couponRepository.findById(couponId);
		if (optionalCoupon.isPresent()) {
			if (optionalCoupon.get().getCompany().getId() == this.companyServiceId) {

				return couponRepository.getOne(couponId);
			} else {
				throw new CouponServiceException("getOneCoupon(couponId) - failed. Sorry but the coupon with id: "
						+ couponId + " it does not belong to your company");

			}
		}
		throw new CouponServiceException(
				"getOneCoupon(couponId) - failed. Sorry but the coupon with id: " + couponId + " does not exist");

	}

	public List<Coupon> getAllCompanyCoupons() throws CouponSystemException {

		List<Coupon> companyCoupons = couponRepository.findAllByCompanyId(this.companyServiceId);
		if (companyCoupons.size() != 0) {
			return companyCoupons;
		} else {
			throw new CouponServiceException("getCompanyCoupons() - failed. You have no coupons");
		}
	}

	public List<Coupon> getAllCompanyCoupons(Category category) throws CouponSystemException {

		List<Coupon> companyCoupons = couponRepository.findAllByCompanyIdAndCategory(this.companyServiceId, category);
		if (companyCoupons.size() != 0) {
			return companyCoupons;
		} else {
			throw new CouponServiceException(
					"getCompanyCoupons(category) - failed. No coupons found with category " + category.name());
		}

	}

	public List<Coupon> getAllCompanyCoupons(double price) throws CouponSystemException {
		List<Coupon> companyCoupons = couponRepository.findAllByCompanyIdAndPriceLessThanEqual(this.companyServiceId,
				price, Sort.by("title"));
		if (companyCoupons.size() != 0) {
			return companyCoupons;
		} else {
			throw new CouponServiceException(
					"getCompanyCoupons(price) - failed. No coupons found equals or cheaper than " + price);
		}

	}

	public Company getCompanyDetails() throws CouponSystemException{
		Optional<Company> optionalCompany = companyRepository.findById(this.companyServiceId);
		if (optionalCompany.isPresent()) {
			return optionalCompany.get();
		}
		return null;
	}

	public Integer getCompanyServiceId() {
		return companyServiceId;
	}

}
