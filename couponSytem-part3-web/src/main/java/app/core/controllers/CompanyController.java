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
import app.core.services.CompanyService;

@RestController
@RequestMapping("/company")
public class CompanyController extends ClientController {

	@Autowired
	private CompanyService companyService;

	@Override
	@PostMapping("/login")
	public boolean login(@RequestParam String email, @RequestParam String password) {
		try {
			return companyService.login(email, password);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, e.getMessage());
		}
	}

	@PostMapping(path = "/addCoupon")
	public Coupon addCoupon(@RequestBody Coupon coupon) {
		try {
			return companyService.addCoupon(coupon);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());

		}
	}

	@PutMapping(path = "/updateCoupon")
	public Coupon updateCoupon(@RequestBody Coupon coupon) {
		try {
			return companyService.updateCoupon(coupon);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());

		}
	}

	@DeleteMapping(path = "/deleteCoupon={couponId}")
	public Coupon deleteCoupon(@PathVariable Integer couponId) {

		try {
			return companyService.deleteCoupon(couponId);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());

		}

	}

	@GetMapping(path = "/getOneCoupon={couponId}")
	public Coupon getOneCoupon(@PathVariable Integer couponId) {
		try {
			return companyService.getOneCoupon(couponId);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());

		}
	}

	@GetMapping(path = "/getAllCompanyCouponsByCategory")
	public Collection<Coupon> getAllCompanyCoupons(@RequestParam Category couponCategory) {
		try {
			return companyService.getAllCompanyCoupons(couponCategory);

		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());

		}
	}

	@GetMapping(path = "/getAllCompanyCouponsByPrice")
	public Collection<Coupon> getAllCompanyCoupons(@RequestParam Double couponPrice) {
		try {
			return companyService.getAllCompanyCoupons(couponPrice);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());

		}
	}

	@GetMapping(path = "/getAllCompanyCoupons")
	public Collection<Coupon> getAllCoupons() {
		try {
			return companyService.getAllCompanyCoupons();
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());

		}
	}

	@GetMapping(path = "/getCompanyDetails")
	public Company getCompanyDetails() {
		try {
			return companyService.getCompanyDetails();
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NO_CONTENT, e.getMessage());

		}

	}

}
