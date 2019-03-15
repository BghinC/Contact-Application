package isen.java2.project.db.daos;

import javax.sql.DataSource;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

public class DataSourceFactory {

	private static MysqlDataSource dataSource;

	public static DataSource getDataSource() {
		if (dataSource == null) {
			dataSource = new MysqlDataSource();
			dataSource.setServerName("127.0.0.1");
			dataSource.setPort(3306);
			dataSource.setDatabaseName("contact_app");
			dataSource.setUser("root");
			dataSource.setPassword("");
		}
		return dataSource;
	}
}

