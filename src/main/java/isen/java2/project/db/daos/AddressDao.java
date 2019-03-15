package isen.java2.project.db.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import isen.java2.project.model.Address;

public class AddressDao {
	
	public Address addAddress(Address address) {
		try (Connection cnx = DataSourceFactory.getDataSource().getConnection()) {
			
			try (PreparedStatement stmt = cnx.prepareStatement(
					"INSERT INTO address(number,street,postalCode,city) VALUES(?,?,?,?)",
					Statement.RETURN_GENERATED_KEYS)) {
				stmt.setInt(1, address.getNumber());
				stmt.setString(2, address.getStreet());
				stmt.setInt(3, address.getPostalCode());
				stmt.setString(4, address.getCity());
				stmt.executeUpdate();
				// A little routine to grab the key and set it in our object
				try (ResultSet keys = stmt.getGeneratedKeys()) {
					keys.next();
					address.setId(keys.getInt(1));
					return address;
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException("An error occured", e);
		}
	}
	
	public Address getAddress(int idaddress) {
		try (Connection cnx = DataSourceFactory.getDataSource().getConnection()) {
			try (PreparedStatement stmt = cnx.prepareStatement(
					"SELECT * FROM address WHERE idaddress = ?")) {
				
				stmt.setInt(1, idaddress);
				ResultSet results = stmt.executeQuery();
				
				results.next();
				
				Address address = new Address(
						results.getInt("idaddress"),
						results.getInt("number"),
						results.getString("street"),
						results.getInt("postalCode"),
						results.getString("city")
						);
				
				return address;
			}
		} catch (SQLException e) {
			throw new RuntimeException("Oops", e);
		}
	}
	
	public boolean isAddressExist(Address address) {
		
		try (Connection cnx = DataSourceFactory.getDataSource().getConnection()) {
			try (PreparedStatement stmt = cnx
						.prepareStatement("SELECT idaddress FROM address WHERE number = ? AND street = ? AND postalCode = ? AND city = ?")) {
				stmt.setInt(1, address.getNumber());
				stmt.setString(2, address.getStreet());
				stmt.setInt(3, address.getPostalCode());
				stmt.setString(4, address.getCity());
				ResultSet results = stmt.executeQuery();
				if (results.next()) {
					address.setId(results.getInt("idaddress"));
					return true;
				}
				return false;
			}
		} catch (SQLException e) {
			throw new RuntimeException("Oops", e);
		}
	}
	
	public void deleteTable() {
		try (Connection connection = DataSourceFactory.getDataSource().getConnection()) {
			try (Statement stmt = connection.createStatement()) {
				stmt.executeUpdate("DELETE FROM address");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException("Oops", e);
		}
	}
}
