
package app.core.services;

import java.util.Collection;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import app.core.entities.Company;
import app.core.entities.Customer;
import app.core.excpetions.CouponServiceException;
import app.core.excpetions.CouponSystemException;

@Service
@Transactional

public class AdminService extends ClientService {


	public static String ADMIN_EMAIL = "admin@admin.com";
	public static String ADMIN_PASSWORD = "admin";

	@Override
	public boolean login(String adminEmail, String adminPassword) {
		if (adminEmail.equals(ADMIN_EMAIL) && adminPassword.equals(ADMIN_PASSWORD)) {
		
			return true;
		}
		return false;
	}

	public Company addCompany(Company company) throws CouponSystemException {

		if (!(companyRepository.findByName(company.getName()).isPresent()
				&& companyRepository.findByEmail(company.getEmail()).isPresent())) {

			return companyRepository.save(company);

		} else {

			if (companyRepository.findByName(company.getName()).isPresent()
					&& companyRepository.findByEmail(company.getEmail()).isPresent()) {
				throw new CouponServiceException(
						"addCompany(company) - failed. The company you are trying to add already exists with name: "
								+ company.getName() + " and email: " + company.getEmail());
			} else if (companyRepository.findByName(company.getName()).isPresent()) {
				throw new CouponServiceException(
						"addCompany(company) - failed. The company you are trying to add already exists with name: "
								+ company.getName());
			} else {
				throw new CouponServiceException(
						"addCompany(company) - failed. The company you are trying to add already exists with email: "
								+ company.getEmail());
			}
		}
	}

	public Company updateCompany(Company company) throws CouponSystemException {

		Company companyToUpdate = companyRepository.findById(company.getId()).get();
		if (companyToUpdate != null) {
			companyToUpdate.setEmail(company.getEmail());
			companyToUpdate.setPassword(company.getPassword());
			return companyToUpdate;
		} else {
			throw new CouponServiceException(
					"updateCompany(company) - failed. The company you are trying to update does not exist");
		}
	}

	public Company deleteCompany(int companyId) throws CouponSystemException {
		Optional<Company> companyToDelete = companyRepository.findById(companyId);
		if (companyToDelete.get() != null) {

			companyRepository.delete(companyToDelete.get());
			return companyToDelete.get();

		} else {

			throw new CouponServiceException("deleteCompany(companyId) - failed. The company with id: " + companyId
					+ " you are trying to delete does not exist");
		}
	}

	public Company getOneCompany(int companyId) throws CouponSystemException {

		Optional<Company> optionalCompany = companyRepository.findById(companyId);
		if (optionalCompany.isPresent()) {

			return optionalCompany.get();
		}
		throw new CouponServiceException(
				"getOneCompany(companyId) - failed. The company with id: " + companyId + " does not exist");

	}

	public Collection<Company> getAllCompanies() throws CouponSystemException{
		return companyRepository.findAll(Sort.by("name"));
	}

	public Customer addCustomer(Customer customer) throws CouponSystemException {

		if (!customerRepository.findByEmail(customer.getEmail()).isPresent()) {

			return customerRepository.save(customer);
		} else {
			throw new CouponServiceException(
					"addCustomer(customer) - failed. The customer you are trying to add already exists with email: "
							+ customer.getEmail());
		}
	}

	public Customer updateCustomer(Customer customer) throws CouponSystemException {
		Customer customerToUpdate = customerRepository.findById(customer.getId()).get();
		if (customerToUpdate != null) {
			customerToUpdate.setFirstName(customer.getFirstName());
			customerToUpdate.setLastName(customer.getLastName());
			customerToUpdate.setEmail(customer.getEmail());
			customerToUpdate.setPassword(customer.getPassword());
			return customerToUpdate;

		} else {
			throw new CouponServiceException(
					"updateCustomer(customer) - failed. The customer you are trying to update does not exist");
		}
	}

	public Customer deleteCustomer(int customerId) throws CouponSystemException {
		Customer customerToDelete = customerRepository.findById(customerId).get();
		if (customerToDelete != null) {
			customerRepository.delete(customerToDelete);
			return customerToDelete;
		} else {

			throw new CouponServiceException("deleteCustomer(customerId) - failed. The customer with id: " + customerId
					+ " you are trying to delete does not exist");
		}
	}

	public Customer getOneCustomer(int customerId) throws CouponSystemException {

		Optional<Customer> optionalCustomer = customerRepository.findById(customerId);
		if (optionalCustomer.isPresent()) {
			return optionalCustomer.get();
		}
		throw new CouponServiceException(
				"getOneCustomer() - failed. The customer with id: " + customerId + " does not exist");

	}

	public Collection<Customer> getAllCustomers() throws CouponSystemException{
		return customerRepository.findAll(Sort.by("lastName"));
	}
}
