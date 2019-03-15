package isen.java2.project.model;

import java.time.LocalDate;

public class Person{

	private Integer id;
	private String firstname;
	private String lastname;
	private String nickname;
	private String phonenumber;
	private Address address;
	private String emailaddress;
	private LocalDate birthdate;
	private String avatarPath;
	
	public Person (Integer id, String firstname, String lastname) {
		this.id = id;
		this.firstname = firstname;
		this.lastname = lastname;
	}

	public Person(Integer id, String firstname, String lastname, String nickname, String phonenumber, Address address,
			String emailaddress, LocalDate birthdate, String avatarPath) {
		super();
		this.id = id;
		this.firstname = firstname;
		this.lastname = lastname;
		this.nickname = nickname;
		this.phonenumber = phonenumber;
		this.address = address;
		this.emailaddress = emailaddress;
		this.birthdate = birthdate;
		this.avatarPath = avatarPath;
	}
	
	public String getAvatarPath() {
		return avatarPath;
	}

	public void setAvatarPath(String avatarPath) {
		this.avatarPath = avatarPath;
	}

	public Integer getId() {
		return id;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getPhonenumber() {
		return phonenumber;
	}

	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public String getEmailaddress() {
		return emailaddress;
	}

	public void setEmailaddress(String emailaddress) {
		this.emailaddress = emailaddress;
	}

	public LocalDate getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(LocalDate birthdate) {
		this.birthdate = birthdate;
	}

	public void setId(Integer id) {
		// TODO Auto-generated method stub
		this.id = id;
	}
}