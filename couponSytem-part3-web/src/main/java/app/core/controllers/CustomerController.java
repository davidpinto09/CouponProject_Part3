package app.core.controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import app.core.entities.Company;
import app.core.entities.Coupon;
import app.core.entities.Coupon.Category;
import app.core.entities.Customer;
import app.core.services.CustomerService;

@RestController
@RequestMapping("/customer")
public class CustomerController extends ClientController {

	@Autowired
	private CustomerService customerService;

	@Override
	@PostMapping("/login")
	public boolean login(@RequestParam String email, @RequestParam String password) {
		try {
			return customerService.login(email, password);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, e.getMessage());
		}
	}

	@PostMapping(path = "/purchaseCoupon")
	public Coupon purchaseCoupon(@RequestBody Coupon coupon) {
		try {
			return customerService.purchaseCoupon(coupon);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());

		}
	}

	@GetMapping(path = "/getCustomerCoupons")
	public Collection<Coupon> getCustomerCoupons() {
		try {
			return customerService.getCustomerCoupons();
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());

		}
	}

	@GetMapping(path = "/getCustomerCouponsByCategory")
	public Collection<Coupon> getCustomerCoupons(@RequestParam Category couponCategory) {
		try {
			return customerService.getCustomerCoupons(couponCategory);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());

		}
	}

	@GetMapping(path = "/getCustomerCouponsByPrice")
	public Collection<Coupon> getCustomerCoupons(@RequestParam Double couponPrice) {
		try {
			return customerService.getCustomerCoupons();
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());

		}
	}

	@GetMapping(path = "/getCustomerDetails")
	public Customer getCustomerDetails() {
		try {
			return customerService.getCustomerDetails();
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NO_CONTENT, e.getMessage());

		}

	}

	@GetMapping(path = "/getOneCoupon={couponId}")

	public Coupon getOneCoupon(@PathVariable Integer couponId) {

		try {
			return customerService.getOneCoupon(couponId);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		}
	}

}
