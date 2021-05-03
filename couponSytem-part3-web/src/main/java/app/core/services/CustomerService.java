package app.core.services;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import app.core.entities.Coupon;
import app.core.entities.Coupon.Category;
import app.core.entities.Customer;
import app.core.excpetions.CouponServiceException;
import app.core.excpetions.CouponSystemException;

@Service
@Transactional
@Scope("prototype")
public class CustomerService extends ClientService {

	private Integer customerServiceId;

	public Integer getCustomerServiceId() {
		return customerServiceId;
	}

	@Override
	public boolean login(String customerEmail, String customerPassword) {

		Optional<Customer> customerDB = customerRepository.findByEmailAndPassword(customerEmail, customerPassword);

		if (customerDB.isPresent()) {

			this.customerServiceId = customerDB.get().getId();

			return true;

		}
		return false;
	}

	public Coupon purchaseCoupon(Coupon coupon) throws CouponSystemException {

		Customer actualCustomer = customerRepository.findById(customerServiceId).get();
		if (actualCustomer != null) {
			Coupon couponToPurchase = couponRepository.getOne(coupon.getId());
			if (couponToPurchase != null) {
				if (!couponToPurchase.getCustomers().contains(actualCustomer)) {
					if (couponToPurchase.getQuantity() > 0) {
						if (couponToPurchase.getEndDate().isAfter(LocalDate.now())) {

							actualCustomer.getCoupons().add(couponToPurchase);
							couponToPurchase.setQuantity(couponToPurchase.getQuantity() - 1);
							return couponToPurchase;
						} else {
							throw new CouponServiceException(
									"purchaseCoupon(coupon) - failed. The coupon you are trying to purchase is expired ");

						}
					} else {
						throw new CouponServiceException(
								"purchaseCoupon(coupon) - failed. The coupon you are trying to purchase is no more available ");

					}
				} else {
					throw new CouponServiceException(
							"purchaseCoupon(coupon) - failed. The coupon you are trying to purchase you have already purchase ");
				}

			} else {
				throw new CouponServiceException(
						"purchaseCoupon(coupon) - failed. The coupon you are trying to purchase does not exist ");
			}
		}throw new CouponServiceException(
				"purchaseCoupon(coupon) - failed. Customer with id: " + customerServiceId +" doesn't exist");
		
	}

	public Collection<Coupon> getCustomerCoupons() throws CouponSystemException {

		List<Coupon> customerCoupons = couponRepository.findByCustomersId(this.customerServiceId);
		if (customerCoupons.size() != 0) {
			return customerCoupons;
		} else {
			throw new CouponServiceException("getCustomerCoupons() - failed. You have no coupons");
		}
	}

	public Collection<Coupon> getCustomerCoupons(double price) throws CouponSystemException {

		List<Coupon> customerCoupons = couponRepository.findByCustomersIdAndPriceLessThanEqual(this.customerServiceId,
				price);
		if (customerCoupons.size() != 0) {

			return customerCoupons;
		} else {
			throw new CouponServiceException(
					"getCustomerCoupons(price) - failed. No coupons found equals or cheaper than " + price);
		}
	}

	public Collection<Coupon> getCustomerCoupons(Category category) throws CouponSystemException {

		List<Coupon> customerCoupons = couponRepository.findByCustomersIdAndCategory(this.customerServiceId, category);
		if (customerCoupons.size() != 0) {

			return customerCoupons;
		} else {

			throw new CouponServiceException(
					"getCustomerCoupons(category) - failed. No coupons found with category " + category.name());
		}
	}

	public Customer getCustomerDetails() throws CouponSystemException {

		Optional<Customer> optionalCustomer = customerRepository.findById(this.customerServiceId);
		if (optionalCustomer.isPresent()) {
			return optionalCustomer.get();
		}
		throw new CouponServiceException(
				"getCustomerDetails() failed - Sorry but the customer you are trying to retrieve it does not exist in the database");

	}

	public Coupon getOneCoupon(int couponId) throws CouponSystemException {
		Optional<Coupon> optionalCoupon = couponRepository.findById(couponId);
		if (optionalCoupon.isPresent()) {
			return optionalCoupon.get();
		}
		throw new CouponServiceException(
				"getOneCoupon(couponId) - fialed. The coupon with id: " + couponId + " does not exist");
	}

}
