package app.core.services;

import java.time.LocalDate;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.core.excpetions.CouponSystemException;
import app.core.repositories.CouponRepository;

@Service
@Transactional
public class JobService {

	@Autowired
	private CouponRepository couponRepository;
	
	public void deleteExpiredCoupons(LocalDate localDate) throws CouponSystemException {
		
		
		couponRepository.deleteAllByEndDateBefore(localDate);
		
	}
}
