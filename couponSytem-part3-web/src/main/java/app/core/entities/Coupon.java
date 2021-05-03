package app.core.entities;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.context.annotation.Scope;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "coupons")
@Scope("prototype")
public class Coupon {

	public static enum Category {
		FOOD, ELECTRICITY, RESTAURANT, VACATION, LIFESTYLE_AND_HEALTH, CULTURE_AND_ENTERTAINMENT, SPORTS, GAMES,
		SHOPPING;

	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "coupon_id")
	private Integer id;

	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH,
			CascadeType.REFRESH }, fetch = FetchType.LAZY)
	@JoinColumn(name = "company_id", referencedColumnName = "company_id")
	private Company company;

	@Column(name = "coupon_title")
	private String title;

	@Column(name = "coupon_description")
	private String description;

	@Column(name = "coupon_category")
	@Enumerated(EnumType.STRING)
	private Category category;

	@ManyToMany(cascade = { CascadeType.DETACH, CascadeType.REFRESH, CascadeType.MERGE,
			CascadeType.PERSIST }, fetch = FetchType.LAZY)
	@JoinTable(name = "customers_vs_coupons", joinColumns = @JoinColumn(name = "coupon_id", referencedColumnName = "coupon_id"), inverseJoinColumns = @JoinColumn(name = "customer_id", referencedColumnName = "customer_id"))
@JsonIgnore
	private Collection<Customer> customers = new ArrayList<>();

	@Column(name = "coupon_start_date")
	private LocalDate startDate;

	@Column(name = "coupon_end_date")
	private LocalDate endDate;

	@Column(name = "coupon_price")
	private Double price;

	@Column(name = "coupon_quantity")
	private Integer quantity;

	@Column(name = "coupon_image")
	private String image;

	public Coupon() {
		super();
	}

	

	public Coupon(String title, String description, Category category, LocalDate startDate, LocalDate endDate,
			Double price, Integer quantity, String image) {
		super();
		this.title = title;
		this.description = description;
		this.category = category;
		this.startDate = startDate;
		this.endDate = endDate;
		this.price = price;
		this.quantity = quantity;
		this.image = image;
	}



	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Collection<Customer> getCustomers() {
		if (customers == null) {
			customers = new ArrayList<>();
		}
		return customers;
	}

	public void setCustomers(Collection<Customer> customers) {
		this.customers = customers;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	@Override
	public String toString() {
		return "Coupon [id=" + id + ", companyId=" + company.getId() + ", title=" + title + ", description="
				+ description + ", category=" + category + ", startDate=" + startDate + ", endDate=" + endDate
				+ ", price=" + price + ", quantity=" + quantity + ", image=" + image + "]";
	}



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((company == null) ? 0 : company.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		return result;
	}



	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Coupon other = (Coupon) obj;
		if (company == null) {
			if (other.company != null)
				return false;
		} else if (!company.equals(other.company))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}

}
