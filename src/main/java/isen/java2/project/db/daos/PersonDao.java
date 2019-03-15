package isen.java2.project.db.daos;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import isen.java2.project.model.Address;
import isen.java2.project.model.Person;

public class PersonDao {
	
	public List<Person> listPeople() {
		List<Person> listOfPeople = new ArrayList<>();
		try (Connection connection = DataSourceFactory.getDataSource().getConnection()) {
			try (Statement stmt = connection.createStatement()) {

				try (ResultSet results = stmt
						.executeQuery("select * from person")) {
					while (results.next()) {
						// The novelty, here, is that we will instanciate TWO
						// objects from our Resultset line : a film, and a genre
						
						Person person = new Person(
											results.getInt("idperson"),
											results.getString("firstname"),
											results.getString("lastname"));
						
						if(results.getString("nickname") != null) {
							person.setNickname(results.getString("nickname"));
						}
						if(results.getString("phonenumber") != null) {
							person.setPhonenumber(results.getString("phonenumber"));
						}
						if(results.getInt("address_id") != 0) {
							AddressDao aDao = new AddressDao();
							Address address = aDao.getAddress(results.getInt("address_id"));
							person.setAddress(address);
						}
						if(results.getString("emailaddress") != null) {
							person.setEmailaddress(results.getString("emailaddress"));
						}
						if(results.getString("birthdate") != null) {
							person.setBirthdate(results.getDate("birthdate").toLocalDate());
						}
						if(results.getString("avatarPath") != null) {
							person.setAvatarPath(results.getString("avatarPath"));
						}

						listOfPeople.add(person);
					}
					return listOfPeople;
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException("Oops", e);
		}
	}
	
	public Person addPerson(Person person) {
		try (Connection cnx = DataSourceFactory.getDataSource().getConnection()) {
			
			// Here we pass an option to tell the DB that we want to get the
			// generated keys back
			try (PreparedStatement stmt = cnx.prepareStatement(
					"INSERT INTO person(firstname,lastname,nickname,phonenumber,address_id,emailaddress,birthdate,avatarPath) VALUES(?,?,?,?,?,?,?,?)",
					Statement.RETURN_GENERATED_KEYS)) {
				stmt.setString(1, person.getFirstname());
				stmt.setString(2, person.getLastname());
				stmt.setString(3, person.getNickname());
				// Nothing fancy here. we just have to grab the genre id to make
				// the link in the DB. Warning: this is a foreign key, so in
				// real life you should decide how to handle the case where the
				// genre does not exist in the DB
				stmt.setString(4, person.getPhonenumber());
				
				if(person.getAddress() != null) {
					stmt.setInt(5, person.getAddress().getId());
				}else {
					stmt.setInt(5, 0);
				}
				
				stmt.setString(6, person.getEmailaddress());
				
				if(person.getBirthdate() != null) {
					stmt.setDate(7, Date.valueOf(person.getBirthdate()));
				}else {
					stmt.setDate(7, null);
				}
				
				stmt.setString(8, person.getAvatarPath());
				stmt.executeUpdate();
				// A little routine to grab the key and set it in our object
				try (ResultSet keys = stmt.getGeneratedKeys()) {
					keys.next();
					person.setId(keys.getInt(1));
					return person;
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException("An error occured", e);
		}
	}
	
	public void updatePerson(Person person) {
		try (Connection cnx = DataSourceFactory.getDataSource().getConnection()) {
			
			// Here we pass an option to tell the DB that we want to get the
			// generated keys back
			try (PreparedStatement stmt = cnx.prepareStatement(
					"UPDATE person SET firstname = ? ,lastname = ? ,nickname = ? ,phonenumber = ? ,address_id = ? ,emailaddress = ? ,birthdate = ?, avatarPath = ? WHERE idperson = ?",
					Statement.RETURN_GENERATED_KEYS)) {
				stmt.setString(1, person.getFirstname());
				stmt.setString(2, person.getLastname());
				stmt.setString(3, person.getNickname());
				stmt.setString(4, person.getPhonenumber());
				if(person.getAddress() != null) {
					stmt.setInt(5, person.getAddress().getId());
				}else {
					stmt.setInt(5, 0);
				}
				stmt.setString(6, person.getEmailaddress());
				if(person.getBirthdate() != null) {
					stmt.setDate(7, Date.valueOf(person.getBirthdate()));
				}else {
					stmt.setDate(7, null);
				}
				stmt.setString(8, person.getAvatarPath());
				stmt.setInt(9, person.getId());
				stmt.executeUpdate();
			}
		} catch (SQLException e) {
			throw new RuntimeException("An error occured", e);
		}
	}
	
	public void deletePerson(Person person) {
		try (Connection cnx = DataSourceFactory.getDataSource().getConnection()) {
			
			// Here we pass an option to tell the DB that we want to get the
			// generated keys back
			try (PreparedStatement stmt = cnx.prepareStatement(
					"DELETE FROM person WHERE idperson = ?",
					Statement.RETURN_GENERATED_KEYS)) {
				stmt.setInt(1, person.getId());
				stmt.executeUpdate();
			}
		} catch (SQLException e) {
			throw new RuntimeException("An error occured", e);
		}
	}
	
	public void deleteTable() {
		try (Connection connection = DataSourceFactory.getDataSource().getConnection()) {
			try (Statement stmt = connection.createStatement()) {
				stmt.executeUpdate("DELETE FROM person");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException("Oops", e);
		}
	}
}
