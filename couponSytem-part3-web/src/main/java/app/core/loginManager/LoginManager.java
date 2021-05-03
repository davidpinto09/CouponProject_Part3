package app.core.loginManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

import app.core.services.AdminService;
import app.core.services.ClientService;
import app.core.services.CompanyService;
import app.core.services.CustomerService;

@Component
public class LoginManager {

	@Autowired
	private ConfigurableApplicationContext appCtxt;
	public enum ClientType {

		ADMIN, COMPANY, CUSTOMER
	}

	private boolean isLogged;

	public boolean isLogged() {
		return isLogged;
	}

	public void setLogged(boolean isLogged) {
		this.isLogged = isLogged;
	}

	public ClientService logIn(String email, String password, ClientType clientType) {

		if (clientType != null) {
			ClientService service = null;
			switch (clientType) {
			case ADMIN:

				AdminService adminService = appCtxt.getBean(AdminService.class);
				if(adminService.login(email, password)) {
					
					service = adminService;
				
					
			
				}else {
					System.err.println("Login error as admin verify email and password" + email + " " + password);
				}
				break;
			case COMPANY:
				
				CompanyService companyService = appCtxt.getBean(CompanyService.class);
				if(companyService.login(email, password)) {
					service = companyService;
					
				} else {
					System.err.println("Company Login error with email: " + email + " or password: " + password+".\nThe data does not match with database");
				}
				break;
			case CUSTOMER:
				
				CustomerService customerService = appCtxt.getBean(CustomerService.class);
				if(customerService.login(email, password)) {
					service = customerService;
					
				}else {
					System.err.println("Customer Login error with email: " + email + " or password: " + password+".\nThe data does not match with database");
				}

				break;
		
			}
			this.setLogged(true);
			return service;
		}
		return null;
	}

}
