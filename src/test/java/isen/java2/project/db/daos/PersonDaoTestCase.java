package isen.java2.project.db.daos;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import isen.java2.project.model.Address;
import isen.java2.project.model.Person;

public class PersonDaoTestCase {
	@Before
	public void initDb() throws SQLException {
		Connection connection = DataSourceFactory.getDataSource().getConnection();
		Statement stmt = connection.createStatement();
		stmt.executeUpdate("DELETE FROM person");
		stmt.executeUpdate("DELETE FROM address");
		stmt.executeUpdate("INSERT INTO address(idaddress, number, street, postalCode, city) VALUES (1,1,'Avenue des Champs-Elysées',75000,'Paris')");
		stmt.executeUpdate("INSERT INTO address(idaddress, number, street, postalCode, city) VALUES (2,450,'Boulevard Vauban',59000,'Lille')");
		stmt.executeUpdate("INSERT INTO person(idperson, firstname, lastname, nickname, phonenumber, address_id, emailaddress, birthdate, avatarPath) "
				+ "VALUES (1, 'firstname1', 'lastname1', 'nickname1', 'phonenumber1', 1, 'emailaddress1', '2019-03-10', 'avatarPath1')");
		stmt.executeUpdate("INSERT INTO person(idperson, firstname, lastname, nickname, phonenumber, address_id, emailaddress, birthdate, avatarPath) "
				+ "VALUES (2, 'firstname2', 'lastname2', 'nickname2', 'phonenumber2', 2, 'emailaddress2', '2000-01-01', 'avatarPath2')");
		stmt.executeUpdate("INSERT INTO person(idperson, firstname, lastname, nickname, phonenumber, address_id, emailaddress, birthdate, avatarPath) "
				+ "VALUES (3, 'firstname3', 'lastname3', 'nickname3', 'phonenumber3', 2, 'emailaddress3', '1950-10-10', 'avatarPath3')");
		stmt.close();
		connection.close();
	}

	@Test
	public void shouldListPerson() throws SQLException {
		PersonDao personDao = new PersonDao();
		List<Person> filmList = personDao.listPeople();
		//Should contain 3 items
		assertThat(filmList).hasSize(3).doesNotContainNull();
	}
	
	@Test
	public void shouldAddPerson() throws Exception {
		PersonDao personDao = new PersonDao();
		Person personToCreate = new Person(Integer.valueOf(42), "add_firstname", "add_lastname", "add_nickname", 
				"add_phonenumber", new Address(2,450,"Boulevard Vauban",59000,"Lille"), "add_emailaddress", LocalDate.of(1975, 12, 25), "add_avatarPath");
		
		Person newPerson = personDao.addPerson(personToCreate);

		Connection connection = DataSourceFactory.getDataSource().getConnection();
		Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery("SELECT * FROM person WHERE firstname='add_firstname'");
		assertThat(resultSet.next()).isTrue();
		assertThat(resultSet.getInt("idperson")).isNotNull();
		assertThat(resultSet.getString("firstname")).isEqualTo("add_firstname");
		assertThat(resultSet.getString("lastname")).isEqualTo("add_lastname");
		assertThat(resultSet.getString("nickname")).isEqualTo("add_nickname");
		assertThat(resultSet.getString("phonenumber")).isEqualTo("add_phonenumber");
		assertThat(resultSet.getString("emailaddress")).isEqualTo("add_emailaddress");
		assertThat(resultSet.getDate("birthdate").toLocalDate()).isEqualTo(LocalDate.of(1975, 12, 25));
		assertThat(resultSet.getString("avatarPath")).isEqualTo("add_avatarPath");
		

		assertThat(resultSet.next()).isFalse(); 
		resultSet.close();
		statement.close();
		connection.close();
		
		assertThat(newPerson).isNotNull();
		assertThat(newPerson.getId()).isNotNull(); 
		

		assertThat(newPerson.getFirstname()).isEqualTo(personToCreate.getFirstname());
		assertThat(newPerson.getLastname()).isEqualTo(personToCreate.getLastname());
		assertThat(newPerson.getNickname()).isEqualTo(personToCreate.getNickname());
		assertThat(newPerson.getPhonenumber()).isEqualTo(personToCreate.getPhonenumber());
		assertThat(newPerson.getEmailaddress()).isEqualTo(personToCreate.getEmailaddress());
		assertThat(newPerson.getBirthdate()).isEqualTo(personToCreate.getBirthdate());
		assertThat(newPerson.getAvatarPath()).isEqualTo(personToCreate.getAvatarPath());
	}
}
