package app.core.dailyJob;

import java.time.LocalDate;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import app.core.excpetions.CouponSystemException;
import app.core.services.JobService;

@Component
public class CouponExpirationDailyJob implements Runnable {

	
	@Autowired
	private JobService jobService;
	
	private Thread job;
	
	private boolean quit;

	private static Integer totalCouponsDeleted;

	@Override
	public void run() {

		System.out.println("Starting Coupon Expiration Daily Job");
		while (!quit) {
			try {

				jobService.deleteExpiredCoupons(LocalDate.now());

				Thread.sleep(86400000);

			} catch (InterruptedException e) {
				stopDailyJob();
			} catch (CouponSystemException e) {
				e.printStackTrace();
			}

		}

	}

	@PostConstruct
	public void startDailyJob() {
		job = new Thread(this);
		job.start();
	}

	@PreDestroy
	public void stopDailyJob() {
		System.out.println("Shutting down Coupon Expiration Daily Job");
		System.out.println("A total of " + totalCouponsDeleted + " coupons have been deleted");
		quit = true;
		job.interrupt();

	}

}
