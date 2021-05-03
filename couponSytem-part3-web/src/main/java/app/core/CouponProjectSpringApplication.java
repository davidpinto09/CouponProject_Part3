package app.core;

import java.time.LocalDate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import app.core.dailyJob.CouponExpirationDailyJob;
import app.core.entities.Company;
import app.core.entities.Coupon;
import app.core.entities.Coupon.Category;
import app.core.entities.Customer;
import app.core.excpetions.CouponServiceException;
import app.core.excpetions.CouponSystemException;
import app.core.loginManager.LoginManager;
import app.core.loginManager.LoginManager.ClientType;
import app.core.services.AdminService;
import app.core.services.CompanyService;
import app.core.services.CustomerService;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableAspectJAutoProxy
@EnableSwagger2

public class CouponProjectSpringApplication {


	public static void main(String[] args) {

			ConfigurableApplicationContext applicationContext = SpringApplication.run(CouponProjectSpringApplication.class, args) ;
		

			applicationContext.getBean(CouponExpirationDailyJob.class);
//			try {
//				adminMethods(applicationContext);
////			companyServiceMethods(applicationContext);
////			customerServiceMethods(applicationContext);
//			} catch (CouponSystemException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}

			
		


		

	}



	private static void customerServiceMethods(ConfigurableApplicationContext applicationContext)
			throws CouponSystemException {
		LoginManager loginManager = applicationContext.getBean(LoginManager.class);
		CustomerService customerService = (CustomerService) loginManager.logIn("cust1@gmail.com", "1234", ClientType.CUSTOMER);
		

			System.out.println(customerService.getCustomerDetails());
			Coupon couponToPurchase1 = customerService.getOneCoupon(1);
			Coupon couponToPurchase2 = customerService.getOneCoupon(2);
			Coupon couponToPurchase4 = customerService.getOneCoupon(4);
			Coupon couponToPurchase5 = customerService.getOneCoupon(5);
			System.out.println(customerService.purchaseCoupon(couponToPurchase1));
			System.out.println(customerService.purchaseCoupon(couponToPurchase2));
			System.out.println(customerService.purchaseCoupon(couponToPurchase4));
			System.out.println(customerService.purchaseCoupon(couponToPurchase5));

			System.out.println(customerService.getCustomerCoupons());
			System.out.println(customerService.getCustomerCoupons(Category.LIFESTYLE_AND_HEALTH));
			System.out.println(customerService.getCustomerCoupons(40.00));

		
	}

	private static void companyServiceMethods(ConfigurableApplicationContext applicationContext)
			throws CouponSystemException, CouponServiceException {
		LoginManager loginManager = applicationContext.getBean(LoginManager.class);
		CompanyService companyService =  (CompanyService) loginManager.logIn("comp1@gmail.com", "1234", ClientType.COMPANY);
		

			System.out.println(companyService.getCompanyDetails());

			Coupon coup1 = new Coupon("SHOW", "MADONNA", Coupon.Category.CULTURE_AND_ENTERTAINMENT,
					LocalDate.of(2021, 01, 31), LocalDate.of(2023, 02, 01), 100.00, 300, null);
			Coupon coup2 = new Coupon("TOASTER", "TOAST THE BREAD", Coupon.Category.ELECTRICITY,
					LocalDate.of(2021, 01, 31), LocalDate.of(2022, 02, 01), 29.90, 60, null);
			Coupon coup3 = new Coupon("HAMBURGER", "BBB 220", Coupon.Category.FOOD, LocalDate.of(2021, 01, 31),
					LocalDate.of(2024, 03, 01), 29.90, 200, null);
			Coupon coup4 = new Coupon("FIFA", "FIFA21", Coupon.Category.GAMES, LocalDate.of(2021, 01, 31),
					LocalDate.of(2022, 03, 01), 90.00, 70, null);
			Coupon coup5 = new Coupon("SPA", "MASSAGE", Coupon.Category.LIFESTYLE_AND_HEALTH,
					LocalDate.of(2021, 01, 31), LocalDate.of(2025, 04, 01), 30.00, 100, null);
			Coupon coup6 = new Coupon("PIZZA", "2 FAMILY PIZZAS", Coupon.Category.RESTAURANT,
					LocalDate.of(2021, 01, 31), LocalDate.of(2024, 02, 10), 300.00, 65, null);
			Coupon coup7 = new Coupon("ADIDAS", "SHOES", Coupon.Category.SHOPPING, LocalDate.of(2021, 01, 31),
					LocalDate.of(2022, 02, 10), 80.00, 120, null);
			Coupon coup8 = new Coupon("TICKETS TO DERBY", "MACCABI VS HAPOEL", Coupon.Category.SPORTS,
					LocalDate.of(2021, 06, 01), LocalDate.of(2021, 06, 02), 70.00, 500, null);
			Coupon coup9 = new Coupon("FLIGHT TICKET", "TO EILAT", Coupon.Category.VACATION, LocalDate.of(2021, 01, 31),
					LocalDate.of(2026, 05, 01), 250.00, 600, null);

			System.out.println(companyService.addCoupon(coup1));
			System.out.println(companyService.addCoupon(coup2));
			System.out.println(companyService.addCoupon(coup3));
			System.out.println(companyService.addCoupon(coup4));
			System.out.println(companyService.addCoupon(coup5));
			System.out.println(companyService.addCoupon(coup6));
			System.out.println(companyService.addCoupon(coup7));
			System.out.println(companyService.addCoupon(coup8));
			System.out.println(companyService.addCoupon(coup9));

			Coupon couponToUpdate = companyService.getOneCoupon(3);
			couponToUpdate.setCategory(Category.SPORTS);
			couponToUpdate.setQuantity(2000);
			couponToUpdate.setDescription("New Description");
			couponToUpdate.setTitle("New Coupon Update");
			couponToUpdate.setStartDate(LocalDate.of(2021, 04, 23));
			couponToUpdate.setEndDate(LocalDate.of(2021, 05, 23));
			couponToUpdate.setImage("imageUpdate.jpg");
			couponToUpdate.setPrice(20.00);
			System.out.println(companyService.updateCoupon(couponToUpdate));

			System.out.println(companyService.deleteCoupon(3));
			System.out.println(companyService.getAllCompanyCoupons());
			System.out.println(companyService.getAllCompanyCoupons(Category.SPORTS));
			System.out.println(companyService.getAllCompanyCoupons(120.00));
			/*
			 * getCompanyDetails addCoupon updateCoupon deleteCoupon getOneCoupon
			 * getCompanyCoupons and derived
			 * 
			 */

		
	}

	private static void adminMethods(ConfigurableApplicationContext applicationContext) throws CouponSystemException {
		LoginManager loginManager = applicationContext.getBean(LoginManager.class);
		AdminService admin = (AdminService) loginManager.logIn("admin@admin.com", "admin", ClientType.ADMIN);

		

			Company c1 = new Company("comp1", "comp1@gmail.com", "1234");
			Company c2 = new Company("comp2", "comp2@gmail.com", "1234");
			Company c3 = new Company("comp3", "comp3@gmail.com", "1234");

			System.out.println(admin.addCompany(c1));
			System.out.println(admin.addCompany(c2));
			System.out.println(admin.addCompany(c3));

			Company cToUpdate = admin.getOneCompany(2);
			cToUpdate.setName("cToUpdate");
			cToUpdate.setEmail("cToUpdate@gmail.com");
			cToUpdate.setPassword("cToUpdatePass");
			System.out.println(admin.updateCompany(cToUpdate));

			admin.deleteCompany(3);
			System.out.println(admin.getAllCompanies());

			Customer customer1 = new Customer("cust1", "lastCust1", "cust1@gmail.com", "1234");
			Customer customer2 = new Customer("cust2", "lastCust2", "cust2@gmail.com", "1234");
			Customer customer3 = new Customer("cust3", "lastCust3", "cust3@gmail.com", "1234");
			Customer customer4 = new Customer("cust4", "lastCust4", "cust4@gmail.com", "1234");

			System.out.println(admin.addCustomer(customer1));
			System.out.println(admin.addCustomer(customer2));
			System.out.println(admin.addCustomer(customer3));
			System.out.println(admin.addCustomer(customer4));

			Customer customerToUpdate = admin.getOneCustomer(3);
			customerToUpdate.setFirstName("customerUpdate");
			customerToUpdate.setLastName("LastcustomerUpdate");
			customerToUpdate.setEmail("customerUpdate@gmail.com");
			customerToUpdate.setPassword("customerUpdatePass");
			System.out.println(admin.updateCustomer(customerToUpdate));

			System.out.println(admin.deleteCustomer(2));

			System.out.println(admin.getAllCustomers());

		
	}

}
