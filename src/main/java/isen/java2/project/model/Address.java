package isen.java2.project.model;

public class Address {

	private Integer id;
	private Integer number;
	private String street;
	private Integer postalCode;
	private String city;
	
	public Address(Integer id, Integer number, String street, Integer postalCode, String city) {
		super();
		this.id = id;
		this.number = number;
		this.street = street;
		this.postalCode = postalCode;
		this.city = city;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}

	public String getAddressFormat() {
		return number + " " + street + " " + postalCode + " " + city.toUpperCase();
	}
	
	public Integer getId() {
		return id;
	}	
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public int getPostalCode() {
		return postalCode;
	}
	public void setPostalCode(int postalCode) {
		this.postalCode = postalCode;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	
}
