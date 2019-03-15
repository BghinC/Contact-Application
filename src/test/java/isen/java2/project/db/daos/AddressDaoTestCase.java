package isen.java2.project.db.daos;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import org.junit.Before;
import org.junit.Test;

import isen.java2.project.model.Address;

public class AddressDaoTestCase {
	private AddressDao addressDao = new AddressDao();

	@Before
	public void initDatabase() throws Exception {
		Connection connection = DataSourceFactory.getDataSource().getConnection();
		Statement stmt = connection.createStatement();
		stmt.executeUpdate("DELETE FROM person");
		stmt.executeUpdate("DELETE FROM address");
		stmt.executeUpdate("INSERT INTO address(idaddress, number, street, postalCode, city) VALUES (1,5,'Avenue des Champs-Elysées',75000,'Paris')");
		stmt.executeUpdate("INSERT INTO address(idaddress, number, street, postalCode, city) VALUES (2,450,'Boulevard Vauban',59000,'Lille')");
		stmt.executeUpdate("INSERT INTO address(idaddress, number, street, postalCode, city) VALUES (3,88,'address 3 street',01011,'Lille')");
		stmt.close();
		connection.close();
	}
	
	@Test
	public void shouldGetAddressById() {
		// WHEN
		Address address = addressDao.getAddress(1);
		// THEN
		assertThat(address.getId()).isEqualTo(1);
		assertThat(address.getNumber()).isEqualTo(5);
		assertThat(address.getStreet()).isEqualTo("Avenue des Champs-Elysées");
		assertThat(address.getPostalCode()).isEqualTo(75000);
		assertThat(address.getCity()).isEqualTo("Paris");
	}
	
	@Test
	public void shouldAddAddress() throws Exception {
		// WHEN 
		addressDao.addAddress(new Address(4,7,"add_street",99999,"add_city"));
		// THEN
		Connection connection = DataSourceFactory.getDataSource().getConnection();
		Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery("SELECT * FROM address WHERE city='add_city'");
		assertThat(resultSet.next()).isTrue();
		assertThat(resultSet.getInt("idaddress")).isNotNull();
		assertThat(resultSet.getInt("number")).isNotNull();
		assertThat(resultSet.getString("street")).isEqualTo("add_street");
		assertThat(resultSet.getInt("postalCode")).isNotNull();
		assertThat(resultSet.getString("city")).isEqualTo("add_city");
		assertThat(resultSet.next()).isFalse();
		resultSet.close();
		statement.close();
		connection.close();
	}
}
