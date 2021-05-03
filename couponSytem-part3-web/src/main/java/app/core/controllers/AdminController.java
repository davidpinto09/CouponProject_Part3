package app.core.controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
import app.core.entities.Customer;
import app.core.services.AdminService;

@RestController
@RequestMapping("/admin")
public class AdminController extends ClientController {

	@Autowired
	private AdminService adminService;

	public AdminController() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	@PostMapping("/login")
	public boolean login(@RequestParam String email, @RequestParam String password) {
		try {
			return adminService.login(email, password);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, e.getMessage());
		}
	}

	@PostMapping(path = "/addCompany")
	public Company addCompany(@RequestBody Company company) {

		try {
			return adminService.addCompany(company);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
		}

	}

	@PutMapping(path = "/updateCompany")
	public Company updateCompany(@RequestBody Company company) {

		try {
			return adminService.updateCompany(company);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		}

	}

	@DeleteMapping(path = "/deleteCompany")
	public Company deleteCompany(@RequestParam Integer companyId) {

		try {
			return adminService.deleteCompany(companyId);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
		}

	}

	@GetMapping(path = "/getOneCompany")
	public Company getOneCompany(@RequestParam Integer companyId) {
		try {
			return adminService.getOneCompany(companyId);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		}

	}

	@GetMapping(path = "/getAllCompanies")
	public Collection<Company> getAllCompanies() {
		try {
			return adminService.getAllCompanies();
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NO_CONTENT, e.getMessage());
		}
	}

	@PostMapping(path = "/addCustomer")
	public Customer addCustomer(@RequestBody Customer customer) {

		try {
			return adminService.addCustomer(customer);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
		}

	}

	@PutMapping(path = "/updateCustomer")
	public Customer updateCustomer(@RequestBody Customer customer) {

		try {
			return adminService.updateCustomer(customer);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		}

	}

	@DeleteMapping(path = "/deleteCustomer={customerId}")
	public Customer deleteCustomer(@PathVariable Integer customerId) {

		try {
			return adminService.deleteCustomer(customerId);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
		}

	}

	@GetMapping(path = "/getOneCustomer={customerId}")
	public Customer getOneCustomer(@PathVariable Integer customerId) {
		try {
			return adminService.getOneCustomer(customerId);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		}

	}

	@GetMapping(path = "/getAllCustomers")
	public Collection<Customer> getAllCustomers() {
		try {
			return adminService.getAllCustomers();
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NO_CONTENT, e.getMessage());
		}
	}

}
