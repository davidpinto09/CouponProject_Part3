package app.core;

import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import app.core.entities.Company;

//RestTemplate
public class CouponProjectRestTemplate {

	public static void main(String[] args) {
		try {
			RestTemplate restTemplate = new RestTemplate();
			String urlString;

			{
				// GET ONE Company

//				urlString = "http://localhost:8080/admin/getOneCompany?companyId=6";
//				ResponseEntity<Company> responseEntity = restTemplate.exchange(urlString, HttpMethod.GET, null,
//						Company.class);
//				System.out.println(responseEntity);
//				Company company = responseEntity.getBody();
//				System.out.println(company);

			}
			{
//				 GET ALL Companies
				urlString = "http://localhost:8080/admin/getAllCompanies";

				ParameterizedTypeReference<List<Company>> ptr = new ParameterizedTypeReference<List<Company>>() {

				};
				ResponseEntity<List<Company>> responseEntity = restTemplate.exchange(urlString, HttpMethod.GET, null,
						ptr);
				System.out.println(responseEntity);
				List<Company> allCompanies = responseEntity.getBody();
				System.out.println(allCompanies);

			}
			{

				// POST
//				urlString = "http://localhost:8080/admin/addCompany";
//				Company newCompany = new Company("javaCompany", "javaCompanyRest@gmail.com", "javaRest");
//				newCompany = restTemplate.postForObject(new URI(urlString), newCompany, Company.class);

			}

			{

				// PUT
//				urlString = "http://localhost:8080/admin/updateCompany";
//				Company companyUpdate = new Company(5, "RESTCompany", "restEMAIL@GMAIL.COM", "restPassword");
//				RequestEntity<Company> requestEntity = new RequestEntity<>(companyUpdate, HttpMethod.PUT,
//						new URI(urlString));
//				
//				companyUpdate = restTemplate.exchange(requestEntity,Company.class).getBody();
//				System.out.println(companyUpdate);

				{// DELETE
					urlString = "http://localhost:8080/admin/deleteCompany?companyId=6";
					restTemplate.delete(urlString);

				}

			}

		} catch (Exception e) {
			System.out.println("=====================");
			System.out.println(e.getMessage());
			System.out.println("=====================");
		}

	}

}
